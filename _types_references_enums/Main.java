package _types_references_enums;

import java.util.Arrays;

import _types_references_annotations.my_annotations.Ntrstn;
/* КЛАСС
 * public abstract class Enum
 * extends Object
 * implements Comparable<E extends Enum<E>>, Serializable */


/* НАСЛЕДОВАНИЕ
 * - каждое перечисление неявно наследуется от java.lang.Enum
 *      - явное наследование запрещено (не может быть суперклассом или подклассом)
 * - не может быть абстрактным:
 *      - но может иметь абстрактный метод с последующим оверрайдом в его статических элементах
 * - могут быть объявлены:
 *      - вне класса
 *      - внутри класса
 * - не могут быть объявлены:
 *      - внутри нестатического подкласса (т.к. являются статическими)
 *      - внутри метода (т.к. не должны быть локальным членом)*/


/* КОНСТАНТЫ ПЕРЕЧИСЛИМОГО ТИПА
 * - экземпляры (т.е. типы) enum-класса
 *      - получить оператором new нельзя
 * - статически доступны
 * - должны идти первыми
 *      - если после них нет кода, то ; не нужна
 * - можно сравнивать оператором ==
 * - имеют порядковый номер
 * - могут иметь собственные:
 *      - конструкторы:
 *        - являются приватными
 *        - вызов происходит при перечислении констант внутри самого класса перечисления
 *        - в случае вызова дефолтного конструктора, переменным (если есть) назначаются дефолтные
 *        значения
 *      - поля:
 *          - приватные переменные будут доступны внутри класса, где объявлено перечисление
 *          - могут быть в статических полях, где описана внутренняя логика
 *      - методы
 *          - приватные методы будут доступны внутри класса, где объявлено перечисление
 *          - можно переопределять методы родителей (напр. toString)
 *          - могут быть в статических полях:
 *             - может быть внутренняя логика (не будет видна снаружи)
 *             - можно реализовывать абстрактные методы
 * - статические блоки инициализации:
 *      - здесь можно реализовывать абстрактные методы
 *      - объявлять внутренние методы и поля:
 *         - не видны снаружи
 *         - могут понадобиться, напр, для реализации абстрактного метода
 * - могут реализовывать интерфейсы*/


/* УДОБНО ИСПОЛЬЗОВАТЬ В SWITCH И ИТЕРИРОВАТЬ ПО НИМ
 * - для итерарирования - использовать метод valueOf()*/


/* НЕЛЬЗЯ ИСПОЛЬЗОВАТЬ ОБОБЩЕНИЯ
 * todo - но можно EnumMap и EnumSet */


/* МЕТОДЫ КЛАССА ENUM
 * - getDeclaringClass() - тип значения перечисления
 *
 * - name() - имя константы в виде строки
 *
 * - ordinal() - порядковый номер константы (0, 1, 2..)
 *
 * - values() - массив со списком констант (нет в java.lang.Enum - автоматически добавляется при
 * компиляции)
 *
 * - valueOf() - константа, значение которой соответствует символьной строке аргумента (нет в
 * java.lang.Enum - автоматически добавляется при компиляции). Если элемент не найден -
 * IllegalArgumentException, если равен null - NullPointerException. Альтернатива:
 *      java.lang.Enum.valueOf(Test .class, "K");)
 *
 * - java.lang.Class.getEnumConstants() - все значения перечисления списком*/


@Ntrstn("Несмотря на то, что перечисления являются неявно финализированными, они могут иметь " +
        "абстрактные методы для дальнейшей реализации в статических элементах этого же перечисления")
public class Main {
    /*НЕ МОЖЕТ ЯВНО НАСЛЕДОВАТЬСЯ ОТ Enum*/
    /*enum Internal extends Enum {}*/


    /*МОЖЕТ БЫТЬ ВНУТРИ КЛАССА*/
    enum Internal {
        NON_GENERIC,
        /*НЕЛЬЗЯ ИСПОЛЬЗОВАТЬ ОБОБЩЕНИЯ*/
        //GENERIC<Е>
    }


