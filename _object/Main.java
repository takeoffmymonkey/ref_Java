package _object;


/* ЯВЛЯЕТСЯ ВЕРШИНОЙ ВСЕХ КЛАССОВ
 * - в т.ч. массивов
 * - все классы наследуют его методы*/


/* ПЕРЕМЕННУЮ ТИПА OBJECT МОЖНО ИСПОЛЬЗОВАТЬ КАК ССЫЛКУ НА ОБЪЕКТ ЛЮБОГО ТИПА */


/*~~~~~~~~~~~~~~~~~~~~~~~МЕТОДЫ~~~~~~~~~~~~~~~~~~~~~~~*/
/* CLONE()
 * - protected native Object clone() throws CloneNotSupportedException:
 *      - создает и возвращает объект такого же класса и инициализирует переменные с теми же
 *      значениями, что и у оригинала
 *
 * - контракт для переопределения (не обязательный):
 *              - x.clone() != x даст true
 *              - x.clone().getClass() == x.getClass() даст true
 *              - x.clone().equals(x) даст true
 *
 * - делает "неглубое копирование"
 *      - т.е. значения не клонируются, а копируются
 *          - т.е. для ссылочных переменных не создаются новые объекты, а копии ссылок на них
 *
 * - метод придется переопределить, т.к. он protected
 *      - по конвенции копия должна возвращаться вызовом super.clone()
 *      - по конвенции объект и копия должны быть полностью независимы
 *          - т.н. "глубокое копирование":
 *              - в случае, если у объекта не только примитивные поля и неизменяемые объекты по
 *              ссылкам, нужно создать копии объектов по этим ссылкам в данном объекте
 *                  - чтобы изменения в одном не затрагивали изменения в другом
 *
 * - можно использовать, если класс или суперкласс имплементируют маркерный интерфейс Cloneable
 *      - иначе CloneNotSupportedException
 *      - класс Object не имплементирует Cloneable
 *          - т.е. вызов на нем приведет к исключению
 *      - все массивы имплементируют Cloneable
 *          - и возвращают массив типа объектов, в нем содержащихся
 *          - происходит "неглубокое" копирование
 *
 * - хорошей альтернативой клонирования является копирующий конструктор
 *      - принимает в качестве агрумента экземпляр собственного класса */


/* TOSTRING()
 * - public String toString():
 *      - возвращает строковое представление объекта
 * - по дефолту выводит имя класса и адрес объекта:
 *      - getClass().getName() + "@" + Integer.toHexString(hashCode());
 *          - equalstest.EqualsTest@27b92d
 * - желательно переопределять для своих классов
 *      - полезно для дебаггинга
 * - вместо вызова х.toString() можно воспользоваться выражением "" + х
 *      - даже если х относится к примитиву
 * - для массивов можно использовать Arrays.toString(), а для многомерных - deepToString() */


/* GETCLASS()
 * - public final Class getClass()
 *      - возвращает в runtime объект типа Class для объекта
 *
 * - todo настоящий результирующий тип:
 *      - Class<? extends |X|>
 *          - где |X| - результат стирания статического типа выражения, для которого вызывается
 *          getClass. Напр.:
 *              Number n = 0;
 *              Class<? extends Number> c = n.getClass();
 *
 * - можно использовать для получения информации о классе
 *     - имя:
 *         - getSimpleName()
 *     - суперкласс:
 *         - getSuperclass()
 *     - имплементированные интерфейсы:
 *         - getInterfaces()
 *     - и т.д.
 *         - имеет более 50 методов:
 *              - isAnnotation()), isInterface(), isEnum(), getFields(), getMethods() и т.д.
 * - нельзя переопределять */


/* FINALIZE()
 * - protected void finalize() throws Throwable
 *      - вызывается сборщиком мусора для объекта, когда JVM определила, что на него больше нет
 *      ссылок
 *          - т.е. метод обратного вызова
 * - нужно переопределять
 *       - оригинальная имплементация ничего не делает
 * - обычно для освобождения ресурсов
 * - если он вызывается, то время его вызова точно не известно
 * - не пойманные здесь исключения игнорируются, а выполнение метода прерывается
 * - никогда не вызывается больше 1 раза для 1 объекта
 * - вообще, не стоит на него особо полагаться для очистки
 *      - см. Сборка мусора */


