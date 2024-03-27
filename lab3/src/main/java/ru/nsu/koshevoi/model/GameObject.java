package ru.nsu.koshevoi.model;

public interface GameObject {
    public void move(Direction direction);
    public int getX();
    public int getY();
}
