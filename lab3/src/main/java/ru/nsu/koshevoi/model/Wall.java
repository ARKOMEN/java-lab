package ru.nsu.koshevoi.model;

public class Wall {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isColliding(int x, int y) {
        return x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}