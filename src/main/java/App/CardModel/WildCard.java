package App.CardModel;

import java.util.Objects;

public class WildCard extends AbstractCard {

    private CardColorEnum chosenColor;

    public WildCard(CardTypeEnum type, CardColorEnum color) {
        super(type, color);
    }

    public void setChosenColor(CardColorEnum chosenColor) {
        this.chosenColor = chosenColor;
    }

    @Override
    public CardColorEnum getColor() {
        return this.chosenColor;
    }

    @Override
    public boolean isPlayable(AbstractCard card) {
        if (Objects.isNull(card)) {
            return false;
        }

        // WildCard is always playable
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getColor());
    }

    @Override
    public String toString() {
        return "[WildCard, " +
                getType() + ", " + (chosenColor != null ? chosenColor : getColor()) +
                " ]";
    }

}
