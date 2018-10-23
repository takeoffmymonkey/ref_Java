package __aggregate_ops;

/* Pipeline (труба)
 * - последовательность агрегатных операций
 *
 *
 * - состоит из:
 *      - источника: коллекция, массив, generator function или канал IO
 *      - 0 или более промежуточных операций
 *          - могут производить потоки:
 *              - последовательность элементов
 *              - не хранит элементы, как коллекция, т.е. не является структурой данных
 *              - несет значения из источника по трубе
 *      - терминальной операции:
 *          - может производить какое-то значение, но точно не поток
 * */

/* РАзница между ао и итераторами
 * - используют внутренную итарацию
 *      - т.е. внутреннюю делегацию - т.е. только указывается коллекция:
 *          - JDK сам решает как итерировать по коллекции
 *              - т.е. м. это делать параллельно, а не обязательно последовательно, как при
 *              внешней делегацию
 *
 * - обрабатывают элементы из потока, а не напрямую из коллекции
 *      - следовательно также являются потоковыми операциями
 *
 * - поддерживают поведение в качестве параметра
 *      - можно указать лв как параметр для большинства ао
 *          - позволяет кастомизировать поведение для конкретной ао
 * */

/* РЕДУКЦИЯ
 * double average = roster
    .stream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .mapToInt(Person::getAge)
    .average()
    .getAsDouble();
 *
 * - терминальная операция, которая возвращают 1 значение или коллекцию при помощи сочетания
 * содержимого потока
 *      - напр average, sum, min, max, count
 * - каждая выполняет особую функцию, но есть и общего назначения
 *      - reduce
 *      Integer totalAge = roster
    .stream()
    .mapToInt(Person::getAge)
    .sum();

    Integer totalAgeReduce = roster
   .stream()
   .map(Person::getAge)
   .reduce(
       0,
       (a, b) -> a + b);

 *      - collect
 * */

/* ПАРАЛЛЕЛИЗМ
 * - в fork/join framework нужно указывать как делить операции и собирать, а в ао это делается
 * автоматически
 * - в Collections framework есть синхронизационный wrapper, который делает любую коллекцию
 * thread-safe, если ее не менять
 * - double average = roster
    .parallelStream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .mapToInt(Person::getAge)
    .average()
    .getAsDouble();
 * */


import java.util.ArrayList;
import java.util.List;

public class
AggregateOps {

    public static void main(String[] args) {
        List<Person> roster = new ArrayList<>();

        // пайплайн
        roster
                // создание нового потока
                .stream()
                // промежуточная операция, создает новый поток на основе своего предиката
                // который возвращает boolean
                .filter(e -> e.getGender() == Person.Sex.MALE)
                // терминальная операция
                .forEach(e -> System.out.println(e.getName()));
    }
}


class Person {
    String name;
    Sex gender;
    String emailAddress;

    public int getAge() {
        return 0;
    }

    public String getName() {
        return null;
    }

    public Sex getGender() {
        return gender;
    }

    public enum Sex {
        MALE, FEMALE
    }

}