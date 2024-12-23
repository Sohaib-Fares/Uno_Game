package App.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import App.Deck.Deck;
import App.Game.Screens.PlayerSetupScreen;
import App.Game.Screens.HowToPlayScreen;
import App.Game.Screens.WelcomeScreen;
import App.OurUtils.Utils;
import App.PlayerModel.Player;

public class GameService {
    private Deck deck = new Deck();

    public void showGameMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            WelcomeScreen.showWelcomeScreen();

            // TODO: Used for tests only, MUST-REMOVE in the end
            System.out.println("*************************");
            System.out.println(deck.toString());
            System.out.println("*************************");
            //

            System.out.print("Enter your choice: ");

            // Validate input
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            Utils.clearScreen();

            switch (choice) {
                case 1 -> {
                    System.out.println("Starting the game...");
                    ArrayList<Player> players = PlayerSetupScreen.setupPlayers(deck, scanner);
                    System.out.println("Game setup complete! Here are the players:");
                    players.forEach(player -> System.out.println(player));

                    Utils.pauseForUser();
                }
                case 2 -> {
                    HowToPlayScreen.showHowToPlay();
                    Utils.pauseForUser();
                }
                case 3 -> System.out.println("Thank you for playing JavUno ♥");
                default -> {
                    System.out.println("Invalid choice. Please select an option from the menu.");
                    Utils.pauseForUser();
                }
            }

            if (choice != 3) {
                System.out.println("\nReturning to the main menu...");
            }

        } while (choice != 3);

        scanner.close();
    }


    // TODO: Used for tests only, MUST-REMOVE in the end
    public static void main(String[] args) throws IOException {
        GameService service = new GameService();
        service.showGameMenu();
    }
}