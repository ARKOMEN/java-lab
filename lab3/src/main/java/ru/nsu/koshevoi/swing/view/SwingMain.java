package ru.nsu.koshevoi.swing.view;

import ru.nsu.koshevoi.model.PacManModel;
import ru.nsu.koshevoi.swing.controller.SwingController;

import javax.swing.*;
import java.io.IOException;

public class SwingMain {
    public static void main(String[] args) throws IOException {
        PacManModel model = new PacManModel();
        SwingUtilities.invokeLater(() -> {
            SwingController controller = new SwingController(model);
            MainWindow mainWindow = new MainWindow(model, controller);
            mainWindow.setVisible(true);
            mainWindow.addKeyListener(controller);
        });
    }
}
