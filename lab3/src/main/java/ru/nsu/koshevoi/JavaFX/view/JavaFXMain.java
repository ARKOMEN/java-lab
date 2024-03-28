package ru.nsu.koshevoi.JavaFX.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ru.nsu.koshevoi.JavaFX.controller.JavaFXController;
import ru.nsu.koshevoi.model.*;

import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class JavaFXMain extends Application implements ModelListener {
    private PacManModel model;
    private final static int SIZE = 20;
    private Board board;
    Image ghostImage;
    Image wallImage;
    Image pacManImage;
    StackPane root;

    @Override
    public void start(Stage primaryStage) throws IOException {
        model = new PacManModel();
        JavaFXController controller = new JavaFXController(model);
        model.setListener(this);
        board = model.getBoard();
        root = new StackPane();
        primaryStage.setTitle("Pac-Man JavaFX");
        Scene scene = new Scene(root, 640, 480);
        scene.setOnKeyPressed(controller);
        primaryStage.setScene(scene);
        try (InputStream inputStream = new FileInputStream("/home/artemiy/java-labs/java-lab/lab3/src/main/resources/a.png")) {
            wallImage = new Image(inputStream);
        } catch (IOException e) {
            System.out.println("error");
        }
        try (InputStream inputStream = new FileInputStream("/home/artemiy/java-labs/java-lab/lab3/src/main/resources/b.png")) {
            pacManImage= new Image(inputStream);
        } catch (IOException e) {
            System.out.println("error");
        }
        try (InputStream inputStream = new FileInputStream("/home/artemiy/java-labs/java-lab/lab3/src/main/resources/c.png")) {
            ghostImage = new Image(inputStream);
        } catch (IOException e) {
            System.out.println("error");
        }
        ArrayList<Wall> walls = (ArrayList<Wall>) board.getWalls();
        for (Wall wall : walls) {
            loadImage(root, wallImage, wall.getX(), wall.getY());
        }
        PacMan pacMan = model.getPacMan();
        loadImage(root, pacManImage, pacMan.getX(), pacMan.getY());
        ArrayList<Ghost> ghosts = (ArrayList<Ghost>) model.getGhosts();
        for(Ghost ghost : ghosts){
            loadImage(root, ghostImage, ghost.getX(), ghost.getY());
        }
        drawBoard(root, model);
        primaryStage.show();
    }

    @Override
    public void onModelChanged(){
        drawBoard(root, model);
    }

    public void drawBoard(StackPane root, PacManModel model) {
        for(int i = 0 ; i < root.getChildren().size(); i++){
            root.getChildren().get(i).setTranslateX(x * SIZE - (double) board.getWidth() /2*20);
            root.getChildren().get(i).setTranslateX(y * SIZE - (double) board.getWidth() /2*20);
        }
    }

    private void loadImage(StackPane root, Image image, int x, int y) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(SIZE);
        imageView.setFitHeight(SIZE);
        root.getChildren().add(imageView);
        imageView.setTranslateX(x * SIZE - (double) board.getWidth() /2*20);
        imageView.setTranslateY(y * SIZE - (double) board.getHeight() /2*20);
    }

    public static void main(String[] args) {
        launch(args);
    }
}