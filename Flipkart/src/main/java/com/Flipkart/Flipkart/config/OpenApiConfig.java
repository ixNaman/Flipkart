package com.Flipkart.Flipkart.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info=@Info(
				title="E-commerce API",
				description="CRUD operations",
				summary="A basic e-commerce Backend ",
				termsOfService="Terms and conditions apply",
				contact=@Contact(
						name="Naman",
						email="123@gmail.com"),
				license=@License(
						name="License number"),
				version="v1"
			
				),
		servers= {@Server(
						description="Dev",
						url="http://localhost:8082"
						
						),
				@Server(
						description="Test",
						url="http://localhost:8082"
						
						)
						
						
				
						}
		)


public class OpenApiConfig {

}
