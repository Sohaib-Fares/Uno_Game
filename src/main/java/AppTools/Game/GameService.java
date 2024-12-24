package AppTools.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import AppTools.CardModel.AbstractCard;
import AppTools.Deck.Deck;
import AppTools.Game.Screens.PlayerSetupScreen;
import AppTools.Game.Screens.HowToPlayScreen;
import AppTools.Game.Screens.WelcomeScreen;
import AppTools.OurUtils.Utils;
import AppTools.PlayerModel.BotPlayer;
import AppTools.PlayerModel.HumanPlayer;
import AppTools.PlayerModel.Player;

import javax.swing.*;

public class GameService {
    private Deck deck = new Deck();
    private ArrayList<AbstractCard> discardPile = new ArrayList<>();
    private ArrayList<Player> players;
    private Player currentPlayer;
    private AbstractCard currentCard;
    private Boolean gmeOver = null;
    private Direction direction;

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
                    players = PlayerSetupScreen.setupPlayers(deck, scanner);
                    System.out.println("Game setup complete! Here are the players:");
                    players.forEach(player -> System.out.println(player));

                    Utils.pauseForUser();
                }
                case 2 -> {
                    HowToPlayScreen.showHowToPlay();
                    Utils.pauseForUser();
                }
                case 3 -> {
                    System.out.println("Thank you for playing JavUno ♥");
                }
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

    public void gameStart() throws IOException {

    }

    public void humanPlays(HumanPlayer player, AbstractCard currentCard, Scanner scanner) {
        int index;
        System.out.println("Your hand:");
        player.showHand();

        if(player.hasPlayableCard(player.getHand(), currentCard)) {

            System.out.println("Since you have cards pick a card to play according to it's index");

            while (true) {
                if (scanner.hasNextInt()) {
                    index = scanner.nextInt();

                    if (index < 0 || index >= player.getHand().size()) {
                        System.out.println("Invalid index: " + index + ". Please enter a valid index.");
                    }
                    else if (player.getHand().get(index).isPlayable(currentCard))
                    {
                        discardPile.add(player.playCard(player.getHand(),currentCard,index));
                        break;
                    }
                }
                else
                {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear the invalid input
                }
            }
        }
        else {
            AbstractCard tempCard  = (deck.drawCard());
            if(tempCard.isPlayable(currentCard)) {
                discardPile.add(tempCard);
            }
            else {
                player.addCard(tempCard);
            }
        }

    }

    public void BotPlays(BotPlayer botPlayer, AbstractCard currentCard){

        if (botPlayer == null || currentCard == null) {
            throw new IllegalArgumentException("BotPlayer or currentCard cannot be null.");
        }
        if ( botPlayer.hasPlayableCard(currentCard) ) {
            discardPile.add(botPlayer.playCard(currentCard));
        }
        else {
            AbstractCard tempCard  = (deck.drawCard());
            if(tempCard.isPlayable(currentCard)) {
                discardPile.add(tempCard);
            }
            else {
                botPlayer.addCard(tempCard);
            }
        }
    }



    // TODO: Used for tests only, MUST-REMOVE in the end
    public static void main(String[] args) throws IOException {
        GameService service = new GameService();
        service.showGameMenu();
    }
}