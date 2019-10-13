package guru.springframework.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Vendor {	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@OneToMany(
			mappedBy="vendor",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Product> products = new ArrayList<>();

	
	public void addProduct(Product product) {
		this.products.add(product);
		product.setVendor(this);
	}
	
	public void removeProduct(Product product) {
		this.products.remove(product);
		product.setVendor(null);
	}
}
