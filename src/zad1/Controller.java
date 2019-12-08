package zad1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


//obsluga przyciskow
public class Controller {

    private static int count = 0;

    @FXML
    Button stopButton;
    @FXML
    Button addThread;

    @FXML
    Text text;

    @FXML
    HBox box;

    public void stop(ActionEvent actionEvent) {
        //natychmiastowe zakończenie działania wszystkich watkow, napisy ich przycisków na "T ... done!" (wszystkie obecne przyciski staną się wtedy niedostępne).
        text.setText(text.getText() + "\nSTOP!");
        stopButton.setDisable(true);
        addThread.setDisable(true);

        for (int i = 0; i < count; i++) {
            box.getChildren().get(i).setDisable(true);
        }

    }

    public void addThread(ActionEvent actionEvent) {
        //add button
        Button button = new Button("T" + ++count);
        button.setId(String.valueOf(count));
        button.setOnAction(new EventHandler<ActionEvent>() {
            private boolean isStarted = false;
            private boolean isSuspended = false;
            private boolean isDone = false;
            private boolean first = true;

            @Override
            public void handle(ActionEvent event) {
                //TODO po kliknieciu + C stopuje watek i blokuje przycisk bez usuwania

                //threadpool - przetwarza a zwraca tu do wypisania?
                if (first) {
                    //start, zaczynamy losowanie i sumowanie liczb
                    System.out.println("start");
                    text.setText(text.getText() + "\nThread " + button.getId() + ": Start!");
                    button.setText("Suspend T" + button.getId());
                    isStarted = true;
                    first = false;
                } else if (isDone) {
                    //done - jak suma przekroczy wartosc to wylaczyc button i po 2 sekundach go ukryc
                    text.setText(text.getText() + "\nThread " + button.getId() + ": Done!");
                    button.setText("Done T" + button.getId());
                    button.setDisable(true);
                    isStarted = false;
                    isSuspended = false;
                } else if (isStarted) {
                    //suspend - wstrzymanie watku wait/notify
                    System.out.println("suspend");
                    text.setText(text.getText() + "\nThread " + button.getId() + ": Suspended!");
                    button.setText("Continue T" + button.getId());
                    isStarted = false;
                    isSuspended = true;
                } else if (isSuspended) {
                    //continue - wznowienie tego samego watku! wait/notify
                    text.setText(text.getText() + "\nThread " + button.getId() + ": Continue!");
                    button.setText("Suspend T" + button.getId());
                    isStarted = true;
                    isSuspended = false;
                }
            }
        });
        box.getChildren().add(button);
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Controller.count = count;
    }

    public Button getStopButton() {
        return stopButton;
    }

    public void setStopButton(Button stopButton) {
        this.stopButton = stopButton;
    }

    public Button getAddThread() {
        return addThread;
    }

    public void setAddThread(Button addThread) {
        this.addThread = addThread;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public HBox getBox() {
        return box;
    }

    public void setBox(HBox box) {
        this.box = box;
    }
}
