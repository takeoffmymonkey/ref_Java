package __concurrency;

import types_references_annotations.my_annotations.Ntrstn;

import static __concurrency.ThreadsColor.ANSI_BLUE;
import static __concurrency.ThreadsColor.ANSI_CYAN;
import static __concurrency.ThreadsColor.ANSI_GREEN;

/*java.util.concurrent*/

/* PROCESS, THREAD, CONCURRENCY (MULTI-THREADING)
 * - даже если 1 ядро у процессора, его время делится между процессами и ветками через фичу ОС
 * "time slicing"
 *
 * - процесс: имеет свою самодостаточную среду исполнения. Он обычно имеет полный, приватный набор
 * базовых run-time ресурсов - в частности у каждого процесса есть свое место в памяти. Обычно 1 апп -
 * 1 процесс, но это может быть и набор коммуницирующих процессов - большинство ОС поддерживают
 * ресурсы Inter Process Communication (IPC), такие как pipes и sockets. IPC может использоваться
 * для общения процессов не только на 1 системе, но и на разных. Большинство имплементаций JVM
 * работают как 1 процесс. Приложение может создавать дополнительные процессы при помощи объекта
 * ProcessBuilder.
 *
 * - ветки (потоки): т.н. легковесные процессы - также предоставляют среду исполнения, но создание новой
 * требует меньше ресурсов, чем создание процесса. Существуют внутри процесса (у каждого процесса
 * есть минимум 1 ветка) и делят его ресурсы между собой, включая память и открытие файлов (
 * эффективно, но приводит к проблемам коммуникации). Изначально есть только 1 ветка main - из нее
 * можно создавать другие, а также существуют системные ветки (напр. для управления памятью). Каждая
 * ветка ассоциирована с экземпляром Thread */

/* 2 СТРАТЕГИИ ИСПОЛЬЗОВАНИЯ ОБЪЕКТА THREAD
 * - чтобы напрямую управлять созданием и управлением ветки, нужно просто создавать Thread каждый
 * раз, когда приложению нужно инициализировать несинхронную задачу
 * - чтобы абстрагировать управление ветками от остальной части приложения, нужно передавать задачи
 * приложения в executor (относится к высокоуравнему API для управления ветками) */


/* 2 СПОСОБА СОЗДАНИЯ И ЗАПУСКА ПОТОКА:
 * - 1: имплементировать интерфейс Runnable и его метод run, и передать экземпляр в конструктор
 * Thread (Runnable)
 *      - предпочтительней, т.к. класс может наследоваться от любого другого, а не обязательно от
 *      Thread. Кроме того, данный способ используется в высокоурованевом API
 * - 2: наследовать Thread и переопределить его метод run (изначально ничего не делает)
 *      - легче исопльзовать в простых приложениях
 * - после этого запустить на метод start на экземпляре Thread*/


/* RUNNABLE
 * - функциональный инт-с: run()
 * - имплементируется классом, чьи экземпляры должны испольнятся веткой
 * - предоставляет общий протокол для объектов, которые хотят исполнять код, пока они активны (т.е.
 * ветка началась и пока еще не остановлена)
 *      - напр. Thread имплементирует Runnable
 * - предоставляет возможность классу быть активным без наследования Thread
 *      - класс имплементирует Runnable, создает экземпляр Thread и передает себя в качестве цели
 * - обычно инт-с должен использоваться, если только планируется овверайдить run(), а не другие
 * методы Thread
 * - run: когда для создания ветки используется объект, имплементирующий Runnable, начинание ветки
 * приводит к вызову метода run у объекта в этой отдельно исполняющейся ветке */


