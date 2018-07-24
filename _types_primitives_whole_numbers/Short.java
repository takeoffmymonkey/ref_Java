package _types_primitives_whole_numbers;

/*- Редко используется
 * - Для экономии памяти (как и byte)*/

public class Short {

    static short defaultValue;

    public static void main(String[] args) {
        System.out.println("\"Short\"");

        /*РАЗМЕР: 2 байт (16 бит)*/
        System.out.println("Количество байт в short: " + java.lang.Short.BYTES);
        System.out.println("Количество бит в short: " + java.lang.Short.SIZE);


        /*ДИАПАЗОН ЗНАЧЕНИЙ: -32768..32767(вкл.) или (-2^15)..((2^15)-1)*/
        System.out.println("Минимальное значение: " + java.lang.Short.MIN_VALUE);
        System.out.println("Максимальное значение: " + java.lang.Short.MAX_VALUE);


        /*ДЕФОЛТНОЕ ЗНАЧЕНИЕ: 0*/
        System.out.println("Дефолтное значение: " + defaultValue);


        /*В ВЫРАЖЕНИЯХ ПОВЫШАЕТСЯ ДО INT*/
        short a = 100;
        System.out.println("тип short a: " + ((Object) a).getClass().getName());
        // short b = a / 1; // повысилось до int
        System.out.println("тип результата a/1: " + ((Object) (a / 1)).getClass().getName());
    }
}
