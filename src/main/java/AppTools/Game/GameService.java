package AppTools.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import AppTools.CardModel.AbstractCard;
import AppTools.CardModel.ActionCard;
import AppTools.CardModel.CardColorEnum;
import AppTools.CardModel.CardTypeEnum;
import AppTools.CardModel.NumberedCard;
import AppTools.CardModel.WildCard;
import AppTools.Deck.Deck;
import AppTools.Game.Screens.PlayerSetupScreen;
import AppTools.Game.Screens.RunningGameScreen;
import AppTools.Game.Screens.GameOverScreen;
import AppTools.Game.Screens.HowToPlayScreen;
import AppTools.Game.Screens.WelcomeScreen;
import AppTools.OurUtils.Utils;
import AppTools.PlayerModel.BotPlayer;
import AppTools.PlayerModel.HumanPlayer;
import AppTools.PlayerModel.Player;

public class GameService {
    private Deck deck = new Deck();
    private ArrayList<AbstractCard> discardPile = new ArrayList<>();
    private ArrayList<Player> players;
    private Player currentPlayer;
    private AbstractCard currentCard;
    private Boolean gameOver = null;
    private Direction gameDirection;
    private boolean nextDrawsCards = false;
    private boolean nextSkip = false;
    private int cardsToDraw = 0;

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
        currentPlayer = players.get(ThreadLocalRandom.current().nextInt(players.size()));
        gameDirection = Direction.CLOCKWISE;
        gameOver = false;

