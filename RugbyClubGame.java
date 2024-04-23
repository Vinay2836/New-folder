import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Comparator;

class Player {
    private String name;
    private String position;
    private Coach coach;
    private Team team;

    public Player(String name, String position, Coach coach, Team team) {
        this.name = name;
        this.position = position;
        this.coach = coach;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public Coach getCoach() {
        return coach;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return name + " (" + position + "), Coach: " + coach.getName() + ", Team: " + team.getName();
    }
}

class Coach {
    private String name;
    private String type;

    public Coach(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}

class Team {
    private String name;
    private List<Player> players;

    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return name + " Team";
    }
}

enum MenuOption {
    ADD_PLAYER("Add Player"),
    GENERATE_RANDOM_PLAYER("Generate Random Player"),
    READ_SORT_DISPLAY("Read, Sort, and Display Records"),
    SEARCH_BY_NAME("Search for People by Name"),
    EXIT("Exit");

    private final String description;

    MenuOption(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public static MenuOption fromInt(int optionNumber) {
        return MenuOption.values()[optionNumber - 1];
    }
}

public class RugbyClubGame {
    private List<Player> players;
    private List<Coach> coaches;
    private List<Team> teams;

    public RugbyClubGame() {
        players = new ArrayList<>();
        coaches = new ArrayList<>();
        teams = new ArrayList<>();
    }

    // Read the file and initialize data
    private void readFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Skip header line if any
            // br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                // Assuming each line is a comma-separated value (CSV) of the format:
                // PlayerName,Position,CoachName,CoachType,TeamName
                String[] data = line.split(",");
                if (data.length == 5) {
                    // Extract data from the line
                    String playerName = data[0];
                    String position = data[1];
                    String coachName = data[2];
                    String coachType = data[3];
                    String teamName = data[4];

                    // Find or create coach and team
                    Coach coach = findOrCreateCoach(coachName, coachType);
                    Team team = findOrCreateTeam(teamName);

                    // Create a player object and add it to the team and player list
                    Player player = new Player(playerName, position, coach, team);
                    team.addPlayer(player);
                    players.add(player);
                }
            }
            System.out.println("File read successfully.");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    // Define the readSortDisplay method here
    private void readSortDisplay() {
        // Create a list to store names from the file
        List<String> records = new ArrayList<>();
        // Read the file and store names
        try (BufferedReader br = new BufferedReader(new FileReader("Club_Form.txt"))) {
            // Skip header line if any
            // br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                // Assuming each line contains a name
                records.add(line.trim());
            }
            System.out.println("File read successfully.");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        // Sort the list of names alphabetically
        Comparator<String> nameComparator = (record1, record2) -> {
            // Parse the first record
            String[] data1 = record1.split(",");
            String firstName1 = data1[1];
            String lastName1 = data1[2];
            String fullName1 = firstName1 + " " + lastName1;

            // Parse the second record
            String[] data2 = record2.split(",");
            String firstName2 = data2[1];
            String lastName2 = data2[2];
            String fullName2 = firstName2 + " " + lastName2;
            // Compare the full names
            return fullName1.compareToIgnoreCase(fullName2);
        };

        // Sort the list of records by full name using the defined comparator
        Collections.sort(records, nameComparator);



        // Display the first 20 records of the sorted list
        System.out.println("First 20 records:");
        for (int i = 0; i < Math.min(20, records.size()); i++) {
            System.out.println((i + 1) + ". " + records.get(i));
        }
    
    }

