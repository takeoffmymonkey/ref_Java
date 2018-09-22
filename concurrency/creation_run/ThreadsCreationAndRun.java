package concurrency.creation_run;

import static concurrency.ThreadsColor.ANSI_BLUE;
import static concurrency.ThreadsColor.ANSI_CYAN;
import static concurrency.ThreadsColor.ANSI_GREEN;
import static concurrency.ThreadsColor.ANSI_PURPLE;
import static concurrency.ThreadsColor.ANSI_RED;

public class ThreadsCreationAndRun {

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


class ThreadSon extends Thread {
    @Override
    public void run() {
        System.out.println(ANSI_BLUE + "ThreadSon: " + currentThread().getName());
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(ANSI_RED + "MyRunnable");
    }
}