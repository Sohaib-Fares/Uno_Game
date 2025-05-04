package view;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import UI.MuCheckBox;
import UI.Components.Buttons.MuFilledButton;
import UI.Components.Buttons.MuOutlinedButton;
import UI.Components.Containers.MuLayeredPane;
import UI.Components.Labels.MuLabel;
import UI.Components.Misc.MuBox;
import UI.Components.Misc.MuImageIcon;
import UI.Components.Panels.MuPlayerPanel;
import UI.Constatnts.MuColors;
import controllers.NavController;

public class MuGameSetupPanel extends JPanel {

    private JPanel menuPanel;
    private List<JPanel> playerRows; // Store player row panels (player panel + checkbox)
    private JPanel playerSection; // Panel containing the player rows
    private MuFilledButton addButton;
    private MuFilledButton removeButton;
    private int currentPlayerCount = 2; // Start with 2 players
    private final int MIN_PLAYERS = 2;
    private final int MAX_PLAYERS = 4;

    public MuGameSetupPanel(NavController navController) {
        // Setup frame properties
        super.setSize(900, 900);
        // Create background panel with radial gradient

        setLayout(new GridBagLayout());

        // Create the main menu panel
        createMenuPanel(navController);

        // Add the menu panel to the background using GridBagLayout for centering
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(menuPanel, gbc);
        setOpaque(false);
    }

