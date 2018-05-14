package types_references_classes.nested.inner;

import java.io.Serializable;

import types_references_annotations.my_annotations.Ntrstn;


/* АНОНИМНЫЙ КЛАСС - КАК ЛОКАЛЬНЫЙ - ОПРЕДЕЛЕН В ЛЮБОМ БЛОКЕ КОДА - НО НЕ ИМЕЕТ ИМЕНИ
 * - простом блоке
 * - статическом блоке
 * - в методе
 * - в конструкторе
 * - блоке if, for и т.д.
 * - в перечислении
 * - в интерфейсе:
 *      - только в дефолтной имплементации метода
 *          - в других местах любые блоки кода запрещены */


/* ИСПОЛЬЗОВАНИЕ - ПЕРЕОПРЕДЕЛЕННЫЙ КЛАСС НУЖЕН ОДНОРАЗОВО
 * - когда нужно объявлять только поля или дополнительные методы
 * - переопределение существующего класса
 * - реализация абстрактного класса
 * - реализация интерфейса
 *      - если метод только 1 (функциональный интерфейс), лучше использовать лямбда-выражение */


/* СХОДСТВО С ВНУТРЕННИМ КЛАССОМ:
 * - является его особым подвидом
 * - так же не может существовать без экземпляра обрамляющего класса
 * - так же имеет доступ ко всем членам и конструкторам обрамляющего класса (в т.ч приватным)
 *      - если объявлен в статическом блоке (в т.ч. интерфейсе и перечислении) - только к
 *      статическим членам и конструкторам
 * - так же не может иметь статических членов, потому что ассоциирован с экземпляром обрамляющего
 * класса:
 *      - при этом так же может иметь статические переменные-константы
 *      - при этом так же может унаследовать статические члены*/


/* ЭТО ЛОКАЛЬНАЯ ЕДИНИЦА
 * - (т.е. как и локальная переменная):
 *      - не является членом класса
 *          - полностью скрыт от внешнего кода
 *      - может использоваться только после объявления
 *      - не может иметь модификаторов доступа
 *          - но его члены могут иметь любой модификатор доступа
 *      - область действия всегда ограничивается блоком, в котором он объявлен:
 *          - уничтожается после выполнения блока или оператора, в котором он был создан
 *          - может затемнять и конфликтовать с именами из обрамляющих блоков */


/* КОМПИЛЯТОР СОЗДАЕТ НАСЛЕДОВАННЫЙ ТИП ОТ РОДИТЕЛЬСКОГО КЛАССА
 * - т.е. получается просто тип-наследник - расширенный или переопределенный
 * - при этом явного имени у нового типа нет:
 *      - и все новые члены доступны только до конца инструкции (до точки с запятой)
 *      - а если объект нового класса присваивается ссылке родительского типа, то доступ в дальнейшем
 *      будет только к членам, которые были в родительском классе (т.е. как при обычном приведении)*/


/* ОТЛИЧИЕ ОТ ЛОКАЛЬНЫХ КЛАССОВ
 * - не может объявлять о наследовании другого класса (т.к. является расширение определенного класса)
 * - не может объявлять об имплементации какого-то интерфейса (т.к. он сам уже может становиться
 * какой-то реализацией интерфейса)
 * - не может иметь конструкторов внутри, потому что нет имени класса
 *      - параметры, необходимые для создания объекта, передаются конструктору суперкласса
 * - новые члены (те, которых нет в родительском классе) не доступны после конца инструкции (точки с
 * запятой) */


/* ДОСТУП
 * - имеет доступ ко всем членам и конструкторам обрамляющего класса (в т.ч. приватным и статическим)
 *      - если объявлен в статическом блоке (в т.ч. интерфейсе и перечислении) - только к
 *      статическим членам и конструкторам
 * - имеет доступ только к финализированным другим локальным переменным или параметрам
 *      - т.к. при таком обращении локальный класс захватывает переменную или параметр
 *      - с J8 может обращаться без final, если переменная или параметр эффективно финализированы:
 *          - т.е. их значение не меняется после инициализации) */


/* ОБЪЯВЛЕНИЕ КЛАССА
 * - это выражение:
 *      - следовательно, оно должно быть частью инструкции (statement)
 *          - напр. инструкции, которая создает объект класса
 *          - это объясняет точку с запятой в конце
 *
 * - состоит из:
 *      - оператора new
 *      - имя интерфейса для имплементации или класса для расширения
 *      - в скобках аргументы конструктора
 *          - или пустые, если интерфейс
 *      - тело класса (только):
 *          - поля
 *          - дополнительные методы
 *          - внутренние классы
 *          - инициализаторы экземпляра */


/* ОБРАЩЕНИЕ
 * - вне блока кода обратиться нельзя
 * - в блоке кода:
 *      - до конца инструкции - можно обратиться к любому члену анонимного класса
 *      - после инструкции - только к членам родительского
 *          - если объект анонимного класса присваивался ссылочной переменной родительского класса*/


