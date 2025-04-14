package ui;

public enum Sticktype {

    LEFT(17),
    RIGHT(18);

    private final int value;

    Sticktype(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
