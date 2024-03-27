package ru.nsu.koshevoi.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
    private final int width;
    private int height;
    private final List<Wall> walls;
    private final List<PowerPellet> powerPellets;
    private List<String> map;
    public Board() throws IOException {
        map = new ArrayList<String>();
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
                for(int i = 0; i < walls.length(); i++){
                    if(walls.charAt(i) == '1'){
                        Wall wall = new Wall(i, this.height, 1, 1);
                        addWall(wall);
                    } else if (walls.charAt(i) == '4') {
                        PowerPellet powerPellet = new PowerPellet(i, this.height);
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

    public void addPowerPellet(PowerPellet powerPellet) {
        powerPellets.add(powerPellet);
    }

    public List<Wall> getWalls() {
        return this.walls;
    }

    public List<PowerPellet> getPowerPellets() {
        return this.powerPellets;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public boolean isPowerPelletEaten(int x, int y) {
        return powerPellets.removeIf(powerPellet -> powerPellet.isColliding(x, y));
    }
    public List<String> getMap(){
        return this.map;
    }
}