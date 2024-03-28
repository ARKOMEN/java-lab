package ru.nsu.koshevoi.model;

public class PacMan implements GameObject{
    private int x;
    private int y;
    private Direction direction;
    private final int WIDTH;
    private final int HEIGHT;

    public PacMan(int x, int y, int WIDTH, int HEIGHT) {
        this.x = x;
        this.y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.direction = Direction.NONE;
    }
    @Override
    public void move(Direction direction) {
        this.direction = direction;
        switch (direction) {
            case UP:
                if(y - 1 >= 0)
                    y -= 1;
                break;
            case DOWN:
                if(y + 1 < HEIGHT * 20)
                    y += 1;
                break;
            case LEFT:
                if(x - 1 >= 0)
                    x -= 1;
                break;
            case RIGHT:
                if(x + 1 < WIDTH * 20)
                    x += 1;
                break;
            case NONE:
                break;
        }
    }
    @Override
    public int getX() {
        return x;
    }
    @Override
    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }
}
