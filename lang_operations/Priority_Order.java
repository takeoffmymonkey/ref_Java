package lang_operations;

/*ПРИОРИТЕТ ОПЕРАЦИЙ
 * Слева направо () [] .
 * Справа налево ! ~ ++ -- +(унарный) -(унарный) ()(приведение) new
 * Слева направо * / %
 * Слева направо + -
 * Слева направо >> >>> <<
 * Слева направо > >= < <=
 * Слева направо == !=
 * Слева направо &
 * Слева направо ^
 * Слева направо |
 * Слева направо &&
 * Слева направо ||
 * Справа налево ?:
 * ->
 * Справа налево = += -= *= /= %= >>= <<= &= ^= |=
 * Слева направо ,*/

public class Priority_Order {

    public static void main(String[] args) {
        /*ПРИ ОДИНАКОВОМ ПРИОРИТЕТЕ, ВЫЧИСЛЯЕТСЯ СЛЕВА НАПРАВО*/
        int a = 3 + 4 + 2; // будет вычислено как (3 + 4) + 2


        /*ИСКЛЮЧЕНИЕ: ОПЕРАЦИЯ ПРИСВАИВАНИЯ - СПРАВА НАЛЕВО*/
        int b, c, d = 0;
        b = c = d = 1; // будет вычислено как b = (c = (d = 1))


        /*ОПЕРАНДЫ ПОЛНОСТЬЮ ВЫЧИСЛЯЮТСЯ ПЕРЕД ИСПОЛНЕНИЕМ ОПЕРАЦИИ*/
        System.out.println(3 + 4 + 5); // сначала вычисляется сумма, потом идет операция печати


        /*БЫСТРОДЕЙСТВИЕ НЕ ЗАВИСИТ ОТ КОЛИЧЕСТВА СКОБОК*/
        int e = ((((((12 + 21)))))); // выполнится также быстро как 12 + 21


        /* В КОМБИНИРОВАННОЙ ОПЕРАЦИИ ПРИСВОЕНИЯ ЗНАЧЕНИЕ ЛЕВОЙ ЧАСТИ СОХРАНЯЕТСЯ ДЛЯ ИСПОЛНЕНИЯ В
         * ПРАВОЙ ЧАСТИ*/
        int f = 5;
        f += 10; // 5 переходит в левую часть, там участвует в операции 5 + 10, f остается не тронутой
    }
}
