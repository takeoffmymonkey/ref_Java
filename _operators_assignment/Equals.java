package _operators_assignment;

/* Простой оператор присваивания */

public class Equals {
    static int intA = 3;
    static byte byteB = 4;
    static int intE, intF, intG;

    public static void main(String[] args) {
        System.out.println("\"Equals\"");

        /*ПРИМЕНЕНИЕ
         * - присваивает значения из правой стороны операндов к левому операнду
         * - переменная и выражения должны иметь совместимый тип*/
        intE = intA; // intE стал такого же значения как и intA
        byteB = (byte) intA; // необходимо приведение
        intG = intF = 5 + 10; // intG и intF будет назначен 15
        System.out.println(intG); // даст 15
        System.out.println(intF); // даст 15
    }
}
