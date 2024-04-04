package ru.nsu.koshevoi.model;

public class PacMan extends GameObject{
    private Direction direction = Direction.NONE;
    private int score = 0;
    private String userName;

    public PacMan(int x, int y, int WIDTH, int HEIGHT, Board board, PacManModel model) {
        super(x, y, WIDTH, HEIGHT, board, model);
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
        for(int i = 0; i < model.getBoard().getNum() - score; i++){
            if(model.getPowerPellets().get(i).getX() == x &&
            model.getPowerPellets().get(i).getY() == y){
                score++;
                model.getPowerPellets().remove(i);
            }
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
    public Direction getDirection() {
        return direction;
    }

    public int getScore() {
        return score;
    }
    public void setUserName(String name){
        this.userName = name;
    }
    public String getUserName() {
        return userName;
    }
}
