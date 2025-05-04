package model.PlayerModel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import model.CardModel.AbstractCard;

public abstract class Player {

    private String name;
    private Color color; // Added color property
    protected ArrayList<AbstractCard> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>(); // Initialize hand
    }

    public Player() {
        this.hand = new ArrayList<>(); // Initialize hand in default constructor too
    }

    public boolean HasCard() {
        return !hand.isEmpty();
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        name = Name;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayList<AbstractCard> getHand() {
        return hand;
    }

    public void setHand(ArrayList<AbstractCard> Hand) {
        hand = Hand;
    }

    public ArrayList<AbstractCard> removeCard(int index) {
        hand.remove(index);
        return hand;
    }

    public void addCard(AbstractCard card) {
        hand.add(card);
    }

    /**
     * Plays a card from the player's hand
     * @param hand The player's hand of cards
     * @param currentCard The current top card on the discard pile
     * @param index The index of the card to play
     * @return The played card, or null if the card cannot be played
     */
    public AbstractCard playCard(List<AbstractCard> hand, AbstractCard currentCard, int index) {
        // Validate the hand and index
        if (hand == null || index < 0 || index >= hand.size()) {
            return null;
        }

        // Check if the card at the specified index is playable
        AbstractCard selectedCard = hand.get(index);
        if (!selectedCard.isPlayable(currentCard)) {
            return null;
        }

        // Remove and return the playable card
        return hand.remove(index);
    }

    @Override
    public String toString() {
        return name;
    }
}
