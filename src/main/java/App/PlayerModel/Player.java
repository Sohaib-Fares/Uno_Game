package App.PlayerModel;

import java.util.ArrayList;
import App.CardModel.AbstractCard;


public abstract class Player {

private String name;
ArrayList<AbstractCard> hand;

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

@Override
public String toString() {
    return name;
}
}

