package bikeshare;

public interface RideRepository {
    void add(Bike bike, Ride ride, String endStation, int minutes, int fee);
}
