package abstract_vs_interface;

import types_references_annotations.my_annotations.Ntrstn;

/* СХОДСТВО
 * - нельзя создать экземпляр
 * - могут иметь разные методы
 *      - с и без имплементации */


/* РАЗЛИЧИЯ
 * - абстрактный класс:
 *      - запрещено множественное наследование
 *      - поля - любой модификатор
 *      - методы - любой модификатор
 *
 * - интерфейс:
 *      - разрешено множественное наследование
 *      - поля - автоматически public static final
 *      - методы - автоматически public */


/* КОГДА ИСПОЛЬЗОВАТЬ АБСТРАКТНЫЙ КЛАСС
 * - нужно поделиться кодом с несколькими тесно связанными классами
 *
 * - ожидается, что наследующие классы имеют много общих методов или полей или требуют любой
 * модификатор доступа, кроме public
 *
 * - нужно объявить нестатичное или нефинализированное поле
 *      - так можно определять методы, которые могут иметь доступ и могут менять состояние объекта,
 *      к которому они принадлежат */


/* КОГДА ИСПОЛЬЗОВАТЬ ИНТЕРФЕЙС
 * - ожидается, что его имплементировать будут несвязанные классы
 *
 * - нужно уточнить поведение конкретного типа данных, но не важно, кто имплементирует его поведение
 *      - поэтому если есть метод, принимающий интерфейс, то работать с этим методом потенциально
 *      сможет любой класс, имплементирущий его, а не только иерархические наследники класса
 *
 * - нужно воспользоваться преимуществами множественного наследования */


@Ntrstn("Сходства абстрактного класса и интерфейса: нельзя создать экземпляр м могут содержать " +
        "реализованные и абстрактные методы")

@Ntrstn("Различия абстрактного класса и интерфейса. Абстрактный класс: запрещено множественное " +
        "наследование, у полей и методов может быть любой модификатор. Интерфейс: разрешено " +
        "множественное наследование, поля автоматически public static final, методы автоматически " +
        "public")

@Ntrstn("Абстрактный класс используется, когда нужно поделиться кодом с тесно связанными классами. " +
        "При это преимущества множественного наследования не важны или не нужны ограничения на " +
        "модификаторы доступа")

@Ntrstn("Интерфейс используется, когда его имплементируют не обязательно связанные классы, при этом " +
        "нужно только уточнить поведение конкретного класса и не важно, кто именно имплементирует " +
        "данное поведение (т.е. требуя тип интерфейса на вход метода нет такого жесткого " +
        "иерархического ограничения на передаваемый в него тип, как у абстрактного класса); или " +
        "нужно воспользоваться преимуществами множественного наследования")

abstract class Absrtact {
    /* НЕЛЬЗЯ СОЗДАВАТЬ ЭКЗЕМПЛЯР */
    /* МОЖЕТ ИМЕТЬ АБСТРАКТНЫЕ И НЕ АБСТРАКТНЫЕ МЕТОДЫ */
    /* ЗАПРЕЩЕНО МНОЖЕСТВЕННОЕ НАСЛЕДОВАНИЕ */
    /* У ПОЛЕЙ И МЕТОДОВ МОГУТ БЫТЬ ЛЮБЫЕ МОДИФИКАТОРЫ */
    public int field;
    private int field2;
    protected int field3;
    int field4;
    static int field5;
    final int field6 = 0;
    transient int field7;
    volatile int field8;

    native void meth();

    strictfp void meth2() {
    }

    synchronized void meth3() {
    }

    abstract void meth4();
}


interface Interface {
    /* НЕЛЬЗЯ СОЗДАВАТЬ ЭКЗЕМПЛЯР */
    /* ВСЕ ПОЛЯ АВТОМАТИЧЕСКИ PUBLIC STATIC FINAL */
    public static final int field = 0;

    /* ВСЕ МЕТОДЫ АВТОМАТИЧЕСКИ PUBLIC*/
    public void meth();

    /* МОЖЕТ ИМЕТЬ АБСТРАКТНЫЕ И НЕ АБСТРАКТНЫЕ МЕТОДЫ */
    default void defaultMeth() {
    }
}

public class Main {
}
