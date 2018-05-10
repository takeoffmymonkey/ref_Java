package types_references_interfaces;

import types_references_annotations.my_annotations.Ntrstn;


/* НАСЛЕДОВАНИЕ
 * - интерфейс может быть унаследован
 *
 * - интерфейс может наследовать несколько интерфейсов одновременно
 *      - словом extends
 *
 * - интерфейс первого уровня также наследуется от Object
 *      - поэтому на нем доступны методы Object
 *          - их даже можно переопределять */


/* НАСЛЕДУЕТСЯ
 * - все, кроме статических методов! */


/* СОКРЫТИЕ И ПЕРЕОПРЕДЕЛЕНИЕ В ИНТЕРФЕЙСЕ-НАСЛЕДНИКЕ
 * - все унаследованные члены могут быть сокрыты членами с таким же именем в наследнике
 *      - кроме методов
 *          - для абстрактных и дефолтных - переопределение
 *              - !дефолтным методом можно определить поведение абстрактного метода из родительского
 *                  - в самом интерфейсе-родителе так сделать не получится
 *                      - конфликт имен
 *              - нельзя обратиться к дефолтной реализации в родителе
 *                  - т.к. для super нужен экземпляр
 *          - статический метод переопределить нельзя
 *              - т.к. он не наследуется
 *              - он скрывает имя родительского
 *
 * - при сокрытии обратиться к родительскому члену можно только через имя интерфейса
 *      - а не через super
 *          - т.к. они все статические, а для super нужен экземпляр
 *      - т.е. нельзя обратиться к дефолтному или абстрактному методам родителя */


/* КОНФЛИКТ ИМЕН МЕТОДОВ ПРИ РЕАЛИЗАЦИИ
 * - дефолтная реализация лишает нужды имплементировать абстрактный метод в классе или в интерфейсе-
 * наследнике, но тогда множественное наследование может привести к конфликту имен методов, при:
 *      - имплементации нескольких интерфейсов классом:
 *          - если в 2 и более имплементируемых интерфейсах есть реализация одноименного метода
 *              - РАЗРЕШЕНИЕ ПОЛУАВТОМАТИЧЕСКОЕ:
 *                  1. Реализация в классе побеждает реализацию в любом интерфейсе
 *                  2. Реализация в интерфейсе-наследнике побеждает реализацию в интерфейсе-родителе
 *                  3. Если конфликтуют не интерфейс-наследник и интерфейс-родитель, класс должен
 *                  предоставить реализацию
 *
 *      - наследовании от нескольких интерфейсов интерфейсом-наследником:
 *          - если в 2 и более интерфейсах-родителях есть одноименный метод и он в них реализован
 *          - если в 1 из интерфейсов-родителях есть реализация метода, а другой интерфейс-родитель
 *          наследует абстрактный метод с таким же именем
 *          - РАЗРЕШЕНИЕ ТОЛЬКО РУЧНОЕ:
 *              - переопрелением конфликтующих методов в интерфейсе-наследнике
 *
 * - т.е. для разрешения конфликта в первую очередь используется реализация в текущем классе/
 * интерфейсе
 *
 * - не возникают для статических методов
 *      - т.к. они не наследуются */


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


@Ntrstn("Вершиной иерархии интерфейсов также является Object, поэтому ссылочной переменной " +
        "интерфейсного типа также доступны методы Object. Их даже можно перепределять.")

@Ntrstn("Интерфейсы наследуют от родительских интерфейсов все, кроме статических методов")

@Ntrstn("Интерфейсы могут наследовать от нескольких интерфейсов одновременно")

@Ntrstn("Обратиться к сокрытому (статическому) члену через super нельзя (для super требуется " +
        "экземляр), но можно через имя родителя. К нестатическому члену (абстрактный и дефолтный " +
        "методы) обратиться нельзя.")

@Ntrstn("Дефолтный метод интерфейса может переопределить абстрактный метод родителя (но в самом " +
        "родителе этого сделать нельзя из-за конфликт имен), а также быть обратно переобъявленным в " +
        "абстрактый в наследнике")

@Ntrstn("Из-за дефолтных реализаций абстрактных методов интерфейсов и множественного наследования " +
        "могут возникать конфликты имен методов. В таких случаях неразрешимые конфликты нужно решать " +
        "реализацией метода в текущем классе/интерфейсе - она всегда считается главной. В случае " +
        "множественной реализации классом разрешение может происходить автоматически - если " +
        "реализуемые интерфейсы - родитель и наследник. Тогда реализация в наследнике побеждает " +
        "родительскую.")

