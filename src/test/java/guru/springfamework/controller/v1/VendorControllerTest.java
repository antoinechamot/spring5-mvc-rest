package guru.springfamework.controller.v1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.fasterxml.jackson.core.JsonProcessingException;

import ch.qos.logback.core.boolex.Matcher;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.CustomerController;
import guru.springfamework.controllers.v1.RestResponseEntityExceptionHandler;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.services.VendorService;

public class VendorControllerTest extends AbstractRestControllerTest {
	
	
	@Mock
	private VendorService vendorService;
	
	@InjectMocks
	private VendorController vendorController;
	
	MockMvc mockMvc;
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}
	
	@Test
	public void testListVendors() throws Exception {
		
		VendorDTO vendor1 = new VendorDTO();
		vendor1.setName("TOTO");
		vendor1.setVendorUrl(VendorController.BASE_URL + "/1");
		
		VendorDTO vendor2 = new VendorDTO();
		vendor2.setName("TOTO2");
		vendor2.setVendorUrl(VendorController.BASE_URL + "/2");
		
		List<VendorDTO> vendors = Arrays.asList(vendor1,vendor2);
		when(vendorService.getAllVendors()).thenReturn(vendors);
		
		mockMvc.perform(get(VendorController.BASE_URL)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.vendors", Matchers.hasSize(2)));
		
	}
	
	@Test
	public void testGetVendorById() throws Exception {
		VendorDTO vendor1 = new VendorDTO();
		vendor1.setName("TOTO");
		vendor1.setVendorUrl(VendorController.BASE_URL + "/1/products/");
		
		when(vendorService.getVendorById(Mockito.anyLong())).thenReturn(vendor1);
		
		mockMvc.perform(get(VendorController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", Matchers.equalTo("TOTO")));
		
	}
	
	@Test
	public void testPostVendor() throws  Exception {
		VendorDTO vendor1 = new VendorDTO();
		vendor1.setName("TOTO");
		
		VendorDTO returnedVendorDTO = new VendorDTO();
		returnedVendorDTO.setName(vendor1.getName());
		returnedVendorDTO.setVendorUrl(VendorController.BASE_URL + "/1");
		
		when(vendorService.createNewVendor(Mockito.any(VendorDTO.class))).thenReturn(returnedVendorDTO);
		
		
		mockMvc.perform(post(VendorController.BASE_URL).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(vendor1)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.name", Matchers.equalTo(vendor1.getName())))
		.andExpect(jsonPath("$.vendor_url", Matchers.equalTo(VendorController.BASE_URL + "/1")));
	}
	
	@Test
	public void testDeleteById() throws Exception {
		mockMvc.perform(delete(VendorController.BASE_URL+"/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		verify(vendorService, Mockito.times(1)).deleteVendor(Mockito.anyLong());
	}
	
	
	@Test
	public void testUpdateVendorById() throws Exception {
	
		VendorDTO returnedDTO = new VendorDTO();
		returnedDTO.setName("TOTO");
		returnedDTO.setVendorUrl(VendorController.BASE_URL + "/1");
		
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName("TOTO2");
		vendorDTO.setVendorUrl(VendorController.BASE_URL + "/1");
		
		when(vendorService.saveVendorById(Mockito.anyLong(), Mockito.any(VendorDTO.class))).thenReturn(returnedDTO);
		
		mockMvc.perform(put(VendorController.BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(vendorDTO)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", equalTo(returnedDTO.getName())));
	}

}
