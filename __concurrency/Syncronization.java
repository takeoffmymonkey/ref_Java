package __concurrency;

public class Syncronization {

    public static void main(String[] args) {
        Countdown countdown = new Countdown();

        CountdownThread t1 = new CountdownThread(countdown);
        CountdownThread2 t2 = new CountdownThread2(countdown);

        t1.start();
        t2.start();
    }

}

class Countdown {

    private int i;

    public void doCountdown() {
        String color;

        switch (Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadsColor.ANSI_CYAN;
                break;
            case "Thread 2":
                color = ThreadsColor.ANSI_PURPLE;
                break;
            default:
                color = ThreadsColor.ANSI_GREEN;
        }

        for (i = 10; i > 0; i--) {
            System.out.println(color + Thread.currentThread().getName() + ": i =" + i);
        }
    }
}

class CountdownThread extends Thread {
    private Countdown threadCountdown;

    public CountdownThread(Countdown countdown) {
        threadCountdown = countdown;
    }

    public void run() {
        try {
            sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadCountdown.doCountdown();
    }
}

class CountdownThread2 extends Thread {
    private Countdown threadCountdown;

    public CountdownThread2(Countdown countdown) {
        threadCountdown = countdown;
    }

    public void run() {
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadCountdown.doCountdown();
    }
}
