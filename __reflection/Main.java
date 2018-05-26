package __reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

import types_references_annotations.my_annotations.Ntrstn;

/* РЕФЛЕКСИЯ ПОЗВОЛЯЕТ ИССЛЕДОВАТЬ И ИЗМЕНЯТЬ ПОВЕДЕНИЕ ПРОГРАММЫ ВО ВРЕМЯ РАБОТЫ
 * - можно создавать объекты от внешних файлов классов
 * - доступна информация о всех (в т.ч. приватных) членах класса
 *      - полезно для визуальных средств программирования
 *      - полезно для средств тестирования
 *          - где желателен доступ к приватным членам */


/* МИНУСЫ РЕФЛЕКСИИ
 * - ухудшение производительности
 *      - из-за типов, которые разрешаются динамически и JVM не может произвести некоторые
 *      оптимизации
 *
 * - ограничение безопасности
 *      - todo требует runtime права, которого может не быть при работе в security manager (Applet)
 *
 * - доступность скрытого
 *      - доступность приватных членов может привести неожиданным побочным эффектам, которые могут
 *      вызвать нарушения в коде и нарушить переносимость (portability). Рефлектный код нарушает
 *      абстрактность и таким образом может изменить поведение при обновлении платформы */



/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~КЛАССЫ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * - для каждого типа объекта JVM инициализирует неизменяемый экземпляр java.lang.Class
 *      - предоставляет методы для изучения runtime-свойств объекта
 *          - в т.ч. его члены и информацию о типе
 *      - позволяет создавать новые классы и объекты
 *      - является входной точкой для всего Reflection APIs
 *
 *      java.lang
Class Class<T>
java.lang.Object
java.lang.Class<T>
Type Parameters:
T - the type of the class modeled by this Class object. For example, the type of String.class is Class<String>. Use Class<?> if the class being modeled is unknown.
All Implemented Interfaces:
Serializable, AnnotatedElement, GenericDeclaration, Type
 *      */




/* ~~~~~~~~~~~~~~~~~~~ПОЛУЧЕНИЕ ОБЪЕКТОВ CLASS~~~~~~~~~~~~~~~~~~~~~
 * - ни 1 из классов в java.lang.reflect не имеет публичных конструкторов
 *      - за исключением java.lang.reflect.ReflectPermission
 *      - чтобы получить классы, нужно воспользоваться методами класса Class
 *
 *
 * - при получении объекта Class по имени нужного класса, для массивов используется особый синтаксис:
 *      - применим как ссылочным массивам, так и к примитивным
 *          - [ значит 1 измерение массива
 *          - boolean: Z
 *          - byte:	B
 *          - char: C
 *          - класс/интерфейс Lclassname;
 *          - double: D
 *          - float: F
 *          - int: I
 *          - long: J
 *          - short: S
 *          - напр. [[D - значит 2-мерный массив из элементов типа double
 *
 *
 * - методы получения объекта Class:
 *      - объект.getClass(): когда есть нужный объект
 *          - только для ссылочных типов
 *              - т.е. наследующих от Object
 *
 *      - Тип.class: для примитивов и void или когда есть тип, но нет объекта
 *
 *      - Оболочка.TYPE: альтернатива для получения примитивов и void от их оболочек
 *          - у каждого класса-оболочки есть поле TYPE с классом их примитива
 *
 *      - Class.forName(): когда есть полное имя класса
 *          - работает только для ссылочных типов
 *          - может выбросить исключение ClassNotFoundException
 *
 *
 * - методы получения объекта Class от другого Class:
 *      - класс.getSuperclass(): получить родителя данного класса
 *
 *      - класс.getEnclosingClass(): получить обрамляющий класс
 *
 *      - класс.getClasses(): массив из всех публичных(!) классов, интерфейсов и перечислений, которые
 *      являются членами данного класса (в т.ч. унаследованные)
 *
 *      - класс.getDeclaredClasses(): массив из всех классов, интерфейсов и перечислений, которые
 *      явно объявлены в данном классе
 *
 *      - класс.getDeclaringClass(): возвращает класс, в котором объявлен данный член
 *          - не работает для анонимных классов
 *              - но можно получить обрамляющий класс
 *          - todo соответствующие аналоги для полей, методов и конструкторов:
 *              - java.lang.reflect.Field.getDeclaringClass()
 *              - java.lang.reflect.Method.getDeclaringClass()
 *              - java.lang.reflect.Constructor.getDeclaringClass() */





