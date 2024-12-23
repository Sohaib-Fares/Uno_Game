package App.Deck;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void initializeDeck() {
        Deck deck = new Deck();
        System.out.println("Deck initialized");
        System.out.println(deck.toString());
    }


}