/**
 * Core Java. Home work 6. Пишем Chat
 *
 * @author Irek Nabiullin
 * @version dated March 28, 2018
 * @link https://github.com/IrekNabiullin
 *
 *
 *  Задание:
 *  1. Разобраться с кодом
 *  2. Добавить имена клиентам: клиент #1, клиент #2, ...
 *  чтобы в сообщениях было понятно от кого сообщение
 *  клиент #1: Привет
 *  клиент #2: Привет-2 *
 */


package com.geekbrains.server;

public class MainServer {
    public static void main(String[] args) {
        new Server();
    }    
}


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

package com.geekbrains.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Chat Client");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {

      launch(args);  //пробовал написать цикл для запуска нескольких клиентов, но столкнулся с тем, что launch можно запускать только один раз. Пришлось бы дублировать несколько классов для несколтких клиентов...

    }
}

import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        String str = in.readUTF();
                        textArea.appendText(str);
                        textArea.appendText("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg() {
        try {
            String str = textField.getText();
            out.writeUTF(str);
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
 при закрытии окна клиента оно закрывается, но процесс не останавливается, висит в фоне!
*/

<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.control.Button?>

<VBox fx:controller="com.geekbrains.client.Controller"
      xmlns:fx="http://javafx.com/fxml" alignment="center">
    <TextArea fx:id="textArea" editable="false" VBox.vgrow="ALWAYS"/>
    <HBox>
        <TextField promptText="Напишите сообщение..." HBox.hgrow="ALWAYS" fx:id="textField" onAction="#sendMsg"/>
        <Button text="Отправить" onAction="#sendMsg"/>
    </HBox>
</VBox>
