package view;

import javax.swing.*;

import UI.Components.Frames.MuFrame;
import UI.Components.Misc.MuImageIcon;
import UI.Components.Utils.MuMainBackgroundPanel;

import java.awt.*;
import java.util.List; // Import List
import controllers.NavController;
import controllers.GameController; // Import GameController
import model.PlayerModel.PlayerConfig; // Import PlayerConfig
import view.Components.MuConstants;

public class MuMainFrame extends MuFrame {

    private CardLayout cardLayout;
    private JPanel mainContentPanel;
    private NavController navController; // Reference to the controller
    private GameController gameController; // Reference to GameController

    // Child Panels - Declare them but don't instantiate here
    private MuMenuPanel menuPanel;
    private MuGameSetupPanel setupPanel;
    private MuHowToPlayPanel howToPlayPanel;

    public MuMainFrame() {
        super("JUno"); // Set a title

        MuImageIcon image = new MuImageIcon("src/main/resources/assets/JUNO.png");
        super.setIconImage(image.getImage());

        MuMainBackgroundPanel backgroundPanel = new MuMainBackgroundPanel(); // Keep the background
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(new GridBagLayout()); // Center the content container

        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setOpaque(false); // Make it transparent to see the background

        // Add the mainContentPanel to the backgroundPanel (initially empty)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(mainContentPanel, gbc);

        super.setLocationByPlatform(false);
    }

    // Method for the NavController to call for standard panels
    public void displayPanel(String panelName) {
        if (cardLayout != null && mainContentPanel != null) {
            cardLayout.show(mainContentPanel, panelName);
        } else {
            System.err.println("Error: CardLayout or mainContentPanel not initialized before displayPanel call.");
        }
    }

    // Specific method to display the game panel, creating it fresh each time
    public void displayGamePanel(List<PlayerConfig> playerConfigs) {
        System.out.println("[MuMainFrame] displayGamePanel called."); // Debug
        if (navController == null || gameController == null) {
            System.err.println("[MuMainFrame] Error: NavController or GameController not set.");
            return;
        }
        if (cardLayout != null && mainContentPanel != null) {
            System.out.println("[MuMainFrame] CardLayout and mainContentPanel are valid."); // Debug
            // Remove the old game panel if it exists
            Component oldGamePanel = null;
            for (Component comp : mainContentPanel.getComponents()) {
                if (comp instanceof MuGamePlayPanel) {
                    oldGamePanel = comp;
                    System.out.println("[MuMainFrame] Found old MuGamePlayPanel instance to remove."); // Debug
                    break;
                }
            }
            if (oldGamePanel != null) {
                mainContentPanel.remove(oldGamePanel);
                System.out.println("[MuMainFrame] Removed old game panel."); // Debug
            } else {
                System.out.println("[MuMainFrame] No old game panel found to remove."); // Debug
            }

            // Create a new game panel
            MuGamePlayPanel newGamePanel = null;
            try {
                System.out.println("[MuMainFrame] Creating new MuGamePlayPanel..."); // Debug
                newGamePanel = new MuGamePlayPanel(navController, gameController, playerConfigs);
                System.out.println("[MuMainFrame] MuGamePlayPanel created successfully."); // Debug
            } catch (Exception e) {
                System.err.println("[MuMainFrame] Error creating MuGamePlayPanel: " + e.getMessage());
                e.printStackTrace(); // Print stack trace for detailed error
                return; // Stop if panel creation fails
            }

            // Initialize the game controller
            try {
                System.out.println("[MuMainFrame] Initializing GameController..."); // Debug
                gameController.initializeGame(playerConfigs);
                System.out.println("[MuMainFrame] GameController initialized successfully."); // Debug
            } catch (Exception e) {
                System.err.println("[MuMainFrame] Error initializing GameController: " + e.getMessage());
                e.printStackTrace();
                // Decide if you want to return here or proceed with showing the panel anyway
            }

            // Add the new panel to CardLayout
            System.out.println("[MuMainFrame] Adding new game panel to CardLayout with name: " + MuConstants.GAME_PANEL); // Debug
            mainContentPanel.add(newGamePanel, MuConstants.GAME_PANEL);

            // Revalidate and repaint the container panel AFTER adding/removing components
            System.out.println("[MuMainFrame] Revalidating and repainting mainContentPanel..."); // Debug
            mainContentPanel.revalidate();
            mainContentPanel.repaint();

            // Show the newly added panel
            System.out.println("[MuMainFrame] Calling cardLayout.show() for panel: " + MuConstants.GAME_PANEL); // Debug
            cardLayout.show(mainContentPanel, MuConstants.GAME_PANEL);
            System.out.println("[MuMainFrame] cardLayout.show() called."); // Debug

        } else {
            System.err.println("[MuMainFrame] Error: CardLayout or mainContentPanel is null.");
        }
    }

    // Setter for the NavController
    public void setNavController(NavController navController) {
        this.navController = navController;
        initializeUI(); // Call a common init method
    }

    // Setter for the GameController
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
        initializeUI(); // Call a common init method
    }

    // Common method to initialize UI once both controllers are set
    private void initializeUI() {
        // Only proceed if both controllers are available
        if (this.navController != null && this.gameController != null && menuPanel == null) { // Check if already initialized
            // Instantiate non-game panels
            menuPanel = new MuMenuPanel(this.navController);
            setupPanel = new MuGameSetupPanel(this.navController);
            howToPlayPanel = new MuHowToPlayPanel(this.navController);

            // Wrap MuMenuPanel for centering
            JPanel menuWrapper = new JPanel(new GridBagLayout());
            menuWrapper.setOpaque(false);
            menuWrapper.add(menuPanel, new GridBagConstraints());

            // Add non-game panels to the CardLayout
            mainContentPanel.add(menuWrapper, MuConstants.MENU_PANEL);
            mainContentPanel.add(setupPanel, MuConstants.SETUP_PANEL);
            mainContentPanel.add(howToPlayPanel, MuConstants.HOW_TO_PLAY_PANEL);

            // Show the initial panel (Menu)
            cardLayout.show(mainContentPanel, MuConstants.MENU_PANEL);

            // Make the frame visible
            super.pack(); // Pack after adding components
            super.setLocationRelativeTo(null); // Center
            super.setVisible(true);
        } else if (this.navController == null || this.gameController == null) {
             // Controllers not ready yet, do nothing
        } else {
             // Already initialized, do nothing
        }
    }

    public JPanel getContentContainer() {
        return mainContentPanel;
    }
}