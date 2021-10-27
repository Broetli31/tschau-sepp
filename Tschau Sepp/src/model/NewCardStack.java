package model;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Vector;

public class NewCardStack {

    private ArrayDeque<Card> cards;

    public NewCardStack() {
        cards = new ArrayDeque<>();
    }

    public void clear() {
        this.cards.clear();
    }

    public int getSize() {
        return this.cards.size();
    }

    public Card[] getAllCardsAsArray() {

        Card[] cards = this.cards.toArray(new Card[this.cards.size()]);
        return cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void addCards(Vector<Card> cards) {
        for (Card card : cards) {
            this.cards.add(card);
        }
    }

    public Vector<Card> getCards(int numberOfCards) {
        Vector<Card> requestedCards = new Vector<>();

        for (int i = 0; i < numberOfCards; i++) {
            requestedCards.add(cards.poll()); // returns null if no element is found
        }

        return requestedCards;
    }

    public void mixCards(int requestedAmountOfRunThroughs) {
        Card[] allCards = this.getAllCardsAsArray();
        cards.clear();

        int otherCard;
        Card shortTermCardHolder;
        int amountOfRunThroughs = 0;

        while (amountOfRunThroughs < requestedAmountOfRunThroughs) {
            for (int i = 0; i < allCards.length; i++) {
                otherCard = (int) (Math.random() * allCards.length);
                shortTermCardHolder = allCards[otherCard];
                allCards[otherCard] = allCards[i];
                allCards[i] = shortTermCardHolder;
            }

            amountOfRunThroughs = amountOfRunThroughs + 1;
        }

        this.cards.addAll(Arrays.asList(allCards));
    }
}
