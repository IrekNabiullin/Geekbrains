package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;


public class Controller {
    @FXML
    TextField mainTextField;

    @FXML
    TextArea textArea;

    @FXML
    Button exitHBox;

    @FXML
    Button btnAddText;

    public void firstOfThreeButtonClick(ActionEvent actionEvent) { //при нажатии на кнопку Clear
        mainTextField.setText(""); //очищаем окно ввода
        mainTextField.requestFocus();
        mainTextField.selectEnd();
    }

    public void oneOfThreeButtonClick(ActionEvent actionEvent) {
        mainTextField.appendText(" " + ((Button)actionEvent.getSource()).getText());
        mainTextField.requestFocus();
        mainTextField.selectEnd();
    }

    public void addTextToArea(ActionEvent actionEvent) {
        textArea.appendText("YOU: " + mainTextField.getText() + "\n");
        mainTextField.setText("");
    }

    public void exitWindow(ActionEvent actionEvent) {
        textArea.appendText("YOU: GOOD BYE!\n");
        mainTextField.setText("");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(1);
    }
}
