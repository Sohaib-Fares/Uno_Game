package AppService.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.SwingUtilities;

import model.CardModel.AbstractCard;
import model.CardModel.CardColorEnum;
import model.CardModel.CardTypeEnum;
import model.CardModel.WildCard;
import model.Deck.Deck;
import model.PlayerModel.BotPlayer;
import model.PlayerModel.HumanPlayer;
import model.PlayerModel.Player;
import model.PlayerModel.PlayerConfig;

/**
 * Core game service that manages game state and logic
 */
public class GameService {

    // Game components
    private Deck deck;
    private List<AbstractCard> discardPile;
    private List<Player> players;
    private Player currentPlayer;
    private AbstractCard currentCard;
    
    // Game state
    private Direction gameDirection;
    private boolean nextDrawsCards;
    private boolean nextSkip;
    private int cardsToDraw;
    private boolean gameOver;
    
    // Event listener for UI updates
    private GameEventListener eventListener;
    
    // Constants
    private static final int INITIAL_HAND_SIZE = 7;
    
    /**
     * Creates a new GameService instance
     */
    public GameService() {
        this.deck = new Deck();
        this.discardPile = new ArrayList<>();
        this.players = new ArrayList<>();
        this.gameDirection = Direction.CLOCKWISE;
        this.nextDrawsCards = false;
        this.nextSkip = false;
        this.cardsToDraw = 0;
        this.gameOver = false;
    }
    
    /**
     * Set up the game with the provided player configurations
     */
    public void setupGame(List<PlayerConfig> playerConfigs) {
        // Reset the game state
        deck = new Deck();
        discardPile = new ArrayList<>();
        players = new ArrayList<>();
        nextDrawsCards = false;
        nextSkip = false;
        cardsToDraw = 0;
        gameOver = false;
        
        // Create players based on configurations
        for (PlayerConfig config : playerConfigs) {
            Player player;
            
            if (config.isBot()) {
                player = new BotPlayer();
            } else {
                player = new HumanPlayer();
            }
            
            player.setName(config.getName());
            player.setColor(config.getColor());
            players.add(player);
        }
        
        // Deal cards to players
        for (Player player : players) {
            ArrayList<AbstractCard> hand = new ArrayList<>();
            for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
                hand.add(deck.drawCard());
            }
            player.setHand(hand);
        }
        
        // Set initial discard card (top card)
        AbstractCard topCard;
        do {
            topCard = deck.drawCard();
            // The first card cannot be a wild card or action card
            if (topCard.getType() == CardTypeEnum.NUMBER) {
                break;
            }
            // Put non-number cards back in deck and shuffle
            deck.returnCardToDeck(topCard);
            deck.shuffle();
        } while (true);
        
        discardPile.add(topCard);
        currentCard = topCard;
        
        // Select the first player randomly
        Random random = new Random();
        currentPlayer = players.get(random.nextInt(players.size()));
        
        // Default direction is clockwise
        gameDirection = Direction.CLOCKWISE;
        
        // Notify listeners about initial game state
        if (eventListener != null) {
            // Notify about initial state
            eventListener.onGameStateChanged();
            
            // Also update the player hands
            for (Player player : players) {
                eventListener.onPlayerHandChanged(player);
            }
        }
        
