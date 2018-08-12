package operators_logical;

/* Укороченное «ИЛИ» */

public class OrOr {

    static int intA = 5, intB = 0;

    public static void main(String[] args) {
        System.out.println("\"OrOr\"");

        /*ПРОСТОЕ ПРИМЕНЕНИЕ
         * - eсли любой из операндов равен true, то условие становится true*/
        System.out.println((5 < 3) || (6 > 2)); // Даст true


        /*СОКРАЩЕНИЕ ВЫЧИСЛЕНИЯ
         * - Если первый операнд true, то второй не вычисляется */
        System.out.println(true && (5 < 0)); // Не дойдет до сравнения чисел


        /*ИЗБЕЖАТЬ ДЕЛЕНИЯ НА 0*/
        if (intB == 0 || intA / intB > 10) { // Деление не будет произведено, т.е. intB = 0
        }
    }
}
