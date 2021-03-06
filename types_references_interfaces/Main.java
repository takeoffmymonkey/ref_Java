package types_references_interfaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import types_references_annotations.my_annotations.Ntrstn;
/* ИНТЕРФЕЙС - ЭТО КОНТРАКТ
 * - класс, который его реализует, обязуется иметь в себе все методы данного интерфейса
 *      - т.е. будет известно ЧТО будет уметь класс
 *          - но не КАК
 *      - т.о. достигается отделение интерфейса от раелизации*/


/* ~~~~~~~~~~~~~~~~~~~~~~ОБЪЯВЛЕНИЕ~~~~~~~~~~~~~~~~~~~~~~
 * 1. Модификаторы
 * 2. Ключевое слово interface
 * 3. Имя интерфейса
 * 4. Если есть родители, указываются через запятую после extends
 * 5. В фигурных скобках тело интерфейса */


/* МЕСТА ОПРЕДЕЛЕНИЯ - ГДЕ ЕСТЬ МЕСТО СТАТИЧЕСКОМУ ЧЛЕНУ (НЕ СЧИТАЯ ВЕРХНЕГО УРОВНЯ)
 * - можно:
 *      - верхний уровень
 *      - член класса
 *      - член вложенного (статического) класса
 *      - член интерфейса
 *      - член перечисления
 *
 * - нельзя:
 *      - член внутреннего класса
 *          - в т.ч. локального
 *          - в т.ч. анонимного
 *       - блок кода
 *       - блок статической инициализации
 *       - тело метода
 *          - в т.ч. статического */


/* СОДЕРЖАНИЕ
 * - можно:
 *      - ничего
 *      - поля-константы
 *          - были заменой перечислений, пока они не появились в Java 5
 *      - методы:
 *          - абстрактные
 *          - дефолтные
 *          - статические
 *      - вложенные (статические) классы
 *      - перечисления
 *      - интерфейсы
 *          - необязательно реализовывать при реализации обрамляющего интерфейса
 *      - внутри методов, где возможна реализация:
 *          - локальные переменные
 *          - блоки кода
 *          - локальные классы
 *          - анонимные классы
 *
 * - нельзя:
 *      - поля-неконстанты
 *      - простые методы с реализацией
 *      - внутренние классы
 *          - станет автоматически статическим
 *              - т.е. вложенным
 *          - локальный и анонимный могут быть в методе, где возможна реализация
 *      - блок кода
 *      - блок статической инициализации
 *      - внутри методов, где возможна реализация:
 *          - все статическое */


/* ЯВНЫЕ И НЕЯВНЫЕ МОДИФИКАТОРЫ
 * - интерфейс
 *      - неявно:
 *          - abstract
 *          - static
 *              - интерфейс верхнего уровня не должен быть явно static
 *      - вне класса или как член интерфейса
 *          - может быть только:
 *              - public
 *              - без модификатора
 *      - как член класса
 *         - может иметь любой доступ
 *              - если интерфейс объявлен как private, то его видно только в области объявления
 *                  - т.е. реализовать его сможет только класс того же уровня
 *                      - при этом снаружи области тип этого класса не сможет быть типа данного
 *                      интерфейса
 *
 * - члены интерфейса
 *      - ВСЕ ЧЛЕНЫ
 *          - неявно:
 *              - public
 *      - константы:
 *          - неявно:
 *              - static
 *              - final
 *      - методы:
 *          - не могут быть:
 *              - final
 *          - абстрактные:
 *              - неявно:
 *                  - abstract
 *          - статические:
 *              - явно:
 *                  - static
 *      - остальные члены
 *          - неявно:
 *              - static
 *                  - т.е. внутренний класс станет вложенным статическим */


