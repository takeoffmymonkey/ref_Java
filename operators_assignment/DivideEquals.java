package operators_assignment;

/* Оператор присваивания «Деление» */

/* Составные операции эффективнее простых*/

public class DivideEquals {
    static int intA = 10;
    static int intB = 2;

    public static void main(String[] args) {
        System.out.println("\"DivideEquals\"");

        /*ПРИМЕНЕНИЕ
         * - переменная и выражения должны иметь совместимый тип
         * - больше информации в соответствующем операторе*/
        intA /= intB;
        /* Тоже, что и intA = intA / intB
         * intA / intB = 10 / 2 = 5
         * intA = 5*/
        System.out.println(intA); // даст 5
    }
}
