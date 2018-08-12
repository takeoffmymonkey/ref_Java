package types_primitives_fp_numbers;


/* - Двойная точность
 * - Суффикс можно не указывать
 * - Для десятичных значений
 * - sin(), cos(), sqrt() возвращают double*/

public class Double {

    static float defaultValue;

    public static void main(String[] args) {
        System.out.println("\"Double\"");

        /*РАЗМЕР: 8 байт (64 бита)*/
        System.out.println("Количество байт в Double: " + java.lang.Double.BYTES);
        System.out.println("Количество бит в Double: " + java.lang.Double.SIZE);


        /*ДИАПАЗОН ЗНАЧЕНИЙ: 4.9E-324..1.7976931348623157E308 (для позитивных и негативных)
         * СТЕПЕНЬ: -1022..1023
         * МИНУС БЕСКОНЕЧНОСТЬ: -Infinity или 0xfff0000000000000L
         * ПЛЮС БЕСКОНЕЧНОСТЬ: Infinity или 0x7ff0000000000000L
         * NAN: NAN или 0x7ff8000000000000L*/
        System.out.println("Минимальное значение: " + java.lang.Double.MIN_VALUE);
        System.out.println("Максимальное значение: " + java.lang.Double.MAX_VALUE);
        System.out.println("Минимальное значение степени: " + java.lang.Double.MIN_EXPONENT);
        System.out.println("Максимальное значение степени: " + java.lang.Double.MAX_EXPONENT);
        System.out.println("Минус бесконечность: " + java.lang.Double.NEGATIVE_INFINITY);
        System.out.println("Плюс бесконечность: " + java.lang.Double.POSITIVE_INFINITY);
        System.out.println("NaN: " + java.lang.Double.NaN);


        /*ДЕФОЛТНОЕ ЗНАЧЕНИЕ: 0.0*/
        System.out.println("Дефолтное значение: " + defaultValue);


        /* НЕ ТРЕБУЕТ ЯВНОГО УКАЗАНИЯ суффикса D (или d) ДЛЯ ИСПОЛЬЗОВАНИЯ */
        double explicitDouble = 3.2234234234234523452523543523452452452345234523;
        double explicitDouble2 = 3.2234234234234523452523543523452452452345234523d; // но позволяет


        /*ТОЧНОСТЬ
         * 15 - 16 значащих десятичных*/
        System.out.println("Точность Double: " + explicitDouble); // даст 3.2234234234234522


        /*  НЕЛЯЗЯ ВПИСАТЬ В FLOAT*/
        // float explicitDoubleInDouble = 3.243523452452452345234523d; // не позволяет


        /*МНОГИЕ СТАТИЧЕСКИЕ МЕТОДЫ MATH ПРИНИМАЮТ И ВОЗВРАЩАЮТ DOUBLE*/
        double sin = Math.sin(3.5);
        double cos = Math.cos(3.5);
        double sqrt = Math.sqrt(3.5);
    }
}