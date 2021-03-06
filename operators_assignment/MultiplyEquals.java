package operators_assignment;

/* Оператор присваивания «Умножение»*/

/* Составные операции эффективнее простых*/

public class MultiplyEquals {
    static int intA = 10;
    static int intB = 2;

    public static void main(String[] args) {
        System.out.println("\"MultiplyEquals\"");
        /*ПРИМЕНЕНИЕ
         * - переменная и выражения должны иметь совместимый тип
         * - больше информации в соответствующем операторе*/
        intA *= intB;
        /* Тоже, что и intA = intA * intB
         * intA * intB = 10 * 2 = 20
         * intA = 20*/
        System.out.println(intA); // даст 20
    }
}
