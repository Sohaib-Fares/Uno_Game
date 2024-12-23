package App.PlayerModel;

import App.CardModel.AbstractCard;

public class BotPlayer extends Player {

    public BotPlayer() {
    }

    public BotPlayer(String name) {
        super(name);
    }

    public  Boolean hasPlayableCard(AbstractCard CurrentCard){
        for (AbstractCard i : hand){
            if(i.isPlayable(CurrentCard)){
                return true ;
            }
        }
        return false;
    }

    public AbstractCard playCard(AbstractCard CurrentCard) {
        for (AbstractCard i : hand) {
            if (i.isPlayable(CurrentCard)) {
                hand.remove(i);
            }
                return i;
            }
        return null;
        }
    }