package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import AppService.Game.Direction;
import controllers.GameController;
import controllers.NavController;
import model.CardModel.AbstractCard;
import model.PlayerModel.HumanPlayer;
import model.PlayerModel.Player;
import model.PlayerModel.PlayerConfig;
import view.MuGamePlayPanels.BottomBarPanel;
import view.MuGamePlayPanels.CenterGamePanel;
import view.MuGamePlayPanels.TopBarPanel;

public class MuGamePlayPanel extends JPanel {

    private List<PlayerConfig> playerConfigs;
    private GameController gameController;
    private NavController navController;

    private TopBarPanel topBarPanel;
    private CenterGamePanel centerGamePanel;
    private BottomBarPanel bottomBarPanel;

    public MuGamePlayPanel(NavController navController, GameController gameController, List<PlayerConfig> playerConfigs) {
        this.playerConfigs = playerConfigs;
        this.gameController = gameController;
        this.navController = navController;

        setLayout(new BorderLayout(0, 0));
        setOpaque(true);
        setBackground(Color.WHITE);

        topBarPanel = new TopBarPanel(navController, gameController, this.playerConfigs);
        bottomBarPanel = new BottomBarPanel(gameController, this.playerConfigs);
        centerGamePanel = new CenterGamePanel(gameController, this.playerConfigs);

        add(topBarPanel, BorderLayout.NORTH);
        add(bottomBarPanel, BorderLayout.SOUTH);
        add(centerGamePanel, BorderLayout.CENTER);

        System.out.println("MuGamePlayPanel created with GameController and " + playerConfigs.size() + " players.");
        
        // Register this panel with the controller so it can receive updates
        gameController.setGamePlayPanel(this);
    }
    
    /**
     * Updates the overall game state
     * @param players All players in the game
     * @param currentPlayer The current player whose turn it is
     * @param currentCard The current top card on the discard pile
     * @param direction The current game direction
     */
    public void updateGameState(List<Player> players, Player currentPlayer, AbstractCard currentCard, Direction direction) {
        // Delegate updates to the appropriate panels
        updateCurrentPlayer(currentPlayer);
        updateDiscardPile(currentCard);
        updateDirection(direction);
        
        // Update all opponent information - any player that is not a human player
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            // Only update center panel for non-human players (opponents)
            if (!(player instanceof HumanPlayer)) {
                centerGamePanel.updateOpponentInfo(player, i);
            }
        }
        
        // Refresh all panels
        revalidate();
        repaint();
    }
    
    /**
     * Updates the current player information displayed in the UI
     * @param player The current player
     */
    public void updateCurrentPlayer(Player player) {
        topBarPanel.updateCurrentPlayer(player);
    }
    
    /**
     * Updates the discard pile with the new top card
     * @param topCard The new top card
     */
    public void updateDiscardPile(AbstractCard topCard) {
        centerGamePanel.updateDiscardPile(topCard);
    }
    
    /**
     * Updates the direction indicator in the UI
     * @param direction The new game direction
     */
    public void updateDirection(Direction direction) {
        centerGamePanel.updateDirection(direction);
    }
    
    /**
     * Updates a specific player's hand in the UI
     * @param player The player whose hand changed
     */
    public void updatePlayerHand(Player player) {
        if (player != null) {
            // Check if this is a human player (show in bottom bar)
            if (player instanceof HumanPlayer) {
                bottomBarPanel.updatePlayerHand(player);
            } else {
                // Update opponent card counts in center panel
                int playerIndex = gameController.getPlayers().indexOf(player);
                centerGamePanel.updateOpponentInfo(player, playerIndex);
            }
        }
    }
}
