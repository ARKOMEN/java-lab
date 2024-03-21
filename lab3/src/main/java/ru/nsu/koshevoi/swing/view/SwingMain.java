package ru.nsu.koshevoi.swing.view;

import ru.nsu.koshevoi.model.PacManModel;

import javax.swing.*;
import java.io.IOException;

public class SwingMain {
    public static void main(String[] args) throws IOException {
        PacManModel model = new PacManModel();
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow(model);
            mainWindow.setVisible(true);
        });
    }
}
