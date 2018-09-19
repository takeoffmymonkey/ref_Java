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
 *      - существуют внутри процесса и делят его ресурсы между собой (находятся в Heap)
 *          - из-за этого могут возникать конфликты
 *      - имеет свой Stack для локальных переменных и только она имеет к нему доступ
 *      - изначально есть только ветка main
 *          - другие создаются из нее
 *              - каждая ветка ассоциирована с экземпляром Thread
 *          - (также есть системные ветки (напр. для управления памятью))
 *
 * - многопоточность: способность программы работать с несколькими потоками одновременно */


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
 *      - имеет уникальный идентификационный номер (getId)
 *          - сохраняется на время жизни и может быть переиспользован после смерти
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
 *      - currentThread(): ссылка на текущий выполняющийся объект ветки
 *      - dumpStack(): вывод stack trace текущей ветки в standard error stream.
 *      - enumerate(Thread[] tarray): вернуть в массиве копии (ссылки?) всех веток и подветок у данной
 *      - getAllStackTraces(): map of stack traces всех живых веток
 *      - getThreadGroup(): группа ветки, которой принадлежит данная ветка
 *      - holdsLock(Object obj): проверка на удержание монитора веткой для указанного объекта
 *      - isAlive(): проверка живости данной ветки
 *      - toString(): имя ветки, приоритет и группа */


/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~ПРОБЛЕМЫ МНОГОПОТОЧНОСТИ И СИНХРОНИЗАЦИЯ~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/* ПРОБЛЕМЫ РАБОТЫ С ОБЩИМ РЕСУРСОМ
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
 * 2 - Memory consistency errors (+ race condition): нет гарантий последовательности выполнения
 * инструкций, поэтому при чтении значения можно получить неактульные данные
 *      - процессор, компилятор и JIT из-за оптимизации выполняют инструкции непоследовательно, а
 *      какие-то значения могут кешироватся
 *          - race condition: ошибка проектирования, при которой работа аппа зависит от
 *          последовательности выполнения частей кода
 *          - компилятору даже разрешено перевести while(!stop) в while(true), если значение stop
 *          (при этом не volatile!) в данной ветке не меняется, и если его изменит другая ветка, то
 *          изменение не будет видно, возможно, никогда!
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
 *                  - todo нельзя менять сам объект при захвате, иначе другие ветки теперь будут
 *                  синхронизироваться на другой объект (и у монитора не будет эксклюзивности)
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
 *                  - только не String, потому что 2 объекта могут быть на самом деле 1 из-за пула!
 *          - синхронизированных конструкторов не бывает: только создающая его ветка имеет к нему
 *          доступ при создании
 *
 * - атомарные операции: одна операция над объектом -> следующая операция над объектом (операция
 * либо совершается полностью, либо не совершается совсем, т.е. в процессе операции нет видимых
 * побочных эффектов)
 *      - запись ссылочных переменных/примитивов (кроме long и double) -> чтение значения
 *      - volatile: запись любого значения (в т.ч. long и double) -> чтение значения
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
 *      - в java.util.concurrent есть атомарные классы разных (не всех) примитивов и массивов (чего
 *      сложно добиться простым кодом), которые позволяют производить над ними несколько операций,
 *      как атомарные
 *          - todo решают проблемы Memory consistency?
 *          - например, атомарная инкрементация - 1 операция, а не 3, поэтому не будет Thread
 *          interference при инкрементации
 *
 * - (wait/notify) код до защищенного блока -> код после защищенного блока:
 *      - защищенный блок: используется для коммуникации между ветками (также при помощи монитора)
 *          1. ветка, имеющая монитор, доходит до вызова wait и там останавливается, освобождая
 *          монитор
 *              - wait должен быть внутри лупа, который проверяет какое-то свое условие, так как
 *              вызов notifyAll только сообщает о наступлении "важного" события, но не какого именно
 *          2. другая ветка, получившая монитор, может вызвать метод notifyAll и после этого
 *          освобождает монитор
 *          3. первая ветка получает уведомление о наступлении "важного" события, запрашивает
 *          монитор и начинает с сразу после wait
 *              - например, снова проверит условие лупа, и если оно изменилось, то можно пропустить
 *              луп и выполнять код, идущий после него
 *          - метод notify не позволяет указать ветку, которую нужно разбудить, и используется в
 *          сильно параллельных аппах - где много веток и все делают схожие действия, и неважно,
 *          какая из веток проснется
 *          - может использоваться в паттерне producer-consumer, но для него лучше использовать
 *          существующие структуры данных из фреймворка коллекций
 *
 * - высокоуравневые:
 *      - действия над объектом до помещения его в concurrent коллекцию -> действия по доступу или
 *      удалению этого элемента из коллекции
 *      - действия в ветке до помещения Runnable в Executor -> начало его выполнения
 *          - аналогично для помещения Callable в ExecutorService
 *      - действия до "освобождающего" синхронизированного метода -> действия после успешного
 *      "запрашивающего" метода для того же синхронизируемого объекта в другой ветке
 *          - пример освобождающих методов: Lock.unlock, Semaphore.release, и
 *          CountDownLatch.countDown()
 *          - пример запрашивающих методов: Lock.lock, Semaphore.acquire, Condition.await, и
 *          CountDownLatch.await
 *      - для пары веток, которые обмениваются объектами через Exchanger, действия до exchange() в
 *      одной ветке -> действия после exchange() в другой ветке
 *      - действия до CyclicBarrier.await и Phaser.awaitAdvance (и их вариантов) -> барьерное
 *      действие -> действия, следующие за успешным возвращением из соответвующего await в других
 *      ветках
 *
 * - неизменяемые объекты: не могут быть повреждены при thread interference или наблюдаться с
 * измененным состоянием
 *      - влияние от создания объекта часто переоценивается и может быть компенсировано удобствами:
 *          - уменьшенный оверхед благодаря сбору мусора
 *          - сокращение кода, который защищает изменяемые объекты от повреждения
 *      - правила проектрирования:
 *          - не предоставлять сеттеры
 *          - все поля final private
 *          - запретить переопределение
 *          - если поля экземпляров ссылаются на изменяемые объекты, не позволять этим объектам
 *          изменяться:
 *              - не предоставлять методов, которые меняют изменяемые объекты
 *              - не делится ссылками на изменяемые объекты:
 *                  - не хранить ссылки на внешние, изменяемые объекты, передаваемые в конструктор
 *                  - создавай копии, когда нужно */


