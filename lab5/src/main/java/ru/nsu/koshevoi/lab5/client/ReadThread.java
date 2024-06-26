package ru.nsu.koshevoi.lab5.client;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.naming.ldap.SortKey;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.Socket;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;

public class ReadThread extends Thread{
    private Socket socket;
    private Client client;
    private CountDownLatch latch;

    private volatile boolean running = true;

    private DataInputStream dataInputStream;

    public ReadThread(Socket socket, Client client, CountDownLatch latch){
        this.socket = socket;
        this.client = client;
        this.latch = latch;
        try{
            InputStream input = socket.getInputStream();
            dataInputStream = new DataInputStream(input);
        }catch (IOException e){
            System.out.println("Ошибка получения входного потока: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try {
            latch.await();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        while (running){
            try {
                int messageLength = dataInputStream.readInt();
                byte[] messageByte = new byte[messageLength];
                dataInputStream.readFully(messageByte);
                String response = new String(messageByte);
                if (response.contains("users") || response.contains("Users")) {
                    handleUserList(response);
                } else if (response.contains("message")) {
                    handleChatMessage(response);
                }else if(response.contains("userlogin")) {
                    handleUserLogin(response);
                } else if (response.contains("userlogout")) {
                    handleUserLogout(response);
                }else if(response.contains("recentMessages")) {
                    handleRecentMessages(response);
                }else if (response.contains("file")) {
                    handleFileEvent(response);
                } else if (response.contains("id")) {
                    handleFileDownload(response);
                } else if(response.contains("error")){
                    System.out.println("\n" + response);
                }
            }catch (IOException e){
                if(running) {
                    System.out.println("Ошибка чтения из потоков: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            }
        }
        cleanup();
    }

    private void handleUserLogout(String response){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(response.getBytes());
            Document doc = builder.parse(is);

            String userName = doc.getElementsByTagName("name").item(0).getTextContent();
            client.displayMessage("Пользователь " + userName + " вышел из чата.");
        }catch (Exception e){
            System.out.println("Ошибка обработки уведомления об отключении пользователя: " + e.getMessage());
        }
    }

    private void handleUserLogin(String response){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(response.getBytes());
            Document doc = builder.parse(is);

            String userName = doc.getElementsByTagName("name").item(0).getTextContent();
            client.displayMessage("Пользователь " + userName + " вошел в чат.");
        }catch (Exception e){
            System.out.println("Ошибка обработки уведомления о новом пользователе: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleChatMessage(String response){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(response.getBytes());
            Document doc = builder.parse(is);

            String message = doc.getElementsByTagName("message").item(0).getTextContent();
            String name = doc.getElementsByTagName("from").item(0).getTextContent();

            client.displayMessage(name + " " + message);
        }catch (Exception e){
            System.out.println("Ошибка обработки сообщения: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleUserList(String response){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(response.getBytes());
            Document doc = builder.parse(is);

            NodeList userNodes = doc.getElementsByTagName("user");
            if(userNodes.getLength() == 0){
                userNodes = doc.getElementsByTagName("User");
            }
            StringBuilder users = new StringBuilder("Список пользователей в чате:\n");
            for(int i = 0; i < userNodes.getLength(); i++){
                Element userElement = (Element) userNodes.item(i);
                users.append(" - ").append(userElement.getElementsByTagName("name").item(0).getTextContent()).append("\n");
            }
            client.displayMessage(users.toString());
        }catch (Exception e){
            System.out.println("Ошибка обработки списка пользователей: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void shutdown(){
        running = false;
        try {
            socket.close();
        }catch (IOException e){
            System.out.println("Ошибка при закрытии сокета: " + e.getMessage());
        }
    }

    private void cleanup(){
        try{
            if(dataInputStream != null){
                dataInputStream.close();
            }
        }catch (IOException e){
            System.out.println("Ошибка при закрытии входного потока: " + e.getMessage());
        }
    }

    private void handleRecentMessages(String response){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(response.getBytes());
            Document doc = builder.parse(is);

            NodeList messageNodes = doc.getElementsByTagName("message");
            System.out.println("История сообщений:");
            for(int i = 0; i < messageNodes.getLength(); i++){
                String message = messageNodes.item(i).getTextContent();
                client.displayMessage(message);
            }
        }catch (Exception e){
            System.out.println("Ошибка обработки истории сообщений: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleFileEvent(String response) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(response.getBytes());
            Document doc = builder.parse(is);

            String id = doc.getElementsByTagName("id").item(0).getTextContent();
            String from = doc.getElementsByTagName("from").item(0).getTextContent();
            String name = doc.getElementsByTagName("name").item(0).getTextContent();
            String size = doc.getElementsByTagName("size").item(0).getTextContent();
            String mimeType = doc.getElementsByTagName("mimeType").item(0).getTextContent();

            String message = "Пользователь " + from + " отправил файл: " + name + " (" + size + " байт, " + mimeType + ")";
            client.displayMessage(message);

            client.notifyFileReceived(id, name);
        } catch (Exception e) {
            System.out.println("Ошибка обработки уведомления о новом файле: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleFileDownload(String response) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(response.getBytes());
            Document doc = builder.parse(is);

            String id = doc.getElementsByTagName("id").item(0).getTextContent();
            String name = doc.getElementsByTagName("name").item(0).getTextContent();
            String mimeType = doc.getElementsByTagName("mimeType").item(0).getTextContent();
            String encoding = doc.getElementsByTagName("encoding").item(0).getTextContent();
            String content = doc.getElementsByTagName("content").item(0).getTextContent();

            if ("base64".equals(encoding)) {
                byte[] fileContent = Base64.getDecoder().decode(content);

                client.saveFile(name, fileContent);
            } else {
                client.displayMessage("Ошибка: Неподдерживаемое кодирование файла");
            }
        } catch (Exception e) {
            System.out.println("Ошибка обработки скачивания файла: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
