package modifiers_access;

import _types_references_annotations.my_annotations.Ntrstn;

/* - ограничивает область действия пакетом и всеми наследующими подклассами
 * - используется только при наследовании
 * - дает подклассу возможность использовать вспомогательный метод или переменную, предотвращая
 * неродственный класс от попыток использовать их */


/* ДОСТУП ОПРЕДЕЛЯЕТСЯ МЕСТОМ ОБРАЩЕНИЯ К ЧЛЕНУ, А НЕ К ЕГО ОБЪЕКТУ!
 * - т.е. я могу обратится к члену protected, если нахожусь в самом классе, в его пакете или в его
 * наследнике, но не могу обратится к члену через объект класса или наследника, если нахожусь
 * не в его пакете! */


@Ntrstn("Я могу обратится к члену protected, если нахожусь в самом классе, в его пакете или в его " +
        "наследнике, но не могу обратится к члену через объект класса или наследника, если нахожусь " +
        "не в его пакете!")

        /*КЛАСС
         * - не может быть protected*/
class ProtectedModifier {

    /*PROTECTED ПОЛЕ
     * - Доступно только в данном пакете и в подклассах в другом пакете */
    protected int protectedField;

    /*PROTECTED КОНСТРУКТОР
     * - Доступен только в данном пакете и в подклассах в другом пакете
     * - в аргументах модификатор доступа не указывается*/
    protected ProtectedModifier() {
        super();
    }

    /*PROTECTED МЕТОД
     * - Доступен только в данном пакете и в подклассах в другом пакете
     * - в аргументах модификатор доступа не указывается*/
    protected void protectedMethod() {
    }

    /*ПУБЛИЧНЫЙ МЕТОД СУПЕРКЛАССА (ПЕРЕОПРЕДЕЛЕНИЕ)
     * - не должен СОКРАЩАТЬ (но может расширять) видимость (т.е. здесь должен оставаться public)*/
    @Override
    public String toString() {
        return super.toString();
    }

    /* ВНУТРЕННИЙ КЛАСС
     * - может быть protected*/
    protected class ProtectedInnerClass {
        protected int protectedInnerField;

        protected void protectedInnerMethod() {
        }
    }

    /* ВНУТРНЕННИЙ СТАТИЧЕСКИЙ (ВЛОЖЕННЫЙ) КЛАСС
     * - может быть protected*/
    protected static class ProtectedInnerStaticClass {
        protected int protectedInnerStaticClassField;

        protected void protectedInnerStaticClassMethod() {
        }
    }

    /* ВНУТРЕННИЙ ИНТЕРФЕЙС
     * - может быть protected*/
    protected interface ProtectedInnerInterface {
        int alwaysPublicStaticFinalVar = 0;

        void alwaysPublicAbstractMethod();
    }
}


/*ВНЕШНИЙ ИНТЕРФЕЙС
 * - не может быть protected*/
interface ProtectedOuterInterface {
    /*ПЕРЕМЕННАЯ ИНТЕРФЕЙСА
     * - не может быть protected*/
    int nonProtectedVar = 0;

    /*МЕТОД ИНТЕРФЕЙСА
     * - не может быть protected*/
    void nonProtectedMethod();
}