/* ДОСТУП К ЧЛЕНАМ ИНТЕРФЕЙСА
 * - внутри интерфейса:
 *      - к нестатическим членам (т.е. к абстрактным и дефолтным методам)
 *          - по имени
 *          - только в нестатическом контексте
 *              - т.е. только внутри дефолтного метода
 *      - к статическим членам (т.е. ко всему остальному)
 *          - по имени
 *          - везде
 *
 * - из класса, реализующего интерфейс:
 *      - к нестатическим членам (т.е. к реализованным абстрактным и дефолтным методам)
 *          - по имени
 *          - только в нестатическом контексте
 *      - к статическим методам
 *          - !через имя интерфейса
 *          - везде
 *      - к другим статическим членам
 *          - по имени
 *          - везде
 *
 * - из другого места:
 *      - только к статическим
 *      - по уточненному имени */


/* ~~~~~~~~~~~~~~~~~~~~~~ТИП~~~~~~~~~~~~~~~~~~~~~~
 * - объявление интерфейса определяет новый ссылочный тип
 *
 * - имя интерфейса можно использовать везде, где используется имя типа
 *
 * - интерфейс не может иметь экземпляров
 *      - в отличие от других ссылочных типов
 *          - кроме абстрактного класса
 *      - экземплярами становятся классы, которые имплементируют данный интерфейс
 *
 * - через ссылочную переменную можно обратиться только к методам, которые есть у данного интерфейса
 *      - поиск и вызов реального метода происходит динамически
 *          - как и для классов
 *          - т.е. динамический полиморфизм */



/* ~~~~~~~~~~~~~~~~~~~~~~ИМПЛЕМЕНТАЦИЯ~~~~~~~~~~~~~~~~~~~~~~
 * - интерфейс в итоге должен быть имплементирован */


/* СПОСОБЫ ИМПЛЕМЕНТАЦИИ ИНТЕРФЕЙСА
 * - имплементация классом интерфейса в месте объявления класса
 *      - используется оператор implements и имя интерфейса
 *
 * - имплементация анонимным классом интерфейса в месте создания класса
 *      - используется оператор new и имя интерфейса
 *
 * - имплементация лямбда-выражением
 *      - только для функциональных интерфейсов */


/* ОБЪЯВЛЕНИЕ ОБ ИМЛЕМЕНТАЦИИ КЛАССОМ В МЕСТЕ ОБЪЯВЛЕНИЯ КЛАССА
 * - implements + имена интерфейсов через запятую
 *      - после имени класса
 *          - (если есть) после имени наследуемого класса
 *              - по конвенции */


/* ИМПЛЕМЕНТАЦИЯ КЛАССОМ В МЕСТЕ ОБЪЯВЛЕНИЯ КЛАССА
 * - класс может имплементировать сразу несколько интерфейсов
 *
 * - каждый класс может предоставить свою собственную реализацию интерфейса
 *      - должен имплементировать все абстрактный методы интерфейса
 *          - для методов нужно явно указать public
 *              - т.к. компилятор может подумать, что сужается допуск при переопределении
 *          - или должен быть сам объявлен абстрактным */


/* ~~~~~~~~~~~~~~~~~~~~~~СТАТИЧЕСКИЕ И ДЕФОЛТНЫЕ МЕТОДЫ~~~~~~~~~~~~~~~~~~~~~~
 * - причина появления: если добавить в интерфейс новый метод, все текущие реализации поломаются
 *      - т.к. будет требоваться реализовать новый метод
 *      - альтернатива статическим и дефолтным методам - новый интерфейс, наследующий оригинальный */


/* ДЕФОЛТНЫЙ МЕТОД
 * - указывает на важность новой функциональности
 *      - в отличие от статического
 * - перед названием указывается ключевое слово default
 * - является автоматически public
 * - имплементирующий класс или расширяющий интерфейс может не имплементировать такой метод
 * - может реализовать абстрактный метод интерфейса-родителя
 * - может быть обратно переобъявленным в абстрактный метод в интерфейсе-наследнике */


/* СТАТИЧЕСКИЙ МЕТОД
 * - является утилитарным
 *      - в отличие от дефолтного
 * - перед названием ставится static
 * - является автоматически public
 *      - и явно static
 * - не наследуется классами/интерфейсами
 * - в статическом есть доступ только к статическому контенту интерфейса
 * - вне интерфейса обращение к статическому методу происходит по полному имени */


