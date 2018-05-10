package operators_control_flow_conditional;

/* обеспечивает исполнение или пропуск инструкции в зависимости от справедливости условия
 * - if (условие) оператор1 else оператор2 */

public class IfElse {

    public static void main(String[] args) {
        System.out.println("\"IfElse\"");

        /* - Если операторов (не условных) только 1, то можно не заключать в блок
         * - можно без else */
        if (1 > 0) System.out.println("Только один оператор");


        /*Если операторов (не условных) > 1, то они заключаются в блок*/
        if (true) {
            System.out.println("Первая оператор");
            System.out.println("Второй оператор");
        }


        /* else объединяется с ближайшим оператором if*/
        if (false) ;
        else System.out.println("Принадлежу ближайшему сверху if оператору");
    }
}
