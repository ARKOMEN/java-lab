package ru.nsu.koshevoi.model;

public class PacMan {
    private int x;
    private int y;
    private Direction direction;
    private int speed;

    public PacMan(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.direction = Direction.RIGHT;
        this.speed = speed;
    }

    public void move(Direction direction) {
        this.direction = direction;
        switch (direction) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void changeDirection(Direction direction) {
        if (this.direction.opposite() != direction) {
            this.direction = direction;
        }
    }
    public void update() {
        move(direction);
    }
}
