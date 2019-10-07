package guru.springfamework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import guru.springfamework.api.v1.model.ProductDTO;
import guru.springfamework.domain.Product;

@Mapper(componentModel="spring")
public interface ProductMapper {
	
	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
	
	ProductDTO productToProductDTO(Product product);

}
