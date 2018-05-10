package modifiers_other;

/* - Обозначает, что метод написан на другом языке программирования
 *
 * - Классы в Java используют много native методов для повышения производительности и доступа к
 * аппаратным средствам*/


public class NativeModifier {

    /*НАТИВНЫЙ МЕТОД
     * - native должен использоваться с методом
     * - должен заканчиваться ;
     * - внутрь можно передавать и получать обратно объекты Java*/
    native void method(int javaVar);

}

interface MyInterface {
    /*ПЕРЕМЕННАЯ ИНТЕРФЕЙСА
     * - не может быть native*/
    int nonNativeVar = 0;

    /*МЕТОД ИНТЕРФЕЙСА
     * - не может быть native*/
    void nonNativeMethod();
}