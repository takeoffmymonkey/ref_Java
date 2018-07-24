package _types_primitives_char;

/* - Для любого символа
 * - является кодовая единицей UTF-16
 * - может быть из двух char (cм. Unicode)*/
public class Char {
    static char defaultValue;

    public static void main(String[] args) {
        System.out.println("\"Char\"");

        /*РАЗМЕР: 2 байта (16 бит)*/
        System.out.println("Количество байт в char: " + java.lang.Character.BYTES);
        System.out.println("Количество бит в char: " + java.lang.Character.SIZE);


        /*ДИАПАЗОН ЗНАЧЕНИЙ:
         * - \u0000..\uFFFF или
         * - 0..65535*/
        System.out.println("Минимальное значение: " + java.lang.Character.MIN_VALUE);
        System.out.println("Максимальное значение: " + java.lang.Character.MAX_VALUE);


        /*ДЕФОЛТНОЕ ЗНАЧЕНИЕ: \u0000*/
        System.out.println("Дефолтное значение: " + defaultValue);


        /*ИНИЦИАЛИЗИРУЕТСЯ ОДИНАРНЫМИ КАВЫЧКАМИ */
        char charA = 'a';
        char charEmpty = '\u0000'; // u значит Unicode, 0000 - код символа


        /* СИМВОЛ МОЖЕТ НЕ БЫТЬ char, НО БЫТЬ СТРОКОЙ*/
        char charB = 'b';
        String stringB = "b";


        /*ОТНОСЯТСЯ К ЦЕЛОЧИСЛЕННЫМ - НАД НИМИ МОЖНО ПРОВОДИТЬ АРИФМЕТИЧЕСКИЕ ОПЕРАЦИИ*/
        System.out.println('\uFFFF' + '\uFFFF'); // даст 131070
        System.out.println('f' + 'u'); // даст 219
    }
}
