package lang_operations;

public class Main {
    static int a = 0;

    public static void main(String[] args) {
        /*ОПЕРАНД
         * Объект, над которым выполняется операция*/
        // a + 5 // a - операнд, 5 - операнд


        /*ЗНАК ОПЕРАЦИИ
         * - предопределённый символ или группа символов, предписывающие выполнить некоторую операцию*/
        // a + 5 // + это знак операции


        /*ОПЕРАЦИЯ (operator)
         * - действие над операндами
         * - заканчивается на ;*/
        // a++; // унарная операция
        // a + 5 // бинарная операция
        // a > 5 ? a + 5 : a - 5 // тернарная операция


        /*ВЫРАЖЕНИЕ
         * - последовательность операндов и знаков операций
         * - или запись в программе, вычисленное значение которой характеризуется типом данных
         * // ((x < 10) && ( x > 5)) || ((x > 20) && (x < 25));


        /*ОПЕРАТОР АКА ИНСТРУКЦИЯ (statement)
         * - завершенный модуль исполнения (объект, выполняющий операцию)
         * - в них выражается действия программы
         * - напр:
         *   + объявление переменных
         *   + присваивания значений
         *   + вызов метода
         *   + проход по циклу
         *   + проход по ветке с блоком кода*/
        int a; // оператор присваивания
        { // Statement блок
            a = 5; // присваивание значения
            Integer.toString(a); // вызов метода
        }
    }
}
