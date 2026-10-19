package bikeshare;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvRideRepository implements RideRepository {
    private final String fileName;

    public CsvRideRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void add(Bike bike, Ride ride, String endStation, int minutes, int fee) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(String.join(",", bike.id(), ride.memberId(), ride.memberType().name(),
                    ride.startStation(), endStation, String.valueOf(minutes), String.valueOf(fee)));
            writer.newLine();
        } catch (IOException exception) {
            throw new IllegalStateException("Could not save ride", exception);
        }
    }
}
