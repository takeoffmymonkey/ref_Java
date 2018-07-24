package _operators_assignment;

/* Оператор присваивания «Сдвиг вправо» */

/* Составные операции эффективнее простых */
public class MoveRightEquals {
    static int intA = 60; // (0011 1100)

    public static void main(String[] args) {
        System.out.println("\"MoveRightEquals\"");

        /*ПРИМЕНЕНИЕ
         * - больше информации в соответствующем операторе*/
        intA >>= 2;
        /* Тоже, что и intA = intA >> 2
         * intA >> 2 :
         * (00111100) = 60
         * (00001111) = 15
         * intA = 15*/
        System.out.println(intA);// даст 15
    }
}
