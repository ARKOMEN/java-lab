package ru.nsu.koshevoi.model;

public enum Direction {
    NONE,
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public Direction opposite() {
        return NONE;
    }
}
