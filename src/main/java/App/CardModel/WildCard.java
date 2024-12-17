package App.CardModel;

import java.util.Objects;

public class WildCard extends AbstractCard {

    public WildCard(String type, String color) {
        super(type, color);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
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
