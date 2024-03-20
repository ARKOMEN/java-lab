package ru.nsu.koshevoi.model;

public class PowerPellet {
    private int x;
    private int y;

    public PowerPellet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isColliding(int x, int y) {
        return this.x == x && this.y == y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}