package __types_system;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import types_references_annotations.my_annotations.Ntrstn;
import types_references_annotations.my_annotations.Ntrstns;

/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ПАКЕТ JAVA.LANG.REFLECTION~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * - предоставляет программный доступ к информации о полях, методах и конструкторах загруженных
 * классов
 *      - позволяет работать (читать/записывать/вызывать) с данными членами
 *
 * - интерфейсы:
 *      - AnnotatedArrayType: потенциально аннотированное использование массивного типа, тип
 *      элементов которого и сам может представлять аннотированное использование типа
 *      - AnnotatedElement: аннотированный элемент программы, которая сейчас выполняется VM
 *      - AnnotatedParameterizedType: потенциально аннотированное использование параметризированного
 *      типа, типы аргумента которого могут сами представлять аннотированное использование типов.
 *      - AnnotatedType: потенциально аннотированное использование типа в программе
 *      - AnnotatedTypeVariable: потенциально аннотированное использование переменной типа, чье
 *      объявление может иметь границы, которые также могут представлять аннотированное
 *      использование типов
 *      - AnnotatedWildcardType: потенциально аннотированное использование агрумента типа wildcard,
 *      чьи верхняя или нижняя границы могут и сами представлять потенциально аннотированные
 *      использования типов
 *      - GenericArrayType: представляет тип массива, тип элементов которого либо параметризированный
 *      тип либо переменная типа
 *      - GenericDeclaration: общий интерфейс для всех сущностей, объявляющих переменные типа
 *      - InvocationHandler: интерфейс, реализуемые помощником по инициализации прокси экземпляра
 *      - Member: интерфейс, представляющий идентифицирующую информацию об одном члене (поле, методе
 *      или конструкторе)
 *      - ParameterizedType: представляет параметризированный тип, например Collection<String>
 *      - Type: общий интерфейс-родитель для всех типов в Java
 *      - TypeVariable<D extends GenericDeclaration>: общий интерфейс-родитель для переменных типа
 *      - WildcardType: представляет выражение типа с wildcard, например ?, ? extends Number, или
 *      ? super Integer
 *
 * - классы:
 *      - AccessibleObject: родитель для объектов Field, Method и Constructor
 *      - Array: предоставляет статические методы для динамического создания и доступа к массивам
 *      - Constructor<T>: предоставляет информацию и доступ к одному конструктору класса
 *      - Executable: общий родитель для общей функциональности классов Method и Constructor
 *      - Field: предоставляет информацию и динамический доступ к одному полю класса/интерфейса
 *      - Method: предоставляет информацию и доступ к одному методу класса/интерфейса
 *      - Modifier: предоставляет статические методы и константы для раскодирования модификаторов
 *      доступа класса и членов
 *      - Parameter: информация о параметрах метода
 *      - Proxy: предоставляет статические методы для создания динамических прокси-классов и
 *      объектов, а также является родителем все динамических прокси классов, созданных этими методами
 *      - ReflectPermission: представляет разрешения для операций рефлексии
 *
 * - исключения:
 *      - InvocationTargetException: проверяемое исключения, которое оборачивает исключение,
 *      выбрасываемое вызванным методом или конструктором
 *      - MalformedParameterizedTypeException: выбрасывается когда семантически некорректный
 *      параметризированный тип встречается рефлексивным методом, которому нужно инициировать его
 *      - MalformedParametersException: выбрасывается, когда пакет java.lang.reflect пытается
 *      прочитать параметры метода из файла класса и определяет, что один из них или более некорректны
 *      - UndeclaredThrowableException: выбрасывается при вызове метода на экземпляре прокси, если
 *      его метод-помощник по инициализации выбрасывает проверяемое исключение, которое не
 *      относится ни к одному из объявленных типов исключений в блоке throws метода, который был
 *      вызван на экземпляре прокси и передан помощнику по инициализации (ага)
 *
 * - ошибки:
 *      - GenericSignatureFormatError: выбрасывается, когда синтатически некорректный атрибут
 *      подписи встречается рефлексивным методом, которому нужно интерпретировать общую (generic)
 *      информацию подписи для типа, метода или конструктора */


/* РЕФЛЕКСИЯ - МЕХАНИЗМ ИССЛЕДОВАНИЯ ДАННЫХ О ПРОГРАММЕ В RUNTIME; ПОЗВОЛЯЕТ:
 * - получить информацию о классе и его структуре в runtime
 *      - при помощи объекта Class соответствующего класса
 *          - находится в пакете java.lang
 *          - напр. узнать список методов и какие из них являются переопределениями для
 *          имплементируемого интерфейса
 *
 * - изменить поведение объектов
 *      - при помощи вспомогательных классов из пакета java.lang.reflect работать со всеми членами
 *
 * - полноценно работать с классами, которые были не доступны в runtime
 *      - при помощи вспомогательных классов из пакета java.lang.reflect работать со всеми членами
 *          - а не как с объектом типа Object */


/* РЕФЛЕКСИЯ ОБХОДИТ ОГРАНИЧЕНИЯ RTTI
 * - позволяет также получать информацию о типе класса, которого не существовало во время компиляции
 *
 * - с классами, которых не существовало при компиляции, можно работать полноценно:
 *      - изменять значение полей
 *      - вызывать методы
 *      - создавать объекты
 *
 * - доступна ВСЯ структура класса ПОСЛЕ компиляции, в т.ч.:
 *      - приватные члены
 *      - унаследованные члены
 *      - синтетические конструкции, которых не было на момент компиляции */


