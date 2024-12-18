package App.CardModel;

import java.util.Objects;

public class ActionCard extends AbstractCard {


    public ActionCard(CardType type, CardColor color) {
        super(type, color);
    }

    @Override
    public boolean isPlayable(Object o) {
        if (Objects.isNull(o)) {
            return false;
        }
        ActionCard actionCard = (ActionCard) o;
        return getColor() == actionCard.getColor();

    }

    public int hashCode() {
        return Objects.hash(getType(), getColor());
    }

    @Override
    public String toString() {
        return "ActionCard{" +
                getType() + ", " + getColor() +
                '}';
    }
}
