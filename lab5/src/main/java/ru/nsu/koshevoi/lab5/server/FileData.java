package ru.nsu.koshevoi.lab5.server;

public class FileData {
    private String id;
    private String name;
    private String mimeType;
    private byte[] content;

    public FileData(String id, String name, String mimeType, byte[] content){
        this.id = id;
        this.name = name;
        this.mimeType = mimeType;
        this.content = content;
    }

    public byte[] getContent(){
        return content;
    }

    public String getMimeType(){
        return mimeType;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }
}
