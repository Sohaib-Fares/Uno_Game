package App.Deck;

import App.CardModel.*;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<AbstractCard> cards;
    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
        shuffle();
    }

    private void initializeDeck() {
        createWildCards();
        createActionCards();
        createNumberCards();
    }

    private void createNumberCards() {
        for (var color : CardColor.values()) {
            cards.add(new NumberedCard(color, 0));

            for (var i = 1; i <= 9; i++) {
                cards.add(new NumberedCard(color, i));
                cards.add(new NumberedCard(color, i));
            }
        }
    }

    private void createActionCards() {
        for (var color : CardColor.values()) {
            for (var i = 0; i < 2; i++) {
                cards.add(new ActionCard(CardType.SKIP, color));
                cards.add(new ActionCard(CardType.REVERSE, color));
                cards.add(new ActionCard(CardType.DRAW_TWO, color));
            }
        }
    }

    private void createWildCards() {
        for (var i = 0; i < 4; i++) {
            cards.add(new WildCard(CardType.WILD_COLOR, null));
            cards.add(new WildCard(CardType.WILD_DRAW_FOUR, null));
        }
    }


        public void shuffle(){
        Collections.shuffle(cards);
    }

}
