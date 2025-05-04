package view.MuGamePlayPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import AppService.Game.Direction;
import UI.Components.Cards.MuNumberedCard;
import UI.Components.Cards.MuCard;
import UI.Components.Borders.MuRoundedBorder;
import UI.Components.Labels.MuCircleLabel;
import UI.Components.Labels.MuLabel;
import UI.Components.Panels.MuPanel;
import controllers.GameController;
import model.CardModel.AbstractCard;
import model.CardModel.CardColorEnum;
import model.CardModel.CardTypeEnum;
import model.PlayerModel.Player;
import model.PlayerModel.PlayerConfig;

public class CenterGamePanel extends JPanel {

    private List<PlayerConfig> playerConfigs;
    private MuPanel playersPanel;
    private GameController gameController;
    private JPanel drawDeckPlaceholder;
    private MuCard currentCardDisplay; // Reference to the displayed card
    private MuLabel directionLabel; // Reference to direction label
    private Map<Integer, MuPanel> opponentPanels = new HashMap<>(); // Store references to opponent panels by index

    private static final int OPPONENT_CARD_WIDTH = 60;
    private static final int OPPONENT_CARD_HEIGHT = 90;
    private static final int CURRENT_CARD_WIDTH = 80;
    private static final int CURRENT_CARD_HEIGHT = 120;

