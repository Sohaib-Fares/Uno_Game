package AppTools.Game;

import AppTools.CardModel.CardColorEnum;
import AppTools.CardModel.WildCard;
import AppTools.PlayerModel.BotPlayer;
import AppTools.PlayerModel.HumanPlayer;
import AppTools.PlayerModel.Player;

import java.util.Random;
import java.util.Scanner;

public class CardsEffect {

    public void wildCardsEffect(WildCard playedCard, Player player, Scanner scanner) {
        if (player instanceof BotPlayer) {
            Random random = new Random();
            int selectedColorIndex = random.nextInt(0,CardColorEnum.values().length);
            CardColorEnum selectedColor = CardColorEnum.values()[selectedColorIndex];
            System.out.println("Bot played a wild card and chose the color: " + selectedColor);

        } else if (player instanceof HumanPlayer) {
            System.out.println("You just played a wild card!");
            System.out.println("Pick a color to continue playing with:");

            // Display available colors to choose from
            System.out.println("Available colors:");
            for (CardColorEnum color : CardColorEnum.values()) {
                System.out.println("- " + color);
            }

            int selectedColorIndex = -1;
            while (selectedColorIndex < 0 || selectedColorIndex >= CardColorEnum.values().length) {
                System.out.print("Enter a number (0 - " + (CardColorEnum.values().length - 1) + "): ");

                if (scanner.hasNextInt()) {
                    selectedColorIndex = scanner.nextInt();
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
        final int MAX_PLAYERS = 4;

        if (direction == Direction.CLOCKWISE) {
            return (currentIndex + 2) % MAX_PLAYERS;
        } else if (direction == Direction.COUNTERCLOCKWISE) {
            return (currentIndex - 2 + MAX_PLAYERS) % MAX_PLAYERS;
        } else {
            throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }


}
