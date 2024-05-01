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
import ru.nsu.koshevoi.lab4.model.FactoryThread;
import ru.nsu.koshevoi.lab4.model.ModelListener;
import ru.nsu.koshevoi.lab4.controller.Controller;
import ru.nsu.koshevoi.lab4.model.Model;
import ru.nsu.koshevoi.lab4.model.dealers.BMW;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class App extends Application implements ModelListener {
    private Model model;
    private Controller controller;
    private Label bodyLabel;
    private Label engineLabel;
    private Label accessoryLabel;
    private Label carLabel;
    private Label resultLabel;
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
        bodyProductionSlider = new Slider(0.0, 20.0, 10.0);
        engineProductionSlider = new Slider(0.0, 20.0, 10.0);
        accessoryProductionSlider = new Slider(0.0, 20.0, 10.0);
        carProductionSlider = new Slider(0.0, 20.0, 10.0);
        bodyProductionSlider.setValue(model.getBodyTimeout());
        bodyProductionSlider.setShowTickMarks(true);
        bodyProductionSlider.setSnapToTicks(true);
        bodyProductionSlider.setShowTickLabels(true);
        bodyProductionSlider.setBlockIncrement(2.0);
        bodyProductionSlider.setMajorTickUnit(5.0);
        bodyProductionSlider.setMinorTickCount(4);
        Label labelBody = new Label("body");
        Label labelEngine = new Label("engine");
        Label labelAccessory = new Label("accessory");
        Label labelCar = new Label("car");
        engineProductionSlider.setValue(model.getEngineTimeout());
        engineProductionSlider.setShowTickMarks(true);
        engineProductionSlider.setSnapToTicks(true);
        engineProductionSlider.setShowTickLabels(true);
        engineProductionSlider.setBlockIncrement(2.0);
        engineProductionSlider.setMajorTickUnit(5.0);
        engineProductionSlider.setMinorTickCount(4);
        accessoryProductionSlider.setValue(model.getAccessoryTimeout());
        accessoryProductionSlider.setShowTickMarks(true);
        accessoryProductionSlider.setSnapToTicks(true);
        accessoryProductionSlider.setShowTickLabels(true);
        accessoryProductionSlider.setBlockIncrement(2.0);
        accessoryProductionSlider.setMajorTickUnit(5.0);
        accessoryProductionSlider.setMinorTickCount(4);
        carProductionSlider.setValue(model.getCarTimeout());
        carProductionSlider.setShowTickMarks(true);
        carProductionSlider.setSnapToTicks(true);
        carProductionSlider.setShowTickLabels(true);
        carProductionSlider.setBlockIncrement(2.0);
        carProductionSlider.setMajorTickUnit(5.0);
        carProductionSlider.setMinorTickCount(4);
        bodyLabel = new Label("Колоичество кузовов: ");
        engineLabel = new Label("Количество моторов: ");
        accessoryLabel = new Label("Количество аксессуаров: ");
        carLabel = new Label("Количество машин на складе: ");
        resultLabel = new Label("Количество машин переданных дилерам: ");
        Map<String, Storage> map = model.getStorageList();
        bodyProductionSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        model.setBodyTimeout((Double) t1);
                    }
                });
        engineProductionSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        model.setEngineTimeout((Double) t1);
                    }
                });
        accessoryProductionSlider.valueProperty().addListener(
            new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    model.setAccessoryTimeout((Double) t1);
                }
            });
        carProductionSlider.valueProperty().addListener(
            new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    model.setCarTimeout((Double) t1);
                }
            });
        VBox leftLayout = new VBox(10);
        HBox topRightLayout = new HBox(10);
        HBox bottomLayout = new HBox(10);
        leftLayout.getChildren().addAll(
                labelBody,
                bodyProductionSlider,
                labelEngine,
                engineProductionSlider,
                labelAccessory,
                accessoryProductionSlider,
                labelCar,
                carProductionSlider
        );
        topRightLayout.getChildren().addAll(
                bodyLabel,
                engineLabel,
                accessoryLabel,
                carLabel,
                resultLabel
        );
        VBox root = new VBox(10);
        root.getChildren().addAll(leftLayout, topRightLayout, bottomLayout);
        Scene scene = new Scene(root, 1100, 400);
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
                        case Car ->  carLabel.setText("Количество машин на складе: " + entry.getValue().getSize());
                    }
                }
                int n = 0;
                List<FactoryThread> list = model.getListOfDealers();
                for(FactoryThread dealer : list){
                    n += ((BMW)dealer).getResult();
                }
                resultLabel.setText("Количество машин переданных дилерам: " + n);
            });
        } catch (Exception ignored) {}
    }
}