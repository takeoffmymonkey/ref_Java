package _types_null;

/* ИСПОЛЬЗОВАНИЕ В КАЧЕСТВЕ ЗНАЧЕНИЯ ССЫЛОЧНОЙ ПЕРЕМЕННОЙ
 * - использовать можно только с ссылочными переменными, но не примитивами
 * - значение по умолчанию для всех ссылочных переменных
 * - может быть использован везде, где используются ссылочные переменные
 *      - при вызове метода
 *      - с операторами:
 *          - логические:
 *              - можно сравнивать операторами == и !=
 *                  - null == null даст true
 *              - нельзя с другими логическими операторами
 *          - арифметические:
 *              - можно конкатенировать к строке
 *                  - просто добавится строка "null"
 *              - остальные нельзя
 *          - instanceof:
 *              - вернет false */


/*ВЫЗОВ МЕТОДОВ У ССЫЛОЧНОЙ ПЕРЕМЕННОЙ СО ЗНАЧЕНИЕМ NULL
 * - нестатический метод: NPE
 * - статический метод: нет NPE!
 *      - видимо, что статическому методу и не нужен объект в принципе*/


import _types_references_annotations.my_annotations.Ntrstn;


@Ntrstn("для ссылочной переменной со значением null можно вызвать статический метод ее типа без " +
        "получения NPE")
public class Main {
    static String empty;

    public static void main(String[] args) {
        /*МОЖНО ИСПОЛЬЗОВАТЬ ТОЛЬКО С ССЫЛОЧНЫМИ ПЕРЕМЕННЫМИ*/
//        int i = null; // нельзя назначить примитиву
        Integer i = null;


        /*ЗНАЧЕНИЕ ПО УМОЛЧАНИЮ ДЛЯ ВСЕХ ССЫЛОЧНЫХ ПЕРЕМЕННЫХ*/
        System.out.println(empty); // null


        /*ИСПОЛЬЗОВАНИЕ ПРИ ВЫЗОВЕ МЕТОДА*/
        forObject(null);


        /*СРАВНЕНИЕ, INSTANCEOF И ДРУГИЕ ОПЕРАТОРЫ*/
        empty = null;
        System.out.println(empty == null); // даст true
        System.out.println(null == null); // даст true
//        System.out.println(empty > null); // нельзя
        System.out.println(empty instanceof String); // даст false
        System.out.println("string" + empty); // даст stringnull
        System.out.println("string" + null); // даст stringnull


        /*ВЫЗОВ МЕТОДА У ПЕРЕМЕННОЙ СО ЗНАЧЕНИЕМ NULL*/
        Main main = null;
//        main.toString(); // NPE
        main.someStaticMeth(); // не будет NPE!

    }

    static void forObject(Object obj) {
    }

    static void someStaticMeth() {
        System.out.println("Вызван статический метод через ссылочную переменную со значением null");
    }
}