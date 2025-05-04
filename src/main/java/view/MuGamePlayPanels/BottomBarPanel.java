package view.MuGamePlayPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import UI.Components.Buttons.MuOutlinedButton;
import UI.Components.Cards.MuCard;
import UI.Components.Cards.MuNumberedCard;
import UI.Components.Labels.MuCircleLabel;
import UI.Components.Labels.MuLabel;
import UI.Components.Panels.MuPanel;
import controllers.GameController;
import model.CardModel.AbstractCard;
import model.CardModel.CardColorEnum;
import model.CardModel.CardTypeEnum;
import model.PlayerModel.Player;
import model.PlayerModel.PlayerConfig;

public class BottomBarPanel extends JPanel {

    private List<PlayerConfig> playerConfigs;
    private PlayerConfig currentPlayer;
    private MuPanel cardsPanel;
    private JScrollPane scrollPane;
    private GameController gameController;
    private Map<AbstractCard, MuCard> cardDisplayMap = new HashMap<>();

    private static final int PLAYER_CARD_WIDTH = 70;
    private static final int PLAYER_CARD_HEIGHT = 105;

    public BottomBarPanel(GameController gameController, List<PlayerConfig> playerConfigs) {
        this.gameController = gameController;
        this.playerConfigs = playerConfigs;

        if (playerConfigs != null && !playerConfigs.isEmpty()) {
            this.currentPlayer = playerConfigs.get(0);
        } else {
            this.currentPlayer = new PlayerConfig("Player ?", false, Color.GRAY);
            System.err.println("Warning: BottomBarPanel received empty or null playerConfigs.");
        }

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(0, 200));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(
            1, 0, 0, 0, new Color(255, 221, 0)
        ));
        setOpaque(true);

        MuPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        cardsPanel = new MuPanel();
        cardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        cardsPanel.setBackground(Color.WHITE);
        
        // In the real game, cards will be populated when the game service initializes
        // For now, keep the dummy cards for preview
        populateDummyCards();

        scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, BorderLayout.CENTER);
    }

    private MuPanel createTopPanel() {
        MuPanel topPanel = new MuPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setPreferredSize(new Dimension(0, 55));

        MuPanel leftPanel = new MuPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 8));
        leftPanel.setOpaque(false);

        MuCircleLabel circlePlayer = new MuCircleLabel(currentPlayer.getColor(), 35, true);
        MuLabel currentPlayerLabel = new MuLabel();
        currentPlayerLabel.setText(currentPlayer.getName());
        currentPlayerLabel.setForeground(Color.BLACK);
        currentPlayerLabel.setOpaque(false);
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        currentPlayerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentPlayerLabel.setVerticalAlignment(SwingConstants.CENTER);

        leftPanel.add(circlePlayer);
        leftPanel.add(currentPlayerLabel);
        topPanel.add(leftPanel, BorderLayout.WEST);

        MuPanel rightPanel = new MuPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        rightPanel.setOpaque(false);

        MuOutlinedButton drawCardButton = new MuOutlinedButton(
            "Draw Card",
             new Color(255, 221, 0),
              Color.BLACK,
               14,
                110,
                 38
        );
        
        // Add event handler for draw card button
        drawCardButton.addActionListener(e -> gameController.drawCardClicked());

        rightPanel.add(drawCardButton);
        topPanel.add(rightPanel, BorderLayout.EAST);

        return topPanel;
    }

    /**
     * Creates a visual card representation for a game card
     */
    private MuCard createCardDisplay(final AbstractCard cardData) {
        MuCard cardDisplay;
        
        Color cardColor = convertCardColorToAwtColor(cardData.getColor());
        
        if (cardData.getType() == CardTypeEnum.NUMBER) {
            // Extract number from card
            String cardValue = cardData.toString().replaceAll("[^0-9]", "");
            cardDisplay = new MuNumberedCard(cardColor, cardValue);
        } else {
            // For special cards, use the first letter as a display
            String cardText = cardData.getType().toString().replace("_", " ");
            // For wild cards with no chosen color
            if (cardColor == null) {
                cardDisplay = new MuNumberedCard(Color.BLACK, "W"); 
            } else {
                cardDisplay = new MuNumberedCard(cardColor, cardText.substring(0, 1));
            }
        }
        
        cardDisplay.setPreferredSize(new Dimension(PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT));
        
        // Add event handler for card clicks
        cardDisplay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.cardClicked(cardData);
            }
        });
        
        return cardDisplay;
    }

    /**
     * Updates the player's hand to show the current cards
     * @param player The player (should be the human player) whose hand to display
     */
    public void updatePlayerHand(Player player) {
        if (player == null || player.getHand() == null) return;
        
        // Clear previous cards but keep map of existing cards to reuse if possible
        cardsPanel.removeAll();
        List<AbstractCard> hand = player.getHand();
        
        // Add each card to the display
        for (AbstractCard card : hand) {
            MuCard cardDisplay;
            
            // Reuse existing card display if available, otherwise create new
            if (cardDisplayMap.containsKey(card)) {
                cardDisplay = cardDisplayMap.get(card);
            } else {
                cardDisplay = createCardDisplay(card);
                cardDisplayMap.put(card, cardDisplay);
            }
            
            cardsPanel.add(cardDisplay);
        }
        
        // Clean up map to remove cards no longer in hand
        List<AbstractCard> toRemove = new ArrayList<>();
        for (AbstractCard card : cardDisplayMap.keySet()) {
            if (!hand.contains(card)) {
                toRemove.add(card);
            }
        }
        for (AbstractCard card : toRemove) {
            cardDisplayMap.remove(card);
        }
        
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private void populateDummyCards() {
        cardsPanel.removeAll();
        cardsPanel.add(createPlayerCard("1", Color.GREEN));
        cardsPanel.add(createPlayerCard("7", Color.RED));
        cardsPanel.add(createPlayerCard("3", Color.YELLOW));
        cardsPanel.add(createPlayerCard("5", Color.BLUE));
        cardsPanel.add(createPlayerCard("9", Color.RED));
        cardsPanel.add(createPlayerCard("2", Color.GREEN));
        cardsPanel.add(createPlayerCard("SKIP", Color.BLUE));
        cardsPanel.add(createPlayerCard("0", Color.YELLOW));
        cardsPanel.add(createPlayerCard("4", Color.RED));
        cardsPanel.add(createPlayerCard("8", Color.GREEN));
        cardsPanel.add(createPlayerCard("6", Color.BLUE));

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }
    
    /**
     * Helper method for dummy cards (will be removed in production)
     */
    private MuCard createPlayerCard(String label, Color color) {
        MuNumberedCard card = new MuNumberedCard(color, label);
        card.setPreferredSize(new Dimension(PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT));

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Dummy card clicked: " + label);
                // No game controller action for dummy cards
            }
        });
        return card;
    }
    
    /**
     * Helper method to convert CardColorEnum to AWT Color
     */
    private Color convertCardColorToAwtColor(CardColorEnum cardColor) {
        if (cardColor == null) return null;
        
        switch (cardColor) {
            case RED:
                return new Color(229, 5, 0); // Red
            case GREEN:
                return new Color(0, 169, 37); // Green
            case BLUE:
                return new Color(0, 171, 240); // Blue
            case YELLOW:
                return new Color(255, 221, 0); // Yellow
            default:
                return Color.BLACK;
        }
    }
}
