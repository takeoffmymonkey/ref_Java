package types_references_classes.classes_inside;

import types_references_annotations.my_annotations.Ntrstn;


/* ПОДКЛАСС - ОБЪЯВЛЕНИЕ КЛАССА МОЖЕТ БЫТЬ ВНУТРИ ДРУГОГО КЛАССА/ИНТЕРФЕЙСА/ПЕРЕЧИСЛЕНИЯ/БЛОКА КОДА */


/* ОСОБЕННОСТИ
 * - являются членами обрамляющего класса (т.е. наследуются)
 *      - вложенный класс может имплементировать любой интерфейс
 *      - вложенный класс может наследовать любой класс
 *          - в т.ч. нестатическим статический
 *      - вложенный класс можно наследовать отдельно
 *          - в т.ч. нестатическим статический
 *
 * - как и другие члены, могут иметь любые модификаторы доступа
 *      - члены вложенных классов могут иметь любые модификаторы доступа
 *      - в интерфейсе автоматически становится статическим открытым
 *
 * - все это не относится к внутренним локальным, анонимным и лямбда-выражениям */


/* ВИДЫ
 * - статический (вложенный, classes_inside): с модификатором static
 *
 * - нестатический (внутренний, inner): без модификатора static
 *      - ДОПОЛНИТЕЛЬНЫЕ (ОСОБЫЕ) подвиды: ведут себя иначе
 *          - локальный: внутри блока кода (класс нужен только там)
 *          - анонимный: внутри блока кода, но без имени (переопределенный класс нужен одноразово)*/


/*ВНУТРЕННИЕ ЛОКАЛЬНЫЕ, АНОНИМНЫЕ И ЛЯМБДА-ВЫРАЖЕНИЯ ЯВЛЯЮТСЯ ЛОКАЛЬНЫМИ ЭЛЕМЕНТАМИ
 * - т.е. не являются членами (т.е. не наследуются), НО:
 *      - могут имплементировать любой интерфейс
 *      - могут наследовать любой класс
 *          - в т.ч. статический
 * - не могут иметь модификатор доступа
 *      - но их члены могут иметь любой модификатор доступа
 * - могут использоваться только после объявления*/


/* СТАТИЧЕСКИЙ
 * - имеет модификатор static
 * - может находится только на уровне других членов обрамляющего класса
 * - может существовать независимо от экземпляра обрамляющего класса
 * - обращение извне происходит через имя обрамляющего класса
 * - может иметь и статические члены и нестатические
 * - обрамляющий класс имеет непрямой (через имя) доступ к статическим членам
 * - имеет прямой доступ ко всем конструкторам и только к статическим членам обрамляющего класса:
 *      - в т.ч. к приватным статическим */


/* НЕСТАТИЧЕСКИЙ (ВНУТРЕННИЙ)
 * - не имеет модификатор static
 * - может находится в любом блоке (в т.ч. статическом) обрамляющего класса (хоть в for)
 * - не может существовать независимо от экземпляра обрамляющего класса
 * - обращение извне происходит через экземпляр обрамляющего класса
 * - не может иметь статических членов (т.к. ассоциирован с экземпляром обрамляющего класса)
 * - обрамляющий класс не имеет прямого доступа к членам внутреннего класса
 * - имеет прямой доступ ко всем членам и конструкторам обрамляющего класса:
 *      - в т.ч. к статическим
 *      - в т.ч. к приватным */


/* УДОБСТВО ИСПОЛЬЗОВАНИЯ
 * - логическая группировка классов, используемых только в одном месте ("машина", "двигатель")
 * - улучшение инкапсуляции (нужные "двигателю" элементы "машины" могут оставаться private)*/


@Ntrstn("Классы внутри других классов/интерфейсов/перечислений/блоков кода называются подклассами и " +
        "могут быть статическими и нестатическими. Статические классы называются вложенными (nested) " +
        "и имеют static перед объявлением. Нестатические классы называются внутренними (inner). " +
        "Кроме простого простого внутреннего, существует 2 особых его подвида: локальный и анонимный " +
        "- они могут объявляться в любом блоке кода, доступны только после объявления и только в " +
        "этом блоке, а также не могут иметь модификаторов доступа")

