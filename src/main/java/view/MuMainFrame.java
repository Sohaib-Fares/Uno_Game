package view;

import javax.swing.*;

import UI.Components.Frames.MuFrame;
import UI.Components.Misc.MuImageIcon;
import UI.Components.Utils.MuMainBackgroundPanel;

import java.awt.*;
import controllers.NavController;
import view.Components.MuConstants;

public class MuMainFrame extends MuFrame {

    private CardLayout cardLayout;
    private JPanel mainContentPanel;
    private NavController navController; // Reference to the controller

    // Child Panels - Declare them but don't instantiate here
    private MuMenuPanel menuPanel;
    private MuGameSetupPanel setupPanel;
    private MuGamePlayPanel gamePanel;
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

        // Don't set visible until components are initialized
        // super.setVisible(true); // Move this or call it after setNavController
        super.setLocationByPlatform(false);
    }

    // Method for the NavController to call
    public void displayPanel(String panelName) {
        if (cardLayout != null && mainContentPanel != null) {
            cardLayout.show(mainContentPanel, panelName);
        } else {
            System.err.println("Error: CardLayout or mainContentPanel not initialized before displayPanel call.");
        }
    }

    // Setter for the NavController - NOW initializes components
    public void setNavController(NavController navController) {
        this.navController = navController;

        // --- Initialize Panels AFTER controller is available ---
        if (this.navController != null) {
            // Instantiate panels, passing the controller
            menuPanel = new MuMenuPanel(this.navController); // Assuming constructor takes NavController
            setupPanel = new MuGameSetupPanel(this.navController); // Assuming constructor takes NavController
            gamePanel = new MuGamePlayPanel(this.navController); // Assuming constructor takes NavController
            howToPlayPanel = new MuHowToPlayPanel(this.navController); // Assuming constructor takes NavController

            // --- Wrap MuMenuPanel to allow centering ---
            JPanel menuWrapper = new JPanel(new GridBagLayout());
            menuWrapper.setOpaque(false);
            menuWrapper.add(menuPanel, new GridBagConstraints());
            // --- End Wrapper ---

            // Add panels to the CardLayout
            mainContentPanel.add(menuWrapper, MuConstants.MENU_PANEL);
            mainContentPanel.add(setupPanel, MuConstants.SETUP_PANEL);
            mainContentPanel.add(gamePanel, MuConstants.GAME_PANEL);
            mainContentPanel.add(howToPlayPanel, MuConstants.HOW_TO_PLAY_PANEL);

            // Show the initial panel (Menu)
            cardLayout.show(mainContentPanel, MuConstants.MENU_PANEL);

            // Now make the frame visible
            super.setVisible(true);

        } else {
            System.err.println("Error: NavController provided to MuMainFrame is null.");
            // Handle error appropriately - maybe show an error message panel
        }
    }

    // Optional: Method to get the content container if needed
    public JPanel getContentContainer() {
        return mainContentPanel;
    }
}