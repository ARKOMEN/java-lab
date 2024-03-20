package ru.nsu.koshevoi.model;

import java.util.ArrayList;
import java.util.List;


public class PacManModel {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int SPEED = 2;


    private PacMan pacMan;
    private List<Ghost> ghosts;
    private Board board;

    public PacManModel() {
        pacMan = new PacMan(WIDTH / 2, HEIGHT / 2, SPEED);
        ghosts = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ghosts.add(new Ghost(i * WIDTH / 4, HEIGHT / 2, SPEED));
        }
        board = new Board(WIDTH, HEIGHT, ghosts);
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
    public void movePacMan(Direction direction) {
        pacMan.move(direction);
    }
    public void moveGhost(Ghost ghost, Direction direction) {
        ghost.move(direction);
    }

    public void update() {
        pacMan.update();
        for (Ghost ghost : ghosts) {
            ghost.update();
        }
    }
}