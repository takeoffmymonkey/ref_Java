package _operators_arithmetic;

/* Прибавление 1 к значению */


public class PlusPlus {

    static int a = 1, b = 1;
    static int c = 7, d = 7;

    public static void main(String[] args) {

        System.out.println("\"PlusPlus\"");

        /*ИНКРЕМЕНТАЦИЯ
         * - нежелательна в выражениях (запутывает)
         * - intA++: вычислить выражение, затем прирастить 1 к переменной
         * - ++intA: прираститть 1 к переменной, затем вычислить выражение*/
        System.out.println(a++); // Напечатает 1, а потом станет 2
        System.out.println(++b); // Станет 2 и напечает 2
        /*Закрепим*/
        System.out.println(2 * c++); // (2 * 7) = 14 (с затем станет 8)
        System.out.println(2 * ++d); // 2 * (7 + 1) = 16 (doubleD сразу станет 8)


        /* У ЦЕЛОЧИСЛЕННЫХ ВЫХОДА ИЗ ДИАПАЗОНА НЕ ПРОИСХОДИТ
         * - значением станет другая граница диапазона */
        byte e = 127;
        e++;
        System.out.println(e); // даст -128
    }
}
