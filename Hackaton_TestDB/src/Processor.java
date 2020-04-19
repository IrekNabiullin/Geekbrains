import java.sql.SQLException;
import java.sql.Statement;

public class Processor {
    public static boolean checkDB(Statement statement, String string){
        int i = 0;
        try{
            i = statement.executeUpdate(string);
        }catch(SQLException e){
            return false;
        }
        return i != 0;
    }

}

