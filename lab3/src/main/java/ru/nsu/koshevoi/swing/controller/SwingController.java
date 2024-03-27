package ru.nsu.koshevoi.swing.controller;

import ru.nsu.koshevoi.model.Direction;
import ru.nsu.koshevoi.model.PacManModel;

import java.awt.event.*;

public class SwingController extends WindowAdapter implements KeyListener {
    private final PacManModel model;

    public SwingController(PacManModel model){
        this.model = model;
    }
    @Override
    public void windowClosing(WindowEvent e) {
        try {
            model.close();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        e.getWindow().setVisible(false);
        e.getWindow().dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                model.setNewPacManDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                model.setNewPacManDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                model.setNewPacManDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                model.setNewPacManDirection(Direction.RIGHT);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
