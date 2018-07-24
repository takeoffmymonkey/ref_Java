package _oop_inheritance;

import _types_references_annotations.my_annotations.Ntrstn;

/* МНОЖЕСТВЕННОЕ НАСЛЕДОВАНИЕ КЛАССОВ ЗАПРЕЩЕНО
 * - класс (не считая Object) может иметь только 1 родителя
 *
 * - проблема множественного наследования - в конфликтах имен членов
 *      - член какого класса будет выбираться при работе с ним в наследнике?
 *
 * - все задачи можно решать с помощью интерфейсов
 *      - до Java 8:
 *          - у них нет полей
 *              - нет конфликта их имен
 *          - методы не реализованы
 *              - реализация в классе все равно будет только одна
 *                  - поэтому конфликта не возникнет
 *      - c Java 8:
 *          - появились поля
 *          - появились дефолтные методы (с реализацией в интерфейсе)
 *          - может возникнуть конфликт имен с именами членов в классе:
 *              - для статических методов не происходит
 *                  - т.к. они не наследуются
 *              - в таком случае разрешение происходит по правилам наследования:
 *                  1. Реализация в экземпляре побеждает реализацию в любом интерфейсе
 *                  2. Реализация в интерфейсе-наследнике побеждает реализацию в интерфейсе-родителе
 *                  3. Экземпляр, который имплементирует больше 1 равнозначного интерфейса (и там и
 *                  там есть переопределения одних и тех же методов или полей), должен сам
 *                  предоставить правильную имплементацию
 *                      - иначе ошибка компилятора
 *              - в случае наследования интерфейсами, конфликты необходимо разрешать вручную
 *
 *      - родительские методы экземпляра также могут стать реализацией методов интерфейса
 *          - через наследование */


@Ntrstn("Проблема множественного наследования - в конфликте имен членов класса: какой из них выбрать " +
        "в наследнике, если такие есть в разных классах-родителях? ")
@Ntrstn("Интерфейс решает проблему множественного наследования имен членов разных родителей: у него " +
        "нет полей, реализация его методов все равно может быть только в единичном экземпляре. " +
        "Единственная проблема может возникнуть при одинаковых названиях дефотных методов - тогда " +
        "приоритет отдается реализации в самом классе. Для статических методов конфликта имен не " +
        "происходит, т.к. они не наследуется. В случае конфликтов при наследовании у интерфейсов " +
        "требуется ручное разрешение")
@Ntrstn("Родитель может реализовывать методы интерфейса, которые имплементирует его наследник. Т.е. " +
        "реализация происходит через наследование метода!")
interface ParentInterface {
    String iField = "ParentInterface field";

    default void meth() {
        System.out.println("ParentInterface");
    }

    static void meth2() {
        System.out.println("ParentInterface");
    }
}

interface ChildInterface extends ParentInterface {
    String iField = "ChildInterface field";

    default void meth() {
        System.out.println("ChildInterface");
    }

    static void meth2() {
        System.out.println("ChildInterface");
    }
}

interface ChildInterface2 extends ParentInterface {
    String iField = "ChildInterface2 field";

    default void meth() {
        System.out.println("ChildInterface2");
    }

    static void meth2() {
        System.out.println("ChildInterface2");
    }
}

/* РЕАЛИЗАЦИЯ МОЖЕТ ПРОИСХОДИТЬ И В КЛАССЕ РОДИТЕЛЕ */
class InterfaceRealizerParent {
    public void meth() {
        System.out.println("InterfaceRealizerParent");
    }
}

/*ПРИ ОДИНАКОВЫХ ИНТЕРФЕЙСАХ - Я ДОЛЖЕН САМ ПРЕДОСТАВИТЬ ПРАВИЛЬНУЮ РЕАЛИЗАЦИЮ*/
class InterfaceRealizer extends InterfaceRealizerParent implements ChildInterface, ChildInterface2 {
    String iField = "InterfaceRealizer field";
}


public class MultipleInheritance {

    public static void main(String[] args) {
        InterfaceRealizer interfaceRealizer = new InterfaceRealizer();
        /* РЕАЛИЗАЦИЯ В ЭКЗЕМПЛЯРЕ ПОБЕЖДАЕТ */
        /* РЕАЛИЗАЦИЯ МОЖЕТ ПРОИСХОДИТЬ В РОДИТЕЛЬСКОМ МЕТОДЕ */
        interfaceRealizer.meth(); // "InterfaceRealizerParent" - реализация экземпляра побеждает
        System.out.println(interfaceRealizer.iField); // InterfaceRealizer field
    }
}
