package operators_control_flow_conditional;

/* замена else if, когда нужно выбирать из вариантов (т.е. проверка на равенство, а не результат
 * выражения */

public class Switch {

    /*только для char, byte, short или int, enum или строкового литерала (с JSE7, но ниже эффективность*/
    static char charA = 'a';
    static byte byteB = 127;
    static short shortC = 1231;
    static int intD = 111;
    static String stringLiteralE = "Строковый литерал";

    enum TimeOFYear {
        AUTUMN, WINTER, SPRINT, SUMMER
    }

    static TimeOFYear timeOfYearF = TimeOFYear.AUTUMN;


    public static void main(String[] args) {
        System.out.println("\"Switch\"");

        /*ОБЫЧНОЕ ИСПОЛЬЗОВАНИЕ
         * - при выборе большой группы значений - эффективней if/else*/
        int intG = 111;
        switch (intG) {
            case 111: // case должен совпадать по типу с условием
                // здесь идет необходимое действие при совпадении
                System.out.println("Есть совпадение с 111!");
                break; // !Необязателен, но тогда ВЫПОЛНЯТСЯ ВСЕ СЛЕДУЮЩИЕ ВАРИАНТЫ (до break)
            case 222: // сразу несколько вариантов для одного case
            case 333:
            case 444:
                System.out.println("Есть совпадение с 222, 333 или 444!");
                break;
            default: // Выполнится в случае несовпадения
                // Необязателен и может быть не последний
                // Здесь break не обязателен
                System.out.println("Нет совпадений!");
        }
    }
}
