package zad1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPool implements Callable<String> {


    //Działanie każdego wątku polega na wylosowywaniu liczb naturalnych z podanego przedziału (np. [1, 100]) do momentu, kiedy ich suma przekroczy podany limi
    //watek 1 suma - 1000, 2 - 2000 itd.
    //Przetwarzanie tymczasowych wyników (oraz odbiór wyników końcowych) w trakcie działania wątków powinno odbywać się wyłącznie w obsłudze tych wątków/zadań.

    @Override
    public String call() {
        System.out.println("Create thread");
        try {
            Thread.sleep((long) (Math.random()*2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Thread.currentThread().getName();
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        //create a list to hold the Future object associated with Callable
        List<Future<String>> list = new ArrayList<Future<String>>();
        //Create MyCallable instance
        Callable<String> callable = new ThreadPool();
        for (int i = 0; i < 100; i++) {
            //submit Callable tasks to be executed by thread pool
            Future<String> future = executor.submit(callable);
            //add Future to the list, we can get return value using Future
            list.add(future);
        }
        for (Future<String> fut : list) {
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                System.out.println(new Date() + "::" + fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service now
        executor.shutdown();
    }


}
