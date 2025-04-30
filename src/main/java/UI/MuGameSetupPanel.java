package UI;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.Point2D;

import UI.Components.Buttons.MuFilledButton;
import UI.Components.Buttons.MuOutlinedButton;
import UI.Components.Containers.MuLayeredPane;
import UI.Components.Labels.MuLabel;
import UI.Components.Misc.MuBox;
import UI.Components.Misc.MuImageIcon;
import UI.Components.Panels.MuPlayerPanel;
import UI.Constatnts.MuColors;

public class MuGameSetupPanel extends JPanel {

    private JPanel menuPanel;

    public MuGameSetupPanel(MuMainFrame mainFrame) {
        // Setup frame properties
        super.setSize(900, 900);
        // Create background panel with radial gradient

        setLayout(new GridBagLayout());

        // Create the main menu panel
        createMenuPanel(mainFrame);

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

    private void createMenuPanel(MuMainFrame mainFrame) {
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
        JPanel topPanel = createTopPanel(mainFrame);

        // Create and add the bottom panel with BoxLayout
        JPanel bottomPanel = createBottomPanel(mainFrame);

        // Add panels to the main panel
        menuPanel.add(topPanel);
        menuPanel.add(bottomPanel);
    }

    private JPanel createTopPanel(MuMainFrame mainFrame) {
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
        backButton.addActionListener(e -> mainFrame.switchToPanel(MuMainFrame.MENU_PANEL));

        // Create and center the JUNO logo
        MuImageIcon originalIcon = new MuImageIcon("src/main/java/UI/Assets/JUNO.png");
        MuLabel logoLabel;

        if (originalIcon.getIconWidth() == -1) {
            System.err.println("Warning: Could not load image icon at src/main/java/UI/Assets/JUNO.png");
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

    private JPanel createBottomPanel(MuMainFrame mainFrame) {
        // Create bottom panel with BoxLayout Y_AXIS
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Create player section panel with BoxLayout Y_AXIS
        JPanel playerSection = new JPanel();
        playerSection.setLayout(new BoxLayout(playerSection, BoxLayout.Y_AXIS));
        playerSection.setBackground(Color.WHITE);
        playerSection.setMinimumSize(new Dimension(400, 550));
        playerSection.setBorder(BorderFactory.createEmptyBorder(15, 30, 5, 30));
        playerSection.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add player panels with spacing
        MuPlayerPanel player1 = new MuPlayerPanel(MuColors.MuRed, "Player 1 name");
        MuCheckBox player1BotCheck = new MuCheckBox("Bot");
        player1BotCheck.setOpaque(false);
        JPanel player1Row = createPlayerRowPanel(player1, player1BotCheck);
        playerSection.add(player1Row);
        playerSection.add(MuBox.createVerticalStrut(15));

        MuPlayerPanel player2 = new MuPlayerPanel(MuColors.MuBlue, "Player 2 name");
        MuCheckBox player2BotCheck = new MuCheckBox("Bot");
        player2BotCheck.setOpaque(false);
        JPanel player2Row = createPlayerRowPanel(player2, player2BotCheck);
        playerSection.add(player2Row);
        playerSection.add(MuBox.createVerticalStrut(15));

        MuPlayerPanel player3 = new MuPlayerPanel(MuColors.MuGreen, "Player 3 name");
        MuCheckBox player3BotCheck = new MuCheckBox("Bot");
        player3BotCheck.setOpaque(false);
        JPanel player3Row = createPlayerRowPanel(player3, player3BotCheck);
        playerSection.add(player3Row);
        playerSection.add(MuBox.createVerticalStrut(15));

        MuPlayerPanel player4 = new MuPlayerPanel(MuColors.MuBlue, "Player 4 name");
        MuCheckBox player4BotCheck = new MuCheckBox("Bot");
        player4BotCheck.setOpaque(false);
        JPanel player4Row = createPlayerRowPanel(player4, player4BotCheck);
        playerSection.add(player4Row);
        playerSection.add(MuBox.createVerticalStrut(15));

        // Create Add/Remove panel with BorderLayout for left/right alignment
        JPanel addRemovePanel = new JPanel();
        addRemovePanel.setLayout(new BorderLayout());
        addRemovePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addRemovePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        addRemovePanel.setPreferredSize(new Dimension(600, 60));
        addRemovePanel.setBackground(Color.WHITE);

        // Create left button
        MuFilledButton addButton = new MuFilledButton("+  Add Player", MuColors.MuGreen, Color.WHITE, 16, 140, 45);

        // Create right button
        MuFilledButton removeButton = new MuFilledButton("-  Remove", MuColors.MuRed, Color.WHITE, 16, 140, 45);

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
        startGameButton.addActionListener(e -> mainFrame.switchToPanel(MuMainFrame.GAME_PLAY_PANEL));
        startGamePanel.add(startGameButton);

        // Add all sections to bottom panel
        bottomPanel.add(playerSection);
        bottomPanel.add(addRemovePanel);
        bottomPanel.add(startGamePanel);

        return bottomPanel;
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
