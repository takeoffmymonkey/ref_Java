package __concurrency;

import static __concurrency.ThreadsColor.ANSI_BLUE;

public class ThreadSon extends Thread {
    @Override
    public void run() {
        System.out.println(ANSI_BLUE + "ThreadSon: " + currentThread().getName());
    }
}
