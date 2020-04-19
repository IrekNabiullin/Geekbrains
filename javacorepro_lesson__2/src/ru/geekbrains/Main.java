package ru.geekbrains;

import java.sql.*;

public class Main {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    public static void main(String[] args) {
        try{
            connect();
            createTable();
            clearTable();
//            addData();
//            updateData();
//            preparedStatementData();
            deleteData();
//            getData();
            statement.executeUpdate("INSERT INTO students(name, score) VALUES ('Rick1', 10);");
            Savepoint sp1 = connection.setSavepoint(); //отключает автокоммит
            statement.executeUpdate("INSERT INTO students(name, score) VALUES ('Rick2', 10);");
            connection.rollback(sp1);
            statement.executeUpdate("INSERT INTO students(name, score) VALUES ('Rick3', 10);");
            connection.commit();

        }catch(SQLException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){

        }finally{
            disconnect();
        }
    }
    public static void createTable() throws SQLException{
        statement.execute("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, score INTEGER);");
    }
    public static void clearTable() throws SQLException{
        statement.execute("DELETE FROM students;");
    }
    public static void preparedStatementBatchData() throws SQLException{
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement("INSERT INTO students(name, score) VALUES (?,?);");
        for(int i = 0; i < 10; i++){
            preparedStatement.setString(1, "Rick" + i);
            preparedStatement.setInt(2, (i*10));
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        connection.commit();
    }
    public static void addData() throws SQLException{
        for(int i = 0; i < 10; i++){
            statement.executeUpdate("INSERT INTO students(name, score) VALUES ('Rick" + i + "',"+i*10+");");
        }
    }
    public static void updateData() throws SQLException{
        statement.executeUpdate("UPDATE students SET score=100 WHERE name = 'Rick4';");
    }
    public static void deleteData() throws SQLException{
        statement.executeUpdate("DELETE FROM students WHERE id < 23;");
    }
    private static void getData() throws SQLException{
        ResultSet resultSet = statement.executeQuery("SELECT id, name FROM students WHERE score > 50");
        while(resultSet.next()) System.out.println(resultSet.getInt(1) + " " + resultSet.getString("name"));
    }
    private static void preparedStatementData() throws SQLException{
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement("INSERT INTO students(name, score) VALUES (?,?);");
        for(int i = 0; i < 5000; i++){
            preparedStatement.setString(1, "Rick" + i);
            preparedStatement.setInt(2, (i*10));
            preparedStatement.executeUpdate();
        }
        connection.commit(); //завершение транзакции в БД
    }
    public static void connect() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:jcoreprol2.db");
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
