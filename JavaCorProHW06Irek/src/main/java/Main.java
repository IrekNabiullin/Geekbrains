/*
 * Java Core Pro. Home work 6. Тестирование с использованием JUnit
 *
 * @author Irek Nabiullin
 * @version dated Apr 23, 2018
 * @link https://github.com/IrekNabiullin
 *
 *  Задание:
 *  Написать метод, которому в качестве аргумента передается не пустой одномерный
 *  целочисленный массив. Метод должен вернуть новый массив, который получен путем
 *  вытаскивания из исходного массива элементов, идущих после последней четверки. Входной
 *  массив должен содержать хотя бы одну четверку, иначе в методе необходимо выбросить
 *  RuntimeException. Написать набор тестов для этого метода (по 3-4 варианта входных данных).
 *  Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
 *  2. Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной
 *  четверки или единицы, то метод вернет​false​; Написать набор тестов для этого метода (по 3-4
 *  варианта входных данных). 3. Создать небольшую базу данных. Таблица ​«Студенты» с полями ​id, фамилия, балл​).
 *  Написать тесты для проверки корректности добавления, обновления и чтения записей.
 *  Следует учесть, что в базе есть заранее добавленные записи, и после проведения тестов эти
 *  они не должны быть удалены, изменены или добавлены вновь. *
 */


//Задание 3.

import java.sql.*;

public class Main {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    public static void main(String[] args) {

        try{
            connect();
            createTable();                  // Формируем таблицу студентов
            clearTable();
            preparedStatementBatchData();   // и заполняем ее 10 записями
            disconnect();
         }catch(SQLException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){

        }finally{
            disconnect();
        }
    }

    public static void createTable() throws SQLException{
        statement.execute("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY AUTOINCREMENT, studid INTEGER, name TEXT, score INTEGER);");
    }


    public static void clearTable() throws SQLException{
        statement.execute("DELETE FROM students;");
    }


    public static void preparedStatementBatchData() throws SQLException {
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement("INSERT INTO students(studid, name, score) VALUES (?,?,?);");
        int kMin = 1;
        int kMax = 10;
        for (int i = kMin; i <= kMax; i++) {
            preparedStatement.setInt(1, (i));
            preparedStatement.setString(2, "name" + i);
            preparedStatement.setInt(3, (i * 10));
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        connection.commit(); //завершаем тразакцию в БД
    }


    public static void connect() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:jcorepro6.db");
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
