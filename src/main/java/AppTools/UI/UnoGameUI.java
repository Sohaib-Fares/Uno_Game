package AppTools.UI;

import AppTools.CardModel.*;
import AppTools.Deck.Deck;
import AppTools.Game.GameService;
import AppTools.PlayerModel.BotPlayer;
import AppTools.PlayerModel.HumanPlayer;
import AppTools.PlayerModel.Player;
import AppTools.UI.Screens.MainMenuScreen;
import AppTools.UI.Screens.PlayerSetupScreen;
import AppTools.UI.Screens.HowToPlayScreen;
import AppTools.UI.Screens.GameScreen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UnoGameUI extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);

    private final MainMenuScreen mainMenuScreen;
    private final PlayerSetupScreen playerSetupScreen;
    private final HowToPlayScreen howToPlayScreen;

    private GameScreen gameScreen;

    public UnoGameUI() {
        // Set up the frame
        setTitle("UNO Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 680); // Slightly larger to accommodate the game screen
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        // Apply theme
        UnoTheme.setupTheme();

        // Create screens
        mainMenuScreen = new MainMenuScreen(
                e -> showScreen("playerSetup"),
                e -> showScreen("howToPlay"),
                e -> System.exit(0));

        playerSetupScreen = new PlayerSetupScreen(
                e -> showScreen("mainMenu"),
                e -> startGame());

        howToPlayScreen = new HowToPlayScreen(
                e -> showScreen("mainMenu"));

        // Add screens to content panel
        contentPanel.add(mainMenuScreen, "mainMenu");
        contentPanel.add(playerSetupScreen, "playerSetup");
        contentPanel.add(howToPlayScreen, "howToPlay");

        // Show main menu initially
        add(contentPanel);
        showScreen("mainMenu");
    }

    private void showScreen(String screenName) {
        cardLayout.show(contentPanel, screenName);

        // Adjust window size based on screen
        if (screenName.equals("gameScreen")) {
            setSize(900, 680);
        } else {
            setSize(800, 600);
        }

        // Center the window after resize
        setLocationRelativeTo(null);
    }

    private void startGame() {
        List<PlayerSetupScreen.PlayerSetupData> playerDataList = playerSetupScreen.getPlayerData();

        // Create players based on setup
        ArrayList<Player> players = new ArrayList<>();
        Deck deck = new Deck();

        for (PlayerSetupScreen.PlayerSetupData playerData : playerDataList) {
            Player player;
            if (playerData.isHuman()) {
                player = new HumanPlayer(playerData.getName());
            } else {
                player = new BotPlayer(playerData.getName());
            }

            // Initialize player's hand
            ArrayList<AbstractCard> hand = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                hand.add(deck.drawCard());
            }
            player.setHand(hand);

            players.add(player);
        }

        // Initialize game service with players
        GameService gameService = new GameService();

        // Create game screen
        gameScreen = new GameScreen(
                gameService,
                players,
                deck,
                e -> showScreen("mainMenu"));

        // Add game screen to content panel
        contentPanel.add(gameScreen, "gameScreen");

        // Show game screen
        showScreen("gameScreen");

        // Start the game
        gameScreen.startGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Apply the system look and feel with UNO theme customizations
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                UnoTheme.setupTheme();
            } catch (Exception e) {
                e.printStackTrace();
            }

            new UnoGameUI().setVisible(true);
        });
    }
}