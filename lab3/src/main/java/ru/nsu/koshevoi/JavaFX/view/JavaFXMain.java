package ru.nsu.koshevoi.JavaFX.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.nsu.koshevoi.JavaFX.controller.JavaFXController;
import ru.nsu.koshevoi.model.*;

import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;


public class JavaFXMain extends Application implements ModelListener {
    private PacManModel model;
    private final static int SIZE = 20;
    private Board board;
    Image ghostImage;
    Image wallImage;
    Image pacManImage;
    Image dotImage;
    StackPane root;
    Stage primaryStage;
    boolean flag = true;
    Text text;
    Text text1;
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        model = new PacManModel();
        JavaFXController controller = new JavaFXController(model);
        model.setListener(this);
        board = model.getBoard();
        root = new StackPane();
        primaryStage.setTitle("Pac-Man JavaFX");
        Scene scene = new Scene(root, 640, 480);
        scene.setOnKeyPressed(controller);
        primaryStage.setScene(scene);
        InputStream inputStream = new FileInputStream("/home/artemiy/java-labs/java-lab/lab3/src/main/resources/a.png");
        wallImage = new Image(inputStream);
        inputStream = new FileInputStream("/home/artemiy/java-labs/java-lab/lab3/src/main/resources/b.png");
        pacManImage= new Image(inputStream);
        inputStream = new FileInputStream("/home/artemiy/java-labs/java-lab/lab3/src/main/resources/c.png");
        ghostImage = new Image(inputStream);
        inputStream = new FileInputStream("/home/artemiy/java-labs/java-lab/lab3/src/main/resources/d.png");
        dotImage = new Image(inputStream);
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            try {
                model.close();
                System.exit(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        ArrayList<Wall> walls = (ArrayList<Wall>) board.getWalls();
        for (Wall wall : walls) {
            loadImage(root, wallImage);
        }
        PacMan pacMan = model.getPacMan();
        loadImage(root, pacManImage);
        ArrayList<Ghost> ghosts = (ArrayList<Ghost>) model.getGhosts();
        for(Ghost ghost : ghosts){
            loadImage(root, ghostImage);
        }
        ArrayList<PowerPellets> powerPellets = (ArrayList<PowerPellets>) model.getPowerPellets();
        for(PowerPellets pellets : powerPellets){
            loadImage(root, dotImage);
        }
        onModelChanged();
        primaryStage.show();
    }

    @Override
    public void onModelChanged(){
        drawBoard(model);
    }

    public void drawBoard(PacManModel model) {
        if(!root.getChildren().isEmpty()) {
            int i;
            for (i = 0; i < model.getBoard().getWalls().size(); i++) {
                root.getChildren().get(i).setTranslateX(model.getBoard().getWalls().get(i).getX() * SIZE - (double) board.getWidth() / 2 * SIZE);
                root.getChildren().get(i).setTranslateY(model.getBoard().getWalls().get(i).getY() * SIZE - (double) board.getHeight() / 2 * SIZE);
            }
            root.getChildren().get(i).setTranslateX(model.getPacMan().getX() * SIZE - (double) board.getWidth() / 2 * SIZE);
            root.getChildren().get(i).setTranslateY(model.getPacMan().getY() * SIZE - (double) board.getHeight() / 2 * SIZE);
            i++;
            int finish = i;
            for (; i < finish + model.getLevel().getValue(); i++) {
                root.getChildren().get(i).setTranslateX(model.getGhosts().get(i - finish).getX() * SIZE - (double) board.getWidth() / 2 * SIZE);
                root.getChildren().get(i).setTranslateY(model.getGhosts().get(i - finish).getY() * SIZE - (double) board.getHeight() / 2 * SIZE);
            }
            finish = i;
            for (; i < finish + model.getBoard().getNum() - model.getPacMan().getScore(); i++) {
                root.getChildren().get(i).setTranslateX(model.getPowerPellets().get(i - finish).getX() * SIZE - (double) board.getWidth() / 2 * SIZE);
                root.getChildren().get(i).setTranslateY(model.getPowerPellets().get(i - finish).getY() * SIZE - (double) board.getHeight() / 2 * SIZE);
            }
            if(flag){
                text = new Text("SCORE:" + model.getPacMan().getScore());
                text.setTranslateX(-270);
                text.setTranslateY(200);
                root.getChildren().add(text);
                flag = false;
            }
            if(model.getState() == State.ALIVE) {
                text.setText("SCORE:" + model.getPacMan().getScore());
            }else if(model.getState() == State.DEAD){
                text.setText("GAME OVER");
                root.getChildren().removeAll();
            }else if(model.getState() == State.WIN){
                text.setText("YOU WIN! Your time " + model.getTime()/60);
                root.getChildren().removeAll();
            }

        }
    }

    private void loadImage(StackPane root, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(SIZE);
        imageView.setFitHeight(SIZE);
        root.getChildren().add(imageView);
    }

    public static void main(String[] args) {
        launch(args);
    }
}