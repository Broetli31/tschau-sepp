package model;

public class Settings {
    private int initialCardCount;
    private int pointsNeededToWin;
    private boolean putTwoIdenticalCardsIsPossible;
    private int playerCount;

    public Settings() {

    }

    public int getInitialCardCount() {
        return initialCardCount;
    }

    public void setInitialCardCount(int initialCardCount) {
        this.initialCardCount = initialCardCount;
    }

    public int getPointsNeededToWin() {
        return pointsNeededToWin;
    }

    public void setPointsNeededToWin(int pointsNeededToWin) {
        this.pointsNeededToWin = pointsNeededToWin;
    }

    public boolean isPutTwoIdenticalCardsIsPossible() {
        return putTwoIdenticalCardsIsPossible;
    }

    public void setPutTwoIdenticalCardsIsPossible(boolean putTwoIdenticalCardsIsPossible) {
        this.putTwoIdenticalCardsIsPossible = putTwoIdenticalCardsIsPossible;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
}
