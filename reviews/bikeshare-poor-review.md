Files read: bikeshare-poor/src/main/java/bikeshare/Main.java

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
|---:|---|---|---|---|---|
| 1 | Check 1 | Main.java:16 | The public `stations` field is never read. | Should fix | Remove the unused field. |
| 2 | Check 1 | Main.java:114 | `memberType` is passed to `calculateFee` but is never used in the method body; the boolean controls the rate instead. | Should fix | Remove the unused parameter or calculate the rate directly from the member type. |
| 3 | Check 1 | Main.java:20 | `main` initializes state, prints the menu, reads input, detects quit, and dispatches commands, making it procedural and difficult to read. | Should fix | Move the command loop and command dispatch into separate methods or classes. |
| 4 | Check 1 | Main.java:85 | `returnBike` validates input, changes inventory, calculates the fee, persists the ride, and prints the receipt in one long method. | Should fix | Extract validation, fee calculation, persistence, and presentation into focused operations. |
| 5 | Check 1 | Main.java:15, 81, 109 | Active ride data is stored in a positional `String[]`, so invalid indexes or inconsistent field order can corrupt ride data. | Should fix | Replace the array with a named ride type or separate fields. |
| 6 | Check 2 | Main.java:20 | `main` has procedural cohesion because its unrelated steps are connected mainly by execution order. | Should fix | Delegate the input loop and application setup to focused collaborators. |
| 7 | Check 2 | Main.java:51 | `handle` has procedural cohesion because it parses a command, chooses an operation, and invokes another operation in sequence. | Note | Separate parsing from command execution. |
| 8 | Check 2 | Main.java:58 | `rent` has procedural cohesion because it validates station and type, changes inventory, updates rides, and prints output. | Should fix | Separate validation, rental state changes, and output. |
| 9 | Check 2 | Main.java:85 | `returnBike` has procedural cohesion because it validates, computes, mutates state, persists, and prints. | Should fix | Split return processing from persistence and presentation. |
| 10 | Check 2 | Main.java:13 | `Main` represents the menu, fee rules, bike inventory, active rides, and CSV persistence, so the class has weak cohesion. | Must fix | Separate the inventory, fee, persistence, and command-line responsibilities. |
| 11 | Check 2 | Main.java:14-16 | `bikes`, `activeRides`, and `stations` are public fields, exposing class state directly. | Must fix | Make the fields private and expose only narrow operations. |
| 12 | Check 2 | Main.java:114 | The boolean `useStaffRate` is control coupling because callers must know what `true` means. | Should fix | Pass a member-type abstraction or provide a named fee operation. |
| 13 | Check 2 | Main.java:81, 109 | The `String[]` ride representation creates position connascence: both methods must agree on the meanings of indexes 0, 1, and 2. | Should fix | Use a named object with accessors instead of positional indexes. |
| 14 | Check 2 | Main.java:14-15 | The public mutable maps create common coupling because every operation can depend on and modify shared global state. | Must fix | Encapsulate the maps inside a service object. |

Check 1 findings: 5.
Check 2 findings: 9.
