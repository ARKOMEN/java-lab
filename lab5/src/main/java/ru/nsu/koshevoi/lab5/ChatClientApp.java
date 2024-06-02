package ru.nsu.koshevoi.lab5;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.nsu.koshevoi.lab5.client.Client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.SplittableRandom;
import java.util.concurrent.CountDownLatch;

import static java.lang.System.exit;

public class ChatClientApp extends Application {

    private Client client;
    private TextArea messageArea;
    private TextField inputField;
    private Button sendButton;
    private Button listButton;
    private Button logoutButton;
    private Button uploadButton;
    private Stage primaryStage;
    private VBox chatLayout;
    private static int port;
    private static String ip;
    private static CountDownLatch latch = new CountDownLatch(1);


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Chat Client");

        showLoginScene();
    }

    private void showLoginScene() {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Имя пользователя");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Пароль");

        TextField ipField = new TextField();
        ipField.setPromptText("IP");

        TextField portField = new TextField();
        portField.setPromptText("PORT");

        Button loginButton = new Button("Войти");
        loginButton.setOnAction(e -> {
            ip = ipField.getText();
            port = Integer.parseInt(portField.getText());
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (!username.isEmpty() && !password.isEmpty()) {
                initializeClient(username, password);
                showChatScene();
            } else {
                showAlert("Ошибка входа", "Имя пользователя и пароль не могут быть пустыми.");
            }
        });

        VBox loginLayout = new VBox(10, usernameField, passwordField, ipField, portField, loginButton);
        loginLayout.setPadding(new Insets(20));
        Scene loginScene = new Scene(loginLayout, 300, 200);

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void showChatScene() {
        messageArea = new TextArea();
        latch.countDown();
        messageArea.setEditable(false);

        inputField = new TextField();
        inputField.setPromptText("Введите сообщение");

        sendButton = new Button("Отправить");
        sendButton.setOnAction(e -> sendMessage());

        listButton = new Button("Список пользователей");
        listButton.setOnAction(e -> requestUserList());

        logoutButton = new Button("Выйти");
        logoutButton.setOnAction(e -> {
            logout();
            exit(0);
        });

        uploadButton = new Button("Загрузить файл");
        uploadButton.setOnAction(e -> uploadFile());

        HBox buttonBox = new HBox(10, sendButton, listButton, logoutButton, uploadButton);

        chatLayout = new VBox(10, messageArea, new HBox(10, inputField, buttonBox));
        chatLayout.setPadding(new Insets(10));
        chatLayout.setPrefSize(700, 400);

        Scene chatScene = new Scene(chatLayout);

        primaryStage.setScene(chatScene);

        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            logout();
            exit(0);
        });
    }

    private void uploadFile(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        if(file != null){
            try{
                byte[] fileContent = Files.readAllBytes(file.toPath());
                String encodedContent = Base64.getEncoder().encodeToString(fileContent);
                String mimeType = Files.probeContentType(file.toPath());
                client.uploadFile(file.getName(), mimeType, encodedContent);
            }catch (IOException e){
                showAlert("Ошибка загрузки файла", "Не удалось загрузить файл: " + e.getMessage());
            }
        }
    }

    private void initializeClient(String username, String password) {
        client = new Client(ip, port, this, username, password);
        client.execute();
    }

    public void displayMessage(String message) {
        messageArea.appendText(message + "\n");
    }

    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            client.sendMessage(message);
            inputField.clear();
        }
    }

    private void requestUserList() {
        client.requestUserList();
    }

    private void logout() {
        client.logout();
        primaryStage.close();
    }

    static public void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait();
            exit(0);
        });
    }

    public void notifyFileReceived(String fileId, String fileName) {
        Platform.runLater(() -> {
            Button downloadButton = new Button("Скачать " + fileName);
            downloadButton.setOnAction(e -> client.requestFileDownload(fileId));
            messageArea.appendText("Файл получен: " + fileName + "\n");
            VBox vBox = (VBox) messageArea.getParent().getParent();
            chatLayout.getChildren().add(downloadButton);
        });
    }

    public void saveFile(String fileName, byte[] fileContent) {
        Platform.runLater(() -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName(fileName + "t");
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                try {
                    Files.write(file.toPath(), fileContent);
                } catch (IOException e) {
                    showAlert("Ошибка сохранения файла", "Не удалось сохранить файл: " + e.getMessage());
                }
            }
        });
    }

    public static CountDownLatch getLatch(){
        return latch;
    }
}