    // Add new player functionality
    private void addNewPlayer(Scanner scanner) {
        scanner.nextLine(); // Consume newline left over
        System.out.print("Please input the Player Name: ");
        String playerName = scanner.nextLine();

        System.out.println("Please select from the following Coach Staff:");
        System.out.println("Head Coach (1)");
        System.out.println("Assistant Coach (2)");
        System.out.println("Scrum Coach (3)");
        System.out.print("Enter your choice: ");
        int coachChoice = scanner.nextInt();
        String coachType;
        switch (coachChoice) {
            case 1:
                coachType = "Head Coach";
                break;
            case 2:
                coachType = "Assistant Coach";
                break;
            case 3:
                coachType = "Scrum Coach";
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Head Coach.");
                coachType = "Head Coach";
        }

        System.out.println("Please select the Teams:");
        System.out.println("A Squad (1)");
        System.out.println("B Squad (2)");
        System.out.println("Under-13 Squad (3)");
        System.out.print("Enter your choice: ");
        int teamChoice = scanner.nextInt();
        String teamName;
        switch (teamChoice) {
            case 1:
                teamName = "A Squad";
                break;
            case 2:
                teamName = "B Squad";
                break;
            case 3:
                teamName = "Under-13 Squad";
                break;
            default:
                System.out.println("Invalid choice. Defaulting to A Squad.");
                teamName = "A Squad";
        }

        // Create coach and team if not exist
        Coach coach = findOrCreateCoach("Coach", coachType);
        Team team = findOrCreateTeam(teamName);

        Player newPlayer = new Player(playerName, "Player Position", coach, team);
        players.add(newPlayer);
        team.addPlayer(newPlayer);

        System.out.printf("\n\"%s\" has been added as \"%s\" to \"%s\" successfully!%n", playerName, coachType, teamName);
    }

    // Generate random player functionality
    private void generateRandomPlayer() {
        Random random = new Random();
        String[] firstNames = {"John", "David", "Michael", "Chris", "Daniel", "Paul", "Sarah", "Jessica", "Emily", "Samantha"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Davis", "Miller", "Wilson", "Moore", "Taylor"};
        String[] positions = {"Forward", "Back"};
        String[] coachTypes = {"Head Coach", "Assistant Coach", "Scrum Coach"};
        String[] teamNames = {"A Squad", "B Squad", "Under-13 Squad"};

        // Generate random data
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        String playerName = firstName + " " + lastName;
        String position = positions[random.nextInt(positions.length)];
        String coachType = coachTypes[random.nextInt(coachTypes.length)];
        String teamName = teamNames[random.nextInt(teamNames.length)];

        Coach coach = findOrCreateCoach("Coach", coachType);
        Team team = findOrCreateTeam(teamName);

        Player randomPlayer = new Player(playerName, position, coach, team);
        players.add(randomPlayer);
        team.addPlayer(randomPlayer);

        System.out.printf("\n\"%s\" has been added as \"%s\" to \"%s\" successfully!%n", playerName, coachType, teamName);
    }

    // Define the searchByName method
    private void searchByName(Scanner scanner) {
        scanner.nextLine(); // Consume newline left over
        System.out.print("Enter the name to search for: ");
        String searchName = scanner.nextLine();

        boolean found = false;
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(searchName)) {
                System.out.printf("Name: %s - Coach: %s - Team: %s%n", player.getName(), player.getCoach().getType(), player.getTeam().getName());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No players found with the name '" + searchName + "'.");
        }
    }

    // Find or create a coach
    private Coach findOrCreateCoach(String coachName, String coachType) {
        for (Coach coach : coaches) {
            if (coach.getName().equals(coachName) && coach.getType().equals(coachType)) {
                return coach;
            }
        }
        Coach newCoach = new Coach(coachName, coachType);
        coaches.add(newCoach);
        return newCoach;
    }

    // Find or create a team
    private Team findOrCreateTeam(String teamName) {
        for (Team team : teams) {
            if (team.getName().equals(teamName)) {
                return team;
            }
        }
        Team newTeam = new Team(teamName);
        teams.add(newTeam);
        return newTeam;
    }

    public static void main(String[] args) {
        RugbyClubGame game = new RugbyClubGame();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the filename to read: ");
        String filename = scanner.nextLine();

        // Call the readFile method with the provided filename
        game.readFile(filename);

        while (true) {
            // Display options for the user
            System.out.println("\nPlease select an option from the following:");
            for (MenuOption option : MenuOption.values()) {
                System.out.println((option.ordinal() + 1) + ". " + option);
            }

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            MenuOption selectedOption = MenuOption.fromInt(choice);

            switch (selectedOption) {
                case ADD_PLAYER:
                    game.addNewPlayer(scanner);
                    break;
                case GENERATE_RANDOM_PLAYER:
                    game.generateRandomPlayer();
                    break;
                case READ_SORT_DISPLAY:
                    game.readSortDisplay();
                    break;
                case SEARCH_BY_NAME:
                    game.searchByName(scanner);
                    break;
                case EXIT:
                    System.out.println("Exiting program.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
