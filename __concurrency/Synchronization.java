package __concurrency;

/* 2 (СЛОЖНОВОСПРОИЗВОДИМЫЕ!) ПРОБЛЕМЫ РАБОТЫ С ОБЩИМ РЕСУРСОМ
 * 1 - Thread interference: при одновременном воздействии на общий ресурс, результат работы одной
 * ветки может быть утерян (т.е. заменен результатом второй ветки)
 *      - например, 2 ветки A и B, А инкрементирует (это 3 операции!) переменную С, B декрементирует:
 *          1. А получает значение С (0)
 *          2. В получает значение С (0)
 *          3. А инкрементирует полученное значение (1)
 *          4. В инкрементирует полученное значение (-1)
 *          5. А записывает результат в С (1) // будет проигнорирован
 *          6. А записывает результат в С (-1)
 *
 * 2 - Memory consistency errors: нет гарантий последовательности выполнения инструкций, поэтому при
 * его чтении можно получить неактульные данные
 *      - процессор, компилятор и JIT из-за оптимизации выполняют инструкции непоследовательно
 *          - но для 1 ветки можно безопасно рассчитывать на правильную последовательность!
 *      - например:
 *          1. ветка A увеличивает на 1 значение переменной С (1)
 *          2. ветка B печатает значение переменной С: будет либо 0 либо 1, т.к. нет гарантий, что
 *          увеличение видно данной ветке
 *
 * - обе решается отношением happens-before (синхронизацией) */


/* ОТНОШЕНИЕ HAPPENS-BEFORE (ПОРЯДОК ВЫПОЛНЕНИЯ)
 * - определяет частичный порядок действий в программе - удостоверение, что 1 ссобытые точно произошло
 * до начала другого
 *      - решает проблемы Thread interference и Memory consistency
 *      - например, код в ветке А - до создания и запуска ветки В - имеет с веткой В отношение Н-В,
 *      т.е. ветка В имеет доступ к коду, который был перед ней в ветке А.
 *          - после завершения ветки В, если в ветке A был join c В, то после него ветке А доступно
 *          все, что было сделано в ветке В
 *
 * - является основой для всех приемов синхронизации
 *
 * - является транзитивным: если А случилось до В, а В случилось до С, то A случилось до C */