@Ntrstn("У интерфейсов при приведении также работают правила восходящего и нисходящего преобразований, " +
        "но с некоторыми исключениями. Если приводится класс, имплементирующий интерфейс, к " +
        "интерфейсу или приводится интерфейс к интерфейсу, интерфейсы сравниваются по своей иерархии, " +
        "и если происходит нисходящее преобразование, то требуется явное приведение. Но здесь без " +
        "проверки компилятора также можно приводить любые (не только родственные) интерфейсы друг к " +
        "другу (необходимо явное приведение). Кроме того, если приводится интерфейс к классу, то " +
        "явное приведение требуется всегда. Исключения времени выполнения работают аналогично")


public class InheritanceConflictsCasting {

    public static void main(String[] args) {
        /* НАСЛЕДОВАНИЕ ОТ КЛАССА OBJECT */
        I i = new I() {
            @Override
            public String toString() {
                return super.toString();
            }
        };


        /* ~~~~~~~~~~~~~~КОНФЛИКТЫ ИМЕН~~~~~~~~~~~~~~ */
        Conflicter conflicter = new Conflicter();
        conflicter.meth();
        conflicter.meth2();
        conflicter.meth3();
        conflicter.defMeth();
        conflicter.defMeth2();
        conflicter.defMeth3();


        /* ~~~~~~~~~~~~~~ПРИВЕДЕНИЕ~~~~~~~~~~~~~~ */
        ParentInterfaceImplementer parentInterfaceImplementer = new ParentInterfaceImplementer();
        ChildInterfaceImplementer childInterfaceImplementer = new ChildInterfaceImplementer();
        ChildInterface2Implementer childInterface2Implementer = new ChildInterface2Implementer();
        ParentInterface parentInterface = new ParentInterfaceImplementer();
        ChildInterface childInterface = new ChildInterfaceImplementer();
        ChildInterface2 childInterface2 = new ChildInterface2Implementer();

        /* КЛАСС К ИНТЕРФЕЙСУ */
        parentInterface = parentInterfaceImplementer; // ok
        parentInterface = childInterfaceImplementer; // upcast
        parentInterface = childInterface2Implementer; // upcast
        childInterface = (ChildInterface) parentInterfaceImplementer; // downcast
        childInterface = childInterfaceImplementer; // ok
        childInterface = (ChildInterface) childInterface2Implementer; // интерфейсы 1 уровня

        /* ИНТЕРФЕЙС К КЛАССУ */
        parentInterfaceImplementer = (ParentInterfaceImplementer) parentInterface;
        parentInterfaceImplementer = (ParentInterfaceImplementer) childInterface;
        parentInterfaceImplementer = (ParentInterfaceImplementer) childInterface2;
        childInterfaceImplementer = (ChildInterfaceImplementer) parentInterface;
        childInterfaceImplementer = (ChildInterfaceImplementer) childInterface;
        childInterfaceImplementer = (ChildInterfaceImplementer) childInterface2;

        /* ИНТЕРФЕЙС К ИНТЕРФЕЙСУ */
        parentInterface = childInterface;
        parentInterface = childInterface2;
        childInterface = (ChildInterface) parentInterface;
        childInterface = (ChildInterface) childInterface2;
    }
}

/* РАЗРЕШЕНО МНОЖЕСТВЕННОЕ НАСЛЕДОВАНИЕ*/
interface I {
}

interface I2 extends I {
}

interface I3 extends I, I2 {
}


/* НАСЛЕДУЮТСЯ ВСЕ ЧЛЕНЫ, КРОМЕ СТАТИЧЕСКИХ */
interface Parent {
    int var = 1;

    void meth();

    void meth2();

    default void defMeth() {
    }

    static void statMeth() {
    }

    class NestedClass {
        int nestedVar = var;
        static int nestedStaticVar = var;

        void nestedMeth() {
        }

        static void nestedStaticMeth() {
        }
    }

    enum En {ONE}

    interface InnerInterface {
    }
}

interface Child extends Parent {
    default void defMeth2() {
        int v = var;
        meth();
        defMeth();
        Parent.statMeth();
        int v2 = NestedClass.nestedStaticVar;
        NestedClass.nestedStaticMeth();
        En e = En.ONE;
        InnerInterface innerInterface;
    }
}


/* ~~~~~~~~~~~~~~~~~~~~~~~ПЕРЕКРЫТИЕ И ПЕРЕОПРЕДЕЛЕНИЕ~~~~~~~~~~~~~~~~~~~~~~~ */
interface Child2 extends Parent {
    int var = 2;

