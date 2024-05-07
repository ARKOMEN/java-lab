package ru.nsu.koshevoi.lab5.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static Socket socket;

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}