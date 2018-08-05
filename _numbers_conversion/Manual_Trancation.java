package _numbers_conversion;

/* РУЧНОЕ (ЯВНОЕ, СУЖАЮЩЕЕ) ПРЕОБРАЗОВАНИЕ
 * - происходит:
 *   + при присваивании
 *   + в выражениях
 *
 * - при условии, что:
 *   + длина целевого меньше исходного
 *      - ИСКЛЮЧЕНИЕ: присвоение литерала (int по дефолту), если его значение вмещается в целевой тип
 *   + типы не совместимы:
 *      - подписанный в не подписанный или наоборот
 *      - c плавающей точкой в целое*/


/* КРУГЛЫЕ СКОБКИ - ОПЕРАТОР ПРЕОБРАЗОВАНИЯ
 * - ручное преобразование может приводить к потере данных, поэтому компилятор требует явно указать
 * тип преобразования
 * - в выражении может быть несколько преобразований */


/* КОГДА ЕСТЬ СМЫСЛ
 * - когда исходное значение действительно может поместиться в целевое
 * - нужно отбросить все после запятой у float или double (но лучше использовать Math.round())*/


/* КОГДА НЕТ СМЫСЛА
 * - когда исходное значение не может поместиться в целевое */


/* УСЕЧЕНИЕ ЦЕЛОЧИСЛЕННЫХ ПРИ ПРИВЕДЕНИИ
 * - лишние старшие биты просто отбрасываются (см. переполнение)*/


/*УСЕЧЕНИЕ ДРОБНЫХ ПРИ ПРИВЕДЕНИИ
 * - в целочисленные:
 *      + слишком большое отрицательное превращается в соответствующее MIN_VALUE
 *      + слишком большое положительно превращается в соответствующее MAX_VALUE кроме byte и short,
 *      у которых значение становится -1
 *
 * - double в float: слишком большой позитивный или негативный double становится соответственно
 * Float.POSITIVE_INFINITY или Float.NEGATIVE_INFINITY*/


/*ПОВЕДЕНИЕ ТИПОВ-ОБЕРТОК
 * - стандартные правила, если приводится чпримитив к обертке
 * - обертки напрямую к примитивам не приводятся, так как являются объектами, и не являются
 * наследниками друг друга*/


/*КАК ПРЕОБРАЗОВАТЬ BOOLEAN В ЦЕЛОЧИСЛЕННОЕ (хак)
 * - при помощи тернарного выражения (напр. 5 > 4 ? 1 : 0)*/


/*ТИПЫ
 * - byte (8-bit signed integers); MIN_VALUE: -128; MAX_VALUE: 127
 * - short (16-bit signed integers); MIN_VALUE: -32768; MAX_VALUE: 32767
 * - char (16-bit unsigned integers); MIN_VALUE: 0; MAX_VALUE: 65535
 * - int (32-bit signed integers); MIN_VALUE: -2147483648; MAX_VALUE: 2147483647
 * - long (64-bit signed integers); MIN_VALUE: -9223372036854775808; MAX_VALUE: 9223372036854775807
 * - float (32-bit floating point); MIN_VALUE (не отрицательное): 1.4E-45; MAX_VALUE: 3.4028235E38
 * - double (64-bit floating point); MIN_VALUE (не отрицательное): 4.9E-324; MAX_VALUE: 1.7976931348623157E308
 * - boolean (32-bit signed integers); false, true*/

public class Manual_Trancation {
    static byte byteA = 1;
    static short shortA = 1;
    static int intA = 1;
    static char charA = 1;
    static long longA = 1L;
    static float floatA = 1f;
    static double doubleA = 1;

    static Byte byteB = 1;
    static Short shortB = 1;
    static Integer intB = 1;
    static Character charB = 1;
    static Long longB = 1L;
    static Float floatB = 1f;
    static Double doubleB = 1d;

