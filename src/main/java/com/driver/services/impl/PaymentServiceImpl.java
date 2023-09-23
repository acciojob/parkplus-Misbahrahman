package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        mode.toLowerCase();
        PaymentMode paymentMode = null;
        if(mode.equals("cash"))paymentMode = PaymentMode.CASH;
        else if(mode.equals("card"))paymentMode = PaymentMode.CARD;
        else if(mode.equals("upi"))paymentMode = PaymentMode.UPI;
        else throw new Exception("Payment mode not detected");

        Optional<Reservation> response = reservationRepository2.findById(reservationId);
        Reservation reservation = response.get();

        int amountNeeded = reservation.getNumberOfHours() * reservation.getSpot().getPricePerHour();
        if(amountNeeded < amountSent)throw new Exception("Insufficient Amount");

        Payment payment = new Payment();

        payment.setPaymentCompleted(true);
        payment.setPaymentMode(paymentMode);
        payment.setReservation(reservation);

        return payment;

    }
}
