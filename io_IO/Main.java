package io_IO;

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
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


/* ИЕРАРХИЯ ПАКЕТА JAVA.IO
 * - File: путь к файлу или папке
 *
 * - RandomAccessFile: неупорядоченное RW в файл
 *
 * - Console: методы доступа к символьному консольному устройству, если такое есть
 *
 * - FilePermission: доступ к файлу или папке
 *      - extends java.security.Permission
 *
 * - SerializablePermission: сериализируемые разрешения доступа
 *      - extends java.security.BasicPermission
 *
 * - (FileDescriptor): открытый файл, сокет и др. источник/цель
 *      - не создается напрямую
 *      - содержится в FileInputStream/FileOutputStream
 *
 * - ObjectStreamClass: дескриптор сериализации для классов
 *      - содержит имя и serialVersionUID
 *
 * - ObjectStreamField: описание поля Serializable из сериализируемого класса
 *
 * - StreamTokenizer: парсит входящий поток на токены, позволяя читать их 1 за раз
 *
 * - /InputStream/: входящий поток байтов
 *      - ByteArrayInputStream: содержит внутренний буфер с байтами, которые можно прочитать из
 *      потока
 *      - FileInputStream: получает входящие байты из файла в файловой системе
 *      - FilterInputStream: содержит другие входяшие потоки, которые использует как источник данных,
 *      возможно, преобразуя данные по ходу или предоставляя дополнительный функционал
 *          - BufferedInputStream: буферизация входного потока
 *          - DataInputStream: чтение примитивов из входящего потока
 *          - PushbackInputStream: добавляет функциональность "отхода назад" или "unread" 1 байта
 *      - ObjectInputStream: десериализует примитивы и объекты, предварительно записанные при помощи
 *      ObjectOutputStream
 *          - ObjectInputStream.GetField: доступ к полям, считанным из входного потока
 *      - PipedInputStream: должен подключаться к трубному выходящему потоку и предоставляет все
 *      байты, которые пишутся в трубный выходящий поток
 *      - SequenceInputStream: логическая конкатенация к другим исходящим потокам
 *
 *
 * - /OutputStream/: исходящий поток байтов
 *      - ByteArrayOutputStream: имплементирует исходящий поток, в который данные записываются в
 *      байтовый массив
 *      - FileOutputStream: исходящий поток для записи данных в File или в FileDescriptor
 *      - FilterOutputStream: суперкласс для всех классов, которые фильтруют исходящие потоки
 *          - BufferedOutputStream: буферизация выходного потока
 *          - DataOutputStream: запись примитивов из входящего потока
 *          - PrintStream: добавляет удобный функционал представления значений различных типов данных
 *      - ObjectOutputStream: пишет примитивы и графы объектов Java в OutputStream
 *          - ObjectOutputStream.PutField: программный доступ к полям для записи в ObjectOutput
 *      - PipedOutputStream: подключается к входящему трубному потоку для создания коммуникационной
 *      трубы
 *
 * - /Reader/: чтение символов из потока
 *      - implements Readable, Closeable
 *      - BufferedReader: буферизирует символы из входящего потока для эффективного чтения
 *          - LineNumberReader: буферизированный входящий поток, который отслеживает номера строк
 *      - CharArrayReader: имплементирует символьный буфер, который можно использовать как входной
 *      симпольный поток
 *      - /FilterReader/: чтение фильтрованных симпольных потоков
 *          - PushbackReader: позволяет символам "отступать назад" в потоке
 *      - InputStreamReader: мост между байтовыми и символьными потоками: читает байты и декодирует
 *      их в символы, используя указанный charset
 *          - FileReader: чтение из символьного файла
 *      - PipedReader: трубный входящий символьный поток
 *      - StringReader: символьный поток, у которого источником является строка
 *
 * - /Writer/: запись символов в поток
 *      - implements Closeable, Flushable, Appendable, AutoCloseable
 *      - BufferedWriter: буферизирует символы для эффективной записи в исходящий поток
 *          - LineNumberWriter:
 *      - CharArrayWriter: имплементирует символьный буфер, который можно использовать как Writer
 *      - /FilterWriter/: запись в фильтрованные симпольные потоки
 *      - OutputStreamWriter: мост между символьным и байтовым потоком: записанные в него символы
 *      кодируются в байты, используя указанный charset
 *          - FileWriter: запись в символьный файл
 *      - PipedWriter: трубный исходящий символьный поток
 *      - PrintWriter: печатает отформатированное представление объекта в тектовый исходящий поток
 *      - StringWriter: символьный поток, который собирает свой выход в строковый буфер, который
 *      можно использовать для создания строк
 *
 * ----------------------------------ИНТЕРФЕЙСЫ--------------------------------------
 *
 * - Closable: источник или цель для данных, который можно закрыть
 *      - extends java.lang.AutoCloseable
 *
 * - |DataInput|: чтение байтов из бинарного потока и реконструкция из них данных в любой из
 * примитивов Java
 *      - |ObjectInput|: возможность также читать объекты
 *
 * - |DataOutput|: перевод любого примитива Java в серию байтов и запись их в бинарный поток
 *      - |ObjectOutput|: возможность также писать объекты
 *
 * - |ObjectInputValidation|: интерфейс-колбек, чтобы позволить валидацию объектов в графе
 *      - позволяет вызвать объект, когда полный граф объектов был десериализован
 *
 * - |Serializable|: обозначает, что имплементирующий класс можно (де)сериализировать
 *      - маркерный интерфейс
 *      - |Externalizable|: только имплементирующие объекты могут передаваться в сериализационный
 *      поток
 *
 * - |FileFilter|: фильтр для абстрактных имен путей
 *      - функциональный интерфейс
 *      - объекты имплементируемых классов передаются в listFiles(FileFilter) класса File
 *
 * - |FileNameFilter|: объекты имплементируемых классов используются для фильтрования имен файлов
 *      - функциональный интерфейс
 *
 * - |Flushable|: цель для данных, который можно слить (записать любой буферизированный выход в поток)
 *
 * - |ObjectStreamConstants|: константы, записываемые в Сериализационный Поток Объекта
 *
 * -----------------------------------ЛЕГЕНДА----------------------------------------
 *
 * - Reader: простой класс
 * - /Reader/: абстрактный класс
 * - (Reader): финализированный класс
 * - |Reader|: интерфейс */


