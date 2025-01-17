package guru.springframework.services;

import java.util.List;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.ProductDTO;
import guru.springframework.api.v1.model.VendorDTO;

public interface VendorService {
	
	List<VendorDTO> getAllVendors(); 
	VendorDTO getVendorById(Long id);
	List<ProductDTO> getVendorProducts(Long id);
	VendorDTO createNewVendor(VendorDTO vendorDTO);
	void deleteVendor(Long id);
	VendorDTO saveVendorById(Long id,VendorDTO vendorDTO);
	

}
