package _operators_bitwise;

/* Копирует бит в результат, если он существует в обоих операндах */

public class And {
    /* - только для целочисленных: long, int, short, char и byte
     * - может также и для boolean (но там сравнение не происходит побитово)*/
    static int intA = 60; //   (00111100)
    static byte byteB = 13; // (00001101)

    public static void main(String[] args) {
        System.out.println("\"And\"");

        /* (00111100) = 60
           (00001101) = 13
           (00001100) = 12*/
        System.out.println(intA & byteB); // даст 12

        /* Булеан (не побитово) и число (побитово) нельзя сравнить */
        //System.out.println(true & byteD);
    }
}
