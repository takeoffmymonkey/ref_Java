package _oop_inheritance;

import _types_references_annotations.my_annotations.Ntrstn;

/* СОКРЫТИЕ, КОНФЛИКТЫ ИМЕН, ПЕРЕОПРЕДЕЛЕНИЕ И ПЕРЕГРУЗКА
 * - унаследованные члены могут использоваться напрямую по имени
 * - сокрытие: имя в наследнике скрывает такие же имена в родителе
 * - не происходит для private членов родителя
 * - при объявлении нового члена с таким же именем, как в родителе, он скроет или переопределит
 * родительский член:
 *      - поля (все модификаторы):
 *          - сокрытие
 *
 *      - методы:
 *          - при разной сигнатуре
 *              - перегрузка
 *          - при одинаковой сигнатуре, если родительский:
 *              - простой или абстрактный:
 *                  - переопределение
 *                      - уровень доступа наследника не должен сужать уровень родительского
 *                      - дефолтные методы интерфейсов и абстрактные методы могут вызывать конфликт
 *                      имен
 *                          - компилятор использует правила разрешения
 *                              - переопределение в наследнике всегда имеет большую силу
 *                              - см. MultipleInheritance
 *              - статический:
 *                  - если "статичность" не отличается - сокрытие
 *                  - если "статичность" отличается - ошибка компилятора
 *              - финализированный:
 *                  - ошибка компилятора
 *              - финализированный и статический:
 *                  - ошибка компилятора
 *
 *      - вложенные классы (все модификаторы):
 *              - сокрытие
 *
 *      - интерфейсы (все модификаторы):
 *              - сокрытие
 *
 *      - перечисления (все модификаторы):
 *              - сокрытие  */


@Ntrstn("Если в подклассе определен статический метод с той же сигнатурой, что и статический метод " +
        "суперкласса, то он скрывает метод суперкласса, а не переопределит")

@Ntrstn("Статичность методов родителя и наследника должна совпадать - иначе ошибка компилятора")
abstract class ParentOver {
    /* ПОЛЯ */
    int parentField;
    static int parentStaticField;
    final int parentFinalField = 0;
    static final int parentStaticFinalField = 0;


    /* МЕТОДЫ */
    void parentMeth() {
    }

    static void parentStaticMeth() {
    }

    final void parentFinalMeth() {
    }

    static final void parentStaticFinalMeth() {
    }

    abstract void parentAbstractMeth();


    /* ВЛОЖЕННЫЕ КЛАССЫ */
    class ParentInner {
    }

    static class ParentStaticInner {
    }

    final class ParentFinalInner {
    }

    static final class ParentStaticFinalInner {
    }


    /* ИНТЕРФЕЙСЫ */
    interface ParentInterface {
    }


    /* ПЕРЕЧИСЛЕНИЯ */
    enum ParentEnum {
        ONE
    }
}


class ChildOver extends ParentOver {
    /* ПОЛЯ */
    int parentField; // сокрытие
    static int parentStaticField; // сокрытие
    final int parentFinalField = 0; // сокрытие
    static final int parentStaticFinalField = 0; // сокрытие


    /* МЕТОДЫ - ОДИНАКОВАЯ СИГНАТУРА */
    @Override
    void parentMeth() { // переопределение
    }

    static void parentStaticMeth() { // сокрытие
    }

//    final void parentFinalMeth() { // ошибка компилятора
//    }

//    static final void parentStaticFinalMeth() { // ошибка компилятора
//    }

    @Override
    final void parentAbstractMeth() { // переопределение
    }


    /* МЕТОДЫ - РАЗНАЯ СИГНАТУРА */
    void parentMeth(int i) { // перегрузка
    }

    static void parentStaticMeth(int i) { // перегрузка
    }

    final void parentFinalMeth(int i) { // перегрузка
    }

    static final void parentStaticFinalMeth(int i) { // перегрузка
    }


    /* ВЛОЖЕННЫЕ КЛАССЫ */
    class ParentInner { // сокрытие
    }

    static class ParentStaticInner { // сокрытие
    }

    final class ParentFinalInner { // сокрытие
    }

    static final class ParentStaticFinalInner { // сокрытие
    }


    /* ИНТЕРФЕЙСЫ */
    interface ParentInterface { // сокрытие
    }


    /* ПЕРЕЧИСЛЕНИЯ */
    enum ParentEnum { // сокрытие
        ONE
    }
}

public class HidingOverloadingOverriding {
}