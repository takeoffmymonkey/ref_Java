package oop_inheritance;

import types_references_annotations.my_annotations.Ntrstn;

/* ФИНАЛИЗАЦИЯ
 * - ЛИБО ЗАПРЕЩАЕТ НАСЛЕДОВАНИЕ
 * - ЛИБО ЗАПРЕЩАЕТ ПЕРЕОПРЕДЕЛЕНИЕ
 * - определяется модификатором final */


/* ABSTRACT VS FINAL VS STATIC
 * - abstract:
 *      - классы: для создания объекта требуется реализация всех его абстрактных методов
 *          - при помощи переопределения
 *          - в случае с вложенным статическим - создание объекта не требуется
 *      - методы: требует объявления сущности как абстрактной
 *      - поля/переменные: не могут быть абстрактными
 *
 * - final:
 *      - классы: запрещает наследование класса
 *      - методы: при наследовании запрещает переопределение
 *      - поля/переменные: запрещают присваивание других значений или объектов
 *
 * - static:
 *      - классы: не используется с классами верхнего уровня
 *          - вложенные статические: ведет себя как класс верхнего уровня
 *      - методы: доступны без экземпляра класса
 *      - поля: доступны без экземпляра класса */


/* МОГУТ БЫТЬ ФИНАЛИЗИРОВАНЫ:
 * - классы
 *      - в т.ч. все вложенные
 *          - кроме анонимного
 *              - у него нет имени
 *      - нельзя наследовать класс
 *          - но можно создавать объекты
 *      - все методы автоматически становятся финализированными
 *      - не может иметь абстрактные методы
 *          - все остальное:
 *              - либо итак абстрактное:
 *                  - абстрактные подклассы
 *                  - интерфейсы
 *                  - перечисления
 *              - либо не могут быть абстрактными
 *                  - поля
 *
 * - методы
 *      - нельзя переопределять в наследнике
 *      - в финализированном классе все методы автоматически становятся финализированными
 *      - не могут быть абстрактными и сразу же предоставлять реализацию
 *          - в отличие от перечисления
 *
 *
 * - поля/переменные
 *      - примитивные - нельзя изменить значение
 *      - ссылочные - нельзя присвоить ссылку другому объекту
 *      - перед использованием должно быть присвоено значение
 *          - либо сразу
 *          - либо в конструкторе
 *          - либо в блоке инициализации */


/* НЕ МОГУТ/НЕ ДОЛЖНЫ БЫТЬ ФИНАЛИЗИРОВАННЫМИ
 * - абстрактные методы класса
 *      - т.к. их нельзя будет реализовать
 * - анонимные классы
 *      - т.к. у класса нет имени
 * - интерфейсы
 *      - автоматически абстрактные
 * - методы интерфейса
 *      - автоматически абстрактные
 * - дефолтные методы интерфейса
 *      - на то они и дефолтные, что могут быть и не дефолтные
 * - статические методы интерфейса
 *      - итак невозможно переопределить
 * - поля интерфейса
 *      - автоматически финализированные
 * - перечисления
 *      - неявно финализированны
 *      - но может иметь абстрактный метод для дальнейшей реализиации в подклассе!
 * - конструкторы
 *      - т.к. итак не может быть переопределен */


@Ntrstn("В финализированном классе все методы автоматически финализированы")

@Ntrstn("В финализированном классе не может быть только абстрактных методов, а остальные члены не " +
        "могут быть объявлены абстрактными по своим причинам")

@Ntrstn("Методы, вызываемые из конструктора, обычно должны быть финализированными, иначе подкласс " +
        "может переопределить такой метод с неожиданными результатами")
/*~~~~~~~~~~~~~~~~~~~~~~~~МОЖНО ФИНАЛИЗИРОВАТЬ~~~~~~~~~~~~~~~~~~~~~~~~*/
/* ФИНАЛИЗИРОВАННЫЙ КЛАСС НЕЛЬЗЯ НАСЛЕДОВАТЬ */
final class AbleToFinalize { //
    /*~~~~~~~~~~ПОЛЯ~~~~~~~~~~*/
    /* ЗНАЧЕНИЕ ФИНАЛИЗИРОВАННЫХ ПОЛЕЙ НЕЛЬЗЯ ИЗМЕНИТЬ */
    final int finalClassField = 0; // требуется обязательная инициализация
    final Object o = new Object(); // состояние объекта может измениться
    final static int finalClassStaticField = 0; // требует инициализации

    /*~~~~~~~~~~МЕТОДЫ~~~~~~~~~~*/
    /* НЕЛЬЗЯ ПЕРЕОПРЕДЕЛИТЬ */
    /* В ФИНАЛИЗИРОВАННОМ КЛАССЕ ЯВЛЯЕТСЯ АВТОМАТИЧЕСКИ ФИНАЛИЗИРОВАННЫМ */
    final void finalClassMeth(final int param) { // параметр нельзя изменить
        /*~~~~~~~~~~ЛОКАЛЬНЫЕ ПЕРЕМЕННЫЕ~~~~~~~~~~*/
        /* АНАЛОГИЧНО ПОЛЯМ*/
        final int local; // локальную переменную нельзя изменить
    }

    final static void finalClassStaticMeth() { // final и static оба запрещают переопределение
    }

    /*~~~~~~~~~~ВЛОЖЕННЫЕ КЛАССЫ~~~~~~~~~~*/
    /* НЕЛЬЗЯ НАСЛЕДОВАТЬ */
    final class Inner {
        {
            /* ЛОКАЛЬНЫЕ КЛАССЫ */
            final class Inner2 {
            }
        }
    }

    final static class InnerStatic {
    }
}


/*~~~~~~~~~~~~~~~~~~~~~~~~НЕЛЬЗЯ ФИНАЛИЗИРОВАТЬ~~~~~~~~~~~~~~~~~~~~~~~~*/
/* АБСТРАКТНЫЙ КЛАСС */
abstract class NotAbleToFinalize {

    /* АБСТРАКТНЫЙ МЕТОД */
    abstract void abstractMethod();

    /*АБСТРАКТНЫЙ ПОДКЛАСС */
    abstract class AbstactInner {
    }

    /* КОНСТРУКТОР */
    public NotAbleToFinalize() {
    }

    /* ИНТЕРФЕЙС */
    interface SomeInterface {
        /* ПОЛЕ ИНТЕРФЕЙСА ЯВЛЯЕТСЯ ИТАК ФИНАЛИЗИРОВАННЫМ */
        final int i = 5;

        /* МЕТОД ИНТЕРФЕЙСА ЯВЛЯЕТСЯ НЕЯВНО АБСТРАКТНЫМ */
        abstract void meth();

        /* ДЕФОЛТНЫЙ МЕТОД ИНТЕРФЕЙСА НЕ МОЖЕТ БЫТЬ ФИНАЛИЗИРОВАН */
        default void meth2() {
        }

        /* СТАТИЧЕСКИЙ МЕТОД ИНТЕРФЕЙСА НЕ МОЖЕТ БЫТЬ ФИНАЛИЗИРОВАН */
        static void meth3() {
        }
    }

    /* ПЕРЕЧИСЛЕНИЕ */
    /* ЯВЛЯЕТСЯ НЕЯВНО ФИНАЛИЗИРОВАННЫМ */
    enum FinalEnum {
    }

    /* АНОНИМНЫЙ КЛАСС */
}


public class Final {
}