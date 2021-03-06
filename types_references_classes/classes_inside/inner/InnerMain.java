package types_references_classes.classes_inside.inner;

import types_references_annotations.my_annotations.Ntrstn;


/* (ПРОСТОЙ) ВНУТРЕННИЙ КЛАСС - КЛАСС БЕЗ МОДИФИКАТОРА STATIC, ВЛОЖЕННЫЙ В ДРУГОЙ КЛАСС ИЛИ
 * ПЕРЕЧИСЛЕНИЕ*/


/* КАК ВСЕ ВЛОЖЕННЫЕ
 * - являются членами обрамляющего класса (т.е. наследуются)
 *      - вложенный класс может имплементировать любой интерфейс
 *      - вложенный класс может наследовать любой класс
 *          - в т.ч. нестатическим статический
 *      - вложенный класс можно наследовать отдельно
 *          - в т.ч. нестатическим статический
 *
 * - как и другие члены, могут иметь любые модификаторы доступа
 *      - члены вложенных классов могут иметь любые модификаторы доступа
 *      - в интерфейсе автоматически становится статическим открытым */


/* НАСЛЕДОВАНИЕ
 * - может быть унаследован только в своем внешнем классе и только другим (любым) внутренним*/


/* ДОСТУП
 * - внутренний класс имеет прямой доступ:
 *      - ко всем конструкторам обрамляющего класса
 *          - в т.ч. к приватным!!
 *      - ко всем членам обрамляющего класса:
 *          - в т.ч. к приватным!!
 *          - в т.ч. к статическим!!
 *
 * - обрамляющий класс не имеет прямого доступа к членам вложенного класса */


/* НЕ МОЖЕТ СУЩЕСТВОВАТЬ БЕЗ ЭКЗЕМПЛЯРА ОБРАМЛЯЮЩЕГО КЛАССА
 * - компилятор автоматически добавляет в конструктор внутреннего класса параметр для ссылки на
 * экземпляр обрамляющего класса */


/* ОБРАЩЕНИЕ
 * - из внутреннего к обрамляющему:
 *      - только из нестатического контекста:
 *          - либо по имени
 *          - либо через ИмяОбрамляющегоКласса.this.поле/метод (в случае затемнения)
 *
 * - к внутреннему из обрамляющего:
 *      - из нестатического контекста:
 *          - через объект внутреннего
 *      - из статического контекста:
 *          - через объект внутреннего, привязанный к объекту обрамляющего
 *
 * - к внутреннему извне обрамляющего:
 *      - из статического и нестатического контекста:
 *          - через объект внутреннего, привязанный к объекту обрамляющего */


/* НЕ МОЖЕТ ИМЕТЬ СТАТИЧЕСКИХ ЧЛЕНОВ
 * - т.к. ассоциирован с экземпляром обрамляющего класса
 * - в т.ч. не может иметь перечислений и интерфейсов (т.к. это статические члены)
 * - !! но может их унаследовать
 * - !! также может иметь переменные-константы:
 *      - это переменные-примитивы или String, которые объявлены как final и инициализированы
 *      константным выражением во время компиляции (т.е. может быть проверено во время компиляции):
 *          - напр. 3 + 5, или "стро" + "ка"
 *      - todo это как-то связанно с замыканием
 *      - todo несмотря на то, что предполагается получить однозначный экземпляр статического поля,
 *      для каждого внешнего объекта имеется отдельный экземпляр внутреннего класса. Если бы поле
 *      не было конечным, оно могло бы и не стать однозначным*/


/* ДРУГИЕ ОСОБЕННОСТИ
 * - обращение с внутренними классами происходит на уровне компилятора, а не JVM. Для их обозначения
 * используется знак $, разделяющий имена внешних и внутренних классов. В результате компиляции
 * получаются 2 класса __Implicit_Synthetic_Bridge и __Implicit_Synthetic_Bridge$Inner. Таким образом, для JVM внутренние классы неотличимы от
 * внешних
 *
 * - todo сериализация любых внутренних классов не приветствуется (из-за разных имплементаций компилятором
 * синтетических конструкций (для классов, методов, членов и т.д. - нужны, чтоб не трогать код JVM
 * при новых фичах) может возникнуть проблемы совместимости при обратной десериализации в другом JRE) */


