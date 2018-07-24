package modifiers_access;

import _types_references_annotations.my_annotations.Ntrstn;

/* Ограничивает по умолчанию область действия пакетом */


/* ДОСТУП ОПРЕДЕЛЯЕТСЯ МЕСТОМ ОБРАЩЕНИЯ К ЧЛЕНУ, А НЕ К ЕГО ОБЪЕКТУ!
 * - т.е. я не могу обратится к default члену даже через объект этого класса, если нахожусь вне его
 * пакета */


@Ntrstn("Я не могу обратится к default члену даже через объект этого класса, если нахожусь вне его " +
        "пакета")
        /*КЛАСС
         * - может быть default*/
class DefaultModifier {

    /*ДЕФОЛТНОЕ ПОЛЕ
     * - доступен внутри пакета*/
    int defaultField;

    /*ДЕФОЛТНЫЙ БЛОК
     * - модификатор доступа - всегда default*/ {
    }

    /*ДЕФОЛТНЫЙ КОНСТРУКТОР
     * - доступен внутри пакета
     * - в аргументах модификатор доступа не указывается*/
    DefaultModifier() {
    }

    /*ДЕФОЛТНЫЙ МЕТОД
     * - доступен внутри пакета
     * - в аргументах модификатор доступа не указывается*/
    void defaultMethod() {
    }

    /* ДЕФОЛТНЫЙ ВНУТРЕННИЙ КЛАСС
     * - может быть дефолтный*/
    class DefaultInnerClass {
        int defaultInnerField;

        void defaultInnerMethod() {
        }
    }

    /* ДЕФОЛТНЫЙ ВНУТРЕННИЙ СТАТИЧЕСКИЙ (ВЛОЖЕННЫЙ) КЛАСС
     * - может быть дефотный*/
    static class DefaultInnerStaticClass {
        int defaultInnerStaticClassField;

        void defaultInnerStaticClassMethod() {
        }
    }


    /* ДЕФОЛТНЫЙ ВНУТРЕННИЙ ИНТЕРФЕЙС
     * - может быть приватным*/
    interface DefaultInnerInterface {
        int alwaysPublicStaticFinalVar = 0;

        void alwaysPublicAbstractMethod();
    }

}

/*ДЕФОЛТНЫЙ ВНЕШНИЙ ИНТЕРФЕЙС
 * - может быть дефолным*/
interface DefaultOuterInterface {
    /*ПЕРЕМЕННАЯ ИНТЕРФЕЙСА
     * - является public, а не default*/
    int alwaysPublicStaticFinal = 0;

    /*МЕТОД ИНТЕРФЕЙСА
     * - является public, а не default*/
    void alwaysPublicAbstract();
}