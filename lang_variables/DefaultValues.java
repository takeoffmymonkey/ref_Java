package lang_variables;

public class DefaultValues {
    /*ПЕРЕМЕННЫЕ РЕАЛИЗАЦИИ*/
    byte aByte; // 0 типа byte
    short aShort; // 0 типа short
    int anInt; // 0 типа int
    long aLong; // 0 типа long
    float aFloat; // +0 типа float
    double aDouble; // +0 типа double
    char aChar; // '\u0000'
    boolean aBoolean; // false
    DefaultValues defaultValues; // null

    /*ПЕРЕМЕННЫЕ КЛАССА - АНАЛОГИЧНО*/
    static byte aByteStatic; // 0 типа byte

    /*ЭЛЕМЕНТЫ МАССИВА - АНАЛОГИЧНО*/
    int[] ints = new int[4]; // все элементы 0 типа int


    /* АРГРУМЕНТЫ КОНСТРУКТОРА - ПЕРЕДАННОЕ ФАКТИЧЕСКОЕ ЗНАЧЕНИЕ*/
    public DefaultValues(byte aByte) {
    }

    /* АРГРУМЕНТЫ МЕТОДА - ПЕРЕДАННОЕ ФАКТИЧЕСКОЕ ЗНАЧЕНИЕ*/
    void method(byte aByte) {
    }

    /*ЛОКАЛЬНАЯ ПЕРЕМЕННАЯ
     * - нет дефолтного значения
     * - перед использованием должна быть инициализирована либо в декларации либо оператором
     * присваивания*/
    void method2() {
        int a;
        // System.out.println(a); // сначала должна быть инициализирована
        a = 5;
        System.out.println(a); // теперь ок
    }

    public static void main(String[] args) {
        /*ПАРАМЕТР ОБРАБОТЧИКА ИСКЛЮЧЕНИЙ - инициализация соответствующим объектом*/
        try {
        } catch (ArithmeticException e) { // - т.е. здесь e будет объектом ArithmeticException
        }
    }
}
