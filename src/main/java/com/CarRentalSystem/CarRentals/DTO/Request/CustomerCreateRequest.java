package com.CarRentalSystem.CarRentals.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequest {
    private String customerName;
    private String customerPhoneNo;
    private String customerEmail;
}
