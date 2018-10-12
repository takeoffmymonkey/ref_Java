package algorithms_datastructures;

/* Обязательно требуется break condition, чтобы не было stackoverflow
 *      - но даже с ним при высоком значении может быть stackoverflow
 * каждый вызов хранится в стек(LIFO)-фрейме
 *
 * */

public class Recursion {
    public static void main(String[] args) {

        System.out.println(factorialIterative(5));
        System.out.println(factorialRecursive(5));


    }


    // factorial(n) = n*(n-1)*(n-2)*...1
    private static int factorialIterative(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // factorial(n) = n*factorial(n-1)
    private static int factorialRecursive(int n) {
        if (n == 0) return 1;
        return n * factorialRecursive(n - 1);
    }
}