/* ПРИЕМЫ СИНХРОНИЗАЦИИ (ОСНОВАНЫ НА УСТАНОВЛЕНИИ ОТНОШЕНИЯ HAPPENS-BEFORE)
 * - код в 1 ветке -> последующий код в той же ветке:
 *      - т.е. однопоточный код
 *
 * - запись значения в final-поле в конструкторе -> запись конструируемого объекта в переменную
 *      - если поле - ссылка, то и всех достижимых из поля переменных!
 *      - но можно передать ссылку на объект из конструктора (this) и тогда можно увидеть
 *      недостроенный объект извне
 *      - т.е. все, что нужно для final-поля, выполняется до записи в него
 *          - todo т.е. можно без volatile/synchronize
 *
 * - код в ветке A до Thread.start() -> код в запущенной ветке В
 *
 * - код в ветке B -> код в ветке А после Thread.join()
 *
 * - освобождение монитора (intrinsic lock) -> его последующий захват:
 *      - монитор: эксклюзивный замок, который есть у каждого объекта
 *          - используется для последовательного выполнения кода в синхронизированном блоке/методе
 *          из разных веток:
 *              - любая ветка, перед попыткой выполнить синхронизированный код, сначала должна
 *              захватить монитор, а после завершения работы освобождает его
 *                  - захват и освобождение происходит автоматически
 *              - пока ветка держит монитор, никакая другая ветка не должна получить данный монитор
 *                  - если другая ветка попытается получить удерживаемый монитор, она будет
 *                  заблокирована
 *                  - при этом сама ветка может повторно запросить монитор (reentrant synchronization):
 *                      - напр. в синхронизированном методе вызывается другой синхронизированный метод
 *              - монитор какого объекта удерживается, роли не играет
 *      - способы захвата:
 *          - синхронизированный метод: synchronized void method(){};
 *              - захватывает текущий объект, которому принадлежит (т.е. this)
 *                  - если это статический метод, то захватывает монитор объекта Class данного класса
 *              - недостаток: нельзя параллельно выполнять другие синхронизированные методы у того
 *              же объекта, даже если они никак не пересекаются с данным
 *          - синхронизированный блок: synchronized (lock) {}
 *              - захватывает указанный объект
 *              - полезно для более детальной синхронизации
 *              - можно иметь несколько замков Object для разных синхронизированных блоков, если они
 *              между собой не пересекаются
 *          - синхронизированных конструкторов не бывает: только создающая его ветка имеет к нему
 *          доступ при создании
 *
 * - атомарные операции: операция либо совершается полностью, либо не совершается совсем, т.е. в
 * процессе операции нет видимых побочных эффектов
 *      - запись ссылочных переменных/большинства примитивов -> чтение значения
 *          - кроме long и double
 *      - volatile: запись любого значения -> чтение значения
 *          - легкая версия synchronized, но менее предпочтительная, т.к. подходит только для
 *          некоторых случаев использования (из-за Memory consistency errors): должна не зависить от
 *          предыдущего значения и от других переменных
 *      - исключает Thread interference:
 *          - т.е. если ветка А произвела запись в volatile С, то ветка B после этого увидит эту
 *          запись
 *      - НЕ ИСКЛЮЧАЕТ MEMORY CONSISTENCY ERRORS!!!
 *          - например, если ветка А начала операцию инкрементации (не является атомической и состоит
 *          из 3 шагов, где запись последний) над volatile С, то за это время ветка B может успеть
 *          получить начальное значение С
 *          - поэтому используется только в ограниченных
 *
 *
 *
 *
 * - высокоуравневые:
 *      - Semaphore
 *      - Lock
 *      - todo Mutex
 *
 *          - запись в volatile переменную происходит до чтения той же переменной
 *              - эффект тот же, что и у замка, но не влечет за собой взаимноисключающий локинг
 *              - не относится к внутренним ссылкам у данной переменной
 *              - volatile store + read быстрее, чем блокировка

 *      - методы и классы в подпакетах java.util.concurrent расширяют данные гарантии до высокоуравневой
 *      синхронизации
 *      */


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



/* СИНХРОНИЗАЦИЯ И THREAD СОСТЯЗАНИЕ
 *  - синхронизация призвана предотвратить thread interference и memory consistency errors, но может
 *  вызывать thread contention (соперничество веток). 2 не взаимосключающие формы соперничества:
 *      - starvation: 2 или более ветки пытаются получить одновременно доступ к 1 ресурсу
 *      - livelock: JVM исполняет 1 или более веток медленней или даже совсем приостанавливает их
 *      исполнение */



/*
 * Не всегда возможно использовать разные объекты, чтобы избежать взаимодействия между ветками,
 * например банковский счет должен быть в единственном числе. Чтобы гарантировать, что член класса
 * вызывается только 1 веткой за 1 раз, его нужно синхронизировать. Но только этот член должен быть
 * везде синхронизирован, если ветки с ним работают в разных местах
 * Конструкторы нельзя синхронизировать из-за бессмесленности (у каждой ветки и так будет свой объект)
 * не синхронизировать локальные переменные (они в стеке, другие ветки не имеют к ним доступа итак),
 * кроме строк, из-за пула
 * */


/* if(data == null) {
synchronized(this) {
        if(data == null) {
        data = new Data();
        }
        }
        }

synchronized(this) {
            if(data == null) {
                data = new Data();
            }
        }


        */




