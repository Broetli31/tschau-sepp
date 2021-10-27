package model;

import javax.swing.*;
import java.util.Vector;

public class Player extends DefaultListModel<Card> {

    private String name;
    private Vector<Card> cards;
    private Call call;
    private int points;
    private String iconFileName;

    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.cards = new Vector<>();
        this.call = Call.NONE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCard(Card card) {
        cards.add(card);
        this.fireIntervalAdded(this, (this.cards.size() - 1), (this.cards.size() - 1));
    }

    public void addCards(Vector<Card> cards) {

        for (Card card : cards) {
            this.cards.add(card);
        }

        this.fireIntervalAdded(this, 0, (this.cards.size() - 1));

        // TODO eventuell noch abändern
    }

    public Card removeCard(int index) {
        Card removedCard = this.cards.remove(index);
        this.fireIntervalRemoved(this, index, index);
        return removedCard;
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public Vector<Card> getCards() {
        return this.cards;
    }

    @Override
    public Card getElementAt(int index) {
        return getCard(index);
    }

    @Override
    public int getSize() {
        return this.cards.size();
    }

    public int getAmountOfCards() {
        return cards.size();
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        this.points = this.points + points;
    }

    public String getIconFileName() {
        return iconFileName;
    }

    public void setIconFileName(String iconFileName) {
        this.iconFileName = iconFileName;
    }
}
