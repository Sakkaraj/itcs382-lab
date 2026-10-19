# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/Sakkaraj/itcs382-lab

## Tools

| Item | Version or name |
| --- | --- |
| JDK | 27 |
| Maven | 27.0.1 |
| IDE | VSCode |
| SonarQube for IDE | 6.1.1 |
| AI tool and exact model | GitHub Copilot (with GPT-5.6 Luna) |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change:

`1e1929a`

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S106 | Main.java: [Ln 9, Col 8] | Replace this use of System.out by a logger |
| 2 | java:S106 | Main.java: [Ln 15, Col 16] | Replace this use of System.out by a logger |
| 3 | java:S106 | Main.java: [Ln 17, Col 16] | Replace this use of System.out by a logger |
| 4 | java:S106 | Main.java: [Ln 22, Col 16] | Replace this use of System.out by a logger |
| 5 | java:S2143 | ParcelService.java: [Ln 1, Col 0] | Use the "java.time" API for date and time |
| 6 | java:S1128 | ParcelService.java: [Ln 6, Col 7] | Remove this unused import 'java.util.Date' |
| 7 | java:S1068 | ParcelService.java: [Ln 12, Col 16] | Remove this unused "maxParcels" private field |
| 8 | java:S106 | ParcelService.java: [Ln 30, Col 16] | Replace this use of System.out by a logger |
| 9 | java:S4973 | ParcelService.java: [Ln 42, Col 51] | Strings and Boxed types should be compared using "equals()" |
| 10 | java:S106 | ParcelService.java: [Ln 67, Col 16] | Replace this use of System.out by a logger |
| 11 | java:S106 | ParcelService.java: [Ln 69, Col 19] | Replace this use of System.out by a logger |
| 12 | java:S2095 | ParcelService.java: [Ln 76, Col 32] | Use try-with-resources or close this "FileWriter" in a "finally clause" |
| 13 | java:S108 | ParcelService.java: [Ln 80, Col 32] | Remove this block of code, fill it in, or add a comment explaining why it is empty |


## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 19/10/2026 |
| AI tool and exact model | GPT-5.6 Luna |
| Commit reviewed | Starter code reviewed |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | Attatched files, attatched folder |
| Output file in the reviews folder | [parcelpoint-review.md](reviews/parcelpoint-review.md) |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | Main.java:9, 15, 17, 22; ParcelService.java:30, 67, 69 | `System.out` should be replaced by a logger | SonarQube | java:S106 | Yes; these are direct console-output calls. For this command-line program, keeping them could be justified because console output is part of the required user interface. |
| 2 | ParcelService.java:1 | Use the `java.time` API instead of `java.util.Date` | SonarQube | java:S2143 | Yes; `java.util.Date` is a legacy date/time API. |
| 3 | ParcelService.java:6 | Unused `java.util.Date` import | SonarQube and AI | java:S1128; code quality | Yes; the import is not used and can be removed. |
| 4 | ParcelService.java:12 | Unused `maxParcels` field | SonarQube and AI | java:S1068; code quality | Yes; the field is never read, so it does not enforce a parcel limit. |
| 5 | ParcelService.java:42 | Parcel codes are compared with `==` | SonarQube and AI | java:S4973; code quality | Yes; `==` compares String references rather than their contents, so pickup can fail for equal text in different objects. |
| 6 | ParcelService.java:76 | `FileWriter` is not closed | SonarQube and AI | java:S2095; code quality | Yes; the writer can leak resources and buffered data may not be flushed reliably. |
| 7 | ParcelService.java:80 | Empty `catch` block hides persistence failures | SonarQube and AI | java:S108; code quality | Yes; the empty block silently discards the exception. |
| 8 | Main.java:8 | `Scanner` is never closed | AI | code quality | Yes; the scanner owns an input resource, although closing it would also close `System.in`. |
| 9 | Main.java:10 | Input is parsed without validation | AI | code quality | Yes; blank, unknown, incomplete, or non-numeric commands can cause incorrect behavior or exceptions. |
| 10 | ParcelService.java:39 | Pickup returns `0` for both success and not-found cases | AI | code quality | Yes; callers cannot distinguish a zero-fee pickup from a missing parcel. |
| 11 | ParcelService.java:22, 59 | Repeated `STORED` status literal | AI | code quality | Yes; repeating a status string risks inconsistent spelling and should be replaced with a constant or enum. |
| 12 | Main.java:6 | `main` handles input, parsing, dispatch, service calls, and output | AI | code quality | Yes; these responsibilities make the method harder to read and extend. |
| 13 | Main.java:6 | `main` has procedural cohesion | AI | cohesion | Yes; its operations are connected mainly by the required execution order. |
| 14 | ParcelService.java:18 | `storeParcel` combines locker allocation, state changes, notification, persistence, and result handling | AI | cohesion | Yes; the method performs several different responsibilities in sequence. |
| 15 | ParcelService.java:31 | `pickUp` combines searching, state changes, persistence, and fee calculation | AI | cohesion | Yes; the method combines multiple responsibilities that can change independently. |
| 16 | ParcelService.java:52 | `printParcels` uses one boolean to select two output formats | AI | cohesion | Yes; the boolean makes one method serve two distinct output purposes. |
| 17 | ParcelService.java:7 | `ParcelService` handles locker management, parcel state, fees, output, notification, and file persistence | AI | class cohesion | Yes; the class has several unrelated responsibilities. |
| 18 | Main.java:5 | `Main` has weak class cohesion because it has no attributes and coordinates external objects | AI | class cohesion | No; having no attributes does not by itself prove weak cohesion, although the method's responsibilities can reasonably be separated. |
| 19 | Main.java:26, 28; ParcelService.java:52 | `forStaff` controls behavior through a boolean parameter | AI | coupling | Yes; callers must know what `true` and `false` mean. |
| 20 | ParcelService.java:22, 37; Parcel.java:8 | `ParcelService` directly reads and writes `Parcel.status` | AI | coupling | Yes; the service depends on the parcel's field representation instead of a controlled API. |
| 21 | ParcelService.java:15; Main.java:20 | `getLockers` exposes a collection and `Main` reaches through it | AI | coupling; encapsulation | Yes; callers can depend on the collection and locker internals instead of a service operation. |
| 22 | Main.java:7, 11, 12, 20 | `Main` depends directly on several domain classes | AI | coupling | Yes; command-line code is coupled to `ParcelService`, `Parcel`, and `Locker` details. |
| 23 | ParcelService.java:3, 7, 73 | `ParcelService` depends on management, output, notification, and file I/O concerns | AI | coupling | Yes; these dependencies make changes to one concern affect the service. |
| 24 | ParcelService.java:22, 59 | Status names are shared by convention between storage and display logic | AI | connascence | Yes; both locations must agree on the same string value. |
| 25 | Main.java:26, 28; ParcelService.java:52 | The meaning of `true` and `false` is shared by convention | AI | connascence | Yes; the boolean value has meaning only through its position and undocumented convention. |
| 26 | Locker.java:7, Parcel.java:7, ParcelService.java:44 | Numeric size values `1`, `2`, and `3` are shared by convention | AI | connascence | Yes; multiple classes and fee logic must interpret the same magic numbers identically. |
| 27 | Parcel.java:8 | `status` is not private | AI | encapsulation | Yes; external code can change parcel state without controlled transitions. |
| 28 | ParcelService.java:15 | `getLockers` returns the internal mutable collection | AI | encapsulation | Yes; callers can modify the service's internal locker list. |

2.1 Name two problems that only the AI review found.

The AI review alone found that command input is not validated in `Main.java`, and that `Parcel.status` is exposed instead of being encapsulated. It also identified design issues such as the boolean `forStaff` parameter and the mixed responsibilities in `ParcelService`.

2.2 Name one problem that only SonarQube found, or write none.

SonarQube alone found the `java.util.Date` usage and recommended the `java.time` API under rule `java:S2143`. The AI noted the unused import, but did not make this separate API recommendation.

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.

