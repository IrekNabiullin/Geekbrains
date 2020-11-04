//Задание 3.
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.Processor;
import java.sql.*;

public class MainTest {
    private static Connection connection;
    private static Statement statement;

    @Before

    public void connect() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:jcorepro6.db");
        connection.setAutoCommit(false); // убираем автоматический коммит
        connection.setSavepoint(); //создаем точау для отката
        statement = connection.createStatement();
    }

    @Test
    public void addEntry(){
        try{
            statement.execute("INSERT INTO students(studid, name, score) VALUES (100,100,1000);");
            Assert.assertEquals(getCount(),11);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void updateEntry() {
        try{
            System.out.println("Main test");
            statement.executeUpdate("UPDATE students SET score = '15000' WHERE name = 'name11';");
            ResultSet resultSet = statement.executeQuery("SELECT score FROM students WHERE name = 'name11';" );
            while(resultSet.next()) System.out.println(resultSet.getString("score"));
            Assert.assertEquals(resultSet.getString("score"),1500);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }


    @Test
    public void removeEntry() {
        try{
            statement.executeUpdate("DELETE FROM students WHERE score = '15000';");
            ResultSet resultSet = statement.executeQuery("SELECT score FROM students WHERE name = 'name11';" );
            Assert.assertEquals(getCount(),10);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }




    public static int getCount() throws SQLException{
        int maxID = 0;
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM students;");
        if (resultSet.next()) {
            maxID = resultSet.getInt(1);
        }
        return maxID;
    }


    public void conn(){

        try{
            connect();
            System.out.println("MaxID = " + getCount());

        }catch(SQLException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){

        }finally{
            disconnect();
        }
    }


    @After
    public void disconnect(){
        try{
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            connection.rollback();//откатываемся назад в точку сохранения
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