/* ЧТО МОЖНО УЗНАТЬ С ПОМОЩЬЮ РЕФЛЕКСИИ
 * - пакет класса
 * - класс-родитель
 * - всю информацию о типе класса, членов, их параметров, элементов массива
 * - все переменные типа (но не их реальные значения - они стерты!)
 * - все интерфейсы, которые имплементирует класс
 * - все аннотации (у которых стоит RetentionPolicy RUNTIME)
 * - все модификаторы
 * - все члены - приватные, унаследованные публичные, обобщенные и поля-константы
 * - все названия параметров метода/конструктора
 * - все выбрасываемые исключения у метода/конструктора
 * - все дополнительные элементы, которых не было при компиляции (например, синтетические методы)
 * - кому принадлежит член (напр. данный внутренний класс)
 * - является ли член массивом, перечислением, аннотацией, полем-константой, интерфейсом, локальным
 * или анонимным классом, методом имплементируемого интерфейса
 * - является ли член синтезированным, неявным, мостовым
 * - является ли член доступным для модификации из другого указанного класса
 * - является ли член экземпляром указанного класса (аналог instanceof) */


/* ЧТО НЕЛЬЗЯ УЗНАТЬ С ПОМОЩЬЮ РЕФЛЕКСИИ
 * - класс, в котором объявлен данный анонимный класс
 * - значение, установленное в параметре типа */


/* ПОЛЬЗА РЕФЛЕКСИИ И МЕСТА ПРИМЕНЕНИЯ
 * - полноценная работа с классами, которых не существовало в момент компиляции
 *
 * - визуальные средства программирования
 *      - показывать структуру класса
 *      - скрывать синтетические и другие нерелевантные члены
 *
 * - средства тестирования
 *      - увидеть полную структуру класса
 *      - получить доступ к приватным членам
 *
 * - а также дебаггеры, интерпретаторы, инспекторы объектов, браузеры классов, сериализация объектов
 * и JavaBeans
 *
 * - в обычной работе применяется не часто */


/* МИНУСЫ РЕФЛЕКСИИ
 * - ухудшение производительности
 *      - из-за типов, которые разрешаются динамически
 *          - JVM не может произвести некоторые, связанные с ними, оптимизации
 *
 * - ограничение безопасности
 *      - todo требует runtime права, которого может не быть при работе в security manager (Applet)
 *
 * - доступность скрытого (нарушение инкапсуляции!)
 *      - нарушается сокрытие приватных членов
 *         - может привести к проблемам с кодом */


/*~~~~~~~~~~~~~~~~~~~~~~КЛАСС JAVA.LANG.CLASS - ВХОДНАЯ ТОЧКА В REFLECTION API~~~~~~~~~~~~~~~~~~~~~~
 * - Class Class<T> extends Object implements Serializable, AnnotatedElement, GenericDeclaration, Type
 *
 * - не является частью пакета java.lang.reflect
 *
 * - каждый тип объекта в программе имеет 1 соответствующий этому типу экземпляр класса Class
 *      - этот экземпляр создается JVM единожды при первом обращении к статическому члену данного
 *      класса
 *          - в т.ч. конструктору
 *      - экземпляр является неизменяемым
 *
 * - экземпляр класса Class используется как входная точка в Reflection API
 *      - он предоставляет множество методов для получения членов класса и информации о типе
 *          - для членов существуют отдельные классы java.lang.reflex, но они не имеют конструкторов,
 *          поэтому для получения их экземпляров используется соответствующий экземпляр Class
 *
 * - параметризированная версия существует для удобства:
 *      - можно ограничить тип, которому может принадлежать ссылка типа Class
 *          - Class<?> можно использовать, чтобы указать, что тип неизвестен
 *      - можно также использовать и непараметризированную версию */


/* ~~~~~~~~~~~~~~ПОЛУЧЕНИЕ НУЖНЫХ ЭКЗЕМПЛЯРОВ CLASS~~~~~~~~~~~~~~
 * - получения объекта Class:
 *      - Тип.class: для примитивов и void или когда есть тип, но нет объекта
 *          - наиболее эффективный (не нужно вызывать метод)
 *              - т.е. это уже готовая ссылка на нужный объект Class
 *              - проверка осуществляется при компиляции
 *          - не приводит к созданию экземпляра Class, если он еще не создан
 *
 *      - объект.getClass(): когда есть нужный объект
 *          - только для ссылочных типов
 *
 *      - Class.forName(): когда есть полное имя класса
 *          - только для ссылочных типов
 *          - может выбросить исключение ClassNotFoundException
 *          - приводит к созданию экземпляра Class, если он еще не создан
 *
 *      - Оболочка.TYPE: альтернатива Тип.class для получения примитивов и void от их оболочек
 *          - у каждого класса-оболочки есть поле TYPE с ссылкой на объект Class соответствующего
 *          примитива
 *
 *
 * - получение объекта Class от другого Class:
 *      - класс.getSuperclass(): получить родителя данного класса
 *
 *      - класс.getEnclosingClass(): получить обрамляющий класс
 *
 *      - класс.getClasses(): массив из всех публичных(!) классов, интерфейсов и перечислений,
 *      которые являются членами данного класса (в т.ч. унаследованные)
 *
 *      - класс.getDeclaredClasses(): массив из всех классов, интерфейсов и перечислений, которые
 *      явно объявлены в данном классе
 *
 *      - класс.getDeclaringClass(): возвращает класс, в котором объявлен данный член
 *          - не работает для анонимных классов
 *              - но можно получить обрамляющий класс
 *          - соответствующие аналоги для полей, методов и конструкторов:
 *              - java.lang.reflect.Field.getDeclaringClass()
 *              - java.lang.reflect.Method.getDeclaringClass()
 *              - java.lang.reflect.Constructor.getDeclaringClass() */


