package types_references_annotations;

import com.sun.istack.internal.NotNull;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;


/* ФОРМА МЕТАДАННЫХ СО СПРАВОЧНЫМИ ДАННЫМИ О ПРОГРАММЕ
 * - обработка компилятором: использует ее для обнаружения ошибок и подавления предупреждений
 * - обработка во время компиляции и деплоя - программные инструменты могут обрабатывать
 * информацию из аннотаций для генерации кода, XML файлов и т.д. (напр. JavaDoc)
 * - обработка во время рантайма - некоторые доступны для анализа во время работы программы */


/*ФОРМАТ
 * - @
 * - тип аннотации
 * - (инициализируемые члены и их значения):
 *       - могут быть с именем или без
 *       - если 1 элемент (или несколько, но с дефолтными значениями и они не указываются) и он с
 *       именем value, то имя можно не указывать
 *       - если нет элементов
 *          - это "маркерная" аннотация
 *          - скобки можно опустить */


/* МЕСТА ИСПОЛЬЗОВАНИЯ
 * - ТОЛЬКО при объявлении:
 *      - классов
 *      - полей
 *      - методов
 *      - др. эл-тов программы
 *
 * - todo ЛИБО в типовой аннотации (начиная с J8 SE):
 *      - создание класса:
 *          new @Interned MyObject();
 *
 *      - приведение типа:
 *          myString = (@NonNull String) str;
 *
 *      - имплементация интерфейса:
 *          class UnmodifiableList<T> implements @Readonly List<@Readonly T> { ... }
 *
 *      - объявление исключений:
 *          void monitorTemperature() throws @Critical TemperatureException { ... }
 *
 *      - !!! J8 НЕ ПРЕДОСТАВЛЯЕТ ФРЕЙМВОРК ДЛЯ ПРОВЕРКИ ТИПА:
 *          - но можно, напр., скачать такой в виде подключаемого модуля и он будет использоваться
 *          вместе с компилятором */


/* АННОТАЦИЯ - ФОРМА ИНТЕРФЕЙСА
 * - но вначале идет @
 * - все аннотации автоматически расширяют интерфейс Annotation (java.lang.annotation):
 *      - в нем переопределены методы:
 *          - hashCode()
 *          - equals()
 *          - toString()
 *      - в нем объявлен метод annotationType(), возвращающий объект типа Class, представляющий
 *      вызывающую аннотацию
 *      - при ручном расширении интерфейса - определения типа аннотации не происходит
 *      - сам интерфейс также не определяет тип аннотации*/



/* ПРЕДОПРЕДЕЛЕННЫЕ ТИПЫ
 * - используемые компилятором (java.lang):
 *      - @Deprecated:
 *          - элемент является устаревшим, его не нужно использовать
 *          - компилятор генерирует предупреждение при использовании помеченного элемента
 *          - элемент следует задокументировать в Javadoc тегом @deprecated
 *
 *      - @Override:
 *          - элемент предназначен для переопределения элемента из суперкласса
 *          - необязателен, но помогает предотвратить ошибки (компилятор сообщает о неправильном
 *          переопределении)
 *
 *      - @SuppressWarnings:
 *          - подавить конкретные предупреждения, которые иначе бы выводил компилятор (напр. в
 *          случае с @Deprecated методом)
 *          - deprecation - устаревший элемент и не желателен в использовании
 *          - unchecked - может происходить при использовании старого кода до обобщений
 *          - подавить сразу несколько @SuppressWarnings({"unchecked", "deprecation"})
 *
 *      - @SafeVarargs:
 *          - предположение, что код не исполняет потенциально опасные операции с переменным числом
 *          параметров (varargs) метода или конструктора
 *              - это важно при работе с обобщениями, где массивы обобщенного типа запрещены, но для
 *              varargs (которые также являются массивами) запрет ослаблен до предупреждения
 *              компилятора
 *          - подавляет unchecked предупреждения, связанные с их использованием
 *          - применим только к нематерилизируемым типам (т.е. тем, тип которых не полностью известен
 *          в рантайме (т.е. параметризированный тип))
 *          - метод должен быть финализирован или статический
 *
 *      - @FunctionalInterface:
 *          - объявление типа предполагает функциональный интерфейс
 *
 *
 * - используемые другими аннотациями (java.lang.annotation) ("мета-аннотации"):
 *      - @Retention:
 *          - уточняет, где используется отмеченная аннотация:
 *                 - RetentionPolicy.SOURCE – сохраняется только в коде и игнорируется компилятором
 *                 - RetentionPolicy.CLASS (дефолтный) – сохраняется компилятором во время
 *                 компиляции, но игнорируется JVM
 *                 - RetentionPolicy.RUNTIME – сохраняется JVM и может использоваться в runtime
 *                 environment
 *
 *      - @Documented:
 *          - используемые здесь элементы должны документироваться при помощи инструмента Javadoc
 *          (по дефолту этого не происходит)
 *
 *      - @Target:
 *          - отмечает у другой аннотации ограничение на тип элементов, с которыми она может быть
 *          использована:
 *              - ElementType.ANNOTATION_TYPE - с любым типом аннотаций
 *              - ElementType.CONSTRUCTOR - с конструктором
 *              - ElementType.FIELD - с полем или свойством
 *              - ElementType.LOCAL_VARIABLE - с локальной переменной
 *              - ElementType.METHOD - с методом
 *              - ElementType.PACKAGE - с объявлением пакета
 *                  - отмечать нужно в отдельном файле package-info.java
 *              - ElementType.PARAMETER - с параметрами метода
 *              - ElementType.TYPE - с любым элементом класса
 *
 *      - todo @Inherited:
            - относится только к объявлению классов
 *          - тип аннотаций может быть унаследован от суперкласса (по дефолту не так)
 *          - при запросе на тип аннотаций, если у текущего нет такого типа, он ищется в суперклассе
 *
 *      - @Repeatable:
 *          - аннотация может применяться более 1 раза к подобному объявлению или использованию типа */


