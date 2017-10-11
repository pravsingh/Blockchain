package com.hashfold.blockchain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Basic configuration for the application.
 * 
 * @author Praveendra Singh
 *
 */
@Configuration
@EnableSwagger2 // initialize Swagger.
@ComponentScan({ "com.hashfold.blockchain.service", "com.hashfold.blockchain.api", "com.hashfold.blockchain.config" })
public class BlockchainConfig {

	/**
	 * initialize the Swagger docklet.
	 * 
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}