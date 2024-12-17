package App.CardModel;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberedCard that = (NumberedCard) o;
        return value == that.value && getColor() == that.getColor();
    }

    public int hashCode() {
        return Objects.hash(value, getColor());
    }

    @Override
    public String toString() {
        return "";
    }
}
