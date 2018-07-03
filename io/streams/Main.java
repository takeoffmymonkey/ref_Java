package io.streams;

/* ПОТОКИ - ПОСЛЕДОВАТЕЛЬНОСТЬ ДАННЫХ, СЧИТЫВАЕМЫХ ИЗ ИСТОЧНИКА ИЛИ ЗАПИСЫВАЕМЫХ В МЕСТО НАЗНАЧЕНИЯ
 * - напр. файлы на диске, устройства, другие программы, массивы памяти и т.д.
 *
 * - поддерживают разные виды данных:
 *     - простые байты
 *     - примитивы
 *     - локализированные символы (Unicode)
 *     - объекты
 *
 * - некоторые просто передают данные, некоторые удобно ими манипулируют
 *
 * - всегда необходимо закрывать потоко после использования */


/* ПОТОКИ БАЙТОВ
 * - все байтовые потоковые классы наследуют от InputStream и OutputStream, напр:
 *      - FileInputStream: считывает байты (в виде int от 0 до 255) из файла
 *      - FileOutPutStream: записывает байты (в виде int от 0 до 255) в файл
 * */


/* ПОТОКИ СИМВОЛОВ
 * - обычно являются оболочками для байтовых потоков и переводят из байтов в символы
 *
 * - все символьные потоковые классы наследуют от Reader и Writer, напр:
 *      - FileReader:
 *          - считывает символы (в виде int от 0 до 65535) из файла
 *          - оборачивает FileInputStream
 *      - FileWriter: записывает символы (в виде int от 0 до 65535) в файл
 *          - оборачивает FileOutputStream
 *
 * - автоматически переводят из Unicode в/из локального набора символов
 *
 * */

/* InputStreamReader и OutputStreamWriter - 2 моста типа "байт-в-символ" общего назначения -
 * используются для создания символьных потоков, когда нет подходящих символьных классов*/


/* Добавление ф-ла происходит при помощи оборачивания (Декоратора), например:
 * new BufferedReader(new FileReader("xanadu.txt")); // сделать из посимвольного чтения буферное
 * */


/* Для эффективности, необходимо передавать байты/символы не по 1, а блоками
 * - для байтов: BufferedInputStream и BufferedOutputStream
 * - для символов: BufferedReader и BufferedWriter
 *
 * - входной поток считывает данные из буфера в памяти, когда буфер заполнен
 *
 * - выходной поток записывает данные в буфер, ко гда буфер заполнен
 *
 * - иногда необходимо очищать (flush) буфер, прежде чем он заполнится:
 *      - некоторые классы буферизированного вывода поддерживают autoflush
 *          - передается дополнительным аргументом в конструктор
 *      - срабатывает при наступлении определенных событий
 *          - напр. в PrintWriter autoflush срабатывает при каждом вызове println или format
 *      - запустить вручную: flush()
 *          - метод валиден для любого выходного потока
 *              - но только если он буферизирован
 *
 * - например, строками:
 *      - символ возвращения каретки\line-feed sequence: "\r\n"
 *      - символ возвращения каретки: "\r"
 *      - line-feed sequence: "\n" */


/* СКАНИРОВАНИЕ И ФОРМАТИРОВАНИЕ
 * - Scanner API: разделяет вход на отдельные куски, ассоциированные с битами данных, и переводит их
 * в зависимости от их типа данных
 *      - по дефолту использует пробелы (включая табуляции, разделители строк - узнать: Character.
 *      isWhitespace) для разделения на куски
 *      - Scanner
 *          - не является потоком (но внутри он есть?)
 *          - метод close() вызывает автоматически по завершении сканирования объекта
 *          - useDelimiter(): для своего разделителя
 *          - кусками могут быть строки, примитивы (кроме char) и BigInteger и BigDecimal
 *              - у численных значений могут корректно парситься разделитель тысячи (напр "32,767")
 *                  - зависит от локали
 *
 *
 * - Formatting API: собирает данные в форматированный вид
 *      - для потока битов: PrintStream
 *          - System.out и System.err, вероятно, единственные 2 объекта этого типа, с которыми
 *          нужно будет работать
 *      - для потока символов: PrintWriter
 *          - обычно используется этот класс
 *      - print/println: форматирование единичных значений стандартным способом
 *      - format: форматирование почти любого количества значений на основе форматной строки
 *
 * */

/* COMMAND-LINE IO: СТАНДАРТНЫЕ ПОТОКИ И КОНСОЛЬ
 * - стандартный поток ввода (клавиатура): System.in
 *      - объект InputStream
 *
 * - стандартный поток вывода (дисплей): System.out
 *      - объект PrintStream, но внутри использует объект символьного потока
 *
 * - стандартный поток ошибок: System.err
 *      - объект PrintStream, но внутри использует объект символьного потока
 *
 * - Console - более продвинутая версия стандартных потоков
 *      - использует символьные потоки
 *      - подходит, например, для ввода паролей
 *          - метод readPassword()
 *      - получить: System.console() */