/* ~~~~~~~~~~~~~~~~~~~~~~ОСОБЫЕ ВИДЫ~~~~~~~~~~~~~~~~~~~~~~*/
/* ФУНКЦИОНАЛЬНЫЙ ИНТЕРФЕЙС
 * - интерфейс с 1 абстрактным методом
 *      - могут также быть дефолтные и статические
 * - используется как шаблон для лямбда-выражения
 * - помечается аннотацией @FunctionalInterface */


/* ОБОБЩЕННЫЙ ИНТЕРФЕЙС
 * - объявляются так же, как и обобщенные классы
 *      - т.е. нужно указать параметр типа после имени интерфейса
 * - при объявлении о реализации обобщенного интерфейса переменную типа нужно передать в него
 *      - если этот класс принимает параметр типа, передаваемый далее интерфейсу
 *          - т.е. это не обязательно, но обычно так и происходит
 *          - если для типа есть ограничение, то его нужно указать только для класса
 *              - а для интерфейса достаточно указать только переменную типа, т.к. она уже ограничена
 * - дает 2 преимущества:
 *      - может быть реализован для разных типов данных
 *      - позволяет наложить ограничения на типы данных, для которых он может быть реализован */


/* ОБОБЩЕННЫЙ ФУНКЦИОНАЛЬНЫЙ ИНТЕРФЕЙС
 * - указывать параметры типа в самом лямбда-выражении нельзя
 *      - т.е. само лямбда-выражение не может быть обобщенным
 *
 * - но функциональный интерфейс, связанный с лямбда-выражением, может быть обобщенным
 *      - целевой тип лямбда-выражения отчасти определяется аргументом типа или теми аргументами,
 *      которые указываются при объявлении ссылки на функциональный интерфейс */


/* МАРКЕРНЫЙ ИНТЕРФЕЙС
 * - интерфейс без методов
 * - просто "помечает" особенности класса
 *      - их можно проверить через instanceof */

@Ntrstn("Интерфейс - это контракт, который подписывает класс. Он гарантирует, что у него будут " +
        "реализованы все методы данного интерфейса. Однако то, КАК он их реализует, договоренности " +
        "нет - договоренность есть только о том, ЧТО именно он реализует. Т.е. он также, как и " +
        "абстрактный класс, способствует отделению интерфейса от его реализации")

@Ntrstn("Интерфейс является неявно abstract static, т.е. не может быть final и может быть объявлен " +
        "только там, где обычно можно объявить статический член - внутри класса верхнего уровня, " +
        "статического вложенного класса, перечисления и другого интерфейса (плюс еще можно на " +
        "верхнем уровне, если не использовать модификатор static). Соответственно, интерфейс нельзя " +
        "объявлять во внутренних классах, блоках кода, статических инициализационных блоках и методах")

@Ntrstn("Вложенный интерфейс в другой интерфейс необязательно реализовывать при реализации обрамляющего")

@Ntrstn("Внутри интерфейса нельзя объявлять простые поля и методы, блоки кода, статические блоки " +
        "инициализации и не получится объявить внутренний класс (т.к. он станет автоматически " +
        "вложенным статическим)")

@Ntrstn("До Java 5 поля-константы у интерфейса использовались для замены (пока еще) отсутствующих " +
        "перечислений")

@Ntrstn("Все члены интерфейса неявно являются public, большинство также является static (исключение: " +
        "абстрактные и дефолтные методы). Константы также являются final. Абстрактые методы неявно " +
        "abstract, статические методы - явно static.  Никакие методы не могут быть final (т.к. " +
        "основное предназначение у методов интерфейса - быть переопределенным)")

@Ntrstn("Внутри почти все члены интерфейса неявно статические (кроме абстрактных и дефолтных методов), " +
        "поэтому внутренний класс автоматически становится вложенным статическим.")

@Ntrstn("Доступ ко всем членам интерфейса внутри самого интерфейса осуществляется по имени, но к " +
        "нестатическим членам (т.е. абстрактным и дефолтным методам) можно обратиться только из " +
        "нестатического контекста, а это только тело дефолтного метода")

