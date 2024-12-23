package App.CardModel;

import java.util.Objects;

public class WildCard extends AbstractCard {

    private CardColor chosenColor;

    public WildCard(CardType type, CardColor color) {
        super(type, color);
    }

    @Override
    public boolean isPlayable(Object o) {
        if (Objects.isNull(o)) {
            return false;
        }
            WildCard wildCard = (WildCard) o;
            return getColor() == wildCard.getColor();

    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getColor());
    }

    @Override
    public String toString() {
        return "[WildCard, " +
                getType() + ", " + getColor() +
                " ]";
    }

}
