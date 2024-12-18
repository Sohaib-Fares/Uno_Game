package App.PlayerModel;

import java.util.ArrayList;
import App.CardModel.AbstractCard;


public abstract class Player {
private String name;
ArrayList<AbstractCard> hand = new ArrayList<>();

public void AddCard(Deck Deck2){
    hand.add(Deck2.get(Deck2.size()));
    Deck2.remove(Deck2.size());
}
public boolean HasCard(){
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
public String toString() {
    return name;
}
}

