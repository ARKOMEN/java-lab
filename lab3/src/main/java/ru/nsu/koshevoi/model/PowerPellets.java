package ru.nsu.koshevoi.model;

public class PowerPellets extends GameObject{

    PowerPellets(int x, int y, int WIDTH, int HEIGHT, Board board, PacManModel model){
        super(x, y, WIDTH, HEIGHT, board, model);
    }
    @Override
    public void move(Direction direction) {
        //can't move
    }
}
