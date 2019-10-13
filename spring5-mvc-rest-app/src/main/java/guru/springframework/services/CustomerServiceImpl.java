package guru.springframework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.controllers.v1.CustomerController;
import guru.springframework.domain.Customer;
import guru.springframework.exceptions.ResourceNotFountException;
import guru.springframework.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	
	
	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
		super();
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll()
				.stream()
				.map(customer -> {
					CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
					customerDTO.setCustomerUrl(getCustomerUrl( customer.getId()));
					return customerDTO;
				})
				.collect(Collectors.toList());
			
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		
		return customerRepository.findById(id).map(customerMapper::customerToCustomerDTO)
				.orElseThrow(ResourceNotFountException::new); 
		
	
	}

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
			return saveAndReturnDTO(customerMapper.customerDTOToCustomer(customerDTO));
	}

	
	@Override
	public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
		Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
		customer.setId(id);
		return saveAndReturnDTO(customer);
	}
	
	
	private CustomerDTO saveAndReturnDTO(Customer customer) {
		Customer savedCustomer = customerRepository.save(customer);
		CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);
		returnDto.setCustomerUrl(getCustomerUrl( savedCustomer.getId()));
		return returnDto;
		
	}

	@Override
	public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
		return customerRepository.findById(id).map(customer ->{
		
			if(customerDTO.getFirstName() != null) {
				customer.setFirstName(customerDTO.getFirstName());
			}
			
			if(customerDTO.getLastName() != null) {
				customer.setLastName(customerDTO.getLastName());
			}
			
			CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
			returnDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
			
			return returnDTO;
		}).orElseThrow(ResourceNotFountException::new);
	}

	@Override
	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);
		
	}	
	
	private String getCustomerUrl(Long id) {
		return CustomerController.BASE_URL + "/" + id;
	}

}
