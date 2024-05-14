package ru.nsu.koshevoi.lab5.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private ExecutorService executorService;

    public Server(int port){
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void start() {
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Сервер запущен на порту: " + port);

            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("Новое подключение: " + socket.getInetAddress());
                executorService.execute(new ClientHandler(socket));
            }
        }catch (IOException e){
            System.out.println("Ошибка при работе сервера: " + e.getMessage());
        }
    }

    public static void main(String[] args){
        int port = 1235;
        Server server = new Server(port);
        server.start();
    }
}
