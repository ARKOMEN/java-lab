package ru.nsu.koshevoi.model;

public class Ghost {
    private int x;
    private int y;
    private int speed;
    private Direction direction;

    public Ghost(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = Direction.RIGHT;
    }

    public void move(Direction direction) {
        this.direction = direction;
    }

    public void update() {
        switch (direction) {
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
        }
    }
    public boolean isColliding(int x, int y) {
        return this.x == x && this.y == y;
    }
}
