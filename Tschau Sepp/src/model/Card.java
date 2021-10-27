package model;

public class Card {

    private CardType cardType;
    private CardValue cardValue;

    public Card(CardType type, CardValue value) {
        this.cardType = type;
        this.cardValue = value;
    }

    public String getFilename() {
        return ("/img/cardicons/" + cardType.getName() + cardValue.getName() + "_fr.gif");
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardValue(CardValue cardValue) {
        this.cardValue = cardValue;
    }

    public CardValue getCardValue() {
        return cardValue;
    }
}
