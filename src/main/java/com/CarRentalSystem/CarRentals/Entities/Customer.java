package com.CarRentalSystem.CarRentals.Entities;

import com.CarRentalSystem.CarRentals.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "customer_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Column(nullable = false,length=100)
    private String customerName;

    @Column(nullable = false,unique = true,length = 10)
    private String customerPhoneNo;

    @Column(nullable = false,unique = true,length = 40)
    private String customerEmail;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean active=true;

    @OneToMany(mappedBy="customer",
            fetch = FetchType.LAZY)
    private List<Rental> rentals;
    private LocalDateTime deletedAt;
}
