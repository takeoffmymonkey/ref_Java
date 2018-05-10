package operators_control_flow_loops.undefined;

/* Выполнять операцию пока условие справедливо*/

public class While_WithPreCondition {
    public static void main(String[] args) {
        System.out.println("\"While_WithPreCondition \"");

        /*ОБЫЧНОЕ ПРИМЕНЕНИЕ
         * - выполняется пока условие true
         * - условие проверяется перед итерацией*/
        int i = 0;
        while (i < 5) {
            System.out.println("В цикле while");
            i++;
        }
    }
}