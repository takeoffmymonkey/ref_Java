package io_other;

/* СКАНИРОВАНИЕ
 * - Scanner API: разделяет вход на отдельные куски, ассоциированные с битами данных, и переводит их
 * в зависимости от их типа данных
 *      - по дефолту использует пробелы (включая табуляции, разделители строк - узнать: Character.
 *      isWhitespace) для разделения на куски
 *      - java.util.Scanner
 *          - не является потоком (но внутри он есть?)
 *          - метод close() вызывает автоматически по завершении сканирования объекта
 *          - useDelimiter(): для своего разделителя
 *          - кусками могут быть строки, примитивы (кроме char) и BigInteger и BigDecimal
 *              - у численных значений могут корректно парситься разделитель тысячи (напр "32,767")
 *                  - зависит от локали
 *          - добавлен в J5
 *          - работает только на чтение
 *          - не относится к java.io
 *          - предназначен в основном для создания обработчиков кода или "мини-языков"
 * */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ScannerApi {


    public static void main(String[] args) throws IOException {
        textScan();
    }

    static void textScan() throws IOException {
        Scanner s = null;

        try {
            s = new Scanner(new BufferedReader(new FileReader("src\\io_io_and_streams\\scan_test_src.txt")));

            int sum = 0;

            while (s.hasNext()) { // метод close() вызывает автоматически по завершении сканирования
                if (s.hasNextInt()) sum += s.nextInt();
                else System.out.println(s.next());
            }
            System.out.println("sum = " + sum);

        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}
