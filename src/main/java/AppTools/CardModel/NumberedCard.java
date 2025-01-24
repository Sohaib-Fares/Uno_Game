package AppTools.CardModel;

import java.util.Objects;

public class NumberedCard extends AbstractCard {

    private final int value;

    public NumberedCard(CardColorEnum color, int value) {
        super(CardTypeEnum.NUMBER, color);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean isPlayable(AbstractCard currentCard) {
        if (currentCard instanceof NumberedCard) {
            NumberedCard numberedCard = (NumberedCard) currentCard;
            return getColor().equals(numberedCard.getColor()) || this.value == numberedCard.getValue();
        } else if (currentCard instanceof ActionCard) {
            return getColor().equals(currentCard.getColor());
        } else if (currentCard instanceof WildCard) {
            return getColor().equals(currentCard.getColor());
        } else {
            System.out.println("************-----------************");
            System.err.println("The card is not a numbred, it is: " + currentCard.getClass());
            System.out.println("************-----------************");
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(value, getColor());
    }

    @Override
    public String toString() {
        return "[NumberedCard, " +
                value + ", " + getColor() +
                " ]";
    }
}
