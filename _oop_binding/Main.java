package _oop_binding;

import types_references_annotations.my_annotations.Ntrstn;

/* СВЯЗЫВАНИЕ (ВЫЗОВА МЕТОДА В КОДЕ С РЕАЛЬНЫМ МЕТОДОМ, КОТОРЫЙ НУЖНО ВЫЗВАТЬ)
 * - методы должны быть одноименными
 *
 * - реальный метод может быть неизвестен при компиляции, т.к. ссылка родительского типа может
 * ссылаться на объект-наследник, который может переопределять родительские методы
 *
 * - является одной из ключевых концепций при обеспечении полиморфизма
 *      - обеспечивает корректный вызов метода для объекта по ссылке
 *
 * - реальный метод известнен - статическое (раннее) связывание
 *
 * - реальный метод неизвестен - динамическое (позднее) связывание */


/* СТАТИЧЕСКОЕ (РАННЕЕ) СВЯЗЫВАНИЕ
 * - компилятор точно знает какой из одноименных методов нужно вызывать:
 *      - либо для метода запрещено/невозможно переопределение:
 *          - private методы (являются final по умолчанию)
 *          - final методы
 *          - static
 *          - конструктор
 *
 *      - либо по параметрам:
 *          - параметры метода уникальны
 *              - т.е. это перегрузка, а не переопределение
 *          - в отсутствие подходящего метода, может быть произведено автоматические приведение
 *          параметра
 *              - напр. int в double
 *          - методы могут быть как в 1 классе, так и в нескольких, связанных наследованием
 *
 * - немного оптимальней динамического связывания, т.к. не требуется разрешение методов в runtime */


/* ДИНАМИЧЕСКОЕ (ПОЗДНЕЕ) СВЯЗЫВАНИЕ (ДИСПЕТЧЕРИЗАЦИЯ)
 * - механизм, с помощью которого вызов переопределенного метода разрешается во время выполнения
 *      - то же, что и вызов виртуального метода
 *
 * - вызов нужного метода (с одинаковой сигнатурой) происходит по фактическому типу объекта по ссылке
 *
 * - позволяет:
 *     - ограничить доступ к ненужным членам наследника
 *     - не делать лишнюю проверку на тип
 *     - todo видоизменять программы без перекомпиляции их кода, делая их динамически расширяемыми
 *          - напр. если в коде есть вызов метода через родительскую ссылку, а по ссылке окажется
 *          объект нового класса, то сам код с вызовом не нуждается в перекомпиляции
 *
 * - немного замедляет работу программы, т.к. разрешение методов происходит во время работы */


/* ДЛЯ ЭКОНОМИИ ВРЕМЕНИ В РАНТАЙМЕ JVM СОЗДАЕТ ТАБЛИЦУ МЕТОДОВ ДЛЯ КАЖДОГО КЛАССА
 * - в ней перечисляются сигнатуры всех методов класса и соответсвующие методы для запуска:
 *      - если для сигнатуры есть переопределенный метод, то используется он
 *      - если нет, то используется родительский
 *      - напр. для выдуманного класса Employee:
 *          getName() -> Employee.getName()
 *          raiseSalary(double) -> Employee.raiseSalary(double)
 *          toString() -> Object.toString()
 *
 * - процедура работы JVM:
 *      1. Загрузка таблицы методов класса, соответствующему реальному объекту по ссылке
 *      2. Определение, в каком классе есть метод с такой сигнатурой
 *      3. Вызов метода */


/* ПОПЫТКА ОПТИМИЗАЦИИ РАННИМ СВЯЗЫВАНИЕМ
 * - чтобы избежать издержки динамического связывания, раньше пытались финализировать метод
 *      - через final
 *
 * - сейчас для маленьких методов, которые не переопределяются и часто вызываются, динамический
 * компилятор производит автоматическое встраивание:
 *      - напр. вызов метода е.getName() заменяется доступом к полю е.name
 *      - если в JVM появится новый класс, который переопределит встроенный метод - придется
 *      отменить встраивание
 *          - выполнение программы замедлится
 *              - происходит редко */


@Ntrstn("Связывание само по себе означает связывание вызова метода в коде с фактическим одноименным(!) " +
        "методом, который будет вызван во время работы программы. Если реальный метод известен при " +
        "компиляции, то будет произведено статическое (раннее) связывание, если нет - динамическое " +
        "(позднее)")

@Ntrstn("Связывание является одной из ключевых концепций для обеспечения полиморфизма - помогает " +
        "правильно выбрать метод, который нужно вызвать для объекта")

@Ntrstn("Реальный метод, который будет вызван во время работы может быть неизвестен, т.к. в Java по " +
        "ссылке родительского типа может находится наследник, который может переопределять " +
        "родительские методы.")

@Ntrstn("Статическое связывание возможно в тех случаях, когда метод не может быть переопределен (он " +
        "final или private или static или вообще конструктор) либо это не переопределение, а простая " +
        "перегрузка, т.е. метод принимает уникальные параметры. Статическое связывание немного " +
        "оптимальней динамического, т.к. разрешение вызовов не происходит во время работы программы.")

@Ntrstn("У перегрузки метода при статическом связывании есть 2 особенности: 1 - если метода с " +
        "подходящим типом параметра нет, может быть произведено автоматическое преобразование " +
        "аргумента в подходящий параметр (напр. int в double) (что является проявлением полиморфизма) " +
        "и 2 - перегруженные методы не подразумевают нахождение только в 1 классе - т.е. наследник " +
        "может перегрузить родительский метод, просто изменив принимаемый параметр, и компилятору " +
        "остается понятным какой метод нужно вызвать по таким параметрам")

@Ntrstn("Динамическое связывание возможно только для переопределенных методов и подразумевает вызов " +
        "именно того метода, который переопределяет фактический объект по ссылке. Оно позволяет " +
        "ограничиться информацией только о родителе при работе с любыми наследниками и помогает " +
        "избежать лишней проверки типа перед использованием объекта. Т.к. разрешение методов " +
        "происходит во время работы программы, это немного замедляет ее работу.")

@Ntrstn("Чтобы ускорить процесс разрешения методов во время работы, JVM создает для каждого класса " +
        "таблицу методов, в которой в 1 колонке указана сигнатура метода, а в другой местонахождение " +
        "реального метода, который нужно вызвать при обращении по такой сигнатуре")

@Ntrstn("Ранее, чтобы избежать задержек от динамического связывания, те методы, которые точно не " +
        "планировалось переопределять, финализировали вручную. Сейчас для маленьких методов, которые " +
        "часто вызываются и не переопределяются, производится динамическое встраивание (напр. вызов " +
        "метода е.getName() заменяется доступом к полю е.name). Но если появится класс с " +
        "переопределением (что происходит редко), программа замедлится, т.к. нужно будет отменить " +
        "встраивание")

@Ntrstn("Связывание подразумевает методы, а не поля. Поэтому при вызове поля класса у объекта-" +
        "наследника через родительскую ссылку, будет вызвано поле родителя. Чтобы корректно " +
        "обратиться к полю в наследнике через такую ссылку, нужно при обращении вручную привести его " +
        "к наследнику")

public class Main {

    class A {
        void meth() {
            System.out.println("Class's A method");
        }
    }

    class B extends A {
        @Override
        void meth() {
            System.out.println("Class's B method");
        }
    }


    public static void main(String[] args) {
        Main m = new Main();

        A a = m.new A();
        B b = m.new B();
        a = b; // теперь ссылка типа A ссылается на объект класса B
        a.meth(); // Class's B method
    }
}