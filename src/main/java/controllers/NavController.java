package controllers;

import java.util.List;
import model.PlayerModel.PlayerConfig;
import view.MuMainFrame;
import view.Components.MuConstants;

public class NavController {

    private MuMainFrame mainFrame;
    private List<PlayerConfig> currentPlayers; // Store player configuration

    public NavController(MuMainFrame mainFrame) {
        if (mainFrame == null) {
            throw new IllegalArgumentException("MuMainFrame cannot be null");
        }
        this.mainFrame = mainFrame;
    }

    public void showMainMenu() {
        mainFrame.displayPanel(MuConstants.MENU_PANEL);
    }

    public void showGameSetup() {
        mainFrame.displayPanel(MuConstants.SETUP_PANEL);
    }

    // Method to start the game with the collected player configurations
    public void startGame(List<PlayerConfig> playerConfigs) {
        this.currentPlayers = playerConfigs;
        // TODO: Initialize GameController with playerConfigs here if applicable
        System.out.println("Starting game with " + playerConfigs.size() + " players."); // Debugging
        // Now navigate to the game panel, potentially passing the data
        showGamePlay();
    }

    // Updated to potentially use stored player data
    public void showGamePlay() {
        // Pass the player data to the main frame so it can create/update the game panel
        mainFrame.displayGamePanel(currentPlayers);
    }

    public void showHowToPlay() {
        mainFrame.displayPanel(MuConstants.HOW_TO_PLAY_PANEL);
    }

    // Add methods for any other navigation needed, e.g., goBack()
    public void goBackToMenu() { // Example back navigation
        showMainMenu();
    }

    // Getter for player configs if needed by other parts (like GameController)
    public List<PlayerConfig> getCurrentPlayers() {
        return currentPlayers;
    }
}
