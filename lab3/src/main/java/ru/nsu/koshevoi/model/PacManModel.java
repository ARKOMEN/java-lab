package ru.nsu.koshevoi.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PacManModel implements AutoCloseable {
    private static int WIDTH;
    private static int HEIGHT;
    private static final int SPEED = 2;
    private final long timeout = 5000;
    private Thread thread;
    private ModelListener listener;
    private State state = State.STAND;

    private PacMan pacMan;
    private List<Ghost> ghosts;
    private Board board;

    public PacManModel() throws IOException {
        thread = new Ticker(this);
        thread.start();
        generate();
        this.WIDTH = board.getWidth();
        this.HEIGHT = board.getHeight();
    }

    synchronized void generate() throws IOException {
        board = new Board();
        ghosts = new ArrayList<>();
        ghosts.add(new Ghost(1, 1, SPEED));
        ghosts.add(new Ghost(1, HEIGHT - 1, SPEED));
        ghosts.add(new Ghost(WIDTH - 1, 1, SPEED));
        ghosts.add(new Ghost(WIDTH - 1, HEIGHT - 1, SPEED));
        pacMan = new PacMan(WIDTH / 2, HEIGHT / 2, SPEED);
        state = State.STAND;
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
    public void movePacMan(PacManDirection direction) {
        pacMan.move(direction);
    }
    public void moveGhost(Ghost ghost, GhostsDirection direction) {
        ghost.move(direction);
    }

    public void update() {
        pacMan.update();
        for (Ghost ghost : ghosts) {
            ghost.update();
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
    @Override
    public void close() throws InterruptedException {
        thread.interrupt();
        thread.join();
    }
}