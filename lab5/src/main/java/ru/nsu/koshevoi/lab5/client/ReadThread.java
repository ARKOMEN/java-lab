package ru.nsu.koshevoi.lab5.client;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.naming.ldap.SortKey;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.Socket;

public class ReadThread extends Thread{
    private BufferedReader reader;
    private Socket socket;
    private Client client;

    private volatile boolean running = true;

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
        while (running){
            try {
                String response = reader.readLine();
                if(response == null){
                    System.out.println("Соединение с сервером потеряно");
                    break;
                }
                if(!response.equals("<success></success>")) {
                    if (response.startsWith("<success><users>")) {
                        handleUserList(response);
                    } else if (response.startsWith("<event name=\"message\">")) {
                        handleChatMessage(response);
                    }else if(response.startsWith("<event name=\"userlogin\">")) {
                        handleUserLogin(response);
                    } else if (response.startsWith("<event name=\"userlogout\">")) {
                        handleUserLogout(response);
                    }else if(response.startsWith("<event name=\"recentMessages\">")){
                        handleRecentMessages(response);
                    }
                    else {
                        System.out.println("\n" + response);
                    }
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
            System.out.println("Пользователь " + userName + " вышел из чата.");
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
            System.out.println("Пользователь " + userName + " вошел в чат.");
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

            System.out.println(message);
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
            System.out.println("Список пользователей в чате: ");
            for(int i = 0; i < userNodes.getLength(); i++){
                Element userElement = (Element) userNodes.item(i);
                String userName = userElement.getElementsByTagName("name").item(0).getTextContent();
                System.out.println(" - " + userName);
            }
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
            if(reader != null){
                reader.close();
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
                System.out.println(message);
            }
        }catch (Exception e){
            System.out.println("Ошибка обработки истории сообщений: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
