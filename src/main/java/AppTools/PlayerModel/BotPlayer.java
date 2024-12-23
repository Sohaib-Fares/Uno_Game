package AppTools.PlayerModel;

import AppTools.CardModel.AbstractCard;

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
        for (AbstractCard card : hand) {
            if (card.isPlayable(CurrentCard)) {
                hand.remove(card);
            }
                return card;
            }
        return null;
        }
    }