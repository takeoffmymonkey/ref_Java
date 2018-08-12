package operators_assignment;

/* Оператор присваивания «Модуль» */

/* Составные операции эффективнее простых*/
public class ModuloEquals {

    static int intA = 10;
    static int intB = 2;

    public static void main(String[] args) {
        System.out.println("\"ModuloEquals\"");

        /*ПРИМЕНЕНИЕ
         * - переменная и выражения должны иметь совместимый тип
         * - больше информации в соответствующем операторе*/
        intA %= intB;
        /* Тоже, что и intA = intA % intB
         * intA % intB = 10 % 2 = 0
         * intA = 0*/
        System.out.println(intA); // даст 0
    }
}
