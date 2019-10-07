package guru.springfamework.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.v1.CustomerController;
import guru.springfamework.domain.Customer;

public class CustomerMapperTest {
	
	CustomerMapper customerMapper = CustomerMapper.INSTANCE;
	private static final String FIRST_NAME = "Toto";
	private static final String LAST_NAME = "TATA";
	private static final Long ID = 1L;
	
	
	@Test
	public void customerToCustomerDTOTest() {
		
		Customer customer = new Customer();
		customer.setFirstName(FIRST_NAME);
		customer.setLastName(LAST_NAME);
		customer.setId(ID);
		
		
		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
	
		assertEquals(FIRST_NAME,customerDTO.getFirstName());
		assertEquals(LAST_NAME, customerDTO.getLastName());
	}

}
