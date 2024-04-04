package ru.nsu.koshevoi.JavaFX.view;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
import java.util.List;
import java.util.Map;
import javafx.application.Platform;


public class JavaFXMain extends Application implements ModelListener {
    private PacManModel model;
    private final static int SIZE = 20;
    private Board board;
    Image ghostImage;
    Image wallImage;
    Image pacManImage;
    Image dotImage;
    Pane root;
    StackPane nodeWalls;
    StackPane nodeGhosts;
    StackPane nodePacMan;
    StackPane nodePowerPellets;
    Stage primaryStage;
    boolean flag = true;
    Text text;
    JavaFXController controller;
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.getIcons().add(new Image(new FileInputStream("pac.png")));
        model = new PacManModel();
        controller = new JavaFXController(model);
        model.setListener(this);
        board = model.getBoard();
        root = new StackPane();
        nodeWalls = new StackPane();
        nodeGhosts = new StackPane();
        nodePacMan = new StackPane();
        nodePowerPellets = new StackPane();
        root.getChildren().add(nodeGhosts);
        root.getChildren().add(nodePacMan);
        root.getChildren().add(nodePowerPellets);
        root.getChildren().add(nodeWalls);
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
            loadImage(wallImage, nodeWalls);
        }
        ArrayList<PowerPellets> powerPellets = (ArrayList<PowerPellets>) model.getPowerPellets();
        for(PowerPellets pellets : powerPellets){
            loadImage(dotImage, nodePowerPellets);
        }
        PacMan pacMan = model.getPacMan();
        loadImage(pacManImage, nodePacMan);
        for(int i = 0; i < model.getNunGhosts(); i++){
            loadImage(ghostImage, nodeGhosts);
            nodeGhosts.getChildren().get(i).setVisible(false);
        }
        onModelChanged();
        primaryStage.show();
    }

    @Override
    public void onModelChanged(){
        switch (model.getState()){
            case ALIVE, DEAD -> drawBoard(model);
            case WIN -> {model.setTimeout(5000);displayWin();}
            case TABLE -> displayTable();
        }
    }
    private void displayTable(){
        Map<String, List<String>> map = model.parser();
        Platform.runLater(() -> {
            root.getChildren().clear();
            TableView<Map.Entry<String, List<String>>> tableView = new TableView<>();
            tableView.prefHeightProperty().bind(primaryStage.heightProperty());
            tableView.prefWidthProperty().bind(primaryStage.widthProperty());
            TableColumn<Map.Entry<String, List<String>>, String> nameColumn = new TableColumn<>("Name");
            TableColumn<Map.Entry<String, List<String>>, String> levelColumn1 = new TableColumn<>("first level");
            TableColumn<Map.Entry<String, List<String>>, String> levelColumn2 = new TableColumn<>("second level");
            TableColumn<Map.Entry<String, List<String>>, String> levelColumn3 = new TableColumn<>("third level");
            TableColumn<Map.Entry<String, List<String>>, String> levelColumn4 = new TableColumn<>("fourth level");
            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
            levelColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().get(0)));
            levelColumn2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().get(1)));
            levelColumn3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().get(2)));
            levelColumn4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().get(3)));
            tableView.getColumns().addAll(nameColumn, levelColumn1, levelColumn2, levelColumn3, levelColumn4);
            tableView.getItems().addAll(map.entrySet());
            root.getChildren().add(tableView);
        });
    }
    private void displayWin(){
        Platform.runLater(() -> {
            root = new GridPane();
            Scene scene = primaryStage.getScene();
            Label messageLabel = new Label("You win!");
            messageLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
            GridPane.setConstraints(messageLabel, 0, 0, 2, 1);
            root.getChildren().add(messageLabel);
            Label nameLabel = new Label("Enter your name:");
            GridPane.setConstraints(nameLabel, 0, 1);
            root.getChildren().add(nameLabel);
            TextField nameField = new TextField();
            GridPane.setConstraints(nameField, 1, 1);
            root.getChildren().add(nameField);
            Button enterButton = new Button("Enter");
            GridPane.setConstraints(enterButton, 2, 1);
            enterButton.setOnAction(actionEvent -> {
                controller.enterName(nameField.getText());
            });
            root.getChildren().add(enterButton);
            scene.setRoot(root);
        });
    }



    private void drawBoard(PacManModel model) {
        if(!nodePacMan.getChildren().isEmpty()) {
            for(int i = 0; i < nodeWalls.getChildren().size(); i++){
                nodeWalls.getChildren().get(i).setTranslateX(model.getBoard().getWalls().get(i).getX() * SIZE - (double) board.getWidth() / 2 * SIZE);
                nodeWalls.getChildren().get(i).setTranslateY(model.getBoard().getWalls().get(i).getY() * SIZE - (double) board.getHeight() / 2 * SIZE);
            }
            for(int i = 0; i < nodePowerPellets.getChildren().size(); i++){
                if(i < nodePowerPellets.getChildren().size() - model.getPacMan().getScore()) {
                    nodePowerPellets.getChildren().get(i).setTranslateX(model.getPowerPellets().get(i).getX() * SIZE - (double) board.getWidth() / 2 * SIZE);
                    nodePowerPellets.getChildren().get(i).setTranslateY(model.getPowerPellets().get(i).getY() * SIZE - (double) board.getHeight() / 2 * SIZE);
                    nodePowerPellets.getChildren().get(i).setVisible(true);
                }else{
                    nodePowerPellets.getChildren().get(i).setVisible(false);
                }
            }
            nodePacMan.getChildren().getFirst().setTranslateX(model.getPacMan().getX() * SIZE - (double) board.getWidth() / 2 * SIZE);
            nodePacMan.getChildren().getFirst().setTranslateY(model.getPacMan().getY() * SIZE - (double) board.getHeight() / 2 * SIZE);
            for(int i = 0; i < model.getGhosts().size(); i++){
                nodeGhosts.getChildren().get(i).setVisible(true);
                nodeGhosts.getChildren().get(i).setTranslateX(model.getGhosts().get(i).getX() * SIZE - (double) board.getWidth() / 2 * SIZE);
                nodeGhosts.getChildren().get(i).setTranslateY(model.getGhosts().get(i).getY() * SIZE - (double) board.getHeight() / 2 * SIZE);
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
            }else if(model.getState() == State.DEAD) {
                text.setText("GAME OVER");
                root.getChildren().removeAll();
            }
        }
    }

    private void loadImage(Image image, StackPane node) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(SIZE);
        imageView.setFitHeight(SIZE);
        node.getChildren().add(imageView);
    }

    public static void main(String[] args) {
        launch(args);
    }
}