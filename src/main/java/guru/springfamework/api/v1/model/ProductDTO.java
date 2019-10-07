package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProductDTO {
	
	private String name;
	
	@JsonProperty("product_url")
	private String productUrl;

}
