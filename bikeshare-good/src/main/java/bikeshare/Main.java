package bikeshare;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        BikeShareService service = new BikeShareService(new CsvRideRepository("rides.csv"));
        System.out.println("BikeShare. Commands: rent <memberId> <student|staff> <station>, return <bikeId> <station> <minutes>, quit");
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine().trim();
                if (command.equalsIgnoreCase("quit")) {
                    return;
                }
                if (!command.isEmpty()) {
                    execute(service, command);
                }
            }
        }
    }

    private void execute(BikeShareService service, String command) {
        String[] parts = command.split("\\s+");
        try {
            if (parts.length == 4 && parts[0].equalsIgnoreCase("rent")) {
                Bike bike = service.rent(parts[1], MemberType.valueOf(parts[2].toUpperCase()), parts[3]);
                System.out.println("Rented " + bike.id() + " to " + parts[1] + ".");
            } else if (parts.length == 4 && parts[0].equalsIgnoreCase("return")) {
                Receipt receipt = service.returnBike(parts[1], parts[2], Integer.parseInt(parts[3]));
                System.out.println("Receipt: bike=" + receipt.bikeId() + ", minutes=" + receipt.minutes()
                        + ", fee=" + receipt.fee() + " baht");
            } else {
                System.out.println("Invalid command.");
            }
        } catch (IllegalArgumentException | IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
