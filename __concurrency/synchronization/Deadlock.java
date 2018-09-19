package __concurrency.synchronization;

public class Deadlock {
    static class Friend {
        private final String name;

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public synchronized void bow(Friend bower) {
            System.out.println(this.name + ": bowing to " + bower.getName());
            System.out.println("Asking to bow back");
            bower.bowBack(this);
        }

        public synchronized void bowBack(Friend bower) {
            System.out.format(this.name + ": %s" + " asked me to bow back!%n",
                    bower.getName());
        }
    }

    public static void main(String[] args) {
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");
        new Thread(() -> alphonse.bow(gaston)).start();
        new Thread(() -> gaston.bow(alphonse)).start();
    }
}
