package com.CarRentalSystem.CarRentals.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Customer_List")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false,unique = true,length = 10)
    private String customerPhoneNo;

    @Column(nullable = false,unique = true)
    private String customerEmail;
}
