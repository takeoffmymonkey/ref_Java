package _generics;

/* ВЫВЕДЕНИЕ ТИПА
 * - определение компилятором типов аргументов, которые сможет принять метод, основываясь на его
 * объявленных параметрах
 *      - а также, если возможно, возвращаемый тип
 *
 * - алгоритм использует аргументы вызова, целевые типы и возможно return типы для выведения
 *      - todo но не использует их в программе
 *      - целевой тип - тип, который ожидает компилятор, в зависимости от того, где используется
 *      выражение
 *
 * */

/*
* - выведение типа - способность компилятора смотреть на каждый вызов метода и соответствующее объявление для определения аргумента(-ов) типа, которые могут делать вызов возможным

- алгоритм выведения определяет типы аргументов и, если возможно, тип, который будет возвращен

- алгоритм пытается найти наиболее точный тип, который работает со всеми аргументами:
   static <T> T pick(T a1, T a2) { return a2; }
   ...
   Serializable s = pick("d", new ArrayList<String>());
   //алгоритм определяет, что второй передаваемый аргумент методу pick() является типом Serializable

- можно не указывать аргумент параметра при вызове обобщенного метода:
   public static <U> void addBox(U u, java.util.List<Box<U>> boxes) {}
   ...
   BoxDemo.<Integer>addBox(Integer.valueOf(10), listOfIntegerBoxes);//можно без <Integer> (он называется type witness)
   BoxDemo.addBox(Integer.valueOf(30), listOfIntegerBoxes);

- можно не указывать аргумент параметра для вызова конструктора при создании объекта:
   Map<String, List<String>> myMap = new HashMap<String, List<String>>();//можно без String, List<String>
   Map<String, List<String>> myMap = new HashMap<>();
   Map<String, List<String>> myMap = new HashMap(); // unchecked conversion warning

- алгоритм использует только аргументы вызова, целевые типы и возможно очевидные ожидаемые (return) типы для выведения, но не использует их в программе

- целевой тип выражения - тип данных, который ожидает компилятор, в зависимости от того, где используется выражение:
   static <T> List<T> emptyList();//из Collections
   ...
   List<String> listOne = Collections.emptyList();//выражение ожидает List<String> - этот тип и есть целевой. Компилятор понимает, что Т будет String
   List<String> listOne = Collections.<String>emptyList();//в данном контексте можно без type witness

- НО в J7 (в J8 уже нет) иногда надо указывать type witness:
   void processStringList(List<String> stringList) {}
   ...
   processStringList(Collections.emptyList());//ошибка в J7: List<Object> cannot be converted to List<String>; в J8 компилятор правильно выводит целевой тип
   processStringList(Collections.<String>emptyList());
* */



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TypeInference_TypeWitness {
    static <T> T pick(T a1, T a2) {
        return a2;
    }

    public static void main(String[] args) {
        Serializable s = pick("d", new ArrayList<String>()); // второй аргумент определен как Serializable

        List <ArrayList<Integer>> list = new ArrayList<>();
        addBox(10, list);

    }


    public static <U> void addBox(U u, java.util.List<ArrayList<U>> boxes) {

    }
}