package AppTools.UI.Screens;

import AppTools.CardModel.*;
import AppTools.UI.Components.MaterialPanel;
import AppTools.UI.Components.UnoCardComponent;
import AppTools.UI.UnoTheme;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HowToPlayScreen extends JPanel {

    public HowToPlayScreen(ActionListener backActionListener) {
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
        MaterialPanel contentPanel = new MaterialPanel(20, 10, Color.WHITE, true);
        contentPanel.setLayout(new MigLayout("wrap, insets 0, fillx", "[grow]", "[]0[]"));

        // Add header panel
        JPanel headerPanel = createHeaderPanel(backActionListener);
        contentPanel.add(headerPanel, "growx, height 80!, wrap");

        // Create rules content
        JPanel rulesPanel = createRulesPanel();
        JScrollPane scrollPane = new JScrollPane(rulesPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        contentPanel.add(scrollPane, "grow, push");

        // Add content panel to background
        backgroundPanel.add(contentPanel, new GridBagConstraints());
        contentPanel.setPreferredSize(new Dimension(700, 500));
    }

    private JPanel createHeaderPanel(ActionListener backActionListener) {
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
        JLabel titleLabel = new JLabel("How To Play");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // UNO logo
        JLabel logoLabel = new JLabel("UNO");
        logoLabel.setFont(new Font("Impact", Font.BOLD, 36));
        logoLabel.setForeground(Color.WHITE);
        headerPanel.add(logoLabel);

        return headerPanel;
    }

    private JPanel createRulesPanel() {
        JPanel rulesPanel = new JPanel();
        rulesPanel.setLayout(new MigLayout("wrap, fillx, insets 20", "[grow]", "[]20[]20[]20[]20[]"));
        rulesPanel.setOpaque(false);

        // Objective section
        rulesPanel.add(createSectionTitle("Objective"), "growx");
        rulesPanel.add(createSectionContent(
                "The goal of UNO is to be the first player to get rid of all the cards in your hand. " +
                        "The first player to play all of their cards wins the round."),
                "growx");

        // Setup section
        rulesPanel.add(createSectionTitle("Setup"), "growx");
        rulesPanel.add(createSectionContent(
                "Each player is dealt 7 cards at the beginning of the game. " +
                        "The remaining cards form the draw pile. " +
                        "The top card from the draw pile is turned over to start the discard pile."),
                "growx");

        // Gameplay section
        rulesPanel.add(createSectionTitle("Gameplay"), "growx");
        rulesPanel.add(createSectionContent(
                "On your turn, you must match a card from your hand to the card on the discard pile, " +
                        "either by number, color, or symbol. " +
                        "If you don't have a playable card, you must draw a card from the draw pile. " +
                        "If the card you drew can be played, you may play it immediately. " +
                        "Otherwise, play passes to the next player."),
                "growx");

        // Card Types section
        rulesPanel.add(createSectionTitle("Card Types"), "growx");

        // Create card examples panel
        JPanel cardExamplesPanel = new JPanel(new MigLayout("wrap 4, insets 0, center", "[]30[]30[]30[]", "[]20[]"));
        cardExamplesPanel.setOpaque(false);

        // Add number card example
        addCardExample(cardExamplesPanel,
                new NumberedCard(CardColorEnum.RED, 5),
                "Number cards are played matching color or number");

        // Add skip card example
        addCardExample(cardExamplesPanel,
                new ActionCard(CardTypeEnum.SKIP, CardColorEnum.YELLOW),
                "Skip cards make the next player lose their turn");

        // Add reverse card example
        addCardExample(cardExamplesPanel,
                new ActionCard(CardTypeEnum.REVERSE, CardColorEnum.GREEN),
                "Reverse cards change the direction of play");

        // Add draw two card example
        addCardExample(cardExamplesPanel,
                new ActionCard(CardTypeEnum.DRAW_TWO, CardColorEnum.BLUE),
                "Draw Two cards make the next player draw 2 cards and lose their turn");

        // Add wild cards
        addCardExample(cardExamplesPanel,
                new WildCard(CardTypeEnum.WILD_COLOR, null),
                "Wild cards can be played on any color and let you choose the next color");

        // Add wild draw four card example
        addCardExample(cardExamplesPanel,
                new WildCard(CardTypeEnum.WILD_DRAW_FOUR, null),
                "Wild Draw Four cards work like Wild cards but make the next player draw 4 cards");

        rulesPanel.add(cardExamplesPanel, "growx");

        // Winning section
        rulesPanel.add(createSectionTitle("Winning"), "growx");
        rulesPanel.add(createSectionContent(
                "The first player to get rid of all their cards wins the game. " +
                        "When you have only one card left, you must say 'UNO!' " +
                        "If you don't say 'UNO' and another player catches you before the next player begins their turn, "
                        +
                        "you must draw two cards as a penalty."),
                "growx");

        return rulesPanel;
    }

    private JLabel createSectionTitle(String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(UnoTheme.UNO_RED);
        return titleLabel;
    }

    private JTextArea createSectionContent(String content) {
        JTextArea contentArea = new JTextArea(content);
        contentArea.setWrapStyleWord(true);
        contentArea.setLineWrap(true);
        contentArea.setEditable(false);
        contentArea.setFocusable(false);
        contentArea.setOpaque(false);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));

        return contentArea;
    }

    private void addCardExample(JPanel container, AbstractCard card, String description) {
        JPanel cardPanel = new JPanel(new MigLayout("wrap, insets 0, center", "[center]", "[]10[]"));
        cardPanel.setOpaque(false);

        UnoCardComponent cardComponent = new UnoCardComponent(card);
        cardComponent.setPreferredSize(new Dimension(100, 150));
        cardPanel.add(cardComponent);

        JTextArea descLabel = new JTextArea(description);
        descLabel.setWrapStyleWord(true);
        descLabel.setLineWrap(true);
        descLabel.setEditable(false);
        descLabel.setOpaque(false);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setPreferredSize(new Dimension(120, 80));

        cardPanel.add(descLabel);

        container.add(cardPanel);
    }
}