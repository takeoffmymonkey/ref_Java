package _operators_logical;

/* - Тернарный оператор
 * - условие ? выражение_1 : выражение_2
 * - если условие true, то вычисляется первое выражение, если нет – второе*/

public class Ternary {

    static int intA = 5, intB = 2;

    public static void main(String[] args) {
        System.out.println("\"Ternary\"");


        /* УЗНАТЬ, КАКОЕ ЗНАЧЕНИЕ МЕНЬШЕ*/
        int lesserValue = intA < intB ? intA : intB;
        System.out.println("Наименьшее значение из двух равно " + lesserValue);


        /* ТЕРНАРНЫМ МОЖНО ПРИВЕСТИ ЛОГИЧЕСКОЕ ЗНАЧЕНИЯ К ЦЕЛОМУ
         * - что невозможно сделать простым приведением*/
        int result = 5 > 4 ? 1 : 0;
        System.out.println("5 > 4 ? " + result); // даст 1
    }
}
