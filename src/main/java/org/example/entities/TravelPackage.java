package org.example.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Builder
public class TravelPackage {
    private String name;
    private int passengerCapacity;
    private List<Destination> destinationList;
    private List<Passenger> passengerList;

}
