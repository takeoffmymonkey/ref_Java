package operators_arithmetic;

/* Получать произведение значений */

public class Multiply {
    static int intA = 2, intB = 3;
    static double doubleC = 1.5;
    static float floatF = 1.5f;


    public static void main(String[] args) {
        System.out.println("\"Multiply\"");

        /*УМНОЖЕНИЕ
         * - при участии double или float, результат будет соответствующего типа*/
        System.out.println(intA * intB); // даст 6
        System.out.println(intB * doubleC); // даст 4.5 double
        System.out.println(intB * floatF); // даст 4.5 float
    }
}
