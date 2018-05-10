package oop_inheritance;

import types_references_annotations.my_annotations.Ntrstn;


/* SUPER */


/* ЗАЧЕМ
 * - super:
 *      - помогает обратится к нужному члену родительского класса при сокрытии
 * - super():
 *      - вызов родительского конструктора */


/* ЯВЛЯЕТСЯ НЕСТАТИЧЕСКОЙ ПЕРЕМЕННОЙ
 * - на нее нельзя ссылаться из статического контекста
 * - как и this */


/* НЕ ЯВЛЯЕТСЯ ССЫЛКОЙ НА ОБЪЕКТ
 * - только сообщает компилятору, что нужно сделать вызов из родителя
 * - в отличие от this */


@Ntrstn("this и super являются нестатическими переменными - их нельзя использовать в статическом " +
        "контексте")
@Ntrstn("super, в отличие от this, не является ссылочной переменной (нельзя, напр. присвоить " +
        "значение другой объектной переменной) - она только сообщает компилятору, что нужно сделать " +
        "вызов из родителя")
class ParentSuper {
    int parentSuperField;
}

class ChildSuper extends ParentSuper {
    /* ОБРАЩЕНИЕ К РОДИТЕЛЬСКОМУ ЧЛЕНУ ПРИ СОКРЫТИИ */
    int parentSuperField = super.parentSuperField;


    /* ВЫЗОВ РОДИТЕЛЬСКОГО КОНСТРУКТОРА */
    public ChildSuper() {
        super();
    }


    /* ЯВЛЯЕТСЯ НЕСТАТИЧЕСКОЙ ПЕРЕМЕННОЙ КАК И THIS*/
    static void staticChildSuperMeth() {
//        int parentSuperField2 = super.parentSuperField; // вызов из статического контекста запрещен
//        int parentSuperField2 = this.parentSuperField; // вызов из статического контекста запрещен
    }


    void childSuperMeth() {
        Object o = new Object();
        /* НЕ ЯВЛЯЕТСЯ ССЫЛОЧНОЙ ПЕРЕМЕННОЙ В ОТЛИЧИЕ ОТ THIS */
//        o = super;
        o = this; // this является ссылочной переменной
    }
}

public class Super {
}