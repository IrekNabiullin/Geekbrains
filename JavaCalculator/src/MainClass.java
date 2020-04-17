public class MainClass {
    public static void main(String[] args) throws Exception {
        InputClass input = new InputClass(RulesClass.INPUT_TYPE, RulesClass.NUMBER_OF_INPUT_OBJECTS);
        OperationClass calculation = new OperationClass(InputClass.findFigures(InputClass.inputFromConsole()), InputClass.findOperation(InputClass.inputFromConsole()));
        OutputClass output = new OutputClass(calculation.result);
    }
}
