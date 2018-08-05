package _asserts;

/* По дефолту отключены для runtime
 *
 * - 2 опции командной строки:
 *   + -enableassertions (или -ea)
 *   + -disableassertions (или da)
 *
 * - можно указать гранулярность (классы) переключателю:
 *   + без аргументов - переключает для всех классов, кроме системных (иначе: -enablesystemassertions
 *   (или esa) и disablesystemassertions (dsa))
 *   + имяПакета - для пакета и подпакетов
 *   + ... - для текущей рабочей директории
 *   + имяКласса - для указанного класса
 *
 * - статус assert-ности устанавливается во время инициализации и не меняется (но можно, хоть и не
 * желательно выполнять методы или конструкторы до инициализации - в таком случае выполнение
 * программы должно вести себя, как если бы assert были включены)
 *
 * - для разрешения и запрета утверждений компилировать программу заново не нужно. Это задача
 * загрузчика классов. Если диагностические утверждения запрещены, загрузчик классов проигнорирует
 * их код, чтобы не замедлять выполнение программы. */


public class Enabling_Disabling {
    /* ПРИМЕР:
     * включить для 1 класса: java -ea:com.wombat.fruitbat.MyClass
     *
     * в 1 строке: java -ea:com.wombat.fruitbat... -da:com.wombat.fruitbat.Brickbat <class>
     *
     * для системных и остальных: java -esa -ea:com.wombat.fruitbat... */

}


