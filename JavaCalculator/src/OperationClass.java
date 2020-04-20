import java.util.ArrayList;


class OperationClass {

    OperationClass(String typeOfInput, int minRange, int maxRange){
        this.typeOfInput = typeOfInput;
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    private String typeOfInput;
    protected int minRange;
    protected int maxRange;

    private static String[] tokens;
    private static ArrayList<String> figures;
    private String inputMessage;
    private String inputMessageWithoutSpaces;
    private String inputType;
    private ArrayList<String> inputFigures;
    private double[] arabicNumerals;
    private double[] romanNumerals;
    private double[] figuresToCalculate;
    private double tempFigure;
    private int counterArabicType = 0;
    private int counterRomanType = 0;
    double result;
    int arabicLength = 0;
    int romanLength = 0;
    int countArabic = 0;
    int countRoman = 0;
    int countResult = 0;


    public void startCalculation(){

        while(true){
            InputClass input = new InputClass(typeOfInput);
            inputMessage = input.getInputMessage();
            calculation(inputMessage);
        }

    }

    private void calculation(String inputMessage){
        findFigures(inputMessage);
        transformFigures(figures);
        arithmeticCalculations(figuresToCalculate);
        variablesReset();
    }

    private void variablesReset(){          // reset added to use calculator one more
        tokens = null;   //
        figures = null;   //
        inputMessage = null;
        inputMessageWithoutSpaces = null;
        inputType = null;  //
        inputFigures = null;
        arabicNumerals = null;
        romanNumerals = null;
        figuresToCalculate = null;
        tempFigure = 0.0;
        counterArabicType = 0;
        counterRomanType = 0;
        result = 0.0;
        arabicLength = 0;
        romanLength = 0;
        countArabic = 0;
        countRoman = 0;
        countResult = 0;
    }


    protected ArrayList<String> findFigures(String userInput){       // Split input string to tokens divided by Math operation chars
        SpaceRemover tester = new SpaceRemover();
//        System.out.println("userInput " + userInput);
        inputMessageWithoutSpaces = tester.getMessageWithoutSpaces(userInput);

        figures = new ArrayList<>();
        tokens = inputMessageWithoutSpaces.split("[- +*/]");

        for (int i=0; i<tokens.length; i++) {
//            System.out.println("token " + i + " = " + tokens[i]);  // remove commenting slashes if you want to see every token
            figures.add(tokens[i]);
        }
        return figures;
    }

    private double[] transformFigures(ArrayList<String> inputFigures) {

        arabicNumerals = new double[inputFigures.size()];               //counter for arabic figures in input message
        romanNumerals = new double[inputFigures.size()];               //counter for roman figures in input message

        for (int i = 0; i < inputFigures.size(); i++) {
            try {
                tempFigure = Integer.parseInt(inputFigures.get(i));
                if (tempFigure >= minRange && tempFigure <= maxRange) {  //Compare with range condition. [1-10] by Default. May be changed in MainClass
                    arabicNumerals[i] = Double.parseDouble(inputFigures.get(i));
                    counterArabicType++;
                } else {
                    System.out.println("Wrong input. Program finished.");
                    System.exit(0);
                }
            } catch (NumberFormatException e) {
                if (inputFigures.get(i).equals("I") ||
                        inputFigures.get(i).equals("II") ||
                        inputFigures.get(i).equals("III") ||
                        inputFigures.get(i).equals("IV") ||
                        inputFigures.get(i).equals("V") ||
                        inputFigures.get(i).equals("VI") ||
                        inputFigures.get(i).equals("VII") ||
                        inputFigures.get(i).equals("VIII") ||
                        inputFigures.get(i).equals("IX") ||
                        inputFigures.get(i).equals("X")) {      //TODO. Fix range problem for Roman figures. If we want to change input range and calculate XI for example

                    counterRomanType++;
                } else {
                    System.out.println("Wrong input. Program finished.");
                    System.exit(0);
                }
            }

            switch (inputFigures.get(i)) {      //convert roman characters into standard figures
                case "I":
                    romanNumerals[i] = 1.0;     // we use double type to correct calculations for divide operation
                    counterRomanType++;
                    break;
                case "II":
                    romanNumerals[i] = 2.0;
                    counterRomanType++;
                    break;
                case "III":
                    romanNumerals[i] = 3.0;
                    counterRomanType++;
                    break;
                case "IV":
                    romanNumerals[i] = 4.0;
                    counterRomanType++;
                    break;
                case "V":
                    romanNumerals[i] = 5.0;
                    counterRomanType++;
                    break;
                case "VI":
                    romanNumerals[i] = 6.0;
                    counterRomanType++;
                    break;
                case "VII":
                    romanNumerals[i] = 7.0;
                    counterRomanType++;
                    break;
                case "VIII":
                    romanNumerals[i] = 8.0;
                    counterRomanType++;
                    break;
                case "IX":
                    romanNumerals[i] = 9.0;
                    counterRomanType++;
                    break;
                case "X":
                    romanNumerals[i] = 10.0;
                    counterRomanType++;
                    break;
            }
//            System.out.println("Roman figure " + i + " = " + romanNumerals[i]);  // remove commenting slashes if you want to see every figure
        }

        for (double arabic : arabicNumerals     //to exclude spaces in input
        ) {
            if (arabic > 0.0) {
                arabicLength++;                 //calculate array length without spaces
            }
        }

        for (double roman : romanNumerals       //to exclude spaces in input
        ) {
            if (roman > 0.0) {
                romanLength++;                  //calculate array length without spaces
            }
        }


        if (arabicLength > 0) {                                 //work with input in case of arabic figures
            countArabic = 0;
            figuresToCalculate = new double[arabicLength];

            while (countArabic < arabicLength) {
                if (arabicNumerals[countArabic] != 0.0) {       // move not zero figures into new array
                    figuresToCalculate[countResult] = arabicNumerals[countArabic];
                    countResult++;
                    countArabic++;
                } else if (arabicNumerals[countArabic] == 0.0) {
                    countArabic++;
                }
            }
        } else if (romanLength > 0) {                           //work with input in case of roman figures
            countRoman = 0;
            figuresToCalculate = new double[romanLength];
            while (countRoman < romanLength) {
                if (romanNumerals[countRoman] != 0.0) {
                    figuresToCalculate[countResult] = romanNumerals[countRoman];
                    countResult++;
                    countRoman++;
                } else if (romanNumerals[countRoman] == 0.0) {
                    countRoman++;
                }
            }
        }

        if (counterArabicType > 0 && counterRomanType == 0) {  //check which type of figures were entered
            inputType = "Arabic";
        } else if (counterArabicType == 0 && counterRomanType > 0) {
            inputType = "Roman";
        } else {
            throw new NumberFormatException("Different formats of input figures! Program finished.");
        }
        return figuresToCalculate;
    }




    private void arithmeticCalculations(double[] figuresToCalculate) {


        FindOperators operators = new FindOperators();                  //receive arithmetic operators from input
        char[] arr = operators.getOperators(inputMessageWithoutSpaces);

        result = figuresToCalculate[0];
        try {
            for (int i = 0; i < arr.length; i++) {
                switch (arr[i]) {                                       //make arithmetic operation depending on received operator
                    case 43:
                        result = result + figuresToCalculate[i + 1];
                        break;
                    case 45:
                        result = result - figuresToCalculate[i + 1];
                        break;
                    case 42:
                        result = result * figuresToCalculate[i + 1];
                        break;
                    case 47:
                        result = result / figuresToCalculate[i + 1];
//                    System.out.println("RESULT = " + result);         // remove commenting slashes if you want to see correct result in double format
                        break;
                }
            }

            OutputClass output = new OutputClass();             // Use special Class for output our result
            output.outputToConsole(result, inputType);          // if we want to change output channel< not only console
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong input. Try again!");
        }
    }

}
