package AppTools.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import AppTools.CardModel.AbstractCard;
import AppTools.CardModel.ActionCard;
import AppTools.CardModel.CardTypeEnum;
import AppTools.CardModel.WildCard;
import AppTools.Deck.Deck;
import AppTools.Game.Screens.PlayerSetupScreen;
import AppTools.Game.Screens.RunningGameScreen;
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
    private Boolean gameOver = null;
    private Direction gameDirection;

    public void showGameMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        CardsEffect cardsEffect = new CardsEffect();
        int choice;

        do {
            WelcomeScreen.showWelcomeScreen();

            System.out.print("Enter your choice: ");

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
                    System.out.println("\nGame setup complete! Here are the players:\n");
                    players.forEach(player -> System.out.println("- " + player));

                    Utils.pauseForUser();

                    gameStart();

                    gameRunning(cardsEffect, scanner);
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
        AbstractCard topCard;
        do {
            topCard = deck.drawCard();
            discardPile.add(topCard);
        } while (topCard instanceof ActionCard || topCard instanceof WildCard);

        currentCard = topCard;
        Random random = new Random();
        currentPlayer = players.get(random.nextInt(players.size()));
        gameDirection = Direction.CLOCKWISE;
        gameOver = false;

        RunningGameScreen.showGameStarted(currentPlayer, players, gameDirection, currentCard);
    }

    public void gameRunning(CardsEffect cardsEffect, Scanner scanner) {
        while (!gameOver) {

            if (currentPlayer instanceof HumanPlayer) {
                System.out.println("***************************");
                System.out.println(currentPlayer.getHand());
                System.out.println("***************************");

                RunningGameScreen.showGameStatus(players, currentPlayer, deck, discardPile.get(discardPile.size() - 1));
                humanPlays((HumanPlayer) currentPlayer, currentCard, scanner);

                System.out.println("***************************");
                System.out.println(currentPlayer.getHand());
                System.out.println("***************************");
                Utils.pauseForUser();

            } else {
                System.out.println("Bot is playing . . . ");
                System.out.println("***************************");
                System.out.println(currentPlayer.getHand());
                System.out.println("***************************");

                System.out.println("***************************");
                System.out.println(gameDirection);
                System.out.println("***************************");

                Utils.waitFor(2000);
                botPlays((BotPlayer) currentPlayer, currentCard);

                System.out.println("***************************");
                System.out.println(currentPlayer.getHand());
                System.out.println("***************************");

                System.out.println("***************************");
                System.out.println(gameDirection);
                System.out.println("***************************");
                Utils.pauseForUser();
            }

            checkWin(currentPlayer);
            
            if (currentCard.getType() != CardTypeEnum.NUMBER) {
                System.out.println("Entered the card effect");
                cardEffectController(currentCard, cardsEffect, scanner);
            }

            if (currentCard.getType() == CardTypeEnum.SKIP) {
                continue;
            }

            int currentIndex = players.indexOf(currentPlayer);
            if (gameDirection == Direction.CLOCKWISE) {
                currentPlayer = players.get((currentIndex + 1) % players.size());
            } else {
                currentPlayer = players.get((currentIndex - 1 + players.size()) % players.size());
            }


            Utils.clearScreen();
            RunningGameScreen.showPlayerTurn(currentPlayer);
        }
    }

    public void humanPlays(HumanPlayer player, AbstractCard currentCard, Scanner scanner) {
        int index;
        System.out.println("Your hand:");
        player.showHand();

        if (currentCard.getType() == CardTypeEnum.WILD_DRAW_FOUR) {
            System.out.println("You gotta draw 4 cards !\n");
            for (int i = 0; i < 4; i++) {
                checkEmptyDeck();
                currentPlayer.addCard(deck.drawCard());
            }
        }
        
        if (currentCard.getType() == CardTypeEnum.DRAW_TWO) {
            System.out.println("You gotta draw 2 cards !\n");
            for (int i = 0; i < 2; i++) {
                checkEmptyDeck();
                currentPlayer.addCard(deck.drawCard());
            }
        }

        if (player.hasPlayableCard(player.getHand(), currentCard)) {
            System.out.println("Since you have cards pick a card to play according to its index");

            while (true) {
                System.out.println("Please enter the index of the card you want to play:");
                if (scanner.hasNextInt()) {
                    index = scanner.nextInt() - 1;

                    if (index < 0 || index >= player.getHand().size()) {
                        System.out.println("Invalid index: " + index + ". Please enter a valid index.");
                    } else if (player.getHand().get(index).isPlayable(currentCard)) {
                        AbstractCard playedCard = player.playCard(player.getHand(), currentCard, index);
                        discardPile.add(playedCard);
                        this.currentCard = playedCard;
                        break;
                    } else {
                        System.out.println("The selected card is not playable. Please choose another card.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear the invalid input
                }
            }
        } else {
            System.out.println("You have no card to play, draw a card");
            checkEmptyDeck();
            AbstractCard tempCard = deck.drawCard();
            if (tempCard.isPlayable(currentCard)) {
                discardPile.add(tempCard);
                this.currentCard = tempCard;
            } else {
                player.addCard(tempCard);
            }
        }
    }

    public void botPlays(BotPlayer botPlayer, AbstractCard currentCard) {
        if (botPlayer == null || currentCard == null) {
            throw new IllegalArgumentException("BotPlayer or currentCard cannot be null.");
        }
        if (currentCard.getType() == CardTypeEnum.WILD_DRAW_FOUR) {
            for (int i = 0; i < 4; i++) {
                checkEmptyDeck();
                currentPlayer.addCard(deck.drawCard());
            }
        }
        if (currentCard.getType() == CardTypeEnum.DRAW_TWO) {
            for (int i = 0; i < 2; i++) {
                checkEmptyDeck();
                currentPlayer.addCard(deck.drawCard());
            }
        }
        if (botPlayer.hasPlayableCard(currentCard)) {
            AbstractCard playedCard = botPlayer.playCard(currentCard);
            discardPile.add(playedCard);
            this.currentCard = playedCard;
        } else {
            checkEmptyDeck();
            AbstractCard tempCard = deck.drawCard();
            if (tempCard.isPlayable(currentCard)) {
                discardPile.add(tempCard);
                this.currentCard = tempCard;
            } else {
                botPlayer.addCard(tempCard);
            }
        }
    }

    public void checkWin(Player player) {
        if (player.getHand().isEmpty()) {
            gameOver = true;
            System.out.println(player.getName() + " has won the game!");
            Utils.pauseForUser();
        }
    }

    public void cardEffectController(AbstractCard card, CardsEffect cardsEffect, Scanner scanner) {
        if (card instanceof WildCard) {
            cardsEffect.wildCardsEffect((WildCard) card, currentPlayer, scanner);

        } else if (card instanceof ActionCard) {
            if (card.getType() == CardTypeEnum.REVERSE) {
                gameDirection = cardsEffect.reverseCardEffect(gameDirection);
                System.out.println("Game reversed: " + gameDirection);
            } else if (card.getType() == CardTypeEnum.SKIP) {
                int currentIndex = players.indexOf(currentPlayer);
                currentPlayer = players.get(cardsEffect.skipCardEffect(currentIndex, gameDirection));
            } 
        }
    }

    private void checkEmptyDeck() {
        if (deck.isEmpty()) {
            deck.reInitializeDeck(discardPile);
        }
    }

    // TODO: Used for tests only, MUST-REMOVE in the end
    public static void main(String[] args) throws IOException {
        GameService service = new GameService();
        service.showGameMenu();
    }
}