    public static void main(String[] args) {
        /* КРУГЛЫЕ СКОБКИ - ОПЕРАТОР ЯВНОГО ПРЕОБРАЗОВАНИЯ */
        byteA = (byte) intA; // может приводить к потере данных из-за переполнения, поэтому требует
        // явное преобразование
        byteA = (byte) (float) floatB; // напр. Float в byte: необходимо несколько преобразований
        byteA = (byte) floatA; // float в byte: нескольких преобразований не требуется


        /*КОГДА ЕСТЬ СМЫСЛ*/
        intA = 1;
        byteA = (byte) intA; // исходное значение вмещается в целевое
        System.out.println(byteA); // даст 1
        doubleA = 3.434; // нужно отбросить все после запятой у float или double
        intA = (int) doubleA;
        System.out.println(intA); // даст 3
        doubleA = 3.434;
        intA = (int) Math.round(doubleA); // предпочтительный способ округления (возвращает long)


        /*КОГДА НЕТ СМЫСЛА
         * - исходное значение не вмещается в целевое*/
        intA = 1212112;
        byteA = (byte) intA;
        System.out.println(byteA); // даст -48, и это, вероятно, не то, что я намеревался получить


        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        /* УСЕЧЕНИЕ ЦЕЛОЧИСЛЕННЫХ ПРИ ПРИВЕДЕНИИ*/

        /* ПРИ ПРИВЕДЕНИИ БОЛЕЕ ЁМКОГО К МЕНЕЕ ЁМКОМУ СТАРШИЕ БИТЫ ПРОСТО ОТБРАСЫВАЮТСЯ*/
        shortA = 32639; // 0111_1111_0111_1111
        byteA = (byte) shortA;
        System.out.println(Integer.toBinaryString(byteA)); // 0111_1111 (или 127)


        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        /* УСЕЧЕНИЕ ДРОБНЫХ ПРИ ПРИВЕДЕНИИ*/

        /*СЛИШКОМ БОЛЬШОЕ ОТРИЦАТЕЛЬНОЕ ДРОБНОЕ ЧИСЛО ПРИ ПРИВЕДЕНИИ К ЦЕЛОМУ ПРЕВРАЩАЕТСЯ В MIN_VALUE*/
        //1111_1011_0010_1111_1000_0001_0000_0000_0011_1110_1101_0111_1110_0010_1110_0110
        doubleA = -2342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233.234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233;
        byteA = (byte) doubleA; // даст 0 (т.е. MIN_VALUE)
        shortA = (short) doubleA; // даст 0 (т.е. MIN_VALUE)
        charA = (char) doubleA; // даст 0 (т.е. MIN_VALUE)
        intA = (int) doubleA; // даст -2147483648 (т.е. MIN_VALUE) или 1000_0000_0000_0000_0000_0000_0000_0000
        longA = (long) doubleA; // даст -9223372036854775808 (т.е. MIN_VALUE) или 1000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000

        /*СЛИШКОМ БОЛЬШОЕ ПОЛОЖИТЕЛЬНОЕ ДРОБНОЕ ЧИСЛО ПРИ ПРИВЕДЕНИИ К ЦЕЛОМУ ПРЕВРАЩАЕТСЯ В MAX_VALUE
         * - !!! КРОМЕ byte и short - у них становится -1*/
        // 111_1011_0010_1111_1000_0001_0000_0000_0011_1110_1101_0111_1110_0010_1110_0110
        doubleA = 2342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233.234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233;
        byteA = (byte) doubleA; // даст -1 или 1111_1111_1111_1111_1111_1111_1111_1111
        shortA = (short) doubleA; // даст -1 или 1111_1111_1111_1111_1111_1111_1111_1111
        charA = (char) doubleA; // даст 65535 (т.е. MAX_VALUE) или 1111_1111_1111_1111 (не -1, т.к. unsigned?)
        intA = (int) doubleA; // даст 2147483647 (т.е. MAX_VALUE) или 111_1111_1111_1111_1111_1111_1111_1111
        longA = (long) doubleA; // даст 9223372036854775807 (т.е. MAX_VALUE) или 111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111

        /* СЛИШКОМ БОЛЬШОЙ ПОЗИТИВНЫЙ ИЛИ НЕГАТИВНЫЙ DOUBLE ПРИ ПРИВЕДЕНИИ К FLOAT СТАНОВИТСЯ
         * СООТВЕТСТВЕННО Float.POSITIVE_INFINITY или Float.NEGATIVE_INFINITY*/
        doubleA = 2342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233.234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233;
        floatA = (float) doubleA; // Infinity или 111_1111_1000_0000_0000_0000_0000_0000
        doubleA = -2342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233.234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233234234234234234232342342332342342342342342323423423323423423423423423234234233;
        floatA = (float) doubleA; // -Infinity или 1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1000_0000_0000_0000_0000_0000


        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        /*ПОВЕДЕНИЕ ТИПОВ-ОБЕРТОК
         * - стандартные правила, если приводится примитив к обертке
         * - обертки напрямую к примитивам не приводятся, так как являются объектами*/
        byteB = (byte) shortA;
        //byteA = (byte) shortB; // нельзя


        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        /* ЛОГИЧЕСКОЕ ЗНАЧЕНИЕ МОЖНО ПРИВЕСТИ К ЦЕЛОМУ ТЕРНАРНЫМ ВЫРАЖЕНИЕМ
         * - что невозможно сделать простым приведением */
        int result = 5 > 4 ? 1 : 0;
        System.out.println("5 > 4 ? " + result); // даст 1
    }
}