package __collections;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import types_references_annotations.my_annotations.Ntrstn;

/* АЛГОРИТМЫ РЕАЛИЗАЦИИ
 * - Hash
 * - Linked
 * - LinkedHash
 * - Tree
 * - Array
 * */


/* ИЕРАРХИЯ КОНТЕЙНЕРОВ
 * - {Collection}: группа элементов
 *      - {List}: упорядоченная группа элементов (последовательность)
 *          - LinkedList
 *          - ArrayList
 *
 *      - {Set}: группа элементов без дубликатов
 *          - HashSet
 *              - LinkedHashSet
 *          - TreeSet
 *
 *      - {Queue}: группа элементов, которые перед обработкой сначала должны быть добавлены
 *          - PriorityQueue
 *
 * - {Map}: связь ключ-значение; не может иметь дубликатов ключей; 1 ключ связан только с 1 значением
 *      - HashMap
 *          - LinkedHashMap
 *      - TreeMap
 *
 *
 *
 * */



/* ИНТЕРФЕЙС COLLECTION
 * - по сути это "случайный интерфейс", появившийся из-за общности между другими интерфейсами
 *
 * - позволяет писать более универсальный код - код написанный для интерфейса может применяться к
 * большему числу объектов
 *      - в C++ сходство между контейнерами выражается итератором, а не Collection
 *          - можно было бы и здесь так сделать, но здесь реализация Collection также означает
 *          поддержку методат iterator()
 *          - т.е. можно писать методы, принимающие либо Iterator либо Collection
 *              - но Collection еще и Iterable, т.е. можно форичить
 *              - но если влом реализовывать для своего класса все методы Collection, тогда удобней
 *              использовать Iterator
 *                  - правда, можно легко наследоваться от AbstractCollection
 *                      - но тогда нельзя ни от чего другого
 *                      - и все равно нужно реализовывать iterator() и size()
 *              - короче, Iterator все же модульней. Можно из метода своего класса просто возвращать
 *              свой итератор
 *
 * - последовательность отдельных элементов, формируемая по некоторым правилам:
 *      - интерфейс List: хранит элементы в определенной последовательности
 *          - реализация ArrayList:
 *          - реализация LinkedList:
 *      - интерфейс Set: среди хранимых элементов не должно быть повторов
 *          - реализация HashSet: обеспечивает самую быструю выборку элементов, но порядок
 *          следования элементов выглядит бессмысленно (что обычно неважно). Чаще всего используется
 *          так как чаще всего нужно тольк выяснить, есть ли элемент в списке
 *          - реализация TreeSet: хранит объекты упорядоченными по возрастанию в структуре "красно-
 *          черное дерево"
 *              - может на вход принимать компаратор для определения порядка добавления
 *          - реализация LinkedHashSet: хранит объекты в порядке добавления
 *      - интерфейс Queue: выдает элементы в порядке, определяемом дисциплиной очереди (обычно
 *      совпадающем с порядком вставки)
 *
 * - add(): "убеждается", что в коллекции присутствует заданный элемент
 *      - такая формулировка подразумевает функциональность множества, куда элемент может быть
 *      добавлен только раз, а для List всегда эквивалентен добавлению, т.к. проверка дубликатов
 *      отсутствует
 *
 * - методы
 *
 * - AbstractCollection предоставляет реализацию по умолчанию
 *

 *
 * - iterator() - 1 из самых важных методов интерфейса - возвращает объект, реализующий Iterator
 *
 * */

/* LIST - ГАРАНТИРУЕТ ХРАНЕНИЕ ЭЛЕМЕНТОВ В ОПРЕДЕЛЕННОЙ ПОСЛЕДОВАТЕЛЬНОСТИ
 * - добавляет в Collection методы вставки и удаления в середине списка
 *
 * - реализация ArrayList:
 *      - отличная скорость произвольного доступа
 *      - относительно медленная скорость вставки и добавления элементов
 *
 * - реализация LinkedList:
 *      - оптимальный последовательный доступ
 *      - низкозатратные операции вставки и удаления
 *      - относительно медленная скорость произвольного доступа
 *      - обладает более широкой функциональностью, чем ArrayList
 *          - есть методы, которые позволяют использовать его как стек, очередь или двустороннюю
 *          очередь
 *      - методы:
 *          - getFirst() и element(): возвращают первый элемент списка или NoSuchElementExcteption,
 *          если пустой
 *          - peek() - то же, но возвращает null, если список пуст
 *          - addFirst() - вставить элемент в начало списка
 *          - offer(), add(), addLast() - добавить элемент в конец списка
 *          - removeLast() - удаляет и возвращает последний элемент из списка
 *
 * - реализация Stack:
 *      - принцип LIFO:
 *          - первый вошел, последний вышел
 *      - LinkedList вполне может также работать
 *      - push() - добавляет
 *      - pop() - достает с удалением
 *      - peek() - достать без удаления
 *
 *
 * */

