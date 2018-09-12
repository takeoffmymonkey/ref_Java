package __concurrency;

import static __concurrency.ThreadsColor.ANSI_CYAN;
import static __concurrency.ThreadsColor.ANSI_GREEN;
import static __concurrency.ThreadsColor.ANSI_PURPLE;

public class ThreadsCreation {

    public static void main(String[] args) {
        System.out.println(ANSI_PURPLE + "Main thread");

        /* EXTENDING THREAD CLASS */
        new ThreadSon().start();


        /* ANONYMOUS THREAD IMPLEMENTATION */
        new Thread() {
            @Override
            public void run() {
                System.out.println(ANSI_GREEN + "Anonymous thread: " + currentThread().getName());
            }
        }.start();


        /* THREAD FROM RUNNABLE */
        new Thread(new MyRunnable()).start();


        /* THREAD FROM ANONYMOUS RUNNABLE IMPLEMENTATION*/
        new Thread(new MyRunnable() {
            @Override
            public void run() {
                System.out.println(ANSI_CYAN + "Anonymous runnable");
            }
        }).start();
    }
}