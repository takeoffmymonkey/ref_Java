package __concurrency.high_level_api;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Locks {
    static volatile int i;
    static Lock lock = new ReentrantLock();

    static void increment() {
        try {
            lock.lock();
            Thread.sleep(5);
            i++;
            System.out.println(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally { // необходимо освобождать замок
            lock.unlock();
        }
    }

    static void decrement() {
        try {
            lock.lock();
            Thread.sleep(5);
            i--;
            System.out.println(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                increment();
            }
        }).start();

        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                decrement();
            }
        }).start();
    }
}