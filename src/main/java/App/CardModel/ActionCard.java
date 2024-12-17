package App.CardModel;

public class ActionCard extends AbstractCard {


    public ActionCard(CardType type, CardColor color) {
        super(type, color);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }
}
