package ru.nsu.koshevoi.lab4.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
import java.util.Map;
import java.util.Objects;

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
    private Slider bodyProductionSlider;
    private Slider engineProductionSlider;
    private Slider accessoryProductionSlider;
    private Slider carProductionSlider;

    @Override
    public void start(Stage stage) throws IOException {
        model = new Model(this);
        controller = new Controller();
        this.stage = stage;
        this.stage.setOnCloseRequest((WindowEvent event) ->{
            try {
                model.stop();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.exit(0);
        });
        bodyProductionSlider = new Slider();
        engineProductionSlider = new Slider();
        accessoryProductionSlider = new Slider();
        carProductionSlider = new Slider();
        bodyProductionSlider.setMax(20);
        bodyProductionSlider.setValue(model.getM());
        engineProductionSlider.setMax(20);
        engineProductionSlider.setValue(model.getN1());
        accessoryProductionSlider.setMax(20);
        accessoryProductionSlider.setValue(model.getN2());
        carProductionSlider.setMax(20);
        carProductionSlider.setValue(model.getN3());
        bodyLabel = new Label("Колоичество кузовов: ");
        engineLabel = new Label("Количество моторов: ");
        accessoryLabel = new Label("Количество аксессуаров: ");
        carLabel = new Label("Количество машин: ");
        Map<String, Storage> map = model.getStorageList();
        bodyProductionSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        model.setM((Double) t1);
                    }
                });
        engineProductionSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        model.setN1((Double) t1);
                    }
                });
        accessoryProductionSlider.valueProperty().addListener(
            new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    model.setN2((Double) t1);
                }
            });
        carProductionSlider.valueProperty().addListener(
            new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    model.setN3((Double) t1);
                }
            });
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
        try {
            Platform.runLater(() ->{
                Map<String, Storage> map = model.getStorageList();
                for(Map.Entry<String, Storage> entry : map.entrySet()){
                    switch (entry.getValue().getType()){
                        case Body -> bodyLabel.setText("Колоичество кузовов: " + entry.getValue().getSize());
                        case Engine -> engineLabel.setText("Количество моторов: " + entry.getValue().getSize());
                        case Accessories -> accessoryLabel.setText("Количество аксессуаров: " + entry.getValue().getSize());
                        case Car ->  carLabel.setText("Количество машин: " + entry.getValue().getSize());
                    }
                }
            });
        } catch (Exception ignored) {}
    }

    private void display(){
    }
}