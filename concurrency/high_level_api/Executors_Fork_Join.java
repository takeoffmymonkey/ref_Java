package concurrency.high_level_api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Executors_Fork_Join {

    public static ForkJoinPool forkJoinPool = new ForkJoinPool(2);

    public static void main(String[] args) {
        /* ~~~~~~~~~~~~~~~~CALLABLE + FUTURE + EXECUTORSERVICE~~~~~~~~~~~~~~~~ */
        Callable<String> c = () -> {
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName() + " callable");
            return "Yo";
        };

        List<Callable<String>> callables = new ArrayList<>();
        callables.add(c);
        callables.add(c);
        callables.add(c);
        callables.add(c);
        callables.add(c);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<Future<String>> futures = null;
        try {
            futures = executorService.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!futures.get(4).isDone()) { // возможно не тред-сейв код
            futures.get(4).cancel(true);
        }

        for (Future<String> future : futures) {
            try {
                String s = future.get(1000, TimeUnit.MILLISECONDS);
                System.out.println(s);
            } catch (InterruptedException | TimeoutException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();


        /* ~~~~~~~~~~~~~~~~SCHEDULEDEXECUTORSERVICE~~~~~~~~~~~~~~~~ */
        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
        /* DELAYED */
        service.schedule(c, 1, TimeUnit.SECONDS);


        /* PERIODIC */
        Runnable r = () -> System.out.println("Periodic");
        service.scheduleAtFixedRate(r, 1, 2, TimeUnit.SECONDS);


        /* ~~~~~~~~~~~~~~~~~~~FORK/JOIN FRAMEWORK~~~~~~~~~~~~~~~~~~*/
        int [] array  = {2,2,543,3,123,234,4,32,2,34};

        ArrayTransform mainTask = new ArrayTransform(array, 123, 0, array.length);
        ForkJoinPool pool = new ForkJoinPool();
        forkJoinPool.invoke(mainTask);

    }
}

class ArrayTransform extends RecursiveAction {
    int[] array;
    int number;
    int threshold = 3;
    int start;
    int end;

    public ArrayTransform(int[] array, int number, int start, int end) {
        this.array = array;
        this.number = number;
        this.start = start;
        this.end = end;
    }

    protected void compute() {
        if (end - start < threshold) {
            computeDirectly();
        } else {
            int middle = (end + start) / 2;

            ArrayTransform subTask1 = new ArrayTransform(array, number, start, middle);
            ArrayTransform subTask2 = new ArrayTransform(array, number, middle, end);

            invokeAll(subTask1, subTask2);
        }
    }

    protected void computeDirectly() {
        for (int i = start; i < end; i++) {
            array[i] = array[i] * number;
            System.out.println(array[i]);
        }
    }
}