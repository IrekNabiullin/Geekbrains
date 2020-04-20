public class OutputClass {

//    private double resultToOutput;
    private int resultToRomanOutput;

    public void outputToConsole(double resultToOutput, String typeOfOutput){
        if(typeOfOutput.equals("Roman")){
            resultToRomanOutput = (int)resultToOutput;
//            System.out.println("resultToRomanOutput = " + resultToRomanOutput);
            ArabicRomanConverter.arabicToRoman(resultToRomanOutput);
        } else if(typeOfOutput.equals("Arabic")) {
//            System.out.println(" Result = " + (int)resultToOutput);
            System.out.println((int)resultToOutput);
        }
    }
}
