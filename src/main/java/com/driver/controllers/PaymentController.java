package com.driver.controllers;

import com.driver.model.Payment;
import com.driver.model.Reservation;
import com.driver.services.PaymentService;
import com.driver.services.impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;


@RestController
@RequestMapping(value = "/payment")
public class PaymentController {
	
	@Autowired
    PaymentServiceImpl paymentService;

    @PostMapping("/pay")
    public ResponseEntity pay(@RequestParam Integer reservationId, @RequestParam Integer amountSent, @RequestParam String mode) throws Exception{
        //Attempt a payment of amountSent for reservationId using the given mode ("cASh", "card", or "upi")
        //If the amountSent is less than bill, throw "Insufficient Amount" exception, otherwise update payment attributes
        //If the mode contains a string other than "cash", "card", or "upi" (any character in uppercase or lowercase), throw "Payment mode not detected" exception.
        //Note that the reservationId always exists
        try {
            Payment payment =  paymentService.pay(reservationId , amountSent , mode);
            return new ResponseEntity(payment , HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }
}
