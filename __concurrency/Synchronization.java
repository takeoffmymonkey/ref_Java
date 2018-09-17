package __concurrency;

/*
 * Не всегда возможно использовать разные объекты, чтобы избежать взаимодействия между ветками,
 * например банковский счет должен быть в единственном числе. Чтобы гарантировать, что член класса
 * вызывается только 1 веткой за 1 раз, его нужно синхронизировать. Но только этот член должен быть
 * везде синхронизирован, если ветки с ним работают в разных местах
 * Конструкторы нельзя синхронизировать из-за бессмесленности (у каждой ветки и так будет свой объект)
 * не синхронизировать локальные переменные (они в стеке, другие ветки не имеют к ним доступа итак),
 * кроме строк, из-за пула
 * */



/* ПРОБЛЕМЫ МНОГОПОТОЧНОСТИ И ОТНОШЕНИЕ HAPPENS-BEFORE
 * - взаимодействие между ветками приводит к 2 возможным ошибкам:
 *      - thread interference: попытка записи в общие ресурсы несколькими ветками
 *      - memory consistency errors: несколько веток несогласовано считывают данные в общей памяти
 *      (т.е. данные уже могут быть неактуальны, т.к. другая ветка произвела запись данных)
 *          - решается отношением happens-before
 *
 * - отношение happens-before: определяет частичный порядок действий в программе - удостоверение, что
 * одно событые точно произошло до начала другого
 *      - является transitive
 *      - формируется разными действиями, напр:
 *          - освобождение замка и его же захват
 *              - и так как transitive, то все действия до освобождения замка произошли до всех
 *              действий после последующего захвата
 *          - запись в volatile переменную происходит до чтения той же переменной
 *              - эффект тот же, что и у замка, но не влечет за собой взаимноисключающий локинг
 *              - не относится к внутренним ссылкам у данной переменной
 *              - volatile store + read быстрее, чем блокировка
 *          - запись значения в final-поле (и, если это поле — ссылка, то ещё и всех переменных,
 *          достижимых из этого поля (dereference-chain)) при конструировании объекта happens-before
 *          запись этого объекта в какую-либо переменную, происходящая вне этого конструктора
 *              - т.е. если есть объект, у которого есть final-поле, то этот объект можно будет
 *              использовать только после установки этого final-поля (и всего, на что это поле может ссылаться)
 *              - но если вы передадите из конструктора ссылку на конструируемый объект (т.е. this) наружу, то кто-то может увидеть ваш объект в недостроенном состоянии.
 *              - т.е. все, что нужно для файнал должно выполниться до записи в нее, т.е. можно без
 *              волатайл и синхронизации
 *          - порядок инструкций в 1 ветке
 *          - synchronized
 *          - Thread.start()
 *          - Thread.join()
 *          - semaphore
 *          - mutex
 *      - например, код в ветке А до создания и запуска ветки Б имеет с веткой Б happens-before
 *      отношение, т.е. ветка Б имеет доступ к коду, который был перед ней в ветке А. После
 *      завершения ветки Б, если в ветке Б был join, то после него доступно все, что было сделано
 *      ветке А
 *      - отношение нв транзитивно, если А случилось до б, а б случилось до с, то а случилось до с
 *      - методы и классы в подпакетах java.util.concurrent расширяют данные гарантии до высокоуравневой
 *      синхронизации
 *      */


/* СИНХРОНИЗАЦИЯ И THREAD СОСТЯЗАНИЕ
 *  - синхронизация призвана предотвратить thread interference и memory consistency errors, но может
 *  вызывать thread contention (соперничество веток). 2 не взаимосключающие формы соперничества:
 *      - starvation: 2 или более ветки пытаются получить одновременно доступ к 1 ресурсу
 *      - livelock: JVM исполняет 1 или более веток медленней или даже совсем приостанавливает их
 *      исполнение */


/* МОНИТОРЫ, CИНХРОНИЗИРОВАННЫЕ МЕТОДЫ И БЛОКИ СИНХРОНИЗАЦИИ
 * - монитор (intrinsic (подлинный) lock или monitor lock):
 *      - играет роль в обоих аспектах синхронизации:
 *          - обеспечение эксклюзивного доступа к состоянию объекта
 *          - установление Н-В отношения
 *      - у каждого объекта есть ассоциированный монитор
 *          - по договоренности, ветка, которая нуждается в эксклюзивном доступе к полям объекта, должна
 *          запросить монитор, прежде чем обращаться к ним, а затем, по завершении, отпустить монитор
 *          - пока ветка держит монитор, никакая другая ветка не должна получить данный монитор
 *              - если ветка попытается получить монитор, удерживаемый другой веткой, она будет
 *              заблокирована
 *              - при этом сама ветка может повторно запросить монитор (reentrant synchronization):
 *              напр. в синхронизированном методе вызывается другой синхр. метод (Without reentrant
 *              synchronization, synchronized code would have to take many additional precautions to
 *              avoid having a thread cause itself to block)
 *
 *          - после освобождения монитора устанавливается Н-В отношение между освобождением и
 *          последующим запросом этого же монитора
 *
 * - синхронизированные методы:
 *      - запрещают 2 пересекающихся запуска себя на 1 объекте
 *          - предотвращает thread interference и memory consistency errors: все чтения и записи в
 *          в переменную объекта совершаются через синхронизированные методы
 *              - исключение: final поля могут безопасно читаться и без синхронизации
 *      - при завершении метода, автоматически создается отношение H-B с каждым последующим запуском
 *      данного метода в данном объекте
 *      - синхронизированных конструкторов не бывает, т.к. только создающая объект ветка должна иметь
 *      к нему доступ, пока создается объект
 *          - при создании объекта, который будет использован несколькими ветками, важно, чтобы
 *          ссылка не досталась ветке раньше времени
 *              - например, хранить экземпляры в List, добавлять в него эти экземпляры, пока они
 *              только создаются, при этом другая ветка уже может обратиться к этому списку
 *      - при запуске метода, ветка автоматически запрашивает монитор для объекта, чей метод
 *      вызывается, и отпускает его при завершении (даже при возникновении несловленного исключения)
 *          - если это статический метод, то запрашивается монитор на объект Class данного класса
 *
 * - блоки синхронизации:
 *      - в отличие от методов, требуют указания объекта, у которого запрашивается монитор
 *      - полезно для более детальной синхронизации
 * */

/* АТОМИЧЕСКИЙ ДОСТУП
 * - операция либо производится полностью, либо никак - т.е. никаких побочных эффектов операции не
 * видно, если она еще не завершилась
 * - атомические операции:
 *      - чтение и запись ссылочных переменных и для большинства примитивов (кроме long и double)
 *      - чтение и запись для всех переменных, объявленных как volatile
 * - избавляют от thread interference, но memory consistency errors все еще возможны, хоть и с
 * меньшими шансами (каждая запись устанавливает H-B отношение с последующим чтением)
 * - более эффективны чем доступ к переменным через синхронизацию, но требуют от программера
 * большего внимания для избежания memory consistency errors.
 * - некоторые классы в java.util.concurrent предоставляют атомические методы, которые не полагаются
 * на синхронизацию
 *
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