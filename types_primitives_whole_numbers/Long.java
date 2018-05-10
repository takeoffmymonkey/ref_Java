package types_primitives_whole_numbers;

/* Когда int недостаточно */

public class Long {

    static long defaultValue;

    public static void main(String[] args) {
        System.out.println("\"Long\"");

        /*РАЗМЕР: 8 байт (64 бита)*/
        System.out.println("Количество байт в long: " + java.lang.Long.BYTES);
        System.out.println("Количество бит в long: " + java.lang.Long.SIZE);


        /*ДИАПАЗОН ЗНАЧЕНИЙ: -9223372036854775808 ..9223372036854775807(вкл.) или (-2^63)..((2^63)-1)*/
        System.out.println("Минимальное значение: " + java.lang.Long.MIN_VALUE);
        System.out.println("Максимальное значение: " + java.lang.Long.MAX_VALUE);


        /*ДЕФОЛТНОЕ ЗНАЧЕНИЕ: 0*/
        System.out.println("Дефолтное значение: " + defaultValue);


        /* ТРЕБУЕТ ЯВНОГО УКАЗАНИЯ суффикса L (или l, но нежелательно) ЕСЛИ ЛИТЕРАЛ БОЛЬШЕ ДИАПАЗОНА INT*/
        long implicitLong = 213123; // ок, литерал int будет повышен до long
        long explicitLong2 = 213123123211231231L; // выходит за диапазон int - требуется явное указание L
        //long implicitLong = 213123123211231231; // не разрешает, так как думает, что int
    }
}
