package guru.springfamework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import guru.springfamework.api.v1.mapper.ProductMapper;
import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;

public class VendorServiceTest {
	
	private VendorService vendorService;
	
	@Mock
	private VendorRepository vendorRepository;
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE,ProductMapper.INSTANCE);
	}

	@Test
	public void testGetAllVendors() {
		//given
		List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());
		when(vendorRepository.findAll()).thenReturn(vendors);
		//when
		List<VendorDTO> vendorsDTO = vendorService.getAllVendors();
		
		assertEquals( 2,vendorsDTO.size());
	}
	
	@Test
	public void testGetVendorById() {
		//given 
		Vendor vendor = new Vendor();
		vendor.setId(1L);
		vendor.setName("vendor1");
		when(vendorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(vendor));
		
		//when
		VendorDTO vendorDTO = vendorService.getVendorById(1L);
		
		assertEquals("vendor1", vendorDTO.getName());
		assertEquals(VendorController.BASE_URL + "/1/products/", vendorDTO.getProductUrl());
		
	}
	
	@Test
	public void testCreateVendor() {
		Vendor vendor = new Vendor();
		vendor.setName("vendor1");
		vendor.setId(1L);
		
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(vendor.getName());
		
		when(vendorRepository.save(Mockito.any(Vendor.class))).thenReturn(vendor);
		
		VendorDTO newVendorDTO = vendorService.createNewVendor(vendorDTO);
		
		assertEquals(vendor.getName(), newVendorDTO.getName());
		assertEquals(VendorController.BASE_URL + "/1", newVendorDTO.getVendorUrl());
	}
	
	@Test
	public void testDeleteById() {
		vendorService.deleteVendor(1L);
		verify(vendorRepository, times(1)).deleteById(Mockito.anyLong());
	}
	

}