/* ПОВТОРЯЮЩИЕСЯ АННОТАЦИИ
 * - иногда нужно применить ту же аннотацию к объявлению или использованию типа
 * - несколько аннотаций 1 типа - повторяющиеся аннотации
 * - начиная с J8 SE
 * - можно использовать везде, где можно использовать стандартную аннотацию
 * - по причинам совместимости хранятся в контейнерах аннотаций (автоматически создаются компилятором)*/


/* ОГРАНИЧЕНИЯ
 * - одна аннотация не может наследовать другую
 * - todo не могут быть обобщенными (т.е. не могут принимать параметры типа)*/


/* ДРУГИЕ ОСОБЕННОСТИ
 * - не являются частью программы и не имеют эффекта на код
 * - по конвенции каждая аннотация идет на новой строке */

/*~~~~~~~~~~~~~~~~~ОБЪЯВЛЕНИЕ СВОЕГО ТИПА АННОТАЦИИ~~~~~~~~~~~~~~~~~
 * - поля:
 *       - выглядят как методы
 *          - не должны иметь параметров!
 *          - нельзя указывать throws
 *       - могут иметь дефолтные значения
 *          - не должно быть null
 *       - разрешены только:
 *          - перечисления
 *          - примитивные типы (константы)
 *          - объект класса String (константы)
 *          - объекты класса Class;
 *          - тип другой аннотации;
 *          - массив одного из предыдущих типов
 * - не может включать слово extends */

/* ~~~~~~~~~~~~~~~~~ПРИМЕР БЕЗ ПАРАМЕТРОВ~~~~~~~~~~~~~~~~~
 * - маркерная аннотация
 * - заодно буду использовать для методов, у которых нет параметров*/
@Retention(RetentionPolicy.RUNTIME)// будет виден в рантайме
@Target(ElementType.METHOD) //применимость - только методы
@Documented
        // поместить в документацию
@interface NoParameters {

}

/* ~~~~~~~~~~~~~~~~~ПРИМЕР С ПАРАМЕТРОМ VALUE~~~~~~~~~~~~~~~~~
 * - остальных параметров не должно быть или они должны иметь дефолтное значение (т.е. они не будут
 * указыватся в момент аннотирования)*/
@Inherited
        // также будет распространятся на подклассы помеченного класса
@interface OneParameter {
    String someOtherValue() default "some"; // нужно дефолтное значение

    // ключ "value" параметра можно будет потом не указывать (только для value)
    int value(); // поля выглядят как методы
}

/* ~~~~~~~~~~~~~~~~~ПРИМЕР СО ВСЕМИ ДОПУСТИМЫМИ ПАРАМЕТРАМИ~~~~~~~~~~~~~~~~~*/
@interface AllParameters {

    enum MyEnum {ONE, TWO} // объявление enum

    int d = 4 + 2342; // объявление примитива

    MyEnum myEnum() default MyEnum.ONE; // использование enum

    int primitive() default d; // примитив: должно быть константное значение

    String string() default "только" + "константа"; // cтрока: должно быть константное значение

    Class<?> someClass() default Void.class; // объект какого-то класса

    OneParameter oneParameter() default @OneParameter(5); // другая аннотация

    String[] stringArray(); // массив типа из указанных выше элементов

//    Object obj(); // нельзя использовать любые другие параметры
//    String string2() default null; // дефолтное значение не должно быть null
//    int notMethod (int i); // не должно быть параметров
//    int notMethod () throws NullPointerException; // не должно быть throws
}

/* ~~~~~~~~~~~~~~~~~ПРИМЕР АННОТАЦИИ ДЛЯ ВСЕГО ПАКЕТА~~~~~~~~~~~~~~~~~
 * - указать аннотацию нужно в отдельном файле package-info.java*/
