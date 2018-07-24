package _operators_assignment;

/* Оператор присваивания побитового исключающего «ИЛИ» («XOR»)*/

/* Составные операции эффективнее простых */

public class XorEquals {
    static int intA = 60; //   (00111100)
    static byte byteB = 13; // (00001101)

    public static void main(String[] args) {
        System.out.println("\"XorEquals\"");

        /*ПРИМЕНЕНИЕ
         * - переменная и выражения должны иметь совместимый тип
         * - больше информации в соответствующем операторе*/
        intA ^= byteB;
        /* Тоже, что и intA = intA ^ byteB
         * intA ^ byteB:
         * (00111100) = 60
         * (00001101) = 13
         * (00110001) = 49
         * intA = 49*/
        System.out.println(intA);// даст 49

    }
}
