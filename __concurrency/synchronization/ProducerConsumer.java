package __concurrency.synchronization;

public class ProducerConsumer {

    public static void main(String[] args) {
        Item item = new Item();
        Producer producer = new Producer();
        Consumer consumer = new Consumer();

            new Thread(() -> {
                try {
                    producer.produce(item);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            new Thread(() -> {
                try {
                    consumer.consume(item);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
    }
}


class Item {
    volatile boolean ready;

    void put() {
        ready = true;
    }

    void get() {
        ready = false;
    }
}


class Producer {
    void produce(Item item) throws InterruptedException {
        while (true) {
            if (!item.ready) {
                Thread.sleep(1000);
                item.put();
                System.out.println("Producer: produced");
                synchronized (item) {
                    item.notifyAll();
                }
            }
        }
    }
}


class Consumer {
    void consume(Item item) throws InterruptedException {
        while (true) {
            while (!item.ready) {
                synchronized (item) {
                    item.wait();
                }
                Thread.sleep(1000);
                item.get();
                System.out.println("Consumer: consumed");
            }
        }
    }
}