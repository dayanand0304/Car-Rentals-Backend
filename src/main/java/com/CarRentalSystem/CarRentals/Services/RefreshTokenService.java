package com.CarRentalSystem.CarRentals.Services;

import com.CarRentalSystem.CarRentals.CustomExceptions.Customers.RefreshTokenExpireException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Customers.RefreshTokenNotFoundException;
import com.CarRentalSystem.CarRentals.Entities.Customer;
import com.CarRentalSystem.CarRentals.Entities.RefreshToken;
import com.CarRentalSystem.CarRentals.Repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    //CREATE REFRESH TOKEN
    public RefreshToken createRefreshToken(Customer customer){
        RefreshToken token=new RefreshToken();

        token.setCustomer(customer);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plus(30, ChronoUnit.DAYS));

        return refreshTokenRepository.save(token);
    }

    //VALIDATE TOKEN EXPIRY
    public RefreshToken VerifyTokenExpiry(RefreshToken token){
        if(token.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(token);
            throw new RefreshTokenExpireException();
        }
        return token;
    }

    public RefreshToken findByToken(String token){
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(()->new RefreshTokenNotFoundException());
    }
}
