package ru.nsu.koshevoi.model;

public class PowerPellets implements GameObject{
    private int x, y;

    PowerPellets(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public void move(Direction direction) {
        //can't move
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
