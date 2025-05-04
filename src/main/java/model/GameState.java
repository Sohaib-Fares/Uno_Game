package model;

import java.util.List;

import AppService.Game.CardsEffect;
import AppService.Game.Direction;
import model.CardModel.AbstractCard;
import model.Deck.Deck;
import model.PlayerModel.Player;

public class GameState {
    private List<Player> players;
    private List<AbstractCard> discardPile;
    private Deck deck;
    Direction gameDirection;
    AbstractCard currentCard;
    boolean gmaeIsOver;
    CardsEffect cardsEffect;

}
