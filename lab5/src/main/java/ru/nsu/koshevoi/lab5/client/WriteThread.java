package ru.nsu.koshevoi.lab5.client;

import ru.nsu.koshevoi.lab5.ChatClientApp;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;

public class WriteThread extends Thread {
    private Socket socket;
    private Client client;
    private ReadThread readThread;
    private DataOutputStream dataOutputStream;
    private BufferedReader reader;

    private String running = "false";

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
        if(readThread != null) {
            readThread.shutdown();
        }
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
        try {
            InputStream input = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(input);
            int messageLength = dataInputStream.readInt();
            byte[] messageByte = new byte[messageLength];
            dataInputStream.readFully(messageByte);
            String serverResponse = new String(messageByte);

            if (serverResponse.contains("success")) {
                running = "true";
                client.setUserName(client.getUserName());

                readThread = new ReadThread(socket, client, ChatClientApp.getLatch());
                readThread.run();
            } else if (serverResponse.contains("error")) {
                running = serverResponse;
                ChatClientApp.showAlert("error", serverResponse);
            }
        } catch (IOException e) {
            System.out.println("Ошибка входа: " + e.getMessage());
            e.printStackTrace();
        }
    }
}