@Ntrstn("Простой внутренний класс (inner) - это нестатический подкласс, который объявлен либо внутри " +
        "другого класса, либо перечисления. Подклассы, объявленные внутри интерфейсов, автоматически " +
        "становятся статическими (т.е. вложенными), а подклассы, объявленные в блоке кода, являются " +
        "специальными подтипами внутреннего - локальным и анонимным - они доступны только после " +
        "объявления и только в блоке, в котором объявлены, а также не могут иметь модификаторов " +
        "доступа")

@Ntrstn("Ключевой особенностью всех внутренних классов является то, что они не могут существовать " +
        "без экземпляра обрамляющего класса. Компилятор автоматически добавляет в конструктор " +
        "внутреннего класса параметр для ссылки на экземпляр обрамляющего класса. При необходимости, " +
        "получить эту ссылку можно с помощью ВнешКласс.this. Т.е. внутренний класс по сути является " +
        "объектно-ориентрированным замыканием, т.к. он содержит не только информацию об объекте " +
        "внешнего класса (место создания), но также у него есть ссылка на весь объект внешнего " +
        "класса, с помощью которой он может манипулировать всеми членами этого объекта")

@Ntrstn("Так как внутренние классы не могут существовать без экземпляров внешних классов, они не " +
        "могут иметь статические члены (иначе пришлось бы создавать экземляр внешнего класса). Но " +
        "они могут иметь static final константы времени копмиляции (переменные-примитивы или String, " +
        "которые объявлены как final и инициализированы константным выражением во время компиляции) " +
        "или могут унаследовать статические члены")

@Ntrstn("Как и для всех подклассов, чтобы обратиться к их нестатическим членам извне, сначала нужно " +
        "создать экземпляр такого подкласса. Создать экземпляр внутри обрамляющего можно по простому " +
        "имени внутреннего класса. Чтобы создать экземпляр снаружи обрамляющего класса нужно " +
        "использовать .new после ссылки на объект (а не имя) внешнего класса. При этом тип ссылки " +
        "при объявлении должен иметь форму ВнешнийКласс.ВнутреннийКласс. Напр. " +
        "Outer.Inner i = outer.new Inner();")

@Ntrstn("Обрамляющий класс не имеет доступа к нестатическим членам подклассов. Следовательно, ни к " +
        "каким членам внутреннего класса обратиться напрямую нельзя (т.к. у него могут быть только " +
        "нестатические члены)")

@Ntrstn("Одним из преимуществ подклассов является то, что они имеют прямой доступ ко всем членам и " +
        "конструкторам (!) обрамляющего класса. Доступ есть даже к приватным членам!")

@Ntrstn("Простые внутренние классы являются членами обрамляющего класса - т.е. передаются при " +
        "наследовании. Также, как члены класса, они могут иметь любые модификаторы доступа и должны " +
        "находиться на уровне других членов.")

@Ntrstn("Простой внутренний класс может быть унаследован только в своем внешнем классе и только " +
        "другим (любым) внутренним")

@Ntrstn("Все виды подклассов могут независимо от обрамляющего класса наследовать любой класс или " +
        "имплементировать любой интерфейс - это важное преимущество - т.к. тем самым они " +
        "обеспечивают множественность реализации. Обычно так и происходит: внутренний класс " +
        "наследует от класса или реализует интерфейс, а код внутреннего класса манипулирует объектом " +
        "внешнего класса, в котором он был создан.")

