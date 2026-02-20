package com.CarRentalSystem.CarRentals.Entities;

import com.CarRentalSystem.CarRentals.Enums.BookingStatus;
import com.CarRentalSystem.CarRentals.Enums.RentalType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rental_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rentalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id",nullable = false)
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentalType rentalType;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime expectedReturnTime;

    private LocalDateTime actualReturnTime;

    private LocalDateTime canceledTime;

    @Column(precision = 9,scale = 2,nullable = false)
    private BigDecimal totalPrice;

    @Column(precision = 9,scale = 2,nullable = false)
    private BigDecimal taxAmount;

    @Column(precision = 9,scale = 2)
    private BigDecimal discountAmount;

    private BigDecimal lateFee;

    private boolean damaged;

    private BigDecimal damagedFee;

    @Column(precision = 9,scale = 2,nullable = false)
    private BigDecimal grandTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status= BookingStatus.PENDING;

}
