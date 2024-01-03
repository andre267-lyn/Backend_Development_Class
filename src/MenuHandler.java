import java.util.ArrayList;
import java.util.Scanner;

public class MenuHandler {
    private static ArrayList<String> jobs = new ArrayList<>();

    public static void displayHeader() {
        System.out.println("(DIGIJOBS)");
        System.out.println("(Please choose following command :)");
    }

    public static void displayOptions() {
        System.out.println("1. Add new job.");
        System.out.println("2. Print all job.");
        System.out.println("3. Delete job.");
        System.out.println("4. Exit.");
    }

    public static int getUserChoice(Scanner scanner) {
        int choice = 0;
        boolean isValid = false;

        while (!isValid) {
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scanner.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        return choice;
    }

    public static void handleUserChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                addNewJob(scanner);
                break;
            case 2:
                printAllJobs();
                break;
            case 3:
                deleteJob(scanner);
                break;
            case 4:
                System.out.println("Exiting program.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addNewJob(Scanner scanner) {
        System.out.print("Enter the job title: ");
        String jobTitle = scanner.nextLine();
        jobs.add(jobTitle);
        System.out.println("Job added: " + jobTitle);
    }

    private static void printAllJobs() {
        if (jobs.isEmpty()) {
            System.out.println("No jobs available.");
        } else {
            System.out.println("All Jobs:");
            for (int i = 0; i < jobs.size(); i++) {
                System.out.println((i + 1) + ". " + jobs.get(i));
            }
        }
    }

    private static void deleteJob(Scanner scanner) {
        if (jobs.isEmpty()) {
            System.out.println("No jobs to delete.");
        } else {
            System.out.print("Enter the job number to delete (1-" + jobs.size() + "): ");
            int jobNumber;
            try {
                jobNumber = Integer.parseInt(scanner.nextLine());
                if (jobNumber >= 1 && jobNumber <= jobs.size()) {
                    String deletedJob = jobs.remove(jobNumber - 1);
                    System.out.println("Job deleted: " + deletedJob);
                } else {
                    System.out.println("Invalid job number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}