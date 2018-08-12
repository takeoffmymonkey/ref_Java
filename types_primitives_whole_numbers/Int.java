package types_primitives_whole_numbers;

/* - Наиболее используемый
 * - Для целых значений
 * - Для циклов и индексов
 * - Когда нет опасений из-за памяти*/

public class Int {

    static int defaultValue;

    public static void main(String[] args) {
        System.out.println("\"Int\"");

        /*РАЗМЕР: 4 байта (32 бита)*/
        System.out.println("Количество байт в int: " + java.lang.Integer.BYTES);
        System.out.println("Количество бит в int: " + java.lang.Integer.SIZE);


        /*ДИАПАЗОН ЗНАЧЕНИЙ: -2147483648.. 2147483647(вкл.) или (-2^31).. ((2^31)-1)*/
        System.out.println("Минимальное значение: " + java.lang.Integer.MIN_VALUE);
        System.out.println("Максимальное значение: " + java.lang.Integer.MAX_VALUE);


        /*ДЕФОЛТНОЕ ЗНАЧЕНИЕ: 0*/
        System.out.println("Дефолтное значение: " + defaultValue);
    }
}
