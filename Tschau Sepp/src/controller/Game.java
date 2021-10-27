package controller;

import model.*;
import view.GameView;
import view.SetupView;

import javax.swing.*;
import java.util.Collections;
import java.util.Vector;

public class Game extends DefaultListModel<Player> {

    private Vector<Player> players;
    private int currentPlayer;
    private Settings settings;
    private CentralCardStack centralCardStack;
    private NewCardStack newCardStack;
    private GameView view;


    public Game() {
        players = new Vector<>();
        settings = new Settings();
        centralCardStack = new CentralCardStack();
        newCardStack = new NewCardStack();
        currentPlayer = 0;

        new SetupView(this);
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Vector<Player> getPlayers() {
        return players;
    }

    @Override
    public Player getElementAt(int index) {
        return players.get(index);
    }

    @Override
    public int getSize() {
        return this.players.size();
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int player) {
        this.currentPlayer = player;
    }

    public CentralCardStack getCentralCardStack() {
        return centralCardStack;
    }

    public NewCardStack getNewCardStack() {
        return newCardStack;
    }

    public void putCardsToCentralStack(Vector<Integer> selectedCards) {
        Player currentPlayer = players.get(this.currentPlayer);

        Collections.sort(selectedCards, Collections.reverseOrder());

        for (Integer number : selectedCards) {
            Card card = currentPlayer.removeCard(number.intValue());
            centralCardStack.addCard(card);
        }

        this.fireContentsChanged(this, this.currentPlayer, this.currentPlayer);

        if (currentPlayer.getAmountOfCards() == 0) {
            calculatePoints();
        } if (currentPlayer.getPoints() >= settings.getPointsNeededToWin()) {

        }

        if (this.currentPlayer == (players.size() - 1)) {
            this.currentPlayer = 0;
        } else {
            this.currentPlayer = this.currentPlayer + 1;
        }
    }

    public void start() {
        this.createCards();
        this.mixCardsInNewCardStack(4);
        this.setUpPlayers();
        this.distributeCards();

        view = new GameView(this);
    }

    public void calculatePoints() {
        Player currentPlayer = this.players.get(this.currentPlayer);

        for (Player player : players) {
            for (Card card : player.getCards()) {
                currentPlayer.addPoints(card.getCardValue().calculatePoints());
            }
        }
    }

    private void setUpPlayers() {
        for (int i = 0; i < this.settings.getPlayerCount(); i++) {
            Player player = new Player(("Spieler " + (i + 1)));
            player.setIconFileName("sp" + (i + 1) + "icon.png");
            this.players.add(player);
        }
    }

    // Used for JUnit-Tests
    public void setUpPlayers(int amountOfPlayers) {
        for (int i = 0; i < amountOfPlayers; i++) {
            Player player = new Player(("Spieler " + (i + 1)));
            this.players.add(player);
        }
    }

    public void createCards() {
        int numberOfCardSets = 0;

        while (numberOfCardSets < 2) {
            for (CardType type : CardType.values()) {
                for (CardValue value : CardValue.values()) {
                    Card card = new Card(type, value);
                    newCardStack.addCard(card);
                }
            }

            numberOfCardSets = numberOfCardSets + 1;
        }
    }

    public void resetCards() {
        for (Player player : this.players) {
            Vector<Integer> cardIndices = new Vector<>();
            for (int i = 0; i < player.getAmountOfCards(); i++) {
                cardIndices.add(i);
            }
            Collections.sort(cardIndices, Collections.reverseOrder());

            for (Integer num : cardIndices) {
                this.newCardStack.addCard(player.removeCard(num));
            }


        }

        newCardStack.addCards(this.centralCardStack.getCards(centralCardStack.getSize() - 1));
        this.mixCardsInNewCardStack(4);
        this.distributeCards();
    }

    private void distributeCards() {
        for (Player player : this.players) {
            player.addCards(this.newCardStack.getCards(this.settings.getInitialCardCount()));
        }

        this.fireContentsChanged(this, 0, this.settings.getPlayerCount());

        Vector<Card> cards = this.newCardStack.getCards(1);
        this.centralCardStack.addCard(cards.get(0));
    }

    public void getCardsFromNewStack(int numberOfCards) {
        // TODO Implement logic in this method and the one above
        Player currentPlayer = players.get(this.currentPlayer);

        Vector<Card> newCards = newCardStack.getCards(numberOfCards);
        currentPlayer.addCards(newCards);
        this.fireContentsChanged(this, this.currentPlayer, this.currentPlayer);
    }

    // to be called by the view.
    public boolean turnIsValid(Card firstCard) {
        Card topCard = this.centralCardStack.getTopCard();

        if (firstCard.getCardType() == topCard.getCardType() ||
            firstCard.getCardType() == topCard.getCardType().typeIsSameColour() ||
            firstCard.getCardValue() == topCard.getCardValue()) {

            this.playerDidCallCorrectly();
            return true;
        } else {
            return false;
        }
    }

    // TODO implement this method
    public boolean turnIsValid(Card firstCard, Card secondCard) {


        return false;
    }

    private void playerDidCallCorrectly() {
        Player currentPlayer = players.get(this.currentPlayer);

        if (currentPlayer.getAmountOfCards() == 2 &&
            currentPlayer.getCall() != Call.TSCHAU) {

            this.getCardsFromNewStack(2);
        } else if (currentPlayer.getAmountOfCards() == 1 &&
                   currentPlayer.getCall() != Call.SEPP) {

            this.getCardsFromNewStack(4);
        } else if (currentPlayer.getCall() != Call.NONE) {

            this.getCardsFromNewStack(2); // May have to be adjusted, because this is in theory not part
                                                         // of the general rules.
        }
    }

    public void mixCardsInNewCardStack(int requestedAmountOfRunThroughs) {
        this.newCardStack.mixCards(requestedAmountOfRunThroughs);
    }

}
