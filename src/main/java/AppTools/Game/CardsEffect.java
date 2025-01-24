package AppTools.Game;

import java.util.Random;
import java.util.Scanner;

import AppTools.CardModel.CardColorEnum;
import AppTools.CardModel.WildCard;
import AppTools.Game.Screens.PlayerSetupScreen;
import AppTools.PlayerModel.BotPlayer;
import AppTools.PlayerModel.HumanPlayer;
import AppTools.PlayerModel.Player;

public class CardsEffect {

    public void wildCardsEffect(WildCard playedCard, Player player, Scanner scanner) {

        // Check if the color has already been chosen
        if (playedCard.getColor() != null) {
            return;
        }

        if (player instanceof BotPlayer) {
            Random random = new Random();
            int selectedColorIndex = random.nextInt(0, CardColorEnum.values().length);
            CardColorEnum selectedColor = CardColorEnum.values()[selectedColorIndex];
            System.out.println("Bot played a wild card and chose the color: " + selectedColor);

        } else if (player instanceof HumanPlayer) {
            System.out.println("You just played a wild card!");
            System.out.println("Pick a color to continue playing with:");

            // Display available colors to choose from
            System.out.println("Available colors:");
            for (int i = 0; i < CardColorEnum.values().length; i++) {
                System.out.println((i + 1) + "- " + CardColorEnum.values()[i]);
            }

            int selectedColorIndex = -1;
            while (selectedColorIndex < 0 || selectedColorIndex >= CardColorEnum.values().length) {
                System.out.print("Enter a number (1 - " + (CardColorEnum.values().length) + "): ");

                if (scanner.hasNextInt()) {
                    selectedColorIndex = scanner.nextInt() - 1;
                    if (selectedColorIndex < 0 || selectedColorIndex >= CardColorEnum.values().length) {
                        System.out.println("Invalid choice. Please choose a valid color.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // consume invalid input
                }
            }

            CardColorEnum selectedColor = CardColorEnum.values()[selectedColorIndex];
            System.out.println("You selected: " + selectedColor);
            playedCard.setChosenColor(selectedColor);
        }
    }

    public Direction reverseCardEffect(Direction direction) {

        if (direction == Direction.CLOCKWISE) {
            return Direction.COUNTERCLOCKWISE;
        } else if (direction == Direction.COUNTERCLOCKWISE) {
            return Direction.CLOCKWISE;
        } else {
            throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

    public int skipCardEffect(int currentIndex, Direction direction) {
        final int MAX_PLAYERS = PlayerSetupScreen.MAX_PLAYERS;

        if (direction == Direction.CLOCKWISE) {
            return (currentIndex + 2) % MAX_PLAYERS;
        } else if (direction == Direction.COUNTERCLOCKWISE) {
            return (currentIndex - 2 + MAX_PLAYERS) % MAX_PLAYERS;
        } else {
            throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

}