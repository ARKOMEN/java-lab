package ru.nsu.koshevoi.model;

import javax.security.auth.callback.PasswordCallback;

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
            }
        }
    }
}
