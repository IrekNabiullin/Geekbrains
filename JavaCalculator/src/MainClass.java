public class MainClass {
    public static void main(String[] args) {
        InputClass input = new InputClass(RulesClass.INPUT_TYPE);

        OperationClass calculation = new OperationClass(input.getInputMessage());
        calculation.calculation();
//        OutputClass output = new OutputClass(calculation.getResult());
//        output.outputToConsole(calculation.result);
    }
}
