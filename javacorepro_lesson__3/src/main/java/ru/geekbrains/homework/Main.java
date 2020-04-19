package ru.geekbrains.homework;

import java.sql.*;

public class Main {
    private static Connection connection;
    private static Statement statement;
    public static void main(String[] args){
        try{
            setConnection();
            dropTable();
            createTable();
            addData();
            getPriceByName("item10");
            updatePriceByName("item20", 999);
            selectInPriceRange(10, 700);
        }catch(SQLException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }finally{
            disconnect();
        }
    }
    public static void createTable() throws SQLException{
        statement.execute("CREATE TABLE IF NOT EXISTS goods (id INTEGER PRIMARY KEY AUTOINCREMENT, itemID INTEGER, name TEXT, price INTEGER);");
    }
    public static void dropTable() throws SQLException{
        statement.execute("DROP TABLE IF EXISTS goods;");
    }
    public static void addData() throws SQLException{
        connection.setAutoCommit(false);
        for(int i = 0; i < 10000; i++){
            statement.executeUpdate("INSERT INTO goods(itemID, name, price) VALUES (" + i + ", 'item" + i + "',"+i*10+");");
        }
        connection.setAutoCommit(true);
    }
    public static void getPriceByName(String name) throws SQLException{
        ResultSet resultSet = statement.executeQuery("SELECT price FROM goods WHERE name = '"+name+"';");
        if(resultSet.next()) System.out.println(resultSet.getInt(1));
        else System.out.println("There is no such item!");
    }
    public static void updatePriceByName(String name, int newPrice) throws SQLException{
        statement.executeUpdate("UPDATE goods SET price = " + newPrice + " WHERE name = '" + name + "';");
    }
    public static void selectInPriceRange(int min, int max) throws SQLException{
        ResultSet resultSet = statement.executeQuery("SELECT name FROM goods WHERE price >= " + min + " AND price <= " + max +";");
        while(resultSet.next()) System.out.println(resultSet.getString(1));
    }


    public static void setConnection() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:jcprohw.db");
        statement = connection.createStatement();
    }
    public static void disconnect(){
        try{
            statement.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
