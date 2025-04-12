package AppService.CardModel;

import java.util.Objects;

public class ActionCard extends AbstractCard {

    public ActionCard(CardTypeEnum type, CardColorEnum color) {
        super(type, color);
    }

    @Override
    public boolean isPlayable(AbstractCard card) {
        if (Objects.isNull(card)) {
            return false;
        }
        return getColor() == card.getColor() || getType() == card.getType();

    }

    public int hashCode() {
        return Objects.hash(getType(), getColor());
    }

    @Override
    public String toString() {
        return "[ActionCard, " +
                getType() + ", " + getColor() +
                " ]";
    }
}
