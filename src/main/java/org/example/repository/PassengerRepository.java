package org.example.repository;

import org.example.entities.Passenger;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PassengerRepository {

    private final Map<String, Passenger> passengerMap;

    public PassengerRepository() {
        passengerMap = new HashMap<>();
    }

    public void savePassenger(final Passenger passenger) {
        passengerMap.put(passenger.getName().toLowerCase(), passenger);
    }

    public Optional<Passenger> findPassengerByName(final String name) {
        return Optional.of(passengerMap.get(name.toLowerCase()));
    }

    public Optional<List<Passenger>> findAllPassengers() {
        List<Passenger> passengerList = passengerMap.values()
                .stream()
                .toList();

        return Optional.of(passengerList);
    }
}
