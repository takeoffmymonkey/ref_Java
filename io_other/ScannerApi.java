package io_other;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import _types_references_annotations.my_annotations.Ntrstn;


/* SCANNER API
 * - разделяет вход на отдельные куски, ассоциированные с битами данных, и переводит их в зависимости
 * от их типа данных */


/* JAVA.UTIL.SCANNER
 * - public final class Scanner extends Object implements Iterator<String>, Closeable
 *
 * - сканер текста, который может парсить примитивы и строки при помощи регулярных выражений
 *      - работает с объектами, которые имплементируют Readable
 *      - парсить можно напр. строки или входящие потоки
 *
 * - разбивает ввод на токены в соответствии с указанным разделительным паттерном
 *      - токенами могут быть строки, примитивы (кроме char) и BigInteger и BigDecimal
 *      - по дефолту матчит пробелы (включая табуляции)
 *          - узнать, является ли символ разделителем: Character.isWhitespace
 *          - useDelimiter(): для своего разделителя
 *      - по дефолту числа парсит как десятичные
 *          - у численных значений могут корректно парситься разделитель тысячи (напр "32,767")
 *                  - зависит от локали
 *      - токены можно конвертировать в значения разных типов при помощи методов next...
 *
 * - метод close() вызывается автоматически по завершении сканирования объекта
 *      - при закрытии также закрывает ресурс, если тот Closeable
 *
 * - методы:
 *      - reset(): сбросить разделить к дефолтному
 *      - next() и примитивные варианты: возвращают строку или соответствующий примитив
 *      - hasNext() и примитивные варианты: смотрит, какой примитив идет дальше
 *      - findInLine(java.lang.String), findWithinHorizon(java.lang.String, int),
 *      skip(java.util.regex.Pattern): пытаются заматчить игнорируя разделитель */


@Ntrstn("Scanner API (класс java.util.Scanner) предоставляет парсер объектов, которые являются " +
        "Readable (например, строки или входящие потоки) и разбивать его на куски-токены (могут " +
        "быть строками, примитивами (кроме char) и BigInteger и BigDecimal) по указанному " +
        "разделительному паттерну (регулярное выражение; по дефолту простро пробел)")

@Ntrstn("Продвигаться по объекту нужно в цикле при помощи методов hasNext/next и получать нужные " +
        "типы соответствующими вариантами этих методов, например hasNextInt/nextInt")

@Ntrstn("по завершении сканирования объекта метод close() вызывается автоматически, и при закрытии " +
        "также закрывает ресурс, если тот Closeable")

public class ScannerApi {

    static File currentFolder = new File("C:\\git\\ref_Java\\src\\io_other\\files\\");
    static File tempFile = new File(currentFolder, "scanner_test.txt");

    public static void main(String[] args) throws IOException {
        scanString();
        scanStringWithRegex();
        scanFileForIntsAndPrintSum();
    }

    private static void scanString() {
        String s = "Строка, которую нужно разбить на слова";
        Scanner scanner = new Scanner(s);
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
    }

    private static void scanStringWithRegex() {
        String s = "нужно 4 прочитать 8 все 3 слова 2 в 6 данной 1 строке 2";
        Scanner scanner = new Scanner(s).useDelimiter("\\d");
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
    }

    private static void scanFileForIntsAndPrintSum() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileReader(tempFile))) {
            int sum = 0;
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) sum += scanner.nextInt();
                else scanner.next();
            }
            System.out.println("Сумма равна = " + sum);
        }
    }
}