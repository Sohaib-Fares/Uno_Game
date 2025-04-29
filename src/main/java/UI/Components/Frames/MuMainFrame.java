package UI.Components.Frames;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import UI.Components.Utils.MuMainBackgroundPanel;

public class MuMainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentContainer; // Panel to hold the switchable views
    private MuMainBackgroundPanel backgroundPanel;

    // Define constants for panel names
    public static final String MENU_PANEL = "MenuPanel";
    public static final String HOW_TO_PLAY_PANEL = "HowToPlayPanel";
    public static final String GAME_SETUP_PANEL = "GameSetupPanel";

    // Add more panel names as needed
    public MuMainFrame() {
        super("JUno"); // Set a title
        super.setSize(900, 900);
        super.setPreferredSize(new Dimension(900, 900));
        super.setMinimumSize(new Dimension(900, 700));
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);

        ImageIcon image = new ImageIcon("src/main/java/UI/Assets/JUNO.png");
        super.setIconImage(image.getImage());

        backgroundPanel = new MuMainBackgroundPanel(); // Keep the background
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(new GridBagLayout()); // Center the content container

        // Create the panel that uses CardLayout
        cardLayout = new CardLayout();
        contentContainer = new JPanel(cardLayout);
        contentContainer.setOpaque(false); // Make it transparent to see the background

        // Create and add the different view panels
        MuMenuPanel menuPanel = new MuMenuPanel(this); // Pass frame reference
        MuHowToPlayPanel howToPlayPanel = new MuHowToPlayPanel(this);
        MuGamePlayPanel gameSetupPanel = new MuGamePlayPanel(this); // Create this
        // panel later

        // --- Wrap MuMenuPanel to allow centering ---
        JPanel menuWrapper = new JPanel(new GridBagLayout()); // Use GridBagLayout to center
        menuWrapper.setOpaque(false); // Wrapper is transparent
        menuWrapper.add(menuPanel, new GridBagConstraints()); // Add menuPanel with default constraints (centers)
        // --- End Wrapper ---

        // Add the wrapper instead of the menuPanel directly
        contentContainer.add(menuWrapper, MENU_PANEL);
        contentContainer.add(howToPlayPanel, HOW_TO_PLAY_PANEL); // Add HowToPlayPanel directly
        contentContainer.add(gameSetupPanel, GAME_SETUP_PANEL);
        // Add other panels here

        // Add the contentContainer to the backgroundPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Allow content container to expand horizontally
        gbc.weighty = 1.0; // Allow content container to expand vertically
        gbc.anchor = GridBagConstraints.CENTER; // Center content container if smaller than cell
        gbc.fill = GridBagConstraints.BOTH; // Let content container fill the cell

        backgroundPanel.add(contentContainer, gbc);

        // Show the initial panel (Menu)
        cardLayout.show(contentContainer, MENU_PANEL);

        super.setLocationByPlatform(false); // Consider removing if causing issues
        super.setVisible(true);
    }

    // Method to switch panels
    public void switchToPanel(String panelName) {
        cardLayout.show(contentContainer, panelName);
    }

    // Optional: Method to get the content container if needed by child panels
    public JPanel getContentContainer() {
        return contentContainer;
    }
}