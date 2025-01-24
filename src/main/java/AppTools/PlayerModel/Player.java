package AppTools.PlayerModel;

import java.util.ArrayList;

import AppTools.CardModel.AbstractCard;

public abstract class Player {

    private String name;
    protected ArrayList<AbstractCard> hand;

    public Player(String name) {
        this.name = name;
    }

    public Player() {
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

    @Override
    public String toString() {
        return name;
    }
}
