package operators_bitwise;


/* Оператор дополнения (эффект «отражения» бит) */
public class Tilda {
    /* - только для целочисленных: long, int, short, char и byte*/
    static int intA = 60; //   (00111100)

    public static void main(String[] args) {
        System.out.println("\"Tilda\"");

        /* (00111100) = 60
           (11000011) = -61*/
        System.out.println(~intA); // даст -61
    }
}