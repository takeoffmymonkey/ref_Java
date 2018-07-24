package _memory;

/* 5 ОБЛАСТЕЙ ДАННЫХ:
 * - [PERMGEN (METHOD AREA)]:
 *      - здесь хранятся:
 *          - мета-данные
 *          - код классов
 *          - пул строк
 *         - статические члены
 *      - 1 на JVM
 *      - может переполнится - OutOfMemoryError Perm space
 *      - не расширяется
 *      - todo с J8 стал METASPACE
 *          - как-то динамически расширяется за счет HEAP
 *
 * - [HEAP]:
 *      - здесь хранятся объекты
 *      - 1 на JVM
 *      - может переполнится - OutOfMemoryError Heap space
 *      - загрязнение кучи происходит, когда тип ссылки является наследником объекта по ссылке или
 *      не является родственником вообще
 *          - напр:
 *              - при ручном downcast
 *      - [OLD]
 *          - объекты, прошедшие несколько стадий сборки мусора в SURVIVOR
 *              - количество стадий задается в Threshold
 *      - [YOUNG]
 *          - [EDEN]
 *              - здесь создаются новые объекты
 *          - [SURVIVOR]
 *              - [S1]
 *              - [S2]
 *              - режимы областей S1 и S2:
 *                  - to-space
 *                      - пустой
 *                      - сюда перемещаются пережившие сборку объекты из EDEN и from-space
 *                  - from-space
 *                      - отсюда объекты копируются в to-space или в OLD (если прошли порог сборок)
 *
 * - [JAVA STACKS]
 *      - подеделен между потоками
 *      - каждый поток содержит фреймы стеков Java методов
 *          - здесь хранятся локальные переменные
 *              - примитивные
 *              - ссылки на объекты
 *                  - сами объекты хранятся в Heap
 *          - инструкции здесь не хранятся (а в PermGen)
 *              - т.е. длина метода не играет роли
 *      - может переполнится - StackOverflow
 *
 * - [PC REGISTER]
 *      - поделен между потоками
 *      - хранит информацию о следующих операциях у потока
 *      - todo переполнение?
 *
 * - [NATIVE METHODS STACKS]
 *      - поделен между потоками
 *      - то же, что и Java Stack, но для нативных методов
 *      - todo переполнение? */


public class Main {
    static int counter;
    static int counter10;

    static void testStackOverflow() {
        counter++;
        try {
            testStackOverflow();
        } catch (StackOverflowError e) {
            System.out.println(counter); // 11338
        }
    }

    static void testStackOverflow2() {
        long l0 = 0;
        long l1 = 0;
        long l2 = 0;
        long l3 = 0;
        long l4 = 0;
        long l5 = 0;
        long l6 = 0;
        long l7 = 0;
        long l8 = 0;
        long l9 = 0;
        counter10++;

        try {
            testStackOverflow2();
        } catch (StackOverflowError e) {
            System.out.println(counter10); // 5178
        }
    }

    static void testOutOfMemoryError() {
        try {
            Long[] arr = new Long[Integer.MAX_VALUE];
        } catch (OutOfMemoryError e) {
            System.out.println(e);
        }
    }

    // todo testOutOfMemoryPerm - как-то через class loader

    public static void main(String[] args) {

//        testStackOverflow();
//        testStackOverflow2();
        testOutOfMemoryError();
    }
}