/* THREAD - ВЕТКА ИСПОЛНЕНИЯ В ПРОГРАММЕ
 * - public class Thread extends Object implements Runnable
 * - у каждой ветки есть приоритет - они исполняются соответственно их приоритету
 * - каждая ветка может быть обозначена как демон
 * - при создании ветки в уже запущенной ветке, ее приоритет изначально будет соответствовать
 * родительской, и если родительская была демоном, то это тоже будет
 * - JVM исполняется ветки, пока:
 *      - не был вызван метод exit класса Runtime и security manager разрешил произвести операцию exit
 *      - все ветки, не являющиеся демонами, не умрут - либо возвращаясь из вызова в метод run или
 *      пока не будет выброшено исключение, которое propagates дольше метода run
 * - у каждой ветки есть имя:
 *      - более 1 ветки могут иметь 1 имя
 *      - если имя не указано, генерируется новое
 *
 * - временное прекращение выполнения ветки (sleep): полезно для предоставления процессорного времени
 * другим веткам, для pacing и для ожидания других веток с обязанностями, которые требуют времени
 * выполнения.
 *
 * - прерывание (interrupt): указание ветке, что она должна остановиться делать то, что делает, и
 * сделать что-то иное. Ветка должна поддерживать собственное прерывание (иметь такой механизм). Многие методы
 * поэтому выбрасывают InterruptedException и прерывают свое исполнение. Если ветка работает долго
 * без вызова метода, выбрасывающего InterruptedException, то она должна периодически вызывать
 * Thread.interrupted (флаг со статусом прерванности) для проверки, было ли получено прерывание и работать дальше с учетом данного
 * условия (если было получено, то можно выбросить InterruptedException - это позволяет коду,
 * который обрабатывает прерывания быть сосредоточенным в catch). По договоренности, каждый метод,
 * который завершается выбрасыванием InterruptedException, в этот момент обнуляет статус прерванности
 * (может быть после этого опять установлен)
 *
 * - присоединение (join): позволяет текущей ветке подождать завершения выполнения указанной. Напр.
 * t.join() - текущая ветка будет ждать завершения ветки t, а потом продолжит. Можно также указать
 * время ожидания, вместо завершения ветки (но все равно вызывается на объекте другой ветки) -
 * удобно, чтобы периодически выводить проверять статус выполняющейся ветки и периодически выводить
 * сообщения о ней с главной.
 *
 * - методы:
 *      - sleep: временное прекращение выполнения ветки.
 *
 *      - interrupt: прерывает ветку (установка флага прерванности). Если ветка заблокирована методами wait (класс Object), join,
 *      sleep, то статус прерванности обнуляется и будет получено InterruptedException.
 *      - isInterrupted: проверка статуса прерванности ветки другой веткой
 *      - interrupted (статический): проверка прерванности c обнулением статуса (второй вызов даст false, если
 *      ветка не была снова прервана)
 *
 *      - join: ожидает максимум указанное кол-во миллисекунд до смерти данной ветки. 0 - бесконечно
 *
 *      - run: если ветка была создана отдельным объектом Runnable, то вызывается его метод run,
 *      иначе этот метод ничего не делает
 *      - start: запускает выполнение ветки. JVM вызывает метод run для этой ветки. В результате
 *      теперь 2 ветки, работающие одновременно: текущая ветка (которая вернулась с вызова метода
 *      start) и другая ветка, которая исполняет свой метод run. Запрещено запускать ветку более 1
 *      раза (т.е. в частности не может быть запушена после выполнения)
 *
 * */


/* У каждой ветки свой stack для локальных переменных и только она имеет к нему доступ, но у всех
 * веток есть доступ в heap, где хранятся переменные члена
 * Не всегда возможно использовать разные объекты, чтобы избежать взаимодействия между ветками,
 * например банковский счет должен быть в единственном числе. Чтобы гарантировать, что член класса
 * вызывается только 1 веткой за 1 раз, его нужно синхронизировать. Но только этот член должен быть
 * везде синхронизирован, если ветки с ним работают в разных местах
 * Конструкторы нельзя синхронизировать из-за бессмесленности (у каждой ветки и так будет свой объект)
 * не синхронизировать локальные переменные (они в стеке, другие ветки не имеют к ним доступа итак),
 * кроме строк, из-за пула
 *
 * */



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

@Ntrstn("Вообще у приложения изначально не одна ветка - есть еще системные ветки для управления " +
        "памятью и т.д. Но с точки зрения программиста она 1 - main")

@Ntrstn("Так как main также является веткой, то c ней можно делать то же, что и с другими ветками, " +
        "например, уложить спать")

@Ntrstn("Метод interrupt по сути просьба (установка флага прерванности) у текущей ветки прервать то, " +
        "что она сейчас делает. Сама ветка должна работать с постоянной проверкой флага, и если он " +
        "указывает на необходимость прервать выполнение, то так и нужно поступить, например, выбросив " +
        "InterruptedException. А код, который запросил прерывание, укажет, что делать дальше при " +
        "обработке данного исключения")

@Ntrstn("Если вручную вызывать метод run вместо start, то он будет выполнен в текущей ветке, а не в " +
        "отдельной")


public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread t = sleepyThread(2000);
        t.start();

        Thread t1 = interruptingThread();
        t1.start();
        Thread.sleep(30);
        t1.interrupt();

        System.out.println(ANSI_BLUE + Thread.currentThread() + "Before join");
        Thread t2 = sleepyThread(4000);
        t2.start();
        t2.join(); // текущая ветка продолжится после t или после указанного времени
        System.out.println(ANSI_BLUE + Thread.currentThread() + "After join");

    }


    static Thread sleepyThread(int millis) {
        return new Thread(() -> {
            System.out.println(ANSI_GREEN + Thread.currentThread().getName());
            try {
                System.out.println(ANSI_GREEN + "Going to bed for " + millis + " millisecs...");
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                System.out.println(ANSI_GREEN + "I was interrupted while sleeping");
                return;
            }
            System.out.println(ANSI_GREEN + "I am awake now");
        });
    }


    static Thread interruptingThread() {
        return new Thread(() -> {
            System.out.println(ANSI_CYAN + Thread.currentThread().getName());
            for (int i = 0; i < 100000; i++) {
                System.out.println(ANSI_CYAN + i);
                if (Thread.interrupted()) {
                    System.out.println(ANSI_CYAN + "I've been interrupted");
                    return;
                }
            }
        });
    }

}