package AppTools.CardModel;

public abstract class AbstractCard {
    private final CardTypeEnum type;
    private final CardColorEnum color;

    public AbstractCard(CardTypeEnum type, CardColorEnum color) {
        this.type = type;
        this.color = color;
    }

    public CardTypeEnum getType() {
        return type;
    }

    public CardColorEnum getColor() {
        return color;
    }

    public abstract boolean isPlayable(AbstractCard card);

    public abstract int hashCode();

    public abstract String toString();
}

