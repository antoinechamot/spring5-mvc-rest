package guru.springframework.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Product;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;

@Component
public class Bootstrap implements CommandLineRunner{
	
	private CategoryRepository categoryRepository;
	private CustomerRepository customerRepository;
	private VendorRepository vendorRepository;

	public Bootstrap(CategoryRepository categoryRepository,CustomerRepository customerRepository) {
		super();
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		
		loadCategory();
		loadCustomers();
		loadVendors();
		System.out.println(" DataLoaded = " + categoryRepository.count());
		
		
		
	}

	private void loadCategory() {
		Category fruits = new Category();
		fruits.setName("fruitsFruit");
		
		Category dried = new Category();
		fruits.setName("driedFruit");
		
		Category freshy = new Category();
		fruits.setName("freshyFruit");
		
		Category exotic = new Category();
		fruits.setName("exoticFruit");
		
		Category nuts = new Category();
		fruits.setName("nutsFruit");
		
		categoryRepository.save(fruits);
		categoryRepository.save(dried);
		categoryRepository.save(freshy);
		categoryRepository.save(exotic);
		categoryRepository.save(nuts);
	}

	private void loadCustomers() {
		Customer cust1 = new Customer();
		cust1.setId(1L);
		cust1.setFirstName("cust1FN");
		cust1.setLastName("cust1LastName");
		
		
		Customer cust2 = new Customer();
		cust2.setFirstName("cust1FN");
		cust2.setLastName("cust1LastName");
		cust2.setId(2L);
		
		customerRepository.save(cust1);
		customerRepository.save(cust2);
	}
	
	
	private void loadVendors() {
		Vendor vendor1 = new Vendor();
		vendor1.setId(1L);
		vendor1.setName("vend1");
		
		Product product1 = new Product();
		product1.setId(1L);
		product1.setName("product1");
		
		Product product2 = new Product();
		product2.setId(2L);
		product2.setName("product2");
		
		vendor1.addProduct(product1);
		vendor1.addProduct(product2);
		
		Vendor vendor2 = new Vendor();
		vendor2.setId(2L);
		vendor2.setName("vend2");
		
		vendor2.addProduct(product1);
		
		
		
		vendorRepository.save(vendor1);
		vendorRepository.save(vendor2);
		
	}
	
	

}
