package AppService.PlayerModel;

import java.util.Random;

import AppService.CardModel.AbstractCard;
import AppService.CardModel.CardColorEnum;
import AppService.CardModel.WildCard;

public class BotPlayer extends Player {

    public BotPlayer() {
    }

    public BotPlayer(String name) {
        super(name);
    }

    public Boolean hasPlayableCard(AbstractCard currentCard) {
        for (AbstractCard i : hand) {
            if (i.isPlayable(currentCard)) {
                return true;
            }
        }
        return false;
    }

    public AbstractCard playCard(AbstractCard currentCard) {
        for (AbstractCard card : hand) {
            if (card.isPlayable(currentCard)) {
                // So that the bot could choose a random color when a wild played
                if (currentCard instanceof WildCard) {
                    Random rand = new Random();
                    ((WildCard) currentCard).setChosenColor(CardColorEnum.values()[rand.nextInt(4)]);
                }
                hand.remove(card);
                return card;
            }
        }
        return null;
    }
}