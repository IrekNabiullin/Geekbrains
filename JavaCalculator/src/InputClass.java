
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Scanner;


class InputClass  {

    private String inputType;
    String userInput;
    String input;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));   // We use BufferedReader because it is more preferable than scanner

    private Scanner in;
    String netInput;

    String inputMessage;

    InputClass(String inputType){
        this.inputType = inputType;
    }

    protected String getInputMessage() {
        inputMessage = null;

        input = inputMsg(inputType);
        return input;
    }

    protected String inputMsg(String inputType){
        try {
            if (inputType.equals("Console")) {
                System.out.println();
                System.out.println("You have got the Best calculator ever! Let's try it. Enter \"exit\" to stop.");
                System.out.println("Please input figures from 1 till 10 and arithmetic operation (+-*/):");

                userInput = null;
                userInput = reader.readLine();
                inputMessage = userInput;


                //TODO. If we want to have input not only from console

            } else if (inputType.equals("InputStream")) {
                System.out.println("Not console input");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                if (in.hasNext()) {
                                    netInput = in.nextLine();
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }).start();

                inputMessage = netInput;
            }
        }catch (IOException e){
            System.out.println("Wrong input. Program finished.");
            System.exit(0);
        }
        return inputMessage;
    }
}
