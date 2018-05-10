package operators_logical;

/* Укороченное «И» */

public class AndAnd {
    static int intA = 5, intB = 0;


    public static void main(String[] args) {
        System.out.println("\"AndAnd\"");

        /*ПРОСТОЕ ПРИМЕНЕНИЕ
         * - чтобы дало true, оба сравниваемых операнда должны быть true, иначе false*/
        System.out.println((5 > 3) && (6 > 2)); // Даст true


        /*СОКРАЩЕНИЕ ВЫЧИСЛЕНИЯ
         * - Если первый операнд false, то второй не вычисляется */
        System.out.println(false && (5 > 0)); // Не дойдет до сравнения чисел


        /*ИЗБЕЖАТЬ ДЕЛЕНИЯ НА 0*/
        if (intB != 0 && intA / intB > 10) { // Деление не будет произведено, т.к. intB = 0
        }
    }
}
