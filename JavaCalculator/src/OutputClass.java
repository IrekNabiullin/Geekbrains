public class OutputClass {

    private double resultToOutput;
    private int resultToRomanOutput;

//    OutputClass(){
//        OutputClass(double resultToOutput, String typeOfOutput){
//        this.resultToOutput = resultToOutput;
//    }

    public void outputToConsole(double resultToOutput, String typeOfOutput){
        if(typeOfOutput.equals("Roman")){
            resultToRomanOutput = (int)resultToOutput;
            System.out.println("resultToRomanOutput = " + resultToRomanOutput);
            RomanArabicConverter.arabicToRoman(resultToRomanOutput);
        } else if(typeOfOutput.equals("Arabic")) {
            System.out.println(" Result = " + (int)resultToOutput);
        }
    }
}
