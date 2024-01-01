package org.example.services;

import org.example.entities.TravelPackage;

import java.math.BigDecimal;
import java.util.List;

public interface TravelPackageService {
    void createDestination(final String destinationName);
    void createTravelPackage(final String travelPackageName, final int capacity, List<String> destinationNames);
    void createActivity(final String activityName, final String description, final BigDecimal cost,
                        final int capacity, final String destinationName);
    TravelPackage getTravelPackageByName(final String travelPackageName);


}
