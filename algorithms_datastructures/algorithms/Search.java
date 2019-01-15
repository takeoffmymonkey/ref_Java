package algorithms_datastructures.algorithms;

/* Линейный поиск
 * - сложность: O(n)
 *
 * - алгоритм:
 *      - тупой поочередный проход по каждому элементу */


/* Двоичный поиск
 * - сложность: O(log n)
 *
 * - структура данных должна быть сортирована
 *      - обычно по возрастанию
 *      - напр. java.util.Arrays.sort(...[])
 *
 * - ключевая идея: постоянно резать массив пополам, отбрасывая ту часть, где элементы точно больше
 * ключа
 *
 * - алгоритм:
 *      - определение значение в середине структуры данных
 *          - сравнение его с ключом
 *      - если ключ меньше серединного, продолжить поиск в левой части, если больше, то в правой
 *      - продолжать пока не будет совпадение или не останется интервала
 *
 * - divide and conquer
 *
 * - есть имплементация в JDK: java.util.Arrays.binarySearch(...[], ...) */

public class Search {
}