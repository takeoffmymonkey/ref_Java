package algorithms_datastructures.algorithms.sort;

/* Берется пара, сравнивается, меняется местами если надо, так самое большое значение поднимается в
 * конец
 *
 * сложность: n^2
 *
 * - очень не эффективный и просто рассматривается из-за простоты
 * */


import java.util.Arrays;

public class BubbleSort {

    public static void main(String[] args) {
        int[] a = {1, 4, 6, 2, 10, 3};
        sort(a);
        System.out.println("BubbleSort" + Arrays.toString(a));
    }

    private static void sort(int[] a) {
        //проходов на 1 меньше элементов
        for (int i = 0; i < a.length - 1; i++) {
            // сравниваем пары, начиная с
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int t = a[j + 1];
                    a[j + 1] = a[j];
                    a[j] = t;
                }
            }
        }
    }
}