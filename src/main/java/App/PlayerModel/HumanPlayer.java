package App.PlayerModel;

import java.util.ArrayList;

import App.CardModel.AbstractCard;

public class HumanPlayer extends Player {

    public void showHand() {
        System.out.println(name + "'s hand: " + hand);
    }

    public ArrayList<AbstractCard> CheckPossibleCards(AbstractCard TopCard, ArrayList<AbstractCard> isplayble) {
        for(AbstractCard i : hand) {
            if(i.IsPlayable(TopCard)) {
                isplayble.add(i);
            }
        }
        return isplayble;
    }

    public boolean playCard(ArrayList<AbstractCard> possibleCards, int n, ArrayList<AbstractCard> playedCards) {
        for(AbstractCard i : this.hand) {
            if(i == possibleCards.get(n)) {
                playedCards.add(possibleCards.get(n));
                this.hand.remove(n);
                return true;
            }
        }
        return false;
    }
}