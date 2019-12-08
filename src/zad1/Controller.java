package zad1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;


//obsluga przyciskow
public class Controller {

    @FXML
    Button stopButton;
    @FXML
    Button addThread;

    @FXML
    Text text;

    public void stop(ActionEvent actionEvent) {
        text.setText(text.getText() + "\nSTOP!");
        stopButton.setDisable(true);
        addThread.setDisable(true);

    }

    public void addThread(ActionEvent actionEvent) {

    }
}
