package ru.nsu.koshevoi.lab5.server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientHandler extends Thread{
    private Socket socket;
    private UserStore userStore;
    private boolean loggedIn = false;
    private PrintWriter writer;
    private static List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private static List<String> messageHistory = new CopyOnWriteArrayList<>();
    public static final int HISTORY_SIZE = 10;
    private String username;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ClientHandler(Socket socket, UserStore userStore){
        this.socket = socket;
        this.userStore = userStore;
    }

    @Override
    public void run(){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            writer = new PrintWriter(socket.getOutputStream(), true);

            clients.add(this);

            resetInactivityTimer();

            String clientMessage;
            while((clientMessage = reader.readLine()) != null){
                resetInactivityTimer();

                if(!loggedIn) {
                    if (!processLogin(clientMessage, writer)) {
                        writer.println("<error><message>Login required</message></error>");
                    }
                }else{
                    processCommand(clientMessage);
                }
            }
        } catch (IOException e){

        } finally {
            handleLogout();
            try {
                socket.close();
            }catch (IOException e){}
        }

    }

    private boolean processLogin(String message, PrintWriter writer){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(message.getBytes());
            Document doc = builder.parse(is);

            NodeList commandNodes = doc.getElementsByTagName("command");
            if(commandNodes.getLength() > 0){
                Element commandElement = (Element) commandNodes.item(0);
                String commandName = commandElement.getAttribute("name");

                if("login".equals(commandName)){
                    username = commandElement.getElementsByTagName("name").item(0).getTextContent();
                    String password = commandElement.getElementsByTagName("password").item(0).getTextContent();

                    if(userStore.loginUser(username, password)){
                        writer.println("<success></success>");
                        loggedIn = true;
                        broadcastUserLogin(username);
                        sendRecentMessages();
                    }else {
                        writer.println("<error><message>Invalid username or password</message></error>");
                    }
                    return true;
                }
            }
        }catch (Exception e){
            writer.println("<error><message>Invalid login format</message></error>");
            e.printStackTrace();
        }
        return false;
    }

    private void processCommand(String message){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(message.getBytes());
            Document doc = builder.parse(is);

            NodeList commandNodes = doc.getElementsByTagName("command");
            if(commandNodes.getLength() > 0){
                Element commandElement = (Element) commandNodes.item(0);
                String commandName = commandElement.getAttribute("name");

                if("list".equals(commandName)){
                    sendUserList();
                } else if ("message".equals(commandName)) {
                    String chatMessage = commandElement.getElementsByTagName("message").item(0).getTextContent();
                    broadcastMessage(username + ": " + chatMessage);
                    writer.println("<success></success>");
                }else if("logout".equals(commandName)){
                    handleLogout();
                    writer.println("<success></success>");
                    writer.flush();
                    socket.close();
                    return;
                }
                else {
                    writer.println("<error><message>Invalid command</message></error>");
                }
            }
        }catch (Exception e){
            writer.println("<error><message>Invalid command format</message></error>");
            e.printStackTrace();
        }
    }

    private void handleLogout(){
        if(loggedIn) {
            loggedIn = false;
            clients.remove(this);
            broadcastUserLogout(username);
        }
    }

    private void sendUserList(){
        StringBuilder response = new StringBuilder("<success><users>");
        for(ClientHandler client : clients){
            if(client.loggedIn){
                response.append("<user><name>").append(client.username).append("</name></user>");
            }
        }
        response.append("</users></success>");
        writer.println(response.toString());
    }

    private void broadcastMessage(String message){
        synchronized (messageHistory){
            messageHistory.add(message);
            if(messageHistory.size() > HISTORY_SIZE){
                messageHistory.removeFirst();
            }
        }
        for(ClientHandler clientHandler : clients){
            if(clientHandler.loggedIn){
                String formattedMessage = "<event name=\"message\"><from>" + username + "</from><message>" + message + "</message></event>";
                clientHandler.writer.println(formattedMessage);
            }
        }
    }

    private void broadcastUserLogin(String username){
        for (ClientHandler client : clients){
            if(client.loggedIn && client != this){
                client.writer.println("<event name=\"userlogin\"><name>" + username + "</name></event>");
            }
        }
    }

    private void broadcastUserLogout(String username){
        for(ClientHandler client : clients){
            if(client.loggedIn && client != this){
                client.writer.println("<event name=\"userlogout\"><name>" + username + "</name></event>");
            }
        }
    }

    private void handleInactivity(){
        if(loggedIn){
            System.out.println("Пользователь " + username + " отключен из-за бездействия.");
            handleLogout();
            try {
                socket.close();
            }catch (Exception e){}
        }
    }

    private void resetInactivityTimer(){
        scheduler.shutdown();;
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(this::handleInactivity, 1, TimeUnit.MINUTES);
    }

    private void sendRecentMessages(){
        StringBuilder response = new StringBuilder("<event name=\"recentMessages\">");
        synchronized (messageHistory){
            for(String msg : messageHistory){
                response.append("<message>").append(msg).append("</message>");
            }
        }
        response.append("</event>");
        writer.println(response.toString());
    }
}
