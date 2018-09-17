package __concurrency;

import types_references_annotations.my_annotations.Ntrstn;

import static __concurrency.ThreadsColor.ANSI_BLUE;
import static __concurrency.ThreadsColor.ANSI_CYAN;
import static __concurrency.ThreadsColor.ANSI_GREEN;

/* PROCESS, THREAD, CONCURRENCY (MULTI-THREADING)
 * - даже если 1 ядро у процессора, его время делится между процессами и ветками через фичу ОС
 * "time slicing"
 *
 * - процесс:
 *      - имеет свою среду исполнения и набор run-time ресурсов
 *      - обычно 1 апп - 1 процесс
 *          - но может быть и набор коммуницирующих процессов (Inter Process Communication (IPC))
 *      - у каждого процесса есть минимум 1 ветка
 *      - большинство имплементаций JVM работают как 1 процесс
 *          - апп может создавать дополнительные процессы при помощи объекта ProcessBuilder
 *
 * - ветки (потоки): "легковесные" процессы
 *      - также предоставляют среду исполнения, но их создание требует меньше ресурсов, чем для
 *      процесса
 *      - существуют внутри процесса и делят его ресурсы между собой
 *          - из-за этого могут возникать конфликты
 *      - изначально есть только ветка main
 *          - другие создаются из нее
 *              - каждая ветка ассоциирована с экземпляром Thread
 *          - (также есть системные ветки (напр. для управления памятью))
 *
 * - многопоточность: способность программы работать с несколькими потоками одновременно */


/* ПРОБЛЕМЫ МНОГОПОТОЧНОСТИ, СИНХРОНИЗАЦИЯ И ОТНОШЕНИЕ HAPPENS-BEFORE */


/* 2 СТРАТЕГИИ ИСПОЛЬЗОВАНИЯ ОБЪЕКТА THREAD
 * - 1: создание объекта Thread каждый раз при необходимости выполнения несинхронной задачи и ручное
 * управление созданным объектом
 *
 * - 2: создание и передача объектов Thread в Executor, чтобы абстрагировать управление ветками
 * (от остальной части приложения)
 *      - относится к высокоуравнему API */


/* 2 СПОСОБА СОЗДАНИЯ И ЗАПУСКА ПОТОКА + МЕХАНИЗМ ЗАПУСКА:
 * - 1: наследовать Thread и переопределить его метод run (изначально ничего не делает)
 *      - легче исопльзовать в простых приложениях
 *      - метод run присутствует из-за имплементации классом Thread интерфейса Runnable
 *
 * - 2: для своего класса имплементировать Runnable и его метод run, и передать экземпляр в
 * конструктор Thread (Runnable)
 *      - предпочтительней, т.к:
 *          - класс сможет наследоваться не обязательно от Thread
 *          - в высокоурованевом API используются объекты Runnable
 *
 * - после этого запустить на экземпляре Thread метод start:
 *      - JVM вызывает метод run на Runnable:
 *          - теперь есть 2 ветки, работающие одновременно:
 *              - текущая ветка (вернулась с вызова метода start)
 *              - новосозданная ветка, которая исполняет свой метод run
 *      - запрещено запускать ветку более 1 раза */



/* ФУНКЦИОНАЛЬНЫЙ ИНТЕРФЕЙС RUNNABLE: run()
 * - общий протокол для объектов, которые хотят исполнять свой код (из метода run) в отдельной ветке
 *      - класс имплементирует Runnable, создается экземпляр Thread и ему передается в качестве цели
 *      данный экземпляр Runnable
 *          - начало ветки приводит к вызову метода run на принадлежащем экземпляру Thread объекту
 *          Runnable
 *      - напр. Thread имплементирует Runnable
 *
 * - позволяет работать в ветке без обязательного наследования от Thread
 *      - (если достаточно переопределить только run(), а не другие методы Thread) */


