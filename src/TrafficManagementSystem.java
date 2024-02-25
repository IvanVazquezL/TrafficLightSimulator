import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrafficManagementSystem {
    private final String mainMenu = """
            Menu:
            1. Add road
            2. Delete road
            3. Open system
            0. Quit
            """;
    private final Scanner scanner = new Scanner(System.in);
    TrafficManagementSystem() {}

    public void startSystem() {
        System.out.println("Welcome to the traffic management system!\n");

        System.out.println("Input the number of roads:");
        String numberOfRoadsString;
        do {
            numberOfRoadsString = scanner.nextLine();

            if (!isPositiveNumber(numberOfRoadsString)) {
                System.out.println("Error! Incorrect Input. Try again:");
            }
        } while(!isPositiveNumber(numberOfRoadsString));

        System.out.println("Input the interval:");
        String numberOfIntervalsString;
        do {
            numberOfIntervalsString = scanner.nextLine();

            if (!isPositiveNumber(numberOfIntervalsString)) {
                System.out.println("Error! Incorrect Input. Try again:");
            }
        } while(!isPositiveNumber(numberOfIntervalsString));

        int numberOfIntervals = Integer.parseInt(numberOfIntervalsString);

        int numberOfRoads = Integer.parseInt(numberOfRoadsString);
        SystemThread systemThread = new SystemThread(
"QueueThread",
            numberOfRoads,
            numberOfIntervals
        );
        systemThread.start();
        mainMenu:
        do {
            System.out.println(mainMenu);
            String option = scanner.nextLine();

            if (!isInRange(option)) {
                System.out.println("Incorrect option");
                getAnyInput();
                continue;
            }

            switch(option) {
                case "1":
                    System.out.println("Input road name:");
                    String roadName = scanner.nextLine();
                    systemThread.addRoad(roadName);
                    break;
                case "2":
                    systemThread.deleteRoad();
                    break;
                case "3":
                    System.out.println("System opened");
                    systemThread.setSystemOpen(true);
                    break;
                case "0":
                    System.out.println("Bye!");
                    break mainMenu;
                default:
                    break;
            }

            getAnyInput();
            systemThread.setSystemOpen(false);
        } while(true);
        systemThread.setRunning(false);
    }

    public void getAnyInput() {
        String input = scanner.nextLine();
    }

    public boolean isPositiveNumber(String str) {
        // Check if the string is not null or empty
        if (str == null || str.isEmpty()) {
            return false;
        }

        // Use regular expression to check if the string consists of digits only
        // and if it represents a positive number
        return str.matches("\\d+") && !str.startsWith("0");
    }

    public boolean isInRange(String str) {
        // Define the regex pattern to match integers between 0 and 3
        String regex = "[0-3]";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Match the input string against the pattern
        Matcher matcher = pattern.matcher(str);

        // Check if the input string matches the pattern
        return matcher.matches();
    }

}
