package ru.nsu.koshevoi.model;

public enum Levels {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4)
    ;

    public void setValue(int value) {
        this.value = value;
    }

    private int value;
    Levels(int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }
}
