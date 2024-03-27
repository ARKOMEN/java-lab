package ru.nsu.koshevoi.model;

public class Ghost implements GameObject{
    private int x;
    private int y;

    private Direction direction;
    private final int WIDTH;
    private final int HEIGHT;
    public Ghost(int x, int y, int WIDTH, int HEIGHT) {
        this.x = x;
        this.y = y;
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.direction = Direction.RIGHT;
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

    public void update() {
        switch (direction) {
            case UP:
                if(y - 2 >= 0)
                    y -= 2;
                else{
                    direction = Direction.DOWN;
                }
                break;
            case DOWN:
                if(y + 2 < HEIGHT * 20)
                    y += 2;
                else{
                    direction = Direction.UP;
                }
                break;
            case LEFT:
                if(x - 2 >= 0)
                    x -= 2;
                else{
                    direction = Direction.RIGHT;
                }
                break;
            case RIGHT:
                if(x + 2 < WIDTH * 20)
                    x += 2;
                else{
                    direction = Direction.LEFT;
                }
                break;
        }
    }
    public boolean isColliding(int x, int y) {
        return this.x == x && this.y == y;
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
