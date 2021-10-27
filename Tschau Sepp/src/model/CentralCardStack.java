package model;

import java.util.ArrayDeque;
import java.util.Vector;

public class CentralCardStack {
    private ArrayDeque<Card> cards;

    public CentralCardStack() {
        cards = new ArrayDeque<>();
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public Vector<Card> getCards(int count) {
        Vector<Card> requestedCards = new Vector<>();

        for (int i = 0; i < count; i++) {
            requestedCards.add(cards.poll()); // returns null if no element is found
        }

        return requestedCards;
    }

    public int getSize() {
        return this.cards.size();
    }

    public Card getTopCard() {
        return cards.getLast(); // returns null if no element is found
    }
}
