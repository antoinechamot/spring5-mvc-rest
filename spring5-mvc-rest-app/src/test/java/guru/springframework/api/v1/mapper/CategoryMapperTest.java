package guru.springframework.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import guru.springframework.api.v1.mapper.CategoryMapper;
import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;

public class CategoryMapperTest {
	
	private static final long ID = 1L;
	private static final String JOE = "Joe";
	CategoryMapper categoryMapper = CategoryMapper.INSTANCE;
	

	@Test
	public void categoryToCategoryDTO() throws Exception {
		Category category = new Category();
		category.setName(JOE);
		category.setId(ID);
		
		CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
		
		assertEquals(Long.valueOf(1L), categoryDTO.getId());
		assertEquals(JOE, categoryDTO.getName());
		
	}

}
