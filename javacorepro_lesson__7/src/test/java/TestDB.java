import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDB {
    public static Connection connection;
    public static Statement statement;

    @Before
    public void connect() throws Exception{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:students.db");
        connection.setAutoCommit(false);
        connection.setSavepoint();
        statement = connection.createStatement();
    }
    @After
    public void disconnect(){
        try{
            statement.close();
            connection.rollback();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Test
    public void testDelete(){
        Assert.assertEquals(true, Processor.checkDB(statement, "DELETE FROM students WHERE id < 25;"));
    }
    @Test
    public void testInsert(){
        Assert.assertEquals(true, Processor.checkDB(statement, "INSERT INTO students(name, score) VALUES ('Morty', 45);"));
    }
    @Test
    public void testUpdate(){
        Assert.assertEquals(true, Processor.checkDB(statement, "UPDATE students set score = 66 WHERE name = 'Rick';"));
    }



}
