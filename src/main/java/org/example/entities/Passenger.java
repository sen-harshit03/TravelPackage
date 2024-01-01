package org.example.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.constants.PassengerType;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @Builder
public class Passenger {
    private String name;
    private String passengerNumber;
    private BigDecimal balance;
    private PassengerType passengerType;
    private TravelPackage travelPackage;
    private List<Activity> activities;
}
