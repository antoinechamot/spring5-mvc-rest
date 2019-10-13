package guru.springframework.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.CategoryListDTO;
import guru.springframework.services.CategoryService;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {
	
	public static final String BASE_URL = "/api/v1/categories";
	
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CategoryListDTO getAllCategories(){
		return new CategoryListDTO(categoryService.getAllCategories());
	}
	
	@GetMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public CategoryDTO getCategoryByName(@PathVariable String name){
		return categoryService.getCategoryByName(name);
	}
 

}
