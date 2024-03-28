package ru.nsu.koshevoi.model;

public interface GameObject {
    void move(Direction direction);
    int getX();
    int getY();
}
