package algorithms_datastructures.algorithms.sort;

/*сложность: n^2
 *
 * проход по элементам в поиске самого меньшего элемента, он помещается в первую ячейку, а тот, что
 * был на его месте - в ту, где был самый меньший. теперь первая ячейка сортирована.
 * теперь начиная со второй то же самое и до конца.
 *
 * - лучше BubbleSort
 * - время работы не зависит от сортированности элементов
 * */

import java.util.Arrays;

public class SelectionSort {

    public static void main(String[] args) {
        int[] a = {1, 4, 6, 2, 10, 3};
        sort(a);
        System.out.println("SelectionSort" + Arrays.toString(a));
    }

    private static void sort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[minIndex]) minIndex = j;
            }
            int t = a[minIndex];
            a[minIndex] = a[i];
            a[i] = t;
        }
    }
}