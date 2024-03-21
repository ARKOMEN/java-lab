package ru.nsu.koshevoi.model;

public enum PacManDirection {
    NONE,
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public PacManDirection opposite() {
        return NONE;
    }
}
