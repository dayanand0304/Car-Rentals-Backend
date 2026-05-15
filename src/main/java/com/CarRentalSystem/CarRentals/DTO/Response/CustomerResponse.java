package com.CarRentalSystem.CarRentals.DTO.Response;

import com.CarRentalSystem.CarRentals.Enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "DTO representing customer details in response")
public class CustomerResponse {

    @Schema(example = "101", description = "Customer ID")
    private Integer customerId;

    @Schema(example = "Ravi Kumar", description = "Customer name")
    private String customerName;

    @Schema(example = "ravi.kumar@example.com", description = "Email address")
    private String customerEmail;

    @Schema(example = "CUSTOMER", description = "Role of the user")
    private Role role;

    @Schema(example = "true", description = "Active status")
    private Boolean active;
}

