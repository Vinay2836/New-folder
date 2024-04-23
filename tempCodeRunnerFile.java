import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;

    public Person(int id, String firstName, String lastName, String email, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return String.format("Person [ID: %d, First Name: %s, Last Name: %s, Email: %s, Gender: %s]", id, firstName, lastName, email, gender);
    }
}

enum MenuOption {
    VIEW_PEOPLE("View People"),
    SORT_PEOPLE("Sort People"),
    SEARCH_PEOPLE("Search People"),
    ADD_NEW_PERSON("Add New Person"),
    GENERATE_RANDOM_PEOPLE("Generate Random People"),
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

public class tempCodeRunnerFile {
    private List<Person> people;

    public tempCodeRunnerFile() {
        people = new ArrayList<>();
    }

    // Read the file and initialize data
    private void readFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Skip the header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Create a Person object and add it to the list
                int id = Integer.parseInt(data[0]);
                String firstName = data[1];
                String lastName = data[2];
                String email = data[3];
                String gender = data[4];
                Person person = new Person(id, firstName, lastName, email, gender);
                people.add(person);
            }
            System.out.println("File read successfully.");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    // Display the menu and handle user input
    private void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMenu:");
            for (MenuOption option : MenuOption.values()) {
                System.out.println((option.ordinal() + 1) + ". " + option);
            }

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            if (choice < 1 || choice > MenuOption.values().length) {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }

            MenuOption selectedOption = MenuOption.fromInt(choice);
            switch (selectedOption) {
                case VIEW_PEOPLE:
                    viewPeople();
                    break;
                case SORT_PEOPLE:
                    sortPeople();
                    break;
                case SEARCH_PEOPLE:
                    searchPeople(scanner);
                    break;
                case ADD_NEW_PERSON:
                    addNewPerson(scanner);
                    break;
                case GENERATE_RANDOM_PEOPLE:
                    generateRandomPeople();
                    break;
                case EXIT:
                    exit = true;
                    System.out.println("Exiting. Goodbye!");
                    break;
            }
        }
    }

    // View all people
    private void viewPeople() {
        System.out.println("\nPeople:");
        for (Person person : people) {
            System.out.println(person);
        }
    }

    // Sort people by first name
    private void sortPeople() {
        people.sort((p1, p2) -> p1.getFirstName().compareToIgnoreCase(p2.getFirstName()));
        System.out.println("\nPeople sorted by first name:");
        for (Person person : people) {
            System.out.println(person);
        }
    }

    // Search people by first name or last name
    private void searchPeople(Scanner scanner) {
        scanner.nextLine(); // Consume newline left over
        System.out.print("Enter the first name or last name to search: ");
        String searchName = scanner.nextLine();
        boolean found = false;
        for (Person person : people) {
            if (person.getFirstName().equalsIgnoreCase(searchName) || person.getLastName().equalsIgnoreCase(searchName)) {
                System.out.println(person);
                found = true;
            }
        }

        if (!found) {
            System.out.println("\nPerson not found.");
        }
    }

    // Add a new person
    private void addNewPerson(Scanner scanner) {
        scanner.nextLine(); // Consume newline left over
        System.out.print("Please enter the first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Please enter the last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Please enter the email: ");
        String email = scanner.nextLine();
        System.out.print("Please enter the gender (Male/Female): ");
        String gender = scanner.nextLine();

        // Generate a random ID based on the size of the list
        int id = people.size() + 1;

        // Create a new person object and add it to the list
        Person newPerson = new Person(id, firstName, lastName, email, gender);
        people.add(newPerson);
        System.out.println("\nNew person added successfully.");
    }

    // Generate random people
    private void generateRandomPeople() {
        Random random = new Random();
        String[] firstNames = {"John", "David", "Michael", "Chris", "Daniel", "Paul", "Sarah", "Jessica", "Emily", "Samantha"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Davis", "Miller", "Wilson", "Moore", "Taylor"};
        String[] genders = {"Male", "Female"};

        System.out.println("\nGenerating random people:");
        for (int i = 0; i < 5; i++) {
            // Generate random data
            String firstName = firstNames[random.nextInt(firstNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";
            String gender = genders[random.nextInt(genders.length)];
            int id = people.size() + 1;

            // Create a new person object and add it to the list
            Person randomPerson = new Person(id, firstName, lastName, email, gender);
            people.add(randomPerson);
            System.out.println(randomPerson);
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            tempCodeRunnerFile game = new tempCodeRunnerFile();

            // Prompt user for filename
            System.out.print("Please enter the filename to read: ");
            String filename = scanner.nextLine();
            game.readFile(filename);

            // Display the menu
            game.displayMenu();
        }
    }
}
