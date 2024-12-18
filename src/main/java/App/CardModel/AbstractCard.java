package App.CardModel;

public abstract class AbstractCard {
    private final CardType type;
    private final CardColor color;

    public AbstractCard(CardType type, CardColor color) {
        this.type = type;
        this.color = color;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public abstract boolean isPlayable(Object o);

    public abstract int hashCode();

    public abstract String toString();
}

