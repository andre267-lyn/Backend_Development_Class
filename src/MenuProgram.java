import java.util.Scanner;

public class MenuProgram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            MenuHandler.displayHeader();
            MenuHandler.displayOptions();

            int userChoice = MenuHandler.getUserChoice(scanner);
            MenuHandler.handleUserChoice(userChoice, scanner);

            if (userChoice == 4) {
                running = false;
            }
        }

        scanner.close();
    }
}