@Ntrstn("Вне интерфейса в классе, который не реализует данный интерфейс, есть доступ только к " +
        "статическим членам (т.е. ко всему, кроме дефолтных и абстрактных методов), и он " +
        "осуществялется по уточненному имени")

@Ntrstn("В классе, который реализует интерфейс, есть доступ ко всем членам интерфейса по имени, " +
        "кроме статического метода - для него доступ осуществляется только через имя интерфейса, т.к. " +
        "статические методы не наследуются/передаются")

@Ntrstn("При объявлении интерфейса создается новый тип и делать с ним можно все то же, что и с " +
        "другими типами, кроме как создавать экземпляры самого интерфейса. Экземплярами интерфейса " +
        "являются классы, которые их реализуют")

@Ntrstn("Как член класса интерфейс может иметь любой модификатор доступа. Но если он будет не public, " +
        "то не каждый класс сможет его реализовать, а тот, который сможет, вне зоны действия " +
        "модификатора не сможет быть приведен к типу этого интерфейса")

@Ntrstn("Вызвать методы у объекта класса, реализующего интерфейс, можно только те, которые есть в " +
        "самом интерфейсе")

@Ntrstn("Выбор нужного метода по ссылке на интерфейс происходит также благодаря динамической " +
        "диспетчеризации методов, т.е. полиморфизм реализован и для интерфейсов")

@Ntrstn("Имплементировать интерфейс можно 3 способами: 1 - классом в месте его объявления (при помощи " +
        "оператора implements и имени интерфейса); 2 - анонимным классом в месте его создания (при " +
        "помощи оператора new и имени интерфейса); 3 - лямбда-выражением (если интерфейс фунциональный)")

@Ntrstn("Класс может имплементировать несколько интерфейсов")

@Ntrstn("Класс может не имплементировать все абстрактные методы интерфейса, но тогда должен быть " +
        "сам объявлен абстрактным")

@Ntrstn("При реализации методов нужно обязательно указывать модификатор доступа public, т.к. все " +
        "методы интерфейса неявно являются public и компилятор может подумать, что сужается доступ " +
        "метода при переопределении")

@Ntrstn("Главное предназначение дефолтного и статического методов - возможность добавить в интерфейс " +
        "новые методы без необходимости обязательной реализации их в классах, которые имплементируют " +
        "изначальную версию интерфейса. В качестве альтернативы можно было бы использовать " +
        "наследование с добавлением нового функционала, однако тогда пришлось заставлять всех " +
        "реализовывать новый интерфейс-наследник")

@Ntrstn("Дефолтный метод указывает на важность нового функционала, в то время как статический больше " +
        "ассоциируется с утилитным предназначением")

@Ntrstn("Дефолтный метод интерфейса может переопределить абстрактный метод родителя (но в самом " +
        "родителе этого сделать нельзя из-за конфликт имен), а также быть обратно переобъявленным в " +
        "абстрактый в наследнике")

@Ntrstn("Функциональный интерфейс имеет 1 абстрактный метод (может также иметь статические и " +
        "дефолтные) и используется в основном как шаблон для лямбда-выражений")

@Ntrstn("Обобщенный интерфейс объявляется так же как и обобщенный класс, а класс, реализующий такой " +
        "интерфейс, если хочет принимать параметр типа, должен указать этот параматр при объявлении " +
        "- и для класса и для интерфейса. Если интерфейс работает с ограниченным параметром, то это " +
        "ограничение нужно указать только для класса в его объявлении, а для интерфейса достаточно " +
        "указать сам параметр, т.к. он передается уже ограниченным")

@Ntrstn("Обобщенный интерфейс дает 2 преимущества: 1 - его можно использовать для разных типов " +
        "классов; 2 - типы, с которым он может использоваться, могут быть ограничены")

@Ntrstn("Может быть обобщенный функциональный интерфейс. Тогда целевой тип лямбда-выражения отчасти " +
        "определяется аргументом типа или теми аргументами, которые указываются при объявлении " +
        "ссылки на функциональный интерфейс")

@Ntrstn("Маркерный интерфейс не содержит методов, а просто помечает особенности класса, чтобы их " +
        "можно было определить через instanceof")


