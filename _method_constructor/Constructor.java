package _method_constructor;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

import _types_references_annotations.my_annotations.Ntrstn;


/* ЯВЛЯЕТСЯ СПЕЦИАЛЬНЫМ МЕТОДОМ ДЛЯ СОЗДАНИЯ ЭКЗЕМПЛЯРА КЛАССА
 * - ОТЛИЧИЯ ОТ МЕТОДА:
 *      - ничего не возвращает
 *          - даже void
 *      - не бывает переопределения родительского конструктора
 *      - не может быть:
 *         - static
 *         - final
 *         - abstract
 *         - native
 *         - synchronized
 *      - в теле может быть явный вызов другого конструктора
 *          - через this() или super()
 *      - может существовать неявный дефолтный
 *      - в классе должен быть как минимум 1 конструктор
 *      - вызов всегда с операцией new */


/* ОПРЕДЕЛЕНИЕ
 * 1. Модификаторы
 *      - может отсутсвовать
 *          - т.е. default
 *      - модификаторы доступа:
 *          - контролируют, откуда можно вызвать конструктор
 *          - может быть private
 *              - тогда вызов возможен только изнутри объекта
 *      - не может быть:
 *          - abstract
 *          - final
 *          - native
 *          - static
 *          - synchronized
 *
 * 2. Имя конструктора
 *      - должно совпадать с именем класса
 *      - !! может совпадать с именем метода
 *          - разница:
 *              - метод должен что-то возвращать
 *                  - в т.ч. void
 *              - имя метода по конвенции с маленькой буквы
 *
 * 3. Список принимаемых параметров
 *      - в скобках
 *          - через запятую
 *              - перед каждым указывается тип
 *          - параметр может быть финализирован
 *              - его значение нельзя изменить
 *          - перед типом параметра может идти аннотация
 *      - пустые скобки, если ничего не принимает
 *
 * 4. Список бросаемых исключений
 *
 * 5. Тело конструктора
 *      - в фигурных скобках
 *          - вызов другого конструктора
 *              - this() или super()
 *              - должен идти первым
 *          - выражения
 *          - инструкции
 *          - классы
 *          - блоки кода
 *          - локальные переменные
 *          - может иметь инструкцию return без возвращаемого значение
 *              - однако это и так происходит автоматически */


/* ДЕФОЛТНЫЙ КОНСТРУКТОР
 * - у класса всегда есть минимум 1 конструктор
 *      - даже если он не указан явно
 *          - тогда создается автоматически дефолтный без аргументов
 *              - все поля будут инициализированы с дефолтными значениями
 *
 * - !! дефолтный конструктор вызывает конструктор без аргументов в суперклассе (или Object)
 *      - если такого нет, то компилятор сообщит
 *
 * - если есть конструктор с аргументами, а без аргументов явного нет, то создавать объект без
 * аргументов нельзя */


/* ВЫЗОВ
 * - вызов всегда происходит вместе с операцией new
 *      - т.е. не применим к существующим объектам
 *
 * - аргументы передаются по значению
 *
 * - родительский класс всегда создается первым
 *      - см. инициализация
 *
 * - явный вызов другого конструктора из тела конструктора
 *      - происходит с помощью this() и super()
 *          - должен идти первым в теле конструктора
 *              - поэтому может быть только один
 *          - выбирается в соответствии с аргументами в скобках
 *      - this() - вызов другого конструктора текущего класса
 *      - super() - вызов конструктора родительского класса
 *          - вызов super() всегда итак происходит неявно до создание наследника
 *              - но без явного указания super() с аргументами, вызывается родительский конструктор
 *              без аргументов
 *                  - а если такого нет - ошибка компилятора */


/* ВОЗВРАЩЕНИЕ
 * - аналогично методу, но не имеют явного возвращаемого типа
 *      - не возвращают даже void
 *      - !! но неявно возвращаемым типом является тип самого класса */


/* ПЕРЕГРУЗКА КОНСТРУКТОРОВ
 * - конструкторов может быть несколько
 * - дифференциируются по сигнатуре:
 *      - числу параметров
 *      - типам параметров
 *          - в т.ч. после стирания типов
 *      - порядку параметров
 * - если для параметра нет собственного конструктора, то может использоваться автоматическое
 * преобразование
 *      - напр. аргумент типа int войдет в конструктор с типом double
 *      - способствует полиморфизму
 *          - 1 название метода - разные реализации
 *      - ухудщает читаемость */


/* МОГУТ БЫТЬ С ПЕРЕМЕННЫМ ЧИСЛОМ АРГУМЕНТОВ
 * - аналогично методам */


/* МОГУТ БЫТЬ ОБОБЩЕННЫМИ
 * - аналогично методам
 *   - но без переопределения */

@Ntrstn("Конструктор похож на статический метод (но без модификатора static)")

@Ntrstn("Конструктор также может иметь инструкцию return (без возвращаемого значения), однако это" +
        "и так происходит автоматически (возвращается void)")

