package ru.nsu.koshevoi.swing.controller;

import ru.nsu.koshevoi.model.Direction;
import ru.nsu.koshevoi.model.PacManModel;
import ru.nsu.koshevoi.model.State;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.event.*;

public class SwingController extends KeyAdapter implements ActionListener{
    public static final String SUBMIT_ANSWER = "submitAnswer";
    private final PacManModel model;
    private final Document inputModel;

    public SwingController(PacManModel model){
        inputModel = new PlainDocument();
        this.model = model;
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
    public void actionPerformed(ActionEvent e) {
        try {
            model.getPacMan().setUserName(inputModel.getText(0, inputModel.getLength()));
            model.updateTable();
            model.setState(State.TABLE);
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Document getInputModel() {
        return inputModel;
    }
}
