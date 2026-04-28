package com.CarRentalSystem.CarRentals.DTO.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Authentication response containing JWT tokens")
@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {

    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Refresh token", example = "d8f7c9a2-1234-5678-9abc-xyz")
    private String refreshToken;
}