public class Main {
    public static void main(String[] args) {
        /* ~~~~~~~~~~~~~~СОЗДАНИЕ ЭКЗЕМПЛЯРА И РЕАЛИЗАЦИЯ~~~~~~~~~~~~~~ */
//        Realizable realizable = new Realizable(); // экземпляр интерфейса создать нельзя
        Realizable realizable2 = new Realization(); // можно создать экземпляр класса, его реализующий
        Realizable realizable3 = new Realizable() { // можно реализовать в анонимном классе
            @Override
            public void realize() {
                System.out.println("This is anounimous class realization");
            }
        };
        Realizable realizable4 = () -> System.out.println("This is lambda realization");


        /* ~~~~~~~~~~~~~~ДИНАМИЧЕСКИЙ ПОЛИМОРФИЗМ~~~~~~~~~~~~~~ */
        List<Realizable> realizables = new ArrayList<>();
        realizables.add(realizable2);
        realizables.add(realizable3);
        realizables.add(realizable4);
        for (Realizable realizable : realizables) {
            realizable.realize();
        }

        /* ~~~~~~~~~~~~~~ОБОБЩЕННЫЙ ФУНКЦИОНАЛЬНЫЙ ИНТЕРФЕЙС~~~~~~~~~~~~~~ */
        GenericFunctional g = System.out::println;
        Integer integer = 2;
        g.meth(integer);
//        g.meth(new Object());
    }
}


/* ~~~~~~~~~~~~~~~~~~~~~~~РЕАЛИЗАЦИЯ~~~~~~~~~~~~~~~~~~~~~~~ */
interface Realizable {
    void realize(); // является неявно public
}

/* РЕАЛИЗАЦИЯ В МЕСТЕ ОБЪЯВЛЕНИЯ КЛАССА */
class Realization implements Realizable, Serializable { // множественная реализация
    @Override
    public void realize() { // необходимо явно указать public
        System.out.println("This is normal realization");
    }
}

/* АБСТРАКТНЫЙ КЛАСС МОЖЕТ НЕ РЕАЛИЗОВЫВАТЬ ВСЕ МЕТОДЫ*/
abstract class Realization3 implements Realizable { // абстрактный класс может не реализовывать все
}


/* ~~~~~~~~~~~~~~~~~~~~~~~ ДОСТУП ~~~~~~~~~~~~~~~~~~~~~~~ */
class Accessor {
    interface Access {
        int var = 1;

        void meth(); // нестатический

        default void defMeth() { // нестатический
            int local = var;
            meth();
            defMeth();
            statMeth();
//            int local3 = NestedClass.nestedVar; // доступ только через нестатический контекст
            int local2 = NestedClass.nestedStaticVar;
//            NestedClass.nestedMeth(); // доступ только через нестатический контекст
            NestedClass.nestedStaticMeth();
            En e = En.ONE;
            InnerInterface innerInterface;
        }

        static void statMeth() { // статический
            int local = var;
//            meth(); // доступ только через нестатический контекст
//            defMeth(); // доступ только через нестатический контекст
            statMeth();
//            int local3 = NestedClass.nestedVar; // доступ только через нестатический контекст
            int local2 = NestedClass.nestedStaticVar;
//            NestedClass.nestedMeth(); // доступ только через нестатический контекст
            NestedClass.nestedStaticMeth();
            En e = En.ONE;
            InnerInterface innerInterface;
        }

        class NestedClass { // статический
            int nestedVar = var; // нестатический
            static int nestedStaticVar = var; // статический

            void nestedMeth() { // нестатический
                int local = var;
//                meth(); // доступ только через нестатический контекст
//                defMeth(); // доступ только через нестатический контекст
                statMeth();
                int local3 = nestedVar;
                int local2 = NestedClass.nestedStaticVar;
                nestedMeth();
                nestedStaticMeth();
                En e = En.ONE;
                InnerInterface innerInterface;
            }

