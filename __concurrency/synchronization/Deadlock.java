package __concurrency.synchronization;

public class Deadlock {
    public static void main(String[] args) {
        Business business = new Business();

        for (int i = 0; i < 10; i++) {
            new Thread(business::foo).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(business::bar).start();
        }
    }
}


class Business {
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void foo() {
        synchronized (lock1) { // захват первого замка
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2) { // второй может быть захвачен уже, а там ждут освобождения первого
                System.out.println("foo");
            }
        }
    }

    public void bar() {
        synchronized (lock2) { // захват 2ого замка (а стоило бы зазватывать в том же порядке..)
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock1) {
                System.out.println("bar");
            }
        }
    }
}