package view;

public class InvalidAmountException extends Exception {

    InvalidAmountType type;

    public InvalidAmountException(InvalidAmountType type) {
        super();
        this.type = type;
    }

    public InvalidAmountType getType() {
        return this.type;
    }
}