/*Memory Consistency Properties
Chapter 17 of the Java Language Specification defines the happens-before relation on memory operations such as reads and writes of shared variables. The results of a write by one thread are guaranteed to be visible to a read by another thread only if the write operation happens-before the read operation. The synchronized and volatile constructs, as well as the Thread.start() and Thread.join() methods, can form happens-before relationships. In particular:

Each action in a thread happens-before every action in that thread that comes later in the program's order.
An unlock (synchronized block or method exit) of a monitor happens-before every subsequent lock (synchronized block or method entry) of that same monitor. And because the happens-before relation is transitive, all actions of a thread prior to unlocking happen-before all actions subsequent to any thread locking that monitor.
A write to a volatile field happens-before every subsequent read of that same field. Writes and reads of volatile fields have similar memory consistency effects as entering and exiting monitors, but do not entail mutual exclusion locking.
A call to start on a thread happens-before any action in the started thread.
All actions in a thread happen-before any other thread successfully returns from a join on that thread.

The methods of all classes in java.util.concurrent and its subpackages extend these guarantees to higher-level synchronization. In particular:

Actions in a thread prior to placing an object into any concurrent collection happen-before actions subsequent to the access or removal of that element from the collection in another thread.
Actions in a thread prior to the submission of a Runnable to an Executor happen-before its execution begins. Similarly for Callables submitted to an ExecutorService.
Actions taken by the asynchronous computation represented by a Future happen-before actions subsequent to the retrieval of the result via Future.get() in another thread.
Actions prior to "releasing" synchronizer methods such as Lock.unlock, Semaphore.release, and CountDownLatch.countDown happen-before actions subsequent to a successful "acquiring" method such as Lock.lock, Semaphore.acquire, Condition.await, and CountDownLatch.await on the same synchronizer object in another thread.
For each pair of threads that successfully exchange objects via an Exchanger, actions prior to the exchange() in each thread happen-before those subsequent to the corresponding exchange() in another thread.
Actions prior to calling CyclicBarrier.await and Phaser.awaitAdvance (as well as its variants) happen-before actions performed by the barrier action, and actions performed by the barrier action happen-before actions subsequent to a successful return from the corresponding await in other threads.*/


import java.util.ArrayList;
import java.util.List;

import static __concurrency.ThreadsColor.ANSI_GREEN;
import static __concurrency.ThreadsColor.ANSI_PURPLE;
import static __concurrency.ThreadsColor.ANSI_RED;

public class Synchronization {
    static int v;

    public static void main(String[] args) {
/*        Counter counter = new Counter();

        Thread t0 = new Thread(new MyThread(counter));
        Thread t1 = new Thread(new MyThread2(counter));
        t0.start();
        t1.start();*/

        Runnable r0 = () -> {
            for (int i = 0; i < 10_000_00; i++) {
                Synchronization.v++;
                System.out.println(Thread.currentThread().getName() + v);
            }
        };

        Runnable r1 = () -> {
            for (int i = 0; i < 10_000_00; i++) {
                Synchronization.v--;
                System.out.println(v);
            }
        };

        Thread t0 = new Thread(r0);
        Thread t1 = new Thread(r1);
        t0.start();
        t1.start();

    }
}

class Counter {
    private volatile int c = 0;

    public void increment() {
        c++; // 3 действия: получить, увеличить, вписать новое значение
    }

    public void decrement() {
        c--;
    }

    public int value() {
        return c;
    }
}


class MyThread implements Runnable {
    Counter c;

    public MyThread(Counter c) {
        this.c = c;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            c.increment();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(ANSI_GREEN + Thread.currentThread().getName() + ": " + c.value());
        }
    }
}

class MyThread2 implements Runnable {
    Counter c;

    public MyThread2(Counter c) {
        this.c = c;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            c.decrement();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(ANSI_PURPLE + Thread.currentThread().getName() + ": " + c.value());
        }
    }
}


class SyncBlock {
    List<String> names = new ArrayList<>();
    int nameCount;
    String lastName;

    public void addName(String name) {
        synchronized (this) { // без него должен бы быть отдельный несинхронизированный метод с единой целью names.add(name)
            lastName = name;
            nameCount++;
        }
        names.add(name);
    }
}