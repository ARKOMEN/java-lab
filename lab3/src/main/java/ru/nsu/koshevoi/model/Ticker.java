package ru.nsu.koshevoi.model;

import java.io.IOException;

public class Ticker extends Thread {

    private final PacManModel model;

    public Ticker(PacManModel model) {
        this.model = model;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Thread.sleep(model.getTimeout());
                model.update();
            } catch (InterruptedException e) {
                break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
