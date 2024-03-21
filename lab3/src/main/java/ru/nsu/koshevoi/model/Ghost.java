package ru.nsu.koshevoi.model;

public class Ghost {
    private int x;
    private int y;
    private GhostsDirection direction;
    private final int WIDTH;
    private final int HEIGHT;

    public Ghost(int x, int y, int WIDTH, int HEIGHT) {
        this.x = x;
        this.y = y;
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.direction = GhostsDirection.RIGHT;
    }

    public void move(GhostsDirection direction) {
        this.direction = direction;
    }

    public void update() {
        switch (direction) {
            case UP:
                if(y - 2 >= 0)
                    y -= 2;
                else{
                    direction = GhostsDirection.DOWN;
                }
                break;
            case DOWN:
                if(y + 2 < HEIGHT * 20)
                    y += 2;
                else{
                    direction = GhostsDirection.UP;
                }
                break;
            case LEFT:
                if(x - 2 >= 0)
                    x -= 2;
                else{
                    direction = GhostsDirection.RIGHT;
                }
                break;
            case RIGHT:
                if(x + 2 < WIDTH * 20)
                    x += 2;
                else{
                    direction = GhostsDirection.LEFT;
                }
                break;
        }
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
