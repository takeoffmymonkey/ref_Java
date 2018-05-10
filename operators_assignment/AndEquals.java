package operators_assignment;

/* Оператор присваивания побитового «И» («AND») */

/* Составные операции эффективнее простых */

public class AndEquals {
    static int intA = 60; //   (00111100)
    static byte byteB = 13; // (00001101)

    public static void main(String[] args) {
        System.out.println("\"AndEquals\"");

        /*ПРИМЕНЕНИЕ
         * - переменная и выражения должны иметь совместимый тип
         * - больше информации в соответствующем операторе*/
        intA &= byteB;
        /* Тоже, что и intA = intA + byteB
         * intA + byteB:
         * (00111100) = 60
         * (00001101) = 13
         * (00001100) = 12
         * intA = 12*/
        System.out.println(intA);// даст 12

    }
}
