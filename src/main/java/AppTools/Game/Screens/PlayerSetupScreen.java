package AppTools.Game.Screens;

import java.util.ArrayList;
import java.util.Scanner;

import AppTools.CardModel.AbstractCard;
import AppTools.Deck.Deck;
import AppTools.PlayerModel.BotPlayer;
import AppTools.PlayerModel.HumanPlayer;
import AppTools.PlayerModel.Player;

public class PlayerSetupScreen {

    private static final int MAX_PLAYERS = 4; // Maximum players in the game
    private static final int INITIAL_HAND_SIZE = 7; // Initial number of cards in each hand

    // Used to get the number of bots in the game
    private static int getBotCount(Scanner scanner) {

        System.out.println("Do you want to play with bots (y/N)?");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("y")) {
            System.out.println("How many bots you want to play with (0-3)?");
            int botNum = -1;

            while (!scanner.hasNextInt() || (botNum = scanner.nextInt()) < 0 || botNum > 3) {
                System.out.println("Please enter a valid number of bots (0-3):");
                scanner.next();
            }

            scanner.nextLine();
            return botNum;
        }
        return 0;

    }

    // Used to create a new player
    private static Player createPlayer(int playerIndex, boolean isBot, Deck deck, Scanner scanner) {
        String playerType = isBot ? "Bot" : "Human";

        System.out.println("What's the name of " + playerType + " number " + (playerIndex + 1) + "?");
        String name;
        while ((name = scanner.nextLine().trim()).isEmpty()) {
            System.out.println("Name cannot be empty. Please enter a valid name:");
        }

        Player player = isBot ? new BotPlayer() : new HumanPlayer();
        player.setName(name);

        ArrayList<AbstractCard> hand = new ArrayList<>();
        for (int index = 0; index < INITIAL_HAND_SIZE; index++) {
            hand.add(deck.drawCard());
        }
        player.setHand(hand);

        return player;
    }

    // Used to setup the players
    public static ArrayList<Player> setupPlayers(Deck deck, Scanner scanner) {
        ArrayList<Player> players = new ArrayList<>();
        int bots = getBotCount(scanner);

        if (bots != -1) {
            for (int i = 0; i < bots; i++) {
                Player botPlayer = createPlayer(i, true, deck, scanner);
                players.add(botPlayer);
            }
        }

        for (int index = 0; index < MAX_PLAYERS - bots; index++) {
            Player humanPlayer = createPlayer(index, false, deck, scanner);
            players.add(humanPlayer);
        }

        return players;
    }
}
