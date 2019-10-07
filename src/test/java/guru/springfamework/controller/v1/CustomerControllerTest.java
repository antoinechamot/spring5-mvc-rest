package guru.springfamework.controller.v1;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.v1.CategoryController;
import guru.springfamework.controllers.v1.CustomerController;
import guru.springfamework.controllers.v1.RestResponseEntityExceptionHandler;
import guru.springfamework.exceptions.ResourceNotFountException;
import guru.springfamework.services.CustomerService;

public class CustomerControllerTest extends AbstractRestControllerTest{
	
	@Mock
	CustomerService customerService;
	
	@InjectMocks
	CustomerController customerController;
	
	MockMvc mockMvc;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}
	
	@Test
	public void testListCustomers() throws Exception {
		CustomerDTO cust1 = new CustomerDTO();
		cust1.setFirstName("AA");
		cust1.setLastName("LastName");
		
		CustomerDTO cust2 = new CustomerDTO();
		cust2.setFirstName("BB");
		cust2.setLastName("LastName 2");
		
		List<CustomerDTO> listCustomers = Arrays.asList(cust1,cust2);
		
		when(customerService.getAllCustomers()).thenReturn(listCustomers);
		
		mockMvc.perform(get(CustomerController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.customers", Matchers.hasSize(2)));
		
	}
	
	
	@Test
	public void testgetCustomerById() throws Exception {
		
		CustomerDTO cust1 = new CustomerDTO();
		cust1.setCustomerUrl(CustomerController.BASE_URL + "/1");
		cust1.setFirstName("AA");
		cust1.setLastName("LastName");
		when(customerService.getCustomerById(Mockito.anyLong())).thenReturn(cust1);
		
		mockMvc.perform(get(CustomerController.BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstName", Matchers.equalTo("AA")));
		
	}
	
	@Test
	public void testGetCustomerByIdNotFound() throws Exception {
		when(customerService.getCustomerById(Mockito.anyLong())).thenThrow(ResourceNotFountException.class);
		mockMvc.perform(get(CustomerController.BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void createNewCustomer() throws Exception{
		
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstName("AA");
		customer.setLastName("LastName");
		
		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstName(customer.getFirstName());
		returnDTO.setLastName(customer.getLastName());
		returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");
		
		when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);
		
		
		mockMvc.perform(post(CustomerController.BASE_URL).
				contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer))
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName", equalTo("AA")))
				.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
		
		
		
	}
	
	@Test
	public void testUpdateCustomer() throws Exception {
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstName("Fred");
		customer.setLastName("LastName2");
		
		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstName(customer.getFirstName());
		returnDTO.setLastName(customer.getLastName());
		returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");
		
		when(customerService.saveCustomerByDTO(Mockito.anyLong(),Mockito.any(CustomerDTO.class))).thenReturn(returnDTO);
		
		mockMvc.perform(put(CustomerController.BASE_URL + "/1").
				contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer))
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", equalTo("Fred")))
				.andExpect(jsonPath("$.lastName", equalTo("LastName2")))
				.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
		
		
	}
	
	@Test
	public void testPatchCustomer() throws Exception {
		
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstName("Fred");
		
		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstName(customer.getFirstName());
		returnDTO.setLastName("Toto");
		returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");
		
		when(customerService.patchCustomer(Mockito.anyLong(), Mockito.any(CustomerDTO.class))).thenReturn(returnDTO);
		
		mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", equalTo("Fred")))
				.andExpect(jsonPath("$.lastName", equalTo("Toto")))
				.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
		
	}
	
	@Test
	public void testDeleteCustomer() throws Exception {
		mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		verify(customerService).deleteCustomerById(Mockito.anyLong());
	}
	


}
