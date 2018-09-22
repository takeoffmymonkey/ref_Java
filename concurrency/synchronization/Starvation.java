package concurrency.synchronization;

public class Starvation {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(Starvation::heavyWork).start();
        }
    }

    static synchronized void heavyWork() {
        while (true) { // будет постоянно работать только для первой ветки
            System.out.println(Thread.currentThread().getName() + " working...");
        }
    }
}
