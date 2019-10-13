package guru.springframework.services;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.controllers.v1.CustomerController;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.services.CustomerService;
import guru.springframework.services.CustomerServiceImpl;

public class CustomerServiceTest {
	
	private static final String FIRST_NAME = "Lucas";

	private static final long ID = 1L;

	private CustomerService customerService;
	
	@Mock
	CustomerRepository customerRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
	}
	
	
	@Test
	public void getAllCustomers() {
		//given
		List<Customer> customers = Arrays.asList(new Customer(),new Customer(),new Customer());
		when(customerRepository.findAll()).thenReturn(customers);
		
		//when
		List<CustomerDTO> customersDTO = customerService.getAllCustomers();
		
		//then
		assertEquals(3, customersDTO.size());
	}
	
	@Test
	public void getCustomerById() {
		
		Customer customer = new Customer();
		customer.setId(ID);
		customer.setFirstName(FIRST_NAME);
		customer.setLastName("TOTO");
		
		Optional<Customer> custOptional = Optional.of(customer);
		
		when(customerRepository.findById(Mockito.anyLong())).thenReturn(custOptional);
		
		
		//when 
		CustomerDTO customerDTO = customerService.getCustomerById(1L);
		
		//then
		assertEquals(FIRST_NAME, customerDTO.getFirstName());
		
		
	}
	
	@Test
	public void createNewCustomer() {
		
		//given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName("Jim");
		customerDTO.setLastName("LN");
		
		Customer savedCustomer = new Customer();
		savedCustomer.setFirstName(customerDTO.getFirstName());
		savedCustomer.setLastName(customerDTO.getLastName());
		savedCustomer.setId(1L);
		
		when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(savedCustomer);
		
		//when
		CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);
		
		
		//then
		assertEquals(customerDTO.getFirstName(), savedDTO.getFirstName());
		assertEquals("/api/v1/customers/1", savedDTO.getCustomerUrl());
		
	}
	
	
	@Test
	public void saveCustomerByDTO() {
		
		//given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName("Jim");
		customerDTO.setLastName("LN");
		
		Customer savedCustomer = new Customer();
		savedCustomer.setFirstName(customerDTO.getFirstName());
		savedCustomer.setLastName(customerDTO.getLastName());
		savedCustomer.setId(1L);
		
		when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(savedCustomer);
		
		//when
		CustomerDTO savedDTO = customerService.saveCustomerByDTO(1L, customerDTO);
		
		
		//then
		assertEquals(customerDTO.getFirstName(), savedDTO.getFirstName());
		assertEquals(CustomerController.BASE_URL + "/1", savedDTO.getCustomerUrl());
		
	}
	
	public void deleteCustomerById() {
		Long id = 1L;
		customerRepository.deleteById(id);
		verify(customerRepository,times(1)).deleteById(Mockito.anyLong());
	}
	

}