            static void nestedStaticMeth() { // статический
                int local = var;
//                meth(); // доступ только через нестатический контекст
//                defMeth(); // доступ только через нестатический контекст
                statMeth();
//                int local3 = nestedVar; // доступ только через нестатический контекст
                int local2 = nestedStaticVar;
//                nestedMeth(); // доступ только через нестатический контекст
                nestedStaticMeth();
                En e = En.ONE;
                InnerInterface innerInterface;
            }
        }

        enum En {
            ONE;

            void m() {
                int local = var;
//                meth(); // доступ только через нестатический контекст
//                defMeth(); // доступ только через нестатический контекст
                statMeth();
//                int local3 = NestedClass.nestedVar; // доступ только через нестатический контекст
                int local2 = NestedClass.nestedStaticVar;
//                NestedClass.nestedMeth(); // доступ только через нестатический контекст
                NestedClass.nestedStaticMeth();
                En e = ONE;
                InnerInterface innerInterface;
            }
        }

        interface InnerInterface {
            default void m() {
                int local = var;
//                meth(); // доступ только через нестатический контекст
//                defMeth(); // доступ только через нестатический контекст
                statMeth();
//                int local3 = NestedClass.nestedVar; // доступ только через нестатический контекст
                int local2 = NestedClass.nestedStaticVar;
//                NestedClass.nestedMeth(); // доступ только через нестатический контекст
                NestedClass.nestedStaticMeth();
                En e = En.ONE;
                InnerInterface innerInterface;
            }
        }
    }
}

class NonImplementer {
    void m() {
        int local = Accessor.Access.var;
//        Accessor.Access.meth();
//        Accessor.Access.defMeth();
        Accessor.Access.statMeth();
//        int local2 = Accessor.Access.NestedClass.nestedVar; // доступ только через нестатический контекст
        int local3 = Accessor.Access.NestedClass.nestedStaticVar;
//        Accessor.Access.NestedClass.nestedMeth();
        Accessor.Access.NestedClass.nestedStaticMeth();
        Accessor.Access.En e = Accessor.Access.En.ONE;
        Accessor.Access.InnerInterface innerInterface;
    }

    static void sm() {
        int local = Accessor.Access.var;
//        Accessor.Access.meth();
//        Accessor.Access.defMeth();
        Accessor.Access.statMeth();
//        int local2 = Accessor.Access.NestedClass.nestedVar; // доступ только через нестатический контекст
        int local3 = Accessor.Access.NestedClass.nestedStaticVar;
//        Accessor.Access.NestedClass.nestedMeth();
        Accessor.Access.NestedClass.nestedStaticMeth();
        Accessor.Access.En e = Accessor.Access.En.ONE;
        Accessor.Access.InnerInterface innerInterface;
    }
}

class Implementer implements Accessor.Access {
    void m() {
        int local = var;
        meth();
        defMeth();
        Accessor.Access.statMeth();
//        int local2 = NestedClass.nestedVar; // доступ только через нестатический контекст
        int local3 = NestedClass.nestedStaticVar;
//        NestedClass.nestedMeth();
        NestedClass.nestedStaticMeth();
        En e = Accessor.Access.En.ONE;
        InnerInterface innerInterface;
    }

    static void sm() {
        int local = Accessor.Access.var;
//        meth();
//        defMeth();
        Accessor.Access.statMeth();
//        int local2 = NestedClass.nestedVar; // доступ только через нестатический контекст
        int local3 = NestedClass.nestedStaticVar;
//        NestedClass.nestedMeth();
        NestedClass.nestedStaticMeth();
        En e = En.ONE;
        InnerInterface innerInterface;
    }

    @Override
    public void meth() {
    }
}


/* ~~~~~~~~~~~~~~~~~~~~~~~МЕСТА ОПРЕДЕЛЕНИЯ~~~~~~~~~~~~~~~~~~~~~~~ */
/* НА ВЕРХНЕМ УРОВНЕ */
interface TopLevel {
}

class SomeClass {
    /* НА УРОВНЕ ЧЛЕНОВ КЛАССА */
    static interface ClassMembersLevel {
        /* НА УРОВНЕ ЧЛЕНОВ ИНТЕРФЕЙСА */
        static interface InterfaceLevel {
        }
    }