/* ~~~~~~~~~~~~~~ СПЕЦИФИЧЕСКИЙ СИНТАКСИС ДЛЯ МАССИВОВ ~~~~~~~~~~~~~~
 * - при получении объекта Class по имени нужного класса, для массивов используется особый синтаксис:
 *      - относится и к ссылочным массивам и к примитивным
 *          - [ значит одно измерение массива
 *          - boolean: Z
 *          - byte:	B
 *          - char: C
 *          - класс/интерфейс Lимякласса;
 *          - double: D
 *          - float: F
 *          - int: I
 *          - long: J
 *          - short: S
 *
 * - напр. [[D - значит 2-мерный массив из элементов типа double */


/* ~~~~~~~~~~~~~~ ПОЛУЧЕНИЕ ЧЛЕНОВ КЛАССА / СХОДСТВА В МЕТОДАХ GET...~~~~~~~~~~~~~~
 * - получить член можно соответствующим методом либо по имени либо в массиве
 *      - возваращаемый тип - соответвующая реализация интерфейса java.lang.reflect.Member
 *          - классы Field, Method, Constructor
 *
 * - методы получения членов:
 *      - get...() VS getDeclared...():
 *          - get...(): вернет указанный публичный члены или массив публичных членов, в т.ч.
 *          унаследованный
 *          - getDeclared...(): вернет указанный член или массив членов, явно объявленный в классе,
 *          в т.ч. приватный
 *
 *      - get...()/getDeclared...() VS get...s()/getDeclared...s():
 *          - get...()/getDeclared...(): вернет указанный член
 *          - get...s()/getDeclared...s(): вернет указанные члены в виде массива типа члена (напр.
 *          Fields[])
 *
 *      - get...() VS getGeneric...():
 *          - get...(): возвращает объекты типа Class
 *          - getGeneric...(): возвращает объекты типа Type, предствляющие типы параметров */


/* ~~~~~~~~~~~~~~ ПОЛУЧЕНИЕ МОДИФИКАТОРОВ / КЛАСС MODIFIER ~~~~~~~~~~~~~~
 * - получить модификаторы: класс\член.getModifiers()
 *      - возвращается число - закодированные модификаторы
 *      - раскодировать в строковое значение можно при помощи статического метода Modifier.toString()
 *
 * - класс Modifier:
 *      - числовые константы, представляющие соответствующие модификаторы
 *      - статические методы для работы с модификаторами:
 *          - узнать модификаторы для указанного поля, конструктора, метода и параметра
 *          - узнать является ли данный модификатор указанным
 *          - перевести числовой код модификатора в строковое представление */


/* ~~~~~~~~~~~~ ПОЛУЧЕНИЕ ПЕРЕМЕННОЙ ТИПА / ИНТЕРФЕЙС TYPEVARIABLE ~~~~~~~~~~~~
 * - класс/член.getTypeParameters(): возвращает массив из объектов TypeVariable
 *
 * - методы TypeVariable:
 *      - getBounds(): вернет массив из Type, представляющий верхную границу(ы) переменной
 *      - getGenericDeclaration(): вернтет класс, в котором объявлено данное обобщение
 *      - getName(): вернет имя переменной, какое указано в коде */


/* ~~~~~~~~~~~~ПОЛУЧЕНИЕ АННОТАЦИЙ / ИНТРЕФЕЙС ANNOTATEDELEMENT~~~~~~~~~~~~
 * - представляет аннотированный элемент программы и позволяет читать аннотации
 *      - все возвращаемые аннотации являются неизменяемыми и сериализируемыми
 *          - если аннотации возвращаются в массиве, то сам массив можно менять, но это не приведет
 *          к изменению возвращаемых массивов другими методами
 *
 *
 * - получить можно только аннотации, у которых стоит RetentionPolicy.RUNTIME
 *
 *
 * - имплементируют классы Class, Field, Method, Constructor
 *
 *
 * - понятия:
 *      - "присутствует явно":
 *          - аннотация A присутствует явно на элементе E, если у E есть атрибут
 *          RuntimeVisibleAnnotations или RuntimeVisibleParameterAnnotations или
 *          RuntimeVisibleTypeAnnotations, и в атрибует содержится A
 *
 *      - "присутствует неяно":
 *          - аннотация A присутствует неявно на элементе E, если у E есть атрибут
 *          RuntimeVisibleAnnotations или RuntimeVisibleParameterAnnotations или
 *          RuntimeVisibleTypeAnnotations, и тип A - повторяемый, и атрибут содержит ровно одну
 *          аннотацию, чей элемент значения содержит A и чей тип - содержит тип аннотации типа А
 *
 *      - "присутствует":
 *          - аннотация A присутствует для элемента E если:
 *              - либо A присутсвует явно для Е
 *              - либо для Е аннотация типа A не присутствует явно, Е является классом, а тип A
 *              можно унаследовать, и А присутствует у родителя класса E.
 *
 *      - "ассоциирован":
 *          - аннотация A ассоциирована с элементом E если:
 *              - либо А присутствует явно или неявно на элементе E
 *              - либо А не присутствует явно или неявно на элементе E, Е является классом, а тип А
 *              можно унаследовать, и A ассоциирована с родителем класса E.
 *
 *
 * - методы:
 *      - getAnnotation(Class<T> annotationClass): вернет аннотацию элемента для указанного типа,
 *      если она присутствует, иначе null.
 *
 *      - getAnnotations(): вернет аннотации, присутствуещие для элемента
 *
 *      - getAnnotationsByType(Class<T> annotationClass): вернет аннотации, которые ассоциированы с
 *      данным элементом
 *
 *      - getDeclaredAnnotation(Class<T> annotationClass): вернет аннатацию элемента для указанного
 *      типа, если она присутствует явно, иначе null
 *
 *      - getDeclaredAnnotations(): вернет аннотации, которые присутствуют явно
 *
 *      - getDeclaredAnnotationsByType(Class<T> annotationClass): вернет аннотации элемента для
 *      указанного типа, если такие аннотации либо присутствуют явно либо неявно
 *
 *      - isAnnotationPresent(Class<? extends Annotation> annotationClass): вернет true, если
 *      аннотация для указанного типа присутствует для данного элемента, иначе false*/


