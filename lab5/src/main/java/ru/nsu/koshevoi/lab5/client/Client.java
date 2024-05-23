package ru.nsu.koshevoi.lab5.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import ru.nsu.koshevoi.lab5.ChatClientApp;

public class Client {
    private String hostname;
    private int port;
    private String userName;
    private String password;
    private ChatClientApp chatClientApp;
    private WriteThread writeThread;

    public Client(String hostname, int port, ChatClientApp chatClientApp, String userName, String password) {
        this.hostname = hostname;
        this.port = port;
        this.chatClientApp = chatClientApp;
        this.userName = userName;
        this.password = password;
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            writeThread = new WriteThread(socket, this);
            writeThread.start();

            writeThread.sendLogin(userName, password);
        } catch (UnknownHostException ex) {
            System.out.println("Сервер не найден: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O ошибка: " + ex.getMessage());
        }
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    String getUserName() {
        return this.userName;
    }

    public void sendMessage(String message) {
        if (writeThread != null) {
            writeThread.sendMessage(message);
        }
    }

    public void requestUserList() {
        if (writeThread != null) {
            writeThread.sendListRequest();
        }
    }

    public void uploadFile(String fileName, String mimeType, String encodedContent){
        if(writeThread != null){
            writeThread.sendUpload(fileName, mimeType, encodedContent);
        }
    }

    public void logout() {
        if (writeThread != null) {
            writeThread.sendLogout();
        }
    }

    public void displayMessage(String message) {
        chatClientApp.displayMessage(message);
    }

    public void requestFileDownload(String fileId) {
        if (writeThread != null) {
            writeThread.sendDownloadRequest(fileId);
        }
    }

    public void notifyFileReceived(String fileId, String fileName) {
        chatClientApp.notifyFileReceived(fileId, fileName);
    }

    public void saveFile(String fileName, byte[] fileContent) {
        chatClientApp.saveFile(fileName, fileContent);
    }

    public static void main(String[] args) {
        /*
        String hostname = "localhost";
        int port = 8080;

        Client client = new Client(hostname, port);
        client.execute();
        */
    }
}