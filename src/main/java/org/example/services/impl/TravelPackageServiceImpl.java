package org.example.services.impl;

import org.example.entities.Activity;
import org.example.entities.Destination;
import org.example.entities.TravelPackage;
import org.example.repository.TravelPackageRepository;
import org.example.services.TravelPackageService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TravelPackageServiceImpl implements TravelPackageService {

    private final TravelPackageRepository repository;

    public TravelPackageServiceImpl(final TravelPackageRepository repository) {
        this.repository = repository;
    }

    public void createTravelPackage(final String name, final int capacity, final List<String> destinationNames) {
        final List<Destination> destinationList = getDestinationList(destinationNames);

        final TravelPackage travelPackage = TravelPackage.builder()
                .name(name)
                .passengerCapacity(capacity)
                .passengerList(new ArrayList<>())
                .destinationList(destinationList)
                .build();
        repository.saveTravelPackage(travelPackage);
    }

    public List<TravelPackage> getAllTravelPackage() {
        return repository.findAllTravelPackage().orElseThrow(
                () -> new RuntimeException("No travel package found")
        );
    }


    public void createDestination(final String destinationName) {
        final Optional<List<Destination>> optionalDestinations = repository.findDestinationsByName(List.of(destinationName));
        if(optionalDestinations.isPresent()) {
            throw new RuntimeException("Destination already exists");
        }
        final Destination destination = Destination
                .builder()
                .activityList(new ArrayList<>())
                .name(destinationName)
                .build();

        repository.saveDestination(destination);
    }


    public void createActivity(final String name, final String description, final BigDecimal cost,
                               final int capacity, final String destinationName) {

        final List<Destination> destinationList = getDestinationList(List.of(destinationName));
        final Destination destination = destinationList.get(0);

        final Activity activity = Activity
                .builder()
                .name(name)
                .description(description)
                .destination(destination)
                .cost(cost)
                .capacity(capacity)
                .passengerList(new ArrayList<>())
                .build();

        final List<Activity> activities = destination.getActivityList();
        activities.add(activity);
        repository.saveActivity(activity);
    }

    public Destination getDestinationByName(final String destinationNames) {
        final List<Destination> destinationList = getDestinationList(List.of(destinationNames));
        return destinationList.get(0);
    }


    private List<Destination> getDestinationList(final List<String> destinationNames) {
        return repository.findDestinationsByName(destinationNames).orElseThrow(
                () -> new RuntimeException("Destination not found")
        );
    }

    public TravelPackage getTravelPackageByName(final String travelPackageName) {
        final TravelPackage travelPackage = repository.findTravelPackageByName(travelPackageName).orElseThrow(
                () -> new RuntimeException("No travel package found for this name :" + travelPackageName)
        );

        return travelPackage;
    }

}