/* EQUALS()
 * - public boolean equals(Object obj)
 *      - указывает, равняется ли указанный объект текущему
 *
 * - по дефолту:
 *     - сравнивает примитивы или ссылки на объекты
 *         - а не содержание объектов по ссылке
 *      - возвращает true, если оба его аргумента имеют пустое значение null
 *      - возвращает false, если только 1 из аргументов имеет пустое значение null
 *
 * - контракт для переопределения:
 *      - РЕФЛЕКСИВНОСТЬ: x.equals(x) должно давать true
 *      - СИММЕТРИЧНОСТЬ: x.equals(y) должно давать true, только если y.equals(x) дает true
 *      - ТРАНЗИТИВНОСТЬ: если x.equals(y) и y.equals(z) дают true, то x.equals(z) должен тоже
 *      - КОНСИСТЕНТНОСТЬ: для любых не-null объектов, КОТОРЫЕ НЕ МЕНЯЮТСЯ, множественный вызов
 *      x.equals(y) должен всегда возвращать одинаковое значение
 *      - НЕРАВЕНСТВО С NULL: если x не null, то x.equals(null) должен возвращать false
 *
 * - при переопределении:
 *      - сначала проверить, ссылаются ли объекты на 1 и тот же адрес в памяти
 *          - при помощи ==
 *          - если да, то они одинаковы
 *      - потом проверить, равен ли объект null
 *          - если да, вернуть false
 *              - т.к. оригинальный объект не null, иначе бы первая проверка вернула true
 *      - если подкласс семантически отличается от родителя, то нужно сделать проверку на
 *      принадлежность к тому же классу
 *          - если классы разные, вернуть false
 *      - если классы семантически не отличаются или являются одинаковым классом
 *          - если предыдущая проверка не проводилась
 *              - нужно проверить через instanseof
 *          - привести объект к сравниваемому классу
 *              - сравнить важные поля:
 *                  - примитивы через ==
 *                  - объекты через equals()
 *                  - массивы через Arrays.equals()
 *      - обычно нужно также переопределить hashCode()
 *          - т.к. по контракту hashCode(), одинаковые объекты должны иметь одинаковый хеш-код */


/* HASHCODE()
 * - public int hashCode()
 *      - возвращает значение хеш-кода объекта
 *      - нужен для хеш-таблиц при использовании коллекций
 *          - напр. HashMap
 *
 * - зачем переопределять:
 *      - напр., есть 2 равных по equals() объекта
 *          - первый есть в HashMap
 *              - нужно найти первый объект в HashMap по второму
 *                  - поиск происходит по хеш-коду
 *                      - hashCode() по дефолту возвращает разные коды для разных объектов в памяти
 *                          - поэтому первый объект не будет найден по второму
 *                              - поэтому нужно переопределить hashCode() по тем же полям, что
 *                              использует переопределенный equals()
 *
 * - контракт для переопределения:
 *      - в рамках 1 рантайма должен всегда возвращать 1 и то же целое число, если никакая
 *      информация, используемая в проверке в equals(), не изменилась
 *          - т.е. значение может быть разным при разных запусках программы
 *      - если 2 объекта равны по методу equals(), у них должен быть одинаковый хеш-код
 *          - т.е. при переопределении equals(), скорее всего придется переопределять hashCode()
 *      - необязательно, чтобы 2 неравных по методу equals() объекта, всегда возвращали разные
 *      хеш-коды
 *          - но это улучшит производительность при работе с хеш-таблицами
 *
 * - по дефолту:
 *      - возвращает разные хеш-коды для разных объектов
 *          - обычно при помощи конвертации адреса объекта в число
 *              - но такая имплементация не требуется Java
 *          - т.е. если 2 строки ссылаются на 1 область в памяти (в пуле), у них будет одинаковый
 *          хеш-код
 *      - по умолчанию используется Park-Miller RNG алгоритм
 *          - в основу его работы положен генератор случайных чисел
 *              - при каждом запуске программы у объекта будет разный хэш-код
 *
 * - при переопределении удобно использовать метод Objects.hash()
 *      - туда передаются важные для идентификации объекта поля
 *          - напр. те, что используются в equals()*/


