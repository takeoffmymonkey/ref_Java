package types_references_classes;

/*ЧЛЕНЫ КЛАССА
 * - это то, что наследуется:
 *      - поля
 *      - методы
 *      - вложенные классы
 *      - вложенные интерфейсы (это статический член)
 *      - перечисления (это статический член)
 *
 * - т.е. конструктор не является членом класса*/


/* ВИДЫ ЧЛЕНОВ
 * - члены экземпляра
 *      - принадлежат экземпляру
 *      - разные для каждого экземпляра
 *      - обращение через:
 *          - объект класса
 *
 * - члены класса (статические):
 *      - принадлежат всему классу
 *      - существуют в единственном экземпляре
 *      - объявляются со словом static
 *          - не считая интерфейсов и перечислений
 *      - обращение через:
 *          - имя класса (рекомендовано)
 *          - объект класса*/


import types_references_annotations.my_annotations.Ntrstn;


@Ntrstn("члены класса - это только то, что наследуется (поля, методы, внутренние класса, но не " +
        "конструкторы, т.к. они вызываются у суперкласса)")
@Ntrstn("чтобы создать экземпляр внутреннего класса, сначала нужно создать экземпляр внешнего: " +
        "new Outer().new Inner()")
@Ntrstn("вложенные интерфейсы и перечисления - это статические члены")
public class Members {
    int field; // член
    static int staticField; // статический член

    public Members() { // НЕ ЯВЛЯЕТСЯ ЧЛЕНОМ
    }

    void meth() { // член
    }

    static void staticMeth() { // статический член
    }

    class InnerClass { // член
    }

    static class InnerStaticClass { // статический член
        static int innerStaticClassField;
    }


    interface InnerInterface { // todo статический член?
    }

    enum InnerEnum { // статический член
        ONE, TWO;

    }

    @interface InnerAnnotation { // todo наврядли аннотация - член класса, но хз как проверить
    }
}

class MemberSon extends Members {

    public static void main(String[] args) {
        MemberSon memberSon = new MemberSon();
        int field = memberSon.field;
        int staticField = memberSon.staticField;
        memberSon.meth();
        memberSon.staticMeth();
        MemberSon.InnerClass innerClass = new MemberSon().new InnerClass();
        int innerStaticClassField = MemberSon.InnerStaticClass.innerStaticClassField;
        MemberSon.InnerInterface innerInterface = new MemberSon.InnerInterface() {
        };
        MemberSon.InnerEnum e = MemberSon.InnerEnum.ONE;
    }
}