/* ОБХОД ОГРАНИЧЕНИЯ НА МНОЖЕСТВЕННОЕ НАСЛЕДОВАНИЕ
 * - класс, который наследуется от 1 конкретного класса, может фабричным методом возвращать
 * анонимный класс другого типа и, следовательно, использоваться контекстах и одного типа и другого */


@Ntrstn("Новые члены анонимного класса (не родительские) доступны только до конца инструкции (т.е. " +
        "до точки с запятой)")

@Ntrstn("Анонимные классы базируются на существующих классах/интерфейсах и явлются их " +
        "расширением (переопределение (полей)) или соответственно имплементацией")

@Ntrstn("Чтобы использовать объект внутри внутри анонимного класса, ссылка на него должна " +
        "быть final")

@Ntrstn("Анонимный класс не может содержать конструктор, так как у конструктора не может быть имени")

public class Anonymous extends ClassToOverride {
    int var; // есть доступ
    static int staticVar; // есть доступ
    private int privateVar; // есть доступ
    private static int privateStaticVar; // есть доступ
    int shadow;

    /*МОЖЕТ БЫТЬ В БЛОКЕ КОДА*/ {
        /*НЕ МОЖЕТ ОБЪЯВЛЯТЬ О НАСЛЕДОВАНИИ КАКОГО-ТО КЛАССА*/
        /*НЕ МОЖЕТ ОБЪЯВЛЯТЬ ОБ ИМПЛЕМЕНТАЦИИ КАКОГО-ТО ИНТЕРФЕЙСА*/
        /*В КОНСТРУКТОР ПЕРЕДАЮТСЯ ПАРАМЕТРЫ, НЕОБХОДИМЫЕ ДЛЯ СОЗДАНИЯ СУПЕРКЛАССА*/
        ClassToOverride classToOverride = new ClassToOverride(5) { // no extends implements
            /* МОЖЕТ ИМЕТЬ ТОЛЬКО НЕСТАТИЧЕСКИЕ ЧЛЕНЫ */
            int v;
            // static int v2;
            // static void meth () {}
            // static class StaticInner {}
            // enum InnerEnum {ONE, TWO}
            // interface InnerInterface {}

            /*МОЖЕТ УНАСЛЕДОВАТЬ СТАТИЧЕСКИЕ ЧЛЕНЫ*/
            public int v3 = staticVarToOverride;

            /* МОЖЕТ ИМЕТЬ ПЕРЕМЕННЫЕ-КОНСТАНТЫ*/
            final static int v4 = 34 + 54;


            /*НЕ МОЖЕТ ИМЕТЬ КОНСТРУКТОРОВ*/
//            ClassToOverride() {
//            }


            /*РАСШИРЕНИЕ СУЩЕСТВУЮЩЕГО КЛАССА*/
            int varToOverride = 3; // можно переобъявлять старые поля (только как нестатические)
            int staticVarToOverride2 = 3; // можно переобъявлять старые поля (только как нестатические)
            int varToOverride2; // можно объявлять новые поля

            void methToOverride() { // можно переопределять старые методы (только нестатические)
            }
//            void staticMethToOverride(){ // статические методы нельзя переопределять
//            };

            void newMeth(final int par) { // можно объявлять новые методы
                ClassToOverride c = new ClassToOverride(){
                    int parentField = par; // должно быть финализировано?
                };

                meth();
                staticMethToOverride();
            }

            class Inner extends Anonymous implements Serializable { // можно объявлять внутренние классы
            }

            {
                class InnerLocal { // можно объявлять внутренние локальные классы
                }
            }


            /* ЕСТЬ ДОСТУП КО ВСЕМУ ИЗ ОБРАМЛЯЮЩЕГО КЛАССА*/
            /* ЧЛЕНЫ МОГУТ ИМЕТЬ ЛЮБОЙ МОДИФИКАТОР ДОСТУПА*/
            private int anVar = var;
            protected int anVar2 = staticVar;
            public int anInt3 = privateVar;
            int anInt4 = privateStaticVar;
            Anonymous anonymous = new Anonymous();
            Anonymous anonymous2 = new Anonymous(3);
            MyEnum myEnum = MyEnum.ONE;
            MyInterface myInterface = new MyInterface() {
            };
            /*МОЖЕТ ПРОИСХОДИТЬ ЗАТЕМНЕНИЕ*/
            int shadow = 5; // имя shadow затемняет shadow из внешней области
        }; // в конце точка с запятой, т.к. это инструкция



        /*ДОСТУП К ЧЛЕНАМ АНОНИМНОГО КЛАССА ДО КОНЦА ИНСТРУКЦИИ - ОБЫЧНЫЙ*/
        new ClassToOverride() {
            void newMeth() {
            }
        }.newMeth(); // есть доступ ко всем членам в рамках инструкции


        /* ДОСТУП К ЧЛЕНАМ АНОНИМНОГО КЛАССА ПОСЛЕ ИНСТРУКЦИИ - ТОЛЬКО К РОДИТЕЛЬСКИМ ЧЛЕНАМ */
        ClassToOverride classToOverride1 = new ClassToOverride() {
            int v;
        }; // конец инструкции
        //        int v = classToOverride.v; // такого поля нет в родильском классе
        int v2 = classToOverride1.parentField; // такое поле есть в родительском классе


        /* МОЖЕТ БЫТЬ В ЛЮБОМ БЛОКЕ, НАПР. IF*/
        if (true) {
            new ClassToOverride() {
            };
        }


        /*МОЖЕТ БЫТЬ ИМПЛЕМЕНТАЦИЕЙ КАКОГО-ТО ИНТЕРФЕЙСА*/
        InterfaceToImplement interfaceToImplement = new InterfaceToImplement() {
            @Override
            public void methToImplement() {
                System.out.println("имплементация первого метода");
            }

            @Override
            public void methToImplement2() {
                System.out.println("имплементация второго метода");
            }
        };
        interfaceToImplement.methToImplement();


        /*ФУНКЦИОНАЛЬНЫЕ ИНТЕРФЕЙСЫ (С 1 МЕТОДОМ) ЛУЧШЕ ИМПЛЕМЕНТИРОВАТЬ ЛЯМБДА-ВЫРАЖЕНИЕМ*/
        FunctionalInterfaceToImplement functionalInterfaceToImplement =
                () -> System.out.println("имплементация единственного метода");
    }


