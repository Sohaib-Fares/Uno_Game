package AppTools.CardModel;

import java.util.Objects;

public class NumberedCard extends AbstractCard {

    private final int value;

    public NumberedCard(CardColor color, int value){
        super(CardType.NUMBER, color);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean isPlayable(AbstractCard card) {
        if (Objects.isNull(card)) {
            return false;
        }
        NumberedCard numberedCard = (NumberedCard) card;
        return getColor() == numberedCard.getColor() || getValue() == numberedCard.getValue();

    }

    public int hashCode() {
        return Objects.hash(value, getColor());
    }

    @Override
    public String toString() {
        return "[NumberedCard, " +
                value +", " + getColor() +
                " ]";
    }
}
