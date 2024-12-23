package AppTools.Deck;

import org.junit.jupiter.api.Test;

class DeckTest {

    @Test
    void initializeDeck() {
        Deck deck = new Deck();
        System.out.println("Deck initialized");
        System.out.println(deck.toString());
    }


}