    /* МОЖЕТ БЫТЬ В СТАТИЧЕСКОМ БЛОКЕ КОДА */
    static {
        ClassToOverride classToOverride = new ClassToOverride(3) {
            /* В СТАТИЧЕСКОМ БЛОКЕ ДОСТУП ЕСТЬ ТОЛЬКО К СТАТИЧЕСКИМ ЧЛЕНАМ И КОНСТРУКТОРАМ */
            // int v = var;
            int v2 = staticVar;
            Anonymous anonymous = new Anonymous();
            Anonymous anonymous2 = new Anonymous(3);
        };
    }


    /* МОЖЕТ БЫТЬ В КОНСТРУКТОРЕ*/
    public Anonymous() { // есть доступ
        new ClassToOverride() {
        };
    }

    /* ИМЕЕТ ДОСТУП ТОЛЬКО К (ЭФФЕКТИВНО) ФИНАЛИЗИРОВАННЫМ ДРУГИМ ЛОКАЛЬНЫМ ПЕРЕМЕННЫМ И ПАРАМЕТРАМ*/
    private Anonymous(int parameter) { // есть доступ
        int localVar = 0;
//        localVar = 6; // не должно меняться или должно быть финализировано
        new ClassToOverride() {
            int v = localVar;
            int v2 = parameter;
        };
    }


    /* МОЖЕТ БЫТЬ В МЕТОДЕ */
    void meth() { // есть доступ
        new ClassToOverride() {
        };
    }


    /* МОЖЕТ БЫТЬ В ИНТЕРФЕЙСЕ, НО ТОЛЬКО МЕТОДЕ С ДЕФОЛТНОЙ ИМПЛЕМЕНТАЦИЕЙ */
    interface MyInterface { // есть доступ
        default void meth() {
            new ClassToOverride() {
                /* В СТАТИЧЕСКОМ БЛОКЕ ДОСТУП ЕСТЬ ТОЛЬКО К СТАТИЧЕСКИМ ЧЛЕНАМ И КОНСТРУКТОРАМ */
                // int v = var;
                int v2 = staticVar;
                Anonymous anonymous = new Anonymous();
                Anonymous anonymous2 = new Anonymous(3);
            };
        }
    }


    /* МОЖЕТ БЫТЬ В ПЕРЕЧИСЛЕНИИ */
    enum MyEnum { // есть доступ
        ONE, TWO;

        {
            new ClassToOverride() {
                /* В СТАТИЧЕСКОМ БЛОКЕ ДОСТУП ЕСТЬ ТОЛЬКО К СТАТИЧЕСКИМ ЧЛЕНАМ И КОНСТРУКТОРАМ */
                // int v = var;
                int v2 = staticVar;
                Anonymous anonymous = new Anonymous();
                Anonymous anonymous2 = new Anonymous(3);
            };
        }
    }


    /*ФАБРИЧНЫЙ МЕТОД*/
    InterfaceToImplement returnAsInterfaceToImplement() {
        return new InterfaceToImplement() {

            @Override
            public void methToImplement() {
            }

            @Override
            public void methToImplement2() {
            }
        };
    }


    public static void main(String[] args) {
        /* ОБХОД ОГРАНИЧЕНИЯ НА МНОЖЕСТВЕННОЕ НАСЛЕДОВАНИЕ */
        ClassToOverride classToOverride = new Anonymous();
        InterfaceToImplement interfaceToImplement = new Anonymous().returnAsInterfaceToImplement();
    }
}


class ClassToOverride {
    int parentField;
    static int staticVarToOverride;

    public ClassToOverride() {
    }

    public ClassToOverride(int parameter) {
    }

    static void staticMethToOverride() {
    }
}


interface InterfaceToImplement {
    void methToImplement();

    void methToImplement2();
}

interface FunctionalInterfaceToImplement {
    void methToImplement();
}