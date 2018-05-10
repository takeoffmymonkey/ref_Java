package operators_logical;

/* - Меняет логическое состояние своего операнда (без присваивания). Если условие true, то станет
 * false*/

public class Not {

    static boolean booleanA = false;

    public static void main(String[] args) {
        System.out.println("\"Not\"");

        System.out.println(!booleanA); // Даст обратное значение, т.е. true
        System.out.println(booleanA); // Но так и останется false, т.е. смены значения не произойдет
    }
}
