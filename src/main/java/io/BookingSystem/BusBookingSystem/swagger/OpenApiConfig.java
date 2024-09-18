package io.BookingSystem.BusBookingSystem.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Bus Booking System ", version = "1"), security = @SecurityRequirement(name = "bearerAuth"))
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenApiConfig {
	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
				.group("Bus Booking System API")
				.pathsToMatch("/**")
				.packagesToScan("io.BookingSystem.BusBookingSystem.controller")
				.build();
	}

	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
				.info(new io.swagger.v3.oas.models.info.Info()
				.title("Bus Booking System ")
				.version("1"));
	}
}
