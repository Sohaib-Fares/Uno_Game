package model.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

import model.CardModel.*;

public class Deck {

    private ArrayList<AbstractCard> cards;

    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
        if (cards.isEmpty()) {
            System.err.println("Empty!");
        }
        shuffle();
    }

    public void reInitializeDeck(ArrayList<AbstractCard> discardPile) {
        if (discardPile.isEmpty()) {
            throw new NoSuchElementException("Discard pile is empty.");
        }
        AbstractCard topCard = discardPile.remove(discardPile.size() - 1);
        cards.addAll(discardPile);
        discardPile.clear();
        discardPile.add(topCard);
        shuffle();
        System.out.println("----- Deck has been re shuffled ! -----");
    }

    private void initializeDeck() {
        createWildCards();
        createActionCards();
        createNumberCards();
    }

    
    private void createNumberCards() {
        for (var color : CardColorEnum.values()) {
            cards.add(new NumberedCard(color, 0));

            for (var i = 1; i <= 9; i++) {
                cards.add(new NumberedCard(color, i));
                cards.add(new NumberedCard(color, i));
            }
        }
    }

    private void createActionCards() {
        for (var color : CardColorEnum.values()) {
            for (var i = 0; i < 2; i++) {
                cards.add(new ActionCard(CardTypeEnum.SKIP, color));
                cards.add(new ActionCard(CardTypeEnum.REVERSE, color));
                cards.add(new ActionCard(CardTypeEnum.DRAW_TWO, color));
            }
        }
    }

    private void createWildCards() {
        for (var i = 0; i < 4; i++) {
            cards.add(new WildCard(CardTypeEnum.WILD_COLOR, null));
            cards.add(new WildCard(CardTypeEnum.WILD_DRAW_FOUR, null));
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public AbstractCard drawCard() {
        if (cards.isEmpty()) {
            throw new NoSuchElementException("The deck is empty.");
        }
        return cards.remove(0);
    }
    
    // TODO: Used for tests only, MUST-REMOVE in the end
    @Override
    public String toString() {
        return cards.toString();
    }

    public ArrayList<AbstractCard> getRemainingCards() {
        return new ArrayList<>(cards);
    }

    public Boolean isEmpty() {
        return cards.isEmpty();
    }

}