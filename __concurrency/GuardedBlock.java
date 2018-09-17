package __concurrency;

/* Веткам часто нужно координировать свои действия. Самая распространненная идиома координации -
 * защищенный блок.
 * - проверяет условие на верность прежде чем продолжать. Например, метод может продолжать, когда будет
 * установлено тру для условия.
 * - Если постоянно крутить луп, это слишком расточительно - можно  использовать wait
 *       - запускает проверку условия только при наступлении специальных событий (которые могут быть,
 *       а могут и не быть ожидаемым событием)
 *       - вызов wait не возвращается, пока другая ветка не выдаст уведомление о том, что наступило
 *       какое-то важное событие
 *       - wait должен быть внутри лупа, т.к. не факт, что событие - именно то, что ожидалось
 *       - также выбрасывает InterruptedException
 *       - для вызова wait на объекте, ветке нужен его монитор (иначе ошибка), поэтому wait, например,
 *       используется в синхронизированном методе
 *       - при вызове wait ветка отпускает монитор и приостанавливает выполнение, а в будущем другая
 *       ветка запросит этот же лок и вызовет notifyAll, информируя все ветки, ожидающие данный монитор,
 *       что наступило важное событие
 *       - после того, как ветка после вызова notifyAll отпустит замок, первая ветка снова запросит его
 *       и продолжит с вызова wait
 *
 * - чтобы вызвать wait/notify на объекте текущая ветка должна обладать монитором на данный объект
 *
 * - метод notify пробуждает 1 ветку. Т.к. он не позволяет указывать ветку, которую нужно разбудить,
 * он используется в сильно параллельных аппах - где много веток и все делают схожие действия, и
 * поэтому неважно, какая из веток проснется
 *
 * - producer-consumer: потребитель не должен пытаться получить данные, прежде чем продюсер из принесет,
 * а продюсер не должен пытаться принести данные, пока потребитель не получил предыдущие
 * */


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