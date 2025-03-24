package com.familywarehouse.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "User", description = "Schema to hold User details")
public class UserDto {

    private Long id;

    @NotBlank
    private String firstName;

    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotNull(message = "Password must not be null")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$",
            message = "Password must contain at least one digit, letters in lower and upper cases, and length more than 4 symbols"
    )
    @Schema(description = "User's password", example = "Aaa1")
    private String password;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    @Schema(description = "User's mobile number", example = "1234567890")
    private String mobileNumber;
}
