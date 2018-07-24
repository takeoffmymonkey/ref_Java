package _types_references_wrappers;

/* ОБОЛОЧКА ДЛЯ bool*/


/*КЛАСС BOOLEAN
 * public final class Boolean
 * extends Object
 * implements Serializable, Comparable<Boolean> */


/* ЯВЛЯЕТСЯ ФИНАЛИЗИРОВАННЫМ
 * - запрещено наследование */


/*ИМЕЮТ КОНСТРУКТОРЫ ДЛЯ ПРИМИТИВОВ И СТРОК
 * - но примитивом можно создавать объект без помощи конструктора (автоупаковка)
 * - если строка не является числом - NumberFormatException в рантайме*/


/* МОЖНО ПРИМЕНЯТЬ ДЛЯ УПРАВЛЕНИЯ ОПЕРАТОРАМИ ВСЕХ ЦИКЛОВ (while, for или do/while)
 * - благодаря автоупаковке и автораспаковке*/


/*ВАЖНЫЕ МЕТОДЫ
 * - booleanValue (): вернет значение этого объекта Boolean в виде примитива boolean
 * - compare(boolean x, boolean y): сравнивает 2 значения boolean
 * - compareTo(Boolean b): сравнивает объекты Boolean
 * - getBoolean(String name): возвращает true если, и только если аргумент существует и равен строке
 * "true".
 * - logical...(boolean a, boolean b): возвращает результат указанной операции для операндов
 * - parseBoolean(String s): парсит строку как boolean.
 * - toString(): вернет строковый объект, представляющий значение Boolean.
 * - valueOf(String s): вернет Boolean со значением, представленное указанной строкой.*/

public class BooleanMain {

    public static void main(String[] args) {
        /* ЯВЛЯЕТСЯ ФИНАЛИЗИРОВАННЫМ
         * - запрещено наследование */
        // class i extends Boolean


        /*ИМЕЮТ КОНСТРУКТОРЫ ДЛЯ ПРИМИТИВОВ И СТРОК
         * - но примитивом можно создавать объект без помощи конструктора
         * - если строка не является true - будет назначен false (т.е. не будет исключения в рантайме)*/
        Boolean bool = new Boolean(true);
        Boolean bool1 = new Boolean("true");
        Boolean bool2 = true; // автоупаковка
        Boolean bool3 = new Boolean("dfsdf");
        System.out.println(bool3); // даст false


        /* МОЖНО ПРИМЕНЯТЬ ДЛЯ УПРАВЛЕНИЯ ОПЕРАТОРАМИ ВСЕХ ЦИКЛОВ (while, for или do/while)*/
        Boolean b = true;
        while (b) {
            System.out.println("В цикле");
            b = false;
        }


        /*НЕКОТОРЫЕ МЕТОДЫ*/
        boolean b1 = b.booleanValue();
        Boolean.compare(b1, b);
        Boolean.logicalAnd(b, b1); // то же, что и операция логическое И
    }
}