@Ntrstn("К остальным преимуществам подклассов относится возможность группировать классы логически, " +
        "т.е. внутри класса Машина будут подклассы Руль, Колесо и т.д. Кроме этого, улучшается " +
        "инкапсуляция - нужные Двигателю подклассы Машины могут оставаться скрытыми для Машины")

@Ntrstn("Если подкласс объявить внутри интерфейса, то он автоматически станет public static - т.е. " +
        "вложенным. В то же время, внутри перечисления подкласс может быть как статическим, так и " +
        "нестатическим. Т.е. члены перечисления не становятся автоматически static")

@Ntrstn("Обращение с внутренними классами происходит на уровне компилятора, а не JVM. Для их " +
        "обозначения используется знак $, разделяющий имена внешних и внутренних классов. В " +
        "результате компиляции получаются 2 класса Outer.class и Outer$Inner.class. Таким образом, " +
        "для JVM внутренние классы неотличимы от внешних")


public class InnerMain {
    int var; // есть доступ
    static int staticVar; // есть доступ
    private int privateVar; // есть доступ
    private static int privateStaticVar; // есть доступ

    /*ВНУТРЕННИЙ МОЖЕТ БЫТЬ В ЛЮБОМ БЛОКЕ (ТОГДА ОН СТАНОВИТСЯ ЛОКАЛЬНЫМ ИЛИ АНОНИМНЫМ) */ {
        for (int i = 0; i < 1; i++) {
            class InnerClassLoop { // может быть даже в цикле
            }
            /*ВНУТРЕННИЙ ЛОКАЛЬНЫЙ, АНОНИМНЫЙ И ЛЯМБДА НЕ МОЖЕТ ИМЕТЬ МОДИФИКАТОР ДОСТУПА*/
//            public class InnerClassLoop2 {
//            }
        }
    }

    public InnerMain() { // есть доступ
    }

    private InnerMain(int var) { // есть доступ
    }

    void meth() { // есть доступ
        /* ОБРАЩЕНИЕ К ВНУТРЕННЕМУ ИЗ ОБРАМЛЯЮЩЕГО ИЗ НЕСТАТИЧЕСКОГО КОНТЕКСТА */
        MyInnerClass myInnerClass = new MyInnerClass();
        myInnerClass.innerMeth(); // через объект внутреннего
    }

    static void staticMeth() { // есть доступ
    }

    private void privateMeth() { // есть доступ
    }

    private static void privateStaticMeth() { // есть доступ
    }

    /*ВОЗМОЖНЫЕ УРОВНИ ДОСТУПА - ТАКИЕ ЖЕ КАК И ДРУГИХ ЧЛЕНОВ*/
    public class MyInnerClass {
        /* КОМПИЛЯТОР АВТОМАТИЧЕСКИ ЗАДАЕТ В КОНСТРУКТОРЕ ССЫЛКУ НА ЭКЗЕМПЛЯР ОБРАМЛЯЮЩЕГО КЛАССА*/
        public MyInnerClass() {
            int i;
        }


        /* ЕСТЬ ДОСТУП КО ВСЕМ КОНСТРУКТОРАМ И ВСЕМ ЧЛЕНАМ
         * - в т.ч. приватным!!*/
        void innerMeth() {
            /* ОБРАЩЕНИЕ К ОБРАМЛЯЮЩЕМУ КЛАССУ ИЗ ВНУТРЕННЕГО */
            int innerVar3 = var; // напрямую по имени
            int innerVar = staticVar;
            int innerVar2 = privateStaticVar; // есть доступ к приватным членам
            new InnerMain(); // есть доступ к конструктору
            new InnerMain(3); // есть доступ к приватному конструктору
            InnerMain.this.meth(); // через ОбрамляющийКласс.this.поле/метод
            privateMeth();
            staticMeth();
            privateStaticMeth();
        }

        /* МОЖЕТ ИМЕТЬ ТОЛЬКО НЕ СТАТИЧЕСКИЕ ЧЛЕНЫ */
        int innerVar;
        //        static int innerStaticVar;
//        enum InnerEnum {} // enum - статический элемент
//        interface InnerInterface { } // interface - статический элемент