/* КЛАСС THREAD - ВЕТКА ИСПОЛНЕНИЯ В ПРОГРАММЕ
 * - public class Thread extends Object implements Runnable
 *
 * - свойства:
 *      - имеет имя(setName):
 *          - более 1 ветки могут иметь 1 имя
 *          - если имя не указано, генерируется новое
 *
 *      - имеет приоритет(setPriority): JVM старается исполнять их в порядке, соответствующем приоритету
 *          - наследуется ветками-наследниками, но можно потом изменить
 *
 *      - может быть демоном или пользовательской(setDaemon): JVM может завершить работу только, если
 *      оставшиеся работающие ветки являются демонами
 *          - статус наследуется ветками-наследниками, но можно потом изменить
 *
 * - типичные манипуляции:
 *      - sleep (временное прекращение выполнения ветки) - если нужно:
 *          - предоставить процессорное время другим веткам
 *          - изменить темп выполнения
 *              - например, подождать другую "тяжелую" ветку
 *
 *      - join (присоединение, т.е. текущая ветка для продолжения работы ждет завершения указанной)
 *         - один из механизмов синхронизации (установка отношения Н-В)
 *         - можно также указать время ожидания, вместо завершения ветки (но все равно вызывается на
 *         объекте другой ветки)
 *              - например, чтобы периодически проверять статус выполняющейся ветки и периодически
 *              выводить сообщения о ней с главной ветки
 *
 *      - interrupt ("пожелание", чтобы ветка остановилась и запустила механизм своего прерывания):
 *          - ветка должна иметь такой механизм
 *              - например, выбросить InterruptedException
 *                  - так делают многие методы, прерывая свое исполнение
 *              - если ветка работает долго без выбрасывания InterruptedException, она должна
 *              периодически проверять флаг прерванности (Thread.interrupted (проверяет + обнуляет
 *              статус); устанавливается извне методом interrupt) и продолжать с учетом данного условия
 *                  - если флаг установлен, то можно, например, выбросить InterruptedException
 *                      - позволяет коду, обрабатывающему прерывания, быть сосредоточенным в catch
 *              - по договоренности: при выбрасывании InterruptedException обнуляется статус
 *              прерванности
 *              - todo если ветка заблокирована методами wait (класс Object), join, sleep, то статус
 *              прерванности обнуляется и будет получено InterruptedException
 *
 * - JVM исполняет ветки, пока:
 *      - все ветки, не являющиеся демонами, не умрут или вернутся из вызова run
 *      - todo не будет выброшено исключение, которое выходит за метод run
 *      - todo не был вызван метод exit класса Runtime и security manager разрешил произвести операцию exit
 *
 * - (другие) основные методы:
 *      - activeCount(): приблизительное число активных веток у группы текущей ветки и их подгрупп
 *      - checkAccess(): проверка возможности менять текущую ветку работающей веткой
 *      - clone(): выбрасывает CloneNotSupportedException, т.к. Thread не стоит клонировать
 *      - currentThread(): ссылка на текущий выполняющийся объект ветки
 *      - dumpStack(): вывод stack trace текущей ветки в standard error stream.
 *      - enumerate(Thread[] tarray): вернуть в массиве копии (ссылки?) всех веток и подветок у данной
 *      - getAllStackTraces(): map of stack traces всех живых веток
 *      - getId(): идентификатор ветки
 *      - getState(): состояние данной ветки
 *      - getThreadGroup(): группа ветки, которой принадлежит данная ветка
 *      - holdsLock(Object obj): проверка на удержание монитора веткой для указанного объекта
 *      - isAlive(): проверка живости данной ветки
 *      - toString(): имя ветки, приоритет и группа */




/* НИЗКОУРОВНЕВЫЙ API */


/* ВЫСОКОУРАВНЕВЫЙ API */
/*java.util.concurrent*/

















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
        "отдельной, в то же время start выполняется в новой ветке и единожды для соотв. объекта Thread")

@Ntrstn("Метод run в Thread есть потому, что он и сам имплементирует Runnable")


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