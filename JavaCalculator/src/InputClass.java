
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Scanner;


class InputClass {

    private String inputType;


    String userInput;

    private Scanner in;
    String netInput;

    String inputMessage;

    InputClass(String inputType){
        this.inputType = inputType;
    }

    protected String getInputMessage() {

        String input = inputMsg(inputType);
        return input;
    }

//    protected String input(String inputType) throws Exception {
    protected String inputMsg(String inputType){
        try {
            if (inputType.equals("Console")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));   // We use BufferedReader because it is more preferable then scanner
                System.out.println("Please input arithmetic operation with two figures from 0 till 10:");
                userInput = reader.readLine();
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                inputMessage = userInput;

            } else if (inputType.equals("InputStream")) {  //TO DO. Write bufferedInputStream
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

        }
    return inputMessage;
    }
}