    /* НА УРОВНЕ ЧЛЕНОВ ВЛОЖЕННОГО СТАТИЧЕСКОГО КЛАССА */
    static class NestedClass {
        private interface NestedClassMembersLevel1 {
        }
    }

    /* НА УРОВНЕ ЧЛЕНОВ ПЕРЕЧИСЛЕНИЯ */
    enum En {
        ONE;

        interface Local2 {
        }
    }


    /* ~~~~~~~~~~~~~~~~~~~~~~~ЗАПРЕЩЕННЫЕ МЕСТА ОПРЕДЕЛЕНИЯ~~~~~~~~~~~~~~~~~~~~~~~ */
    /* БЛОК КОДА */ {
//        interface Local {}
    }

    /* БЛОК СТАТИЧЕСКОЙ ИНИЦИАЛИЗАЦИИ */
    static {
//        interface Local2 {}
    }

    /* ТЕЛО МЕТОДА */
    void meth() {
//        interface Local3 {}
    }

    /* ТЕЛО СТАТИЧЕСКОГО МЕТОДА */
    static void staticMeth() {
//        interface Local3 {}
    }

    /* ВНУТРЕННИЕ КЛАССЫ */
    class InnerClass {
        //        interface Local4 {}
        {
            /* ЛОКАЛЬНЫЕ КЛАССЫ */
            class LocalClass {
//                interface Local5 {}
            }
            /* АНОНИМНЫЕ КЛАССЫ */
            LocalClass localClass = new LocalClass() {
//                interface Local5 {}
            };
        }
    }
}


/* ~~~~~~~~~~~~~~~~~~~~~~~СОДЕРЖАНИЕ~~~~~~~~~~~~~~~~~~~~~~~ */
interface Content {
    /* ~~~~~~~~~~~~~~МОЖНО~~~~~~~~~~~~~~*/
    /* КОНСТАНТЫ */
    int var = 0;

    /* АБСТРАКТНЫЕ МЕТОДЫ */
    void meth();

    /* ДЕФОЛТНЫЕ МЕТОДЫ */
    default void defMeth() {
        /* ЛОКАЛЬНЫЕ ПЕРЕМЕННЫЕ */
        int local;
        /* ЛОКАЛЬНЫЙ КЛАСС ВНУТРИ МЕТОДА С РЕАЛИЗАЦИЕЙ */
        class InnerMethodClass {
        }
        /* АНОНИМНЫЙ ЛОКАЛЬНЫЙ КЛАСС ВНУТРИ МЕТОДА С РЕАЛИЗАЦИЕЙ */
        InnerMethodClass inn = new InnerMethodClass() {
        };
        /* БЛОК КОДА */
        {
        }
    }

    /* СТАТИЧЕСКИЕ МЕТОДЫ */
    static void statMeth() {
        /* ЛОКАЛЬНЫЕ ПЕРЕМЕННЫЕ */
        int local;
        /* ЛОКАЛЬНЫЙ КЛАСС ВНУТРИ МЕТОДА С РЕАЛИЗАЦИЕЙ */
        class InnerMethodClass {
        }
        /* АНОНИМНЫЙ ЛОКАЛЬНЫЙ КЛАСС ВНУТРИ МЕТОДА С РЕАЛИЗАЦИЕЙ */
        InnerMethodClass inn = new InnerMethodClass() {
        };
    }

    /* ВЛОЖЕННЫЕ (СТАТИЧЕСКИЕ) КЛАССЫ */
    class NestedStaticClass {
    }

    /* ПЕРЕЧИСЛЕНИЯ */
    enum En {
    }

    /* ИНТЕРФЕЙСЫ */
    interface InnerInterface {
    }

    /* ~~~~~~~~~~~~~~НЕЛЬЗЯ~~~~~~~~~~~~~~*/
    /*НЕКОНСТАНТНЫЕ ПОЛЯ*/
//    int var2;

    /*ПРОСТЫЕ МЕТОДЫ */
//    void justMeth(){}

    /* БЛОКИ КОДА */
//    {}

    /* БЛОКИ СТАТИЧЕСКОЙ ИНИЦИАЛИЗАЦИИ */
//    static {}

}