    private void createMenuPanel(NavController navController) {
        // Main panel with BoxLayout Y_AXIS
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));

        // Set fixed size for menu panel
        menuPanel.setPreferredSize(new Dimension(550, 700));
        menuPanel.setMaximumSize(new Dimension(550, 700));
        menuPanel.setMinimumSize(new Dimension(550, 700));

        // Create and add the top panel with BorderLayout
        JPanel topPanel = createTopPanel(navController);

        // Create and add the bottom panel with BoxLayout
        JPanel bottomPanel = createBottomPanel(navController);

        // Add panels to the main panel
        menuPanel.add(topPanel);
        menuPanel.add(bottomPanel);

        // Initial update for button states based on starting player count
        updateButtonStates();
    }

    private JPanel createTopPanel(NavController navController) {
        // Create top panel with gradient background
        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();

                g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

                Point2D center = new Point2D.Float(width / 2f, height / 2f);
                float radius = 500f;
                float[] dist = { 0.0f, 0.5f, 1.0f };
                Color[] colors = { MuColors.MuRed.darker(), MuColors.MuRed.brighter(),
                        MuColors.MuRed.brighter().brighter() };
                RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);

                g2d.setPaint(p);
                g2d.fillRect(0, 0, width, height);
            }
        };

        topPanel.setPreferredSize(new Dimension(500, 200));

        // Use a layered approach - one layer for the logo, one for the button
        MuLayeredPane layeredPane = new MuLayeredPane();
        layeredPane.setPreferredSize(new Dimension(500, 200));

        // Create back button
        MuOutlinedButton backButton = new MuOutlinedButton("\u2190", MuColors.MuYellow, Color.BLACK, 16, 60, 40, 2,
                Color.black);
        backButton.setBounds(15, 15, 60, 40); // Position at top-left with some margin
        backButton.addActionListener(e -> navController.goBackToMenu());

        // Create and center the JUNO logo
        MuImageIcon originalIcon = new MuImageIcon("src/main/resources/assets/JUNO.png");
        MuLabel logoLabel;

        if (originalIcon.getIconWidth() == -1) {
            System.err.println("Warning: Could not load image icon at src/main/resources/assets/JUNO.png");
            logoLabel = new MuLabel("JUNO Logo Missing");
            logoLabel.setForeground(Color.WHITE);
        } else {
            int desiredWidth = 175;
            int desiredHeight = 175;
            Image scaledImage = originalIcon.getImage().getScaledInstance(desiredWidth, desiredHeight,
                    Image.SCALE_SMOOTH);
            MuImageIcon scaledIcon = new MuImageIcon(scaledImage);
            logoLabel = new MuLabel(scaledIcon);
        }

        // Center logo in the panel
        int logoWidth = 175;
        int logoHeight = 175;
        logoLabel.setBounds((500 - logoWidth + 50) / 2, (200 - logoHeight + 50) / 2, logoWidth, logoHeight);

        // Add components to layered pane with different z-order
        layeredPane.add(logoLabel, Integer.valueOf(0)); // Background layer
        layeredPane.add(backButton, Integer.valueOf(1)); // Foreground layer

        topPanel.setLayout(new BorderLayout());
        topPanel.add(layeredPane, BorderLayout.CENTER);

        return topPanel;
    }

    private JPanel createBottomPanel(NavController navController) {
        // Create bottom panel with BoxLayout Y_AXIS
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Initialize playerRows list
        playerRows = new ArrayList<>();

        // Create player section panel with BoxLayout Y_AXIS
        // Store reference to playerSection
        playerSection = new JPanel();
        playerSection.setLayout(new BoxLayout(playerSection, BoxLayout.Y_AXIS));
        playerSection.setBackground(Color.WHITE);
        playerSection.setMinimumSize(new Dimension(400, 550));
        playerSection.setBorder(BorderFactory.createEmptyBorder(15, 30, 5, 30));
        playerSection.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Create all player rows and add to list ---
        MuPlayerPanel player1 = new MuPlayerPanel(MuColors.MuRed, "Player 1 name");
        MuCheckBox player1BotCheck = new MuCheckBox("Bot");
        player1BotCheck.setOpaque(false);
        JPanel player1Row = createPlayerRowPanel(player1, player1BotCheck);
        playerRows.add(player1Row); // Add to list

        MuPlayerPanel player2 = new MuPlayerPanel(MuColors.MuBlue, "Player 2 name");
        MuCheckBox player2BotCheck = new MuCheckBox("Bot");
        player2BotCheck.setOpaque(false);
        JPanel player2Row = createPlayerRowPanel(player2, player2BotCheck);
        playerRows.add(player2Row); // Add to list

        MuPlayerPanel player3 = new MuPlayerPanel(MuColors.MuGreen, "Player 3 name");
        MuCheckBox player3BotCheck = new MuCheckBox("Bot");
        player3BotCheck.setOpaque(false);
        JPanel player3Row = createPlayerRowPanel(player3, player3BotCheck);
        playerRows.add(player3Row); // Add to list

        MuPlayerPanel player4 = new MuPlayerPanel(MuColors.MuYellow, "Player 4 name"); // Changed color for uniqueness
        MuCheckBox player4BotCheck = new MuCheckBox("Bot");
        player4BotCheck.setOpaque(false);
        JPanel player4Row = createPlayerRowPanel(player4, player4BotCheck);
        playerRows.add(player4Row); // Add to list

        // --- Update player section view initially ---
        updatePlayerSectionView(); // Add initial players to the view

        // Create Add/Remove panel with BorderLayout for left/right alignment
        JPanel addRemovePanel = new JPanel();
        addRemovePanel.setLayout(new BorderLayout());
        addRemovePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addRemovePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        addRemovePanel.setPreferredSize(new Dimension(600, 60));
        addRemovePanel.setBackground(Color.WHITE);

        // Create left button - Store reference
        addButton = new MuFilledButton("+  Add Player", MuColors.MuGreen, Color.WHITE, 16, 140, 45);
        addButton.addActionListener(e -> addPlayer()); // Add listener

        // Create right button - Store reference
        removeButton = new MuFilledButton("-  Remove", MuColors.MuRed, Color.WHITE, 16, 140, 45);
        removeButton.addActionListener(e -> removePlayer()); // Add listener

        // Create left and right panels to hold buttons
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 7));
        leftPanel.setOpaque(false);
        leftPanel.add(addButton);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 20, 7));
        rightPanel.setOpaque(false);
        rightPanel.add(removeButton);

        // Add panels to the main panel using BorderLayout
        addRemovePanel.add(leftPanel, BorderLayout.WEST);
        addRemovePanel.add(rightPanel, BorderLayout.EAST);

        // Create Start Game panel with FlowLayout
        JPanel startGamePanel = new JPanel();
        startGamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        startGamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGamePanel.setBackground(Color.WHITE);
        startGamePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Add Start Game button
        MuFilledButton startGameButton = new MuFilledButton("Start Game", MuColors.MuYellow, Color.black, 16, 420, 50,
                1, Color.black);
        startGameButton.addActionListener(e -> navController.showGamePlay());
        startGamePanel.add(startGameButton);

        // Add all sections to bottom panel
        bottomPanel.add(playerSection);
        bottomPanel.add(addRemovePanel);
        bottomPanel.add(startGamePanel);

        return bottomPanel;
    }

    // Method to update the visible player rows in the playerSection
    private void updatePlayerSectionView() {
        playerSection.removeAll(); // Clear current rows
        for (int i = 0; i < currentPlayerCount; i++) {
            playerSection.add(playerRows.get(i));
            // Add spacing only if it's not the last player row being added in this update
            if (i < currentPlayerCount - 1 || currentPlayerCount < MAX_PLAYERS) {
                playerSection.add(MuBox.createVerticalStrut(15));
            }
        }
        playerSection.revalidate();
        playerSection.repaint();
    }

    // Method to handle adding a player
    private void addPlayer() {
        if (currentPlayerCount < MAX_PLAYERS) {
            currentPlayerCount++;
            updatePlayerSectionView();
            updateButtonStates();
        }
    }

    // Method to handle removing a player
    private void removePlayer() {
        if (currentPlayerCount > MIN_PLAYERS) {
            currentPlayerCount--;
            updatePlayerSectionView();
            updateButtonStates();
        }
    }

    // Method to update the enabled state and appearance of add/remove buttons
    private void updateButtonStates() {
        boolean canAdd = currentPlayerCount < MAX_PLAYERS;
        boolean canRemove = currentPlayerCount > MIN_PLAYERS;

        addButton.setEnabled(canAdd);
        removeButton.setEnabled(canRemove);

        // Change appearance when disabled
        addButton.setBackground(canAdd ? MuColors.MuGreen : Color.LIGHT_GRAY);
        addButton.setForeground(canAdd ? Color.WHITE : Color.DARK_GRAY);

        removeButton.setBackground(canRemove ? MuColors.MuRed : Color.LIGHT_GRAY);
        removeButton.setForeground(canRemove ? Color.WHITE : Color.DARK_GRAY);
    }

    private JPanel createPlayerRowPanel(MuPlayerPanel playerPanel, MuCheckBox checkBox) {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 0)); // BorderLayout with horizontal gap
        rowPanel.setBackground(Color.WHITE); // Match background
        rowPanel.setOpaque(true);
        // Ensure the row takes the width but doesn't stretch vertically unnecessarily
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, playerPanel.getPreferredSize().height));

        rowPanel.add(playerPanel, BorderLayout.CENTER);
        rowPanel.add(checkBox, BorderLayout.EAST);
        return rowPanel;
    }

}