/* ДРУГИЕ МЕТОДЫ КЛАССА CLASS
 * - <U> Class<? extends U> asSubclass(Class<U> clazz):
  *     - позволяет преобразовать объект класса к более конкретному
 *
 * - T cast(Object obj): Casts an object to the class or interface represented by this Class object.
 *      - полезен, когда нельзя исплользовать обычное приведение типа. Напр. при написании
 *      обобщеннго кода и сохранении ссылки на Class, которая будет использоваться для приведения
 *      типа позднее. но такие ситуации возникают редко
 *
 * - isInstance() помогает избавится от громоздких конструкций instanceof
 *      instanceof и isInstance дают одинаковые результаты
 *      == не затрагивает наследование
 *
 * - isAssignableFrom(Class<?> cls)
 *      Integer.class.isAssignableFrom(int.class) == false
 *      Strictly speaking, any attempt to set a field of type X to a value of type Y can only succeed if the following statement holds:
        X.class.isAssignableFrom(Y.class) == true
 *
 * - getCanonicalName()
 *
 * */


/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ЧЛЕНЫ ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/* ~~~~~~~~~~~~~~~~~~~~~~~~ ИНТЕРФЕЙС MEMBER ~~~~~~~~~~~~~~~~~~~~~~~~
 * - для идентификации членов класса: полей, методов и конструкторов
 * - имплементируется классами Field, Method и Constructor
 * - методы:
 *      - getDeclaringClass(): вернет класс, которому принадлежит член
 *      - getModifiers(): вернет закодированное число, представляющее модификаторы члена
 *      - getName(): вернет имя члена из кода
 *      - isSynthetic(): проверка, является ли член добавленным при компиляции */


/* ~~~~~~~~~~~~~~~~~~~~~~~~ КЛАСС ACCESSIBLEOBJECT ~~~~~~~~~~~~~~~~~~~~~~~~
 * - public class AccessibleObject extends Object implements AnnotatedElement
 *
 * - является прямым родителем для Field и прародителем (через класс Executable) для Method и
 * Constructor
 *
 * - предоставляет возможность помечать член для игнорирования стандартных проверок доступа, которые
 * выполняет Java
 *      - при чтении и записи полей, вызовах методов и создании экземпляров классов
 *
 * - по дефолту для всех членов стоит флаг "не доступен"
 *      - но все равно можно обращаться к реально доступным членам
 *          - например, можно установить значение публичному члену, даже если у него стоит флаг "не
 *          доступен"
 *              - но работать с фактически не доступными членами (например, private), без установки
 *              флага "доступен" не получится
 *
 * - основные методы:
 *      - isAccessible(): возвращает значение флага accessible для объекта
 *      - setAccessible(boolean flag): включение/выключение проверки прав доступа
 *      - setAccessible(AccessibleObject[] array, boolean flag): удобный и производительный метод
 *      для установки проверки доступа для массива объектов */


/* ~~~~~~~~~~~~~~~~~~~~~~~~ ПОЛЯ / КЛАСС FIELD~~~~~~~~~~~~~~~~~~~~~
 * - предоставляет доступ к полю класса или интерфейса
 *
 * - поля могут быть:
 *      - принадлежащими экземпляру
 *      - статическими
 *      - синтезированы компилятором
 *          - проверить: isSynthetic()
 *          - обычно являются не публичными
 *      - константами перечисления (если исследуется класс-перечисление)
 *          - проверить: isEnumConstant()
 *
 * - получение и запись значения:
 *      - должно использоваться как можно реже, т.к. нарушает инкапсуляцию (т.е. сам смысл класса)
 *      - замедляет работу, т.к. требуется проверка доступа
 *      - операция замены является атомарной, как и простой замене в коде
 *      - такой код не будет оптимизирован JVM, если оптимизация должна была бы произойти без рефлексии
 *      - get...(Object, ...)
 *      - set...(Object, ...)
 *          - если значение final, то будет вызвано IllegalAccessException
 *      - если поле статическое, то объект просто игнорируется
 *      - автоупаковка и автораспаковка не происходит, т.к. это runtime
 *          - т.е. в поле типа Integer нельзя установить значение простым int, и наоборот */


/* ~~~~~~~~~~~~~~~~~~~~~~~~ КЛАСС EXECUTABLE ~~~~~~~~~~~~~~~~~~~~~~~~
 * - public class Executable extends Object implements AnnotatedElement, GenericDeclaration, Member
 *
 * - является прямым родителем для для Method и Constructor
 *
 * - основные методы:
 *      - getExceptionTypes(): вернет массив объектов типа Class, который представляет типы
 *      исключений, которые объявлены для данного Executable
 *      - isVarArgs(): узнать, есть ли у данного Executable переменное число параметров
 *      - getParameters(): вернет массив объектов типа Parameter, который представляет параметры
 *      данного Executable
 *      - getParameterCount(): вернет число параметров у данного Executable
 *      - getParameterTypes(): вернет массив объектов типа Class, который представляет параметры
 *      типа (в последовательности объявления) у данного Executable
 *      - getTypeParameters(): вернет массив объектов типа TypeVariable, который представляет
 *      переменные типа (в последовательности объявления) у данного Executable */


