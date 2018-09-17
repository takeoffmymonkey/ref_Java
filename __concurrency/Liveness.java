package __concurrency;


/* ЖИВУЧЕСТЬ
 * - способность concurrency исполняться во времени
 * - проблемы: deadlock, starvation и livelock (последние 2 более редкие, но обязательно встречаются)
 *
 * - deadlock: 2 или более ветки заблокированы навсегда, ожидая друг друга. 1 поклонился другому и
 * ждет пока другой тоже поклонится, но поклонились одновременно.
 *
 * - starvation: ветка не может получить обычный доступ к общим ресурсам и не может продвигаться,
 * из-за того, что ресурс долго недоступен, т.к. его зажала другая "жадная ветка". Например, у
 * объекта есть синхронизированный метод, который долго исоплняется. Если 1 ветка часто его вызывает,
 * то другие ветки, которым тоже нужен частый синхронизированный доступ, часто будут заблокированы.
 *
 * - livelock: ветка часто действует в ответ на действие другой. Если действие другой то же является
 * ответом на действие первой. Как и с deadlock, прогресса нет, но ветки не заблокированы, они
 * просто сильно заняты ответом друг другу, чтобы продолжить работу. 2 человека встретились в коридоре
 * и пропускают друг друга одновременно в ту же сторону, постоянно меняя сторону одновременно.
 * */

public class Liveness {

    public static void main(String[] args) {
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");
        new Thread(() -> alphonse.bow(gaston)).start();
        new Thread(() -> gaston.bow(alphonse)).start();
    }
}


class Friend {
    private final String name;

    public Friend(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public synchronized void bow(Friend bower) {
        System.out.format("%s: %s"
                        + "  has bowed to me!%n",
                this.name, bower.getName());
        bower.bowBack(this);
    }

    public synchronized void bowBack(Friend bower) {
        System.out.format("%s: %s"
                        + " has bowed back to me!%n",
                this.name, bower.getName());
    }
}
