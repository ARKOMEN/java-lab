package ru.nsu.koshevoi.model;

public class PacMan {
    private int x;
    private int y;
    private PacManDirection direction;
    private int speed;

    public PacMan(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.direction = PacManDirection.NONE;
        this.speed = speed;
    }

    public void move(PacManDirection direction) {
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

    public PacManDirection getDirection() {
        return direction;
    }

    public void changeDirection(PacManDirection direction) {
        if (this.direction.opposite() != direction) {
            this.direction = direction;
        }
    }
    public void update() {
        move(direction);
    }
}
