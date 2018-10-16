package __collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import types_references_annotations.my_annotations.Ntrstn;
























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