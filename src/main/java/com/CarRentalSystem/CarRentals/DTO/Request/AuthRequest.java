package com.CarRentalSystem.CarRentals.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String customerEmail;
    private String password;
}
