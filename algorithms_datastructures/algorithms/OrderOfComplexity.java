package algorithms_datastructures.algorithms;

/* Анализ алгоритма
 * - space complexity - сколько места занимает структура данных
 * - time complexity - сколько времени уходит на выполнение алгоритма
 *      - зависит от порядка роста при росте кол-ва элементов:
 *          - линейный рост:
 *              - 100 шт. - 10 мс
 *              - 1000 шт. - 100 мс
 *              - 10000 шт. - 1000 мс
 *          - квадратичный рост:
 *              - 100 шт. - 10 мс
 *              - 1000 шт. - 1000 мс
 *              - 10000 шт. - 100000 мс
 *      - обычно учитывается worst case scenario, т.е напр при сортировке баблом, массив изначально
 *      сортирован в обратную сторону, а при поиске элемент отстутсвует
 *          - в некоторых случаях а может расти при worst case scenario, но усреднено работать
 *          константно, поэтому указывается усредненность и случаи роста до worst case scenario
 *      - считается, что мы не ограничены в памяти
 *      - каждая операция (+, -, *, /, =, <, >) занимает 1 единицу времени
 *      - каждая операция доступа к памяти занимает 1 единицу времени
 *      - считается, что мы считываем с RAM
 *      - точность времени можно проигнорировать, т.к. она плавает в разных условиях
 * */


/* ПОРЯДОК РОСТА
 * - описывает, как сложность а растет с увеличением размера входных данных
 *
 * - О-нотация ("Ordnung" - порядок): O(f(x)), где f(x) — формула, выражающая сложность алгоритма
 *      - в формуле может присутствовать переменная n, представляющая размер входных данных
 *
 * - от лучшего к худшему: const <= logn <= rootn <= n <= nlogn <= n^2 <= n^3 <= 2^n <= n!
 *
 * - константный: О(1): t(n) = 17
 *      - отличный!
 *      - сложность не зависит от размера входных данных
 *      - напр. доступ к хеш таблице
 *
 * - логарифмический: O( log n): t(n) = 3 log n
 *      - хороший
 *      - увеличивается на константу - при увеличении n, сложность увеличивается на константу
 *      меньше количества n
 *      - по дефолту логарифм по основанию 2
 *      - напр. binary search tree сортированной таблицы
 *
 * - подлинейный: O(<n)
 *      - хороший
 *      - лучше линейного, но хуже логарифмического
 *      - напр. поиск при помощи параллельного выполнения
 *
 * - линейный: O(n): t(n) = 20n - 4
 *      - неплохой
 *      - сложность растет пропорционально с n - если n удваивается - сложность тоже
 *      - например, посчитать сумму элементов массива
 *
 * - Линеарифметический: O(n * log n): t(n) = 12n log n + 100n
 *      - плохой
 *      - сложность растет кратно константе
 *      - напр. quick sort, merge sort
 *
 * - Квадратичный: O(n^2): t(n) = 3n^2 + 5n - 2
 *      - ужасный
 *      - сложность растет квадратично при увеличении данных
 *      - напр. bubble sort
 *          - т.е. луп в лупе
 *
 * - кубический: O(n^3): t(n) = 8n^3 + 3n^2
 *      - ужасный
 *
 * - экспоненциальный: O(c^n): t(n) = 2^n + 18n^2 + 3n
 *      - ужасный
 *      - растет на основе экспоненты n константы c
 *      - напр. travelling salesman при помощи динамического программирования
 *
 * - факториальный - O(n!)
 *      - ужасный
 *      - растет пропорционально произведению всех чисел
 *      - напр. travelling salesman при помощи brute force
 * */


public class OrderOfComplexity {

    /* Константный O(1) */
    public int GetCount(int[] items) {
        return items.length;
    }


    /*линейный O(n)*/
    public long GetSum(int[] items) {
        long sum = 0;

        for (int item : items) {
            sum += item;
        }

        return sum;
    }

    /*логарифмический: O( log n)*/


}
