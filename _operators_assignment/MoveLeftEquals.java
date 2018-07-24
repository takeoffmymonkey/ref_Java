package _operators_assignment;

/* Оператор присваивания «Сдвиг влево» */

/* Составные операции эффективнее простых */

public class MoveLeftEquals {
    static int intA = 60; // (0011 1100)

    public static void main(String[] args) {
        System.out.println("\"MoveLeftEquals\"");

        /*ПРИМЕНЕНИЕ
         * - больше информации в соответствующем операторе*/
        intA <<= 2;
        /* Тоже, что и intA = intA << 2
         * intA << byteB:
         * (00111100) = 60
         * (11110000) = 240
         * intA = 240*/
        System.out.println(intA);// даст 240
    }
}
