package AppTools.Game.Screens;

import java.util.ArrayList;
import java.util.List;

import AppTools.CardModel.AbstractCard;
import AppTools.Deck.Deck;
import AppTools.Game.Direction;
import AppTools.OurUtils.Utils;
import AppTools.PlayerModel.BotPlayer;
import AppTools.PlayerModel.Player;

public class RunningGameScreen {
    public RunningGameScreen() {
    }

    public static void showGameStarted(Player currentPlayer, ArrayList<Player> players, Direction gameDirection,
            AbstractCard discardPile) {

        String playerName = currentPlayer.getName();

        Utils.clearScreen();

        for (int i = 0; i < 3; i++) {
            System.out.print("        ____                        ____  _             _           _   _ \n" + //
                    "       / ___| __ _ _ __ ___   ___  / ___|| |_ __ _ _ __| |_ ___  __| | | |\n" + //
                    "      | |  _ / _` | '_ ` _ \\ / _ \\ \\___ \\| __/ _` | '__| __/ _ \\/ _` | | |\n" + //
                    "      | |_| | (_| | | | | | |  __/  ___) | || (_| | |  | ||  __/ (_| | |_|\n" + //
                    "       \\____|\\__,_|_| |_| |_|\\___| |____/ \\__\\__,_|_|   \\__\\___|\\__,_| (_)");

            Utils.waitFor(900);

            Utils.clearScreen();

            Utils.waitFor(600);

        }

        Utils.printTable("First Player to start", playerName);
        Utils.printTable("Total Number Of Players", String.valueOf(players.size()));
        Utils.printTable("Number Of Bots", String.valueOf(getBotPlayers(players).size()));

        Utils.pauseForUser();
    }

    public static void showPlayerTurn(Player currentPlayer) {
        Utils.printTable("It's the turn of", currentPlayer.getName());
        System.out.println("\nTo have fun, please don't look at his cards !\n");
        Utils.pauseForUser();
    }

    private static List<BotPlayer> getBotPlayers(List<Player> players) {
        List<BotPlayer> botPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player instanceof BotPlayer) {
                botPlayers.add((BotPlayer) player);
            }
        }
        return botPlayers;
    }

    public static void showGameStatus(ArrayList<Player> players, Player currentPlayer, Deck drawDeck,
            AbstractCard discardPileTop) {
        int rightPlayer = players.indexOf(currentPlayer);

        System.out.println("================================== UNO GAME STATUS ==================================");
        System.out.println();

        // Draw Deck and Discard Pile
        System.out.println("Draw Deck [ 🂠 ]   Discard Pile: " + discardPileTop);
        System.out.println();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            if (player.equals(currentPlayer)) {
                rightPlayer = i;

            } else {
                // Flipped cards for other players
                System.out.println((i + 1) + "- " + player.getName() + ": " + getFlippedCardRepresentation(player.getHand().size()));
            }

            // Print a new line to separate players visually
            System.out.println();
        }
        System.out.println((rightPlayer + 1) + "- " + currentPlayer.getName());

        System.out.println();
    }

    private static String getFlippedCardRepresentation(int cardCount) {
        StringBuilder flippedCards = new StringBuilder();
        for (int i = 0; i < cardCount; i++) {
            flippedCards.append("🂠 "); // Flipped card representation
        }
        return flippedCards.toString();
    }

}