    /*НЕ МОЖЕТ БЫТЬ АБСТРАКТНЫМ*/
    /*abstract enum Abstract {}*/


    enum Display {
        /* ВЫЗОВЫ ПРИВАТНЫХ КОНСТРУКТОРОВ
         * - должны идти первыми
         * - если после перечислений есть код, то нужна ;*/
        SONY,// переменные установятся в дефолтное значение
        SAMSUNG(15, true), LG(22, true), AOC(20, true);


        /*МОГУТ ИМЕТЬ КОНСТРУКТОРЫ
         * - является приватными*/
        Display() { // пустой конструктор, все переменные установятся в дефолтное значение
        }

        Display(int diagonal, boolean isWorking) {
            this.diagonal = diagonal;
            this.isWorking = isWorking;
        }

        /*МОГУТ ИМЕТЬ ПОЛЯ*/
        private int diagonal; // приватное поле все равно видно в обрамляющем классе
        private boolean isWorking;

        /* МОГУТ ИМЕТЬ МЕТОДЫ */
        private int getDiagonal() { // приватный метод все равно виден в обрамляющем классе
            return diagonal;
        }

        private boolean isWorking() {
            return isWorking;
        }

        /*МОЖНО ПЕРЕОПРЕДЕЛЯТЬ МЕТОДЫ СУПЕРКЛАССОВ*/
        @Override
        public String toString() {
            return name();
        }

        void checkDisplay() { // только этот метод будет виден за границей обрамляющего класса
            System.out.printf("Display %s has diagonal of %d and is working: %b\n",
                    name(), getDiagonal(), isWorking());
        }
    }


    /* МОЖЕТ ИМЕТЬ АБСТРАКТНЫЙ МЕТОД С ПОСЛЕДУЮЩЕЙ РЕАЛИЗАЦИЕЙ*/
    /* МОЖЕТ ИМЕТЬ СТАТИЧЕСКИЕ БЛОКИ */
    enum Animal {
        DOG {
            int times = 4; // переменная

            int countWoofs() { // внутренний метод не доступен вовне
                int count = 1;
                return count * times;
            }

            public void makeNoise() { // реализация абстрактного метода
                for (int i = 0; i < countWoofs(); i++) {
                    System.out.println("Woof!");
                }
            }
        },
        CAT {
            public void makeNoise() {
                System.out.println("Meow!");
            }
        };

        abstract void makeNoise();
    }


    /*МОЖЕТ РЕАЛИЗОВЫВАТЬ ИНТЕРФЕЙС*/
    /*ИНТЕРФЕЙС*/
    interface Operator {
        int apply(int a, int b);
    }

    /*РЕАЛИЗАЦИЯ*/
    enum Operating implements Operator {
        PLUS {
            @Override
            public int apply(int a, int b) {
                return a + b;
            }
        },
        MINUS {
            @Override
            public int apply(int a, int b) {
                return a - b;
            }
        }
    }


