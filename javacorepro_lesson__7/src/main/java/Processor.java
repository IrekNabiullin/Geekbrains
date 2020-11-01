import java.sql.SQLException;
import java.sql.Statement;

/**
 * reflection example
 */
public class Processor {
    public static int[] arrayAfter4(int[] array){
        for(int i = array.length-1; i >= 0 ; i--){
            if(array[i] == 4){
                int[] out = new int[array.length-i-1];
                System.arraycopy(array, i+1, out, 0, out.length);
                return out;
            }
        }
        throw new RuntimeException();
    }
    public static boolean only1and4(int[] array){
        boolean has1 = false;
        boolean has4 = false;
        for(int i = 0; i < array.length; i++){
            if(array[i] == 1) has1 = true;
            if(array[i] == 4) has4 = true;
            if(array[i] !=1 && array[i] !=4) return false;
        }
        return has1 && has4;
    }
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
