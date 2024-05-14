package ru.nsu.koshevoi.lab5.client;

import javax.naming.ldap.SortKey;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread{
    private BufferedReader reader;
    private Socket socket;
    private Client client;

    public ReadThread(Socket socket, Client client){
        this.socket = socket;
        this.client = client;

        try{
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        }catch (IOException e){
            System.out.println("Ошибка получения входного потока: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while (true){
            try {
                String response = reader.readLine();
                System.out.println("\n" + response);

                if(client.getUserName() != null){
                    System.out.println("[ " + client.getUserName() + "]: ");
                }
            }catch (IOException e){
                System.out.println("Ошибка чтения из потока " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }
}
