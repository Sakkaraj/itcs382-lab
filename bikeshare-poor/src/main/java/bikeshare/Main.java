package bikeshare;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static final Map<String, ArrayDeque<String>> bikes = new HashMap<>();
    public static final Map<String, String[]> activeRides = new HashMap<>();
    public static final String[] stations = {"Library", "Canteen", "Dorm"};
    public static final int STUDENT = 1;
    public static final int STAFF = 2;

    public static void main(String[] args) {
        setup();
        System.out.println("BikeShare. Commands: rent <memberId> <type 1=student 2=staff> <station>, return <bikeId> <station> <minutes>, quit");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("> ");
                if (!scanner.hasNextLine()) {
                    break;
                }
                String line = scanner.nextLine().trim();
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
                if (!line.isEmpty()) {
                    handle(line);
                }
            }
        }
    }

    public static void setup() {
        bikes.clear();
        activeRides.clear();
        bikes.put("Library", new ArrayDeque<>(List.of("L1", "L2")));
        bikes.put("Canteen", new ArrayDeque<>(List.of("C1", "C2")));
        bikes.put("Dorm", new ArrayDeque<>(List.of("D1", "D2")));
    }

    public static void handle(String line) {
        String[] parts = line.split("\\s+");
        if (parts[0].equalsIgnoreCase("rent") && parts.length == 4) {
            rent(parts[1], parts[2], parts[3]);
        } else if (parts[0].equalsIgnoreCase("return") && parts.length == 4) {
            returnBike(parts[1], parts[2], parts[3]);
        } else {
            System.out.println("Invalid command.");
        }
    }

    public static void rent(String memberId, String typeText, String station) {
        if (!bikes.containsKey(station)) {
            System.out.println("Unknown station.");
            return;
        }
        int memberType;
        try {
            memberType = Integer.parseInt(typeText);
        } catch (NumberFormatException exception) {
            System.out.println("Member type must be 1 or 2.");
            return;
        }
        if (memberType != STUDENT && memberType != STAFF) {
            System.out.println("Member type must be 1 or 2.");
            return;
        }
        String bikeId = bikes.get(station).poll();
        if (bikeId == null) {
            System.out.println("No bikes available at " + station + ".");
            return;
        }
        activeRides.put(bikeId, new String[]{memberId, typeText, station});
        System.out.println("Rented " + bikeId + " to " + memberId + ".");
    }

    public static void returnBike(String bikeId, String station, String minutesText) {
        if (!bikes.containsKey(station)) {
            System.out.println("Unknown station.");
            return;
        }
        String[] ride = activeRides.remove(bikeId);
        if (ride == null) {
            System.out.println("Bike is not currently rented.");
            return;
        }
        int minutes;
        try {
            minutes = Integer.parseInt(minutesText);
        } catch (NumberFormatException exception) {
            activeRides.put(bikeId, ride);
            System.out.println("Minutes must be a number.");
            return;
        }
        if (minutes < 0) {
            activeRides.put(bikeId, ride);
            System.out.println("Minutes cannot be negative.");
            return;
        }
        int memberType = Integer.parseInt(ride[1]);
        int fee = calculateFee(memberType, minutes, memberType == STAFF);
        bikes.get(station).offer(bikeId);
        saveRide(bikeId, ride[0], ride[1], ride[2], station, minutes, fee);
        System.out.println("Receipt: bike=" + bikeId + ", minutes=" + minutes + ", fee=" + fee + " baht");
    }

    public static int calculateFee(int memberType, int minutes, boolean useStaffRate) {
        int rate = useStaffRate ? 3 : 2;
        return rate * minutes;
    }

    public static void saveRide(String bikeId, String memberId, String type, String from, String to, int minutes, int fee) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("rides.csv", true))) {
            writer.write(String.join(",", Arrays.asList(bikeId, memberId, type, from, to, String.valueOf(minutes), String.valueOf(fee))));
            writer.newLine();
        } catch (IOException exception) {
            System.out.println("Could not save ride: " + exception.getMessage());
        }
    }
}
