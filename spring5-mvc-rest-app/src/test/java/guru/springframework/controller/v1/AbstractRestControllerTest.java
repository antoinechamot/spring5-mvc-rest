package guru.springframework.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractRestControllerTest {

	public static String asJsonString(final Object obj) throws JsonProcessingException {

		return new ObjectMapper().writeValueAsString(obj);

	}

}
