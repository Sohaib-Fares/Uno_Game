package AppTools.Deck;

import org.junit.jupiter.api.Test;

import AppTools.Deck.Deck;

class DeckTest {

    @Test
    void initializeDeck() {
        Deck deck = new Deck();
        System.out.println("Deck initialized");
        System.out.println(deck.toString());
    }

}