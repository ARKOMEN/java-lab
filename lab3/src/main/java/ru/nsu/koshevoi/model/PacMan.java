package ru.nsu.koshevoi.model;

public class PacMan implements GameObject{
    private int x;
    private int y;
    private Direction direction;
    private final int WIDTH;
    private final int HEIGHT;
    private Board board;
    private PacManModel model;
    private int score = 0;

    public PacMan(int x, int y, int WIDTH, int HEIGHT, Board board, PacManModel model) {
        this.model = model;
        this.board = board;
        this.x = x;
        this.y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.direction = Direction.NONE;
    }

    private boolean checkWall(Direction direction) {
        return switch (direction) {
            case UP -> board.getMap().get(y - 1).charAt(x) != '1';
            case DOWN -> board.getMap().get(y + 1).charAt(x) != '1';
            case RIGHT -> board.getMap().get(y).charAt(x + 1) != '1';
            case LEFT -> board.getMap().get(y).charAt(x - 1) != '1';
            default -> false;
        };
    }
    private void tryMove(Direction direction) {
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
    private void checkPellet(){
        try {
            if(board.getPowerPellets().get(y).remove(x)){
                score++;
            }
        }catch (Exception e) {
            //do nothing
        }
    }
    @Override
    public void move(Direction newDirection){
        if (checkWall(newDirection)) {
            tryMove(newDirection);
        } else if (checkWall(direction)) {
            tryMove(direction);
        } else {
            tryMove(Direction.NONE);
        }
        checkPellet();
        if(score == board.getNum()){
            long finish = System.currentTimeMillis();
            model.setTime(finish);
            model.setState(State.WIN_LEVEL);
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

    public int getScore() {
        return score;
    }
}
