package guru.springframework.services;



import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.services.CustomerService;
import guru.springframework.services.CustomerServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	CustomerService customerService;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("Loading Customer Data");
		System.out.println(customerRepository.findAll().size());
		
		//Setup data for testing
		Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
		bootstrap.run();
		customerService = new CustomerServiceImpl(customerRepository,CustomerMapper.INSTANCE);
		
	}
	
	@Test
	public void patchCustomerUpdateFirstName() {
		
		String updatedName = "UpdatedName";
		Long id = getCustomerIdValue();
		
		Customer originalCustomer = customerRepository.getOne(id);
		assertNotNull(originalCustomer);
		String originalFirstName = originalCustomer.getFirstName();
		String originalLastName = originalCustomer.getLastName();
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName(updatedName);
		
		customerService.patchCustomer(id, customerDTO);
		
		Customer updatedCustomer = customerRepository.findById(id).get();
		
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getFirstName());
		assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstName())));
		assertThat(originalLastName, equalTo(updatedCustomer.getLastName()));
		
	}
	@Test
	public void patchCustomerUpdateLastName() {
		
		String updatedName = "UpdatedName";
		Long id = getCustomerIdValue();
		
		Customer originalCustomer = customerRepository.getOne(id);
		assertNotNull(originalCustomer);
		String originalFirstName = originalCustomer.getFirstName();
		String originalLastName = originalCustomer.getLastName();
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setLastName(updatedName);
		
		customerService.patchCustomer(id, customerDTO);
		
		Customer updatedCustomer = customerRepository.findById(id).get();
		
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getLastName());
		assertThat(originalFirstName, equalTo(updatedCustomer.getFirstName()));
		assertThat(originalLastName, not(equalTo(updatedCustomer.getLastName())));
		
		
	}
	
	public Long getCustomerIdValue() {
		List<Customer> customers = customerRepository.findAll();
		return customers.get(0).getId();
		
		
	}

}
