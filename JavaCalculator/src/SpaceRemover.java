public class SpaceRemover {

    String inputWithoutSpaces;
    char[] inputArray;
    char[] tempArray;
    int inputArrayLengthCount = 0;
    int tempArrayLengthCount = 0;



    protected String getMessageWithoutSpaces(String userMessage){
        inputWithoutSpaces = null;              // null added to use calculator one more
        inputArray = null;
        tempArray = null;
//        System.out.println("userMessage = " + userMessage);
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

        tempArray = new char[tempArrayLengthCount];

        inputArrayLengthCount = 0;
        tempArrayLengthCount = 0;

        while (inputArrayLengthCount < inputArray.length) {     //move figures>0 into new array
            if (arrayByteCodes[inputArrayLengthCount] != 32) {
                tempArray[tempArrayLengthCount] = inputArray[inputArrayLengthCount];
                tempArrayLengthCount++;
                inputArrayLengthCount++;

            } else if (arrayByteCodes[inputArrayLengthCount] == 32) {
                inputArrayLengthCount++;
            }
        }

        inputWithoutSpaces = new String(tempArray);
//        System.out.println("inputWithoutSpaces = " + inputWithoutSpaces);    // remove commenting slashes if you want to see input cleaned from spaces

        return inputWithoutSpaces;
    }
}
