package guru.springframework.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.api.v1.model.ProductListDTO;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.services.VendorService;

@RestController
@RequestMapping({VendorController.BASE_URL})
public class VendorController {
	
	public static final String BASE_URL = "/api/v1/vendors";
	
	private final VendorService vendorService;
	
	
	public VendorController(VendorService vendorService) {
		super();
		this.vendorService = vendorService;
	}



	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public VendorListDTO getAllVendors(){
		return new VendorListDTO(vendorService.getAllVendors());
	}

	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO getVendorById(@PathVariable Long id) {
		return vendorService.getVendorById(id);
	}
	
	@GetMapping("/{id}/products/")
	@ResponseStatus(HttpStatus.OK)
	public ProductListDTO getVendorProducts(@PathVariable Long id) {
		return new ProductListDTO(vendorService.getVendorProducts(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
		return vendorService.createNewVendor(vendorDTO);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteVendor(@PathVariable Long id) {
		vendorService.deleteVendor(id);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO updatCustomer(@PathVariable Long id,@RequestBody VendorDTO vendorDTO) {
		return vendorService.saveVendorById(id, vendorDTO);
	}
}
