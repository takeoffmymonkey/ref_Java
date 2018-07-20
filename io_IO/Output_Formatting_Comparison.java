package io_IO;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import types_references_annotations.my_annotations.Ntrstn;

/* FORMATTING API
 * - переводит данные в форматированный вид
 *
 * - для потока битов: PrintStream
 *      - обычно используется только при работе с System.out и System.err, которые им являются
 *
 * - для потока символов: PrintWriter
 *      - обычно используется этот класс
 *
 * - методы print/println: форматирование единичных значений стандартным способом
 *
 * - format: форматирование почти любого количества значений на основе форматной строки */


/* JAVA.IO.PRINTSTREAM
 * - public class PrintStream extends FilterOutputStream implements Closeable, Flushable, Appendable,
 * AutoCloseable
 *
 * - добавляет к исходящему потоку удобную возможность печатать разные данные
 *
 * - используется в ситуациях, когда нужно записывать символы, а не байты
 *
 * - в отличие от других исходящий потоков, никогда не выбрасывает IOException
 *      - вместо этого исключительные ситуации устанавливают внутренний флаг, который можно
 *      посмотреть методом checkError
 *
 * - опционально может создаваться с автоматическим сливом
 *      - т.е. метод flush автоматически вызывается после того, как записан массив байтов, или
 *      вызван метод println или записан символ (или байт) новой строки ('\n')
 *
 * - все символы, которые печатаются PrintStream, переводятся в байты, используя дефолтную кодировку
 * платформы
 *
 * - методы:
 *      - print, перегруженный разными типами
 *      - println - то же, но с добавлением символа перевода строки в конце
 *      - методы проверки и очистки ошибки
 *      - методы форматирования format и printf */


/* JAVA.IO.PRINTWRITER
 * - public class PrintWriter extends Writer implements Closeable, Flushable, Appendable, AutoCloseable
 *
 * - печатает форматированное представление объектов в ТЕКСТОВЫЙ исходящий поток
 *
 * - имплементирует те же методы печати, что PrintStream
 *       - но не содержит методов записи байтов
 *
 * - в отличие от PrintStream, если включен автоматический слив, он будет происходить только при
 * вызове одного из методов печати println, printf, или format, а не при встрече символа новой
 * строки
 *      - данные методы используют платформенное представление символа новой строки, а не символ
 *      новой строки */


/* СРАВНЕНИЕ ФОРМАТОВ ЗАПИСИ
 * - все пишется как байты
 *      - если класс позволяет писать символы или другие типы, то перед записью они автоматически
 *      переводятся в байты с учетом текущей кодировки системы
 *      - записанные байты в файле отображаются с учетом кодировки текстового редактора
 *          - метод writeUTF использует особую кодировку и нарушает нормальное отображение
 *
 * - OutputStream: записывает методом write, который принимает только байты (как int)
 *
 * - Writer: записывает методом write, который принимает байты (как int) либо символы (и их
 * последовательности)
 *
 * - PrintStream: записывает методами: write, который принимает только байты (как int) и print (и его
 * вариациями типа println), который принимает кучу разных типов Java
 *
 * - PrintWriter: записывает методами: write, который принимает байты (как int) либо символы (и их
 * последовательности) и print (и его вариациями типа println), который принимает кучу разных типов
 * Java
 *
 * - RandomAccessFile и DataOutputStream (для возможности прочитать примитивы обратно):
 *      - записывают методом write, который принимает только байты (как int)
 *      - записывают методом write..., который принимает примитивы
 *      - записывают методом writeUTF, который принимает последовательности символов
 *          - но они портят всю кодировку */


@Ntrstn("API для форматирования позволяет отправлять в исходящий поток данные в отформатированном " +
        "виде. Он представлен 2 классами: PrintStream (для байтов) и PrintWriter (для символов), а " +
        "для форматирования используются методы print/println (форматирование единичных значений " +
        "стандартным способом) и format (форматирование почти любого количества значений на основе " +
        "форматной строки)")

