package ru.nsu.koshevoi.lab5.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread{
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket socket;
    private Client client;

    public WriteThread(Socket socket, Client client){
        this.socket = socket;
        this.client = client;

        try{
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            System.out.println("Ошибка получения выходного потока: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        Scanner scanner = new Scanner(System.in);
        String text;

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        writer.println("<command name=\"login\">" +
                "<name>" + username + "</name>" +
                "<password>" + password + "</password>" +
                "</command>");

        try {
            String serverResponse = reader.readLine();
            if(serverResponse != null && serverResponse.contains("<success>")){
                System.out.println("Успешный вход в систему");
                client.setUserName(username);

                ReadThread readThread = new ReadThread(socket, client);
                readThread.start();
                while (true){
                    text = scanner.nextLine();
                    if("list".equals(text)){
                        writer.println("<command name=\"list\"></command>");
                    } else if ("logout".equals(text)) {
                        readThread.shutdown();
                        writer.println("<command name=\"logout\"></command>");
                        break;
                    } else {
                        writer.println("<command name=\"message\"><message>" + text + "</message></command>");
                    }
                    if(text.equals("exit"))
                        break;
                }
            }
        }catch (IOException e) {
            System.out.println("Ошибка входа: " + e.getMessage());
            e.printStackTrace();
        }

        try{
            socket.close();
        }catch (IOException e){
            System.out.println("Ошибка при закрытии сокета: " + e.getMessage());
        }
    }
}
