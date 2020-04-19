import java.util.Arrays;

public class FindOperators {
    String inputWithoutSpaces;
    char[] operators;
    char[] tempArray;
    int outputArrayLengthCount;

    public FindOperators(String inputWithoutSpaces){
        this.inputWithoutSpaces = inputWithoutSpaces;
    }

    protected char[] getOperators(String inputWithoutSpaces){
        operators = findOperators(inputWithoutSpaces);
        return operators;
    }

    private char[] findOperators(String inputWithoutSpaces) {
        tempArray = inputWithoutSpaces.toCharArray();
        byte[] arrayByteCodes = inputWithoutSpaces.getBytes();


        System.out.println(Arrays.toString(arrayByteCodes));

        outputArrayLengthCount = 0;
        for (int i = 0; i < inputWithoutSpaces.length(); i++) {
            if(arrayByteCodes[i] == 43 || arrayByteCodes[i] == 45 || arrayByteCodes[i] == 42 || arrayByteCodes[i] == 47){
                outputArrayLengthCount++;
            }
        }
        operators = new char[outputArrayLengthCount];

       outputArrayLengthCount = 0;
        for (int i = 0; i < inputWithoutSpaces.length(); i++) {
            switch (arrayByteCodes[i]) {
                case 43:
                    operators[outputArrayLengthCount] = 43;  // byte code for "+" operator
                    outputArrayLengthCount++;
                    System.out.println("Operator is -");
                    break;
                case 45:
                    operators[outputArrayLengthCount] = 45;   // byte code for "-" operator
                    outputArrayLengthCount++;
                    System.out.println("Operator is +");
                    break;
                case 42:
                    operators[outputArrayLengthCount] = 42;   // byte code for "*" operator
                    outputArrayLengthCount++;
                    System.out.println("Operator is *");
                    break;
                case 47:
                    operators[outputArrayLengthCount] = 47;    // byte code for "/" operator
                    outputArrayLengthCount++;
                    System.out.println("Operator is /");
                    break;
            }
        }
        return operators;
    }
}

