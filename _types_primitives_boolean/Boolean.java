package _types_primitives_boolean;

/* - Для простых признаков (белое - черное)*/

public class Boolean {
    static boolean defaultValue;

    public static void main(String[] args) {
        System.out.println("\"Boolean\"");

        /*РАЗМЕР:
         * - в массивах: 1 байт (8 бит)
         * - не в массивах: 4 байта (32 бита) - т.е. int*/


        /*ДИАПАЗОН ЗНАЧЕНИЙ: true, false*/
        System.out.println("Первое значение: " + java.lang.Boolean.FALSE);
        System.out.println("Второе значение: " + java.lang.Boolean.TRUE);


        /*ДЕФОЛТНОЕ ЗНАЧЕНИЕ: false*/
        System.out.println("Дефолтное значение: " + defaultValue);


        /*FALSE И TRUE НЕ СООТВЕТСТВУЮТ 0 И 1*/
        // System.out.println(0 == false);
        // System.out.println(1 == true);


        /*ЛОГИЧЕСКИЕ ВЫРАЖЕНИЯ ДАЮТ FALSE ИЛИ TRUE*/
        System.out.println("5 больше 3: " + (5 > 3)); // даст true


        /*ОБЯЗАТЕЛЕН В УСЛОВНЫХ ВЫРАЖЕНИЯХ IF, FOR
         * - насчет for не уверен*/
        if (true) {
        }
        for (; true; ) { // позволяет и без true
            break;
        }
    }
}