/* ПРОБЛЕМЫ СИНХРОНИЗАЦИИ (ПРОБЛЕМЫ ЖИЗНЕННОГО ЦИКЛА ВЕТОК (СОПЕРНИЧЕСТВО))
 * - deadlock: ветка, для продолжения работы, ждет ресурс, занятый другой веткой, а та, для
 * продолжения работы, ждет ресурс, занятый первой веткой
 *      - выполнение прекращается
 *      - например, 2 веткам нужно 2 ресурса. Первая захватила первый ресурс, вторая второй, и ждут
 *      освобождения следующего
 *
 * - livelock (редко): выполнение не прекращается, но постоянно выполняется бесполезная работа
 * (реакция на действие другой ветки), не способная снять блок
 *      - например: двое встречаются лицом к лицу. Каждый пытается посторониться, но они не расходятся,
 *      а несколько секунд сдвигаются в одну и ту же сторону
 *
 * - starvation (редко): ветке требуется доступ к ресурсу, но он недоступен из-за того, что очень
 * часто занят другой "жадной" веткой
 *      - например: у объекта есть синхронизированный метод, который долго исоплняется. Если ветка
 *      часто его вызывает, то другие ветки, которым тоже нужен частый синхронизированный доступ,
 *      часто будут заблокированы */