        /* СТАТИЧЕСКИЕ ЧЛЕНЫ МОГУТ БЫТЬ УНАСЛЕДОВАНЫ*/
        class MyInnerInnerClass extends InnerMain {
            void meth2() {
                staticVar = 4;
            }
        }


        /* МОЖЕТ ИМЕТЬ КОНСТАНТЫ ВРЕМЕНИ КОМПИЛЯЦИИ - НАПР. STATIC FINAL ПОЛЯ*/
        static final int innerStaticVar = 2; // значение известно при компиляции
        static final String innerStaticVar2 = "стр" + "ока"; // значение известно при компиляции
//        static final Integer innerStaticVar3 = 2; // значение неизвестно при компиляции
//        static final int staticFinal() { }; // только поля, не методы
    }


    /*МОЖЕТ БЫТЬ УНАСЛЕДОВАН ОТДЕЛЬНО ПРОСТЫМ КЛАССОМ*/
    class InnerChild extends MyInnerClass {
    }
}

/*НЕ МОЖЕТ БЫТЬ УНАСЛЕДОВАН ОТДЕЛЬНО СТАТИЧЕСКИМ КЛАССОМ*/
//static class StaticInnerClass extends MyInnerClass {
//}


/* МОЖЕТ БЫТЬ В ПЕРЕЧИСЛЕНИИ */
enum MyEnum {
    ONE, TWO;

    class EnumInnerClass {
        int enumVar;

        void enumMeth() {
        }
    }

    public static void main(String[] args) {
        /*ОБРАЩЕНИЕ К ВНУТРЕННЕМУ КЛАССУ ИЗ ОБРАМЛЯЮЩЕГО В СТАТИЧЕСКОМ КОНТЕКСТЕ*/
        InnerMain.MyInnerClass myInnerClass = new InnerMain().new MyInnerClass();
        myInnerClass.innerMeth(); // через объект внутреннего класса, привязанный к объекту обрамляющего
        MyEnum.ONE.new EnumInnerClass().enumMeth(); // через объект внутреннего класса, привязанный к объекту обрамляющего
    }
}


class AnotherClass {
    void method() {
        /*ОБРАЩЕНИЕ К ВНУТРЕННЕМУ КЛАССУ ИЗВНЕ ОБРАМЛЯЮЩЕГО В НЕСТАТИЧЕСКОМ КОНТЕКСТЕ */
        InnerMain.MyInnerClass myInnerClass = new InnerMain().new MyInnerClass();
        myInnerClass.innerMeth(); // через объект внутреннего класса, привязанный к объекту обрамляющего
    }

    public static void main(String[] args) {
        /*ОБРАЩЕНИЕ К ВНУТРЕННЕМУ КЛАССУ ИЗВНЕ ОБРАМЛЯЮЩЕГО В СТАТИЧЕСКОМ КОНТЕКСТЕ*/
        InnerMain.MyInnerClass myInnerClass = new InnerMain().new MyInnerClass(); // автоматическая привязка
        myInnerClass.innerMeth(); // через объект внутреннего класса, привязанный к объекту обрамляющего


        /* ВНУТРЕННИЕ КЛАССЫ, ОПРЕДЕЛЕННЫЕ В ИНТЕРФЕЙСАХ, АВТОМАТИЧЕСКИ СЧИТАЮТСЯ static public */
        SomeInterface.InterfaceInnerClass interfaceInnerClass = new SomeInterface.InterfaceInnerClass();
        int v = interfaceInnerClass.var;
        int v2 = SomeInterface.InterfaceInnerClass.var2;
    }
}

interface SomeInterface {
    /* АВТОМАТИЧЕСКИ СТАНОВИТСЯ СТАТИЧЕСКИМ ОТКРЫТЫМ*/
    class InterfaceInnerClass {
        int var = 4;
        static int var2 = 4;
    }
}