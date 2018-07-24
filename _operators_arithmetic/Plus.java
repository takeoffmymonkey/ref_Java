package _operators_arithmetic;

/* Для получения суммы значений*/

/* Для конкатенации строк*/

public class Plus {
    static int intA = 3, intB = 4;
    static byte byteC = 10;
    static double doubleE = 1.5;
    static float floatF = 1.5f;

    static String string = "Текстовая";
    static String string2 = " строка";

    public static void main(String[] args) {
        System.out.println("\"Plus\"");


        /*СЛОЖЕНИЕ
         *  - при участии double или float, результат будет соответствующего типа
         * - + byteC (!но не +=): в выражении повышается до int, если она byte, short или char*/
        System.out.println((intA + intB)); // даст сумму 7
        System.out.println(intA + doubleE); // даст 4.5 double
        System.out.println(intA + floatF); // даст 4.5 float
        byteC = (byte) (byteC + byteC); // в процессе стала int, поэтому понадобилось обратное приведение
        byteC += byteC; // в процессе осталась byte! интересно..


        /*КОНКАТЕНАЦИЯ
         * - если операция начинается со строки, то остальные члены тоже станут строками
         * - оператор + перегружен ТОЛЬКО для строк (++, - и т.д. - нет)*/
        System.out.println((string + string2)); // даст "Текстовая строка"
        System.out.println(string + intA + intB); // даст "Текстовая34"
        System.out.println(intA + intB + string); // даст "7Текстовая"

    }
}