/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ВЫСОКОУРАВНЕВЫЙ API~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
/* ПАКЕТ JAVA.UTIL.CONCURRENT: УТИЛИТНЫЕ КЛАССЫ МНОГОПОТОЧНОСТИ
 * - Executors:
 *      - интерфейсы:
 *          - Executor: простой интерфейс, определяющий кастомные thread-like подсистемы, включая
 *          пулы потоков, асинхронный I/O и легковесные тасковые фреймворки. В зависимости от
 *          используемого конкретного класса, кот. имплементирует Executor, таски могут исполняться
 *          в новых ветках, в существующих или в ветке вызывающей execute, и могут выполняться как
 *          одновременно, так и последовательно
 *          - ExecutorService: более сложный фреймворк: управляет очередями и планированием тасков
 *          и позволяет контролированное завершение
 *              - ScheduledExecutorService: подинтерфейс для отложенного или периодического выполения.
 *              - предоставляет методы для организации асинхронного выполнения объектов Callable
 *                  - аналог Runnable, но возвращает результат
 *          - Future: возвращает результат Callable, позволяет определять, завершилось ли исполнение,
 *          предоставляет возможность отменять выполнение
 *          - RunnableFuture: Future с методом run, который при выполении устанавливает свои результаты.
 *
 *      - имплементации:
 *          - ThreadPoolExecutor и ScheduledThreadPoolExecutor: предоставляют настраиваемые и гибкие
 *          пулы потоков
 *          - Executors: фабричные методы для наиболее часто используемых конфигураций Executor и
 *          некоторые методы для их использования
 *          - FutureTask: простая расширяемая имплементация Future
 *          - ExecutorCompletionService: помогает при координации обработки групповых тасков
 *          - ForkJoinPool: предоставляет Executor для обработки экземпляров ForkJoinTask
 *
 * - Queues:
 *      - ConcurrentLinkedQueue: эффективная масштабируемая thread-safe неблокирующая FIFO очередь.
 *      - ConcurrentLinkedDeque: похоже, но дополнительно поддерживает интерфейс Deque.
 *      - интерфейс BlockingQueue: определяет блокирующие версии put и take
 *          - имплементации: LinkedBlockingQueue, ArrayBlockingQueue, SynchronousQueue,
 *          PriorityBlockingQueue, и DelayQueue
 *              - для разных контекстов: producer-consumer, messaging, parallel tasking и т.д.
 *          - подинтерфейс BlockingDeque: поддержка FIFO и LIFO (stack-based)
 *              - имплементация: LinkedBlockingDeque
 *      -  TransferQueue (и имплементация LinkedTransferQueue): синхронный медод transfer, который
 *      позволяет продюсеру блокировать ожидающего его консумера
 *
 * - Timing:
 *       - TimeUnit: предоставляет разные гранулярности (включая наносекунды) для операций таймаута
 *       или указания времени
 *
 * - Synchronizers:
 *      - Semaphore: классическая тулза многопоточности
 *      - CountDownLatch: очень простая утилита для блокирования пока происходит указанное число
 *      сигналов, событий или условий.
 *      - CyclicBarrier: resettable multiway точка синхронизации.
 *      - Phaser: более гибкая форма барьера, которая может использоваться для контролирования phased
 *      вичислений между несколькими ветками.
 *      - Exchanger: позволяет 2 веткам обмениваться объектами в rendezvous точке.
 *
 * - многопоточные коллекции (помимо Queue):
 *      - ConcurrentHashMap, ConcurrentSkipListMap, ConcurrentSkipListSet, CopyOnWriteArrayList,
 *      CopyOnWriteArraySet
 *          - предпочтительней, чем синхронизованные версии коллекций
 *              - thread-safe, но не управляются 1 монитором
 *      - их итераторы и сплитераторы предоставляют weakly consistent, а не fast-fail перебор */


/* ПАКЕТ JAVA.UTIL.CONCURRENT.LOCKS: РАСШИРЕНИЕ СТАНДАРТНОГО ФУНКЦИОНАЛА МОНИТОРОВ И ОЖИДАНИЯ
 * - интерфейс Lock: поддержка блокировки при помощи другой семантики, и которая может использоваться
 * в неблокирующем контексте, включая алгоритмы hand-over-hand и lock reordering
 *      - ReentrantLock: основная имплементация
 * - интерфейс ReadWriteLock: можно использовать общий замок между читателями, но эксклюзивный для
 * записи
 *      - ReentrantReadWriteLock: единственная имплементация
 * - интерфейс Condition: условные переменные, ассоциированные с Lock
 *      - как у Object.wait, но шире функционал
 *          - например, несколько разных условий
 * - класс AbstractQueuedSynchronizer: суперкласс-удобство для определения мониторов и других
 * синхронизаторов, которые полагаются на очередь блокированных потоков
 *      - унаследован от AbstractOwnableSynchronizer: помогает записывать поток, который удерживает
 *      замок.
 * - класс LockSupport: более низкоуравневый, для своих имплементаций */


/* ПАКЕТ JAVA.UTIL.CONCURRENT.ATOMIC: АТОМИЧЕСКИЕ ОПЕРАЦИИ НАД ПЕРЕМЕННЫМИ
 * - по сути классы в пакете расширяет понятие volatile значений, полей и элементов массивов до таких,
 * что также предоставляют атомическую условную операцию обновления в форме boolean
 * compareAndSet(expectedValue, updateValue);
 *      - метод не является общим заменителем блокировки, а применяется только когда критические
 *      апдейты для объекта заключаются в единственную переменную
 * - в первую очередь предназначены как строительные элементы для имплементаций неблокирующий
 * структур данных
 * - не являются общими заменителями для оберток, так как не определяют методов тех классов
 * - классы AtomicBoolean, AtomicInteger, AtomicLong, и AtomicReference: доступ и обновление
 * переменной соответствующего типа, а также другие удобства, например инкрементация
 * - классы Updater: можно использовать для получения compareAndSet операций для любобо выбранного
 * volatile поля любого класса
 * - классы AtomicIntegerArray, AtomicLongArray, and AtomicReferenceArray поддерживают volatile
 * доступ к элементам массива, что обычным способом не поддерживается */


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
@Ntrstn("бессмысленность синхрониции конструкторов и локальных переменных")

@Ntrstn("Существую 2 аспекта безопасной многопоточности - порядок исполнения кода (в т.ч. порядок " +
        "инструкций) и видимость памяти")

@Ntrstn("Проблемы отладки многопоточных приложений сложны из-за того, что ситуации, способные " +
        "вызвать проблемы, возникают не всегда, а, скорее даже, крайне редко")
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