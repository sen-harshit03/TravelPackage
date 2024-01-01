package org.example.controller;

import org.example.entities.TravelPackage;
import org.example.services.TravelPackageService;
import org.example.services.impl.TravelPackageServiceImpl;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class TravelPlannerController {

    private final TravelPackageService travelPackageService;

    public TravelPlannerController(final TravelPackageServiceImpl travelPackageService) {
        this.travelPackageService = travelPackageService;
    }

    public void createDestination(final String destinationName) {
        travelPackageService.createDestination(destinationName);
    }

    public void createActivity(final String activityName, final String description, final BigDecimal cost,
                               final int capacity, final String destinationName) {
        travelPackageService.createActivity(activityName, description, cost, capacity, destinationName);
    }

    public void createTravelPackage(final String travelPackageName, final int capacity, List<String> destinationNames) {
        travelPackageService.createTravelPackage(travelPackageName, capacity, destinationNames);
    }

    public TravelPackage getItinerary(final String travelPackageName) {
        return travelPackageService.getTravelPackageByName(travelPackageName);
    }

    public TravelPackage getPackageDetails(final String travelPackageName) {
        return travelPackageService.getTravelPackageByName(travelPackageName);

    }
}
