package ru.nsu.koshevoi.model;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected final int WIDTH;
    protected final int HEIGHT;
    protected final PacManModel model;
    protected final Board board;
    public abstract void move(Direction direction);
    public GameObject(int x, int y, int WIDTH, int HEIGHT, Board board, PacManModel model){
        this.model = model;
        this.board = board;
        this.x = x;
        this.y = y;
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
}
