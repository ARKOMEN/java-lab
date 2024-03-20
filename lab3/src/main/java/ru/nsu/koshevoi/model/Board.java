package ru.nsu.koshevoi.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int width;
    private int height;
    private List<Wall> walls;
    private List<PowerPellet> powerPellets;
    private List<Ghost> ghosts;
    private PacMan pacMan;

    public Board(int width, int height, List<Ghost> ghost) {
        this.width = width;
        this.height = height;
        this.walls = new ArrayList<>();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                //addWall();
            }
        }
        this.powerPellets = new ArrayList<>();
        this.ghosts = ghost;
    }

    public void addWall(Wall wall) {
        walls.add(wall);
    }

    public void addPowerPellet(PowerPellet powerPellet) {
        powerPellets.add(powerPellet);
    }

    public void addGhost(Ghost ghost) {
        ghosts.add(ghost);
    }

    public void addPacMan(PacMan pacMan) {
        this.pacMan = pacMan;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<PowerPellet> getPowerPellets() {
        return powerPellets;
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }

    public PacMan getPacMan() {
        return pacMan;
    }

    public boolean isColliding(int x, int y) {
        return walls.stream().anyMatch(wall -> wall.isColliding(x, y));
    }

    public boolean isPowerPelletEaten(int x, int y) {
        return powerPellets.removeIf(powerPellet -> powerPellet.isColliding(x, y));
    }

    public boolean isGhostEaten(int x, int y) {
        return ghosts.removeIf(ghost -> ghost.isColliding(x, y));
    }
}