@Ntrstn("Ключевое отличие вложенного класса от внутреннего - первый может существовать без " +
        "экземпляра обрамляющего класса, а второй - нет. Следовательно, внутренний класс не может " +
        "иметь статических членов (иначе пришлось бы автоматически создавать экземпляр внешнего " +
        "класса). В то же время внутренние классы могут иметь static final константы времени " +
        "копмиляции (переменные-примитивы или String, которые объявлены как final и инициализированы " +
        "константным выражением во время компиляции) или могут унаследовать статические члены")

@Ntrstn("Чтобы обратиться к нестатическим членам вложенных и внутренних подклассов извне, нужно " +
        "сначала создать их экземпляры")

@Ntrstn("Обрамляющий класс не имеет доступа к нестатическим членам подклассов. А к статическим " +
        "членам есть доступ только по полному имени. Следовательно, к членам внутреннего класса " +
        "обратиться напрямую нельзя никак (т.к. у него могут быть только нестатические члены)")

@Ntrstn("Одним из преимуществ подклассов является то, что они имеют прямой доступ ко всем членам и " +
        "конструкторам (!) обрамляющего класса. Доступ есть даже к приватным членам! Но к " +
        "нестатическим членам можно обратиться только в нестатическом контексте. Следовательно, " +
        "вложенный класс имеет прямой доступ только к статическим членам обрамляющего класса и к " +
        "конструктору. Но через существующий экземпляр может обращаться даже к приватным членам " +
        "обрамляющего!")

@Ntrstn("Вложенный и внутренний (только простой!) классы являются членами обрамляющего класса - т.е. " +
        "передаются при наследовании. Также, как члены класса, они могут иметь любые модификаторы " +
        "доступа и должны находиться на уровне других членов.")

@Ntrstn("Наследование подклассов работает по следующей схеме: " +
        "1 - Вложенный класс: не может быть унаследован только классом верхнего уровня, внутри " +
        "которого он объявлен" +
        "2 - Простой внутренний класс: может быть унаследован только в своем внешнем классе и только " +
        "другим (любым) внутренним" +
        "3 - Локальный класс: может быть унаследован только в своей области действия другим локальным " +
        "классом или анонимным классом" +
        "4 - Анонимный класс: не может быть унаследован нигде")

@Ntrstn("Все виды подклассов могут независимо от обрамляющего класса наследовать любой класс или " +
        "имплементировать любой интерфейс - это второе главное преимущество - т.к. тем самым они " +
        "обеспечивают множественность реализации")

@Ntrstn("К остальным преимуществам подклассов относится возможность группировать классы логически, " +
        "т.е. внутри класса Машина будут подклассы Руль, Колесо и т.д. Кроме этого, улучшается " +
        "инкапсуляция - нужные Двигателю подклассы Машины могут оставаться скрытыми для Машины")

@Ntrstn("Если подкласс объявить внутри интерфейса, то он автоматически станет public static - т.е. " +
        "вложенным. В то же время, внутри перечисления подкласс может быть как статическим, так и " +
        "нестатическим. Т.е. члены перечисления не становятся автоматически static")

public class Main {
    int var;
    static int staticVar;
    private int privateVar;
    private static int privateStaticVar;

    public Main() {
    }

    private Main(int var) {
    }

    /*ВНУТРЕННИЙ МОЖЕТ БЫТЬ В ЛЮБОМ БЛОКЕ*/ {
        for (int i = 0; i < 1; i++) {
            /* ЛОКАЛЬНЫЙ КЛАСС */
            class InnerClassLoop { // может быть даже в цикле
            }
            /*ВНУТРЕННИЙ ЛОКАЛЬНЫЙ, АНОНИМНЫЙ И ЛЯМБДА НЕ МОЖЕТ ИМЕТЬ МОДИФИКАТОР ДОСТУПА*/
//            public class InnerClassLoop2 {
//            }
        }
    }

    /*ВНУТРЕННИЙ МОЖЕТ БЫТЬ И СТАТИЧЕСКОМ БЛОКЕ*/
    static {
        class InnerClass {
        }
    }

    void meth() {
    }

    static void staticMeth() {
    }

    private void privateMeth() {
    }

    private static void privateStaticMeth() {
    }

