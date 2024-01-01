package org.example.services;

import org.example.constants.PassengerType;
import org.example.entities.Passenger;

import java.math.BigDecimal;
import java.util.List;

public interface PassengerService {
    void createPassenger(final String name, final String passengerNumber, final BigDecimal balance,
                         final PassengerType passengerType);

    void registerForPackage(final String passengerName, final String travelPackageName);
    void signUpForActivity(final String passengerName, final String activityName);
    List<Passenger> getAllPassengers();

}
