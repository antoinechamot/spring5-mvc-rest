package guru.springframework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import guru.springframework.api.v1.model.ProductDTO;
import guru.springframework.domain.Product;

@Mapper(componentModel="spring")
public interface ProductMapper {
	
	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
	
	ProductDTO productToProductDTO(Product product);

}
