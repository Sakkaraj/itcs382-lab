Files read: bikeshare-good/src/main/java/bikeshare/Main.java, bikeshare-good/src/main/java/bikeshare/BikeShareService.java, bikeshare-good/src/main/java/bikeshare/Bike.java, bikeshare-good/src/main/java/bikeshare/Ride.java, bikeshare-good/src/main/java/bikeshare/Receipt.java, bikeshare-good/src/main/java/bikeshare/MemberType.java, bikeshare-good/src/main/java/bikeshare/RideRepository.java, bikeshare-good/src/main/java/bikeshare/CsvRideRepository.java

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
|---:|---|---|---|---|---|
| 1 | Check 1 | BikeShareService.java:21-38 | `returnBike` removes the active ride and adds the bike to inventory before repository persistence; if `rideRepository.add` fails, the in-memory state no longer represents an unfinished ride. | Should fix | Persist successfully before finalizing the in-memory state, or restore the state when persistence fails. |
| 2 | Check 2 | Main.java:9 | `run` has procedural cohesion because setup, input reading, quit handling, and command dispatch are connected by execution order. | Note | Keep the CLI loop small and delegate command parsing to a dedicated component if it grows. |
| 3 | Check 2 | Main.java:22 | `execute` has procedural cohesion because it parses tokens, selects a command, invokes the service, and formats output. | Note | Separate command parsing from service invocation and presentation if more commands are added. |

Check 1 findings: 1.
Check 2 findings: 2.
