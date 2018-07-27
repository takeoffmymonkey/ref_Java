package oop_inheritance;

import _types_references_annotations.my_annotations.Ntrstn;


/* ПРИВЕДЕНИЕ (ПРЕОБРАЗОВАНИЕ)
 * - когда тип ссылки и тип объекта отличаются иерархически, можно заставить программу работать с
 * объектом как с типом его ссылки
 *      - т.е. получить доступ к тем членам, которые доступны для данного типа ссылки
 *          - но не гарантировано есть у самого объекта
 *
 * - может происходить явно или неявно
 *      - неявно, когда такое действие полностью безопасно
 *          - у объекта по ссылке точно есть все члены, которые есть у типа ссылки
 *              - т.е. они унаследованы
 *
 *      - явно, когда такое действие имеет потенциальный риск
 *          - у объекта по ссылке может не быть всех членов, которые есть у типа ссылки
 *              - т.е. это родительский объект, и наследования не было
 *
 * - контролируется JVM на фактическое соотвтетствие приводимому типу при помощи системы
 * динамического определения типов (RTTI run-time type identification)
 *      - если приводимый объект на самом является родителем для типа ссылки (что приводит к
 *      загрязнение кучи), то выбрасывается ClassCastException */


/* UPCASTING - ВОСХОДЯЩЕЕ ПРЕОБРАЗОВАНИЕ
 * - присвоение объекту типа наследника ссылке типа родительского класса
 *
 * - безопасно
 *
 * - происходит автоматически
 *      - т.к. треубуется доступ только к членам, которые точно есть в родительском классе */


/* DOWNCASTING - НИСХОДЯЩЕЕ ПРЕОБРАЗОВАНИЕ
 * - присвоение объекту типа родителя ссылке типа наследуемого класса
 *
 * - потенциально опасно
 *
 * - фактический объект по ссылке обязан быть наследником или того же типа, к которому он приводится,
 * но не родителем
 *      - иначе - ClassCastException в рантайме
 *          - даже без обращения к члену этого объекта
 *
 * - необходимо делать в ручную
 *      - т.к. нет гарантии, что в реальном объекте есть поля наследника
 *          - а доступ к ним, в то же время, обещается
 *      - в скобках ставится имя типа, к которому необходимо привести тип объекта */


/* ПРИВЕДЕНИЕ ИНТЕРФЕЙСОВ
 * - класс к интерфейсу:
 *          - upcast
 *              - класс, имплементирующий интерфейс-наследник, к интерфейсу-родителю
 *
 *          - downcast (необходимо явное приведение)
 *              - класс, имплементирующий интерфейс-родитель, к интерфейсу-наследнику
 *
 * - интерфейс к классу:
 *      - всегда требуется явное приведение
 *
 * - интерфейс к интерфейсу:
 *      - upcast
 *          - интерфейс-наследник к интерфейсу-родителю
 *
 *      - downcast (необходимо явное приведение)
 *          - интерфейс-родитель к интерфейсу-наследнику
 *
 *      - между двумя родственниками одного уровня
 *          - необходимо явное приведение
 *
 * - так же, как и при приведении классов, могут быть исключения времени исполнения */


@Ntrstn("Преобразование типа объекта к типу ссылки, которая на него указывает, позволяет обращаться " +
        "к тем членам объекта, которые есть у типа ссылки. Типы должны быть связаны иерархически. ")

@Ntrstn("Если тип объекта приводится к родительскому типу ссылки, то такое преобразование безопасно " +
        "и поэтому происходит автоматически, т.к. у любого наследника гарантировано есть " +
        "родительские члены, полученные при наследование. Если тип объекта приводится к " +
        "типу-наследнику ссылки, то преобразование необходимо производить в ручную, т.к. это " +
        "потенциально опасно, поскольку нет гарантии, что у объекта есть все члены, которые есть у " +
        "типа его ссылки. На самом деле, такое преобразование может быть безопасным только в 1 " +
        "случае - реальный объект по ссылке ранее был преобразован к родительскому типу ссылки, а " +
        "теперь обратно преобразовывается к своему реальному типу")