/* SET (МНОЖЕСТВА)
 * - не добавляет новое поведение в Collection, как list, а просто меняет его
 *   - идеально для полиморфизма - т.е. выражение другого поведения
 *
 * */


/* ИНТЕРФЕЙС MAP
 * - набор пар ключ-значение, с возможностью выборки значения по ключу
 *      - т.е. получить объект по другому объекту
 *      - похож на базу данных
 *      - т.е. напр. ключ страна, а значение столица - можно узнать столицу по стране
 * - также называется ассоциированный массив и словарь
 * - не может содержать повторные вхождения ключа
 * - Map.put(key, value) - добавить пару ключ-значение
 * - Map.get(key) - вернуть значение по ключу
 * - реализации:
 *      - HashMap: быстрый поиск, порядок хранения не предсказуем
 *      - TreeMap: ключи отсортированы по возрастанию
 *      - LinkedHashMap: ключи в порядке вставки без потери высокой скорости выборки как HashMap
 *
 * - containsKey(key)
 * - containsValue(value)
 *
 * - как и массивы и Collection, легко расширяются до нескольких измерений
 *      - напр. Map<Person, List<Pet>>
 * - может вернуть Set своих ключей, Collection своих значений или Set их пар
 * */


/* QUEUE
 * - первым пришел первым ушел
 * - для надежного перемещения объектов из одной области в другую
 * - играют важную роль в пареллельном программировании
 * - LinkedList реализует очередь
 * - offer() - добавить элемент в конец или вернуть false(why?)
 * - peek(), element() - вернуть элемент в начале очереди без удаления. peek - возвращает null для
 * пустой очереди, а element - noSuchElementException
 * - poll(), remove() извлекают элемент в начале и возвращают его, но пол возвращает нал для пустой
 * очереди, а ремув - но сач эл
 * - методы специфические для очереди предоставляют полную и законченную функциональность. Т.е. вы
 * получаете работоспособную очередь без испольлзования методов Collection, от которого наследует очередь
 * */

/* PRIORITYQUEUE
 *  - следующим извлекается элемент обладающий наивысшим приоритетом
 *  - работает с Integer, String, Character
 *  - для своих классов можно использовать свой Comparator
 * */

/* COLLECTIONS */


/* ITERABLE */

/* КОМПАРАТОР
 * - объект, определяющий порядок
 * */

/*ITERATOR
 * - объект, обеспечивающий перемещение по последовательности объектов с выбором каждого объекта
 * этой последовательности
 * - создание не должно занимать много ресурсов
 * - является паттерном проектирования
 * - можно выполнять следующие операции:
 *      - iterator() - запросить итератор у Collector. Итератор готов вернуть начальный элемент
 *      последовательности
 *
 * - методы:
 *      - next() - получить следующий элемент последовательности
 *      - hasNext() - проверить, есть ли еще объекты в последовательности
 *      - remove() - удалить из последовательности последний элемент, возвращенный итератором
 *          - не обязателен для имплементации
 *
 * - унифицируют доступ к контейнерам
 *      - т.е. тип не важен
 *
 * - может перемещаться только вперед
 * */

/*LISTITERATOR
 * - более мощная разновидность Iterator, работает только с классами List
 * - является двусторонним hasPrevious()
 * - может выдавать индексы следующего и предыдущего элементов относительно текущей позиции
 * итератора в списке
 * - может заменить последний посещенный элемент методом set()
 * - listIterator() возвращает объект ListIterator, указывающий на начало List
 * - listIterator(n) создает объект ListIterator, установленный в указанную позицию
 *
 * */


/*ITERABLE и FOREACH
 * - использовать, если просто перемещаться по списку, без изменения элементов
 * - Iterable содержит метод iterator() для получения объекта Iterator
 * - интерфейс используется foreach для перемещения по списку
 *      - т.е. любой класс, который реализует этот интерфейс может использоваться форичем
 * - реализуется любой Collection, но не Map
 * - команда форич работает с массивом или любой другой реализацией итерабл но это не означает, что
 * массив автоматически реализует итерабл и не подразумевает автоматической упаковки
 *      - т.е. нельзя передать массив вместо итерабл
 *          - нужно сначала его вручную преобразовать в лист
 *              - напр. при помощи Arrays.asList()
 *          - но при этом он все равно работает с форичем
 *
 * - при помощи "метода-адаптера" можно добавить другое поведение команды форич
 *      - напр. также листать в обратную сторону
 *      - если просто переопределить iterator(), то поведение просто заменится на другое
 *      - нужен метод, который будет создать интерфейс Iterable с нужным поведением
 *
 * */


