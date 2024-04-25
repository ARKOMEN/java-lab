package ru.nsu.koshevoi.lab4.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.nsu.koshevoi.lab4.model.ModelListener;
import ru.nsu.koshevoi.lab4.controller.Controller;
import ru.nsu.koshevoi.lab4.model.Model;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.StorageType;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

public class App extends Application implements ModelListener {
    private Model model;
    private Controller controller;
    private Label bodyLabel;
    private Label engineLabel;
    private Label accessoryLabel;
    private Label carLabel;
    private VBox leftLayout = new VBox(10);
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        model = new Model();
        controller = new Controller();
        model.setListener(this);
        this.stage = stage;
        this.stage.setOnCloseRequest((WindowEvent event) ->{
            model.stop();
            List<Future<?>> futureList = model.getFutureList();
            for(Future<?> future : futureList){
                while(!future.isDone()){
                    continue;
                }
            }
        });
        Slider bodyProductionSlider = new Slider();
        Slider engineProductionSlider = new Slider();
        Slider accessoryProductionSlider = new Slider();
        Slider carProductionSlider = new Slider();
        bodyProductionSlider.setMax(20);
        engineProductionSlider.setMax(20);
        accessoryProductionSlider.setMax(20);
        carProductionSlider.setMax(20);
        bodyProductionSlider.valueProperty().addListener(((observableValue, number, t1) -> {
            Map<String, Storage> map = model.getStorageList();
            for(Map.Entry<String, Storage> entry : map.entrySet()){
                switch (entry.getValue().getType()){
                    case Body -> {
                        bodyLabel.setText("Колоичество кузовов: " + entry.getValue().getSize());

                    }
                    case Engine -> engineLabel.setText("Количество моторов: "  + entry.getValue().getSize());
                    case Accessories -> accessoryLabel.setText("Количество аксессуаров: " + entry.getValue().getSize());
                    case Car ->  carLabel.setText("Количество машин: " + entry.getValue().getSize());}
            }
        }));
        bodyLabel = new Label("Колоичество кузовов: ");
        engineLabel = new Label("Количество моторов: ");
        accessoryLabel = new Label("Количество аксессуаров: ");
        carLabel = new Label("Количество машин: ");
        VBox leftLayout = new VBox(10);
        HBox topRightLayout = new HBox(10);
        HBox bottomLayout = new HBox(10);
        leftLayout.getChildren().addAll(
                bodyProductionSlider,
                engineProductionSlider,
                accessoryProductionSlider,
                carProductionSlider
        );
        topRightLayout.getChildren().addAll(
                bodyLabel,
                engineLabel,
                accessoryLabel,
                carLabel
        );
        VBox root = new VBox(10);
        root.getChildren().addAll(leftLayout, topRightLayout, bottomLayout);
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle("Production Sliders");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void onModelChanged() {
        try {/*
            model.setM((int) carProductionSlider.getValue());
            model.setN1((int) bodyProductionSlider.getValue());
            model.setN2((int) engineProductionSlider.getValue());
            model.setN3((int) accessoryProductionSlider.getValue());*/
            display();
        } catch (Exception e) {}
    }

    private void display(){
    }
}