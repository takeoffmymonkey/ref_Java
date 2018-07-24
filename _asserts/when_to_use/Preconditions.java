package _asserts.when_to_use;

/*ПРЕДУСЛОВИЕ
 * - что должно быть верно перед выполнением*/

public class Preconditions {

    /*ПРИМЕР 1 (что должно быть верно при вызове метода (непубличного))*/
    // Установка интервала обновления (должна соответствовать допустимому frame rate)
    private void setRefreshInterval(int interval) {
        int MAX_REFRESH_RATE = 50;
        // Подтвердить соответствие предусловию в непубличном методе
        assert interval > 0 && interval <= 1000 / MAX_REFRESH_RATE : interval;
        // ... Установить интервал обновления
    }


    /*ПРИМЕР 2 (определение, удерживается ли тот или иной замок (многопоточность))*/
    private Object[] a;

    public synchronized int find(Object key) {
        return find(key, a, 0, a.length);
    }

    // Рекурсивный helper метод - always called with a lock on this.
    private int find(Object key, Object[] arr, int start, int len) {
        assert Thread.holdsLock(this); // предположение относительно lock-статуса
        // ...
        return 0;
    }
}
