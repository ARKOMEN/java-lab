package ru.nsu.koshevoi.model;

public enum GhostsDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public GhostsDirection opposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                throw new IllegalStateException("Invalid direction");
        }
    }
}
