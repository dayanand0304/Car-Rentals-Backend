package com.CarRentalSystem.CarRentals.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerResponse {
    private Integer customerId;
    private String customerName;
    private String customerPhoneNo;
    private String customerEmail;
}

