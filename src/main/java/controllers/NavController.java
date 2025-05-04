package controllers;

import view.MuMainFrame;
import view.Components.MuConstants;

public class NavController {

    private MuMainFrame mainFrame;

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

    public void showGamePlay() {
        // Potentially needs more logic via GameController before showing
        mainFrame.displayPanel(MuConstants.GAME_PANEL);
    }

    public void showHowToPlay() {
        mainFrame.displayPanel(MuConstants.HOW_TO_PLAY_PANEL);
    }

    // Add methods for any other navigation needed, e.g., goBack()
    public void goBackToMenu() { // Example back navigation
        showMainMenu();
    }
}
