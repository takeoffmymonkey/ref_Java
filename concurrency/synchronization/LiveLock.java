package concurrency.synchronization;

public class LiveLock {
    public static void main(String[] args) {
        Buyer buyer = new Buyer();
        Seller seller = new Seller();

        Thread t1 = new Thread(() -> buyer.buy(seller));
        t1.start();

        Thread t2 = new Thread(() -> seller.sell(buyer));
        t2.start();
    }
}


/*
 * Продавец передаст товар только, когда получит деньги
 * Покупатель даст деньги только, когда получит товар
 * */
class Buyer {
    boolean haveItem;

    void buy(Seller buyer) {
        while (!haveItem) {
            System.out.println("no item received so far..");
        }
        System.out.println("received item");
        buyer.haveMoney = true;
    }
}

class Seller {
    boolean haveMoney;

    void sell(Buyer buyer) {
        while (!haveMoney) {
            System.out.println("no money received so far..");
        }
        System.out.println("received money");
        buyer.haveItem = true;
    }
}