package __concurrency.synchronization;

public class SynchronizeLocking {
    public static void main(String[] args) {
        Common c = new Common();

        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    c.syncIncrease();
                    c.independentMeth1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    c.syncDecrease();
                    c.independentMeth2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": " + c.v);
            }
        }).start();
    }
}


class Common {
    int v;
    int a;
    int b;
    final Object lock = new Object();
    final Object lock2 = new Object();

    // берет монитор объекта, на котором вызван -> блокирует весь объект, т.е. никто не изменит v, пока он выполняется
    synchronized void syncIncrease() throws InterruptedException {
        Thread.sleep(2);
        v++;
    }

    synchronized void syncDecrease() throws InterruptedException {
        Thread.sleep(2);
        v--;
    }

    // независимый от общего замка метод -> не блокирует весь объект
    void independentMeth1() throws InterruptedException {
        synchronized (lock) { // имеет отдельный замок
            a++;
        }
    }

    void independentMeth2() throws InterruptedException { // независимый от общего замка метод -> не блокирует весь объект
        synchronized (lock2) { // имеет отдельный замок
            b++;
        }
    }
}