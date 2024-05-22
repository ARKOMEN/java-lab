package ru.nsu.koshevoi.lab5.server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Base64;
import java.io.*;

public class UserStore {
    private ConcurrentHashMap<String, String> users = new ConcurrentHashMap<>();
    private static final String USER_STORE_FILE = "userStore.dat";

    public UserStore(){
        loadUserStore();
    }

    public synchronized boolean registerUser(String username, String password){
        if(users.containsKey(username)){
            return false;
        }
        String hashedPassword = hashPassword(password);
        users.put(username, hashedPassword);
        saveUserStore();
        return true;
    }

    public boolean loginUser(String username, String password){
        if(!users.containsKey(username)){
            return registerUser(username, password);
        }
        String hashedPassword = users.get(username);
        return hashedPassword.equals(hashPassword(password));
    }

    private String hashPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Ошибка хэширования пароля", e);
        }
    }

    private void saveUserStore(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_STORE_FILE))){
            oos.writeObject(users);
        }catch (IOException e){
            System.out.println("Ошибка при сохранении пользователя хранилища: " + e.getMessage());
        }
    }

    private void loadUserStore(){
        File file = new File(USER_STORE_FILE);
        if(!file.exists()){
            saveUserStore();
            return;
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_STORE_FILE))){
            users = (ConcurrentHashMap<String, String>) ois.readObject();
        }catch (FileNotFoundException e){
            System.out.println("Файл пользовательского хранилища не найден: " + e.getMessage());
        }catch (IOException | ClassNotFoundException e){
            System.out.println("Ошибка при загрузке пользовательского хранилища: " + e.getMessage());
        }
    }
}