@Ntrstn("Класс PrintStream добавляет к исходящему потоку удобную возможность печатать разные типы " +
        "данных и используется в ситуациях, когда нужно записывать символы, а не байты. Все символы, " +
        "которые печатаются PrintStream, переводятся в байты, используя дефолтную кодировку платформы. " +
        "Обычно данный класс используется только при работе с System.out и System.err, которые им " +
        "являются")

@Ntrstn("Класс PrintWriter печатает форматированное представление объектов в ТЕКСТОВЫЙ исходящий " +
        "поток. Он имплементирует те же методы печати, что PrintStream, но не содержит методов " +
        "записи байтов. В отличие от PrintStream, если включен автоматический слив (т.е. отправка), " +
        "он будет происходить только при вызове одного из методов печати println, printf, или format, " +
        "а не при встрече символа новой строки")

@Ntrstn("Все пишется как байты. Если класс позволяет писать символы или другие типы, то перед " +
        "записью они автоматически переводятся в байты с учетом текущей кодировки системы. Записанные " +
        "байты в файле отображаются с учетом кодировки текстового редактора. Метод writeUTF " +
        "использует особую кодировку и нарушает нормальное отображение. " +
        "" +
        "Сравнение форматов записи разными классами:" +
        "1 - OutputStream: записывает методом write, который принимает только байты (как int)" +
        "2 - Writer: записывает методом write, который принимает байты (как int) либо символы (и их " +
        "последовательности)" +
        "3 - PrintStream: записывает методами: write, который принимает только байты (как int) и " +
        "print (и его вариациями типа println), который принимает кучу разных типов Java" +
        "4 - PrintWriter: записывает методами: write, который принимает байты (как int) либо символы " +
        "(и их последовательности) и print (и его вариациями типа println), который принимает кучу " +
        "разных типов Java" +
        "5 - RandomAccessFile и DataOutputStream (для возможности прочитать примитивы обратно): " +
        "записывают методом write, который принимает только байты (как int), записывают методом " +
        "write..., который принимает примитивы, записывают методом writeUTF, который принимает " +
        "последовательности символов, но они портят всю кодировку")

public class Output_Formatting_Comparison {

    static File currentFolder = new File("C:\\git\\ref_Java\\src\\io_IO\\files\\");
    static File tempFile = new File(currentFolder, "print_stream_test.txt");
    static File tempFile2 = new File(currentFolder, "output_formatting.txt");

    public static void main(String[] args) throws IOException {
        printBytesToFile();
        printBytesToFileFormatting();
        compareWritingFormats();
    }


    private static void printBytesToFile() throws FileNotFoundException {
        try (PrintStream out = new PrintStream(tempFile)) {
            String s = "String";
            out.print(s);
            double d = 4.3;
            out.print(d);
        }
    }

    private static void printBytesToFileFormatting() throws FileNotFoundException {
        try (PrintStream out = new PrintStream(tempFile)) {
            String stringFormat = "%s %d\n";
            String stringArg = "мне уже";
            int intArg = 33;
            out.printf(stringFormat, stringArg, intArg);
        }
    }

    private static void compareWritingFormats() throws IOException {
        byte b = 123;
        int i = 44;
        String s = "test";

//        try (FileOutputStream out = new FileOutputStream(tempFile2)) {
//            out.write(b);
//            out.write(i);
//            // результат: {,
//        }

//        try (FileWriter out = new FileWriter(tempFile2)) {
//            out.write(b);
//            out.write(i);
//            out.write(s);
//            // результат: {,test
//        }

//        try (PrintStream out = new PrintStream(tempFile2)) {
//            out.write(b);
//            out.write(i);
//            out.print(b);
//            out.print(i);
//            out.print(s);
//            // результат: {,12344test
//        }

//        try (PrintWriter out = new PrintWriter(tempFile2)) {
//            out.write(b);
//            out.write(i);
//            out.write(s);
//            out.print(b);
//            out.print(i);
//            out.print(s);
//            // результат: {,test12344test
//        }

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(tempFile2))) {
            out.writeByte(b);
            out.writeByte(i);
            out.write(b);
            out.write(i);
//            out.writeUTF(s); // портит кодировку
            // результат: {,{,
            // результат c writeUTF: 7b2c 7b2c 0004 7465 7374
        }
    }
}
