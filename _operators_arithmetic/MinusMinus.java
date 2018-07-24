package _operators_arithmetic;

/* Вычитание 1 от значения */

public class MinusMinus {

    static int a = 1, b = 1;
    static int c = 7, d = 7;

    public static void main(String[] args) {

        System.out.println("\"MinusMinus\"");

        /*ДЕКРЕМЕНТАЦИЯ
         * - нежелательна в выражениях (запутывает)
         * - intA--: вычислить выражение, затем отнять 1 от переменной
         * - --intA: отнять 1 от переменной, затем вычислить выражение*/
        System.out.println(a--); // Напечатает 1, а потом станет 0
        System.out.println(--b); // Станет 0 и напечает 0
        /*Закрепим*/
        System.out.println(2 * c--); // (2 * 7) = 14 (с затем станет 6)
        System.out.println(2 * --d); // 2 * (7 - 1) = 12 (doubleD сразу станет 6)


        /* У ЦЕЛОЧИСЛЕННЫХ ВЫХОДА ИЗ ДИАПАЗОНА НЕ ПРОИСХОДИТ
         * - значением станет другая граница диапазона */
        byte e = -128;
        e--;
        System.out.println(e); // даст 127
    }
}