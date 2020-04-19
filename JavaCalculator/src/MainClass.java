public class MainClass {
    public static void main(String[] args) {
        InputClass input = new InputClass(RulesClass.INPUT_TYPE);

        OperationClass calculation = new OperationClass(input.getInputMessage());
        OutputClass output = new OutputClass(calculation.getResult());
        output.outputToConsole(calculation.result);
    }
}
