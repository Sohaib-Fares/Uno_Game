package App.PlayerModel;

import App.CardModel.AbstractCard;

public class BotPlayer extends Player {

    public  Boolean HasPlayableCard(AbstractCard CurrentCard){        
        for (AbstractCard i : hand){
        if(i.IsPlayable(CurrentCard)){
        return true ;}     
        }
        return false;}

    public void PlayCard(Deck Deck1, AbstractCard CurrentCard){
        int j=0;
        for (AbstractCard i : hand){
            if(i.IsPlayable(CurrentCard))
            Deck1.add(i);
            hand.remove(j);
            j++;
          }

}}