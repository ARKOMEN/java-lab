package ru.nsu.koshevoi.lab5.client;

import ru.nsu.koshevoi.lab5.ChatClientApp;
import ru.nsu.koshevoi.lab5.server.ClientHandler;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String hostname;
    private int port;
    private String userName;
    private ChatClientApp chatClientApp;

    public Client(String hostname, int port, ChatClientApp chatClientApp){
        this.hostname = hostname;
        this.port = port;
        this.chatClientApp = chatClientApp;
    }

    public void execute(){
        try{
            Socket socket = new Socket(hostname, port);
            new WriteThread(socket, this).start();
        }catch (UnknownHostException ex){
            System.out.println("Сервер не найден: " + ex.getMessage());
        }catch (IOException ex){
            System.out.println("I/O ошибка: " + ex.getMessage());
        }
    }

    void setUserName(String userName){
        this.userName = userName;
    }

    String getUserName(){
        return this.userName;
    }

    public void sendMessage(String message){

    }
}