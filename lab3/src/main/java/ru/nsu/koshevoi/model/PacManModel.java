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
    private boolean flag = true;
    private int score = 0;
    private Direction newPacManDirection;
    private PacMan pacMan;
    private List<Ghost> ghosts;
    private Board board;
    private State state = State.ALIVE;
    private long start, finish, time;
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
        ghosts.add(new Ghost(1, 1, WIDTH, HEIGHT));
        ghosts.add(new Ghost(1, HEIGHT - 2, WIDTH, HEIGHT));
        ghosts.add(new Ghost(WIDTH - 2, 1, WIDTH, HEIGHT));
        ghosts.add(new Ghost(WIDTH - 2, HEIGHT - 2, WIDTH, HEIGHT));
        pacMan = new PacMan(WIDTH/2, HEIGHT/2, WIDTH, HEIGHT);
        newPacManDirection = pacMan.getDirection();
        start = System.currentTimeMillis();
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

    public boolean checkWall(GameObject object, Direction direction) {
        return switch (direction) {
            case UP -> board.getMap().get((object.getY() - 1)).charAt(object.getX()) != '1';
            case DOWN -> board.getMap().get((object.getY() + 1)).charAt(object.getX()) != '1';
            case RIGHT -> board.getMap().get((object.getY())).charAt(object.getX() + 1) != '1';
            case LEFT -> board.getMap().get((object.getY())).charAt(object.getX() - 1) != '1';
            default -> false;
        };
    }
    public void MovePacMan(Direction direction) {
        if (checkWall(pacMan, direction)) {
            pacMan.move(direction);
        } else if (checkWall(pacMan, pacMan.getDirection())) {
            pacMan.move(pacMan.getDirection());
        } else {
            pacMan.move(Direction.NONE);
        }
        try {
            if(board.getPowerPellets().get(pacMan.getY()).remove(pacMan.getX())){
                score++;
            }
        }catch (Exception e) {
            //do nothing
        }
    }


    public Direction chooseDirection(GameObject object){
        List<Direction> directionList = new ArrayList<>();
        if (checkWall(object, Direction.UP)) {
            directionList.add(Direction.UP);
        }
        if (checkWall(object, Direction.DOWN)) {
            directionList.add(Direction.DOWN);
        }
        if (checkWall(object, Direction.LEFT)) {
            directionList.add(Direction.LEFT);
        }
        if (checkWall(object, Direction.RIGHT)) {
            directionList.add(Direction.RIGHT);
        }
        int ch = ThreadLocalRandom.current().nextInt(0, directionList.size());
        return directionList.get(ch);
    }
    public void moveGhost(Ghost ghost, Direction direction) {
        if(board.getMap().get(ghost.getY()).charAt(ghost.getX()) == 'n'){
            ghost.move(chooseDirection(ghost));
        } else if (checkWall(ghost, direction)) {
            ghost.move(direction);
        } else {
            ghost.move(chooseDirection(ghost));
        }
    }

    public void update() {
        if(state == State.ALIVE) {
            if (this.pacMan != null) {
                MovePacMan(newPacManDirection);
            }
            if (score == 227 && flag) {
                finish = System.currentTimeMillis();
                time = finish - start;
                state = State.WIN;
                flag = false;
            }
            if (this.ghosts != null) {
                for (Ghost ghost : ghosts) {
                    if (ghost.isColliding(pacMan.getX(), pacMan.getY())) {
                        state = State.DEAD;
                    }
                }
            }
            if (this.ghosts != null) {
                for (Ghost ghost : ghosts) {
                    moveGhost(ghost, ghost.getDirection());
                }
            }
            if (this.ghosts != null) {
                for (Ghost ghost : ghosts) {
                    if (ghost.isColliding(pacMan.getX(), pacMan.getY())) {
                        state = State.DEAD;
                    }
                }
            }
            notifyMovement();
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
    public long getTime(){return time;}
    public State getState() {
        return state;
    }
    public int getScore() {
        return score;
    }
}