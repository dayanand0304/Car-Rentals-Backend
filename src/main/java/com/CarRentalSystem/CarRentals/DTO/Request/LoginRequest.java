package com.CarRentalSystem.CarRentals.DTO.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO for user login")
public class LoginRequest {

    @Schema(
            description = "Customer email used for login",
            example = "dayanand03@gmail.com"
    )
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid Email Format")
    private String customerEmail;

    @Schema(
            description = "Customer password",
            example = "Dayanand@123",
            format = "password"
    )
    @NotBlank(message = "Password must not be blank")
    private String password;
}