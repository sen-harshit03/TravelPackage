package org.example;

import org.example.config.AppConfig;
import org.example.constants.PassengerType;
import org.example.controller.PassengerController;
import org.example.controller.TravelPlannerController;
import org.example.entities.Activity;
import org.example.entities.Destination;
import org.example.entities.Passenger;
import org.example.entities.TravelPackage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        final TravelPlannerController travelPlannerController = context.getBean(TravelPlannerController.class);
        final PassengerController passengerController = context.getBean(PassengerController.class);

        /**
         * Creating dummy data
         */
        createDestination(travelPlannerController);
        createActivity(travelPlannerController);
        createTravelPackage(travelPlannerController);

        createPassenger(passengerController);

        registerForPackage(passengerController);

        signUpForActivity(passengerController);

//        printItinerary(travelPlannerController);
//        printPackageDetails(travelPlannerController);
        printPassengerDetails(passengerController);
    }

    private static void createDestination(final TravelPlannerController travelPlannerController) {
        travelPlannerController.createDestination("Delhi");
    }

    private static void createActivity(final TravelPlannerController travelPlannerController) {
        travelPlannerController.createActivity("Bungee Jumping", "1 hour halt", BigDecimal.valueOf(1000), 2, "Delhi");
        travelPlannerController.createActivity("Wall Climbing", "1 hour halt", BigDecimal.valueOf(500), 10, "Delhi");

    }
    private static void createTravelPackage(final TravelPlannerController travelPlannerController) {
        travelPlannerController.createTravelPackage("Yolo Hikers", 15, List.of("Delhi"));
    }

    private static void createPassenger(final PassengerController passengerController) {
        passengerController.createPassenger("Rohan", "999999", BigDecimal.valueOf(3000), PassengerType.PREMIUM);
        passengerController.createPassenger("Akash", "888888", BigDecimal.valueOf(3000), PassengerType.GOLD);
        passengerController.createPassenger("Abhi", "77777", BigDecimal.valueOf(3000), PassengerType.GOLD);
    }

    private static void registerForPackage(final PassengerController passengerController) {
        passengerController.registerForPackage("Rohan", "Yolo Hikers");
        passengerController.registerForPackage("Akash", "Yolo Hikers");
        passengerController.registerForPackage("Abhi", "Yolo Hikers");
    }

    private static void signUpForActivity(final PassengerController passengerController) {
        passengerController.signUpForActivity("Rohan", "Bungee Jumping");
        passengerController.signUpForActivity("Akash", "Bungee Jumping");
        passengerController.signUpForActivity("Abhi", "Bungee Jumping");
        passengerController.signUpForActivity("Abhi", "Wall climbing");
    }

    private static void printItinerary(final TravelPlannerController travelPlannerController) {

        final TravelPackage travelPackage = travelPlannerController.getItinerary("Yolo Hikers");

        System.out.println("Printing itinerary.......");

        System.out.println("Travel package name: " + travelPackage.getName());
        System.out.println("===========================");
        final List<Destination> destinationList = travelPackage.getDestinationList();

        destinationList.forEach((destination) -> {
            System.out.println("Destination:" + destination.getName());
            final List<Activity> activityList = destination.getActivityList();
            activityList
                    .forEach((activity) -> {
                        System.out.println("Activity name: " + activity.getName());
                        System.out.println("Activity description: " + activity.getDescription());
                        System.out.println("Activity cost: " + activity.getCost());
                        System.out.println("Activity capacity: " + activity.getCapacity());
                        System.out.println("Activity current passenger count: " + activity.getPassengerList().size());
                        System.out.println("===========================");

                    });
        });
    }

    private static void printPackageDetails(final TravelPlannerController travelPlannerController) {

        final TravelPackage travelPackage = travelPlannerController.getPackageDetails("Yolo Hikers");
        System.out.println("Printing package details.......");

        System.out.println("Package name: " + travelPackage.getName());
        System.out.println("Passenger Capacity: " + travelPackage.getPassengerCapacity());
        System.out.println("Number of passenger enrolled: " + travelPackage.getPassengerList().size());
        System.out.print("Passenger names: " );
        travelPackage.getPassengerList()
                .forEach(passenger -> {
                    System.out.print(passenger.getName() + " ");
                });
        System.out.println();
        System.out.println("========================");
    }

    private static void printPassengerDetails(final PassengerController passengerController) {
        final List<Passenger> passengerList = passengerController.getPassengerDetails();
        System.out.println("Printing passenger details.......");
        passengerList.forEach(passenger -> {
            System.out.println("Passenger name: " + passenger.getName());
            System.out.println("Passenger number: " + passenger.getPassengerNumber());
            System.out.println("Passenger balance: " + passenger.getBalance());
            System.out.println("Passenger Type: " + passenger.getPassengerType());
            System.out.println("Travel package name: " + passenger.getTravelPackage().getName());
            System.out.println("Activities enrolled: ");
            passenger.getActivities()
                    .forEach(activity -> {
                        System.out.println("Activity name: " + activity.getName());
                        BigDecimal pricePaid = getPricePaid(passenger, activity);
                        System.out.println("Price paid: " + pricePaid);
                        System.out.println("Destination of activity: " + activity.getDestination().getName());
                        System.out.println("===========================");
                    });
        });
    }

    private static BigDecimal getPricePaid(final Passenger passenger, final Activity activity) {
        if(PassengerType.PREMIUM == passenger.getPassengerType()) {
            return BigDecimal.valueOf(0);
        } else if(PassengerType.GOLD == passenger.getPassengerType()) {
            return activity.getCost().multiply(BigDecimal.valueOf(0.90));
        } else {
            return activity.getCost();
        }
    }


}
