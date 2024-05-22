package ru.nsu.koshevoi.lab5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import ru.nsu.koshevoi.lab5.client.Client;

public class ChatClientApp extends Application {

    private Client client;
    private TextArea messageArea;
    private TextField inputField;
    private Button sendButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chat Client");

        // Создание элементов интерфейса
        messageArea = new TextArea();
        messageArea.setEditable(false);

        inputField = new TextField();
        inputField.setPromptText("Введите сообщение");

        sendButton = new Button("Отправить");
        sendButton.setOnAction(e -> sendMessage());

        // Организация расположения элементов
        VBox layout = new VBox(10, messageArea, new HBox(10, inputField, sendButton));
        layout.setPrefSize(400, 300);

        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Инициализация клиента и соединение с сервером
        String hostname = "localhost"; // Измените на нужный хост
        int port = 12345; // Измените на нужный порт
        client = new Client(hostname, port, this);
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
}