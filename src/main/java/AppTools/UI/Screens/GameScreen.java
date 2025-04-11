package AppTools.UI.Screens;

import AppTools.CardModel.*;
import AppTools.Deck.Deck;
import AppTools.Game.Direction;
import AppTools.Game.GameService;
import AppTools.PlayerModel.BotPlayer;
import AppTools.PlayerModel.HumanPlayer;
import AppTools.PlayerModel.Player;
import AppTools.UI.Components.MaterialButton;
import AppTools.UI.Components.MaterialPanel;
import AppTools.UI.Components.UnoCardComponent;
import AppTools.UI.UnoTheme;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignM;
import org.kordamp.ikonli.swing.FontIcon;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GameScreen extends JPanel {
    private final GameService gameService;
    private final List<Player> players;
    private final Deck deck;
    private final ActionListener exitActionListener;

    private Player currentPlayer;
    private AbstractCard currentCard;
    private Direction gameDirection = Direction.CLOCKWISE;
    private int currentPlayerIndex = 0;

    // UI Components
    private final MaterialPanel contentPanel;
    private final JPanel gameAreaPanel;
    private final JPanel handPanel;
    private JPanel discardPilePanel;
    private JPanel playerInfoPanel;
    private JLabel directionLabel;

    // Cards in hand
    private final List<UnoCardComponent> handCardComponents = new ArrayList<>();
    private UnoCardComponent currentCardComponent;

    // Game information
    private boolean isGameRunning = false;
    private boolean isHumanPlayerTurn = false;
    private boolean isColorSelectionActive = false;

    public GameScreen(GameService gameService, List<Player> players, Deck deck, ActionListener exitActionListener) {
        this.gameService = gameService;
        this.players = players;
        this.deck = deck;
        this.exitActionListener = exitActionListener;

        setLayout(new BorderLayout());

        // Create gradient background
        JPanel backgroundPanel = createGradientBackground();
        add(backgroundPanel, BorderLayout.CENTER);

        // Setup content layout
        backgroundPanel.setLayout(new GridBagLayout());
        contentPanel = new MaterialPanel(20, 10, Color.WHITE, true);
        contentPanel.setLayout(new MigLayout("wrap, insets 0, fill", "[grow]", "[80!][30!][grow][180!]"));

        // Add header panel
        JPanel headerPanel = createHeaderPanel();
        contentPanel.add(headerPanel, "growx, height 80!");

        // Direction indicator
        JPanel directionPanel = createDirectionPanel();
        contentPanel.add(directionPanel, "growx");

        // Game area with opponents and discard pile
        gameAreaPanel = createGameAreaPanel();
        contentPanel.add(gameAreaPanel, "grow");

        // Player hand panel at the bottom
        handPanel = createHandPanel();
        contentPanel.add(handPanel, "growx, height 180!");

        // Add content panel to background
        backgroundPanel.add(contentPanel, new GridBagConstraints());
        contentPanel.setPreferredSize(new Dimension(900, 650));
    }

    private JPanel createGradientBackground() {
        return new JPanel() {
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
        backButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to quit the game?",
                    "Quit Game",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                exitActionListener.actionPerformed(e);
            }
        });
        headerPanel.add(backButton);

        // Title label
        JLabel titleLabel = new JLabel("UNO Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Player turn indicator
        playerInfoPanel = new JPanel();
        playerInfoPanel.setOpaque(false);
        playerInfoPanel.setLayout(new MigLayout("insets 0", "[]5[]", "[]"));

        JLabel currentPlayerLabel = new JLabel("Current Player: ");
        currentPlayerLabel.setForeground(Color.WHITE);
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        playerInfoPanel.add(currentPlayerLabel);

        JLabel playerNameLabel = new JLabel("");
        playerNameLabel.setForeground(Color.WHITE);
        playerNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        playerInfoPanel.add(playerNameLabel);

        headerPanel.add(playerInfoPanel);

        return headerPanel;
    }

    private JPanel createDirectionPanel() {
        JPanel directionPanel = new MaterialPanel(10, 2, new Color(240, 240, 240), true);
        directionPanel.setLayout(new MigLayout("insets 5", "[center]", "[]"));

        directionLabel = new JLabel("Direction: Clockwise");
        directionLabel.setIcon(FontIcon.of(MaterialDesignA.ARROW_RIGHT_BOLD_CIRCLE, 16, UnoTheme.UNO_RED));
        directionPanel.add(directionLabel, "center");

        return directionPanel;
    }

    private JPanel createGameAreaPanel() {
        JPanel gameArea = new JPanel();
        gameArea.setOpaque(false);
        gameArea.setLayout(new MigLayout("insets 0, fill", "[grow]", "[60%][40%]"));

        // Opponents area
        JPanel opponentsPanel = new MaterialPanel(15, 5, new Color(245, 245, 245), true);
        opponentsPanel.setLayout(new MigLayout("insets 10, wrap 3", "[grow,fill][grow,fill][grow,fill]", "[]"));
        gameArea.add(opponentsPanel, "grow");

        // Discard pile and deck area
        JPanel centerPanel = new MaterialPanel(15, 5, new Color(240, 240, 240), true);
        centerPanel.setLayout(new MigLayout("insets 10", "[50%,center][50%,center]", "[grow]"));

        // Deck panel
        JPanel deckPanel = new JPanel(new BorderLayout());
        deckPanel.setOpaque(false);
        UnoCardComponent deckCard = new UnoCardComponent();
        deckCard.setFaceDown(true);
        deckPanel.add(deckCard, BorderLayout.CENTER);

        JLabel deckLabel = new JLabel("Draw Pile");
        deckLabel.setHorizontalAlignment(SwingConstants.CENTER);
        deckPanel.add(deckLabel, BorderLayout.SOUTH);

        centerPanel.add(deckPanel, "grow");

        // Discard pile panel
        discardPilePanel = new JPanel(new BorderLayout());
        discardPilePanel.setOpaque(false);
        currentCardComponent = new UnoCardComponent();
        discardPilePanel.add(currentCardComponent, BorderLayout.CENTER);

        JLabel discardLabel = new JLabel("Discard Pile");
        discardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        discardPilePanel.add(discardLabel, BorderLayout.SOUTH);

        centerPanel.add(discardPilePanel, "grow");

        gameArea.add(centerPanel, "grow");

        return gameArea;
    }

    private JPanel createHandPanel() {
        MaterialPanel panel = new MaterialPanel(15, 5, new Color(245, 245, 245), true);
        panel.setLayout(new MigLayout("insets 10", "[30%][70%,fill]", "[grow]"));

        // Player info and action buttons
        JPanel playerActionsPanel = new JPanel();
        playerActionsPanel.setOpaque(false);
        playerActionsPanel.setLayout(new MigLayout("wrap", "[]", "[]10[]push[]"));

        // Player avatar and name
        JPanel playerIdentityPanel = new JPanel();
        playerIdentityPanel.setOpaque(false);
        playerIdentityPanel.setLayout(new MigLayout("insets 0", "[]5[]", "[]"));

        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw avatar circle
                g2.setColor(UnoTheme.UNO_RED);
                g2.fill(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));

                // Draw user icon
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                String userInitial = "P";
                g2.drawString(userInitial, (getWidth() - fm.stringWidth(userInitial)) / 2,
                        (getHeight() + fm.getAscent()) / 2 - fm.getDescent() / 2);

                g2.dispose();
            }
        };
        avatarPanel.setPreferredSize(new Dimension(40, 40));
        playerIdentityPanel.add(avatarPanel, "w 40!, h 40!");

        JLabel playerNameLabel = new JLabel("Your Hand");
        playerNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playerIdentityPanel.add(playerNameLabel);

        playerActionsPanel.add(playerIdentityPanel);

        // Action buttons
        MaterialButton drawCardButton = new MaterialButton("Draw Card", UnoTheme.UNO_BLUE);
        drawCardButton.setIcon(FontIcon.of(MaterialDesignC.CARDS, 18, Color.WHITE));
        drawCardButton.addActionListener(e -> drawCard());
        playerActionsPanel.add(drawCardButton, "growx");

        MaterialButton sayUnoButton = new MaterialButton("Say UNO!", UnoTheme.UNO_YELLOW);
        sayUnoButton.setIcon(FontIcon.of(MaterialDesignM.MICROPHONE, 18, Color.BLACK));
        sayUnoButton.setForeground(Color.BLACK);
        playerActionsPanel.add(sayUnoButton, "growx");

        panel.add(playerActionsPanel, "growy");

        // Cards panel (scrollable)
        JPanel cardsContainer = new JPanel();
        cardsContainer.setOpaque(false);
        cardsContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));

        JScrollPane scrollPane = new JScrollPane(cardsContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        panel.add(scrollPane, "grow");

        return panel;
    }

    public void startGame() {
        isGameRunning = true;

        // Set up the current card and player
        if (currentCard == null) {
            // The first card should already be set in the discard pile
            currentCard = deck.drawCard();
            while (currentCard instanceof ActionCard || currentCard instanceof WildCard) {
                currentCard = deck.drawCard();
            }
        }

        currentCardComponent.setCard(currentCard);

        // Initialize players area
        initializeOpponentsPanel();

        // Assign the human player
        for (Player player : players) {
            if (player instanceof HumanPlayer) {
                currentPlayer = player;
                break;
            }
        }

        updatePlayerInfo();
        updateDirectionIndicator();
        updateHand();

        // Game loop will be handled by the UI interaction
        isHumanPlayerTurn = true;
    }

    private void initializeOpponentsPanel() {
        JPanel opponentsPanel = (JPanel) ((JPanel) gameAreaPanel.getComponent(0));
        opponentsPanel.removeAll();

        for (Player player : players) {
            if (player instanceof BotPlayer) {
                opponentsPanel.add(createOpponentPanel(player), "grow");
            }
        }

        opponentsPanel.revalidate();
        opponentsPanel.repaint();
    }

    private JPanel createOpponentPanel(Player player) {
        JPanel panel = new MaterialPanel(10, 3, new Color(250, 250, 250), true);
        panel.setLayout(new MigLayout("insets 10, wrap", "[center]", "[]5[]10[]"));

        // Avatar
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw avatar circle
                g2.setColor(UnoTheme.UNO_BLUE);
                g2.fill(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));

                // Draw user icon
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                String userInitial = player.getName().substring(0, 1).toUpperCase();
                g2.drawString(userInitial, (getWidth() - fm.stringWidth(userInitial)) / 2,
                        (getHeight() + fm.getAscent()) / 2 - fm.getDescent() / 2);

                g2.dispose();
            }
        };
        avatarPanel.setPreferredSize(new Dimension(50, 50));
        panel.add(avatarPanel, "w 50!, h 50!");

        // Name
        JLabel nameLabel = new JLabel(player.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(nameLabel);

        // Card count
        JLabel cardCountLabel = new JLabel(player.getHand().size() + " cards");
        panel.add(cardCountLabel);

        return panel;
    }

    private void updateDirectionIndicator() {
        if (gameDirection == Direction.CLOCKWISE) {
            directionLabel.setText("Direction: Clockwise");
            directionLabel.setIcon(FontIcon.of(MaterialDesignA.ARROW_RIGHT_BOLD_CIRCLE, 16, UnoTheme.UNO_RED));
        } else {
            directionLabel.setText("Direction: Counter-Clockwise");
            directionLabel.setIcon(FontIcon.of(MaterialDesignA.ARROW_LEFT_BOLD_CIRCLE, 16, UnoTheme.UNO_RED));
        }
    }

    private void updatePlayerInfo() {
        Component[] components = playerInfoPanel.getComponents();
        if (components.length >= 2 && components[1] instanceof JLabel) {
            ((JLabel) components[1]).setText(currentPlayer.getName());
        }
    }

    private void updateHand() {
        JScrollPane scrollPane = (JScrollPane) ((MaterialPanel) handPanel).getComponent(1);
        JViewport viewport = scrollPane.getViewport();
        JPanel cardsContainer = (JPanel) viewport.getView();

        cardsContainer.removeAll();
        handCardComponents.clear();

        Player humanPlayer = null;
        for (Player player : players) {
            if (player instanceof HumanPlayer) {
                humanPlayer = player;
                break;
            }
        }

        if (humanPlayer != null) {
            for (AbstractCard card : humanPlayer.getHand()) {
                UnoCardComponent cardComponent = new UnoCardComponent(card);
                cardComponent.setBorder(BorderFactory.createEmptyBorder(0, -30, 0, 0)); // Overlap cards
                cardComponent.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        if (isHumanPlayerTurn && !isColorSelectionActive) {
                            playCard(card, cardComponent);
                        }
                    }

                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        if (isHumanPlayerTurn && !isColorSelectionActive) {
                            cardComponent.setBorder(BorderFactory.createEmptyBorder(0, -30, -15, 0));
                            cardComponent.setLocation(cardComponent.getX(), cardComponent.getY() - 15);
                            cardsContainer.revalidate();
                        }
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        cardComponent.setBorder(BorderFactory.createEmptyBorder(0, -30, 0, 0));
                        cardComponent.setLocation(cardComponent.getX(), cardComponent.getY() + 15);
                        cardsContainer.revalidate();
                    }
                });
                handCardComponents.add(cardComponent);
                cardsContainer.add(cardComponent);
            }
        }

        cardsContainer.revalidate();
        cardsContainer.repaint();
    }

    private void playCard(AbstractCard card, UnoCardComponent cardComponent) {
        if (card.isPlayable(currentCard)) {
            // Handle wild card color selection
            if (card instanceof WildCard) {
                showColorSelectionDialog((WildCard) card, cardComponent);
            } else {
                executeCardPlay(card, cardComponent);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "This card cannot be played on the current card.",
                    "Invalid Move",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showColorSelectionDialog(WildCard wildCard, UnoCardComponent cardComponent) {
        isColorSelectionActive = true;

        JDialog colorDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Select Color", true);
        colorDialog.setLayout(new MigLayout("insets 20", "[center]", "[]15[]"));
        colorDialog.setResizable(false);

        JLabel promptLabel = new JLabel("Select a color:");
        promptLabel.setFont(new Font("Arial", Font.BOLD, 16));
        colorDialog.add(promptLabel, "wrap");

        JPanel colorsPanel = new JPanel(new MigLayout("insets 0", "[]15[]", "[]15[]"));
        colorsPanel.setOpaque(false);

        // Create color buttons
        createColorButton(colorsPanel, UnoTheme.UNO_RED, CardColorEnum.RED, wildCard, cardComponent, colorDialog);
        createColorButton(colorsPanel, UnoTheme.UNO_BLUE, CardColorEnum.BLUE, wildCard, cardComponent, colorDialog);
        createColorButton(colorsPanel, UnoTheme.UNO_GREEN, CardColorEnum.GREEN, wildCard, cardComponent, colorDialog);
        createColorButton(colorsPanel, UnoTheme.UNO_YELLOW, CardColorEnum.YELLOW, wildCard, cardComponent, colorDialog);

        colorDialog.add(colorsPanel, "grow");

        colorDialog.pack();
        colorDialog.setLocationRelativeTo(this);
        colorDialog.setVisible(true);
    }

    private void createColorButton(JPanel panel, Color color, CardColorEnum cardColor,
            WildCard wildCard, UnoCardComponent cardComponent, JDialog dialog) {
        JPanel colorButton = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw circle
                g2.setColor(color);
                g2.fill(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));

                g2.dispose();
            }
        };
        colorButton.setPreferredSize(new Dimension(60, 60));
        colorButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        colorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wildCard.setChosenColor(cardColor);
                dialog.dispose();
                executeCardPlay(wildCard, cardComponent);
                isColorSelectionActive = false;
            }
        });

        panel.add(colorButton);
    }

    private void executeCardPlay(AbstractCard card, UnoCardComponent cardComponent) {
        // Find the human player
        Player humanPlayer = null;
        for (Player player : players) {
            if (player instanceof HumanPlayer) {
                humanPlayer = player;
                break;
            }
        }

        if (humanPlayer != null) {
            // Remove the card from player's hand
            humanPlayer.getHand().remove(card);

            // Update the current card
            currentCard = card;
            currentCardComponent.setCard(card);

            // Update the hand display
            updateHand();

            // Process special card effects
            processCardEffects(card);

            // Check for win
            if (humanPlayer.getHand().isEmpty()) {
                showGameOverDialog(humanPlayer.getName());
                return;
            }

            // Move to next player (bot's turn)
            isHumanPlayerTurn = false;

            // Simulate bot turns after a short delay
            simulateBotTurns();
        }
    }

    private void processCardEffects(AbstractCard card) {
        if (card instanceof ActionCard) {
            switch (card.getType()) {
                case REVERSE:
                    gameDirection = (gameDirection == Direction.CLOCKWISE) ? Direction.COUNTERCLOCKWISE
                            : Direction.CLOCKWISE;
                    updateDirectionIndicator();
                    break;
                case SKIP:
                    // Skip is handled in the bot turn simulation
                    break;
                case DRAW_TWO:
                    // Draw two is handled in the bot turn simulation
                    break;
                case NUMBER: // Add this case
                    // Regular number cards have no special effects
                    break;
                case WILD_COLOR: // Add this case
                case WILD_DRAW_FOUR: // Add this case
                    // These are handled separately
                    break;
                default:
                    // Handle any future enum values
                    System.out.println("Unhandled card type: " + card.getType());
                    break;
            }
        } else if (card instanceof WildCard && card.getType() == CardTypeEnum.WILD_DRAW_FOUR) {
            // Wild Draw Four is handled in the bot turn simulation
        }
    }

    private void drawCard() {
        if (!isHumanPlayerTurn || isColorSelectionActive) {
            return;
        }

        // Find the human player
        Player humanPlayer = null;
        for (Player player : players) {
            if (player instanceof HumanPlayer) {
                humanPlayer = player;
                break;
            }
        }

        if (humanPlayer != null) {
            // Draw a card from deck
            AbstractCard drawnCard = deck.drawCard();

            // Add to player's hand
            humanPlayer.getHand().add(drawnCard);

            // Show dialog with drawn card
            JOptionPane.showMessageDialog(this,
                    "You drew: " + drawnCard,
                    "Card Drawn",
                    JOptionPane.INFORMATION_MESSAGE);

            // Update hand display
            updateHand();

            // Check if the drawn card can be played
            if (drawnCard.isPlayable(currentCard)) {
                int option = JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to play the drawn card?",
                        "Play Card",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    // Handle wild card color selection
                    if (drawnCard instanceof WildCard) {
                        UnoCardComponent drawnCardComponent = new UnoCardComponent(drawnCard);
                        showColorSelectionDialog((WildCard) drawnCard, drawnCardComponent);
                    } else {
                        // Remove the card from hand
                        humanPlayer.getHand().remove(drawnCard);

                        // Update the current card
                        currentCard = drawnCard;
                        currentCardComponent.setCard(drawnCard);

                        // Update the hand display
                        updateHand();

                        // Process special card effects
                        processCardEffects(drawnCard);
                    }
                }
            }

            // Move to next player (bot's turn)
            isHumanPlayerTurn = false;

            // Simulate bot turns after a short delay
            simulateBotTurns();
        }
    }

    private void simulateBotTurns() {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000); // Short delay for visual feedback

                // Use atomic references to allow mutation
                final AtomicBoolean skipNext = new AtomicBoolean(false);
                final AtomicBoolean drawTwo = new AtomicBoolean(false);
                final AtomicBoolean drawFour = new AtomicBoolean(false);

                // Process any pending effects
                if (currentCard.getType() == CardTypeEnum.SKIP) {
                    skipNext.set(true);
                } else if (currentCard.getType() == CardTypeEnum.DRAW_TWO) {
                    drawTwo.set(true);
                } else if (currentCard.getType() == CardTypeEnum.WILD_DRAW_FOUR) {
                    drawFour.set(true);
                }

                // Loop through bots until back to human player
                for (int i = 0; i < players.size() - 1; i++) {
                    // Find the next player
                    int nextPlayerIndex = findNextPlayerIndex();

                    // Update current player
                    currentPlayer = players.get(nextPlayerIndex);

                    // Update UI
                    SwingUtilities.invokeLater(this::updatePlayerInfo);

                    // Check for skip
                    if (skipNext.get()) {
                        skipNext.set(false);
                        final Player skippedPlayer = currentPlayer;
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
                                skippedPlayer.getName() + " was skipped!",
                                "Skip Turn",
                                JOptionPane.INFORMATION_MESSAGE));
                        Thread.sleep(1000);
                        continue;
                    }

                    // Check for draw two/four
                    if (drawTwo.get()) {
                        drawTwo.set(false);
                        for (int j = 0; j < 2; j++) {
                            currentPlayer.getHand().add(deck.drawCard());
                        }
                        final Player drawingPlayer = currentPlayer;
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
                                drawingPlayer.getName() + " drew 2 cards!",
                                "Draw Two",
                                JOptionPane.INFORMATION_MESSAGE));
                        Thread.sleep(1000);
                        continue;
                    }

                    if (drawFour.get()) {
                        drawFour.set(false);
                        for (int j = 0; j < 4; j++) {
                            currentPlayer.getHand().add(deck.drawCard());
                        }
                        final Player drawingPlayer = currentPlayer;
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
                                drawingPlayer.getName() + " drew 4 cards!",
                                "Draw Four",
                                JOptionPane.INFORMATION_MESSAGE));
                        Thread.sleep(1000);
                        continue;
                    }

                    // Bot plays a card
                    boolean cardPlayed = false;
                    AbstractCard playedCard = null;

                    // Check if bot has a playable card
                    for (AbstractCard card : currentPlayer.getHand()) {
                        if (card.isPlayable(currentCard)) {
                            playedCard = card;
                            cardPlayed = true;
                            break;
                        }
                    }

                    if (cardPlayed && playedCard != null) {
                        // Remove card from bot's hand
                        currentPlayer.getHand().remove(playedCard);

                        // Handle wild card
                        if (playedCard instanceof WildCard) {
                            // Bot chooses a random color
                            CardColorEnum[] colors = CardColorEnum.values();
                            int randomIndex = (int) (Math.random() * colors.length);
                            ((WildCard) playedCard).setChosenColor(colors[randomIndex]);
                        }

                        // Update current card
                        AbstractCard finalPlayedCard = playedCard;
                        final Player playingPlayer = currentPlayer;
                        final AtomicBoolean finalSkipNext = skipNext;
                        final AtomicBoolean finalDrawTwo = drawTwo;
                        final AtomicBoolean finalDrawFour = drawFour;

                        SwingUtilities.invokeLater(() -> {
                            currentCardComponent.setCard(finalPlayedCard);
                            currentCard = finalPlayedCard;

                            // Update UI
                            JOptionPane.showMessageDialog(this,
                                    playingPlayer.getName() + " played: " + finalPlayedCard,
                                    "Bot Turn",
                                    JOptionPane.INFORMATION_MESSAGE);

                            // Process special card effects
                            if (finalPlayedCard instanceof ActionCard) {
                                switch (finalPlayedCard.getType()) {
                                    case REVERSE:
                                        gameDirection = (gameDirection == Direction.CLOCKWISE)
                                                ? Direction.COUNTERCLOCKWISE
                                                : Direction.CLOCKWISE;
                                        updateDirectionIndicator();
                                        break;
                                    case SKIP:
                                        finalSkipNext.set(true);
                                        break;
                                    case DRAW_TWO:
                                        finalDrawTwo.set(true);
                                        break;
                                    case NUMBER: // Add this case
                                        // Regular number cards have no special effects
                                        break;
                                    case WILD_COLOR: // Add this case
                                    case WILD_DRAW_FOUR: // Add this case
                                        // These are handled separately for wild cards
                                        break;
                                    default:
                                        // Handle any future enum values
                                        System.out.println("Unhandled card type: " + finalPlayedCard.getType());
                                        break;
                                }
                            } else if (finalPlayedCard instanceof WildCard &&
                                    finalPlayedCard.getType() == CardTypeEnum.WILD_DRAW_FOUR) {
                                finalDrawFour.set(true);
                            }

                            // Update opponents display
                            initializeOpponentsPanel();
                        });
                    } else {
                        // Bot draws a card
                        AbstractCard drawnCard = deck.drawCard();
                        currentPlayer.getHand().add(drawnCard);
                        final Player drawingPlayer = currentPlayer;

                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(this,
                                    drawingPlayer.getName() + " drew a card.",
                                    "Bot Turn",
                                    JOptionPane.INFORMATION_MESSAGE);

                            // Update opponents display
                            initializeOpponentsPanel();
                        });
                    }

                    // Check for win
                    if (currentPlayer.getHand().isEmpty()) {
                        String winnerName = currentPlayer.getName();
                        SwingUtilities.invokeLater(() -> showGameOverDialog(winnerName));
                        return;
                    }

                    Thread.sleep(1500); // Wait between bot turns
                }

                // Back to human player
                for (Player player : players) {
                    if (player instanceof HumanPlayer) {
                        currentPlayer = player;
                        break;
                    }
                }

                SwingUtilities.invokeLater(() -> {
                    updatePlayerInfo();
                    isHumanPlayerTurn = true;
                    JOptionPane.showMessageDialog(this,
                            "It's your turn!",
                            "Your Turn",
                            JOptionPane.INFORMATION_MESSAGE);
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private int findNextPlayerIndex() {
        int currentIndex = players.indexOf(currentPlayer);
        if (gameDirection == Direction.CLOCKWISE) {
            return (currentIndex + 1) % players.size();
        } else {
            return (currentIndex - 1 + players.size()) % players.size();
        }
    }

    private void showGameOverDialog(String winnerName) {
        JDialog gameOverDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Game Over", true);
        gameOverDialog.setLayout(new MigLayout("insets 20", "[center]", "[]15[]15[]"));
        gameOverDialog.setResizable(false);

        JLabel victoryLabel = new JLabel("Game Over!");
        victoryLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gameOverDialog.add(victoryLabel, "wrap");

        JLabel winnerLabel = new JLabel(winnerName + " is the winner!");
        winnerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gameOverDialog.add(winnerLabel, "wrap");

        JPanel buttonsPanel = new JPanel(new MigLayout("insets 0", "[]15[]", "[]"));

        MaterialButton playAgainButton = new MaterialButton("Play Again", UnoTheme.UNO_GREEN);
        playAgainButton.addActionListener(e -> {
            gameOverDialog.dispose();
            resetGame();
        });

        MaterialButton mainMenuButton = new MaterialButton("Main Menu", UnoTheme.UNO_RED);
        mainMenuButton.addActionListener(e -> {
            gameOverDialog.dispose();
            exitActionListener.actionPerformed(e);
        });

        buttonsPanel.add(playAgainButton);
        buttonsPanel.add(mainMenuButton);

        gameOverDialog.add(buttonsPanel);

        gameOverDialog.pack();
        gameOverDialog.setLocationRelativeTo(this);
        gameOverDialog.setVisible(true);
    }

    private void resetGame() {
        // Reset the deck
        deck.reInitializeDeck(new ArrayList<>());

        // Reset each player's hand
        for (Player player : players) {
            // Clear existing hand
            player.getHand().clear();

            // Deal new cards
            ArrayList<AbstractCard> newHand = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                newHand.add(deck.drawCard());
            }
            player.setHand(newHand);
        }

        // Reset game state
        isGameRunning = false;
        isHumanPlayerTurn = false;
        isColorSelectionActive = false;
        gameDirection = Direction.CLOCKWISE;

        // Draw initial discard card
        currentCard = deck.drawCard();
        while (currentCard instanceof ActionCard || currentCard instanceof WildCard) {
            deck.getRemainingCards().add(0, currentCard); // Put it back in the deck
            currentCard = deck.drawCard();
        }

        // Update UI
        currentCardComponent.setCard(currentCard);
        updateDirectionIndicator();
        initializeOpponentsPanel();

        // Assign the human player as the first player
        for (Player player : players) {
            if (player instanceof HumanPlayer) {
                currentPlayer = player;
                break;
            }
        }

        updatePlayerInfo();
        updateHand();

        // Start the new game
        isGameRunning = true;
        isHumanPlayerTurn = true;
    }
}