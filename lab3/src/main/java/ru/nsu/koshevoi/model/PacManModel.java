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
    private Direction newPacManDirection;
    private PacMan pacMan;
    private List<Ghost> ghosts;
    private Board board;
    private State state = State.ALIVE;
    private long start;
    private long finish;
    private long time;
    private Levels level = Levels.FIRST;
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
        if(level.getValue() >= 1) {
            ghosts.add(new Ghost(1, 1, WIDTH, HEIGHT, board, this));
        }
        if(level.getValue() >= 2) {
            ghosts.add(new Ghost(1, HEIGHT - 2, WIDTH, HEIGHT, board, this));
        }
        if(level.getValue() >= 3) {
            ghosts.add(new Ghost(WIDTH - 2, 1, WIDTH, HEIGHT, board, this));
        }
        if(level.getValue() == 4) {
            ghosts.add(new Ghost(WIDTH - 2, HEIGHT - 2, WIDTH, HEIGHT, board, this));
        }
        pacMan = new PacMan(WIDTH/2, HEIGHT/2, WIDTH, HEIGHT, board, this);
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

    public void update() throws IOException {
        if(state == State.ALIVE) {
            if (this.pacMan != null) {
                pacMan.move(newPacManDirection);
            }
            if (this.ghosts != null) {
                for (Ghost ghost : ghosts) {
                    ghost.move(ghost.getDirection());
                }
            }
            notifyMovement();
        }
        if(state == State.WIN_LEVEL){
            if(level == Levels.FOURTH){
                state = State.WIN;
            }
            else{
                level.setValue(level.getValue() + 1);
                generate();
                state = State.ALIVE;
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
    public void setState(State state) {
        this.state = state;
    }
    public void setTime(long time) {
        this.time = start - time;
    }
    public Levels getLevel() {
        return level;
    }
}