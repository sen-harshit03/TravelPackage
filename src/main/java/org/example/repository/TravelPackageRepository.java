package org.example.repository;

import org.example.entities.Activity;
import org.example.entities.Destination;
import org.example.entities.TravelPackage;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TravelPackageRepository {

    private final Map<String, TravelPackage> travelPackageMap;
    private final Map<String, Destination> destinationMap;
    private final Map<String, Activity> activityMap;

    public TravelPackageRepository() {
        travelPackageMap = new HashMap<>();
        destinationMap = new HashMap<>();
        activityMap = new HashMap<>();
    }

    public void saveTravelPackage(final TravelPackage travelPackage) {
        final String key = travelPackage.getName().toLowerCase();
        travelPackageMap.put(key, travelPackage);
    }

    public void saveDestination(final Destination destination) {
        final String key = destination.getName().toLowerCase();
        destinationMap.put(key, destination);
    }

    public void saveActivity(final Activity activity) {
        final String key = activity.getName().toLowerCase();
        activityMap.put(key, activity);
    }

    public Optional<List<Destination>> findDestinationsByName(final List<String> destinationNames) {
        final List<Destination> destinationList = destinationNames.stream()
                .map(String::toLowerCase)
                .map(destinationMap::get)
                .filter(Objects::nonNull).toList();

        if (destinationList.size() == destinationNames.size()) {
            return Optional.of(destinationList);
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<TravelPackage>> findAllTravelPackage() {
        final List<TravelPackage> travelPackageList = travelPackageMap.values()
                .stream()
                .toList();

        return Optional.of(travelPackageList);
    }

    public Optional<TravelPackage> findTravelPackageByName(final String name) {
        return Optional.of(travelPackageMap.get(name.toLowerCase()));
    }

    public Optional<Activity> findActivityByName(final String activityName) {
        return Optional.of(activityMap.get(activityName.toLowerCase()));
    }
}
