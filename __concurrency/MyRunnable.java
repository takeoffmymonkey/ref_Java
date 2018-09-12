package __concurrency;

import static __concurrency.ThreadsColor.ANSI_RED;

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(ANSI_RED + "MyRunnable");
    }
}
