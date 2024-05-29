package ru.nsu.koshevoi.lab5.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread {
    private Socket socket;
    private Client client;
    private ReadThread readThread;
    private DataOutputStream dataOutputStream;
    private BufferedReader reader;

    public WriteThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(output);
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
        sendMessage(msg);
    }

    public void sendMessage(String message) {
        try{
            byte[] messageBytes = message.getBytes();
            dataOutputStream.writeInt(messageBytes.length);
            dataOutputStream.write(messageBytes);
        }catch (IOException e){
            System.out.println("Ошибка отправки сообщения: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendDownloadRequest(String fileId) {
        sendMessage("<command name=\"download\"><id>" + fileId + "</id></command>");
    }

    public void sendListRequest() {
        sendMessage("<command name=\"list\"></command>");
    }

    public void sendLogout() {
        sendMessage("<command name=\"logout\"></command>");
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
        sendMessage(msg);
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
                        sendListRequest();
                    } else if ("logout".equals(text)) {
                        readThread.shutdown();
                        sendLogout();
                        break;
                    } else {
                        sendMessage("<command name=\"message\"><message>" + text + "</message></command>");
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