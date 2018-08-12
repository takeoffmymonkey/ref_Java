package types_primitives_fp_numbers;

/* Соответствуют норме IEEE 754 */

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        /*НЕ ИСПОЛЬЗОВАТЬ В ФИНАНСОВЫХ РАСЧЕТАХ
         * - Из-за внутреннего 2-ичного представления
         * - Как в 10-тичной нельзя посчитать 1/3
         * - Вместо - класс BigDecimal (максимальная возможная точность представления)*/
        double d = 2.0 - 1.1;
        BigDecimal bd = new BigDecimal(2.0 - 1.1);
        System.out.println("2.0 - 1.1 = " + d); //0.8999999999999999
        System.out.println("BigDecimal: 2.0 - 1.1 = " + bd); //0.899999999999999911182158029987476766109466552734375


        /* ДРОБНЫЕ ИСЧИСЛЕНИЯ НЕ ПРИВОДЯТ К ОШИБКАМ!
         * - одна -бесконечность/+бесконечность/NaN может не быть равной другой*/
        System.out.println(0.0 / 0); // NaN !!!
        System.out.println(3.14 / 0); // Infinity !!!
        double z = 0.0;
        double z1 = +0.0;
        double z2 = -0.0;
        System.out.println(z == z1 && z1 == z2); // даст true (т.к. 0.0 = +0.0 = -0.0)


        /* МОГУТ БЫТЬ В 16-РИЧНОЙ ФОРМЕ
         * p - степень (остается в 10-тичной)*/
        double double16 = 0.125;
        double double16_2 = 0x1.0p-3; // тоже самое, что 2^-3 или 0.125
        System.out.println(double16_2);
    }
}