        RunningGameScreen.showGameStarted(currentPlayer, players, gameDirection, currentCard);
    }

    public void gameRunning(CardsEffect cardsEffect, Scanner scanner) {
        while (true) {

            if (currentPlayer instanceof HumanPlayer) {

                RunningGameScreen.showGameStatus(players, currentPlayer, deck, discardPile.get(discardPile.size() - 1));
                humanPlays((HumanPlayer) currentPlayer, currentCard, scanner);
                if (!(currentCard.getColor() == null)) {
                    Utils.pauseForUser();
                }

            } else {
                System.out.println("Bot is playing . . . ");

                Utils.waitFor(2000);
                botPlays((BotPlayer) currentPlayer, currentCard);

                Utils.pauseForUser();
            }

            if (checkWin(currentPlayer)) {
                System.out.println(currentPlayer.getName() + " Won the game.");
                GameOverScreen.showGameOver(currentPlayer.getName());
                break;
            }

            cardEffectController(currentCard, cardsEffect, scanner);

            if (currentCard.getType() == CardTypeEnum.SKIP) {
                Utils.clearScreen();
                RunningGameScreen.showPlayerTurn(currentPlayer);
                continue;
            }

            nextRound();

            Utils.clearScreen();
            RunningGameScreen.showPlayerTurn(currentPlayer);
        }
    }

    private void nextRound() {
        int currentIndex = players.indexOf(currentPlayer);
        if (gameDirection == Direction.CLOCKWISE) {
            currentPlayer = players.get((currentIndex + 1) % players.size());
        } else {
            currentPlayer = players.get((currentIndex - 1 + players.size()) % players.size());
        }
    }

    private void handleDrawCardEffect() {
        String playerName = currentPlayer instanceof HumanPlayer ? currentPlayer.getName()
                : ("Bot:" + currentPlayer.getName());
        System.out.println(playerName + " has to draw " + cardsToDraw + " cards and lose the turn!");
        if (!(currentPlayer instanceof BotPlayer)) {
            System.out.print("Cards drawed: ");
            for (int i = 0; i < cardsToDraw; i++) {
                checkEmptyDeck();
                currentPlayer.addCard(deck.drawCard());
                System.out.print(currentPlayer.getHand().get(currentPlayer.getHand().size() - 1) + ", ");
            }
            System.out.println();
        } else {
            for (int i = 0; i < cardsToDraw; i++) {
                checkEmptyDeck();
                currentPlayer.addCard(deck.drawCard());
            }
        }
        cardsToDraw = 0;
        nextDrawsCards = false;
    }

    public void humanPlays(HumanPlayer player, AbstractCard currentCard, Scanner scanner) {
        int index;
        player.showHand();

        if (nextDrawsCards) {
            System.out.println();
            handleDrawCardEffect();
            return;
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

                        // Check this out
                        if (playedCard.getType() == CardTypeEnum.WILD_DRAW_FOUR) {
                            cardsToDraw = 4;
                            nextDrawsCards = true;
                        }
                        if (playedCard.getType() == CardTypeEnum.DRAW_TWO) {
                            cardsToDraw = 2;
                            nextDrawsCards = true;
                        }

                        if (playedCard.getType() == CardTypeEnum.SKIP) {
                            nextSkip = true;
                        }

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
                if (tempCard.getType() == CardTypeEnum.WILD_DRAW_FOUR) {
                    cardsToDraw = 4;
                    nextDrawsCards = true;
                }
                if (tempCard.getType() == CardTypeEnum.DRAW_TWO) {
                    cardsToDraw = 2;
                    nextDrawsCards = true;
                }

                if (tempCard.getType() == CardTypeEnum.SKIP) {
                    nextSkip = true;
                }
                System.out.println("You draw a card and played it: " + tempCard);
            } else {
                player.addCard(tempCard);
                System.out.println("The drawn card is: " + tempCard);
            }
        }

    }

    public void botPlays(BotPlayer botPlayer, AbstractCard currentCard) {
        if (botPlayer == null || currentCard == null) {
            throw new IllegalArgumentException("BotPlayer or currentCard cannot be null.");
        }
        if (nextDrawsCards) {
            handleDrawCardEffect();
            return;
        }

        if (botPlayer.hasPlayableCard(currentCard)) {
            AbstractCard playedCard = botPlayer.playCard(currentCard);
            discardPile.add(playedCard);
            this.currentCard = playedCard;

            if (playedCard instanceof WildCard) {
                Random random = new Random();
                int selectedColorIndex = random.nextInt(CardColorEnum.values().length);
                CardColorEnum selectedColor = CardColorEnum.values()[selectedColorIndex];
                ((WildCard) playedCard).setChosenColor(selectedColor);
                System.out.println("Bot played a wild card and chose the color: " + selectedColor);
            } else {
                System.out.println("Bot played the card: " + playedCard);
            }

            if (playedCard.getType() == CardTypeEnum.WILD_DRAW_FOUR) {
                cardsToDraw = 4;
                nextDrawsCards = true;
            }
            if (playedCard.getType() == CardTypeEnum.DRAW_TWO) {
                cardsToDraw = 2;
                nextDrawsCards = true;
            }

            if (playedCard.getType() == CardTypeEnum.SKIP) {
                nextSkip = true;
            }

        } else {
            checkEmptyDeck();
            AbstractCard tempCard = deck.drawCard();
            if (tempCard.isPlayable(currentCard)) {
                discardPile.add(tempCard);
                this.currentCard = tempCard;

                if (tempCard instanceof WildCard) {
                    Random random = new Random();
                    int selectedColorIndex = random.nextInt(CardColorEnum.values().length);
                    CardColorEnum selectedColor = CardColorEnum.values()[selectedColorIndex];
                    ((WildCard) tempCard).setChosenColor(selectedColor);
                    if (tempCard.getType() == CardTypeEnum.WILD_DRAW_FOUR) {
                        System.out.println("Bot draw a Wild card +4, played it and choose the color: " + selectedColor);

                    } else {

                        System.out.println("Bot draw a Wild card, played it and choose the color: " + selectedColor);
                    }
                } else {
                    System.out.println("Bot draw a card and played it : " + tempCard);

                }

                if (tempCard.getType() == CardTypeEnum.WILD_DRAW_FOUR) {
                    cardsToDraw = 4;
                    nextDrawsCards = true;
                }
                if (tempCard.getType() == CardTypeEnum.DRAW_TWO) {
                    cardsToDraw = 2;
                    nextDrawsCards = true;
                }

                if (tempCard.getType() == CardTypeEnum.SKIP) {
                    nextSkip = true;
                }

            } else {
                System.out.println("Bot draw a card.");
                botPlayer.addCard(tempCard);
            }
        }
    }

    public boolean checkWin(Player player) {
        boolean gameOver = false;
        if (player.getHand().isEmpty()) {
            gameOver = true;
            return true;
        }
        return false;
    }

    public void cardEffectController(AbstractCard currentCard, CardsEffect cardsEffect, Scanner scanner) {
        if (currentCard instanceof WildCard) {
            cardsEffect.wildCardsEffect((WildCard) currentCard, currentPlayer, scanner);

        } else if (currentCard instanceof ActionCard) {
            if (currentCard.getType() == CardTypeEnum.REVERSE) {
                gameDirection = cardsEffect.reverseCardEffect(gameDirection);

            } else if (currentCard.getType() == CardTypeEnum.SKIP) {
                int currentIndex = players.indexOf(currentPlayer);
                currentPlayer = players.get(cardsEffect.skipCardEffect(currentIndex, gameDirection));
                nextSkip = false;
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