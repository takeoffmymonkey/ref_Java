package __collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Tests {
    public static void main(String[] args) {
        printShuffle(new String[]{"1", "2", "3"});
        findDuplicatesIgnoringCaseByComparator(new String[]{"i", "do", "I"});
        trimmingWithIterator(Arrays.asList("1   ", "2 ", " 3 "));
    }

    static void printShuffle(String[] args) {
        List<String> l = Arrays.asList(args);
        Collections.shuffle(l);

        for (String s : l) {
            System.out.println(s);
        }

        l.stream().forEach(s -> System.out.println(s));
    }

    static void findDuplicatesIgnoringCaseByComparator(String[] args) {
//        SortedSet<String> s = new TreeSet<>(new MyCmprtr());
        SortedSet<String> s = new TreeSet<>(String::compareToIgnoreCase);
        for (String a : args)
            s.add(a);
        System.out.println(s.size() + " distinct words: " + s);
    }

    static void trimmingWithIterator(List<String> l) {
        for (ListIterator<String> li = l.listIterator(); li.hasNext(); ) {
            li.set(li.next().trim());
        }
        System.out.println(l);
    }
}

class MyCmprtr implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return o1.compareToIgnoreCase(o2);
    }
}