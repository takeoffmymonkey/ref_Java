package __algorithms_datastructures.algorithms.sort;

/* SelectionSort
 * - ключевая идея: с каждой итерацией находить наименьший элемент и переносить его в конец
 * сорированной части, исключая его из дальнейшего прохода
 *
 * - алгоритм:
 *      - находим номер минимального значения в текущем списке
 *      - производим обмен этого значения со значением первой неотсортированной позиции
 *          - обмен не нужен, если минимальный элемент уже находится на данной позиции
 *      - теперь сортируем хвост списка, исключив из рассмотрения уже отсортированные элементы
 *
 * - обычно лучше BubbleSort
 *
 * - сложность:
 *      - лучшая: O(n^2)
 *      - средняя: O(n^2)
 *      - худшая: O(n^2) */


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