package lambda_functional_interface;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import types_references_annotations.my_annotations.MakeUse;
import types_references_annotations.my_annotations.Ntrstn;

/* (НЕКОТОРЫЕ) ПРЕДОПРЕДЕЛЕННЫЕ ИНТЕРФЕЙСЫ java.util.function
 * - Predicate<T> - определяет, удовлетворяет ли объект типа Т некоторому ограничительному условию.
 * Возвращает значение, обозначающее результат. Содержит метод test().
 *
 * - Consumer<T> - выполняет операцию над объектом типа Т. Содержит метод accept().
 *
 * - Supplier<T> - возвращает объект типа Т. Содержит метод get().
 *
 * - Function<T , R> - выполняет операцию над объектом типа Т и возвращает в результате объект типа
 * R. Содержит метод apply().
 *
 * - UnaryOperator<T> - выполняет унарную операцию над объектом типа Т и возвращает результат того
 * же типа. Содержит метод apply(). */


/* У НЕКОТОРЫХ ИНТЕРФЕЙСОВ ЕСТЬ АНАЛОГИЧНЫЕ ИНТЕРФЕЙСЫ ДЛЯ ДВУХ ПАРАМЕТРОВ
 * - начинаются с Bi...
 *      - напр. BiConsumer - принимает 2 параметра, ничего не возвращает*/


/* НЕКОТОРЫЕ ИНТЕРФЕЙСЫ ЯВЛЯЮТСЯ ТЕРМИНАЛЬНЫМИ
 * - т.е. на них обрывается операция
 *      - т.е. они ничего возвращают (void)
 *      - т.е. должны быть последними в очереди
 *      - напр. Сonsumer*/


/* АГРЕГАТНЫЕ ОПЕРАЦИИ
 * - обработка элементов потока, а не напрямую (из коллекции)
 *      - в данном случае операции: filter, map, forEach
 *      - ПОТОК - последовательность элементов, а не структура данных
 *          - несет значения ресурса (напр. коллекции) через канал
 *              - КАНАЛ - последовательность операций потока:
 *                  - напр. filter-map-forEach
 *
 * - часто принимают в качестве параметра Л-В*/

@MakeUse("Функциональный интерфейс Predicate: проверяет указанное условие, возвращает true при " +
        "совпадении")
@MakeUse("Функциональный интерфейс Consumer: выполняет указанное действие и ничего не возвращает")
@MakeUse("Функциональный интерфейс Supplier: ничего не принимает, возвращает указанное действие")
@MakeUse("Функциональный интерфейс Function: принимает объект любого типа и возвращает любого типа")
@Ntrstn("Обрабатывать коллекцию можно потоком, без использования функциональных интерфейсов напрямую")
@Ntrstn("Функциональный интерфейс, используемый в Л-В через Stream, не нуждается в импорте")
public class Java_Util_Function {
    int localVar;

    public static void main(String[] args) {
        /*~~~~~~~~~~~~~ОБРАБОТКА ЭЛЕМЕНТОВ КОЛЛЕКЦИИ~~~~~~~~~~~~~*/
        /* СОЗДАНИЕ КОЛЛЕКЦИИ */
        List<Integer> intList = Arrays.asList(34, 564, 54, 45, 77, 99);

        /* ОБРАБОТКА КОЛЛЕКЦИИ ПРИ ПОМОЩИ СВОЕГО МЕТОДА */
        processIntList(intList, // задаю список
                integer -> integer > 50 && integer < 80, // задаю условие отбора (Predicate)
                integer -> integer + 100, // задаю условие операции (UnaryOperation)
                integer -> "number: " + integer.toString(), // задаю условие метода (Function)
                System.out::println); // задаю финальное действие над прошедшими условие (Consumer)

        /* ТО ЖЕ САМОЕ, НО ПРИ ПОМОЩИ ОБОБЩЕННОЙ ВЕРСИИ ТОГО ЖЕ МЕТОДА */
        processList(intList,
                integer -> integer > 50 && integer < 80,
                integer -> integer + 100,
                integer -> "number: " + integer.toString(),
                System.out::println);

        /* ТО ЖЕ САМОЕ, НО ПРИ ПОМОЩИ АГРЕГАТНОЙ ОПЕРАЦИИ */
        intList
                // получить исходные объекты
                // операция: Stream<E> stream()
                .stream()
                // отфильтровать объекты, которые подходят под объект Predicate
                // операция: Stream<T> filter(Predicate<? super T> predicate)
                .filter(integer -> integer > 50 && integer < 80)
                // cвязать объекты с другим значением, как указано в объекте Function
                // операция: <R> Stream<R> map(Function<? super T,? extends R> mapper)
                .map(integer -> integer + 100)
                .map(integer -> "number: " + integer.toString())
                // выполнить действие, как указано в объекте Consumer
                // терминальная операция: void forEach(Consumer<? super T> action)
                .forEach(System.out::println);


        /*~~~~~~~~~~~~ТЕСТИРОВАНИЕ SUPPLIER~~~~~~~~~~~~*/
        Stream.generate(() -> 5).
                limit(20). // обязательно задать лимит, чтобы не было stackoverflow
                map(i -> i + 5).
                forEach(System.out::println);
    }

    /*МЕТОД ДЛЯ ОБРАБОТКИ СПИСКА ЧИСЕЛ*/
    public static void processIntList(List<Integer> intList,
                                      Predicate<Integer> tester,
                                      UnaryOperator<Integer> unaryOperator,
                                      Function<Integer, String> operation,
                                      Consumer<String> print) {
        for (Integer integer : intList) {
            if (tester.test(integer)) { // Predicate - тестирование условия
                {
                    integer = unaryOperator.apply(integer); // UnaryOperator - выполние операции
                    String intAsString = operation.apply(integer); // Function - выполнение метода
                    print.accept(intAsString); // Consumer - конечная операция над объектом
                }
            }
        }
    }

    /*ТОТ ЖЕ МЕТОД, НО ОБОБЩЕННЫЙ*/
    public static <X, Y> void processList(Iterable<X> source,
                                          Predicate<X> tester,
                                          UnaryOperator<X> unaryOperator,
                                          Function<X, Y> mapper,
                                          Consumer<Y> block) {
        for (X s : source) {
            if (tester.test(s)) {
                {
                    s = unaryOperator.apply(s);
                    Y data = mapper.apply(s);
                    block.accept(data);
                }
            }
        }
    }
}