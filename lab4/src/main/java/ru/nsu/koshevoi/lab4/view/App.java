package ru.nsu.koshevoi.lab4.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import ru.nsu.koshevoi.lab4.model.ModelListener;
import ru.nsu.koshevoi.lab4.controller.Controller;
import ru.nsu.koshevoi.lab4.model.Model;

import java.io.IOException;

public class App extends Application implements ModelListener {
    private Model model;
    private Controller controller;
    @Override
    public void start(Stage stage) throws IOException {
        model = new Model();
        controller = new Controller();
        model.setListener(this);
        Slider carBodyProductionSlider = new Slider();
        Slider engineProductionSlider = new Slider();
        Slider accessoryProductionSlider = new Slider();
        Slider machineProductionSlider = new Slider();
        carBodyProductionSlider.setMax(100);
        engineProductionSlider.setMax(100);
        accessoryProductionSlider.setMax(100);
        machineProductionSlider.setMax(100);
        Label carBodyLabel = new Label("Колоичество кузовов: ");
        Label engineLabel = new Label("Количество моторов: ");
        Label accessoryLabel = new Label("Количество аксессуаров: ");
        Label machineLabel = new Label("Количество машин: ");
        Button stopButton = new Button("Stop");
        Button startButton = new Button("Start");
        Button endButton = new Button("End");
        VBox leftLayout = new VBox(10);
        HBox topRightLayout = new HBox(10);
        HBox bottomLayout = new HBox(10);
        leftLayout.getChildren().addAll(
                carBodyProductionSlider,
                engineProductionSlider,
                accessoryProductionSlider,
                machineProductionSlider
        );
        topRightLayout.getChildren().addAll(
                carBodyLabel,
                engineLabel,
                accessoryLabel
        );
        bottomLayout.getChildren().addAll(stopButton, startButton, endButton);
        VBox root = new VBox(10);
        root.getChildren().addAll(leftLayout, topRightLayout, bottomLayout);
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Production Sliders");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void onModelChanged(){

    }
    private void display(){

    }
}