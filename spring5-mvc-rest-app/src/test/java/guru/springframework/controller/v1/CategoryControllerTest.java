package guru.springframework.controller.v1;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.controllers.v1.CategoryController;
import guru.springframework.controllers.v1.RestResponseEntityExceptionHandler;
import guru.springframework.exceptions.ResourceNotFountException;
import guru.springframework.services.CategoryService;

public class CategoryControllerTest {
	
	public static final String NAME = "Jim";
	
	@Mock
	CategoryService categoryService;
	
	@InjectMocks
	CategoryController categoryController;
	
	MockMvc mockMvc;
	
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}
	
	@Test
	public void testListCategories() throws Exception {
		//given
		CategoryDTO category1 = new CategoryDTO();
		category1.setId(1L);
		category1.setName(NAME);
		
		
		CategoryDTO category2 = new CategoryDTO();
		category2.setId(2L);
		category2.setName("Bob");
		
		List<CategoryDTO> categories = Arrays.asList(category1,category2);
		
	
		when(categoryService.getAllCategories()).thenReturn(categories);
		
		//when
		mockMvc.perform(get(CategoryController.BASE_URL)
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.categories",Matchers.hasSize(2)) );
	
	}
	
	@Test
	public void testGetByNameCategories() throws Exception {
		//given
		CategoryDTO category1 = new CategoryDTO();
		category1.setId(1L);
		category1.setName(NAME);
		
		when(categoryService.getCategoryByName(Mockito.anyString())).thenReturn(category1);
		
		mockMvc.perform(get(CategoryController.BASE_URL + "/Jim")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", Matchers.equalTo(NAME)));
		
		
		
	}
	
	@Test
	public void testGetByNameNotFound() throws Exception {
		when(categoryService.getCategoryByName(Mockito.anyString())).thenThrow(ResourceNotFountException.class);
		mockMvc.perform(get(CategoryController.BASE_URL + "/foo").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
		
	}
	
	

}
