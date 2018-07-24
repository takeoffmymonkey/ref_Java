package _types_primitives_whole_numbers;

/*Длина типа означает не занимаемый объем памяти, а скорее поведение, определяемое им для переменных
и выражений данного типа*/


public class Main {

    public static void main(String[] args) {
        /*НЕТ БЕЗЗНАКОВЫХ
         * - даже 0 имеет знак*/
        int positiveZero = +0; // выглядит как 0_000_0000
        int negativeZero = -0; // после инверсии, добавления 1 бита и усечения выглядит также
        System.out.println(negativeZero == positiveZero); // поэтому даcт true


        /* У ЦЕЛОЧИСЛЕННЫХ ВЫХОДА ИЗ ДИАПАЗОНА НЕ ПРОИСХОДИТ
         * - значением станет другая граница диапазона */
        byte e = -128;
        e--;
        System.out.println(e); // даст 127
    }
}
