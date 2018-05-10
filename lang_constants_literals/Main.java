package lang_constants_literals;

/* ФИКСИРОВАННОЕ ЗНАЧЕНИЕ, БЕЗ ВОЗМОЖНОСТИ ИЗМЕНИТЬ
 * - значение может быть вычислено при компиляции
 * - синоним ЛИТЕРАЛ */


/* ИМЕНОВАННАЯ КОНСТАНТА
 * - имеет осмысленное имя */


/* ПЕРЕМЕННАЯ-КОНСТАНТА
 * - переменные-примитивы или String, которые объявлены как final и инициализированы константным
 * выражением */
public class Main {

    public static void main(String[] args) {

        /*КОНСТАНТЫ*/
        final int СONSTANT1 = 50; // требуют final
        final int CONSTANT2 = 121 + 33; // обычно ЗАГЛАВНЫМИ БУКВАМИ
        final int CONSTANT3; // можно отложенно присвоить значение в блоке
        final String CONSTANT4 = "стро" + "ка";
        {
            CONSTANT3 = 324;
        }
        // СONSTANT1 = 100; // Присвоить значение можно единожды

        /*ИМЕНОВАННЫЕ КОНСТАНТЫ*/
        final int MAX_VALUE = 100500; // именованная константа
    }


    /* ПЕРЕМЕННЫЕ-КОНСТАНТЫ
     * во внутренних классах запрещены статические переменные, но разрешены переменные-константы*/
    class Inner { // обычно статические члены запрещены во внутренних классах
        static final String constantVar = "стро" + "ка";
        static final int constantVar2 = 232 + 5;
//        static final String constantVar3 = new String("строка");
//        static String constantVar3 = new String("строка");
//        static final double constantVar4 = Math.random();
//        static double constantVar4 = Math.random();
    }
}