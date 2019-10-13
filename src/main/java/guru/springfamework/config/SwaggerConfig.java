package guru.springfamework.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport{
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.build()
				.pathMapping("/")
				.apiInfo(metaData());
	}
	
	
	
	private ApiInfo metaData() {
		Contact contact = new Contact("Antoine Chamot", "http://ancm/about/", "antoine.chamot@toto.com");
		return new ApiInfo("Spring Mvc Rest",
				"Rest Services using Spring Boot", 
				"1.0", 
				"Term of Service",
				contact, 
				"Apache license Version 2.0", 
				"https://www.apache.org/licenses/LICENSE-2.0",
				new ArrayList<>());
	
	}
	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
	}


}
