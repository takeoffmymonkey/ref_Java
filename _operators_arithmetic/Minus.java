package _operators_arithmetic;


/* Получать разницу значений
 *
 * Инвертировать значение */


public class Minus {

    static int intA = 3, intB = 4;
    static byte byteC = 10;
    static int intD = -10;
    static double doubleE = 1.5;
    static float floatF = 1.5f;

    public static void main(String[] args) {
        System.out.println("\"Minus\"");


        /*ВЫЧИТАНИЕ
         * - при участии double или float, результат будет соответствующего типа
         * - byteC (!но не -=): в выражении повышается до int, если она byte, short или char*/
        System.out.println((intA - intB)); // даст разницу -1
        System.out.println(intA - doubleE); // даст 1.5 double
        System.out.println(intA - floatF); // даст 1.5 float
        byteC = (byte) (byteC - byteC); // в процессе стала int, поэтому понадобилось обратное приведение
        byteC -= byteC; // в процессе осталась byte! интересно..


        /*ИНВЕРСИЯ*/
        System.out.println(-intD); // Было -10, станет 10
    }
}