/* ~~~~~~~~~~~~~~~~~~~ПОЛУЧЕНИЕ МОДИФИКАТОРОВ И ТИПОВ (ОБОБЩЕННЫЕ) КЛАСС java.lang.reflect.Modifier: МОДИФИКАТОРЫ КЛАССА И ТИПЫ~~~~~~~~~~~~~~~~~~~~~
 * - int класс.getModifiers(): возвращает модификаторы данного класса одним числов
 *      - чтобы его декодировать в модификаторы, нужно использовать методы класса
 *      java.lang.reflect.Modifier
 *          - напр. Modifier.toString(int mod) вернет "public static final"
 *      - для массивов, их модификаторы public, private и protected соответствуют этим модифкаторам
 *      в элементах
 *      - для примитивов и void, модификатор public всегда true, а private false
 *      - If this object represents an array class, a primitive type or void, then its final modifier is always true and its interface modifier is always false. The values of its other modifiers are not determined by this specification
 *
 * - java.lang.reflect.TypeVariable
 *
 *
 * - также и аннотации
 *
 * - получить модификаторы для:
 *      - класса: Class.getModifiers()
 *
 *
 * - константы: все возможные модификаторы
 *      -
 *
 * - методы:
 *      - возвращают возможные модификаторы для интерфейса, класса, конструктора, полей
 *          -
 *      - декодируют возвращенные
 *
 * */


/* не все аннотации доступны во время работы программы, а только те, у которых стоит
java.lang.annotation.RetentionPolicy RUNTIME*/



/* ~~~~~~~~~~~~~~~~~~~ПОЛУЧЕНИЕ ОСТАЛЬНЫХ ЧЛЕНОВ КЛАССА~~~~~~~~~~~~~~~~~~~~~
 * - 2 типа методов для получения членов:
 *      - для перечисления членов
 *      - для поиска нужного члена
 *
 * - методы ищут члены:
 *      - либо объявленные в самом классе
 *      - либо унаследованные */


/* МЕТОДЫ ПОЛУЧЕНИЯ ПОЛЕЙ
 * - getDeclaredField(): вернет указанное поле, если оно объявлено в классе, в т.ч. приватное
 * - getDeclaredFields(): вернет указанные поля, если они объявлены в классе, в т.ч. приватные
 * - getField(): вернет указанное поле, если оно также и унаследованно, но не приватное
 * - getFields(): вернет указанные поля, если они также и унаследованны, но не приватные*/


/* МЕТОДЫ ПОЛУЧЕНИЯ МЕТОДОВ
 *  - getDeclaredMethod(): вернет указанный метод, если он объявлен в классе, в т.ч. приватные
 * - getDeclaredMethods(): вернет указанные методы, если они объявлены в классе, в т.ч. приватные
 * - getMethod(): вернет указанный метод, если он также и унаследованно, но не приватный
 * - getMethods(): вернет указанные методы, если они также и унаследованны, но не приватные*/


/* МЕТОДЫ ПОЛУЧЕНИЯ КОНСТРУКТОРОВ
 * - getDeclaredConstructor(): вернет указанный конструктор, если он объявлен в классе, в т.ч. приватные
 * - getDeclaredConstructors(): вернет указанные конструкторы, если они объявлены в классе, в т.ч. приватные
 * - getConstructor(): вернет указанный конструктор, но не приватный
 * - getConstructors(): вернет указанные конструкторы, но не приватные*/



/* ДРУГИЕ МЕТОДЫ КЛАССА CLASS */


