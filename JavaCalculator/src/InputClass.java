import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;
import java.util.stream.IntStream;

class InputClass {

    private String inputType;
    private int numberOfInputObjects;
    private static String[] tokens;
    private static ArrayList<String> figures;


    InputClass(String inputType, int numberOfInputObjects){
        this.inputType = inputType;
        this.numberOfInputObjects = numberOfInputObjects;
    }

    protected String inputFromConsole() throws Exception {
        System.out.println("Please input arithmetic operation with two figures from 0 till 10:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));   // We use BufferedReader because it is more preferable then scanner
        String userInput = reader.readLine();

        return userInput;
    }

    protected ArrayList<String> findOperation(String userInput){

        ArrayList<String> operationCodes = new ArrayList<>();

        char[] arr = userInput.toCharArray();
        for(int i = 0; i<arr.length; i++) {
            if ((byte) arr[i] == 43) {
                operationCodes.add("PLUS");           // in case of +
            } else if ((byte) arr[i] == 45) {
                operationCodes.add("MINUS");           // in case of -
            } else if ((byte) arr[i] == 42) {
                operationCodes.add("MULTIPLY");           // in case of *
            } else if ((byte) arr[i] == 47) {
                operationCodes.add("PLUS");          // in case of /
            } else {
                operationCodes.add("NONE");            // in case of error
            }
        }

        return operationCodes;
        }

        protected ArrayList<String> findFigures(String userInput){
        figures = new ArrayList<>();
//        tokens = userInput.split("\\s +-/*");                                          // Split input string to tokens devided by Math operation chars
          tokens = userInput.split("[- +*/]");
            System.out.println("tokens.length = " + tokens.length);
            System.out.println(Arrays.toString(tokens));
            for (int i=0; i<tokens.length; i++) {
                System.out.println("token " + i + " = " + tokens[i]);
            }
        return figures;
    }

    /*
    public static ArrayList<Double> inputAndPrepareFigures() throws Exception{
        findFigures(inputFromConsole());
        figures.add(findOperation(inputFromConsole()));
        return figures;
    }
     */
}
