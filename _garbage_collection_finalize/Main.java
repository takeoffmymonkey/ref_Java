package _garbage_collection_finalize;

import types_references_annotations.my_annotations.Ntrstn;

/* РАЗНЫЕ JVM ИМЕЮТ РАЗНЫЕ АЛГОРИТМЫ СБОРКИ МУСОРА*/


/* JVM ОБЫЧНО ЗАПУСКАЕТ СБОРЩИК ПРИ НИЗКОМ УРОВНЕ СВОБОДНОЙ ПАМЯТИ В HEAP
 * - Если памяти недостаточно даже после восстановления, JVM генерирует исключение OutOfMemoryError
 *      - но перед этим запутится сборщик мусора как минимум 1 раз */


/* МОЖНО ЗАПРОСИТЬ ЗАПУСК СБОРЩИКА
 *  - но не принудительно задать это действие
 *  - System.gc();
 *  - или Runtime.getRuntime().gc();*/


/* ПРИГОДНОСТЬ ДЛЯ ЗАПУСКА СБОРЩИКА
 * - объект подлежит утилизации, когда он недоступен живому потоку:
 *      - Если переменная ссылочного типа, которая ссылается на объект, установлена в null, объект
 *      подлежит утилизации, в том случае, если на него нет других ссылок.
 *
 *      - Если переменная ссылочного типа, которая ссылается на объект, создана для ссылки на другой
 *      объект, объект подлежит утилизации, в том случае, если на него нет других ссылок.
 *
 *      - Объекты, созданные локально в методе, подлежат утилизации, когда метод завершает работу,
 *      если только они не экспортируются из этого метода
 *          - т.е, не возвращаются или не генерируются как исключение
 *
 *      - Объекты, которые ссылаются друг на друга, могут подлежать утилизации, если ни 1 из них не
 *      доступен живому потоку */


/* ФИНАЛИЗАЦИЯ (МЕТОД finalize())
 * - вызывается сборщиком мусора для объекта, когда тот готовится к удалению
 * - !! неизвестно, когда он будет вызван, и нормальная работа программы не должна от него зависеть
 * - здесь можно указать действия перед удалением
 *      - напр. освободить ресурс
 *          - лучше это делать либо через try-with-resources либо метод close() у ресурса
 * - описан в классе Object
 *      - значит есть для всех классов
 * - если в нем вызвать создание другого нового объекта, он не будет уничтожен
 * - исключения, возникшие в finalize(), игнорируется, а финализация объекта прекращается
 * - будет активирован только 1 раз за время существования объекта
 *      - или ни разу, если объект не будет подлежать удалению
 * - вызов метода System.runFinalizersOnExit (true) гарантирует, что метод finalize() будет вызван
 * до того, как программа завершит свою работу
 *      - но не рекомендуется
 *          - todo лучше вызвать Runtime.addShutdownHook()
 *              - а еще лучше ресурс.close() */


/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

/*ПОДХОДЫ К ОПРЕДЕЛЕНИЮ МУСОРА
 * - СЧЕТЧИК ССЫЛОК
 *      - сколько существует ссылок на данный объект
 *      - если 0, то объет может считаться мусором
 *      - сложно обеспечить точность счетчика
 *          - напр. 2 объекта ссылаются друг на друга, но ни 1 живой объект на них не ссылается
 *              - утечка памяти
 *      - HotSpot VM не использует данный метод
 *
 * - ОТСЛЕЖИВАНИЕ
 *      - все доступное с живого объекта - также живое
 *          - живые объекты - те, к которым можно добраться с корневых точек (GC ROOT)
 *      - остальное мусор
 *          - в таком случае до 2 ссылающихся друг на друга объектов невозможно добраться
 *              - значит они являются мусором
 *      - используется в HotSpot VM */


/* ТИПЫ КОРНЕВЫХ ТОЧЕК
 * - локальные переменные и параметры методов
 * - Java Потоки
 * - статические переменные
 * - ссылки из JNI */