@Target(ElementType.PACKAGE)
@interface ForWholePackage {
}

/* ~~~~~~~~~~~~~~~~~ПРИМЕР ПОВТОРЯЮЩЕЙСЯ АННОТАЦИИ~~~~~~~~~~~~~~~~~*/
@Retention(RetentionPolicy.RUNTIME) // аннотации будут видны в рантайме
@Target(ElementType.METHOD) // только для методов
@Repeatable(Schedules.class)
        // нужно указать тип аннотации-контейнера, который сгенерирует компилятор
//@Target(ElementType.METHOD)
@interface Schedule {
    boolean fiveTimes() default false;

    boolean tenTimes() default false;

    String author() default "me";
}

/* ПОВТОРЯЮЩЕЙСЯ АННОТАЦИИ НУЖНА АННОТАЦИЯ-КОНТЕЙНЕР С ЭЛЕМЕНТОМ VALUE МАССИВНОГО ТИПА АННОТАЦИИ
 * - должны быть те же мета-аннотации*/
@Target(ElementType.METHOD)//
@Retention(RetentionPolicy.RUNTIME)
@interface Schedules { //
    Schedule[] value();
}


//@NoParameters // не могу поставить классу, т.к. эта аннотация ограничена методами
@OneParameter(2) // если параметр value, то значение ключа можно не указывать
public class Main {

    // @SuppressWarnings("deprecation") // отменит обозначение компилятором устаревших элементов
    public static void main(String[] args) {
        @AllParameters(primitive = 3,
                myEnum = AllParameters.MyEnum.TWO,
                string = "какая-то" + "строка",
                stringArray = {"dfdsf", "dfssdf"},
                someClass = Main.class,
                oneParameter = @OneParameter(3)
        )
        int b = 5;

//        @Deprecated // нельзя использовать в этом месте, т.к. это не объявление
        deprecatedMethod(); // компилятор обозначает использование устаревшего элемента


        /*ВЫВЕСТИ В РАНТАЙМЕ ВСЕ МЕТОДЫ БЕЗ ПАРАМЕТРОВ*/
        printNoParametersMethods(Main.class);


        /*ВЫВОДИТЬ HEY ПО НАСТРОЙКАМ SCHEDULE*/
        Main main = new Main();
        printHey(Main.class);
    }

    /*МЕТОД ДЛЯ ВЫВОДА ВСЕХ МЕТОДОВ В КЛАССЕ БЕЗ ПАРАМЕТРОВ*/
    private static void printNoParametersMethods(Class c) {
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(NoParameters.class)) {
                    System.out.printf("В классе %1$s аннотация %2$s используется методом %3$s\n",
                            c.getSimpleName(), annotation.toString(), method.getName());
                }
            }
        }
    }

    /*МЕТОД С ПОВТОРЯЮЩЕЙСЯ АННОТАЦИЕЙ + ПОЛУЧЕНИЕ ЗНАЧЕНИЙ*/
    @Schedule(author = "me")
    @Schedule(fiveTimes = true) // повторяющаяся аннотация
    @Schedule(tenTimes = true)
    private static void printHey(Class<Main> c) {
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Schedules.class)) { // нужен сначала контейнер
                    System.out.println(annotation);
                    /* Даст: @Schedules(value=[
                    @Schedule(tenTimes=false, author=me, fiveTimes=false),
                    @Schedule(tenTimes=false, author=me, fiveTimes=true),
                    @Schedule(tenTimes=true, author=me, fiveTimes=false)])*/
                    Schedules schedules = (Schedules) annotation;
                    Schedule[] schedules1 = schedules.value();
                    for (Schedule schedule : schedules1) {
                        if (schedule.fiveTimes())
                            System.out.println("hey, hey, hey, hey, hey");
                        if (schedule.tenTimes())
                            System.out.println("hey, hey, hey, hey, hey, hey, hey, hey, hey, hey, ");
                    }
                }
            }
        }
    }


    /**
     * @deprecated метод устарел, еба
     * но документирующий коммент писать не обязятельно
     */
    @Deprecated
    @NoParameters // у этого метода нет параметров
    public static void deprecatedMethod() {
        deprecatedMethod2(null);
        //TODO: компилятор почему-то здесь не обозначает использование устаревшего метода
    }

    @Deprecated
    public static void deprecatedMethod2(@NotNull String s) {
        // NotNull это просто обозначение для себя, что нужно сделать проверку
        // компилятор разрешит передать сюда null
    }


    @NoParameters
    @Override // переопределение родительского метода
    public String toString() {
        return "new string";
    }

    @SafeVarargs // метод должен быть статический или финализированный
    static <T> void varargs(ArrayList<T>... a) { // должен быть нематериализируемый тип
        // без аннотации компилятор предупреждает о возможном загрязнении кучи
    }

    @FunctionalInterface
    interface funcInterface {
        void onlyMethod();
    }
}