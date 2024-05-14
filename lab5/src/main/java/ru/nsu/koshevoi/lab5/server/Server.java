package ru.nsu.koshevoi.lab5.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;

    public Server(int port){
        this.port = port;
    }

    public void start() {
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Сервер запущен на порту: " + port);

            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("Новое подключение: " + socket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start();
            }
        }catch (IOException e){
            System.out.println("Ошибка при работе сервера: " + e.getMessage());
        }
    }

    public static void main(String[] args){
        int port = 1234;
        Server server = new Server(port);
        server.start();
    }
}