The finding that `Main` has weak class cohesion because it has no class attributes is too vague and should be marked no. A class can be cohesive without fields; the absence of attributes does not prove a cohesion problem. The more specific finding that `main` combines input, parsing, dispatch, service calls, and output is supported by the code.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | Kept | Required command-line output; `System.out` is the user interface. |
| 2 | Kept | Required command-line output; `System.out` is the user interface. |
| 3 | Kept | Required command-line output; `System.out` is the user interface. |
| 4 | Kept | Required command-line output; `System.out` is the user interface. |
| 5 | Fixed | `9242ca2` Remove legacy `Date` import; no date value is used by this program. |
| 6 | Fixed | `9242ca2` Remove legacy `Date` import. |
| 7 | Fixed | `4aa8a8c` Remove unused parcel limit field. |
| 8 | Kept | Required SMS output for the command-line program. |
| 9 | Fixed | `ed36a12` Compare parcel-code contents. |
| 10 | Kept | Required public command output. |
| 11 | Kept | Required staff command output. |
| 12 | Fixed | `4d11102` Close `FileWriter` with try-with-resources. |
| 13 | Fixed | `a4ad649` Log persistence failures instead of ignoring them. |

Number of SonarQube issues that remain after your fixes: 7 (`java:S106`, the intentional command-line output calls)

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| Encapsulation and coupling | Replaced `getLockers()` with `countFreeLockers()`, so `Main` no longer reaches into the service's mutable collection. | `2e87726` |
| Control coupling and cohesion | Replaced `printParcels(boolean)` with the named `printPublicParcels()` and `printStaffParcels()` operations. | `fd82af0` |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text
Commands: store <code> <studentId> <size 1-3>, pickup <code> <days>, list, staff, quit
SMS to 6501: parcel P1 is in locker 2
Locker 2
Fee: 30.0
P1 6501 PICKED_UP
Free lockers: 3

parcels.csv:
P1,PICKED_UP
```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | 19/10/2026 | 19/10/2026 |
| AI tool and exact model | GitHub Copilot (exact model name not exposed) | GitHub Copilot (exact model name not exposed) |
| Prompt (B or C as given, or your changed prompt in full) | Prompt B as given | Prompt C as given |
| Builds with mvn package (yes or no) | yes | yes |
| Number of classes, interfaces, enums and records | 1 class, 0 interfaces, 0 enums, 0 records | 3 classes, 1 interface, 1 enum, 3 records |
| SonarQube issues, total | 19 | 5 |
| SonarQube issues, by rule | java:S106: 14; java:S1104: 3; java:S135: 1; java:S1172: 1 | java:S106: 5 |
| AI review findings in Check 1 | 5 | 1 |
| AI review findings in Check 2 | 9 | 2 |
| Review output file in the reviews folder | [bikeshare-poor-review.md](reviews/bikeshare-poor-review.md) | [bikeshare-good-review.md](reviews/bikeshare-good-review.md) |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

No. The SonarQube findings were code-quality rules only: `java:S106` ("Replace this use of System.out by a logger"), `java:S1104` (make the public fields protected), `java:S135` (reduce the number of break and continue statements), and `java:S1172` (remove the unused `memberType` parameter). It did not report a cohesion or coupling rule.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

Yes. For example, the poor review found that `Main.java:14-15` exposes public mutable maps and creates common coupling; this finding does not appear for the good version because its state is private inside `BikeShareService`.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

I would extend bikeshare-good. The `MemberType` enum owns the fee rule and the service receives a typed member value, so adding a visitor rate is localized; the poor version would require coordinating integer codes, the boolean fee flag, and shared arrays and maps in `Main`.

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

5.2 When should you not trust the AI review? Give an example from this lab.

5.3 What will you do differently in Project Phase 2 after this lab?

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| GPT-5.6 Luna | Reviews the `parcelpoint` | Add `parcelpoint-review.md` to the `reviews` directory | Scans throughout the response |
| GPT-5.6 Luna | Generate and review the BikeShare poor and good designs for Task 4 | `bikeshare-poor`, `bikeshare-good`, `reviews/bikeshare-poor-review.md`, `reviews/bikeshare-good-review.md` | Ran `mvn package` and scripted rent/return/quit workflows for both projects; inspected every Java file and ran SonarQube analysis. |
