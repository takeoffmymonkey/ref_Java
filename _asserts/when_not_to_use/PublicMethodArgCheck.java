package _asserts.when_not_to_use;

/* НЕ ИСПОЛЬЗОВАТЬ ДЛЯ ПРОВЕРКИ АРГУМЕНТА В ПУБЛИЧНОМ МЕТОДЕ
 * - это обязательное условие вне зависимости от присутствия assert
 *
 * - иначе не будет вызвано соответствующее исключение (напр. IllegalArgumentException,
 * IndexOutOfBoundsException, NullPointerException)*/

public class PublicMethodArgCheck {

    public static void meth(int arg) {
        int[] array = new int[9];
        assert arg >= 0 && arg < 10; // НЕ БУДЕТ ВЫКИНУТО IndexOutOfBoundsException и
        // NullPointerException, а должно
        int a = array[arg];
    }
}
