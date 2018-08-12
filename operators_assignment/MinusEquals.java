package operators_assignment;

/* Оператор присваивания «Вычитания»*/

/* Составные операции эффективнее простых*/
public class MinusEquals {

    static int intA = 10;
    static int intB = 2;

    public static void main(String[] args) {
        System.out.println("\"MinusEquals\"");

        /*ПРИМЕНЕНИЕ
         * - переменная и выражения должны иметь совместимый тип
         * - больше информации в соответствующем операторе*/
        intA -= intB;
        /* Тоже, что и intA = intA - intB
         * intA - intB = 10 - 2 = 8
         * intA = 8*/
        System.out.println(intA); // даст 8
    }
}
