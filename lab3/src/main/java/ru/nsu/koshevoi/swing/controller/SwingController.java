package ru.nsu.koshevoi.swing.controller;

import ru.nsu.koshevoi.model.PacManDirection;
import ru.nsu.koshevoi.model.PacManModel;

import javax.swing.text.Document;
import java.awt.event.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class SwingController extends WindowAdapter implements ActionListener {
    private final PacManModel model;
    private final Document inputModel;

    public SwingController(PacManModel model, Document inputModel){
        this.model = model;
        this.inputModel = inputModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        model.movePacMan(PacManDirection.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        model.movePacMan(PacManDirection.DOWN);
                        break;
                    case KeyEvent.VK_LEFT:
                        model.movePacMan(PacManDirection.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        model.movePacMan(PacManDirection.RIGHT);
                        break;
                }
            }
        });
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

    public Document getInputModel() {
        return inputModel;
    }
}