    public CenterGamePanel(GameController gameController, List<PlayerConfig> playerConfigs) {
        this.gameController = gameController;
        this.playerConfigs = playerConfigs;
        setLayout(new BorderLayout());
        setBackground(new Color(255, 239, 170));

        // ****direction label****
        directionLabel = new MuLabel();
        directionLabel.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, new Color(255, 221, 0)));
        directionLabel.setText("Direction ➜");
        directionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        directionLabel.setVerticalAlignment(SwingConstants.CENTER);
        directionLabel.setForeground(Color.BLACK);
        directionLabel.setOpaque(true);
        directionLabel.setBackground(new Color(255, 239, 170));
        directionLabel.setPreferredSize(new Dimension(1000, 40));
        directionLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(directionLabel, BorderLayout.NORTH);

        // *****Players panels******
        playersPanel = new MuPanel();
        int opponentCount = playerConfigs.size() - 1;
        if (opponentCount <= 0) {
            opponentCount = 1;
        }
        playersPanel.setLayout(new GridLayout(1, opponentCount, 20, 10));
        playersPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        playersPanel.setBackground(new Color(237, 241, 252));

        List<PlayerConfig> opponents = playerConfigs.stream().skip(1).collect(Collectors.toList());

        for (int i = 0; i < opponents.size(); i++) {
            PlayerConfig opponent = opponents.get(i);
            int cardCount = 7;
            // Store reference to opponent panel with player index (first player is index 0)
            MuPanel opponentPanel = createPlayerPanel(opponent.getName(), opponent.getColor(), cardCount);
            opponentPanels.put(i + 1, opponentPanel); // +1 because these are opponents (not first player)
            playersPanel.add(opponentPanel);
        }

        add(playersPanel, BorderLayout.CENTER);

        // Current card Panel (Deck and Discard Pile)
        MuPanel currentCardPanel = new MuPanel();
        currentCardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 40));
        currentCardPanel.setBackground(new Color(237, 241, 252));
        currentCardPanel.setPreferredSize(new Dimension(1000, 200));

        // Create initial display card (will be replaced when game starts)
        currentCardDisplay = new MuNumberedCard(new Color(0, 171, 240), "4");
        currentCardDisplay.setPreferredSize(new Dimension(CURRENT_CARD_WIDTH, CURRENT_CARD_HEIGHT));

        drawDeckPlaceholder = new JPanel();
        drawDeckPlaceholder.setPreferredSize(new Dimension(CURRENT_CARD_WIDTH, CURRENT_CARD_HEIGHT));
        drawDeckPlaceholder.setBackground(Color.DARK_GRAY);
        drawDeckPlaceholder.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        drawDeckPlaceholder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.drawCardClicked();
            }
        });

        currentCardPanel.add(drawDeckPlaceholder);
        currentCardPanel.add(currentCardDisplay);

        add(currentCardPanel, BorderLayout.SOUTH);
    }

    private MuPanel createPlayerPanel(String name, Color color, int cardCount) {
        MuPanel panel = new MuPanel();
        panel.setLayout(new BorderLayout(0, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new MuRoundedBorder(15),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        MuCircleLabel circlePlayer = new MuCircleLabel(color, 30, true);

        MuLabel nameLabel = new MuLabel();
        nameLabel.setText(name);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        MuLabel cardsLabel = new MuLabel();
        cardsLabel.setText(cardCount + " cards");
        cardsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardsLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));

        panel.add(circlePlayer, BorderLayout.NORTH);
        panel.add(nameLabel, BorderLayout.CENTER);
        panel.add(cardsLabel, BorderLayout.SOUTH);
        return panel;
    }
    
    /**
     * Updates the discard pile with the new top card
     * @param topCard The new top card
     */
    public void updateDiscardPile(AbstractCard topCard) {
        if (topCard == null) return;
        
        // Replace the current card display with a new one based on the topCard
        if (topCard.getType() == CardTypeEnum.NUMBER) {
            // Get the color and value
            Color cardColor = convertCardColorToAwtColor(topCard.getColor());
            String cardValue = topCard.toString().replaceAll("[^0-9]", ""); // Extract number
            
            // Create a new numbered card
            MuNumberedCard newCard = new MuNumberedCard(cardColor, cardValue);
            newCard.setPreferredSize(new Dimension(CURRENT_CARD_WIDTH, CURRENT_CARD_HEIGHT));
            
            // Replace in the parent container
            JPanel parent = (JPanel) currentCardDisplay.getParent();
            if (parent != null) {
                int index = getComponentIndex(parent, currentCardDisplay);
                if (index >= 0) {
                    parent.remove(currentCardDisplay);
                    currentCardDisplay = newCard;
                    parent.add(newCard, index);
                    parent.revalidate();
                    parent.repaint();
                }
            }
        } else {
            // For other card types, we would need more specific UI components
            // For now, just update with the card's color
            Color cardColor = convertCardColorToAwtColor(topCard.getColor());
            String cardText = topCard.getType().toString().replace("_", " ");
            
            MuCard newCard;
            // If the card is a wild card with no chosen color yet
            if (cardColor == null) {
                newCard = new MuNumberedCard(Color.BLACK, "W"); // Placeholder for wild
            } else {
                newCard = new MuNumberedCard(cardColor, cardText.substring(0, 1)); // Use first letter
            }
            
            newCard.setPreferredSize(new Dimension(CURRENT_CARD_WIDTH, CURRENT_CARD_HEIGHT));
            
            JPanel parent = (JPanel) currentCardDisplay.getParent();
            if (parent != null) {
                int index = getComponentIndex(parent, currentCardDisplay);
                if (index >= 0) {
                    parent.remove(currentCardDisplay);
                    currentCardDisplay = newCard;
                    parent.add(newCard, index);
                    parent.revalidate();
                    parent.repaint();
                }
            }
        }
    }
    
    /**
     * Updates the direction indicator in the UI
     * @param direction The new game direction
     */
    public void updateDirection(Direction direction) {
        if (direction == Direction.CLOCKWISE) {
            directionLabel.setText("Direction ➜");
        } else {
            directionLabel.setText("Direction ←");
        }
        directionLabel.revalidate();
        directionLabel.repaint();
    }
    
    /**
     * Updates the displayed information for an opponent
     * @param player The player whose info needs updating
     * @param playerIndex The index of the player in the players list
     */
    public void updateOpponentInfo(Player player, int playerIndex) {
        if (player == null || playerIndex == 0) return; // 0 is human player, not an opponent
        
        MuPanel opponentPanel = opponentPanels.get(playerIndex);
        if (opponentPanel != null) {
            // Find the cards label (it's in the SOUTH position)
            for (java.awt.Component comp : opponentPanel.getComponents()) {
                if (comp instanceof MuLabel && comp == opponentPanel.getComponent(2)) { // SOUTH component
                    MuLabel cardsLabel = (MuLabel) comp;
                    int cardCount = player.getHand().size();
                    cardsLabel.setText(cardCount + " cards");
                    break;
                }
            }
            
            opponentPanel.revalidate();
            opponentPanel.repaint();
        }
    }
    
    /**
     * Helper method to find the index of a component in its parent
     */
    private int getComponentIndex(JPanel parent, java.awt.Component component) {
        for (int i = 0; i < parent.getComponentCount(); i++) {
            if (parent.getComponent(i) == component) {
                return i;
            }
        }
        return -1;
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
