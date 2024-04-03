package ru.nsu.koshevoi.model;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class PacManModel implements AutoCloseable {
    private static int WIDTH;
    private static int HEIGHT;
    private long timeout = 145;
    private Thread thread;
    private ModelListener listener;
    private Direction newPacManDirection;
    private PacMan pacMan;
    private List<Ghost> ghosts;
    private List<PowerPellets> powerPellets;
    private Board board;
    private State state = State.ALIVE;
    private long start;
    private long finish;
    private long time;
    private Levels level = new Levels(1);
    private final int nunGhosts = 4;
    public PacManModel() throws IOException {
        thread = new Ticker(this);
        thread.start();
        generate();
    }

    synchronized void generate() throws IOException {
        board = new Board();
        WIDTH = board.getWidth();
        HEIGHT = board.getHeight();
        powerPellets = board.getPowerPellets();
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
        }else if(state == State.WIN_LEVEL){
            if(level.getValue() == 4){
                level.setTime((System.currentTimeMillis()-start)/1000);
                state = State.WIN;
            }
            else{
                level.setValue(level.getValue() + 1);
                level.setTime((System.currentTimeMillis()-start)/1000);
                start = System.currentTimeMillis();
                generate();
                state = State.ALIVE;
            }
        }
        notifyMovement();
    }
    public void updateTable(){
        try(FileWriter writer = new FileWriter("table.csv", true)){
            writer.append(pacMan.getUserName() + ',' + level.getTime().get(0) + ',' + level.getTime().get(1)
                    + ',' + level.getTime().get(2) + ',' + level.getTime().get(3) + '\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Map<String, List<String>> parser(){
        Map<String, List<String>> table = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("table.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> tmp = new ArrayList<>(List.of(line.split(",")));
                table.put(tmp.getFirst(), tmp.subList(1, tmp.size()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
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
    public List<PowerPellets> getPowerPellets() {
        return powerPellets;
    }
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
    public int getNunGhosts() {
        return nunGhosts;
    }
}