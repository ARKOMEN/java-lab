package ru.nsu.koshevoi.lab5.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Server {
    private int port;
    private ExecutorService executorService;
    private static UserStore userStore = new UserStore();
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private ConcurrentHashMap<String, FileData> files = new ConcurrentHashMap<>();

    public Server(int port){
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void start() {
        try{
            LogManager.getLogManager().readConfiguration(Server.class.getResourceAsStream("/logging.properties"));
        }catch (IOException e){
            logger.log(Level.SEVERE, "Ошибка загрузки конфигурации логирования", e);
        }

        try(ServerSocket serverSocket = new ServerSocket(port)){
            logger.info("Сервер запущен на порту: " + port);

            while(true){
                Socket socket = serverSocket.accept();
                logger.info("Новое подключение: " + socket.getInetAddress());
                new ClientHandler(socket, userStore, this).start();
            }
        }catch (IOException e){
            logger.log(Level.SEVERE, "Ошибка сервера", e);
        }
    }

    public ConcurrentHashMap<String, FileData> getFiles(){
        return files;
    }

    public static void main(String[] args){
        int port = 8080;
        Server server = new Server(port);
        server.start();
    }
}
