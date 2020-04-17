import java.util.ArrayList;
import java.util.Timer;

class OperationClass {

    OperationClass(ArrayList<String> inputFigures, ArrayList<String> operators){
        this.inputFigures = inputFigures;
        this.operators = operators;
    }
 /*
    public enum OperationType {
        PLUS, MINUS, MULTIPLY, DIVIDE;
    }

  */

 //   OperationType type;
 private ArrayList<String> inputFigures;
    private ArrayList<String> operators;
    private double[] arabicNumerals;
    private double[] romanNumerals;
    private double[] figuresToCalculate;
    private double tempFigure;
    private int allTheSameArabicType = 0;
    private int allTheSameRomanType = 0;
    double result;

    private double[] transformFigures(ArrayList<String> inputFigures){
        TypeTester tester = new TypeTester();
        arabicNumerals = new double[inputFigures.size()];
        romanNumerals = new double[inputFigures.size()];
        figuresToCalculate = new double[inputFigures.size()];

        for(int i=0; i<inputFigures.size(); i++) {
            if (tester.testType(inputFigures.get(i)).equals("int")) {
                tempFigure = Double.parseDouble(inputFigures.get(i));
                if (tempFigure>=0 && tempFigure<=10) {
                    arabicNumerals[i] = Double.parseDouble(inputFigures.get(i));
                    allTheSameArabicType++;
                    System.out.println("Arabic figure " + i + " = " + arabicNumerals[i]);
                }

            } else if (tester.testType(inputFigures.get(i)).equals("String") || tester.testType(inputFigures.get(i)).equals("char")) {
                switch (inputFigures.get(i)) {
                    case "I":
                        romanNumerals[i] = 1.0;
                        allTheSameRomanType++;
                        break;
                    case "II":
                        romanNumerals[i] = 2.0;
                        allTheSameRomanType++;
                        break;
                    case "III":
                        romanNumerals[i] = 3.0;
                        allTheSameRomanType++;
                        break;
                    case "IV":
                        romanNumerals[i] = 4.0;
                        allTheSameRomanType++;
                        break;
                    case "V":
                        romanNumerals[i] = 5.0;
                        allTheSameRomanType++;
                        break;
                    case "VI":
                        romanNumerals[i] = 6.0;
                        allTheSameRomanType++;
                        break;
                    case "VII":
                        romanNumerals[i] = 7.0;
                        allTheSameRomanType++;
                        break;
                    case "VIII":
                        romanNumerals[i] = 8.0;
                        allTheSameRomanType++;
                        break;
                    case "IX":
                        romanNumerals[i] = 9.0;
                        allTheSameRomanType++;
                        break;
                    case "X":
                        romanNumerals[i] = 10.0;
                        allTheSameRomanType++;
                        break;
                }
                System.out.println("Roman figure " + i + " = " + romanNumerals[i]);
            }
        }
        if (allTheSameArabicType != inputFigures.size()  && allTheSameRomanType != inputFigures.size()) {
            throw new NumberFormatException("Different formats of input figures!");
        } else if(allTheSameArabicType == inputFigures.size()){
            System.arraycopy(arabicNumerals, 0, figuresToCalculate, 0, inputFigures.size());
        } else if (allTheSameRomanType == inputFigures.size()){
            for (int i=0; i<inputFigures.size(); i++){
                figuresToCalculate[i] = arabicNumerals[i];
            }
        }
       return figuresToCalculate;
    }

    private double calculation(double[] figuresToCalculate, ArrayList<String> operators ) {
        result = figuresToCalculate[0];

        for (int i=1; i<operators.size(); i++) {
            switch (operators.get(i)) {
                case "+":
                    result = result + figuresToCalculate[i + 1];
                    break;
                case "-":
                    result = result - figuresToCalculate[i + 1];
                    break;
                case "*":
                    result = result * figuresToCalculate[i + 1];
                    break;
                case "/":
                    result = result / figuresToCalculate[i + 1];
                    break;
            }
        }

        return result;
    }
}
