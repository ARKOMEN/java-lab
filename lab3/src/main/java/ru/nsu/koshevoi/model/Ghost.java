package ru.nsu.koshevoi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Ghost extends GameObject{
    private Direction direction = Direction.NONE;
    public Ghost(int x, int y, int WIDTH, int HEIGHT, Board board, PacManModel model) {
        super(x, y, WIDTH, HEIGHT, board, model);
    }
    public void tryMove(Direction direction) {
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
    private boolean checkWall(Direction direction) {
        return switch (direction) {
            case UP -> board.getMap().get(y - 1).charAt(x) != '1';
            case DOWN -> board.getMap().get(y + 1).charAt(x) != '1';
            case RIGHT -> board.getMap().get(y).charAt(x + 1) != '1';
            case LEFT -> board.getMap().get(y).charAt(x - 1) != '1';
            default -> false;
        };
    }
    public Direction chooseDirection(){
        List<Direction> directionList = new ArrayList<>();
        if (checkWall(Direction.UP)) {
            directionList.add(Direction.UP);
        }
        if (checkWall(Direction.DOWN)) {
            directionList.add(Direction.DOWN);
        }
        if (checkWall(Direction.LEFT)) {
            directionList.add(Direction.LEFT);
        }
        if (checkWall(Direction.RIGHT)) {
            directionList.add(Direction.RIGHT);
        }
        int ch = ThreadLocalRandom.current().nextInt(0, directionList.size());
        return directionList.get(ch);
    }
    @Override
    public void move(Direction direction){
        if(isColliding(model.getPacMan().getX(), model.getPacMan().getY())){
            model.setState(State.DEAD);
        }
        if(board.getMap().get(y).charAt(x) == 'n'){
            tryMove(chooseDirection());
        } else if (checkWall(direction)) {
            tryMove(direction);
        } else {
            tryMove(chooseDirection());
        }
        if(isColliding(model.getPacMan().getX(), model.getPacMan().getY())){
            model.setState(State.DEAD);
        }
    }
    public boolean isColliding(int x, int y) {
        return this.x == x && this.y == y;
    }

    public Direction getDirection() {
        return direction;
    }
}