@Ntrstn("Если в наследнике используется дефолтный конструктор (без аргументов), то родителе также " +
        "должен быть дефолтного конструктор, иначе - ошибка компилятора")

@Ntrstn("Конструктор явно не возвращает даже void, но неявно возвращает тип самого класса ")

@Ntrstn("Методы, вызываемые из конструктора, обычно должны быть финализированными, иначе подкласс " +
        "может переопределить такой метод с неожиданными результатами")

public class Constructor extends Parent {

    /* ЕСЛИ ИСПОЛЬЗУЕТСЯ ДЕФОЛТНЫЙ КОНСТРУКТОР, ТО В РОДИТЕЛЕ ОН ТАКЖЕ ДОЛЖЕН БЫТЬ */
    /* ЕСЛИ ЕСТЬ КОНСТРУКТОР С АРГУМЕНТАМИ, ТО ИСПОЛЬЗОВАТЬ ДЕФОЛТНЫЙ НЕЯВНО НЕ ПОЛУЧИТСЯ */
    public Constructor(int i) {
    }


    public static void main(String[] args) {
//        new Constructor(); // дефолтный неявно использовать не получится
    }
}

class Parent {
    /* У РОДИТЕЛЯ ДОЛЖЕН БЫТЬ ДЕФОЛТНЫЙ КОНСТРУКТОР, ЕСЛИ НАСЛЕДНИК САМ ИСПОЛЬЗУЕТ ДЕФОЛТНЫЙ */
    public Parent() {
    }
}

/* ЯВНЫЕ ВЫЗОВЫ ДРУГИХ КОНСТРУКТОРОВ */
class ConstructorChild extends Constructor {
    /*ЯВНЫЙ ВЫЗОВ ДРУГОГО КОНСТРУКТОРА ДАННОГО КЛАССА */
    public ConstructorChild() {
        this(4);
    }

    /* ЯВНЫЙ ВЫЗОВ КОНСТРУКТОРА СУПЕРКЛАССА */
    public ConstructorChild(int i) {
        super(i);
    }
}


/* КОНСТРУКТОР МОЖЕТ ИМЕТЬ ТО ЖЕ ИМЯ, ЧТО И МЕТОД */
class NameMatch {
    public void NameMatch() {
    }

    public NameMatch() {
    }
}


/* НЕ МОГУТ БЫТЬ */
/*- abstract
 *          - final
 *          - native
 *          - static
 *          - synchronized*/

class Prohibited {
    /*ABSTRACT*/
    //    abstract Prohibited() { }

    /*FINAL*/
    //    final Prohibited() { }

    /*NATIVE*/
    //    native Prohibited() { }

    /*STATIC*/
    //    static Prohibited() { }

    /*SYNCHRONIZED*/
    //    synchronized Prohibited() { }

}

/* КОНСТРУКТОРЫ МОГУТ БЫТЬ ПЕРЕГРУЖЕНЫ */
class Multiple {
    /* ДОЛЖНЫ ОТЛИЧАТЬСЯ СИГНАТУРОЙ */
    public Multiple(int i, double d) {
    }

    public Multiple(double d, int i) {
    }

    /* МОЖЕТ ПРОИСХОДИТЬ АВТОМАТИЧЕСКОЕ ПРЕОБРАЗОВАНИЕ В ОТСУТСТВИЕ ПОДХОДЯЩЕГО КОНСТРУКТОРА */
    public Multiple(double d) {
    }

    public static void main(String[] args) {
        new Multiple(4); // вызов Multiple(double d)
    }
}


/* ПАРАМЕТРЫ МОГУТ БЫТЬ ФИНАЛИЗИРОВАНЫ И ИМЕТЬ АННОТАЦИЮ */
class Parameters {
    public Parameters(final @NotNull double d) {
    }
}


/* МОЖЕТ ВЫБРАСЫВАТЬ ИСКЛЮЧЕНИЕ */
class Throwing {
    public Throwing() throws ArithmeticException {
    }
}


/* МОЖЕТ ИМЕТЬ ИНСТРУКЦИЮ RETURN, ДАЖЕ ЕСЛИ НИЧЕГО НЕ ВОЗВРАЩАЕТСЯ */
class Return {
    public Return(int i) {
        return;
    }
}


/* МОГУТ ИМЕТЬ ПЕРЕМЕННОЕ ЧИСЛО ПАРАМЕТРОВ */
class VarArgs {
    public VarArgs(int... ints) {
        System.out.println(ints.length);
    }
}


/* МОГУТ БЫТЬ ОБОБЩЕННЫМИ */
class Generics {

    /* ВНУТРИ ОБОБЩЕННОГО КЛАССА ВСЕ КОНСТРУКТОРЫ ЯВЛЯЮТСЯ ОБОБЩЕННЫМ */
    class Generic<T> {
        void Generic() {
            ArrayList<T> list = new ArrayList(); // использование обобщенного типа класса
        }
    }

    /* МОЖЕТ БЫТЬ ВНУТРИ НЕ ОБОБЩЕННОГО КЛАССА */
    <E> Generics(ArrayList<E> list) {
    }
}