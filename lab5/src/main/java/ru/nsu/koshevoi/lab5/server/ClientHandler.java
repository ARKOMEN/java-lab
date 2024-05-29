package ru.nsu.koshevoi.lab5.server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler extends Thread{
    private Socket socket;
    private UserStore userStore;
    private boolean loggedIn = false;
    private static List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private static List<String> messageHistory = new CopyOnWriteArrayList<>();
    public static final int HISTORY_SIZE = 10;
    private String username;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Server server;
    private static final Logger log = Logger.getLogger(ClientHandler.class.getName());
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ClientHandler(Socket socket, UserStore userStore, Server server){
        this.socket = socket;
        this.userStore = userStore;
        this.server = server;
    }

    @Override
    public void run(){
        try{

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            clients.add(this);

            resetInactivityTimer();

            while(true){
                int messageLength = dataInputStream.readInt();
                byte[] messageBytes = new byte[messageLength];
                dataInputStream.readFully(messageBytes);

                String clientMessage = new String(messageBytes);
                resetInactivityTimer();

                if(!loggedIn) {
                    if (!processLogin(clientMessage)) {
                        sendMessage("<error><message>Login required</message></error>");
                    }
                }else{
                    processCommand(clientMessage);
                }
            }
        } catch (IOException e){
            log.log(Level.SEVERE, "Ошибка в работе с клиетом", e);
        } finally {
            handleLogout();
            try {
                socket.close();
            }catch (IOException e){
                log.log(Level.SEVERE, "Ошибка при закрытии сокета", e);
            }
        }

    }

    private boolean processLogin(String message){
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
                        sendMessage("<success></success>");
                        loggedIn = true;
                        broadcastUserLogin(username);
                        sendRecentMessages();
                    }else {
                        sendMessage("<error><message>Invalid username or password</message></error>");
                    }
                    return true;
                }
            }
        }catch (Exception e){
            sendMessage("<error><message>Invalid login format</message></error>");
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
                    sendMessage("<success></success>");
                }else if("logout".equals(commandName)){
                    handleLogout();
                    sendMessage("<success></success>");
                    socket.close();
                    return;
                }else if("upload".equals(commandName)){
                    handleFileUpload(message);
                }else if("download".equals(commandName)){
                    handleFileDownload(message);
                } else {
                    sendMessage("<error><message>Invalid command</message></error>");
                }
            }
        }catch (Exception e){
            sendMessage("<error><message>Invalid command format</message></error>");
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
        sendMessage(response.toString());
    }

    private void broadcastMessage(String message){
        synchronized (messageHistory){
            messageHistory.add(message);
            if(messageHistory.size() > HISTORY_SIZE){
                messageHistory.removeFirst();
            }
        }
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        int length = messageBytes.length;

        for(ClientHandler clientHandler : clients){
            if(clientHandler.loggedIn){
                String formattedMessage = "<event name=\"message\"><from>" + username + "</from><message>" + message + "</message></event>";
                clientHandler.sendMessage(formattedMessage);
            }
        }
    }

    private void broadcastUserLogin(String username){
        for (ClientHandler client : clients){
            if(client.loggedIn && client != this){
                client.sendMessage("<event name=\"userlogin\"><name>" + username + "</name></event>");
            }
        }
    }

    private void broadcastUserLogout(String username){
        for(ClientHandler client : clients){
            if(client.loggedIn && client != this){
                client.sendMessage("<event name=\"userlogout\"><name>" + username + "</name></event>");
            }
        }
    }

    private void handleInactivity(){
        if(loggedIn){
            log.info("Пользователь " + username + " отключен из-за бездействия.");
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
        sendMessage(response.toString());
    }

    private void handleFileUpload(String message) {
        try {
            String fileName = extractTagValue(message, "name");
            String mimeType = extractTagValue(message, "mimeType");
            String encoding = extractTagValue(message, "encoding");
            String content = extractTagValue(message, "content");

            if ("base64".equals(encoding)) {
                byte[] fileContent = Base64.getDecoder().decode(content);

                String fileId = UUID.randomUUID().toString();

                FileData fileData = new FileData(fileId, fileName, mimeType, fileContent);
                server.getFiles().put(fileId, fileData);

                sendMessage("<success><id>" + fileId + "</id></success>");

                String fileEvent = "<event name=\"file\">" +
                        "<id>" + fileId + "</id>" +
                        "<from>" + socket.getInetAddress().getHostAddress() + "</from>" +
                        "<name>" + fileName + "</name>" +
                        "<size>" + fileContent.length + "</size>" +
                        "<mimeType>" + mimeType + "</mimeType>" +
                        "</event>";

                broadcast(fileEvent);
            } else {
                sendMessage("<error><message>Unsupported encoding</message></error>");
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ошибка при обработке загрузки файла", e);
            sendMessage("<error><message>Server error</message></error>");
        }
    }

    private void handleFileDownload(String message) {
        try {
            String fileId = extractTagValue(message, "id");
            FileData fileData = server.getFiles().get(fileId);

            if (fileData != null) {
                String encodedContent = Base64.getEncoder().encodeToString(fileData.getContent());
                sendMessage("<success>" +
                        "<id>" + fileData.getId() + "</id>" +
                        "<name>" + fileData.getName() + "</name>" +
                        "<mimeType>" + fileData.getMimeType() + "</mimeType>" +
                        "<encoding>base64</encoding>" +
                        "<content>" + encodedContent + "</content>" +
                        "</success>");
            } else {
                sendMessage("<error><message>File not found</message></error>");
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ошибка при обработке запроса на скачивание файла", e);
            sendMessage("<error><message>Server error</message></error>");
        }
    }

    private String extractTagValue(String message, String tagName) {
        String startTag = "<" + tagName + ">";
        String endTag = "</" + tagName + ">";
        int startIndex = message.indexOf(startTag) + startTag.length();
        int endIndex = message.indexOf(endTag);
        return message.substring(startIndex, endIndex);
    }

    private void broadcast(String message) {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler != this && clientHandler.loggedIn) {
                clientHandler.sendMessage(message);
            }
        }
    }

    private void sendMessage(String message){
        try{
            byte[] messageByte = message.getBytes();
            dataOutputStream.writeInt(messageByte.length);
            dataOutputStream.write(messageByte);
        }catch (IOException e){
            log.log(Level.SEVERE, "Ошибка при отправке сообщения", e);
        }
    }
}