    /* ПЕРЕОПРЕДЕЛЕНИЕ АБСТРАКТНОГО МЕТОДА */
    @Override
    void meth();

    /* ПЕРЕОПРЕДЕЛЕНИЕ ДЕФОЛТНОГО МЕТОДА */
    @Override
    default void defMeth() {
//        super.defMeth3(); // нельзя обратиться к дефолтной реализации родителя
    }

    /* ПЕРЕОПРЕДЕЛЕНИЕ ДЕФОЛТНЫМ МЕТОДОМ АБСТРАКТНОГО РОДИТЕЛЬСКОГО */
    @Override
    default void meth2() {
        /* ОБРАЩЕНИЕ К СОКРЫТЫМ ЧЛЕНАМ РОДИТЕЛЯ */
        int v = Parent.var;
        Parent.statMeth();
        Parent.NestedClass nestedClass;
        Parent.En e = Parent.En.ONE;
    }

    //    @Override // статический метод не переопределяется, а перекрывает родительский
    static void statMeth() {
    }

    class NestedClass {
        int nestedVar = var;
        static int nestedStaticVar = var;

        void nestedMeth() {
        }

        static void nestedStaticMeth() {
        }
    }

    enum En {ONE}

    interface InnerInterface {
        default void m() {
        }
    }
}


/* ~~~~~~~~~~~~~~~~~~~~~~~КОНФЛИКТЫ ИМЕН ПРИ РЕАЛИЗАЦИИ ~~~~~~~~~~~~~~~~~~~~~~~ */
class Levels {
    /*~~~~~~~~~~~LEVEL 1~~~~~~~~~~~*/
    static class Level_1 {
        interface Interface_1 {
            void meth();

            void meth2();

            void meth3();

            default void defMeth() {
                System.out.println("L1 I1 defMeth");
            }

            default void defMeth2() {
                System.out.println("L1 I1 defMeth2");
            }

            default void defMeth3() {
                System.out.println("L1 I1 defMeth3");
            }
        }

        interface Interface_2 {
            void meth();

            void meth2();

            default void defMeth() {
                System.out.println("L1 I2 defMeth");
            }

            default void defMeth2() {
                System.out.println("L1 I2 defMeth2");
            }
        }

        interface Interface_3 {
            void meth();

            void meth3();

            default void defMeth() {
                System.out.println("L1 I3 defMeth");
            }

            default void defMeth3() {
                System.out.println("L1 I1 defMeth3");
            }
        }
    }

    /*~~~~~~~~~~~LEVEL 2~~~~~~~~~~~*/
    static class Level_2 {
        interface Interface_1 extends Level_1.Interface_1, Level_1.Interface_2 {
            @Override
            default void meth() {
                System.out.println("L2 I1 meth");
            }

            @Override
            default void defMeth() {
                System.out.println("L2 I1 defMeth");
            }

            @Override
            default void defMeth2() {
                System.out.println("L2 I1 defMeth2");
            }
        }

        interface Interface_2 extends Level_1.Interface_2, Level_1.Interface_3 {
            @Override
            default void meth3() {
                System.out.println("L2 I2 meth3");
            }

            @Override
            default void defMeth() {
                System.out.println("L2 I2 defMeth");
            }
        }
    }

    /*~~~~~~~~~~~LEVEL 3~~~~~~~~~~~*/
    static class Level_3 {
        interface Interface_1 extends Level_2.Interface_1, Level_2.Interface_2 {
            @Override
            default void meth() {
                System.out.println("L3 I1 meth");
            }

            @Override
            default void meth3() {
                System.out.println("L3 I1 meth3");
            }

            @Override
            default void defMeth() {
                System.out.println("L3 I1 defMeth");
            }

            @Override
            default void defMeth3() {
                System.out.println("L3 I1 defMeth3");
            }
        }
    }
}


class Conflicter implements Levels.Level_3.Interface_1, Levels.Level_1.Interface_1 {
    @Override
    public void meth2() {
        System.out.println("Conflicter meth2");
    }
}

/* ~~~~~~~~~~~~~~~~~~~~~~~ПРИВЕДЕНИЕ~~~~~~~~~~~~~~~~~~~~~~~ */
interface ParentInterface {
}

interface ChildInterface extends ParentInterface {
}

interface ChildInterface2 extends ParentInterface {
}

class ParentInterfaceImplementer implements ParentInterface {
}

class ChildInterfaceImplementer implements ChildInterface {
}

class ChildInterface2Implementer implements ChildInterface2 {
}