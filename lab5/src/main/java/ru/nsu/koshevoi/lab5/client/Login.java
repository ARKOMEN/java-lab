package ru.nsu.koshevoi.lab5.client;

import java.io.*;
import java.net.Socket;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.nio.ByteBuffer;

public class Login {
    private String user_name = null;
    private String password = null;
    private Socket socket;
    public Login(Socket socket) {
        this.socket = socket;
    }

    public boolean sendLogin() throws ParserConfigurationException, IOException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element rootElement = document.createElement("command");
        rootElement.setAttribute("name", "login");
        document.appendChild(rootElement);
        Element nameElement = document.createElement("name");
        nameElement.setTextContent(user_name);
        rootElement.appendChild(nameElement);
        Element passwordElement = document.createElement("password");
        passwordElement.setTextContent(password);
        rootElement.appendChild(passwordElement);
        OutputStream outputStream = socket.getOutputStream();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        Source source = new DOMSource(document);
        StreamResult result = new StreamResult(new StringWriter());
        transformer.transform(source, result);
        String xmlString = result.getWriter().toString();
        PrintWriter writer = new PrintWriter(outputStream, true);
        int length = xmlString.length();
        writer.println(length + xmlString);
        InputStream inputStream = socket.getInputStream();

    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}