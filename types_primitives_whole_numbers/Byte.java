package types_primitives_whole_numbers;

/* - Экономия места в массивах
 * - Потоки ввода-вывода
 * - Манипуляция двоичными данными*/

public class Byte {
    static byte defaultValue;

    public static void main(String[] args) {
        System.out.println("\"Byte\"");

        /*РАЗМЕР: 1 байт (8 бит)*/
        System.out.println("Количество байт в byte: " + java.lang.Byte.BYTES);
        System.out.println("Количество бит в byte: " + java.lang.Byte.SIZE);


        /*ДИАПАЗОН ЗНАЧЕНИЙ: -128..127(вкл.) или (-2^7)..((2^7)-1)*/
        System.out.println("Минимальное значение: " + java.lang.Byte.MIN_VALUE);
        System.out.println("Максимальное значение: " + java.lang.Byte.MAX_VALUE);


        /*ДЕФОЛТНОЕ ЗНАЧЕНИЕ: 0*/
        System.out.println("Дефолтное значение: " + defaultValue);


        /*В ВЫРАЖЕНИЯХ ПОВЫШАЕТСЯ ДО INT*/
        byte a = 100;
        System.out.println("тип byte a: " + ((Object) a).getClass().getName());
        // byte b = a / 1; // повысилось до int
        System.out.println("тип результата a/1: " + ((Object) (a / 1)).getClass().getName());
    }
}