/*todo ~~~~~~~~~~~~~~~~~~~~~~~ПОТОКОВЫЕ МЕТОДЫ~~~~~~~~~~~~~~~~~~~~~~~*/
/* NOTIFY()
 * - public final void notify() */

/* NOTIFYALL()
 * - public final void notifyAll(): */

/* WAIT()
 * - public final void wait(): */

/* WAIT(LONG TIMEOUT)
 * - public final void wait(long timeout) */

/* WAIT(LONG TIMEOUT, INT NANOS)
 * - public final void wait(long timeout, int nanos) */


import java.util.Objects;

import _types_references_annotations.my_annotations.Ntrstn;

@Ntrstn("Метод clone() копирует значения, а не клонирует их. Т.е. примитивные значения копируются " +
        "как надо, а для ссылочных переменных создаются их копии, а не копии объектов по их ссылкам")

@Ntrstn("Метод clone() при использовании вне класса (а именно там его обычно и нужно использовать) " +
        "придется переопределить, т.к. он объявлен как protected")

@Ntrstn("Метод clone() можно безопасно вызвать только для классов, которые имплементируют Cloneable. " +
        "Object его не имплементирует по дефолту, а массив - да (но копия происходит по значениям, " +
        "т.е. объекты по ссылкам клонированы не будут")

@Ntrstn("Хорошей альтернативой клонирования является копирующий конструктор, который принимает в " +
        "качестве агрумента экземпляр собственного класса")

@Ntrstn("Вместо вызова х.toString() можно воспользоваться выражением \" \" + х, даже если х " +
        "относится к примитиву")

@Ntrstn("Метод getClass() у Object нельзя переопределить, т.к. он является final")

@Ntrstn("При переопределении equals(), если подкласс будет семантически отличаться от родителя, то " +
        "до сверки полей нужно сделать отдельную проверку на принадлежность к тому же классу, и " +
        "вернуть false, если это объекты разных классов")

@Ntrstn("При переопределении equals(), обычно также нужно переопределить hashCode(), т.к. по " +
        "контракту, если объекты одинаковы, они должны возвращать одинаковый хеш-код")

@Ntrstn("hashCode() нужно переопределять, т.к. дефолтная реализация всегда возвращает разный код для " +
        "разных объектов по их адресу в памяти. Т.е. если объекты равны по equals(), я не смогу " +
        "найти первый объект в коллекции (где для поиска используется hashCode()) по второму")

@Ntrstn("Если 2 строки ссылаются на 1 область в памяти (в пуле), у них будет одинаковый хеш-код")

/* НЕЯВНОЕ НАСЛЕДОВАНИЕ ОТ OBJECT */
public class Main extends Object {