/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ЧЛЕНЫ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/* ИНТЕРФЕЙС MEMBER
 * Member is an interface that reflects identifying information about a single member (a field or a method) or a constructor.
 *
 * All Known Implementing Classes: Constructor, Executable, Field, Method
 *
 * Class<?>	getDeclaringClass()
Returns the Class object representing the class or interface that declares the member or constructor represented by this Member.
int	getModifiers()
Returns the Java language modifiers for the member or constructor represented by this Member, as an integer.
String	getName()
Returns the simple name of the underlying member or constructor represented by this Member.
boolean	isSynthetic()
Returns true if this member was introduced by the compiler; returns false otherwise.
 *
 * */


/* ~~~~~~~~~~~~~~~~~~~~~ПОЛЯ~~~~~~~~~~~~~~~~~~~~~
 * - полем является [класс|интерфейс|перечисление] с ассоциированным значением
 *      - в т.ч. статические
 *
 * - java.lang.reflect.Field: предоставляет доступ к информации о типе и возможность чтения/записи новых значений
 *      - имя
 *      - тип
 *      - модификаторы
 *      - аннотации
 *
 * - класс.getDeclaredFields(): получение всех полей (в т.ч. приватных), объявленных в данном классе
 * - класс.getFields(): получение всех публичных полей (в т.ч. унаследованных), кроме приватных
 *      - публичное поле доступно, если это член (1 из):
 *          - данного класса
 *          - родителя данного класса
 *          - интерфейса, имплементируемого данным классом
 *          - интерфейса, расширенного интерфейсом, имплементируемым данным классом
 *
 * */

/* ПОЛУЧЕНИЕ ИНФОРМАЦИИ О ТИПЕ ПОЛЯ */

/* ПОЛУЧЕНИЕ И ПАРСИНГ МОДИФИКАТОРОВ ПОЛЯ */

/* ПОЛУЧЕНИЕ И ЗАПИСЬ ЗНАЧЕНИЯ ПОЛЯ */





/* ~~~~~~~~~~~~~~~~~~~~~МЕТОДЫ~~~~~~~~~~~~~~~~~~~~~
 * java.lang.reflect.Method
 * - предоставляет доступ к информации о типах параметров и возвращаемого значения
 * - предоставляют возможность запускать нужный метод для указанного объекта
 * */

/* ПОЛУЧЕНИЕ ИНФОРМАЦИИ О ТИПЕ ДЛЯ МЕТОДА */
/* ПОЛУЧЕНИЕ ИМЕН ПАРАМЕТРОВ МЕТОДА */
/* ПОЛУЧЕНИЕ И ПАРСИНГ МОДИФИКАТОРОВ МЕТОДА */
/* ЗАПУСК МЕТОДА */





/* ~~~~~~~~~~~~~~~~~~~~~КОНСТРУКТОРЫ~~~~~~~~~~~~~~~~~~~~~
 * - java.lang.reflect.Constructor
 * - аналогично методам, кроме:
 *      - не имеет возвращаемого значения
 *      - вызов конструктора приводит к созданию экземпляра
 * */

/* ПОИСК КОНСТРУКТОРА */

/* ПОЛУЧЕНИЕ И ПАРСИНГ МОДИФИКАТОРОВ КОНСТРУКТОРА */

/* СОЗДАНИЕ НОВЫХ ЭКЗЕМПЛЯРОВ */





/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~МАССИВЫ И ПЕРЕЧИСЛЕНИЯ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/



/*Class.newInstance() will throw an InstantiationException if an attempt is made to create a new instance of the class and the zero-argument constructor is not visible.*/

@Ntrstn("По документации, членом является все, что наследуется, поэтому конструктор не является " +
        "членом класса. Однако в рефлексии он все равно считается за член")

public class Main {
    static Modifier mod;
    static Class c;
    static Class[] ca;
    static Field f;
    static Field[] fa;
    static Method m;
    static Method[] ma;
    static Constructor k;
    static Constructor[] ka;

    static Integer i = 33;
    static double[][] da = new double[1][];
    static String[][] sa = new String[1][];
    private Field field;

