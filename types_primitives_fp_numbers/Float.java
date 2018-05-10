package types_primitives_fp_numbers;


/* - Одинарная точность
 * - Для сохранения памяти в массивах
 * - Нежелательны из-за неточности*/

public class Float {

    static float defaultValue;

    public static void main(String[] args) {
        System.out.println("\"Float\"");

        /*РАЗМЕР: 4 байта (32 бита)*/
        System.out.println("Количество байт в Float: " + java.lang.Float.BYTES);
        System.out.println("Количество бит в Float: " + java.lang.Float.SIZE);


        /*ДИАПАЗОН ЗНАЧЕНИЙ: 1.4E-45..3.4028235E38 (для позитивных и негативных)
         * СТЕПЕНЬ: -126..127
         * МИНУС БЕСКОНЕЧНОСТЬ: -Infinity или 0xff800000
         * ПЛЮС БЕСКОНЕЧНОСТЬ: Infinity или 0x7f800000
         * NAN: NAN или 0x7fc00000*/
        System.out.println("Минимальное значение: " + java.lang.Float.MIN_VALUE);
        System.out.println("Максимальное значение: " + java.lang.Float.MAX_VALUE);
        System.out.println("Минимальное значение степени: " + java.lang.Float.MIN_EXPONENT);
        System.out.println("Максимальное значение степени: " + java.lang.Float.MAX_EXPONENT);
        System.out.println("Минус бесконечность: " + java.lang.Float.NEGATIVE_INFINITY);
        System.out.println("Плюс бесконечность: " + java.lang.Float.POSITIVE_INFINITY);
        System.out.println("NaN: " + java.lang.Float.NaN);


        /*ДЕФОЛТНОЕ ЗНАЧЕНИЕ: 0.0*/
        System.out.println("Дефолтное значение: " + defaultValue);


        /* ТРЕБУЕТ ЯВНОГО УКАЗАНИЯ суффикса F (или f) ДЛЯ ИСПОЛЬЗОВАНИЯ */
        float explicitFloat = 3.243523452452452345234523F;
        //float implicitFloat = 3.243523452452452345234523; // не разрешает


        /*ТОЧНОСТЬ
         * 6-7 значащих десятичных*/
        System.out.println("Точность float: " + explicitFloat); // даст 3.2435234


        /*  МОЖНО ВПИСАТЬ В DOUBLE
         * - тогда вычисления происходят все равно в double*/
        double explicitFloatInDouble = 3.243523452452452345234523f;
        System.out.println("Точность float в double: " + explicitFloatInDouble); // даст 3.243523359298706
    }
}

