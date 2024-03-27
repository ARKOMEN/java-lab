package ru.nsu.koshevoi.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class PacManModel implements AutoCloseable {
    private static int WIDTH;
    private static int HEIGHT;
    private final long timeout = 145;
    private Thread thread;
    private ModelListener listener;
    private State state = State.STAND;

    private Direction newPacManDirection;
    private PacMan pacMan;
    private List<Ghost> ghosts;
    private Board board;
    public PacManModel() throws IOException {
        thread = new Ticker(this);
        thread.start();
        generate();
    }

    synchronized void generate() throws IOException {
        board = new Board();
        WIDTH = board.getWidth();
        HEIGHT = board.getHeight();
        ghosts = new ArrayList<>();
        ghosts.add(new Ghost(0, 1, WIDTH, HEIGHT));
        ghosts.add(new Ghost(0, HEIGHT - 2, WIDTH, HEIGHT));
        ghosts.add(new Ghost(WIDTH - 2, 1, WIDTH, HEIGHT));
        ghosts.add(new Ghost(WIDTH - 2, HEIGHT - 2, WIDTH, HEIGHT));
        pacMan = new PacMan(WIDTH/2, HEIGHT/2, WIDTH, HEIGHT);
        state = State.STAND;
        newPacManDirection = pacMan.getDirection();
        notifyMovement();
    }

    public PacMan getPacMan() {
        return pacMan;
    }
    public List<Ghost> getGhosts() {
        return ghosts;
    }
    public Board getBoard() {
        return board;
    }

    public boolean test(GameObject object, Direction direction) {
        return switch (direction) {
            case UP -> board.getMap().get((object.getY() - 1)).charAt(object.getX()) != '1';
            case DOWN -> board.getMap().get((object.getY() + 1)).charAt(object.getX()) != '1';
            case RIGHT -> board.getMap().get((object.getY())).charAt(object.getX() + 1) != '1';
            case LEFT -> board.getMap().get((object.getY())).charAt(object.getX() - 1) != '1';
            default -> false;
        };
    }
    public void MovePacMan(Direction direction) {
        if(test(pacMan, direction)){
            pacMan.move(direction);
        } else if (test(pacMan, pacMan.getDirection())) {
            pacMan.move(pacMan.getDirection());
        }
        else{
            pacMan.move(Direction.NONE);
        }
        notifyMovement();
    }

    public Direction chooseDirection(GameObject object){
        List<Direction> directionList = new ArrayList<>();
        if (test(object, Direction.UP)) {
            directionList.add(Direction.UP);
        }
        if (test(object, Direction.DOWN)) {
            directionList.add(Direction.DOWN);
        }
        if (test(object, Direction.LEFT)) {
            directionList.add(Direction.LEFT);
        }
        if (test(object, Direction.RIGHT)) {
            directionList.add(Direction.RIGHT);
        }
        int ch = ThreadLocalRandom.current().nextInt(0, directionList.size());
        return directionList.get(ch);
    }
    public void moveGhost(Ghost ghost, Direction direction) {
        if(board.getMap().get(ghost.getY()).charAt(ghost.getX()) == 'n'){
            ghost.move(chooseDirection(ghost));
        } else if (test(ghost, direction)) {
            ghost.move(direction);
        } else {
            ghost.move(chooseDirection(ghost));
        }
        notifyMovement();
    }

    public void update() {
        if(this.pacMan != null) {
            MovePacMan(newPacManDirection);
        }
        if(this.ghosts != null){
            for (Ghost ghost : ghosts) {
                moveGhost(ghost, ghost.getDirection());
            }
        }
    }
    private void notifyMovement(){
        if(null != listener){
            listener.onModelChanged();
        }
    }
    public synchronized void setListener(ModelListener listener) {
        this.listener = listener;
    }
    public State getState() {
        return state;
    }
    public long getTimeout() {
        return timeout;
    }
    public void setNewPacManDirection(Direction newPacManDirection) {
        this.newPacManDirection = newPacManDirection;
    }
    @Override
    public void close() throws InterruptedException {
        thread.interrupt();
        thread.join();
    }
}