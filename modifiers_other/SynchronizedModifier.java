package modifiers_other;

/* Для указания, что метод может быть доступен только одним потоком одновременно*/

public class SynchronizedModifier {

    /*СИНХРОНИЗИРОВАННЫЙ МЕТОД
     * - Может быть применен с любым из четырех модификаторов уровня доступа*/
    synchronized private void synchronizedMethod() {
    }

    void method() {
        /*СИНХРОНИЗИРОВАННАЯ ЧАСТЬ МЕТОДА*/
        synchronized (this) {
            int a = 6;
        }
    }
}