package org.example.controller;

import org.example.constants.PassengerType;
import org.example.entities.Passenger;
import org.example.services.PassengerService;
import org.example.services.impl.PassengerServiceImpl;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(final PassengerServiceImpl passengerService) {
        this.passengerService = passengerService;
    }

    public void createPassenger(final String name, final String passengerNumber, final BigDecimal balance, final PassengerType passengerType) {
        passengerService.createPassenger(name, passengerNumber, balance, passengerType);
    }

    public void registerForPackage(final String passengerName, final String packageName) {
        passengerService.registerForPackage(passengerName, packageName);
    }

    public void signUpForActivity(final String passengerName, final String activityName) {
        passengerService.signUpForActivity(passengerName, activityName);
    }

    public List<Passenger> getPassengerDetails() {
        return passengerService.getAllPassengers();

    }


}
