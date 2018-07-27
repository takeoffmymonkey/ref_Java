package oop_inheritance;

import _types_references_annotations.my_annotations.Ntrstn;

/* НАСЛЕДОВАНИЕ
 * - модель отношения объектов "является"
 * - чтобы избежать дублирования кода
 * - один из принципов ООП */


/* ВСЕ КЛАССЫ ПРОИСХОДЯТ ОТ OBJECT
 *  - в отсутствие явного родителя, им фактически является Object*/


/* НАСЛЕДОВАНИЕ ЧЛЕНОВ СУПЕРКЛАССА
 * - члены класса - то, что может быть унаследовано:
 *      - поля
 *      - методы
 *      - вложенные классы
 *      - вложенные интерфейсы (это статический член)
 *      - перечисления (это статический член)
 *
 * - наследуются:
 *      - т.е. прямой доступ по имени
 *          - все public и protected члены
 *          - default - если в том же пакете
 *          - при этом:
 *              - финализированные члены:
 *                  - нельзя изменить значение
 *                  - нельзя переопределить метод
 *                  - нельзя унаследовать класс
 *                      - в т.ч. статические финализированные
 *                      - все методы внутри автоматически финализированы
 *              - статические члены:
 *                  - можно перекрыть
 *                  - нельзя переопределить метод
 *              - абстрактные методы:
 *                  - в т.ч. интерфейсные
 *                  - требуют реализации
 *
 * - не наследуются:
 *      - т.е. нет прямого доступа по имени
 *      - конструкторы
 *          - но могут быть вызваны из подкласса
 *              - через super()
 *      - private члены
 *          - даже private static
 *              - т.е. 1 для всех объектов этого класса
 *          - могут быть доступны через методы и вложенные классы, которые с ними работают
 *          - не могут быть при этом абстрактными */


@Ntrstn("Члены класса могут быть одновременно private и static: private - значит доступен только в " +
        "пределах класса, static - значит доступен только в 1 экземпляре")
abstract class Parent {
    /*~~~~~~~~~~~~~~~~~~~~~~~~~НАСЛЕДУЮТСЯ~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /* ПОЛЯ */
    int parentField;
    static int parentStaticField;
    final int parentFinalField = 0;
    static final int parentStaticFinalField = 0;


    /* МЕТОДЫ */
    void parentMeth() {
    }

    static void parentStaticMeth() {
    }

    final void parentFinalMeth() {
    }

    static final void parentStaticFinalMeth() {
    }

    abstract void parentAbstractMeth();


    /* ВЛОЖЕННЫЕ КЛАССЫ */
    class ParentInner {
    }

    static class ParentStaticInner {
    }

    final class ParentFinalInner { // нельзя расширить
    }

    static final class ParentStaticFinalInner { // нельзя расширить
    }


    /* ИНТЕРФЕЙСЫ */
    interface ParentInterface {
    }


    /* ПЕРЕЧИСЛЕНИЯ */
    enum ParentEnum {
        ONE
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~НЕ НАСЛЕДУЮТСЯ~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /* КОНСТРУКТОРЫ */
    public Parent() {
    }


    /* ВСЕ ПРИВАТНЫЕ ЧЛЕНЫ */
    /* ПОЛЯ */
    private int parentPrivateField;
    private static int parentPrivateStaticField;
    private final int parentPrivateFinalField = 0;
    private static final int parentPrivateStaticFinalField = 0;

    /* МЕТОДЫ */
    private void parentPrivateMeth() {
    }

    private static void parentPrivateStaticMeth() {
    }

    private final void parentPrivateFinalMeth() {
    }

    private static final void parentPrivateStaticFinalMeth() {
    }

    /* ВЛОЖЕННЫЕ КЛАССЫ */
    private class ParentPrivateInner {
    }

    private static class ParentPrivateStaticInner {
    }

    private final class ParentPrivateFinalInner { // нельзя расширить
    }

    private static final class ParentPrivateStaticFinalInner { // нельзя расширить
    }

    /* ИНТЕРФЕЙСЫ */
    private interface ParentPrivateInterface {
    }

    /* ПЕРЕЧИСЛЕНИЯ */
    private enum ParentPrivateEnum {
        ONE
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~ФИНАЛИЗИРОВАННЫЕ КЛАССЫ НЕЛЬЗЯ РАСШИРИТЬ~~~~~~~~~~~~~~~~~~~~~~~~~*/
}


class Child extends Parent {
    @Override
    void parentAbstractMeth() {
        /*~~~~~~~~~~~~~~~~НАСЛЕДУЮТСЯ~~~~~~~~~~~~~~~~*/
        /* ~~~~~ПОЛЯ~~~~~ */
        int i = parentField;
        int i1 = parentStaticField;
        int i2 = parentFinalField;
        int i3 = parentStaticFinalField;

        /* ~~~~~МЕТОДЫ~~~~~ */
        parentMeth();
        parentStaticMeth();
        parentFinalMeth();
        parentStaticFinalMeth();
        parentAbstractMeth();

        /* ~~~~~ВЛОЖЕННЫЕ КЛАССЫ~~~~~ */
        ParentInner p = new ParentInner();
        ParentStaticInner p1;
        ParentFinalInner p2 = new ParentFinalInner();
        ParentStaticFinalInner p3;

        /* ~~~~~ИНТЕРФЕЙСЫ~~~~~ */
        ParentInterface p4 = new ParentInterface() {
        };

        /* ~~~~~ПЕРЕЧИСЛЕНИЯ~~~~~ */
        ParentEnum e = ParentEnum.ONE;


        /*~~~~~~~~~~~~~~~~НЕ НАСЛЕДУЮТСЯ~~~~~~~~~~~~~~~~*/
        /* ~~~~~КОНСТРУКТОРЫ~~~~~ */

        /* ~~~~~~~~ВСЕ ПРИВАТНЫЕ ЧЛЕНЫ~~~~~~~~ */
        /* ~~~~~ПОЛЯ~~~~~ */
//        int i4 = parentPrivateField;
//        int i5 = parentPrivateStaticField;
//        int i6 = parentPrivateFinalField;
//        int i7 = parentPrivateStaticFinalField = 0;

        /* ~~~~~МЕТОДЫ~~~~~ */
//        parentPrivateMeth();
//        parentPrivateStaticMeth();
//        parentPrivateFinalMeth();
//        parentPrivateStaticFinalMeth();

        /* ~~~~~ВЛОЖЕННЫЕ КЛАССЫ~~~~~ */
//        ParentPrivateInner p5;
//        ParentPrivateStaticInner p6;
//        ParentPrivateFinalInner p7;
//        ParentPrivateStaticFinalInner p8;

        /* ~~~~~ИНТЕРФЕЙСЫ~~~~~ */
//        ParentPrivateInterface p9;

        /* ~~~~~ПЕРЕЧИСЛЕНИЯ~~~~~ */
//        ParentPrivateEnum e2;


        /* ~~~~~~~~ФИНАЛИЗИРОВАННЫЕ КЛАССЫ НЕЛЬЗЯ РАСШИРИТЬ~~~~~~~~ */
//        class ParentFinalInner2 extends ParentFinalInner {}
//        class ParentStaticFinalInner2 extends ParentStaticFinalInner {}
    }
}

public class Main {
}