/* ~~~~~~~~~~~~~~~~~~~~~~~~ КЛАСС PARAMETER ~~~~~~~~~~~~~~~~~~~~~~~~
 * - public final class Parameter extends Object implements AnnotatedElement
 *
 * - представляет информацию о параметрах метода/конструктора
 *      - в т.ч. имя и модификаторы
 *
 * - предоставляет альтернативные средства получения атрибутов параметра
 *
 * - параметры могут также быть:
 *      - неявными: проверка - isImplicit()
 *      - синтетическими: проверка - isSynthetic()
 *      - с переменным числом аргументов: проверка - isVarArgs()
 *      - не иметь имени (т.к. он является синтезированным или синтетическим): проверка - isNamePresent()
 *
 * - другие основные методы:
 *      - equals(Object obj): сравнивает на основании Еxecutable и индекса
 *      - hashCode(): вернет hash code на основании hash code Еxecutable и индекса */


/* ~~~~~~~~~~~~~~~~~~~~~ПОЛУЧЕНИЕ/ВЫПОЛНЕНИЕ МЕТОДОВ / КЛАСС METHOD ~~~~~~~~~~~~~~~~~~~~~
 * java.lang.reflect.Method
 * - предоставляет доступ к информации о типах параметров и возвращаемого значения
 * - предоставляют возможность запускать нужный метод для указанного объекта
 *
 * - можно получить:
 *      - название
 *      - модификаторы
 *      - параметры
 *      - возвращаемый тип
 *      - список выбрасываемых исключений
 *
 * - A Method permits widening conversions to occur when matching the actual parameters to invoke with the underlying method's formal parameters, but it throws an IllegalArgumentException if a narrowing conversion would occur
 *
 * - узнать, есть ли у метода переменное число аргументов:
 *      - Method.isVarArgs()
 *
 * - получение информации о типе:
 *      - возвращаемый тип:
 *          - getReturnType()
 *          - getGenericReturnType()
 *
 *      - типы параметров:
 *          - getParameterTypes()
 *          - getGenericParameterTypes()
 *
 *      - типы исключений:
 *          - getExceptionTypes()
 *          - getGenericExceptionTypes()
 *              - exists because it is actually possible to declare a method with a generic exception type. However this is rarely used since it is not possible to catch a generic exception type.
 *
 *  - получение имен параметров:
 *      - только, если код был скомпилирован с опцией -parameters
 *          - иначе имена типа arg0
 *              - parameter.isNamePresent() - узнать есть ли нормальное имя
 *          - по дефолту имена не хранятся, чтобы сократить размер
 *              - и чтобы нельзя было определить по имени важный параметр (типа secret или password)
 *      - getParameters():
 *          - унаследован от java.lang.reflect.Executable.getParameters
 *              - этот класс наследуется классом Method и Constructor
 *          - возвращает объекты класса Parameter
 *              - имеют методы типа getName(), getType(), getModifiers и т.д.
 *      - параметры могут быть синтезированными и неявными
 *          - parameter.isSynthetic()
 *          - parameter.isImplicit()
 *              - например, у неявного конструктора (который добавляется в любой класс) внутреннего
 *              класса в параметре также есть ссылка на обрамляющий класс
 *                  - также добавляется неявное поле с этой ссылкой в сам класс
 *
 *
 * - получение и парсинг модификаторов метода:
 *      - метод.getModifiers()
 *
 *
 * - запуск метода:
 *      - this would only be necessary if it is not possible to cast an instance of the class to the desired type in non-reflective code
 *      - метод.invoke(Object o, params...)
 *          - если статический, то вместо объекта null
 *          - если викидывает исключение будет обвернуто в InvocationTargetException
 *              - получить оригинал: InvocationTargetException.getCause()
 *      - если переменное число аргументов, передается массив
 *
 * */


/*An IllegalAccessException is thrown if an attempt is made to invoke a private or otherwise
inaccessible method.
m.setAccessible(true) = solution;
An access restriction exists which prevents reflective invocation of methods which normally would not be accessible via direct invocation. (This includes---but is not limited to---private methods in a separate class and public methods in a separate private class.) However, Method is declared to extend AccessibleObject which provides the ability to suppress this check via AccessibleObject.setAccessible(). If it succeeds, then subsequent invocations of this method object will not fail due to this problem.

*/



