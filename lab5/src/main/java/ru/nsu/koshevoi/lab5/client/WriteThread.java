package ru.nsu.koshevoi.lab5.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket socket;
    private Client client;
    private ReadThread readThread;

    public WriteThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Ошибка получения выходного потока: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendLogin(String username, String password) {
        String msg = "<command name=\"login\">" +
                "<name>" + username + "</name>" +
                "<password>" + password + "</password>" +
                "</command>";
        int length = msg.length();
        writer.println(Integer.toString(length) + msg);
    }

    public void sendMessage(String message) {
        writer.println("<command name=\"message\"><message>" + message + "</message></command>");
    }

    public void sendDownloadRequest(String fileId) {
        writer.println("<command name=\"download\"><id>" + fileId + "</id></command>");
    }

    public void sendListRequest() {
        writer.println("<command name=\"list\"></command>");
    }

    public void sendLogout() {
        writer.println("<command name=\"logout\"></command>");
        readThread.shutdown();
        try{
            socket.close();
        }catch (IOException e){
            System.out.println("Ошибка при закрытии сокета: " + e.getMessage());
        }
    }

    public void sendUpload(String fileName, String mimeType, String encodedContent){
        String msg = "<command name=\"upload\">" +
                "<name>" + fileName + "</name>" +
                "<mimeType>" + mimeType + "</mimeType>" +
                "<encoding>base64</encoding>" +
                "<content>" + encodedContent + "</content>" +
                "</command>";
        int length = msg.length();
        writer.println(Integer.toString(length) + msg);
    }

    @Override
    public void run() {
        String text;
        Scanner scanner = new Scanner(System.in);
        try {
            
            String serverResponse = reader.readLine();
            if (serverResponse != null && serverResponse.contains("<success>")) {
                client.setUserName(client.getUserName());

                readThread = new ReadThread(socket, client);
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
        } catch (IOException e) {
            System.out.println("Ошибка входа: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Ошибка при закрытии сокета: " + e.getMessage());
        }
    }
}