    public static void main(String[] args) {
        /* ПЕРЕМЕННУЮ ТИПА OBJECT МОЖНО ИСПОЛЬЗОВАТЬ КАК ССЫЛКУ НА ОБЪЕКТ ЛЮБОГО ТИПА */
        Object obj;
        obj = new Integer(2);
        obj = new Integer[]{};
        obj = new String();


        /* CLONE() */
        TestClass c = new TestClass(10, "10");
        c.mutableRefField = c.new InnerMutable(10);

        try {
            TestClass c1 = c.clone();
            System.out.println(c.clone() != c1); // true - разные адреса
            System.out.println(c.clone().getClass() == c1.getClass()); // true - одинаковые классы
            System.out.println(c.clone().equals(c1)); // true

            /* У МАССИВОВ ЕСТЬ CLONEABLE */
            int[] ints = {1, 2, 3};
            int[] ints2 = ints.clone(); // значения примитивов копируются
            Object[] objects = {new Object()};
            Object[] objects2 = objects.clone(); // копируются ссылки

        } catch (CloneNotSupportedException e) {
        }

        /* TOSTRING() */
        System.out.println(c.toString()); // даст TestClass:10


        /* GETCLASS() */
        System.out.println(c.getClass()); // даст class object.TestClass


        /* EQUALS() */
        TestClass x = new TestClass(10, "10");
        TestClass y = new TestClass(10, "10");
        TestClass z = new TestClass(10, "10");
        TestClass a = new TestClass(11, "11");
        System.out.println("рефлексивность: " + x.equals(x)); // true
        System.out.println("симметричность: " + (x.equals(y) && y.equals(x))); // true
        System.out.println("транзитивность: " + (x.equals(y) && y.equals(x) && x.equals(z))); // true
        System.out.println("консистентность: " + x.equals(y)); // true
        System.out.println("консистентность: " + x.equals(y)); // true
        System.out.println("консистентность: " + x.equals(y)); // true
        System.out.println("не равенство с null: " + x.equals(null)); // false
        System.out.println(x.equals(a)); // false

        /* HASHCODE() */
        System.out.println("Хеш-код x: " + x.hashCode()); // x, y, z должны быть одинаковы
        System.out.println("Хеш-код y: " + y.hashCode()); // x, y, z должны быть одинаковы
        System.out.println("Хеш-код z: " + z.hashCode()); // x, y, z должны быть одинаковы
        System.out.println("Хеш-код a: " + a.hashCode());
        String s = "Ok";
        String s2 = new String("Ok");
        System.out.println("Хеш-код у разных объектов строк:" + (s.hashCode() == s2.hashCode()));
        // true
    }
}


class TestClass implements Cloneable {
    int primitiveField; // не нужно копировать вручную
    String immutableRefField; // не нужно копировать вручную
    InnerMutable mutableRefField; // нужно копировать вручную

    public TestClass(int primitiveField, String immutableRefField) {
        this.primitiveField = primitiveField;
        this.immutableRefField = immutableRefField;
    }

    class InnerMutable {
        int primitiveInnerField;

        InnerMutable(int primitiveInnerField) {
            this.primitiveInnerField = primitiveInnerField;
        }

        @Override
        public boolean equals(Object o) { // переопределил на скорую руку
            return primitiveInnerField == ((InnerMutable) o).primitiveInnerField;
        }
    }


    /* ПЕРЕОПРЕДЕЛЕНИЕ clone()*/
    @Override
    public TestClass clone() throws CloneNotSupportedException {
        TestClass testClass = (TestClass) super.clone(); // получение через super.clone()
        // ручное копирование ссылочной переменной
        testClass.mutableRefField = new InnerMutable(mutableRefField.primitiveInnerField);
        return testClass;
    }


    /* ПЕРЕОПРЕДЕЛЕНИЕ toString()*/
    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + primitiveField;
    }


    /* ПЕРЕОПРЕДЕЛЕНИЕ finalize()*/
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        // здесь я освобождаю ресурсы
    }


    /* ПЕРЕОПРЕДЕЛЕНИЕ equals()*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // если 1 и тот же объект, то точно равны
        if (o == null || getClass() != o.getClass()) return false; // не должен быть null
        // ПРОВЕРКА КЛАССА НУЖНА, ЕСЛИ ПОДКЛАССЫ БУДУТ ОТЛИЧАТЬСЯ СЕМАНТИЧЕСКИ
        TestClass testClass = (TestClass) o; // нужно теперь привести
        return primitiveField == testClass.primitiveField && // и сравнить поля
                Objects.equals(immutableRefField, testClass.immutableRefField) &&
                Objects.equals(mutableRefField, testClass.mutableRefField);
    }


    /* ПЕРЕОПРЕДЕЛЕНИЕ hashCode()*/
    @Override
    public int hashCode() {
        // используются те же поля, что и в переопределенном equals()
        return Objects.hash(primitiveField, immutableRefField, mutableRefField);
    }
}