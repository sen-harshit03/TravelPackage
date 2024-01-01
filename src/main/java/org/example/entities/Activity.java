package org.example.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @Builder
public class Activity {
    private String name;
    private String description;
    private BigDecimal cost;
    private int capacity;
    private Destination destination;
    private List<Passenger> passengerList;

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", capacity=" + capacity +
                ", destination=" + destination.getName() +
                ", passengerList=" + passengerList.size() +
                '}';
    }
}
