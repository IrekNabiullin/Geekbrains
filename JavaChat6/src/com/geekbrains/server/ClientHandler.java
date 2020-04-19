package com.geekbrains.server;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private int clientID;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientHandler(Server server, Socket socket, int clientID) { // принимаем ID клиента  в качестве аргумента
        try {
            this.server = server;
            this.socket = socket;
            this.clientID = clientID;
            System.out.println(socket.getInetAddress().toString());
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        String str = in.readUTF();
                        String messg = "Клиент #" +  clientID + ": " + str; //добавим идентификатор клиента в сообщение
 //                       System.out.println("Сообщение от клиента #" +  clientID + ": " + str);
                        if (str.equals("/end")) {
                            break;
                        }
                        server.broadcastMsg(messg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(this);
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
