package __concurrency.synchronization;

public class DoubleCheckedLockingSingleton {

}


class WrongLoner {
    private WrongLoner loner;

    public WrongLoner getLoner() {
        if (loner == null) { // для нескольких веток возможно loner == null
            loner = new WrongLoner(); // тогда создадутся лишние объекты
        }
        return loner;
    }
}


class SlowSynchroLoner {
    private SlowSynchroLoner loner;

    public synchronized SlowSynchroLoner getLoner() { // правильно, но очень медленно при частом обращении
        if (loner == null) {
            loner = new SlowSynchroLoner();
        }
        return loner;
    }
}


class FasterSynchroLoner {
    private volatile FasterSynchroLoner loner; // должен быть volatile - читай ниже!

    public FasterSynchroLoner getLoner() { // правильно, но медленно при частом обращении
        if (loner == null) { // если уже инициализирована, то можно пропустить код дальше
            synchronized (this) { // захват блока
                if (loner == null) { // повторная проверка, т.к. кто-то мог до блока уже инициализировать
                    //ошибка в том, что проверяться может кешированная версия
                    loner = new FasterSynchroLoner();
                    // ошибка в том, что модель памяти Java позволяет публикацию частично созданных
                    // объектов, поэтому другая ветка может получить ссылку на неготовый объект,
                    // поэтому поле нужно сделать volatile (c J 1.5)
                }
            }
        }
        return loner;
    }
}


class StaticLoner {
    private static WrongLoner loner = new WrongLoner(); // но невозможна ленивая инициализация

    public WrongLoner getLoner() {
        return loner;
    }
}


// Наиболее точный и безопасный способ
enum BestEnumLoner {
    INSTANCE;

    void someMethod() {
    }
}
