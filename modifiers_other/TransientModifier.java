package modifiers_other;

/* Переменная transient указывает JVM, чтобы пропустить определённую переменную при сериализации
объекта, содержащего её */

public class TransientModifier {
    /*ПОЛЕ
     * - может быть transient
     * - может НЕ быть final или static
     * - не сериализируется*/
    transient int transientField;

    void method() {
        /*ЛОКАЛЬНАЯ ПЕРЕМЕННАЯ
         * - не может быть transient*/
        int nonTransientVar;
    }
}
