package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);

        parkingLotRepository1.save(parkingLot);

        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Spot spot = new Spot();
        Optional<ParkingLot> response = parkingLotRepository1.findById(parkingLotId);
        ParkingLot parkingLot = response.get();

        spot.setParkingLot(parkingLot);
        spot.setPricePerHour(pricePerHour);

        if(numberOfWheels <= 2)spot.setSpotType(SpotType.TWO_WHEELER);
        else if(numberOfWheels <= 4)spot.setSpotType(SpotType.FOUR_WHEELER);
        else spot.setSpotType(SpotType.OTHERS);

        spot.setOccupied(false);

        Spot savedSpot = spotRepository1.save(spot);
        return  savedSpot;
    }

    @Override
    public void deleteSpot(int spotId) {
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Optional<Spot> response  = spotRepository1.findById(spotId);
        Spot spot = response.get();
//        if(spot.getParkingLot().getId() != parkingLotId){
//            Optional<ParkingLot> parkingLotResponse = parkingLotRepository1.findById(parkingLotId);
//            ParkingLot parkingLot = parkingLotResponse.get();
//            spot.setParkingLot(parkingLot);
//            parkingLot.getSpotList().add(spot);
//        }
        spot.setPricePerHour(pricePerHour);
        spotRepository1.save(spot);
        return spot;

    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        Optional<ParkingLot> response = parkingLotRepository1.findById(parkingLotId);
        ParkingLot parkingLot = response.get();
        for(Spot spot : parkingLot.getSpotList())spotRepository1.delete(spot);
        parkingLotRepository1.delete(parkingLot);

    }
}
