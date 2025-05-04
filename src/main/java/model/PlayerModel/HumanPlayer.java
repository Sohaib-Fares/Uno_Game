package model.PlayerModel;

import java.util.ArrayList;

import model.CardModel.AbstractCard;

public class HumanPlayer extends Player {

    public HumanPlayer() {
    }

    public HumanPlayer(String name) {
        super(name);
    }

    public void showHand() {
        System.out.println(getName() + "'s hand:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + "- " + hand.get(i));
        }
        System.out.println();
    }

    public boolean hasPlayableCard(ArrayList<AbstractCard> hand, AbstractCard currentCard) {

        for (AbstractCard card : hand) {
            if (card.isPlayable(currentCard)) {
                return true;
            }
        }
        return false;
    }

    public AbstractCard playCard(ArrayList<AbstractCard> hand, AbstractCard currentCard, int index) {
        // Validate the hand and index
        if (hand == null) {
            throw new IllegalArgumentException("Hand cannot be null.");
        }

        // Check if the card at the specified index is playable
        AbstractCard selectedCard = hand.get(index);
        if (!selectedCard.isPlayable(currentCard)) {
            System.out.println("Invalid input: card at index " + index + " is not playable.");
            return null;
        }

        // Remove and return the playable card to be added to discardPile in main
        return hand.remove(index);
    }

}