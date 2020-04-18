import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

    static String inputFromConsole() throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));   // We use BufferedReader because it is more preferable then scanner
        String userInput = reader.readLine();

        return userInput;
    }

    static ArrayList<String> findOperation(String userInput){

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

        static ArrayList<String> findFigures(String userInput){
        figures = new ArrayList<>();
        tokens = userInput.split(" +-/*");                                          // Split input string to tokens devided by Math operation chars
            IntStream.range(0, userInput.length()).forEach(i -> {
                System.out.println(tokens[i]);
                figures.add(tokens[i]);
            });
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
