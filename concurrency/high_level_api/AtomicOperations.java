package concurrency.high_level_api;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicOperations {

    class AtomicCounter {
        private AtomicInteger c = new AtomicInteger(0);

        public void increment() {
            c.incrementAndGet();
        }

        public void decrement() {
            c.decrementAndGet();
        }

        public int value() {
            return c.get();
        }
    }
}
