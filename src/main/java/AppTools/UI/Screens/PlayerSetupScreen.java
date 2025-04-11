package AppTools.UI.Screens;

import AppTools.UI.Components.MaterialButton;
import AppTools.UI.Components.MaterialPanel;
import AppTools.UI.UnoTheme;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;
// Add these imports
import org.kordamp.ikonli.materialdesign2.MaterialDesignM;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class PlayerSetupScreen extends JPanel {
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;

    private final JPanel playersContainer = new JPanel(new MigLayout("wrap 1, fillx, insets 0", "[grow]", "[]10[]"));
    private final List<PlayerInputPanel> playerInputPanels = new ArrayList<>();
    private final MaterialButton startGameButton;
    private final ActionListener backActionListener;
    private final ActionListener startGameActionListener;
    private final MaterialPanel contentPanel;

    public PlayerSetupScreen(ActionListener backActionListener, ActionListener startGameActionListener) {
        this.backActionListener = backActionListener;
        this.startGameActionListener = startGameActionListener;

        setLayout(new BorderLayout());

        // Create gradient background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                // Create gradient from dark red to medium red
                Color darkRed = new Color(180, 0, 0);
                Color mediumRed = UnoTheme.UNO_RED;

                RadialGradientPaint paint = new RadialGradientPaint(
                        new Point(width / 2, height / 2),
                        Math.max(width, height) / 2,
                        new float[] { 0.0f, 1.0f },
                        new Color[] { mediumRed, darkRed });

                g2.setPaint(paint);
                g2.fill(new Rectangle(0, 0, width, height));

                g2.dispose();
            }
        };
        add(backgroundPanel, BorderLayout.CENTER);
        // Set up content panel
        backgroundPanel.setLayout(new GridBagLayout());
        contentPanel = new MaterialPanel(20, 10, Color.WHITE, true);
        contentPanel.setLayout(new MigLayout("wrap, insets 0, fillx", "[grow]", "[]20[][]"));
        contentPanel.setLayout(new MigLayout("wrap, insets 0, fillx", "[grow]", "[]20[][]"));

        // Add header panel
        JPanel headerPanel = createHeaderPanel();
        contentPanel.add(headerPanel, "growx, height 100!, wrap");

        // Set up players container
        playersContainer.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(playersContainer);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        contentPanel.add(scrollPane, "grow, h 250::250, wrap");

        // Add initial players
        addPlayer("Player 1", true, UnoTheme.UNO_RED);
        addPlayer("Player 2", false, UnoTheme.UNO_BLUE);

        // Add buttons panel
        JPanel buttonsPanel = new JPanel(new MigLayout("insets 0, center", "[]push[]", "[]"));
        buttonsPanel.setOpaque(false);

        JPanel controlButtonsPanel = new JPanel(new MigLayout("insets 0", "[]10[]", "[]"));
        controlButtonsPanel.setOpaque(false);

        MaterialButton addPlayerButton = new MaterialButton("", UnoTheme.UNO_GREEN);
        addPlayerButton.setIcon(FontIcon.of(MaterialDesignP.PLUS_CIRCLE, 20, Color.WHITE));
        addPlayerButton.addActionListener(e -> {
            if (playerInputPanels.size() < MAX_PLAYERS) {
                addPlayer("Player " + (playerInputPanels.size() + 1), false, getNextColor());
                updateButtons();
            }
        });
        controlButtonsPanel.add(addPlayerButton, "w 50!, h 50!");

        MaterialButton removePlayerButton = new MaterialButton("", UnoTheme.UNO_RED);
        // Change this line
        removePlayerButton.setIcon(FontIcon.of(MaterialDesignM.MINUS_CIRCLE_OUTLINE, 20, Color.WHITE));
        removePlayerButton.addActionListener(e -> {
            if (playerInputPanels.size() > MIN_PLAYERS) {
                playersContainer.remove(playerInputPanels.get(playerInputPanels.size() - 1));
                playerInputPanels.remove(playerInputPanels.size() - 1);
                playersContainer.revalidate();
                playersContainer.repaint();
                updateButtons();
            }
        });
        controlButtonsPanel.add(removePlayerButton, "w 50!, h 50!");

        buttonsPanel.add(controlButtonsPanel);

        startGameButton = new MaterialButton("Start Game", UnoTheme.UNO_YELLOW);
        startGameButton.addActionListener(startGameActionListener);
        buttonsPanel.add(startGameButton, "w 150!");

        contentPanel.add(buttonsPanel, "growx");

        // Add content panel to background
        backgroundPanel.add(contentPanel, new GridBagConstraints());
        contentPanel.setPreferredSize(new Dimension(500, 550));

        // Initialize button states
        updateButtons();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                        0, 0, UnoTheme.UNO_RED,
                        getWidth(), getHeight(), new Color(180, 0, 0));

                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2.dispose();
            }
        };
        headerPanel.setLayout(new MigLayout("insets 20", "[]push[]push[]", "[]"));

        // Back button
        JButton backButton = new JButton();
        backButton.setIcon(FontIcon.of(MaterialDesignA.ARROW_LEFT, 24, Color.WHITE));
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(backActionListener);
        headerPanel.add(backButton);

        // Title label
        JLabel titleLabel = new JLabel("Player Setup");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // UNO logo
        JLabel logoLabel = new JLabel("UNO");
        logoLabel.setFont(new Font("Impact", Font.BOLD, 36));
        logoLabel.setForeground(Color.WHITE);
        headerPanel.add(logoLabel);

        return headerPanel;
    }

    private void addPlayer(String defaultName, boolean isHuman, Color avatarColor) {
        PlayerInputPanel playerPanel = new PlayerInputPanel(defaultName, isHuman, avatarColor);
        playerInputPanels.add(playerPanel);
        playersContainer.add(playerPanel, "growx");
        playersContainer.revalidate();
        playersContainer.repaint();
    }

    private Color getNextColor() {
        int index = playerInputPanels.size() % 4;
        return switch (index) {
            case 0 -> UnoTheme.UNO_RED;
            case 1 -> UnoTheme.UNO_BLUE;
            case 2 -> UnoTheme.UNO_GREEN;
            case 3 -> UnoTheme.UNO_YELLOW;
            default -> UnoTheme.UNO_RED;
        };
    }

    private void updateButtons() {
        boolean canAdd = playerInputPanels.size() < MAX_PLAYERS;
        boolean canRemove = playerInputPanels.size() > MIN_PLAYERS;
        boolean canStart = validatePlayerNames();

        // Update buttons based on state
        for (Component c : ((JPanel) ((JPanel) contentPanel.getComponent(2)).getComponent(0)).getComponents()) {
            if (c instanceof JPanel) {
                for (Component btn : ((JPanel) c).getComponents()) {
                    if (btn instanceof MaterialButton) {
                        MaterialButton mb = (MaterialButton) btn;
                        if (mb.getText().isEmpty()) { // It's a + or - button
                            if (mb.getIcon() instanceof FontIcon) {
                                FontIcon icon = (FontIcon) mb.getIcon();
                                if (icon.getIkon() == MaterialDesignP.PLUS_CIRCLE) {
                                    mb.setEnabled(canAdd);
                                    // Change this line
                                } else if (icon.getIkon() == MaterialDesignM.MINUS_CIRCLE_OUTLINE) {
                                    mb.setEnabled(canRemove);
                                }
                            }
                        }
                    }
                }
            } else if (c instanceof MaterialButton && "Start Game".equals(((MaterialButton) c).getText())) {
                c.setEnabled(canStart);
            }
        }
        startGameButton.setEnabled(canStart);
    }

    private boolean validatePlayerNames() {
        for (PlayerInputPanel panel : playerInputPanels) {
            if (panel.getPlayerName().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public List<PlayerSetupData> getPlayerData() {
        List<PlayerSetupData> result = new ArrayList<>();
        for (PlayerInputPanel panel : playerInputPanels) {
            result.add(new PlayerSetupData(
                    panel.getPlayerName(),
                    panel.isHumanPlayer()));
        }
        return result;
    }

    // Inner class for player input panel
    private class PlayerInputPanel extends MaterialPanel {
        private final JTextField nameField;
        private final JCheckBox humanCheckbox;
        private final Color avatarColor;

        public PlayerInputPanel(String defaultName, boolean isHuman, Color avatarColor) {
            super(12, 3, UnoTheme.SURFACE_VARIANT, true);
            this.avatarColor = avatarColor;

            setLayout(new MigLayout("insets 15", "[]15[grow]15[]", "[]"));

            // Avatar panel
            JPanel avatarPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Draw avatar circle
                    g2.setColor(PlayerInputPanel.this.avatarColor);
                    g2.fill(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));

                    // Draw user icon
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Arial", Font.BOLD, 20));
                    FontMetrics fm = g2.getFontMetrics();
                    String userInitial = defaultName.isEmpty() ? "P" : defaultName.substring(0, 1).toUpperCase();
                    g2.drawString(userInitial, (getWidth() - fm.stringWidth(userInitial)) / 2,
                            (getHeight() + fm.getAscent()) / 2 - fm.getDescent() / 2);

                    g2.dispose();
                }
            };
            avatarPanel.setPreferredSize(new Dimension(40, 40));
            add(avatarPanel, "w 40!, h 40!");

            // Name field
            nameField = new JTextField(defaultName);
            nameField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UnoTheme.ON_SURFACE_VARIANT),
                    new EmptyBorder(5, 10, 5, 10)));
            nameField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(javax.swing.event.DocumentEvent e) {
                    updateButtons();
                }

                @Override
                public void removeUpdate(javax.swing.event.DocumentEvent e) {
                    updateButtons();
                }

                @Override
                public void changedUpdate(javax.swing.event.DocumentEvent e) {
                    updateButtons();
                }
            });
            add(nameField, "growx");

            // Human/Bot checkbox
            humanCheckbox = new JCheckBox("Human");
            humanCheckbox.setSelected(isHuman);
            humanCheckbox.setOpaque(false);
            add(humanCheckbox);
        }

        public String getPlayerName() {
            return nameField.getText();
        }

        public boolean isHumanPlayer() {
            return humanCheckbox.isSelected();
        }
    }

    // Data class to hold player setup information
    public static class PlayerSetupData {
        private final String name;
        private final boolean isHuman;

        public PlayerSetupData(String name, boolean isHuman) {
            this.name = name;
            this.isHuman = isHuman;
        }

        public String getName() {
            return name;
        }

        public boolean isHuman() {
            return isHuman;
        }
    }
}