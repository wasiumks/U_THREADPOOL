package zad1;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.concurrent.*;

public class Controller {
    private static int count = 0;
    ExecutorService executorService = Executors.newCachedThreadPool();
    FutureTask<String> returnedValues;
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
        executorService.shutdownNow();

        for (int i = 0; i < count; i++) {
            box.getChildren().get(i).setDisable(true);
        }

    }

    public void addThread(ActionEvent actionEvent) {
        Button button = new Button("T" + ++count);
        button.setId(String.valueOf(count));
        button.setOnAction(new EventHandler<ActionEvent>() {
            private boolean isStarted = false;
            private boolean isSuspended = false;
            private boolean first = true;
            final MyCallable call = new MyCallable(button, text, executorService);
            @Override
            public void handle(ActionEvent event) {
                if (first) {
                    //start, zaczynamy losowanie i sumowanie liczb
                    text.setText(text.getText() + "\nThread " + button.getId() + ": Start!");
                    button.setText("Suspend T" + button.getId());
                    isStarted = true;
                    first = false;
                    returnedValues = new MyFutureTask(call);
                    executorService.submit(returnedValues);
                } else if (isStarted) {
                    //suspend - wstrzymanie watku wait/notify
                    text.setText(text.getText() + "\nThread " + button.getId() + ": Suspended!");
                    button.setText("Continue T" + button.getId());
                    isStarted = false;
                    isSuspended = true;
                    call.getWait()[0] = true;
                    synchronized (call) {
                        call.notify();
                    }
                } else if (isSuspended) {
                    //continue - wznowienie tego samego watku! wait/notify
                    text.setText(text.getText() + "\nThread " + button.getId() + ": Continue!");
                    button.setText("Suspend T" + button.getId());
                    isStarted = true;
                    isSuspended = false;
                    call.getWait()[0] = false;
                    synchronized (call) {
                        call.notify();
                    }
                }
            }
        });
        box.getChildren().add(button);
    }

    public static int getCount() {
        return count;
    }
}
