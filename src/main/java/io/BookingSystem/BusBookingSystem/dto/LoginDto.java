package io.BookingSystem.BusBookingSystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
	@NotBlank(message = "Email is required")
	@Size(min = 10)
	@Email(regexp = ".+@gmail\\.com", message = "Email should be a valid gmail address")
	@Schema(description = "Email address of the user", example = "john.doe@example.com")
	
	private String email;
	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Must contain 8 letters")
	@Schema(description = "Password for the user account", example = "P@ssw0rd")
	private String password;
}
