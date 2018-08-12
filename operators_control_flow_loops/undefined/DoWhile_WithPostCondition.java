package operators_control_flow_loops.undefined;

/* Выполнять операцию пока условие справедливо, но вначале обязательно выполнить операцию*/

public class DoWhile_WithPostCondition {
    public static void main(String[] args) {
        System.out.println("\"DoWhile_WithPostCondition\"");

        /* ОБЫЧНОЕ ПРИМЕНЕНИЕ
         * - тело do выполнится минимум 1 раз (условие проверяется после итерации)*/
        int i = 10;
        do {
            System.out.println("Осталось секунд: " + i);
        } while (--i > 0);


        /*ЕДИНОРАЗОВОЕ ВЫПОЛНЕНИЕ*/
        do {
            System.out.println("Выполнюсь только 1 раз");
        } while (false);
    }
}
