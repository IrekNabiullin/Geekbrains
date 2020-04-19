package com.geekbrains.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
    private Vector<ClientHandler> clients;
    static int clientID =0; //идентификатор клиентов

    public Server() {
        try {
            ServerSocket serverSocket = new ServerSocket(8189);
            clients = new Vector<>();
            while (true) {
                System.out.println("Ждем подключения клиента");
                Socket socket = serverSocket.accept();
//                int clientID = clients.indexOf(this); // до создания клиента индекс в веткрое к сожалению не работает
                clientID ++; // используем простой счетчик в качестве идентификатора клиентов
                ClientHandler c = new ClientHandler(this, socket, clientID); // передаем ID клиента в метод subscribe
                subcribe(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void subcribe(ClientHandler client) {
        clients.add(client);
    }

    public void unsubscribe(ClientHandler  client) {
        clients.remove(client);
    }

    public void broadcastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }
}
