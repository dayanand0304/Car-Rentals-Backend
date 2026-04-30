package com.CarRentalSystem.CarRentals.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Car Rentals API is running 🚀";
    }
}
