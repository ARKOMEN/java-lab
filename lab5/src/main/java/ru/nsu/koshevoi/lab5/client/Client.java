package ru.nsu.koshevoi.lab5.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String hostname;
    private int port;
    private String userName;

    public Client(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    public void execute(){
        try(Socket socket = new Socket(hostname, port)){
            System.out.println("подключен к чату");

            new ReadThread(socket, this).start();
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

    public static void main(String[] args) {

        String hostname = "localhost";
        int port = 1235;

        Client client = new Client(hostname, port);
        client.execute();
    }
}