/* ~~~~~~~~~~~~~~~~~~~~~КОНСТРУКТОРЫ~~~~~~~~~~~~~~~~~~~~~
 * - java.lang.reflect.Constructor
 * - аналогично методам, кроме:
 *      - не имеет возвращаемого значения
 *      - вызов конструктора приводит к созданию экземпляра
 *
 * - поиск конструктора:
 *      класс.getConstructors()
 *
 * There is not an explicit Modifier constant which corresponds to "package-private" access, so it
 * is necessary to check for the absence of all three access modifiers to identify a package-private constructor.
 *
 * Constructors implement java.lang.reflect.AnnotatedElement, which provides methods to retrieve runtime annotations with java.lang.annotation.RetentionPolicy.RUNTIME.
 *
 *
 * An important difference between new and Constructor.newInstance() is that new performs method
 * argument type checking, boxing, and method resolution. None of these occur in reflection, where
 * explicit choices must be made.
 *
 * - создавать экземпляр можно 2 методами:
 *      - java.lang.reflect.Constructor.newInstance()
 *          - предпочтительный
 *              It is preferable to use Constructor.newInstance() over Class.newInstance() because the former API permits examination and handling of arbitrary exceptions thrown by constructors.
 *          - вызывает конструктор с любым числом аргументов
 *          - оборачивает исключения в InvocationTargetException
 *          - в некоторых случаях также может вызывать приватные конструкторы
 *
 *      - Class.newInstance()
 *          - вызывает только конструктор с 0 аргументов
 *          - выбрасывает любое исключение конструктора
 *              - проверяемое и не проверяемое
 *          - конструктор должен быть доступен
 *
 *
 * An access restriction exists which prevents reflective invocation of constructors which normally would not be accessible via direct invocation. (This includes, but is not limited to, private constructors in a separate class and public constructors in a separate private class.) However, Constructor is declared to extend AccessibleObject which provides the ability to suppress this check via AccessibleObject.setAccessible().
 * */




/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~МАССИВЫ И ПЕРЕЧИСЛЕНИЯ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * - с точки зрения рефлексии обычные классы
 * - все, что описано выше применяется и к ним
 *      - но для них также есть дополнительные API
 * */


/*~~~~~~~~~~~~~~~~~~~~~МАССИВЫ~~~~~~~~~~~~~~~~~~~~~
 * - сами массивы имплементированы в JVM, а единственные методы, которые им доступны - Object
 *
 * - Class.isArray()
 *
 * - java.lang.reflect.Array
 *      - статические методы позволяют:
 *          - узнать тип массива
 *          - узнать тип компонентов
 *          - создавать новый массив
 *          - получать и устанавливать значения компонентов
 *
 *
 * - получение и установка массивов:
 *      - Field.get(Object)
 *      - Field.set(Object obj, Object value)
 *
 * - узнать тип массива (аналогично имени класса): класс.getName()
 *
 * - узнать тип компонентов: класс.getComponentType()
 *
 * - создание нового массива:
 *      - java.lang.reflect.Array.newInstance(Class type, int length)
 *      - Array.newInstance(Class<?> componentType, int... dimensions)
 *          - для многомерного
 *
 * - получение и установка элементов массива:
 *      - примитивы:
 *          - Array.set...(Object array, int index, ... value)
 *          - Array.get...(Object array, int index)
 *          - методы поддерживают автоматическое расширение
 *              - т.е. Array.getShort() можно использовать, чтобы установить значение в int-овый
 *              массив
 *              - а вызов Array.setLong() для массива из int приведет к IllegalArgumentException
 *      - ссылочные
 *          - Array.set(Object array, int index, Object value)
 *          - Array.get(Object array, int index)
 *
 * - узнать длину массива (относится):
 *      - java.lang.reflect.Array.getLength(Object o)
 *
 *
 *
 * */

/* AccessibleObject */


/* ~~~~~~~~~~~~~~~~~~~~~ПЕРЕЧИСЛЕНИЯ~~~~~~~~~~~~~~~~~~~~~
 * - All enums implicitly extend java.lang.Enum
 * - Class.isEnum()
 * - Class.getEnumConstants() retrieves the enum constants defined in an enum.
 * - java.lang.reflect.Field.isEnumConstant() indicates whether a field is an enumerated type
 *
 * - поле.isEnumConstant(): почему-то не работает
 *
 * - инициализировать экземпляры нельзя
 *      - даст IllegalArgumentException
 *      -  It is a compile-time error to attempt to explicitly instantiate an enum because that would prevent the defined enum constants from being unique. This restriction is also enforced in reflective code. Code which attempts to instantiate classes using their default constructors should invoke Class.isEnum() first to determine if the class is an enum.
 * For various reasons, including support for evolution of the enum type, the declaration order of enum constants is important. Class.getFields() and Class.getDeclaredFields() do not make any guarantee that the order of the returned values matches the order in the declaring source code. If ordering is required by an application, use Class.getEnumConstants().
 *
 *
 * */


/*Class.newInstance() will throw an InstantiationException if an attempt is made to create a new instance of the class and the zero-argument constructor is not visible.*/


@Ntrstn("Нужный экземпляр класса Class является входной точкой в Reflection API")

@Ntrstn("Упаковка (boxing) и распаковка происходят во время компиляции, поэтому не работают для " +
        "рефлексии")

@Ntrstn("удобно для IDE показывать структуру класса и отделять синтетические и неявные методы, параметры")

@Ntrstn("По документации, членом является все, что наследуется, поэтому конструктор не является " +
        "членом класса. Однако в рефлексии он все равно считается за член")

@Ntrstn("Я не могу поменять модификаторы и изменить значение финализированного поля")

@Ntrstn("Методы класса Class работают для всех ссылочных типов, включая массивы и перечисления. " +
        "Однако для последних еще существует отдельное API ")

@Ntrstn("Метод поле.isEnumConstant() предназначен для полей САМОГО класса перечисления, а не полей " +
        "перечислимого типа в обычных классах. Является ли тип поля обычного класса перечислимым " +
        "можно определить методом класс.isEnum(), предварительно узнав его тип методом getType")

@Ntrstn("Инициализировать экземпляры перечисления нельзя, при попытке возникнет исключение " +
        "IllegalArgumentException ")

@Ntrstn("Класс Class находится в пакете java.lang. Все отсальные классы находятся в пакете " +
        "java.lang.reflect")

