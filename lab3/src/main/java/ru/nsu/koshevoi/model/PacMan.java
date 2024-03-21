package ru.nsu.koshevoi.model;

import java.nio.file.WatchEvent;

public class PacMan {
    private int x;
    private int y;
    private PacManDirection direction;
    private final int WIDTH;
    private final int HEIGHT;

    public PacMan(int x, int y, int WIDTH, int HEIGHT) {
        this.x = x;
        this.y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.direction = PacManDirection.NONE;
    }

    public void move(PacManDirection direction) {
        this.direction = direction;
        switch (direction) {
            case UP:
                if(y - 2 >= 0)
                    y -= 2;
                break;
            case DOWN:
                if(y + 2 < HEIGHT * 20)
                    y += 2;
                break;
            case LEFT:
                if(x - 2 >= 0)
                    x -= 2;
                break;
            case RIGHT:
                if(x + 2 < WIDTH * 20)
                    x += 2;
                break;
            case NONE:
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
        move(PacManDirection.NONE);
    }
}
