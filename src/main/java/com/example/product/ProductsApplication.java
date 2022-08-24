package com.example.product;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
/***/
@SuppressWarnings({"ALL", "CheckStyle"})
@OpenAPIDefinition(info = @Info(title = "CRUD APPLICATION ", description = "CRUD Application of Categories which contains List Of Products"))
//@EnableJpaRepositories("com.example.product.repository.*")
//@ComponentScan({"com.example.product.service.*"})
//@EntityScan(basePackages = "com.example.product.entity.*")
public class ProductsApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ProductsApplication.class, args);
	}
}
