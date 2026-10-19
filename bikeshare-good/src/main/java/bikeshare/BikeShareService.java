package bikeshare;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class BikeShareService {
    private final Map<String, ArrayDeque<Bike>> availableBikes = new HashMap<>();
    private final Map<String, Ride> activeRides = new HashMap<>();
    private final RideRepository rideRepository;

    public BikeShareService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
        addStation("Library", "L1", "L2");
        addStation("Canteen", "C1", "C2");
        addStation("Dorm", "D1", "D2");
    }

    public Bike rent(String memberId, MemberType memberType, String station) {
        ArrayDeque<Bike> bikes = stationBikes(station);
        Bike bike = bikes.poll();
        if (bike == null) {
            throw new IllegalStateException("No bikes available at " + station);
        }
        activeRides.put(bike.id(), new Ride(memberId, memberType, station));
        return bike;
    }

    public Receipt returnBike(String bikeId, String station, int minutes) {
        ArrayDeque<Bike> bikes = stationBikes(station);
        Ride ride = activeRides.remove(bikeId);
        if (ride == null) {
            throw new IllegalStateException("Bike is not currently rented");
        }
        if (minutes < 0) {
            activeRides.put(bikeId, ride);
            throw new IllegalArgumentException("Minutes cannot be negative");
        }
        Bike bike = new Bike(bikeId);
        int fee = ride.memberType().feeFor(minutes);
        bikes.offer(bike);
        rideRepository.add(bike, ride, station, minutes, fee);
        return new Receipt(bikeId, minutes, fee);
    }

    private ArrayDeque<Bike> stationBikes(String station) {
        ArrayDeque<Bike> bikes = availableBikes.get(station);
        if (bikes == null) {
            throw new IllegalArgumentException("Unknown station");
        }
        return bikes;
    }

    private void addStation(String station, String firstBike, String secondBike) {
        ArrayDeque<Bike> bikes = new ArrayDeque<>();
        bikes.offer(new Bike(firstBike));
        bikes.offer(new Bike(secondBike));
        availableBikes.put(station, bikes);
    }
}
