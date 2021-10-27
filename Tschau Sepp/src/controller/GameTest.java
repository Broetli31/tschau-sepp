package controller;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class GameTest {

    private static Game game;


    @Before
    public void setUpClass() {
        game = new Game();
        Settings settings = game.getSettings();

        settings.setInitialCardCount(7);
        settings.setPlayerCount(4);

        game.setUpPlayers(settings.getPlayerCount());
        game.createCards();

        int i = 0;
        for (Player player : game.getPlayers()) {
            game.getCardsFromNewStack(settings.getInitialCardCount());
            game.setCurrentPlayer(i + 1);
        }

        game.setCurrentPlayer(0);
    }

    @Test
    public void cardsGotMixed() {
        Card[] cardsBeforeMixed = game.getNewCardStack().getAllCardsAsArray();

        this.game.mixCardsInNewCardStack(2);

        Card[] cardsAfterMixed = game.getNewCardStack().getAllCardsAsArray();

        if (cardsBeforeMixed[1] != cardsAfterMixed[1] ||
                cardsBeforeMixed[6] != cardsAfterMixed[6] ||
                cardsBeforeMixed[11] != cardsAfterMixed[11] ||
                cardsBeforeMixed[18] != cardsBeforeMixed[18]) {

            assertTrue(true);
        } else {
            fail();
        }
    }

    @Test
    public void amountOfPlayersIsCorrect() {
        assertEquals(4, game.getPlayers().size());
    }

    @Test
    public void amountOfCardsIsCorrect() {
        game.getNewCardStack().clear();
        game.createCards();
        assertEquals(104, game.getNewCardStack().getSize());
    }

    @Test
    public void amountOfEachCardTypeIsCorrect() {
        game.getNewCardStack().clear();
        game.createCards();
        Vector<Card> allCards = game.getNewCardStack().getCards(game.getNewCardStack().getSize());

        int kreuzAmount = 0;
        int schaufelAmount = 0;
        int eckenAmount = 0;
        int herzAmount = 0;
        for (Card card : allCards) {
            if (card.getCardType() == CardType.ECKE) {
                eckenAmount = eckenAmount + 1;
            } else if (card.getCardType() == CardType.HERZ) {
                herzAmount = herzAmount + 1;
            } else if (card.getCardType() == CardType.SCHAUFEL) {
                schaufelAmount = schaufelAmount + 1;
            } else if (card.getCardType() == CardType.KREUZ) {
                kreuzAmount = kreuzAmount + 1;
            }
        }

        if (herzAmount == schaufelAmount &&
                herzAmount == eckenAmount &&
                herzAmount == kreuzAmount) {
            assertEquals(26, herzAmount);
        } else {
            fail();
        }
    }

    @Test
    public void amountOfEachCardValueIsCorrect() {
        game.getNewCardStack().clear();
        game.createCards();
        Vector<Card> allCards = game.getNewCardStack().getCards(game.getNewCardStack().getSize());

        int aceAmount = 0;
        int kingAmount = 0;
        int queenAmount = 0;
        int jackAmount = 0;
        int tenAmount = 0;
        int nineAmount = 0;
        int eightAmount = 0;
        int sevenAmount = 0;
        int sixAmount = 0;
        int fiveAmount = 0;
        int fourAmount = 0;
        int threeAmount = 0;
        int twoAmount = 0;


        for (Card card : allCards) {
            switch (card.getCardValue()) {
                case ACE:
                    aceAmount++;
                    break;
                case KING:
                    kingAmount++;
                    break;
                case QUEEN:
                    queenAmount++;
                    break;
                case JACK:
                    jackAmount++;
                    break;
                case TEN:
                    tenAmount++;
                    break;
                case NINE:
                    nineAmount++;
                    break;
                case EIGHT:
                    eightAmount++;
                    break;
                case SEVEN:
                    sevenAmount++;
                    break;
                case SIX:
                    sixAmount++;
                    break;
                case FIVE:
                    fiveAmount++;
                    break;
                case FOUR:
                    fourAmount++;
                    break;
                case THREE:
                    threeAmount++;
                    break;
                case TWO:
                    twoAmount++;
                    break;
            }
        }

        if (aceAmount == kingAmount &&
            aceAmount == queenAmount &&
            aceAmount == jackAmount &&
            aceAmount == tenAmount &&
            aceAmount == nineAmount &&
            aceAmount == eightAmount &&
            aceAmount == sevenAmount &&
            aceAmount == sixAmount &&
            aceAmount == fiveAmount &&
            aceAmount == fourAmount &&
            aceAmount == threeAmount &&
            aceAmount == twoAmount) {

            assertEquals(8, aceAmount);
        } else {
            fail();
        }

    }

    @Test
    public void totalAmountOfCardsThatPlayerHoldsIsCorrect() {
        assertEquals(7, game.getPlayers().get(game.getCurrentPlayer()).getAmountOfCards());
    }

    @Test
    public void playerGetsCardsFromNewStack() {
        game.getCardsFromNewStack(4);

        assertEquals(11, game.getPlayers().get(game.getCurrentPlayer()).getAmountOfCards());
    }

    @Test
    public void cardsAreRemovedFromNewCardStackWhenPlayerTakesThem() {
        int originalAmount = game.getNewCardStack().getSize();

        game.getCardsFromNewStack(4);

        assertEquals(originalAmount - 4, game.getNewCardStack().getSize());
    }

    @Test
    public void playersNameWasSetProperly() {
        Vector<Player> players = game.getPlayers();

        assertEquals("Spieler 2", players.get(1).getName());
    }

    @Test
    public void cardOfSameColourIsValid() {
        Card card1 = new Card(CardType.HERZ, CardValue.ACE);
        Card card2 = new Card(CardType.ECKE, CardValue.EIGHT);

        game.getCentralCardStack().addCard(card1);

        assertTrue(game.turnIsValid(card2));
    }

    @Test
    public void cardOfSameTypeIsValid() {
        Card card1 = new Card(CardType.HERZ, CardValue.ACE);
        Card card2 = new Card(CardType.HERZ, CardValue.EIGHT);

        game.getCentralCardStack().addCard(card1);

        assertTrue(game.turnIsValid(card2));
    }

    @Test
    public void cardOfSameValueButDifferentColourIsValid() {
        Card card1 = new Card(CardType.HERZ, CardValue.ACE);
        Card card2 = new Card(CardType.SCHAUFEL, CardValue.ACE);

        game.getCentralCardStack().addCard(card1);

        assertTrue(game.turnIsValid(card2));
    }

    @Test
    public void playerGetsTwoCardsWhenFalselySayingTschau() {
        game.getPlayers().get(game.getCurrentPlayer()).setCall(Call.TSCHAU);

        Card card1 = new Card(CardType.HERZ, CardValue.ACE);
        Card card2 = new Card(CardType.SCHAUFEL, CardValue.ACE);

        game.getCentralCardStack().addCard(card1);

        game.turnIsValid(card2);

        assertEquals(9, game.getPlayers().get(game.getCurrentPlayer()).getAmountOfCards());
    }

    @Test
    public void playerGetsFourCardsWhenHeDidntSaySepp() {
        game.getPlayers().get(game.getCurrentPlayer()).setCall(Call.NONE);

        Card card1 = new Card(CardType.HERZ, CardValue.ACE);
        Card card2 = new Card(CardType.SCHAUFEL, CardValue.ACE);

        Vector<Integer> almostAllCards = new Vector<>();

        for (int i = 0; i < 6; i++) {
            almostAllCards.add(i);
        }

        game.putCardsToCentralStack(almostAllCards);

        game.getCentralCardStack().addCard(card1);

        game.turnIsValid(card2);

        assertEquals(5, game.getPlayers().get(game.getCurrentPlayer()).getAmountOfCards());
    }

    @Test
    public void cardIsRemovedFromPlayerWhenPutCardsToCentralStackIsInvoked() {
        Vector<Integer> aCard = new Vector<>();
        aCard.add(0);

        game.putCardsToCentralStack(aCard);

        assertEquals(6, game.getPlayers().get(0).getAmountOfCards());
    }

}