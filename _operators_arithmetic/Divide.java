package _operators_arithmetic;

/* Получать результат деления значений */

public class Divide {
    static int intA = 10, intB = 20;
    static float floatC = .5f;
    static double doubleD = .5;

    public static void main(String[] args) {
        System.out.println("\"Divide\"");

        /*ДЕЛЕНИЕ
         * - деление целого на целое = целое
         * - деление double или float на целое или целого на double или float = double или float
         * - деление числа с плавающей точкой на 0 = бесконечность или NaN
         * - деление целых на 0 - ArithmeticException: / by zero*/
        System.out.println(intB / intA); // Даст целое 2
        System.out.println(intA / floatC); // Даст 20.0 (float)
        System.out.println(intA / doubleD); // Даст 20.0 (double)
        System.out.println(floatC / 0); // Даст Infinity
        try {
            System.out.println(intA / 0); // ArithmeticException: / by zero
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException: / by zero");
        }
    }
}
