package App.Game;

import App.CardModel.*;
import App.Deck.*;
import App.PlayerModel.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class GameLogic {

    private final int playerPicker = 1; //An index used to wonder through the players array.
    final private ArrayList<Player> players;
    final private Deck deck;
    final private ArrayList<AbstractCard> queue;
    private ArrayList<AbstractCard> drawPile;

    public GameLogic() {
        players = new ArrayList<>(0);
        queue = new ArrayList<>(0);
        drawPile = new ArrayList<>(0);
        deck = new Deck();
        initializeGame();

    }

    public void initializeGame() {
        Scanner scanner = new Scanner(System.in);
        int numPlayers = 0;

        while (true) {
            System.out.println("Enter the number of players (2-4): ");
            try {
                numPlayers = Integer.parseInt(scanner.nextLine());
                if (numPlayers >= 2 && numPlayers <= 4) {
                    break;
                } else {
                    System.out.println("Invalid number of players. Please enter a number between 2 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        int botPlayers = 0;

        while (true) {
            System.out.println("Enter the number of bot players (0-" + (numPlayers - 1) + "): ");
            try {
                botPlayers = Integer.parseInt(scanner.nextLine());
                if (botPlayers >= 0 && numPlayers - botPlayers >= 1) {
                    break;
                } else {
                    System.out.println("Invalid number of bot players. You should at least one human player");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid number.");
            }

        }
        for (int i = 0; i < numPlayers - botPlayers; i++) {
            System.out.println("Enter the name of the " + (i+1) + " player: ");
            String name = scanner.nextLine();
            players.add(new HumanPlayer(name));
        }

        for (int i = 0; i < botPlayers; i++) {
            players.add(new BotPlayer("BotPlayer" + (i+1)));
        }

        Collections.shuffle(players);

    }

    public void dealCards() {
        int cardsPerPlayer = 7;

        for (Player player : players) {
            ArrayList<AbstractCard> hand = new ArrayList<>();
            for (int i = 0; i < cardsPerPlayer; i++) {
                hand.add(deck.drawCard());
            }
            player.setHand(hand);

        }
        drawPile = deck.getRemainingCards();
    }






}