    public static void main(String[] args) {
        /* ~~~~~~~~~~~~~~~~~~~~~~~ПОЛУЧЕНИЕ ЭКЗЕМПЛЯРА CLASS ДЛЯ ТИПА/ОБЪЕКТА~~~~~~~~~~~~~~~~~~~~~~~ */
        /* ~~~~~~~~~ПОЛУЧЕНИЕ ЭКЗЕМПЛЯРА CLASS~~~~~~~~~*/
        /* ДЛЯ ССЫЛОЧНЫХ ТИПОВ ПО ОБЪЕКТАМ */
        c = i.getClass(); // java.lang.Integer
        c = da.getClass(); // [[D
        c = sa.getClass(); // [[Ljava.lang.String

        /* ДЛЯ ПРИМИТИВОВ ИЛИ КОГДА НЕТ ОБЪЕКТОВ */
        c = Integer.class; // java.lang.Integer
        c = boolean.class; // boolean
        c = void.class; // void
        c = double[][].class; // [[D

        /* ПО ПОЛЮ TYPE У ОБОЛОЧЕК */
        c = Integer.TYPE; // int
        c = Void.TYPE; // void

        /* ПО ПОЛНОМУ ИМЕНИ КЛАССА */
        try {
            c = Class.forName("java.lang.Integer");
            c = Class.forName("[[D");
            c = Class.forName("[[Ljava.lang.String;");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        /* ~~~~~~~~~ПОЛУЧЕНИЕ ЭКЗЕМПЛЯРА CLASS ОТ ДРУГОГО CLASS ~~~~~~~~~*/
        /* ПОЛУЧЕНИЕ РОДИТЕЛЯ */
        c = Child.class.getSuperclass(); // __reflection.Main

        /* ПОЛУЧЕНИЕ ОБРАМЛЯЮЩЕГО КЛАССА */
        c = MainInner.class.getEnclosingClass(); // __reflection.Main

        /* ПОЛУЧЕНИЕ ВСЕХ КЛАССОВ, ИНТЕРФЕЙСОВ И ПЕРЕЧИСЛЕНИЙ, КОТОРЫЕ ЯВЛЯЮТСЯ ЧЛЕНАМИ (В Т.Ч. УНАСЛЕДОВАННЫЕ) */
        ca = __reflection.Main.class.getClasses(); // [class __reflection.Main$MainInner, class __reflection.Main$Child]

        /* ПОЛУЧЕНИЕ ВСЕХ ЧЛЕНОВ, КОТОРЫЕ ЯВНО ОБЪЯВЛЕНЫ В КЛАССЕ */
        ca = __reflection.Main.Child.class.getDeclaredClasses(); // [class __reflection.Main$Child$ChildOwnInner]

        /* ПОЛУЧЕНИЕ КЛАССА/ЧЛЕНОВ, В КОТОРОМ ОН ОБЪЯВЛЕН */
        c = __reflection.Main.Child.class.getDeclaringClass(); // __reflection.Main



        /* ~~~~~~~~~~~~~~~~~~~~~~~ПОЛУЧЕНИЕ МОДИФИКАТОРА КЛАССА И ТИПА ~~~~~~~~~~~~~~~~~~~~~~~ */
        int modifiers = __reflection.Main.Child.class.getModifiers(); // 25
        Modifier.toString(modifiers); // public static final
        TypeVariable[] params = __reflection.Main.Child.class.getTypeParameters(); // T, V


        /* ~~~~~~~~~~~~~~~ПОЛУЧЕНИЕ ЧЛЕНОВ (КОНСТРУКТОРОВ, МЕТОДОВ, ПОЛЕЙ) КЛАССА ~~~~~~~~~~~~~~~*/
        c = Main.class;

        fa = c.getDeclaredFields(); // static java.lang.reflect.Modifier __reflection.Main.mod, etc
        try {
            f = c.getDeclaredField("mod"); // static java.lang.reflect.Modifier __reflection.Main.mod
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        ma = c.getMethods(); // public static void __reflection.Main.main(java.lang.String[]), etc.
        ka = c.getConstructors(); // public __reflection.Main()


        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~РАБОТА С ЧЛЕНАМИ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


        System.out.println(ka.length);
        System.out.println(Arrays.toString(ka));

    }


    public static final class Child<T, V> extends Main {
        public class ChildOwnInner {
        }
    }

    public class MainInner {
    }

    void meth() {
    }
}