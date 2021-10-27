package model;

public enum CardValue {
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING,
    ACE;

    // This method might be unnecessary, as this is not Jass. Check the rules for clarity.
    public int getRank() {
        switch (this) {
            case TWO:
                return 0;
            case THREE:
                return 1;
            case FOUR:
                return 2;
            case FIVE:
                return 3;
            case SIX:
                return 4;
            case SEVEN:
                return 5;
            case EIGHT:
                return 6;
            case NINE:
                return 7;
            case TEN:
                return 8;
            case JACK:
                return 9;
            case QUEEN:
                return 10;
            case KING:
                return 11;
            case ACE:
                return 12;
            default:
                return -1;
        }
    }

    public String getName() {
        switch (this) {
            case TWO:
                return "two";
            case THREE:
                return "three";
            case FOUR:
                return "four";
            case FIVE:
                return "five";
            case SIX:
                return "six";
            case SEVEN:
                return "seven";
            case EIGHT:
                return "eight";
            case NINE:
                return "nine";
            case TEN:
                return "ten";
            case JACK:
                return "jack";
            case QUEEN:
                return "queen";
            case KING:
                return "king";
            case ACE:
                return "ace";
            default:
                return null;
        }
    }

    public int calculatePoints() {
        switch (this) {
            case ACE:
                return 11;
            case KING:
                return 4;
            case QUEEN:
                return 3;
            case JACK:
                return 20;
            case TEN:
                return 10;
            case NINE:
                return 9;
            case EIGHT:
                return 8;
            case SEVEN:
                return 7;
            case SIX:
                return 6;
            default:
                return 0;
        }
    }
}