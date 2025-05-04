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
import model.PlayerModel.PlayerConfig;

public class MuGameSetupPanel extends JPanel {

    private static class PlayerRowComponents {
        final MuPlayerPanel playerPanel;
        final MuCheckBox botCheckBox;
        final JPanel rowPanel;

        PlayerRowComponents(MuPlayerPanel playerPanel, MuCheckBox botCheckBox, JPanel rowPanel) {
            this.playerPanel = playerPanel;
            this.botCheckBox = botCheckBox;
            this.rowPanel = rowPanel;
        }
    }

    private JPanel menuPanel;
    private List<PlayerRowComponents> playerRowComponentsList;
    private JPanel playerSection;
    private MuFilledButton addButton;
    private MuFilledButton removeButton;
    private int currentPlayerCount = 2;
    private final int MIN_PLAYERS = 2;
    private final int MAX_PLAYERS = 4;

    public MuGameSetupPanel(NavController navController) {
        super.setSize(900, 900);
        setLayout(new GridBagLayout());
        createMenuPanel(navController);

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
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));

        menuPanel.setPreferredSize(new Dimension(550, 700));
        menuPanel.setMaximumSize(new Dimension(550, 700));
        menuPanel.setMinimumSize(new Dimension(550, 700));

        JPanel topPanel = createTopPanel(navController);
        JPanel bottomPanel = createBottomPanel(navController);

        menuPanel.add(topPanel);
        menuPanel.add(bottomPanel);

        updateButtonStates();
    }

    private JPanel createTopPanel(NavController navController) {
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

        MuLayeredPane layeredPane = new MuLayeredPane();
        layeredPane.setPreferredSize(new Dimension(500, 200));

        MuOutlinedButton backButton = new MuOutlinedButton("\u2190", MuColors.MuYellow, Color.BLACK, 16, 60, 40, 2,
                Color.black);
        backButton.setBounds(15, 15, 60, 40);
        backButton.addActionListener(e -> navController.goBackToMenu());

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

        int logoWidth = 175;
        int logoHeight = 175;
        logoLabel.setBounds((500 - logoWidth + 50) / 2, (200 - logoHeight + 50) / 2, logoWidth, logoHeight);

        layeredPane.add(logoLabel, Integer.valueOf(0));
        layeredPane.add(backButton, Integer.valueOf(1));

        topPanel.setLayout(new BorderLayout());
        topPanel.add(layeredPane, BorderLayout.CENTER);

        return topPanel;
    }

    private JPanel createBottomPanel(NavController navController) {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        playerRowComponentsList = new ArrayList<>();

        playerSection = new JPanel();
        playerSection.setLayout(new BoxLayout(playerSection, BoxLayout.Y_AXIS));
        playerSection.setBackground(Color.WHITE);
        playerSection.setMinimumSize(new Dimension(400, 550));
        playerSection.setBorder(BorderFactory.createEmptyBorder(15, 30, 5, 30));
        playerSection.setAlignmentX(Component.CENTER_ALIGNMENT);

        MuPlayerPanel player1Panel = new MuPlayerPanel(MuColors.MuRed, "Player 1 name");
        MuCheckBox player1BotCheck = new MuCheckBox("Bot");
        player1BotCheck.setOpaque(false);
        JPanel player1Row = createPlayerRowPanel(player1Panel, player1BotCheck);
        playerRowComponentsList.add(new PlayerRowComponents(player1Panel, player1BotCheck, player1Row));

        MuPlayerPanel player2Panel = new MuPlayerPanel(MuColors.MuBlue, "Player 2 name");
        MuCheckBox player2BotCheck = new MuCheckBox("Bot");
        player2BotCheck.setOpaque(false);
        JPanel player2Row = createPlayerRowPanel(player2Panel, player2BotCheck);
        playerRowComponentsList.add(new PlayerRowComponents(player2Panel, player2BotCheck, player2Row));

        MuPlayerPanel player3Panel = new MuPlayerPanel(MuColors.MuGreen, "Player 3 name");
        MuCheckBox player3BotCheck = new MuCheckBox("Bot");
        player3BotCheck.setOpaque(false);
        JPanel player3Row = createPlayerRowPanel(player3Panel, player3BotCheck);
        playerRowComponentsList.add(new PlayerRowComponents(player3Panel, player3BotCheck, player3Row));

        MuPlayerPanel player4Panel = new MuPlayerPanel(MuColors.MuYellow, "Player 4 name");
        MuCheckBox player4BotCheck = new MuCheckBox("Bot");
        player4BotCheck.setOpaque(false);
        JPanel player4Row = createPlayerRowPanel(player4Panel, player4BotCheck);
        playerRowComponentsList.add(new PlayerRowComponents(player4Panel, player4BotCheck, player4Row));

        updatePlayerSectionView();

        JPanel addRemovePanel = new JPanel();
        addRemovePanel.setLayout(new BorderLayout());
        addRemovePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addRemovePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        addRemovePanel.setPreferredSize(new Dimension(600, 60));
        addRemovePanel.setBackground(Color.WHITE);

        addButton = new MuFilledButton("+  Add Player", MuColors.MuGreen, Color.WHITE, 16, 140, 45);
        addButton.addActionListener(e -> addPlayer());

        removeButton = new MuFilledButton("-  Remove", MuColors.MuRed, Color.WHITE, 16, 140, 45);
        removeButton.addActionListener(e -> removePlayer());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 7));
        leftPanel.setOpaque(false);
        leftPanel.add(addButton);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 20, 7));
        rightPanel.setOpaque(false);
        rightPanel.add(removeButton);

        addRemovePanel.add(leftPanel, BorderLayout.WEST);
        addRemovePanel.add(rightPanel, BorderLayout.EAST);

        JPanel startGamePanel = new JPanel();
        startGamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        startGamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGamePanel.setBackground(Color.WHITE);
        startGamePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        MuFilledButton startGameButton = new MuFilledButton("Start Game", MuColors.MuYellow, Color.black, 16, 420, 50,
                1, Color.black);
        startGameButton.addActionListener(e -> {
            List<PlayerConfig> playerConfigs = getPlayerConfigs();
            navController.startGame(playerConfigs);
        });
        startGamePanel.add(startGameButton);

        bottomPanel.add(playerSection);
        bottomPanel.add(addRemovePanel);
        bottomPanel.add(startGamePanel);

        return bottomPanel;
    }

    private void updatePlayerSectionView() {
        playerSection.removeAll();
        for (int i = 0; i < currentPlayerCount; i++) {
            playerSection.add(playerRowComponentsList.get(i).rowPanel);
            if (i < currentPlayerCount - 1 || currentPlayerCount < MAX_PLAYERS) {
                playerSection.add(MuBox.createVerticalStrut(15));
            }
        }
        for (int i = currentPlayerCount; i < MAX_PLAYERS; i++) {
            playerRowComponentsList.get(i).rowPanel.setVisible(false);
        }
        for (int i = 0; i < currentPlayerCount; i++) {
            playerRowComponentsList.get(i).rowPanel.setVisible(true);
        }
        playerSection.revalidate();
        playerSection.repaint();
    }

    private void addPlayer() {
        if (currentPlayerCount < MAX_PLAYERS) {
            currentPlayerCount++;
            updatePlayerSectionView();
            updateButtonStates();
        }
    }

    private void removePlayer() {
        if (currentPlayerCount > MIN_PLAYERS) {
            currentPlayerCount--;
            updatePlayerSectionView();
            updateButtonStates();
        }
    }

    private void updateButtonStates() {
        boolean canAdd = currentPlayerCount < MAX_PLAYERS;
        boolean canRemove = currentPlayerCount > MIN_PLAYERS;

        addButton.setEnabled(canAdd);
        removeButton.setEnabled(canRemove);

        addButton.setBackground(canAdd ? MuColors.MuGreen : Color.LIGHT_GRAY);
        addButton.setForeground(canAdd ? Color.WHITE : Color.DARK_GRAY);

        removeButton.setBackground(canRemove ? MuColors.MuRed : Color.LIGHT_GRAY);
        removeButton.setForeground(canRemove ? Color.WHITE : Color.DARK_GRAY);
    }

    private JPanel createPlayerRowPanel(MuPlayerPanel playerPanel, MuCheckBox checkBox) {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
        rowPanel.setBackground(Color.WHITE);
        rowPanel.setOpaque(true);
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, playerPanel.getPreferredSize().height));

        rowPanel.add(playerPanel, BorderLayout.CENTER);
        rowPanel.add(checkBox, BorderLayout.EAST);
        return rowPanel;
    }

    public List<PlayerConfig> getPlayerConfigs() {
        List<PlayerConfig> configs = new ArrayList<>();
        for (int i = 0; i < currentPlayerCount; i++) {
            PlayerRowComponents components = playerRowComponentsList.get(i);
            String name = components.playerPanel.getPlayerName();
            if (name == null || name.trim().isEmpty()) {
                name = components.playerPanel.getNameField().getPlaceholder();
            }
            boolean isBot = components.botCheckBox.isSelected();
            Color color = components.playerPanel.getPlayerColor();
            configs.add(new PlayerConfig(name, isBot, color));
        }
        return configs;
    }
}
