import java.util.ArrayList;
import java.util.Arrays;


class OperationClass {

    OperationClass(String inputMessage){
        this.inputMessage = inputMessage;
    }

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


    public void calculation(){
        findFigures(inputMessage);
        transformFigures(figures);
        arithmeticCalculations(figuresToCalculate);
    }

    protected ArrayList<String> findFigures(String userInput){       // Split input string to tokens devided by Math operation chars
        SpaceRemover tester = new SpaceRemover(userInput);
        inputMessageWithoutSpaces = tester.getMessageWithoutSpaces(userInput);
        figures = new ArrayList<>();
//        System.out.println("inputMessageWithoutSpaces = " + inputMessageWithoutSpaces);
        tokens = inputMessageWithoutSpaces.split("[- +*/]");
//        System.out.println("tokens.length = " + tokens.length);
//        System.out.println(Arrays.toString(tokens));
        for (int i=0; i<tokens.length; i++) {
            System.out.println("token " + i + " = " + tokens[i]);
            figures.add(tokens[i]);
        }
        return figures;
    }

    private double[] transformFigures(ArrayList<String> inputFigures) {
        arabicNumerals = new double[inputFigures.size()];               //counter for arabic figures in input message
        romanNumerals = new double[inputFigures.size()];               //counter for roman figures in input message

//        System.out.println("inputFigures.size = " + inputFigures.size());
        for (int i = 0; i < inputFigures.size(); i++) {
            try {
                tempFigure = Integer.parseInt(inputFigures.get(i));
                if (tempFigure > 0 && tempFigure <= 10) {
                    arabicNumerals[i] = Double.parseDouble(inputFigures.get(i));
                    counterArabicType++;
                    System.out.println("Arabic figure " + i + " = " + arabicNumerals[i]);
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
                        inputFigures.get(i).equals("X")) {

                    counterRomanType++;
                } else {
                    System.out.println("Wrong input. Program finished.");
                    System.exit(0);
                }
            }

//            System.out.println("tempFigure = " + tempFigure);


            switch (inputFigures.get(i)) {
                case "I":
                    romanNumerals[i] = 1.0;
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
            System.out.println("Roman figure " + i + " = " + romanNumerals[i]);
        }

        for (double arabic : arabicNumerals
        ) {
            if (arabic > 0.0) {
                arabicLength++;
            }
        }
        System.out.println("arabicLength = " + arabicLength);

        for (double roman : romanNumerals
        ) {
            if (roman > 0.0) {
                romanLength++;
            }
        }
        System.out.println("romanLength = " + romanLength);

        if (arabicLength > 0) {
            countArabic = 0;
            figuresToCalculate = new double[arabicLength];
            while (countArabic < arabicLength) {
                if (arabicNumerals[countArabic] != 0.0) {
                    figuresToCalculate[countResult] = arabicNumerals[countArabic];
                    countResult++;
                    countArabic++;
                } else if (arabicNumerals[countArabic] == 0.0) {
                    countArabic++;
                }
            }
        } else if (romanLength > 0) {
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

        if (counterArabicType > 0 && counterRomanType == 0) {
            inputType = "Arabic";
//            System.out.println("inputType = " + inputType);
        } else if (counterArabicType == 0 && counterRomanType > 0) {
            inputType = "Roman";
//            System.out.println("inputType = " + inputType);
        } else {
            throw new NumberFormatException("Different formats of input figures! Program finished.");
        }
        return figuresToCalculate;
    }




    private void arithmeticCalculations(double[] figuresToCalculate) {
        FindOperators operators = new FindOperators(inputMessageWithoutSpaces);
        char[] arr = operators.getOperators(inputMessageWithoutSpaces);

        result = figuresToCalculate[0];
        for (int i=0; i<arr.length; i++) {
            switch (arr[i]) {
                case 43:
                    result = result + figuresToCalculate[i + 1];
                    System.out.println("RESULT = " + result);
                    break;
                case 45:
                    result = result - figuresToCalculate[i + 1];
                    System.out.println("RESULT = " + result);
                    break;
                case 42:
                    result = result * figuresToCalculate[i + 1];
                    System.out.println("RESULT = " + result);
                    break;
                case 47:
                    result = result / figuresToCalculate[i + 1];
                    System.out.println("RESULT = " + result);
                    break;
            }
        }

//        return result;

//        OutputClass output = new OutputClass(result, inputType);
        OutputClass output = new OutputClass();
        output.outputToConsole(result, inputType);
    }

}
