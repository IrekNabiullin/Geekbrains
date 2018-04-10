/*
 *
 * Java Core Pro. Home work 2. Базы данных (SQL)
 *
 * @author Irek Nabiullin
 * @version dated Apr 09, 2018
 * @link https://github.com/IrekNabiullin
 *
 *  Задание:
 * 1. Сформировать таблицу товаров (id, prodid, title, cost) запросом из Java-приложения:
 * id – порядковый номер записи, первичный ключ;
 * prodid – уникальный номер товара;
 * title – название товара;
 * cost – стоимость.
 * 2. При запуске приложения очистить таблицу и заполнить 10000 товаров вида:
 * id_товара 1 товар1 10
 * id_товара 2 товар2 20
 * id_товара 3 товар3 30
 * …
 * id_товара 10000 товар10000 100000
 * 3. Написать консольное приложение, которое позволяет узнать цену товара по его имени, либо
 *    вывести сообщение «Такого товара нет», если товар не обнаружен в базе. Консольная
 *    команда: «/цена товар545».
 * 4. Добавить возможность изменения цены товара. Указываем имя товара и новую цену.
 *    Консольная команда: «/сменитьцену товар10 10000».
 * 5. Вывести товары в заданном ценовом диапазоне. Консольная команда: «/товарыпоцене 100 600».
 */

package ru.geekbrains;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    public static void main(String[] args) {

        try{
            connect();
            createTable(); // Задание 1. Формируем таблицу товаров
            clearTable();  // Задание 2. При запуске приложения очищаем таблицу
            preparedStatementBatchData(); // и заполняем ее 10000 товарами

            while (true) {
                getData(getInputFromScanner()); //в цикле опрашиваем пользователя
            }

        }catch(SQLException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){

        }finally{
            disconnect();
        }
    }

    public static void createTable() throws SQLException{
        statement.execute("CREATE TABLE IF NOT EXISTS goods (id INTEGER PRIMARY KEY AUTOINCREMENT, prodid INTEGER, title TEXT, cost INTEGER);");
    }

    public static void clearTable() throws SQLException{
        statement.execute("DELETE FROM goods;");
    }

    public static void preparedStatementBatchData() throws SQLException {
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement("INSERT INTO goods(prodid, title, cost) VALUES (?,?,?);");
        int kMin = 1;
        int kMax = 10000;
            for (int i = kMin; i <= kMax; i++) {
            preparedStatement.setInt(1, (i));
            preparedStatement.setString(2, "товар" + i);
            preparedStatement.setInt(3, (i * 10));
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        connection.commit(); //завершаем тразакцию в БД
    }

    // получаем запросы из консоли
    private static String getInputFromScanner() {
        System.out.println("Режим работы с базой данных:");
        System.out.println("1. Чтобы узнать цену товара введите начиная со слеша слово \"/цена\" \n" +
                "и через пробел наименование товара, например: /цена товар500");
        System.out.println("2. Чтобы сменить цену на товар введите начиная со слеша словосочетание \"/сменитьцену\" \n" +
                "и через пробел наименование товара и новую цену, например: /сменить цену товар500 1000");
        System.out.println("3. Чтобы вывести товары в пределах ценового диапазона ведите начиная со слеша словосочетание \"/товарыпоцене\" \n" +
                " и через пробел нижнюю и верхнюю границу диапазона цены (включительно), например: /товары по цене 500 1000");
        System.out.println("Для завершения работы введите \"/end\"");
        Scanner sc = new Scanner(System.in);
        String userRequest = sc.nextLine();
        if (userRequest.equals("/end")) {
            try {
                sc.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
            disconnect();
        }
            System.exit(0);
        }
        System.out.println("userRequest = " + userRequest );
        return userRequest;
    }

    private static void getData(String userRequest) throws SQLException {
        int request =0;
        String[] tokens = userRequest.split("\\s");
        // Задание 3. Узнаем цену товара
            if (tokens[0].equals("/цена")) {
                if (tokens.length == 2) {
                    ResultSet resultSet = statement.executeQuery("SELECT cost FROM goods WHERE title = '" + tokens[1] + "';");
                    if (resultSet.next()) {
 //                       while (resultSet.next())
                            System.out.println(resultSet.getInt("cost"));
                    } else {
                        System.out.println("Такого товара нет в базе.");
                    }
                } else
                    System.out.println("Команда введена неверно. Попробуйте повторить.");

         // Задание 4. Меняем цену товара
            } else if (tokens[0].equals("/сменитьцену")) {
                if (tokens.length == 3) {
                    statement.executeUpdate("UPDATE goods SET cost = '" + Integer.parseInt(tokens[2]) + "' WHERE title = '" + tokens[1] + "';");
                    System.out.println("Цена товара " + tokens[1] + " изменена на " + tokens[2]);
                } else
                    System.out.println("Команда введена неверно. Попробуйте повторить.");

         // Задание 5. Выводим товары из заданного ценового диапазона
            } else if (tokens[0].equals("/товарыпоцене")) {
                if (tokens.length == 3) {
                    if (Integer.parseInt(tokens[1])>0 && Integer.parseInt(tokens[2])>0 ) {
                        int lowCost = Integer.parseInt(tokens[1]);
                        int upCost = Integer.parseInt(tokens[2]);
                        ResultSet resultSet = statement.executeQuery("SELECT title FROM goods WHERE cost >= '" + lowCost + "' AND cost <= '" + upCost + "';" );
                        while(resultSet.next()) System.out.println(resultSet.getString(1));
                    }else
                    System.out.println("Команда введена неверно. Попробуйте повторить.");
                } else
                        System.out.println("Команда введена неверно. Попробуйте повторить.");
            } else
                System.out.println("Команда введена неверно. Попробуйте повторить.");
    }


     public static void connect() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:jcorepro2.db");
        statement = connection.createStatement();
    }


    public static void disconnect(){
        try{
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}