/* Алгоритмы очистки памяти
 * - Copying Collectors:
 *      - память делится на 2 части:
 *          - from-space
 *          - to-space
 *          - когда заполняется from-space, требуется сборка мусора:
 *                  - приложение приостанавливается
 *                  - запускается сборщик мусора
 *                  - находятся живые объекты
 *                  - копируются в to-space
 *                  - после копирования from-space полностью очищается
 *                  - в итоге from-space и to-space меняются местами
 *          - плюс: память используется эффективно
 *          - минус: приложение приостанавливается на полный цикл сборки мусора
 *          - в чистом виде в HotSpot VM не используется
 *
 * - Mark-and-sweep:
 *      - Объекты аллоцируются в памяти
 *      - Нужно запустить GC
 *          - возникла нехватка памяти в области, где работает этот алгоритм
 *      - Приложение приостанавливается
 *      - Сборщик проходится по дереву объектов, помечая живые объекты
 *      - Сборщик проходится по всей памяти, находя все не отмеченные куски памяти, сохраняя их в
 *      "free list"
 *      - Когда новые объекты начинают аллоцироватся они аллоцируются в память доступную в "free
 *      list"
 *      - минусы:
 *          - Приложение не работает пока происходит сборка мусора
 *          - Время работы зависит от размеров памяти и количества объектов
 *          - Если не использовать "compacting", память будет использоваться не эффективно
 *      - в чистом виде в в HotSpot VM не используется */


/* АЛГОРИТМ HotSpot VM
 * - по статистике большинство приложений удовлетворяют 2 правилам ("weak generational hypothesis"):
 *      - Большинство аллоцированых объектов быстро становятся мусором
 *      - Существует мало связей между объектами, которые были созданы в прошлом и только что
 *      аллоцироваными объектами
 *
 * - Generational Garbage Collection
 *      - опирается на подход "weak generational hypothesis"
 *      - позволяет использовать разные алгоритмы для разных этапов сборки мусора */


/* todo 4 СБОРЩИКА МУСОРА В HotSpot VM
 * - Serial GC
 *      - 1 из первых в HotSpot VM
 *      - для 1-ядерных компьютеров
 *      - приложение приостанавливается на время работы сборщика
 *      - память делится на 3 части:
 *          - Young generation
 *              - размер памяти обычно малый
 *              - здесь аллоцируются объекты
 *              - предполагается, что будет мало объектов, переживших эту сборку
 *              - сборка происходит часто
 *              - сборка здесь называется Minor Garbage Collection:
 *                  - очень похожа на Copying Collectors
 *                      - только есть еще область Eden
 *                  - память делится на 2 части:
 *                      - Eden:
 *                          - здесь аллоцируются объекты
 *                          - после сборки мусора становится пустым
 *                          - выжившие переносятся в Survivor
 *
 *                      - Survivor:
 *                          - делится на 2 части:
 *                              - to-space
 *                                  - сюда переносятся живые объекты из Eden
 *                              - from-space
 *                                  - отсюда живые объекты копируются в "to-space" или в Old
 *                                  generation (если прошли количественный порог сборок)
 *                                  - т.е. у объектов здесь еще есть шанс стать мусором при
 *                                  следующей сборке
 *
 *                  - на время сборки приложение приостанавливается:
 *                      - Eden и from-space очищаются, т.к. в них остался только мусор
 *                      - to-space и from-space меняются местами
 *                      - Eden и to-space становятся полностью чистыми
 *                      - в from-space остаются пока выжившие объекты
 *                      - картинка 1 (до) http://ggenikus.github.io/images/YoungGen_1.jpg
 *                      - картинка 2 (после) http://ggenikus.github.io/images/YoungGen_2.jpg
 *
 *          - Old generation
 *              - размер памяти обычно больше, чем Young generation
 *              - сюда помещаются объекты, пережившие Minor Garbage Collection
 *              - заполняется медленно, т.к. большинство объектов обычно живут не долго
 *              - сборка происходит нечасто и медленно
 *              - сборка здесь называется Major Garbage Collection
 *                  - похожа на Mark-and-sweep
 *                      - но есть процедура compacting
 *                          - для более эффективного использования памяти
 *                          - живые объекты перемещаются к началу Old generation
 *                              - т.о. мусор остается в конце
 *                              - сохраняется указатель на последний живой объект
 *                              - указатель дальше сдвигается по ходу добавления новых объектов
 *                      - запускается сборщик
 *                      - приостанавливается работа приложения
 *                      - сборщик идет по дереву объектов в Old generation и помечает живые объекты
 *                      - сборщик идет по всей памяти и находит все не отмеченные куски памяти
 *                          - они помечаются как мусор
 *                      - все живые объекты сдвигаются к началу Old generation
 *                          - мусор становится 1 куском памяти сразу после живых объектов
 *                      - работа возобновляется
 *                      - картинка 1 (до) http://ggenikus.github.io/images/OldGen_1.jpg
 *                      - картинка 2 (после) http://ggenikus.github.io/images/OldGen_2.jpg
 *
 *          - Permanent generation
 *              - здесь хранятся метаданные, классы, статические члены, интернированные строки и т.д.
 *
 *      - необходимо выявить ссылки на объекты в Old generation, которые ссылаются на объекты в
 *      Young generation (old-to-new)
 *          - существует структура "card table":
 *              - массив с 1-байтной ячейкой
 *                  - каждая соответствует куску памяти (карте) в Old generation
 *          - когда в каком-то поле объекта обновляется ссылка, то в "card table" нужная карта
 *          помечается как грязная
 *              - для это как раз нужна 1-байтная ячейка
 *              - теперь в Minor Garbage Collection для выявления ссылок "old-to-new" сканируется
 *              не весь Old generation, а только объекты в грязных картах
 *
 * - Parallel GC
 *      - то же самое, но для компьютеров с несколькими ядрами
 *      - GC выполняется в нескольких потоках
 *      - является дефолтным
 *
 * - CMS (concurrent mark-and-sweep) GC
 *      - конкурентная (concurrent) реализацией Mark-and-sweep
 *          - процедуры mark и remark вызывают остановку работы
 *          - процедура sweep происходит одновременно с работой приложения
 *      - не может применяться к Old generation
 *      - пытается минимизировать количество остановок приложения при сборке
 *
 * - G1 GC (garbage first)
 *      - разделяет память на множество мелких фрагментов
 *      - в каждом фрагменте подсчитывается количество мертвых объектов
 *      - сборка мусора применяется в первую очередь к тем фрагментам, где больше мусора
 *      - используется тот же mark-and-sweep compact*/


