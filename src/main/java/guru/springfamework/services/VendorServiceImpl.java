package guru.springfamework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import guru.springfamework.api.v1.mapper.ProductMapper;
import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.ProductDTO;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.exceptions.ResourceNotFountException;
import guru.springfamework.repositories.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {

	private final VendorRepository vendorRepository;
	private final VendorMapper vendorMapper;
	private final ProductMapper productMapper;

	public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper,ProductMapper productMapper) {
		super();
		this.vendorRepository = vendorRepository;
		this.vendorMapper = vendorMapper;
		this.productMapper = productMapper;
	}

	@Override
	public List<VendorDTO> getAllVendors() {
		return vendorRepository.findAll().stream().map(vendor -> {
			VendorDTO vendorDTO = this.vendorMapper.vendorToVendorDTO(vendor);
			vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
			return vendorDTO;
		}).collect(Collectors.toList());
	}



	@Override
	public VendorDTO getVendorById(Long id) {
		return vendorRepository.findById(id).map(
				vendor -> {
					VendorDTO vendorDTO = this.vendorMapper.vendorToVendorDTO(vendor);
					vendorDTO.setProductUrl(getProductsUrl(vendor.getId()));
					return vendorDTO;
				})
		.orElseThrow(ResourceNotFountException::new);
	}
	
	
	private String getVendorUrl(Long id) {
		return VendorController.BASE_URL + "/" + id;
	}
	
	private String getProductsUrl(Long id) {
		return VendorController.BASE_URL + "/" + id + "/products/";
	}

	@Override
	public List<ProductDTO> getVendorProducts(Long id) {
		
		Vendor vendor =  vendorRepository.findById(id).orElseThrow(ResourceNotFountException::new);
		return vendor.getProducts().stream()
				.map(product ->{
					ProductDTO productDTO = this.productMapper.productToProductDTO(product);
					productDTO.setProductUrl("/shop/products/" + product.getId());
					return productDTO;
		}).collect(Collectors.toList());
	}

	@Override
	public VendorDTO createNewVendor(VendorDTO vendorDTO) {
		return saveAndReturnDTO(vendorMapper.vendorDTOToVendor(vendorDTO));
	}
	
	
	private VendorDTO saveAndReturnDTO(Vendor customer) {
		Vendor savedVendor = vendorRepository.save(customer);
		VendorDTO returnDto = vendorMapper.vendorToVendorDTO(savedVendor);
		returnDto.setVendorUrl(getVendorUrl( savedVendor.getId()));
		return returnDto;
		
	}

	@Override
	public void deleteVendor(Long id) {
		vendorRepository.deleteById(id);
		
	}

	@Override
	public VendorDTO saveVendorById(Long id, VendorDTO vendorDTO) {
		Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
		vendor.setId(id);
		return saveAndReturnDTO(vendor);
	}

}