    /*ВОЗМОЖНЫЕ УРОВНИ ДОСТУПА - ТАКИЕ ЖЕ КАК И ДРУГИХ ЧЛЕНОВ*/
    protected class Inner {
        /*ЕСТЬ ДОСТУП КО ВСЕМ ЧЛЕНАМ И К КОНСТРУКТОРАМ
         * - в т.ч. статическим и приватным */
        /* У ЧЛЕНОВ ВЛОЖЕННЫХ ТАКЖЕ ЛЮБЫЕ УРОВНИ ДОСТУПА */
        private void innerMeth() {
            int innerVar = var;
            int innerVar2 = staticVar;
            int innerVar3 = privateVar;
            int innerVar4 = privateStaticVar;
            new Main();
            new Main(3);
            meth();
            privateMeth();
            staticMeth();
            privateStaticMeth();
        }

        int innerVar;
        // у внутреннего класса не может быть статических членов
    }

    static class StaticInner {
        /*ЕСТЬ ДОСТУП ТОЛЬКО К СТАТИЧЕСКИМ ЧЛЕНАМ И К КОНСТРУКТОРАМ
         * - в т.ч. приватным*/
        void innerMeth() {
            int innerVar = staticVar;
            int innerVar2 = privateStaticVar;
            new Main();
            new Main(3);
            staticMeth();
            privateStaticMeth();
        }

        int innerVar;
        static int innerStaticVar;
    }


    /*ВЛОЖЕННЫЙ КЛАСС МОЖНО НАСЛЕДОВАТЬ ОТДЕЛЬНО
     * - в т.ч. статический нестатическим*/
    class InnerChild extends Inner {

    }

    /* НЕСТАТИЧЕСКИЙ НАСЛЕДУЕТ СТАТИЧЕСКИЙ*/
    class StaticInnerChild extends StaticInner {

    }

    public static void main(String[] args) {
        /*НЕТ ПРЯМОГО ДОСТУПА К ЧЛЕНАМ ВНУТРЕННЕГО КЛАССА*/
        //        innerVar;


        /*ЕСТЬ НЕПРЯМОЙ ДОСТУП К СТАТИЧЕСКИМ ЧЛЕНАМ ВЛОЖЕННОГО СТАТИЧЕСКОГО КЛАССА*/
        System.out.println(StaticInner.innerStaticVar); //
//        System.out.println(StaticInner.innerVar);
    }
}

class MainSon extends Main {
    /*НАСЛЕДУЕТСЯ*/
    void mainSonMeth() {
        Inner inner = new Inner();
        StaticInner inner1 = new StaticInner();
    }
}

class AnotherClass {
    public static void main(String[] args) {
        /*ВНУТРЕННИЙ НЕ МОЖЕТ СУЩЕСТВОВАТЬ НЕЗАВИСИМО ОТ ЭКЗЕМПЛЯРА ОБРАМЛЯЮЩЕГО КЛАССА*/
        Main.Inner inner = new Main().new Inner(); // сначала создается экземпляр обрамляющего


        /*ВЛОЖЕННЫЙ СТАТИЧЕСКИЙ МОЖЕТ СУЩЕСТВОВАТЬ НЕЗАВИСИМО ОТ ЭКЗЕМПЛЯРА ОБРАМЛЯЮЩЕГО КЛАССА*/
        Main.StaticInner staticInner = new Main.StaticInner(); // сразу создается нужный объект


        /*К СТАТИЧЕСКИМ ЧЛЕНАМ СТАТИЧЕСКОГО КЛАССА ЕСТЬ НЕПРЯМОЙ ДОСТУП ВНЕ ОБРАМЛЯЮЩЕГО КЛАССА*/
        int var = Main.StaticInner.innerStaticVar; // есть доступ


        /* ВНУТРЕННИЕ КЛАССЫ, ОПРЕДЕЛЕННЫЕ В ИНТЕРФЕЙСАХ, АВТОМАТИЧЕСКИ СЧИТАЮТСЯ static public */
        SomeInterface.InterfaceInnerClass interfaceInnerClass = new SomeInterface.InterfaceInnerClass();
    }

    /* ПОДКЛАСС ВНУТРИ ПЕРЕЧИСЛЕНИЯ */
    static enum SomeEnum {
        ONE, TWO;

        /* НЕСТАТИЧЕСКИЙ */
        class Inner {
            int i;
        }

        /* СТАТИЧЕСКИЙ */
        static class InnerStatic {
            static int i;
        }
    }
}


interface SomeInterface {
    /* АВТОМАТИЧЕСКИ СТАНОВИТСЯ СТАТИЧЕСКИМ ОТКРЫТЫМ*/
    static class InterfaceInnerClass {
        int var = 4;
        static int var2 = 4;
    }
}