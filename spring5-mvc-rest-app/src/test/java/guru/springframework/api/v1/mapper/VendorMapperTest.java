package guru.springframework.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;

public class VendorMapperTest {
	
	private static final String VENDOR_NAME = "VendorName";
	private static final long ID = 1L;
	VendorMapper vendorMapper = VendorMapper.INSTANCE;
	
	
	@Test
	public void vendorToVendorDTOTest() {
		
		Vendor vendor = new Vendor();
		vendor.setId(ID);
		vendor.setName(VENDOR_NAME);
		
		VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
		
		
		assertEquals(VENDOR_NAME, vendorDTO.getName());
		
		
	}

}
