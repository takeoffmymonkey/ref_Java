package operators_assignment;

/* Операция присваивания со сдвигом вправо и заполнением нулевыми значениями битов*/

/* Составные операции эффективнее простых */

public class MoveRightWithZeroFillEquals {

    static int intA = -268435456; //   (1111 0000 0000 0000 0000 0000 0000 0000)
    public static void main(String[] args) {
        System.out.println("\"MoveRightWithZeroFillEquals\"");

        /*ПРИМЕНЕНИЕ
         * - переменная и выражения должны иметь совместимый тип
         * - больше информации в соответствующем операторе*/
        intA >>>= 2;
        /* Тоже, что и intA = intA >>> 2
         * intA >>> 2:
         * (1111 0000 0000 0000 0000 0000 0000 0000) = -268435456
         * (0011 1100 0000 0000 0000 0000 0000 0000) = 1006632960
         * intA = 1006632960*/
        System.out.println(intA);// даст 1006632960
    }
}
