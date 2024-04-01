package ru.nsu.koshevoi.model;

import java.io.*;
import java.util.*;

public class Board {
    private final int width;
    private int height;
    private final List<Wall> walls;
    private List<String> map;
    private List<PowerPellets> powerPellets;
    private int num = 0;
    public Board() throws IOException {
        map = new ArrayList<>();
        this.height = 0;
        this.walls = new ArrayList<>();
        this.powerPellets = new ArrayList<>();
        try(InputStream stream = Board.class.getResourceAsStream("/map.txt")){
            assert stream != null;
            Scanner scanner = new Scanner(stream);
            String walls = null;
            while (scanner.hasNextLine()){
                walls = scanner.nextLine();
                map.add(walls);
                for(int i = 0; i < walls.length(); i++) {
                    if (walls.charAt(i) == '1') {
                        Wall wall = new Wall(i, this.height, 1, 1);
                        addWall(wall);
                    } else if (walls.charAt(i) == '0') {
                        num++;
                        PowerPellets pellet = new PowerPellets(i, this.height);
                        addPowerPellets(pellet);
                    }
                }
                this.height++;
            }
            assert walls != null;
            this.width = walls.length();
        }

    }

    public void addWall(Wall wall) {
        walls.add(wall);
    }
    public void addPowerPellets(PowerPellets pellets) {
        powerPellets.add(pellets);
    }
    public List<Wall> getWalls() {
        return this.walls;
    }
    public List<PowerPellets> getPowerPellets() {
        return this.powerPellets;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public int getNum(){return this.num;}
    public List<String> getMap(){
        return this.map;
    }
}