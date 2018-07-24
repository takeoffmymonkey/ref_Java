package _asserts.when_to_use;

/*CONTROL FLOW ИНВАРИАНТЫ
 * - инвариант - определенность, не изменяемость
 *
 * - напр. нужно проверить уверенность, что в какую-то точку не дойдет выполнение кода
 *
 * - необходимо использовать с осторожностью: ошибка компиляции, если до statement не добраться по
 * спецификации J*/


public class ControlFlowInvariants {

    void foo() {
        for (; ; ) {
            if (true)
                return;
        }
        // assert false; // Сюда не добраться
        // throw new AssertionError(); // альтернативный вариант
    }
}
