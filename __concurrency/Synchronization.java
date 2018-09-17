package __concurrency;

/* ПРОБЛЕМЫ МНОГОПОТОЧНОСТИ И ОТНОШЕНИЕ HAPPENS-BEFORE
 * - взаимодействие между ветками приводит к 2 возможным ошибкам:
 *      - thread interference: попытка записи в общие ресурсы несколькими ветками
 *      - memory consistency errors: несколько веток несогласовано считывают данные в общей памяти
 *      (т.е. данные уже могут быть неактуальны, т.к. другая ветка произвела запись данных)
 *          - решается отношением happens-before
 *
 * - отношение happens-before: определяет частичный порядок действий в программе - удостоверение, что
 * одно событые точно произошло до начала другого
 *      - формируется разными действиями, напр:
 *          - synchronized
 *          - volatile
 *          - Thread.start()
 *          - Thread.join()
 *      - например, код в ветке А до создания и запуска ветки Б имеет с веткой Б happens-before
 *      отношение, т.е. ветка Б имеет доступ к коду, который был перед ней в ветке А. После
 *      завершения ветки Б, если в ветке Б был join, то после него доступно все, что было сделано
 *      ветке А */


/* СИНХРОНИЗАЦИЯ И THREAD CONTENTION
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