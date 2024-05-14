package ru.nsu.koshevoi.lab5.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)){

            String clientMessage;
            while((clientMessage = reader.readLine()) != null || socket != null){
                //System.out.println("Сообщение от клиента " + clientMessage);
                writer.println("Эхо: " + clientMessage);
            }
        } catch (IOException e){
            System.out.println("ошибка в работе с клиентом " + e.getMessage());
        } finally {
            try{
                System.out.println('1');
                socket.close();
            }catch (IOException e){
                System.out.println("Ошибка при закрытии сокета: " + e.getMessage());
            }
        }

    }
}