/* iterable - простое представление серии элементов, по которым можно пройтись. нет состояния
 * итерации такой как "текущий элемент",а есть единственный метод, который создает итератор
 *
 * iterator - объект с состоянием итерации. позволяет проверять, есть ли элемент и т.д
 * */

/* при написании программ нет никакой необходимости использовать устаревшие классы Vector, Hashtable,
 * stack*/


/* COMPARABLE И COMPARATOR */
@Ntrstn("Фреймворк коллекций - это удобный способ манипулирования определенной кучей объектов")

@Ntrstn("Правильней называть коллекциями, только то, что относится к интерфейсу Collection (т.е. НЕ " +
        "Map), а вот все вместе - контейнерами")
@Ntrstn("Массив обеспечивает самый эффективный способ хранения групп объектов и является первым " +
        "кандидатом при хранении группы примитивов. Однако массив имеет фиксированный размер.")

@Ntrstn("До Java 5 в экземпляры контейнеров можно было добавлять любые типы, но с появлением " +
        "обобщений появились версии классов-контейнеров, использующих ограничение типов")

@Ntrstn("Метод add() в Collection не обязательно добавляет элемент (как в случае дубликата в Set), " +
        "а лишь гарантирует, что такой элемент будет в контейнере")

@Ntrstn("Для добавления групп элементов существуют специальные методы для удобства. Arrays.asList() " +
        "- принимает список элементов через запятую и возвращает контейнер List с ними. Но " +
        "Arrays.asList() создает List, в который нельзя добавлять или удалять элементы в рантайме, " +
        "иначе возникнет UnsupportedOperationException." +
        "Collections.addAll() - добавляет в указанную коллекцию указанные элементы (могут идти как " +
        "через запятую, так и массиве). В отличие от Collections.addAll() метод Collection.addAll() " +
        "может в качестве аргумента получать только другой объект Collection, поэтому он менее удобен")

@Ntrstn("Для печати содержимого контейнера не нужен специальный метод, в отличие от массива")

public class Main {

    public static void main(String[] args) {
        List<Integer> l = Arrays.asList(2, 3, 3);
//        l.add(3); // UnsupportedOperationException

        for (Integer aL : l) {
            System.out.println(aL);
        }

        Random random = new Random(47);
        Set<Integer> integerSet = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            integerSet.add(random.nextInt(30));
        }
        System.out.println(integerSet); // должно быть в беспорядке, но почему-то в порядке

        /**/
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            int r = random.nextInt(20);
            Integer frequency = map.get(r);
            map.put(r, frequency == null ? 1 : frequency + 1);
        }
        System.out.println(map);

        List<String> l2 = new ArrayList<>();
        l2.add("sdf");
        l2.add("sdsdff");
        l2.add("sdaaf");

        Iterator<String> iterator = l2.iterator();
        ListIterator<String> listIterator = l2.listIterator();

        while (iterator.hasNext()) {
            iterator.next();
        }

        while (listIterator.hasPrevious()) {
            listIterator.previous();
        }

        for (Map.Entry entry : System.getenv().entrySet()) { // переменные окружения
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        /*СВОЙ ИТЕРАБЛ*/
        class ReverseArrayList<T> extends ArrayList<T> {
            public ReverseArrayList(Collection<T> c) {
                super(c);
            }

            public Iterable<T> reversed() {
                return new Iterable<T>() {
                    @Override
                    public Iterator<T> iterator() {
                        return new Iterator<T>() {
                            int current = size() - 1;

                            @Override
                            public boolean hasNext() {
                                return current > -1;
                            }

                            @Override
                            public T next() {
                                return get(current--);
                            }
                        };
                    }
                };
            }
        }

        ReverseArrayList<String> ral
                = new ReverseArrayList<>(Arrays.asList("to be or not to be".split(" ")));

        for (String s : ral) { // обычный итератор, вызывается по умолчанию
            System.out.println(s + " ");
        }

        for (String s : ral.reversed()) { // мой итератор
            System.out.println(s + " ");
        }
    }
}