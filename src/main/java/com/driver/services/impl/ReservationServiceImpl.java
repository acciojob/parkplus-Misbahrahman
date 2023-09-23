package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        Optional<ParkingLot> response = parkingLotRepository3.findById(parkingLotId);
        if(!response.isPresent())throw new Exception("Reservation cannot be made");
        ParkingLot parkingLot = response.get();

        Optional<User> userResponse = userRepository3.findById(userId);
        if(!userResponse.isPresent())throw  new Exception("Reservation cannot be made");

        User user = userResponse.get();

        List<Spot> spots = parkingLot.getSpots();

        SpotType reqSpotType = SpotType.OTHERS;
        if(numberOfWheels <= 2)reqSpotType = SpotType.TWO_WHEELER;
        else if(numberOfWheels <= 4)reqSpotType = SpotType.FOUR_WHEELER;

        List<Spot> availableSpots = new ArrayList<>();
        for(Spot spot : spots)if(spot.getSpotType() == reqSpotType && spot.getOccupied())availableSpots.add(spot);

        Collections.sort(availableSpots , (a , b) -> {return (a.getPricePerHour() - b.getPricePerHour());});
        if(availableSpots.size() == 0)throw new Exception("Reservation cannot be made");
        Spot reserveSpot = availableSpots.get(0);

        Reservation reservation = new Reservation();
        reservation.setSpot(reserveSpot);
        reservation.setUser(user);
        reservation.setNumberOfHours(timeInHours);

        reserveSpot.setOccupied(true);

        Reservation savedReservation =  reservationRepository3.save(reservation);

        user.getReservations().add(savedReservation);
        reserveSpot.getReservations().add(savedReservation);
        userRepository3.save(user);
        spotRepository3.save(reserveSpot);
        return reservation;

    }
}
