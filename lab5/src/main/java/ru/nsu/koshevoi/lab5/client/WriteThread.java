package ru.nsu.koshevoi.lab5.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread{
    private PrintWriter writer;
    private Socket socket;
    private Client client;

    public WriteThread(Socket socket, Client client){
        this.socket = socket;
        this.client = client;

        try{
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        }catch (IOException e){
            System.out.println("Ошибка получения выходного потока: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ваше имя: ");
        String userName = scanner.nextLine();
        client.setUserName(userName);
        writer.println(userName);

        String text;

        do{
            text = scanner.nextLine();
            writer.println(text);
        }while (!text.equals("exit"));

        try{
            System.out.println('2');
            socket.close();
        }catch (IOException e){
            System.out.println("Ошибка при закрытии сокета: " + e.getMessage());
        }
    }
}
