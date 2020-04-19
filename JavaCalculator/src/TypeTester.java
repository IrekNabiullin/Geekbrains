public class TypeTester {
    String userInput;
    String inputWithoutSpaces;
    char[] inputArray;
    char[] tempArray;
    int inputArrayLengthCount = 0;
    int tempArrayLengthCount = 0;

    public TypeTester(String userInput) {
        this.userInput = userInput;
    }

    protected String getMessageWithoutSpaces(String userMessage){
        inputWithoutSpaces = removeSpaces(userMessage);
        return inputWithoutSpaces;
    }
    private String removeSpaces(String userInput) {
        inputArray = userInput.toCharArray();
        byte[] arrayByteCodes = userInput.getBytes();
        inputArrayLengthCount = inputArray.length;

        for (char chars : inputArray
        ) {
            if (chars != 32) {  // 32 is byte code (ASCII) for Space
                tempArrayLengthCount++;
            }
        }

        System.out.println("temp char array length  = " + tempArrayLengthCount);
        tempArray = new char[tempArrayLengthCount];

        inputArrayLengthCount = 0;
        tempArrayLengthCount = 0;
        System.out.println("inputArray.length = " + inputArray.length);
        System.out.println("inputArrayLengthCount = " + inputArrayLengthCount);

        while (inputArrayLengthCount < inputArray.length) {
            if (arrayByteCodes[inputArrayLengthCount] != 32) {
                tempArray[tempArrayLengthCount] = inputArray[inputArrayLengthCount];
                System.out.println("tempArray" + tempArrayLengthCount + " = " + tempArray[tempArrayLengthCount]);
                System.out.println("inputArrayLengthCount = " + inputArrayLengthCount);
                tempArrayLengthCount++;
                inputArrayLengthCount++;

            } else if (arrayByteCodes[inputArrayLengthCount] == 32) {
                inputArrayLengthCount++;
            }
        }

        System.out.println(tempArray.toString());
        inputWithoutSpaces = new String(tempArray);
        System.out.println("inputWithoutSpaces = " + inputWithoutSpaces);

        return inputWithoutSpaces;
    }
}
