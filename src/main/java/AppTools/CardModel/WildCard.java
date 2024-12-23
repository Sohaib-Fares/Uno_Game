package AppTools.CardModel;

import java.util.Objects;

public class WildCard extends AbstractCard {

    private CardColor chosenColor;

    public WildCard(CardType type, CardColor color) {
        super(type, color);
    }

    @Override
    public boolean isPlayable(AbstractCard card) {
        if (Objects.isNull(card)){
            return false;
        }

            return true;

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
