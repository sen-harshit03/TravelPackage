package org.example.services.impl;

import org.example.constants.PassengerType;
import org.example.entities.Activity;
import org.example.entities.Passenger;
import org.example.entities.TravelPackage;
import org.example.repository.PassengerRepository;
import org.example.repository.TravelPackageRepository;
import org.example.services.PassengerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final TravelPackageRepository travelPackageRepository;

    public PassengerServiceImpl(final PassengerRepository passengerRepository, final TravelPackageRepository travelPackageRepository) {
        this.passengerRepository = passengerRepository;
        this.travelPackageRepository = travelPackageRepository;
    }

    public void createPassenger(final String name, final String passengerNumber, final BigDecimal balance,
                                final PassengerType passengerType) {

        final Passenger passenger = Passenger.builder()
                .name(name)
                .passengerNumber(passengerNumber)
                .passengerType(passengerType)
                .activities(new ArrayList<>())
                .balance(balance)
                .build();

        passengerRepository.savePassenger(passenger);

    }

    public void registerForPackage(final String passengerName, final String travelPackageName) {
        final Passenger passenger = passengerRepository.findPassengerByName(passengerName).orElseThrow(
                () -> new RuntimeException("Passenger doesn't exist with name " + passengerName)
        );

        final TravelPackage travelPackage = travelPackageRepository.findTravelPackageByName(travelPackageName).orElseThrow(
                () -> new RuntimeException("Travel package doesn't exist with name " + travelPackageName)
        );

        if(travelPackage.getPassengerList().size() >= travelPackage.getPassengerCapacity()) {
            throw new RuntimeException("Travel slot is completely booked");
        }

        final List<Passenger> passengerList = travelPackage.getPassengerList();
        passengerList.add(passenger);
        passenger.setTravelPackage(travelPackage);
    }

    public void signUpForActivity(final String passengerName, final String activityName) {
        // Check for capacity of activity and passenger
        final Activity activity = travelPackageRepository.findActivityByName(activityName).orElseThrow(
                () -> new RuntimeException("Activity doesn't exist")
        );

        if(activity.getPassengerList().size() >= activity.getCapacity()) {
            System.out.println(String.format("Adventure %s is completely booked. Please try another adventure", activityName));
//            throw new RuntimeException(String.format("Adventure %s is completely booked. Please try another adventure", activityName));
            return;
        }

        final Passenger passenger = passengerRepository.findPassengerByName(passengerName).orElseThrow(
                () -> new RuntimeException("Passenger doesn't exist")
        );

        if(canSignUp(passenger, activity)) {
            final List<Activity> activityList = passenger.getActivities();
            activityList.add(activity);
            final List<Passenger> passengerList = activity.getPassengerList();
            passengerList.add(passenger);

            updateBalance(passenger, activity);
        }
    }

    public List<Passenger> getAllPassengers() {
        List<Passenger> passengerList = passengerRepository.findAllPassengers().orElseThrow(
                () -> new RuntimeException("No passengers found")
        );
        return passengerList;
    }

    private void updateBalance(final Passenger passenger, final Activity activity) {
        if(PassengerType.PREMIUM == passenger.getPassengerType()) {
            return;
        } else if(PassengerType.GOLD == passenger.getPassengerType()) {
            final BigDecimal cost = activity.getCost().multiply(BigDecimal.valueOf(0.90));
            final BigDecimal balance = passenger.getBalance();
            passenger.setBalance(balance.subtract(cost));
        } else {
            final BigDecimal cost = activity.getCost();
            final BigDecimal balance = passenger.getBalance();
            passenger.setBalance(balance.subtract(cost));
        }
    }

    private boolean canSignUp(final Passenger passenger, final Activity activity) {
        if(PassengerType.PREMIUM == passenger.getPassengerType()) {
            return true;
        } else if (PassengerType.GOLD == passenger.getPassengerType()) {
            final BigDecimal cost = activity.getCost().multiply(BigDecimal.valueOf(0.90));
            final BigDecimal balance = passenger.getBalance();
            return balance.compareTo(cost) >= 0;
        } else {
            final BigDecimal cost = activity.getCost();
            final BigDecimal balance = passenger.getBalance();
            return balance.compareTo(cost) >= 0;
        }
    }
}

