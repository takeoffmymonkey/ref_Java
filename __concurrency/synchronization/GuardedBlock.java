package __concurrency.synchronization;


public class GuardedBlock {
    boolean b;


    public static void main(String[] args) {
        GuardedBlock g = new GuardedBlock();

        Runnable r0 = () -> {
            while (!g.b) {
                synchronized (g) {
                    try {
                        g.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("b is true now");
        };

        Runnable r1 = () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            g.b = true;
            synchronized (g) {
                g.notifyAll();
            }
        };

        new Thread(r0).start();
        new Thread(r1).start();
    }
}