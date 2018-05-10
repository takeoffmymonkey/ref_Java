package asserts.when_not_to_use;

/* НЕ ИСПОЛЬЗОВАТЬ ДЛЯ ВЫПОЛНЕНИЯ ЗАДАЧ, КОТОРЫЕ ТРЕБУЕТСЯ ДЛЯ НОРМАЛЬНОЙ РАБОТЫ ПРИЛОЖЕНИЯ
 * - т.е. оно не должно никак влиять на состояние приложение
 *
 * - ИСКЛЮЧЕНИЕ: можно менять состояние, если оно используется в других assert*/

import java.util.ArrayList;

public class AppNecessaryWorkInAssert {

    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(231);
        integers.add(2323);
        integers.add(212);

        /* КАК НЕ НАДО */
        assert integers.contains(231); // Не выполнится при отключенных assert!!!
        System.out.println("В листе есть 231");

        /* КАК НАДО */
        if (integers.contains(231)) System.out.println("В листе есть 231");
    }
}
