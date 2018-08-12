package operators_control_flow_logic_breaking;

/* Передает управление в начало текущего вложенного цикла (в отличие от break, который выходит из
 * цикла)
 *
 * В случае for - передает управление оператору увеличения счетчика цикла ()
 *
 * Только для циклов while, do, for */

public class Continue {

    public static void main(String[] args) {
        System.out.println("\"Continue\"");

        /*WHILE loop*/
        int i = 0;
        while (i < 5) {
            // <<<< Переход сюда после continue
            System.out.println("В начале цикла while");
            i++;

            if (i < 6) {
                continue;
            }
            // Сюда никогда не дойдет
            System.out.println("В конце цикла while");
        }


        /*DO-WHILE loop*/
        int a = 0;
        do {
            // <<<< Переход сюда после continue
            System.out.println("В начале цикла do-while");
            a++;

            if (a < 6) {
                continue;
            }
            // Сюда никогда не дойдет
            System.out.println("В конце цикла do-while");
        } while (a < 5);


        /*FOR loop
         * - передает управление оператору увеличения счетчика цикла */
        for (int b = 0; b < 5; b++) { // <<<< Переход сюда после continue (к увеличению счетчика)
            System.out.println("В начале цикла for");
            if (b < 6) {
                continue;
            }
            // Сюда никогда не дойдет
            System.out.println("В конце цикла for");
        }


        /*С МЕТКОЙ
         * - управление передается заголовку оператора помеченного меткой цикла */
        int c = 0;
        metka:
        while (c < 5) {
            // <<<< Переход сюда после continue
            System.out.println("В начале цикла c меткой");
            c++;

            if (c < 6) {
                continue metka;
            }
            // Сюда никогда не дойдет
            System.out.println("В конце цикла c меткой");
        }


    }

}
