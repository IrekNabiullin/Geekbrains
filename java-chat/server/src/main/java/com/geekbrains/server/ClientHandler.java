package com.geekbrains.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            System.out.println(socket.getInetAddress().toString());
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        String str = in.readUTF();
                        // /auth login1 pass1
                        if (str.startsWith("/auth ")) {
                            String[] tokens = str.split("\\s");
                            if (tokens.length == 3) {
                                String nickFromDB = SQLHandler.getNickByLoginAndPassword(tokens[1], tokens[2]);
                                if (nickFromDB != null) {
                                    if (!server.isNickBusy(nickFromDB)) {
                                        sendMsg("/authok " + nickFromDB);
                                        nickname = nickFromDB;
                                        server.subcribe(this);
                                        break;
                                    } else {
                                        sendMsg("Учетная запись уже используется");
                                    }
                                } else {
                                    sendMsg("Неверный логин/пароль");
                                }
                            } else {
                                sendMsg("Неверный формат данных авторизации");
                            }
                        }
                        if (str.startsWith("/registration ")) {
                            String[] tokens = str.split("\\s");
                            // /registration login5 pass5 nick5
                            if (tokens.length == 4) {
                                if (SQLHandler.tryToRegister(tokens[1], tokens[2], tokens[3])) {
                                    sendMsg("Регистрация прошла успешно");
                                } else {
                                    sendMsg("Введены некорректные логин/пароль/ник");
                                }
                            }
                        }
                    }
                    while (true) {
                        String str = in.readUTF();
                        System.out.println("Сообщение от клиента " + nickname + ": " + str);
                        if (!str.startsWith("/")) {
                            server.broadcastMsg(nickname + ": " + str);
                        } else {
                            if (str.equals("/end")) {
                                break;
                            }
                            if (str.startsWith("/w ")) {
                                // /w nick2 hello java
                                String[] tokens = str.split("\\s", 3);
                                if (tokens.length == 3) {
                                    server.personalMsg(this, tokens[1], tokens[2]);
                                } else {
                                    sendMsg("Неверный формат личного сообщения");
                                }
                            }
                            //обрабатываем запрос на смену ника:
                            if (str.startsWith("/changenick ")) {
                                // /changenick newnick
                                String[] tokens = str.split("\\s", 2);
                                if (tokens.length == 2) {
                                    String loginFromDB = SQLHandler.getLoginByNickname(this.getNickname()); //опрелеяем логин клиента
                                    System.out.println("nick: " + this.getNickname());  // в консоль для отладки
                                    System.out.println("login: " + loginFromDB);        // в консоль для отладки
                                    System.out.println("new nick: " + tokens[1]);       // в консоль для отладки
                                    if (SQLHandler.tryToChangeNick(loginFromDB, tokens[1])) { // меняем ник у данного логина в базе данных
                                        server.broadcastMsg(nickname + " has changed nickname to: " + tokens[1]); // сообщаем всем о замене ника
                                        this.nickname = tokens[1];                      // меняем ник у данного логина на сервере
                                    } else {
                                        sendMsg("Ошибка! Ник не должен содержать пробелы и служебные символы.");
                                    }
                                }
                            }
                        }
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
                    server.unsubscribe(ClientHandler.this);
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
