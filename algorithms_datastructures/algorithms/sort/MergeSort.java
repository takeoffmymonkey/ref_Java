package algorithms_datastructures.algorithms.sort;

/* MergeSort
 * - ключевая идея: легко слиять 2 сортированных массива!
 *      - поэтому разделим массив рекурсивно попополам до единичных элементов, которые по определению
 *      являются сортированными
 *      - а затем в обратном порядке начнем их слиять с соседями, получая все большие сортированные
 *      куски
 *
 * - алгоритм:
 *      - первая стадия: массив рекурсивно разбивается пополам пока все элементы не останутся по
 *      отдельности
 *          - каждый элемент по сути является "сортированным"
 *      - вторая стадия: во обратном порядке производится сортировка слиянием каждого элемента с
 *      соседним
 *          - элементы одного по очереди сравниваются с элементами второго и наименьший элемент
 *          переходит в сортированный (третий) массив, а оставшийся сравнивается со следующим в
 *          другом массиве. И так, пока в одном из массивов не закончатся элементы, а затем
 *          остальные элементы в другом массиве просто переносятся в конец сортированного
 *              - в итоге остается только (третий) сортированный массив
 *          - так сначала получаются сортированные пары, потом то же самое для пар - получаются
 *          сортированные четверки и т.д.
 *
 * - использует рекурсию
 *
 * - not in place
 *
 * - устойчивый
 *
 * - сложность:
 *      - лучшая: O(log n)
 *      - средняя: O(log n)
 *      - худшая: O(log n) */


import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] a = {1, 4, 6, 2, 10, 3};
        sort(a, 0, a.length - 1);
        System.out.println("MergeSort" + Arrays.toString(a));
    }

    // Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
    static void merge(int arr[], int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];


        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using
    // merge()
    static void sort(int arr[], int l, int r) {
        if (l < r) {
            // Find the middle point
            int m = (l + r) / 2;

            // Sort first and second halves
            sort(arr, l, m);
            sort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }
}