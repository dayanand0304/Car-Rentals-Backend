package com.CarRentalSystem.CarRentals.ExceptionHandler;

import com.CarRentalSystem.CarRentals.CustomExceptions.Cars.CarAlreadyExistsException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Cars.CarNotAvailableException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Cars.CarNotFoundException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Customers.CustomerNotFoundException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Rentals.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //404: NOT FOUND
    @ExceptionHandler({
            CarNotFoundException.class,
            CustomerNotFoundException.class,
            RentalNotFoundException.class,
            RentalNotFoundByCustomerIdException.class,
            RentalNotFoundByCarIdException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex,
                                                        HttpServletRequest request){
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    //400: Bad Request
    @ExceptionHandler({
            DurationException.class,
            RentalTypeException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException ex,
                                                          HttpServletRequest request){
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    //400: Validation Failure
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException ex,
                                                               HttpServletRequest request){
        String message=ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("Validation failed");
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                message,
                request.getRequestURI()
        );
    }

    //409: Conflict
    @ExceptionHandler({
            CarNotAvailableException.class,
            AlreadyReturnedException.class,
            CannotCancelException.class,
            CarAlreadyExistsException.class
    })
    public ResponseEntity<ErrorResponse> handleConflict(RuntimeException ex,
                                                        HttpServletRequest request){
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    //500: Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected server error",
                request.getRequestURI()
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status,
                                                             String message,
                                                             String path){
        ErrorResponse error=new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.name(),
                message,
                path
        );
        return ResponseEntity.status(status).body(error);
    }
}