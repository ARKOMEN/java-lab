package ru.nsu.koshevoi.JavaFX.controller;

import javafx.event.EventHandler;

import ru.nsu.koshevoi.model.Direction;
import ru.nsu.koshevoi.model.PacManModel;
import javafx.scene.input.KeyEvent;

public class JavaFXController implements EventHandler<KeyEvent> {

    private final PacManModel model;

    public JavaFXController(PacManModel model) {
        this.model = model;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                model.setNewPacManDirection(Direction.UP);
                break;
            case DOWN:
                model.setNewPacManDirection(Direction.DOWN);
                break;
            case LEFT:
                model.setNewPacManDirection(Direction.LEFT);
                break;
            case RIGHT:
                model.setNewPacManDirection(Direction.RIGHT);
                break;
        }
    }
}