@Ntrstn("Виртуальная машина контролирует каждое преобразование при помощи механизма динамического " +
        "определения типов во время работы программы (RTTI run-time type identification). Если " +
        "реальный родительский объект приводится к ссылке типа-наследника, то, чтобы избежать " +
        "загрязнения кучи, будет выброшено исключение ClassCastException. При этом исключение будет " +
        "выброшено уже на стадии некорректного приведения, а не только при попытке обратиться к " +
        "несуществующему члену в объекте")

@Ntrstn("У интерфейсов при приведении также работают правила восходящего и нисходящего преобразований, " +
        "но с некоторыми исключениями. Если приводится класс, имплементирующий интерфейс, к " +
        "интерфейсу или приводится интерфейс к интерфейсу, интерфейсы сравниваются по своей иерархии, " +
        "и если происходит нисходящее преобразование, то требуется явное приведение. Но здесь без " +
        "проверки компилятора также можно приводить любые (не только родственные) интерфейсы друг к " +
        "другу (необходимо явное приведение). Кроме того, если приводится интерфейс к классу, то " +
        "явное приведение требуется всегда. Исключения времени выполнения работают аналогично")
class ParentCast {
    int parentField;
}

class ChildCast extends ParentCast {
    int childField;
}

public class Casting {

    public static void main(String[] args) {
        /* НЕВОЗМОЖНО ПРИВЕСТИ ИЕРАРХИЧЕСКИ НЕ СВЯЗАННЫЕ КЛАССЫ */
        Integer i = new Integer(1);
        Double d = new Double(2.1);
//        i = (Integer) d;


        /*~~~~~~~~~~~~~~~~~~~ПРИВЕДЕНИЕ КЛАССОВ~~~~~~~~~~~~~~~~~~~*/
        /*UPCASTING*/
        ParentCast parent = new ParentCast();
        ChildCast child = new ChildCast();
        parent = child; // обещается доступ только к членам, которые есть в родителе
        int parentField = parent.parentField; // ок
//        int childField = parent.childField; // нет доступа, т.к. в родителе нет такого члена

        /* DOWNCASTING */
        ParentCast parent2 = new ParentCast();
        ChildCast child2 = new ChildCast();
//        child2 = (ChildCast) parent2; // ClassCastException в рантайме, реальный объект - ParentCast!
        ParentCast parent3 = new ChildCast();
        ChildCast child3 = new ChildCast();
        child3 = (ChildCast) parent3; // ок, т.к. реальный объект ChildCast
        int parentField2 = child2.parentField; // ок, т.к. член точно есть у объекта
        int childField2 = child2.childField; // ок, т.к. член точно есть у объекта


        /*~~~~~~~~~~~~~~~~~~~ПРИВЕДЕНИЕ ИНТЕРФЕЙСОВ~~~~~~~~~~~~~~~~~~~*/
        ParentinoImplementino parentinoImplementino = new ParentinoImplementino();
        ChildinoImplementino childinoImplementino = new ChildinoImplementino();
        Childino2Implementino childino2Implementino = new Childino2Implementino();
        Parentino parentino = new ParentinoImplementino();
        Childino childino = new ChildinoImplementino();
        Childino2 childino2 = new Childino2Implementino();

        /* КЛАСС К ИНТЕРФЕЙСУ */
        parentino = parentinoImplementino; // ok
        parentino = childinoImplementino; // upcast
        parentino = childino2Implementino; // upcast
        childino = (Childino) parentinoImplementino; // downcast
        childino = childinoImplementino; // ok
        childino = (Childino) childino2Implementino; // интерфейсы 1 уровня

        /* ИНТЕРФЕЙС К КЛАССУ */
        parentinoImplementino = (ParentinoImplementino) parentino;
        parentinoImplementino = (ParentinoImplementino) childino;
        parentinoImplementino = (ParentinoImplementino) childino2;
        childinoImplementino = (ChildinoImplementino) parentino;
        childinoImplementino = (ChildinoImplementino) childino;
        childinoImplementino = (ChildinoImplementino) childino2;

        /* ИНТЕРФЕЙС К ИНТЕРФЕЙСУ */
        parentino = childino;
        parentino = childino2;
        childino = (Childino) parentino;
        childino = (Childino) childino2;

    }
}

interface Parentino {
}

interface Childino extends Parentino {
}

interface Childino2 extends Parentino {
}

class ParentinoImplementino implements Parentino {
}

class ChildinoImplementino implements Childino {
}

class Childino2Implementino implements Childino2 {
}