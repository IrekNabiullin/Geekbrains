public class OutputClass {
    /**
     * This class for output. We may add methods to do any kind of ouput. Not only to console.
     */
    private double resultToOutput;

    OutputClass(double resultToOutput){
        this.resultToOutput = resultToOutput;
    }

    public void outputToConsole(double resultToOutput){
        System.out.println(" Result = " + resultToOutput);
    }
}