/* ЧЕМ БОЛЬШЕ MINOR ОТНОСИТЕЛЬНО MAJOR, ТЕМ ЛУЧШЕ */


/* ПОЛЕЗНЫЕ НАСТРОЙКИ СБОРКИ
 * - установка режима сборки мусора:
 *      - -XX:+UseSerialGC
 *      - -XX:+UseParNewGC
 *      - -XX:+UseConcMarkSweepGG
 *      - -XX:+UseParallelGC/-XX:+UseParallelOldGC
 *      - -XX:+UseG1GC
 *
 * - настройки памяти (лучше увеличивать обе):
 *      - -XX:+MaxHeapSize
 *      - -XX:+PermSize
 *
 * - настройки времени сборки:
 *      - -XX:+MaxGCPauseMillis
 *      - -XX:+GCTimeRatio*/


/*JVISUALVM или JMC
 * - посмотреть на графиках работу JVM*/


@Ntrstn("не известно, когда будет вызван finalize(), поэтому работа программы не должна от него " +
        "зависеть")
@Ntrstn("исключения, возникшие в finalize(), игнорируется, а финализация объекта прекращается")
@Ntrstn("Можно на графиках посмотреть работу JVM при помощи JVISUALM или JMC")
public class Main {
    public static void main(String[] args) {

        /* ~~~~~~~~~~~~~~ПРИГОДНОСТЬ ДЛЯ ЗАПУСКА СБОРЩИКА~~~~~~~~~~~~~~*/
        Object o1 = new Integer(3);
        Object o2 = new String("Tutorial");
        o1 = o2; // объект new Integer(3) готов к удалению, т.к. на объект никто не ссылается
        o2 = null; // new String("Tutorial") не подлежит удалению, т.к. на него теперь ссылается o1


        /*~~~~~~~~~~~~~~ВЫЗОВ МЕТОДА FINALIZE ПЕРЕД ОЧИСТКОЙ~~~~~~~~~~~~~~*/
        Main m = new Main();
        m = null;

        /*УДОСТОВЕРИТСЯ, ЧТО FINALIZE БУДЕТ ВЫЗВАН */
        System.runFinalizersOnExit(true); // deprecated
        Runtime r = Runtime.getRuntime(); // более правильный
        r.addShutdownHook(Thread.currentThread()); // todo вызывает исключение IllegalArgumentException: Hook already running


        /*~~~~~~~~~~~~~~ЗАПРОС ВЫЗОВА СБОРЩИКА (1 ИЗ ВАРИАНТОВ)~~~~~~~~~~~~~~*/
        System.gc();
        Runtime.getRuntime().gc();
    }

    /* FINALIZE */
    protected void finalize() { // будет вызван до удаления объекта
        System.out.println("Don't kill me!");

        /*ИСКЛЮЧЕНИЕ И ДАЛЬНЕЙШИЙ КОД ИГНОРИРУЕТСЯ*/
        int i = 5 / 0;
        System.out.println("I beg you...");
    }
}