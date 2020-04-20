public class MainClass {


    public static void main(String[] args) {
        String typeOfInput  = "Console";
        int rangeMin = 1;
        int rangeMax = 10;

        OperationClass calculation = new OperationClass(typeOfInput, rangeMin, rangeMax);
        calculation.startCalculation ();

    }
}
