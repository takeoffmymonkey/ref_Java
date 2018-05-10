package types_primitives_conversion;

public class Conversion {

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
    static String stringB = "";

    public static void main(String[] args) {
        /*DOUBLE -> FLOAT*/
        floatA = new Float(10.71);
        floatA = 10.71f;


        /*FLOAT -> STRING*/
        stringB = Float.toString(0f);
        stringB = String.valueOf(floatA);


        /*STRING -> BYTE*/
        byteA = Byte.valueOf("120");


        /*BYTE -> DOUBLE*/
        doubleA = byteB.doubleValue();


        /*FLOAT -> BYTE*/
        byteA = (byte) (float) floatB;
        //byteA = (byte) floatB; // невозможно!!!, floatB – не базовый тип, а класс


        /*DOUBLE -> SHORT*/
        shortA = (short) doubleA;


        /*CHARACTER -> INT*/
        charB = new Character('5');
        intA = Character.digit(charB.charValue(), 10); // 10 - 10-тичная система
    }
}
