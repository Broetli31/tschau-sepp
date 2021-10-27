package model;

public enum CardType {
    ECKE,
    SCHAUFEL,
    HERZ,
    KREUZ;

    public CardType typeIsSameColour() {
        switch(this) {
            case ECKE:
                return HERZ;
            case HERZ:
                return ECKE;
            case SCHAUFEL:
                return KREUZ;
            case KREUZ:
                return SCHAUFEL;
            default:
                return null;
        }
    }

    public String getName() {
        switch (this) {
            case KREUZ:
                return "kreuz";
            case ECKE:
                return "ecken";
            case SCHAUFEL:
                return "schaufel";
            case HERZ:
                return "herz";
            default:
                return null;
        }
    }
}
