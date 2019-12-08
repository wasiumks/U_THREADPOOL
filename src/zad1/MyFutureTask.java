package zad1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MyFutureTask extends FutureTask<String> {
    private final Callable c;

    public MyFutureTask(Callable<String> callable) {
        super(callable);
        c = callable;
    }

    @Override
    protected void done() {
        System.out.println(c + " task skonczony");
    }
}
