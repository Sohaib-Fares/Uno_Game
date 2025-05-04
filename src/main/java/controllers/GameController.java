package controllers;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import AppService.Game.GameService;
import AppService.Game.Direction;
import model.CardModel.AbstractCard;
import model.CardModel.CardColorEnum;
import model.CardModel.WildCard;
import model.PlayerModel.Player;
import model.PlayerModel.HumanPlayer;
import model.PlayerModel.PlayerConfig;
import view.MuGamePlayPanel;

/**
 * Manages the game logic execution and updates the game view.
 */
public class GameController implements GameService.GameEventListener {

    private NavController navController;
    private GameService gameService;
    private List<PlayerConfig> playerConfigs;
    private MuGamePlayPanel gamePlayPanel;

    public GameController(NavController navController) {
        this.navController = navController;
        this.gameService = new GameService();
        this.gameService.setEventListener(this);
    }

    /**
     * Sets the game play panel reference
     * @param gamePlayPanel Reference to the game panel for updates
     */
    public void setGamePlayPanel(MuGamePlayPanel gamePlayPanel) {
        this.gamePlayPanel = gamePlayPanel;
    }

    /**
     * Called when the game view is created or needs to be initialized.
     * @param playerConfigs The configuration of players for this game.
     */
    public void initializeGame(List<PlayerConfig> playerConfigs) {
        this.playerConfigs = playerConfigs;
        System.out.println("GameController initializing game with " + playerConfigs.size() + " players.");
        
        // Set up the game with player configs
        gameService.setupGame(playerConfigs);
    }

    /**
     * Handles the action when a player clicks on a card in their hand.
     * @param card The card that was clicked.
     */
    public void cardClicked(AbstractCard card) {
        System.out.println("Card clicked: " + card);
        
        // Only allow card clicks from human players during their turn
        Player currentPlayer = gameService.getCurrentPlayer();
        
        // Check if the current player is human
        if (currentPlayer instanceof HumanPlayer) {
            // Find the card index in the player's hand
            List<AbstractCard> playerHand = currentPlayer.getHand();
            int cardIndex = -1;
            
            for (int i = 0; i < playerHand.size(); i++) {
                if (playerHand.get(i) == card) {
                    cardIndex = i;
                    break;
                }
            }
            
            if (cardIndex != -1) {
                // Try to play the card
                gameService.playCard(cardIndex);
            }
        } else {
            System.out.println("Not your turn!");
            JOptionPane.showMessageDialog(
                gamePlayPanel, 
                "It's " + currentPlayer.getName() + "'s turn.", 
                "Not Your Turn", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Handles the action when a player clicks the draw deck.
     */
    public void drawCardClicked() {
        System.out.println("Draw deck clicked.");
        
        // Only allow drawing if it's a human player's turn
        Player currentPlayer = gameService.getCurrentPlayer();
        
        if (currentPlayer instanceof HumanPlayer) {
            gameService.drawCard();
        } else {
            System.out.println("Not your turn!");
            JOptionPane.showMessageDialog(
                gamePlayPanel, 
                "It's " + currentPlayer.getName() + "'s turn.", 
                "Not Your Turn", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Shows a dialog to select a color for a wild card
     * @param wildCard The played wild card
     */
    private void showColorSelectionDialog(final WildCard wildCard) {
        SwingUtilities.invokeLater(() -> {
            String[] colors = {"RED", "BLUE", "GREEN", "YELLOW"};
            String selectedColor = (String)JOptionPane.showInputDialog(
                gamePlayPanel,
                "Select a color:",
                "Wild Card Color",
                JOptionPane.QUESTION_MESSAGE,
                null,
                colors,
                colors[0]);
            
            if (selectedColor != null) {
                CardColorEnum color = CardColorEnum.valueOf(selectedColor);
                gameService.setWildCardColor(color);
            } else {
                // Default to red if cancelled
                gameService.setWildCardColor(CardColorEnum.RED);
            }
        });
    }

    /**
     * Gets the current list of players in the game
     * @return List of Player objects
     */
    public List<Player> getPlayers() {
        return gameService.getPlayers();
    }

    // GameEventListener implementation

    @Override
    public void onGameStateChanged() {
        if (gamePlayPanel != null) {
            gamePlayPanel.updateGameState(
                gameService.getPlayers(),
                gameService.getCurrentPlayer(),
                gameService.getCurrentCard(),
                gameService.getGameDirection());
        }
    }

    @Override
    public void onPlayerHandChanged(Player player) {
        if (gamePlayPanel != null) {
            gamePlayPanel.updatePlayerHand(player);
        }
    }

    @Override
    public void onTopCardChanged(AbstractCard topCard) {
        if (gamePlayPanel != null) {
            gamePlayPanel.updateDiscardPile(topCard);
        }
    }

    @Override
    public void onCurrentPlayerChanged(Player player) {
        if (gamePlayPanel != null) {
            gamePlayPanel.updateCurrentPlayer(player);
            
            // Give a clear indication of whose turn it is
            SwingUtilities.invokeLater(() -> {
                // Show whose turn it is now - for both human and bot players
                JOptionPane.showMessageDialog(
                    gamePlayPanel, 
                    "It's " + player.getName() + "'s turn now.", 
                    "Turn Change", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Additional guidance for human players
                if (player instanceof HumanPlayer) {
                    // Give them a hint
                    JOptionPane.showMessageDialog(
                        gamePlayPanel,
                        player.getName() + ", it's your turn to play.",
                        "Your Turn",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            });
        }
    }

    @Override
    public void onGameDirectionChanged(Direction direction) {
        if (gamePlayPanel != null) {
            gamePlayPanel.updateDirection(direction);
        }
    }

    @Override
    public void onGameOver(Player winner) {
        if (gamePlayPanel != null) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                    gamePlayPanel, 
                    winner.getName() + " has won the game!", 
                    "Game Over", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Return to setup screen after game over
                navController.showGameSetup();
            });
        }
    }

    @Override
    public void onCardNotPlayable(Player player, AbstractCard card) {
        if (gamePlayPanel != null && player instanceof HumanPlayer) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                    gamePlayPanel, 
                    "That card cannot be played on the current discard pile.", 
                    "Invalid Move", 
                    JOptionPane.WARNING_MESSAGE);
            });
        }
    }

    @Override
    public void onWildCardPlayed(Player player, WildCard card) {
        if (player instanceof HumanPlayer) {
            showColorSelectionDialog(card);
        }
    }
}