    public static void main(String[] args) {
        /*НЕ МОЖЕТ БЫТЬ ВНУТРИ МЕТОДА, Т.К. НЕ МОЖЕТ БЫТЬ ЛОКАЛЬНЫМ*/
        /*enum InternalMethod {}*/


        /*ЭКЗЕМПЛЯРЫ ПЕРЕЧИСЛЕНИЯ СТАТИЧЕСКИ ДОСТУПНЫ */
        Display d = Display.AOC;


        /*НЕЛЬЗЯ ПОЛУЧИТЬ ОПЕРАТОРОМ NEW*/
//        Display d = new Display();


        /*ЭКЗЕМПЛЯРЫ ЯВЛЯЮТСЯ ТИПОМ СВОЕГО ПЕРЕЧИСЛЕНИЯ*/
        System.out.println(d.getClass().getName()); // __Implicit_Synthetic_Bridge$Display
        System.out.println(d.getDeclaringClass()); // class __Implicit_Synthetic_Bridge$Display


        /*~~~~~~~~~~~~~~~~~~РАБОТА С КОНСТАНТАМИ И МЕТОДЫ~~~~~~~~~~~~~~~~~~*/
        Display display = Display.SONY;
        Display display1 = Display.SONY;
        Display display2 = Display.SAMSUNG;
        Display display3 = Display.LG;
        Display display4 = Display.AOC;


        /*ЕСТЬ ДОСТУП К ПОЛЯМ И МЕТОДАМ*/
        boolean working = display.isWorking; // внутри класса есть доступ даже к приватным полям
        display.getDiagonal(); // внутри класса есть доступ даже к приватным методам
        display.checkDisplay(); // Display SONY has diagonal of 0 and is working: false
        display2.checkDisplay(); // Display SAMSUNG has diagonal of 15 and is working: true


        /*МОЖНО СРАВНИВАТЬ ОПЕРАТОРОМ ==*/
        System.out.println(display == display1); // true
        System.out.println(display == display2); // false


        /*ORDINAL() - УЗНАТЬ ПОРЯДКОВЫЙ НОМЕР*/
        System.out.println(display.ordinal()); // даст 0
        System.out.println(display1.ordinal()); // даст 0
        System.out.println(display2.ordinal()); // даст 1


        /*NAME() - УЗНАТЬ ИМЯ */
        System.out.println(display.name()); // даст SONY


        /*VALUES() - ПОЛУЧИТЬ СПИСКОМ ВСЕ КОНСТАНТЫ */
        System.out.println(Arrays.toString(Display.values())); // [SONY, SAMSUNG, LG, AOC]


        /*VALUEOF() - ПОЛУЧИТЬ КОНСТАНТУ, СООТВЕТСТВУЮЩУЮ УКАЗАННОЙ СТРОКЕ*/
        Display displ = Display.valueOf("LG");
        Display displ2 = Enum.valueOf(Display.class, "LG"); // альтернатива
//        Display.valueOf("ЩЩЩ"); // IllegalArgumentException
//        Enum.valueOf(Display.class, "ЩЩЩ"); // IllegalArgumentException


        /*CLASS.GETENUMCONSTANTS() - ПОЛУЧИТЬ ВСЕ ЭЛЕМЕНТЫ ПЕРЕЧИСЛЕНИЯ*/
        Display[] displays = Display.class.getEnumConstants();
        System.out.println(Arrays.toString(displays)); // [SONY, SAMSUNG, LG, AOC]


        /*~~~~~~~~~~~~~~~~~~ИСПОЛЬЗОВАНИЕ В ИТЕРАЦИЯХ~~~~~~~~~~~~~~~~~~*/
        for (Display disp : Display.values()) {
            System.out.println(disp);
        }


        /*~~~~~~~~~~~~~~~~~~ИСПОЛЬЗОВАНИЕ С SWITCH~~~~~~~~~~~~~~~~~~*/
        switch (display3) {
            case SAMSUNG: {
                System.out.println("we've got samsung");
                break;
            }
            case AOC: {
                System.out.println("we've got aoc");
                break;
            }
            case SONY: {
                System.out.println("we've got sony");
                break;
            }
            case LG: {
                System.out.println("we've got lg");
                break;
            }
        }
    }

    class SubClass {
        /*НЕ МОЖЕТ БЫТЬ ВНУТРИ ВНУТРЕННЕГО КЛАССА, Т.К. ЯВЛЯЕТСЯ СТАТИЧЕСКИМ*/
        /*enum InternalSubClass {}*/
    }

    static class StaticSubClass {
        /*МОЖЕТ БЫТЬ ВНУТРИ ВНУТРЕННЕГО СТАТИЧЕСКОГО КЛАССА*/
        enum InternalStaticSubClass {
        }
    }
}

/*МОЖЕТ БЫТЬ СНАРУЖИ КЛАССА*/
enum External {
}

class SomeClass {
    void someMeth() {
        Main.Display display = Main.Display.AOC;
        /*ВОВНЕ ДОСТУПНЫ ТОЛЬКО ПУБЛИЧНЫЕ ЧЛЕНЫ*/
//        display.isWorking;
        display.checkDisplay();
    }
}