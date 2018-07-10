package io_io_and_streams;

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


/* ПОТОКИ (STREAMS) - ПОСЛЕДОВАТЕЛЬНОСТЬ ДАННЫХ, СЧИТЫВАЕМЫХ ИЗ ИСТОЧНИКА ИЛИ ЗАПИСЫВАЕМЫХ В ЦЕЛЬ
 * - источником/целью могут быть, например:
 *      - файлы на диске
 *      - другие устройства
 *      - другие программы
 *      - массивы памяти
 *      - и т.д.
 *
 * - поддерживают разные виды данных:
 *     - биты (напр. DataInputStream/DataOutputStream)
 *     - простые байты (напр. FileInputStream/FileOutputStream)
 *     - примитивы (напр. DataInputStream/DataOutputStream или ObjectInputStream/ObjectOutputStream)
 *     - локализированные символы (Unicode) (напр. FileReader/FileWriter)
 *     - объекты (напр. ObjectInputStream/ObjectOutputStream)
 *          - если записываются объекты, которые содержат другие объекты - они записываются все
 *              - объект дважды не записывается - записывается только ссылка на него
 *
 * - в отличие от каналов:
 *      - являются однонаправленными:
 *          - либо входящий (InputStream)
 *          - либо исходящий (OutputStream)
 *      - не могут быть закрытыми
 *          - после закрытия перестают существовать
 *      - после использования обязательно закрывать
 *      - манипулируют байтами, а не буферами (напр. с байтами) */


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



/* FILE
 *
 *
 * */


/* СЖАТИЕ ДАННЫХ
 * - классы для сжатия базируются на существующих потоках IO и предоставляют функциональность сжатия
 *      - входят в иерархию IOStream
 *
 * - классы:
 *      - CheckedInputStream: метод getCheckSum() возвращает контрольную сумму для любого входного
 *      потока InputStream (не только для потока распаковки)
 *      - CheckedOutputStream: метод getCheckSum() возвращает контрольную сумму для любого выходного
 *      потока OutputStream (не только для потока распаковки)
 *      - DeflaterOutputStream: базовый класс для сжатия данных
 *          - ZipOutputStream: производит сжатие данных в формате ZIP
 *          - GZIPOutputStream: производит сжатие данных в формате GZIP
 *      - InflaterInputStream: базовый класс для классов распаковки сжатых данных
 *          - ZipIntputStream: распаковывает сжатые данные, хранящиеся в формате ZIP
 *          - GZIPIntputStream: распаковывает сжатые данные, хранящиеся в формате GZIP
 *
 * - просто надстраиваются на потоки IO
 *
 * - можно работать и с несколькими файлами в архиве
 *
 * - 2 алгоритма проверки контрольной суммы:
 *      - класс Adler32 - быстрый
 *      - класс CRC32 - медленный, но гораздо точнее
 * */


/* СЕРИАЛИЗАЦИЯ
 * - transient
 * - Serializable - процесс сериализации происходит автоматически
 * - Externalizable
 *      - способность управлять процессом сериализации, напр. что сериализировать, а что нет
 *      - transient нет смысла использовать, потому что процесс не автоматический
 *
 * - альтернатива Externalizable: реализовать интерфейс Serializable и добавить (а не переопределить)
 * методы write/readObject
 *      - будут автоматически вызваны при сериализации
 * - версии
 * - при восстановлении система не имеет представления о том, что объекты были уже восстановлены и
 * имеются в программе, поэтому она создает новые
 * - статические значения не сериализируются
 * */

/* RANDOM ACCESS FILE
 * - implements Closeable, DataInput, DataOutput, AutoCloseable
 * - не является частью иерархии потоков, так как позволяет перемещаться по файлу в обе стороны
 * - по сути похож на пару совмещенных DataInputStream и DataOutputStream, дополненную методами
 * getFilePointer для текущей позиции и length для максимального размера файла
 * - имеет уже встроенную буферизацию
 *
 * */

/* PREFERENCES API c 1.4
 * - набор пар ключ-значение, образующих иерархию узлов
 *      - обычно используется только 1 узел, названный как класс
 * - хранить можно только примитивы и строки
 *      - длина строки не больше 8 кб
 * - позволяются автоматически сохранять и восстанавливать информацию о предпочтениях пользователя и
 * конфигурации программы
 * - методы userNodeForPackage/systemNodeForPackage: для пользовательских предпочтений и для общей
 * конфигурационной инфы
 * - система предпочтений привлекает для хранения данных подходящие системные возможности текущей ОС
 *      - в Windows используется, например, реестр
 *
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

/* COMMAND-LINE io: СТАНДАРТНЫЕ ПОТОКИ И КОНСОЛЬ
 * - "стандартный io" - концепция (UNIX) единого потока информации, используемого программой
 *      - чтобы соединять выход одной программы со входом другой при помощи стандарта
 *
 * - стандартный поток ввода (клавиатура): System.in
 *      - откуда приходит информация
 *      - объект InputStream
 *          - т.е. при работе нужно использовать обертку
 *              - напр. new BufferedReader(new InputStreamReader(System.in));
 *
 *
 * - стандартный поток вывода (дисплей): System.out
 *      - куда записывается информация
 *      - объект PrintStream, но внутри использует объект символьного потока OutputStream
 *
 * - стандартный поток ошибок: System.err
 *      - объект PrintStream, но внутри использует объект символьного потока OutputStream
 *
 * - потоки можно перенаправлять методами: System.setIn(), System.setOut(), System.setErr()
 *
 * - Console - более продвинутая версия стандартных потоков
 *      - использует символьные потоки
 *      - подходит, например, для ввода паролей
 *          - метод readPassword()
 *      - получить: System.console() */





/* ИЕРАРХИЯ ПАКЕТА JAVA.IO
 * - File: путь к файлу или папке
 *
 * - RandomAccessFile: неупорядоченное RW в файл
 *
 * - Console: методы доступа к символьному консольному устройству, если такой есть
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


public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, BackingStoreException {
        byteStreamCopy();
        charStreamCopy();
        charLineStreamCopy();
        textScan();
//        askPasswordViaConsole();
        writeReadData();
        writeReadObjects();
        preferencesDemo();
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


    private static void preferencesDemo() throws BackingStoreException {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        prefs.putInt("MyValue", 666);
        for (String s : prefs.keys()) {
            System.out.println(s + ":" + prefs.getInt(s, 0));
        }
    }
}