public class Reflection<T extends Number> {
    static Modifier mod;
    static Class c;
    static Class[] ca;
    static Field f;
    static Field[] fa;
    static Method m;
    static Method[] ma;
    static Constructor k;
    static Constructor[] ka;
    static Parameter p;
    static Parameter[] pa;

    static Integer i = 33;
    static double[][] da = new double[1][];
    static String[][] sa = new String[1][];
    static List<?> l = new ArrayList<>();
    static List<Integer> l2 = new ArrayList<>();
    private Field field;
    static TestEnum testEnum = TestEnum.ONE;

    enum TestEnum {ONE, TWO}


    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException,
            NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException {
        /* ~~~~~~~~~~~~~~~~~~~~~~~ CLASS VS CLASS<T> ~~~~~~~~~~~~~~~~~~~~~~~ */
        Class<String> cStr = String.class; // указывает, что типом может быть только String
//        cStr = Integer.class; // ошибка компилятора

        Class<?> cJ = String.class; // просто указывает, что тип неизвестен
        cJ = Integer.class; // ok

        c = String.class; // вообще ничего не указывает
        c = Integer.class; // ok


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
        c = Class.forName("java.lang.Integer");
        c = Class.forName("[[D");
        c = Class.forName("[[Ljava.lang.String;");


        /* ~~~~~~~~~ПОЛУЧЕНИЕ ЭКЗЕМПЛЯРА CLASS ОТ ДРУГОГО CLASS ~~~~~~~~~*/
        /* ПОЛУЧЕНИЕ РОДИТЕЛЯ */
        c = Child.class.getSuperclass(); // __Implicit_Synthetic_Bridge.__Implicit_Synthetic_Bridge

        /* ПОЛУЧЕНИЕ ОБРАМЛЯЮЩЕГО КЛАССА */
        c = MainInner.class.getEnclosingClass(); // __Implicit_Synthetic_Bridge.__Implicit_Synthetic_Bridge

        /* ПОЛУЧЕНИЕ ВСЕХ КЛАССОВ, ИНТЕРФЕЙСОВ И ПЕРЕЧИСЛЕНИЙ, КОТОРЫЕ ЯВЛЯЮТСЯ ЧЛЕНАМИ (В Т.Ч. УНАСЛЕДОВАННЫЕ) */
        ca = Reflection.class.getClasses(); // [class __Implicit_Synthetic_Bridge.__Implicit_Synthetic_Bridge$MainInner, class __Implicit_Synthetic_Bridge.__Implicit_Synthetic_Bridge$Child]

        /* ПОЛУЧЕНИЕ ВСЕХ ЧЛЕНОВ, КОТОРЫЕ ЯВНО ОБЪЯВЛЕНЫ В КЛАССЕ */
        ca = Reflection.Child.class.getDeclaredClasses(); // [class __Implicit_Synthetic_Bridge.__Implicit_Synthetic_Bridge$Child$ChildOwnInner]

        /* ПОЛУЧЕНИЕ КЛАССА/ЧЛЕНОВ, В КОТОРОМ ОН ОБЪЯВЛЕН */
        c = Reflection.Child.class.getDeclaringClass(); // __Implicit_Synthetic_Bridge.__Implicit_Synthetic_Bridge


        /* ~~~~~~~~~~~~~~~~~~~~~~~ПОЛУЧЕНИЕ МОДИФИКАТОРА КЛАССА И ТИПА ~~~~~~~~~~~~~~~~~~~~~~~ */
        int modifiers = Reflection.Child.class.getModifiers(); // 25
        Modifier.toString(modifiers); // public static final

        TypeVariable[] typeVariable = Reflection.class.getTypeParameters();
        typeVariable[0].getName(); // T
        Type t = typeVariable[0].getBounds()[0]; // class java.lang.Number
        typeVariable[0].getGenericDeclaration(); // class __types_system.Reflection


        /* ~~~~~~~~~~~~~~~~~~~~~~~ПОЛУЧЕНИЕ АННОТАЦИЙ~~~~~~~~~~~~~~~~~~~~~~~ */
        c = Reflection.class;
        Annotation[] a = c.getDeclaredAnnotations();


        /* ~~~~~~~~~~~~~~~~~~~~~~~ОТКЛЮЧЕНИЕ ПРОВЕРКИ ДОСТУПА ДЛЯ ЧЛЕНА ~~~~~~~~~~~~~~~~~~~~~~~*/
        c = Reflection.Child.class;
        f = c.getDeclaredField("f");
        f.isAccessible(); // false (для любого члена по дефолту стоит флаг "не доступен")
        f.set(new Child(), 3); // но фактически доступным все-равно членом можно пользоваться
        f.setAccessible(true);


        /*~~~~~~~~~~~~~~~~~~~~~~~ПОЛЯ~~~~~~~~~~~~~~~~~~~~~~~*/



        /* ~~~~~~~~~~~~~~~ПОЛУЧЕНИЕ ЧЛЕНОВ (КОНСТРУКТОРОВ, МЕТОДОВ, ПОЛЕЙ) КЛАССА ~~~~~~~~~~~~~~~*/
        c = Reflection.class;

        fa = c.getDeclaredFields(); // static java.lang.reflect.Modifier __Implicit_Synthetic_Bridge.__Implicit_Synthetic_Bridge.mod, etc

        f = c.getDeclaredField("mod"); // static java.lang.reflect.Modifier __Implicit_Synthetic_Bridge.__Implicit_Synthetic_Bridge.mod

        ma = c.getMethods(); // public static void __Implicit_Synthetic_Bridge.__Implicit_Synthetic_Bridge.reflection(java.lang.String[]), etc.
        ka = c.getConstructors(); // public __Implicit_Synthetic_Bridge.__Implicit_Synthetic_Bridge()


        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~РАБОТА С ЧЛЕНАМИ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        /*~~~~~~~~~~~~~~~~~~~~~~ПОЛЯ~~~~~~~~~~~~~~~~~~~~~~*/
        f = c.getDeclaredField("mod");

        /* УЗНАТЬ, СИНТЕЗИРОВАНО ЛИ ПОЛЕ ПРИ КОМПИЛЯЦИИ */
        f.isSynthetic(); // false

        /* УЗНАТЬ, ЯВЛЯЕТСЯ ЛИ ПОЛЕ ЭКЗЕМПЛЯРОМ ПЕРЕЧИСЛИМОГО ТИПА */
        f.isEnumConstant(); // false

        /* ПОЛУЧЕНИЕ ИНФОРМАЦИИ О ТИПЕ ПОЛЯ*/

        f = c.getDeclaredField("mod");
        f.getType(); // class java.lang.reflect.Modifier

        f = c.getDeclaredField("l");
        f.getType(); // interface java.util.List
        f.getGenericType(); // java.util.List<?>

        f = c.getDeclaredField("l2");
        f.getType(); // interface java.util.List
        f.getGenericType(); // java.util.List<java.lang.Integer>


        /* ПОЛУЧЕНИЕ И ОБРАБОТКА ИНФОРМАЦИИ О МОДИФИКАТОРАХ ПОЛЯ */
        Modifier.toString(f.getModifiers()); // static

        /* ПОЛУЧЕНИЕ И УСТАНОВКА ЗНАЧЕНИЯ В ПОЛЕ */
        f = c.getDeclaredField("i");
        Reflection reflection = new Reflection();

        /*ПОЛУЧЕНИЕ*/
        i = (Integer) f.get(reflection);

        /*УСТАНОВКА*/
        f.set(reflection, 44);

        /*~~~~~~~~~~~~~~~~~~~~~~МЕТОДЫ~~~~~~~~~~~~~~~~~~~~~~*/
        ma = c.getDeclaredMethods();
        m = c.getDeclaredMethod("meth", Integer.class, int[].class);

        /*ПОЛУЧЕНИЕ МОДИФИКАТОРОВ*/
        m.getModifiers(); // final

        /* ПОЛУЧЕНИЕ ПАРАМЕТРОВ */
        m.getParameters();

        /* ЗАПУСК МЕТОДА */
        m.invoke(reflection, 12345, new int[2]); // password is 12345, numbers are [0,0]


        /*~~~~~~~~~~~~~~~~~~~~~~КОНСТРУКТОРЫ~~~~~~~~~~~~~~~~~~~~~~*/
        class Inside {
            public Inside() {
                System.out.println("Default constructor");
            }

            public Inside(int i) {
                System.out.println("Constructor with params");
            }
        }
        c = Inside.class;

        /* ДЕФОЛТНЫЙ КОНСТРУКТОР */
        c.newInstance(); // Default constructor

        /* НЕДЕФОЛТНЫЙ КОНСТРУКТОР */
        k = c.getDeclaredConstructor(int.class);

        k.newInstance(5);


        /*~~~~~~~~~~~~~~~~~~~~~~МАССИВЫ~~~~~~~~~~~~~~~~~~~~~~*/
        c = Reflection.class;
        f = c.getDeclaredField("da");
        c = f.getType(); // [[D
        c.isArray(); // true

        /*УЗНАТЬ ТИП МАССИВА И ТИП КОМПОНЕНТОВ*/
        c.getName(); // [[D
        c.getComponentType(); // class [D

        /*УЗНАТЬ ДЛИНУ МАССИВА*/
        Array.getLength(da);

        /*~~~~~~~~~~~СОЗДАНИЕ НОВОГО МАССИВА, УСТАНОВКА И ЧТЕНИЕ ЗНАЧЕНИЙ ~~~~~~~~~~~*/
        /*СОЗДАНИЕ*/
        Object o = Array.newInstance(double.class, 2);

        /*УСТАНОВКА ЗНАЧЕНИЙ*/
        Array.set(o, 0, 2.3);
        Array.set(o, 1, 4.3);

        /*ЧТЕНИЕ ЗНАЧЕНИЙ*/
        Array.get(o, 1);
        System.out.println(Array.get(o, 1));


        /*~~~~~~~~~~~~~~~~~~~~~~ПЕРЕЧИСЛЕНИЯ~~~~~~~~~~~~~~~~~~~~~~*/
        c = Reflection.TestEnum.class;
        f = c.getDeclaredField("ONE");
        System.out.println(f.isEnumConstant());

        f = c.getDeclaredField("testEnum");

        c = f.getType(); // class __types_system.Reifiability$TestEnum
        c.isEnum(); // true

        /*ПОЛУЧИТЬ СПИСОК ВОЗМОЖНЫХ КОНСТАНТ */
        Arrays.asList(c.getEnumConstants()); // [ONE, TWO]
        System.out.println(Arrays.asList(c.getEnumConstants()));


    }


    public static final class Child<T, V> extends Reflection {
        public int f;
        private int pf;

        class ChildOwnInner {
        }
    }

    public static class MainInner {
    }

    final void meth(Integer password, int[] numbers) {
        System.out.println("password is: " + password);
        System.out.println("numbers are: " + Arrays.toString(numbers));
    }
}