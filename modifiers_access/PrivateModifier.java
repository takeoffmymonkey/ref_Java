package modifiers_access;

import _types_references_annotations.my_annotations.Ntrstn;

/*Ограничивает область действия классом*/


/* ДОСТУП ОПРЕДЕЛЯЕТСЯ МЕСТОМ ОБРАЩЕНИЯ К ЧЛЕНУ, А НЕ К ЕГО ОБЪЕКТУ!
 * - т.е. я могу обратится к private члену, если нахожусь в его классе - даже через объект данного
 * класса */


@Ntrstn("Я могу обратится к private члену, если нахожусь в его классе - даже через объект данного " +
        "класса ")
/*КЛАСС
 * - не может быть private*/
public class PrivateModifier {

    /*ПРИВАТНОЕ ПОЛЕ
     * - может быть доступно только через публичный Getter метод */
    private int privateField;
    private static int privateStaticField;

    /* ПУБЛИЧНЫЙ GETTER МЕТОД (для теста)*/
    public int getPrivateField() {
        return privateField;
    }

    /*ПРИВАТНЫЙ КОНСТРУКТОР
     * - в аргументах модификатор доступа не указывается*/
    private PrivateModifier() {
        super();
    }

    /*ПРИВАТНЫЙ МЕТОД
     * - в аргументах модификатор доступа не указывается*/
    private void privateMethod() {
    }

    /*ПУБЛИЧНЫЙ МЕТОД СУПЕРКЛАССА (ПЕРЕОПРЕДЕЛЕНИЕ)
     * - не должен СОКРАЩАТЬ (но может расширять) видимость (т.е. здесь должен оставаться public)*/
    @Override
    public String toString() {
        return super.toString();
    }

    /* ВНУТРЕННИЙ КЛАСС
     * - может быть приватный*/
    private class PrivateInnerClass {
        private int privateInnerField;

        private void privateInnerMethod() {
        }
    }

    /* ВНУТРНЕННИЙ СТАТИЧЕСКИЙ (ВЛОЖЕННЫЙ) КЛАСС
     * - может быть приватный*/
    private static class PrivateInnerStaticClass {
        private int privateInnerStaticClassField;

        private void privateInnerStaticClassMethod() {
        }
    }


    /* ВНУТРЕННИЙ ИНТЕРФЕЙС
     * - может быть приватным*/
    private interface PrivateInnerInterface {
        int alwaysPublicStaticFinalVar = 0;

        void alwaysPublicAbstractMethod();

    }


    public static void main(String[] args) {
        /* ДОСТУП К ПРИВАТНЫМ ЧЛЕНАМ ЕСТЬ ДАЖЕ ЧЕРЕЗ ОБЪЕКТ, ЕСЛИ ОБРАЩАТЬСЯ ВНУТРИ КЛАССА */
        PrivateModifier privateModifier = new PrivateModifier();
        int i = privateModifier.privateField;
        int i2 = privateStaticField;
    }
}

/*ВНЕШНИЙ ИНТЕРФЕЙС
 * - не может быть приватным*/
interface NonPrivateOutterInterface {
    /*ПЕРЕМЕННАЯ ИНТЕРФЕЙСА
     * - не может быть приватной*/
    int nonPrivateVar = 0;

    /*МЕТОД ИНТЕРФЕЙСА
     * - не может быть приватный*/
    void nonPrivateMethod();
}