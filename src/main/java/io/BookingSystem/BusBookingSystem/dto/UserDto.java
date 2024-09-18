package io.BookingSystem.BusBookingSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {

	@NotBlank(message = "Password is mandatory field")
	@Size(min = 6, message = "Must contain 8 letters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$", message = "Must contain Upper case,Small case ,Numbers and Symbols")
	@Schema(description = "Username of the user", example = "john_doe")
	private String userName;

	@NotBlank(message = "Email is Required field")
	@Size(min = 10)
	@Email(regexp = ".+@gmail\\.com", message = "Email should be a valid gmail address")
	@Schema(description = "Email address of the user", example = "john.doe@example.com")
	private String email;

	@NotBlank(message = "Password is mandatory field")
	@Size(min = 8, message = "Must contain 8 letters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Must contain Upper case,Small case ,Numbers and Specail Symbols")
	@Schema(description = "Password for the user account", example = "P@ssw0rd")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@NotBlank
	@Schema(description = "Role assigned to the user", example = "USER")
	private String role;

}
