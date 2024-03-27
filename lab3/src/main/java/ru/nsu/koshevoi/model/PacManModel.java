package ru.nsu.koshevoi.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;


public class PacManModel implements AutoCloseable {
    private static int WIDTH;
    private static int HEIGHT;
    private final long timeout = 145;
    private Thread thread;
    private ModelListener listener;
    private State state = State.STAND;

    private PacManDirection newPacManDirection;
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
        ghosts.add(new Ghost(1, 1, WIDTH, HEIGHT));
        ghosts.add(new Ghost(1, HEIGHT - 1, WIDTH, HEIGHT));
        ghosts.add(new Ghost(WIDTH - 1, 1, WIDTH, HEIGHT));
        ghosts.add(new Ghost(WIDTH - 1, HEIGHT - 1, WIDTH, HEIGHT));
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

    public boolean test(PacManDirection direction) {
        return switch (direction) {
            case UP -> board.getMap().get((pacMan.getY() - 1)).charAt(getPacMan().getX()) != '1';
            case DOWN -> board.getMap().get((pacMan.getY() + 1)).charAt(getPacMan().getX()) != '1';
            case RIGHT -> board.getMap().get((pacMan.getY())).charAt(getPacMan().getX() + 1) != '1';
            case LEFT -> board.getMap().get((pacMan.getY())).charAt(getPacMan().getX() - 1) != '1';
            default -> false;
        };
    }
    public void MovePacMan(PacManDirection direction) {
        if(test(direction)){
            pacMan.move(direction);
        } else if (test(pacMan.getDirection())) {
            pacMan.move(pacMan.getDirection());
        }
        else{
            pacMan.move(PacManDirection.NONE);
        }
        notifyMovement();
    }
    public void moveGhost(Ghost ghost, GhostsDirection direction) {
        ghost.move(direction);
        notifyMovement();
    }

    public void update() {
        if(this.pacMan != null) {
            MovePacMan(newPacManDirection);
        }
        if(this.ghosts != null){
            for (Ghost ghost : ghosts) {
                ghost.update();
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
    public void setNewPacManDirection(PacManDirection newPacManDirection) {
        this.newPacManDirection = newPacManDirection;
    }
    @Override
    public void close() throws InterruptedException {
        thread.interrupt();
        thread.join();
    }
}