        // If first player is a bot, play automatically after a short delay
        if (currentPlayer instanceof BotPlayer) {
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(1000); // Give player time to see bot's turn
                    playBotTurn();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    
    /**
     * Handle human player card selection
     * @param cardIndex Index of the selected card in the player's hand
     * @return True if the card was played successfully
     */
    public boolean playCard(int cardIndex) {
        // Only handle human turns
        if (currentPlayer instanceof BotPlayer || gameOver) {
            return false;
        }
        
        // Check if the player must draw cards due to previous Draw2/Draw4
        if (nextDrawsCards) {
            handleDrawCardEffect();
            return false;
        }
        
        // Get the selected card
        List<AbstractCard> hand = currentPlayer.getHand();
        if (cardIndex < 0 || cardIndex >= hand.size()) {
            return false;
        }
        
        AbstractCard selectedCard = hand.get(cardIndex);
        
        // Check if the card is playable on the current discard pile
        if (selectedCard.isPlayable(currentCard)) {
            // Remove from hand and add to discard pile
            AbstractCard playedCard = currentPlayer.playCard(hand, currentCard, cardIndex);
            discardPile.add(playedCard);
            currentCard = playedCard;
            
            // Handle special card effects
            if (playedCard instanceof WildCard) {
                // Ask player for color choice via event
                if (eventListener != null) {
                    eventListener.onTopCardChanged(currentCard);
                    eventListener.onPlayerHandChanged(currentPlayer);
                    eventListener.onWildCardPlayed(currentPlayer, (WildCard) playedCard);
                    // The actual color setting and next player handling will happen when setWildCardColor is called
                }
                
                // For Wild Draw 4, set next player to draw cards
                if (playedCard.getType() == CardTypeEnum.WILD_DRAW_FOUR) {
                    cardsToDraw = 4;
                    nextDrawsCards = true;
                }
                
                // We don't call moveToNextPlayer() here for wild cards
                // because we need to wait for the color selection
                
                // Check for win condition
                if (currentPlayer.getHand().isEmpty()) {
                    gameOver = true;
                    if (eventListener != null) {
                        eventListener.onGameOver(currentPlayer);
                    }
                }
                
                return true;
            } else if (playedCard.getType() == CardTypeEnum.DRAW_TWO) {
                cardsToDraw = 2;
                nextDrawsCards = true;
            } else if (playedCard.getType() == CardTypeEnum.SKIP) {
                nextSkip = true;
            } else if (playedCard.getType() == CardTypeEnum.REVERSE) {
                toggleGameDirection();
                
                // In a 2-player game, reverse acts as a skip
                if (players.size() == 2) {
                    nextSkip = true;
                }
            }
            
            // Notify listeners about card play for non-wild cards
            if (eventListener != null) {
                eventListener.onTopCardChanged(currentCard);
                eventListener.onPlayerHandChanged(currentPlayer);
            }
            
            // Check for win condition
            if (currentPlayer.getHand().isEmpty()) {
                gameOver = true;
                if (eventListener != null) {
                    eventListener.onGameOver(currentPlayer);
                }
                return true;
            }
            
            // Move to next player's turn (only for non-wild cards)
            moveToNextPlayer();
            
            return true;
        } else {
            // Notify that card is not playable
            if (eventListener != null) {
                eventListener.onCardNotPlayable(currentPlayer, selectedCard);
            }
            return false;
        }
    }
    
    /**
     * Handle action when player draws a card
     */
    public void drawCard() {
        if (gameOver) return;
        
        // Check if deck needs replenishing
        checkEmptyDeck();
        
        // Handle draw card effects from previous turns first
        if (nextDrawsCards) {
            handleDrawCardEffect();
            return;
        }
        
        // Draw a single card
        AbstractCard drawnCard = deck.drawCard();
        currentPlayer.addCard(drawnCard);
        
        // Notify UI about the updated hand
        if (eventListener != null) {
            eventListener.onPlayerHandChanged(currentPlayer);
        }
        
        // Check if the drawn card is playable
        if (drawnCard.isPlayable(currentCard)) {
            // For bot players, automatically play the card after a short delay
            if (currentPlayer instanceof BotPlayer) {
                // Use a short delay to let the player see the card was drawn
                SwingUtilities.invokeLater(() -> {
                    try {
                        Thread.sleep(500); // Small delay to show drawn card
                        // Find the index of the drawn card
                        int cardIndex = currentPlayer.getHand().size() - 1;
                        // Play the card
                        playCard(cardIndex);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                return; // Return early as the bot will handle its own turn
            }
            // For human players, they decide if they want to play it
            // So we don't move to next player yet
            
        } else {
            // Card not playable, move to next player
            moveToNextPlayer();
        }
    }
    
    /**
     * Set the chosen color for a wild card
     * @param color The chosen color
     */
    public void setWildCardColor(CardColorEnum color) {
        // Set the color on the top card if it's a wild card
        if (currentCard instanceof WildCard) {
            ((WildCard) currentCard).setChosenColor(color);
            
            // Update UI with the new card state
            if (eventListener != null) {
                eventListener.onTopCardChanged(currentCard);
            }
            
            // Always move to next player after color is selected
            // unless the game is already over (player had no cards left)
            if (!gameOver) {
                moveToNextPlayer();
            }
        }
    }
    
    /**
     * Handle the effect of Draw Two or Draw Four cards
     */
    private void handleDrawCardEffect() {
        // Draw cards for the current player
        for (int i = 0; i < cardsToDraw; i++) {
            checkEmptyDeck();
            AbstractCard card = deck.drawCard();
            currentPlayer.addCard(card);
        }
        
        // Notify UI that player's hand has changed
        if (eventListener != null) {
            eventListener.onPlayerHandChanged(currentPlayer);
        }
        
        // Reset the draw cards flag and move to next player
        cardsToDraw = 0;
        nextDrawsCards = false;
        moveToNextPlayer();
    }
    
    /**
     * Switch from clockwise to counterclockwise or vice versa
     */
    private void toggleGameDirection() {
        if (gameDirection == Direction.CLOCKWISE) {
            gameDirection = Direction.COUNTERCLOCKWISE;
        } else {
            gameDirection = Direction.CLOCKWISE;
        }
        
        // Notify UI that direction has changed
        if (eventListener != null) {
            eventListener.onGameDirectionChanged(gameDirection);
        }
    }
    
    /**
     * Move to the next player in sequence, handling skips if needed
     */
    private void moveToNextPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex;
        
        // Determine the next player based on game direction
        if (gameDirection == Direction.CLOCKWISE) {
            nextIndex = (currentIndex + 1) % players.size();
        } else {
            nextIndex = (currentIndex - 1 + players.size()) % players.size();
        }
        
        // If we need to skip, advance one more position
        if (nextSkip) {
            if (gameDirection == Direction.CLOCKWISE) {
                nextIndex = (nextIndex + 1) % players.size();
            } else {
                nextIndex = (nextIndex - 1 + players.size()) % players.size();
            }
            nextSkip = false;
        }
        
        currentPlayer = players.get(nextIndex);
        
        // Notify UI that player has changed
        if (eventListener != null) {
            eventListener.onCurrentPlayerChanged(currentPlayer);
        }
        
        // If next player is a bot, automatically play their turn
        if (currentPlayer instanceof BotPlayer && !gameOver) {
            // Use SwingUtilities.invokeLater to avoid blocking the UI thread
            // and provide a small delay for better visual feedback
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(1000);
                    playBotTurn();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    
    /**
     * Handle bot player's turn
     */
    private void playBotTurn() {
        if (!(currentPlayer instanceof BotPlayer) || gameOver) return;
        
        // Handle draw card effects from previous turns
        if (nextDrawsCards) {
            handleDrawCardEffect();
            return;
        }
        
        // Check if bot has a playable card
        BotPlayer bot = (BotPlayer) currentPlayer;
        boolean hasPlayableCard = false;
        int cardToPlayIndex = -1;
        
        for (int i = 0; i < bot.getHand().size(); i++) {
            if (bot.getHand().get(i).isPlayable(currentCard)) {
                hasPlayableCard = true;
                cardToPlayIndex = i;
                break;
            }
        }
        
        if (hasPlayableCard) {
            // Play the card
            playCard(cardToPlayIndex);
            
            // If we played a wild card, choose a random color
            if (currentCard instanceof WildCard && ((WildCard) currentCard).getChosenColor() == null) {
                // Pick a random color
                CardColorEnum[] colors = {CardColorEnum.RED, CardColorEnum.BLUE, 
                                         CardColorEnum.GREEN, CardColorEnum.YELLOW};
                CardColorEnum chosenColor = colors[new Random().nextInt(colors.length)];
                
                // Set the color
                setWildCardColor(chosenColor);
            }
        } else {
            // No playable card, draw one
            drawCard();
        }
    }
    
    /**
     * Check if the deck is empty and needs replenishing from discard pile
     */
    private void checkEmptyDeck() {
        if (deck.isEmpty()) {
            // Keep the top card from discard pile
            AbstractCard topCard = discardPile.remove(discardPile.size() - 1);
            
            // Put the rest of the discard pile back into the deck
            // Convert List to ArrayList as required by reInitializeDeck
            deck.reInitializeDeck(new ArrayList<>(discardPile));
            
            // Clear the discard pile and put only the top card back
            discardPile.clear();
            discardPile.add(topCard);
        }
    }
    
    /**
     * Returns the current list of players
     */
    public List<Player> getPlayers() {
        return players;
    }
    
    /**
     * Returns the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * Returns the current top card
     */
    public AbstractCard getCurrentCard() {
        return currentCard;
    }
    
    /**
     * Returns the current game direction
     */
    public Direction getGameDirection() {
        return gameDirection;
    }
    
    /**
     * Sets the event listener for game events
     */
    public void setEventListener(GameEventListener listener) {
        this.eventListener = listener;
    }
    
    /**
     * Interface for listening to game events
     */
    public interface GameEventListener {
        void onGameStateChanged();
        void onPlayerHandChanged(Player player);
        void onTopCardChanged(AbstractCard topCard);
        void onCurrentPlayerChanged(Player player);
        void onGameDirectionChanged(Direction direction);
        void onGameOver(Player winner);
        void onCardNotPlayable(Player player, AbstractCard card);
        void onWildCardPlayed(Player player, WildCard card);
    }
}