/* БАЗОВЫЙ IO
 * - пакет java.io
 *
 * - с 1.0
 *
 * - работа с потоками (stream)
 *
 * - побайтовые операции
 *
 * - 2 ключевых родительских класса:
 *      - InputStream: байтовый поток чтения
 *      - OutputStream: байтовый записи байтов
 *      - пример наследников:
 *          - FileInputStream: считывает байты (в виде int от 0 до 255) из файла
 *          - FileOutPutStream: записывает байты (в виде int от 0 до 255) в файл
 *
 * - в основе лежит Декоратор
 *      - функционал добавляется оборачиванием, напр:
 *          - new BufferedReader(new FileReader("1.txt")); // класс, читающий посимвольно файлы,
 *          передается в класс, читающий символы при помощи буфера, и получается класс, читающий
 *          посимвольно файлы при помощи буфера
 *      - FilterInputStream/FilterOutputStream - родитель для декораторов
 *
 * - с 1.1 2 новых родительских класса-удобства (мосты типа "байт-в-символ"), которые оборачивают
 * InputStream и OutputStream:
 *      - Reader: байтовый поток чтения символов
 *      - Writer: байтовый поток записи символов
 *          - пример наследования:
 *              - FileReader: считывает символы (в виде int от 0 до 65535) из файла
 *              - FileWriter: записывает символы (в виде int от 0 до 65535) в файл
 *      - иерархия новых классов почти полностью дублирует байтовую
 *      - являются более предпочтительными, но в некоторых случаях без байтовых классов не обойтись
 *          - напр. при работе с java.util.zip или при передаче данных платформонезависимым способом
 *          (в двоичном виде) классами DataInputStream/DataOutputStream
 *      - если нет подходящей обертки Reader/Writer, есть 2 моста (т.е. адаптеры) "байт-в-символ"
 *      общего назначения:
 *          - InputStreamReader: переводит InputStream в Reader
 *          - OutputStreamWriter: переводит OutputStream в Writer
 *      - автоматически переводят из Unicode в/из локального набора символов
 *      - причиной стал переход от 8-битового Unicode в 16-битовый
 *
 * - "каналы" или "трубы" (класс Piped..) предназначены для работы с несколькими программными
 * потоками, т.е. concurrency
 *      */



/* ТИПИЧНОЕ ИСПОЛЬЗОВАНИЕ
 * - буферизированное чтение из файла:
 *      new BufferedReader(new FileReader("1.txt")).readLine();
 *
 * - чтение из памяти:
 *          new StringReader(BufferedInputFile.read("1.txt")).read();
 *
 * - форматированное чтение из памяти:
 *          new DataInputStream(new ByteArrayInputStream(BufferedInputFile.read("1.txt").getBytes()
 *          .readByte())
 *
 * - вывод в файл:
 *      new PrintWriter(new BufferedWriter(new FileWriter(file)));
 *
 * - сокращенная запись для вывода в текстовые файлы
 *      - c J5
 *      new PrintWriter(file);
 *
 * - сохранение и восстановление данных
 *      new DataOutputStream(new BufferedOutputStream(new FileOutputStream("Data.txt"));
 *      new DataInputStream(new BufferedInputStream(new FileIntputStream("Data.txt"));
 *
 * - чтение/запись файлов с произвольным доступом
 *      new RandomAccessFile(file, "r").readDouble().close();
 *      new RandomAccessFile(file, "rw").writeDouble(2d).close();
 *      RandomAccessFile(file, "rw").seek(5*8).writeDouble(2d).close();
 */


public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, BackingStoreException {
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
            in = new FileInputStream("src\\io_io_and_streams\\byte_test_src.txt");
            out = new FileOutputStream("src\\io_io_and_streams\\byte_test_dest.txt");

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
            in = new FileReader("src\\io_io_and_streams\\char_test_src.txt");
            out = new FileWriter("src\\io_io_and_streams\\char_test_dest.txt");

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
            in = new BufferedReader(new FileReader("src\\io_io_and_streams\\char_line_test_src.txt"));
            out = new PrintWriter(new FileWriter("src\\io_io_and_streams\\char_line_test_dest.txt"));

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
                    BufferedOutputStream(new FileOutputStream("src\\io_io_and_streams\\write_data_test.txt")));

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
                    BufferedInputStream(new FileInputStream("src\\io_io_and_streams\\write_data_test.txt")));

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
                    BufferedOutputStream(new FileOutputStream("src\\io_io_and_streams\\write_obj_test.txt")));

            out.writeObject(bigDecimal);
        } finally {
            out.close();
        }

        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new
                    BufferedInputStream(new FileInputStream("src\\io_io_and_streams\\write_obj_test.txt")));

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