/* ~~~~~~~~~~~~~~~~~~~~~~~ДЕФОЛТНЫЕ МОДИФИКАТОРЫ~~~~~~~~~~~~~~~~~~~~~~~ */
abstract strictfp interface TopLevel2 {
    public static final int var = 0;

    abstract public void meth();

    public default strictfp void defMeth() {
    }

    public static strictfp void statMeth() {
    }

    public static final strictfp class Inner {
    }

    public static enum En {}
}

class SomeClass2 {
    abstract static private strictfp interface ClassMembersLevel {
        public static final int var = 0;

        abstract public void meth();

        public default strictfp void defMeth() {
        }

        public static strictfp void statMeth() {
        }

        public static final strictfp class Inner {
        }

        public static enum En {}

        abstract static strictfp interface InterfaceLevel {
            public static final int var = 0;

            abstract public void meth();

            public default strictfp void defMeth() {
            }

            public static strictfp void statMeth() {
            }

            public static final strictfp class Inner {
            }

            public static enum En {}
        }
    }

    static class NestedClass {
        abstract static strictfp interface NestedClassMembersLevel {
            public static final int var = 0;

            abstract public void meth();

            public default strictfp void defMeth() {
            }

            public static strictfp void statMeth() {
            }

            public static final strictfp class Inner {
            }

            public static enum En {}
        }
    }

    enum En {
        ONE;

        abstract static strictfp interface Local2 {
            public static final int var = 0;

            abstract public void meth();

            public default void defMeth() {
            }

            public static void statMeth() {
            }

            public static strictfp class Inner {
            }

            public static enum Enu {}
        }
    }
}


/* ~~~~~~~~~~~~~~~~~~~~~~~ФУНКЦИОНАЛЬНЫЙ ИНТЕРФЕЙС ~~~~~~~~~~~~~~~~~~~~~~~*/
@FunctionalInterface
interface Functional {
    /*ТОЛЬКО 1 АБСТРАКТНЫЙ МЕТОД*/
    void meth();

    /* СКОЛЬКО УГОДНО ДЕФОЛТНЫХ И СТАТИЧЕСКИХ МЕТОДОВ */
    default void defMeth() {
    }

    static void statMeth() {
    }
}


/* ~~~~~~~~~~~~~~~~~~~~~~~ОБОБЩЕННЫЙ ИНТЕРФЕЙС~~~~~~~~~~~~~~~~~~~~~~~*/
interface Generic<T> {
    void methGen(T t);

    default <E> void defGen(List<E> list) {
    }
}

/* ОГРАНИЧЕНИЕ ПАРАМЕТРА ТИПА */
interface GenericLimited<T extends Number> {
    void methGen(T t);

    default <E> void defGen(List<E> list) {
    }
}

// todo при наследовании можно тип не указывать - тогда передается объектом?
interface GenericSon extends Generic {
}

/* В ОБЪЯВЛЕНИИ КЛАССА НУЖНО УКАЗАТЬ ПЕРЕМЕННУЮ ТИПА */
class GenericImplementer<T> implements Generic<T> {
    @Override
    public void methGen(T t) {
    }
}

/* ОГРАНИЧЕНИЕ ПЕРЕДАЕТСЯ НУЖНО УКАЗАТЬ ТОЛЬКО ДЛЯ КЛАССА */
class GenericLimitedImplementer<T extends Number> implements GenericLimited<T> {
    @Override
    public void methGen(T t) {
    }
}


/* ~~~~~~~~~~~~~~~~~~~~~~~ОБОБЩЕННЫЙ ФУНКЦИОНАЛЬНЫЙ ИНТЕРФЕЙС~~~~~~~~~~~~~~~~~~~~~~~*/
@FunctionalInterface
interface GenericFunctional<T extends Number> {
    void meth(T number);
}

/* ~~~~~~~~~~~~~~~~~~~~~~~МАРКЕРНЫЙ ИНТЕРФЕЙС~~~~~~~~~~~~~~~~~~~~~~~ */
interface Marker {
    /* БЕЗ МЕТОДОВ ВООБЩЕ */
}