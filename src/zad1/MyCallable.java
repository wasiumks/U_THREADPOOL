package zad1;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class MyCallable implements Callable<String> {

    Button button;
    Text text;
    ExecutorService executorService;

    public MyCallable(Button button, Text text, ExecutorService executorService) {
        this.button = button;
        this.text = text;
        this.executorService = executorService;
    }

    boolean[] wait = {false};

    @Override
    public String call() {
        int sum = 0;
        int end = (1000 * Integer.parseInt(button.getId()));
        while (sum < end && !Thread.interrupted()) {
            try {
                if (wait[0]) synchronized (this) {
                    this.wait();
                }
                sum += (int) (Math.random() * 100);
                text.setText(text.getText() + "\nThread" + button.getId() + ": " + sum);
                Thread.sleep((int) (Math.random() * 1000));
            } catch (Exception e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        if (sum >= 1000 * Integer.parseInt(button.getId())) {
            text.setText(text.getText() + "\nThread " + button.getId() + ": Done!");
            button.setText("Done T" + button.getId());
            button.setDisable(true);
            executorService.shutdown();
            return "Thread " + Controller.getCount() + " is done";
        } else {
            return "Thread " + Controller.getCount() + " is cancelled";
        }
    }

    public boolean[] getWait() {
        return wait;
    }
}