/* ПОТОКИ ДАННЫХ - ДЛЯ ДВОИЧНОЙ ПЕРЕДАЧИ ПРИМИТИВОВ И СТРОК
 * - имплементируют DataInput или DataOutput
 * - наиболее используемые имплементации: DataInputStream и DataOutputStream
 * - все имплементации DataInput используют EOFException вместо возвращаемого значения для
 * обозначения конца файла */


/* ПОТОКИ ОБЪЕКТОВ - ДЛЯ ПЕРЕДАЧИ SERIALIZABLE ОБЪЕКТОВ И ПРИМИТИВОВ
 * - имплементируют ObjectInput и ObjectOutput
 *       - наследуют от DataInput и DataOutput
 *           - поэтому работают также с примитивами
 *
 * - наиболее используемые имплементации: ObjectInputStream и ObjectOutputStream
 * - при чтении и последующем приведении к нужному типу возможно ClassNotFoundException
 * - если записываются объекты, которые содержат другие объекты - они записываются все
 *      - объект дважды не записывается - записывается только ссылка на него
 * */


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        byteStreamCopy();
        charStreamCopy();
        charLineStreamCopy();
        textScan();
//        askPasswordViaConsole();
        writeReadData();
        writeReadObjects();
    }


    static void byteStreamCopy() throws IOException {
        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream("src\\io\\streams\\byte_test_src.txt");
            out = new FileOutputStream("src\\io\\streams\\byte_test_dest.txt");

            int c;

            while ((c = in.read()) != -1) {
                out.write(c);
            }

        } finally {
            if (in != null) {
                out.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }


    static void charStreamCopy() throws IOException {
        FileReader in = null;
        FileWriter out = null;

        try {
            in = new FileReader("src\\io\\streams\\char_test_src.txt");
            out = new FileWriter("src\\io\\streams\\char_test_dest.txt");

            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }

        } finally {
            if (in != null) {
                out.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }


    static void charLineStreamCopy() throws IOException {
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new FileReader("src\\io\\streams\\char_line_test_src.txt"));
            out = new PrintWriter(new FileWriter("src\\io\\streams\\char_line_test_dest.txt"));

            String l;
            while ((l = in.readLine()) != null) {
                out.println(l); // может не совпадать с разделителем строки, который был изначально
            }

        } finally {
            if (in != null) {
                out.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }


    static void textScan() throws IOException {
        Scanner s = null;

        try {
            s = new Scanner(new BufferedReader(new FileReader("src\\io\\streams\\scan_test_src.txt")));

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


    static void askPasswordViaConsole() {
        Console c = System.console();
        if (c == null) {
            System.err.println("No console.");
            System.exit(1);
        }

        String login = c.readLine("Enter your login: ");
        char[] oldPassword = c.readPassword("Enter your old password: ");

    }

    static void writeReadData() throws IOException {
        int i = 5;
        double d = 5.5;
        String s = "test";

        DataOutputStream out = null;
        try {
            out = new DataOutputStream(new
                    BufferedOutputStream(new FileOutputStream("src\\io\\streams\\write_data_test.txt")));

            out.writeInt(i);
            out.writeDouble(d);
            out.writeUTF(s);
        } finally {
            out.close();
        }

        DataInputStream in = null;
        double total = 0.0;
        try {
            in = new DataInputStream(new
                    BufferedInputStream(new FileInputStream("src\\io\\streams\\write_data_test.txt")));

            int unit;
            double price;
            String desc;

            try {
                while (true) {
                    unit = in.readInt();
                    price = in.readDouble();
                    desc = in.readUTF();
                    System.out.format("You ordered %d units of %s at $%.2f%n",
                            unit, desc, price);
                    total += unit * price;
                }
            } catch (EOFException e) {
            }
            System.out.format("For a TOTAL of: $%.2f%n", total);
        } finally {
            in.close();
        }
    }


    static void writeReadObjects() throws IOException, ClassNotFoundException {
        BigDecimal bigDecimal = new BigDecimal(23.44);


        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new
                    BufferedOutputStream(new FileOutputStream("src\\io\\streams\\write_obj_test.txt")));

            out.writeObject(bigDecimal);
        } finally {
            out.close();
        }

        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new
                    BufferedInputStream(new FileInputStream("src\\io\\streams\\write_obj_test.txt")));

            BigDecimal bigDecimal1 = null;

            try {
                while (true) {
                    bigDecimal1 = (BigDecimal) in.readObject();
                    System.out.format("Object value = " + bigDecimal1);
                }
            } catch (EOFException e) {
            }
        } finally {
            in.close();
        }
    }
}