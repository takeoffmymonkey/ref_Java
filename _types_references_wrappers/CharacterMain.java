package _types_references_wrappers;

/* ОБОЛОЧКА ДЛЯ CHAR */

/* КЛАСС CHARACTER
 * public final class Character
 * extends Object
 * implements Serializable, Comparable<Character> */


/* ЯВЛЯЕТСЯ ФИНАЛИЗИРОВАННЫМ
 * - запрещено наследование */


/*ИМЕЮТ КОНСТРУКТОРЫ ДЛЯ ПРИМИТИВОВ
 * - но примитивом можно создавать объект без помощи конструктора (автоупаковка)*/


/* НЕТ КОНСТРУКТОРА ТИПА С ПАРАМЕТРОМ STRING*/


/* ВАЖНЫЕ МЕТОДЫ
 * - isLetter(): Определяет, является ли значение указанного типа char буквой.
 * - isDigit(): Определяет, является ли величина указанного типа char цифрой.
 * - isWhitespace(): Определяет, является ли значение указанного типа char пустым пространством.
 * - isUpperCase(): Определяет, является ли значение указанного типа char в верхнем регистре.
 * - isLowerCase(): Определяет, является ли величина указанного типа char в нижнем регистре.
 * - toUpperCase(): Возвращает значение в верхнем регистре в виде указанного типа char.
 * - toLowerCase(): Возвращает значение в нижнем регистре в виде указанного типа char.
 * - toString(): Возвращает строковый объект, представляющий указанное символьное значение,
 * string - один символ.*/


/* ЕСТЬ ЕЩЕ ВАЖНЫЕ МЕТОДЫ, СВЯЗАННЫЕ С UNICODE
 * - см. Unicode*/


public class CharacterMain {

    public static void main(String[] args) {
        /* ЯВЛЯЕТСЯ ФИНАЛИЗИРОВАННЫМ
         * - запрещено наследование */
        // class i extends Character


        /*ИМЕЮТ КОНСТРУКТОРЫ ДЛЯ ПРИМИТИВОВ
         * - но примитивом можно создавать объект без помощи конструктора*/
        Character character = new Character('3');
        Character character2 = '3'; // автоупаковка


        /* НЕТ КОНСТРУКТОРА ТИПА С ПАРАМЕТРОМ STRING*/
        // Character c1 = new Character("3");


        /*НЕКОТОРЫЕ МЕТОДЫ*/
        System.out.println(Character.isDigit(character)); // true


    }
}
