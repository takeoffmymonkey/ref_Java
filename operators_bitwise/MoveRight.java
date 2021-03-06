package operators_bitwise;

/*Значение левого операнда перемещается вправо на количество битов, указанное в правом операнде*/

public class MoveRight {

    /* - только для целочисленных: long, int, short, char и byte*/
    static int intA = 60; //   (0000 0000 0000 0000 0000 0000 0011 1100)
    static byte byteB = 60; // (0011 1100)
    static int intC = 1; // (0000 0000 0000 0000 0000 0000 0000 0001)


    public static void main(String[] args) {
        System.out.println("\"MoveRight\"");

        /* ОБЫЧНЫЙ СДВИГ
         * (0000 0000 0000 0000 0000 0000 0011 1100) = 60
         * (0000 0000 0000 0000 0000 0000 0000 1111) = 15*/
        System.out.println(intA >> 2); // даст 15


        /*АВТОМАТИЧЕСКОЕ РАСШИРЕНИЕ ДО INT*/
        // byte byteD = (byte) byteB >> 2; // ДАЖЕ НЕ РАЗРЕШАЕТ ПРИВЕДЕНИЕ! Даже, если указать сдвиг 0
        // Только так
        int intD = byteB >> 2;


        /*При сдвиге двоичного разряда значения за пределы, оно теряется
         * (0000 0000 0000 0000 0000 0000 0000 0001) = 1
         * (0000 0000 0000 0000 0000 0000 0000 0000) = 0*/
        System.out.println(intC >> 1); // даст 0
    }
}
