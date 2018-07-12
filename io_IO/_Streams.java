package io_IO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/* ПОТОК (STREAM) - ПОСЛЕДОВАТЕЛЬНОСТЬ ДАННЫХ, СЧИТЫВАЕМЫХ ИЗ ИСТОЧНИКА ИЛИ ЗАПИСЫВАЕМЫХ В ЦЕЛЬ
 *
 * [ИСТОЧНИК ДАННЫХ] -> (входящий поток) -> [ПРОГРАММА] -> (исходяший поток) -> [ЦЕЛЬ ДАННЫХ]
 *
 * - источником/целью могут быть, например:
 *      - файлы на диске
 *      - другие устройства
 *      - другие программы
 *      - массивы памяти
 *      - и т.д.
 *
 * - данные внутри представлены байтами
 *
 * - классы потоков представлены 2 иерархиями:
 *      - InputStream/OutputStream: чтение/запись ЛЮБЫХ данных как байтов (диапазон чисел 0-255)
 *      - Reader/Writer: чтение/запись ТЕКСТОВЫХ данных как символов (диапазон чисел 0-65357)
 *          - оборачивают байтовый поток
 *          - производят все нужное декодирование из байтов в символы и обратно
 *
 * - кроме работы с байтовыми и символьными источниками, можно работать с:
 *      - битами (DataInputStream/DataOutputStream)
 *      - примитивами (DataInputStream/DataOutputStream или ObjectInputStream/ObjectOutputStream)
 *      - объектами (ObjectInputStream/ObjectOutputStream)
 *
 * - являются однонаправленными, т.е.:
 *      - входной для считывания из источника
 *      - выходной для записи в цель
 *
 * - требуют закрытия после использования, обычно в блоке:
 *      - finally
 *      - try-with-resources если ресурс реализует интерфейс Closable/AutoClosable
 *
 * - после закрытия перестают существовать (в отличие от каналов)
 *
 * - передача может происходит побайтово либо при помощи буфера:
 *      - буфер необходимо использовать для повышения эффективности
 *      - для байтовых иерархий: BufferedInputStream и BufferedOutputStream
 *      - для символьных иерархий: BufferedReader и BufferedWriter
 *      - входной поток считывает данные из буфера в памяти, когда буфер заполнен
 *      - выходной поток записывает данные в буфер, когда буфер заполнен
 *      - иногда необходимо очищать (flush) буфер, прежде чем он заполнится:
 *          - некоторые классы буферизированного вывода поддерживают autoflush
 *              - передается дополнительным аргументом в конструктор
 *          - срабатывает при наступлении определенных событий
 *              - напр. в PrintWriter autoflush срабатывает при каждом вызове println или format
 *          - запустить вручную: flush()
 *              - метод валиден для любого выходного потока
 *                  - но только если он буферизирован
 *
 * - функционал добавляется оборачиванием (паттерн Декоратор), напр:
 *      - new BufferedReader(new FileReader("1.txt")); // класс, читающий посимвольно файлы,
 *      передается в класс, читающий символы при помощи буфера, и получается класс, читающий
 *      посимвольно файлы при помощи буфера
 *      - FilterInputStream/FilterOutputStream - родители для Декораторов */


/* ПРОЦЕСС ЧТЕНИЯ ИЗ ПОТОКА:
 * 1. Идентифицировать ресурс (напр. файл, строка, массив, сетевое подключение)
 * 2. Построить входящий поток при помощи идентифицированного ресурса
 *      - поток сразу предоставляется программе для чтения, его не нужно отдельно открывать
 * 3. Прочитать данные из входящего потока
 *      - обычно в цикле, 1 единица данных за другой, до уведомления о конце потока
 * 4. Закрыть входящий поток */


/* ПРОЦЕСС ЗАПИСИ В ПОТОК:
 * 1. Идентифицировать цель (напр. файл, строка, массив, сетевое подключение)
 * 2. Построить исходящий поток при помощи идентифицированной цели
 *      - поток сразу предоставляется программе для записи, его не нужно отдельно открывать
 * 3. Записать данные в исходящий поток
 * 4. Закрыть поток */



/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ИЕРАРХИЯ БАЙТОВЫХ ПОТОКОВ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/* JAVA.IO.INPUTSTREAM - ВХОДЯЩИЙ ПОТОК БАЙТОВ
 * - abstract class InputStream extends Object implements Closeable, AutoClosable
 *
 * - методы:
 *      - available(): сколько примерно байтов можно прочитать (или пропустить) в потоке без
 *      блокирования до следующего вызова метода данного потока
 *      - close(): закрыть поток и освободить все связанные с ним ресурсы
 *      - mark(int readlimit): отметить текущее положение в потоке
 *      - markSupported(): проверка поддержки потоком методов mark и reset
 *      - read(): прочитать следующий байт из потока
 *      - read(byte[] b): прочитать несколько байтов из потока и сохранить их в массив
 *      - read(byte[] b, int off, int len): прочитать указанное количество байтов из потока и
 *      сохранить их в массив
 *      - reset(): переместить поток во время, когда последний раз вызывался метод mark для потока
 *      - skip(long n): пропустить указанное количество байтов в потоке
 *
 * - иерархия:
 *      - ByteArrayInputStream: содержит внутренний буфер с байтами, которые можно прочитать из
 *      потока
 *      - FileInputStream: получает входящие байты из файла в файловой системе
 *      - FilterInputStream: содержит другие входящие потоки, которые использует как источник данных,
 *      возможно, преобразуя данные по ходу или предоставляя дополнительный функционал
 *          - BufferedInputStream: буферизация входного потока
 *          - DataInputStream: чтение примитивов из входящего потока
 *          - PushbackInputStream: добавляет функциональность "отхода назад" или "unread" 1 байта
 *      - ObjectInputStream: десериализует примитивы и объекты, предварительно записанные при помощи
 *      ObjectOutputStream
 *          - ObjectInputStream.GetField: доступ к полям, считанным из входного потока
 *      - PipedInputStream: должен подключаться к трубному выходящему потоку и предоставляет все
 *      байты, которые пишутся в трубный выходящий поток
 *      - SequenceInputStream: логическая конкатенация к другим исходящим потокам */


/* JAVA.IO.OUTPUTSTREAM - ИСХОДЯЩИЙ ПОТОК БАЙТОВ
 * - public abstract class OutputStream extends Object implements Closeable, Flushable, AutoCloseable
 *
 * - методы:
 *      - close(): закрыть поток и освободить все связанные с ним ресурсы
 *      - flush(): сливает поток и записывает все буферизированные выходные байты
 *      - write(byte[] b): пишет количество байтов, которое соответствует длине массива, из
 *      указанного байтового массива в выходящий поток
 *      - write(byte[] b, int off, int len): то же, но с указанием длины и смещения
 *      - write(int b): пишет указанный байт в выходной поток  *
 *
 * - иерархия:
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
 *      трубы */



/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ИЕРАРХИЯ СИМВОЛЬНЫХ ПОТОКОВ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/* JAVA.IO.READER - ВХОДЯЩИЙ СИМВОЛЬНЫЙ ПОТОК БАЙТОВ
 * - public abstract class Reader extends Object implements Readable, Closeable, AutoCloseable
 *
 * - методы:
 *      - close(): закрыть поток и освободить все связанные с ним системные ресурсы
 *      - mark(int readAheadLimit): пометить текущее расположение в потоке
 *      - markSupported(): проверить поддержку потоком операции mark
 *      - read(): прочитать 1 символ
 *      - read(char[] cbuf): считать символы в массив
 *      - read(char[] cbuf, int off, int len): считать символы в кусок массива
 *      - read(CharBuffer target): попытаться считать символы в указанный символьный буфер
 *      - ready(): проверка открытости потока для чтения
 *      - reset(): ресетнуть поток
 *      - skip(long n): пропустить указанное количество символов при чтении из потока
 *
 * - иерархия:
 *      - BufferedReader: буферизирует символы из входящего потока для эффективного чтения
 *          - LineNumberReader: буферизированный входящий поток, который отслеживает номера строк
 *      - CharArrayReader: имплементирует символьный буфер, который можно использовать как входной
 *      символьный поток
 *      - /FilterReader/: чтение фильтрованных символьных потоков
 *          - PushbackReader: позволяет символам "отступать назад" в потоке
 *      - InputStreamReader: мост между байтовыми и символьными потоками: читает байты и декодирует
 *      их в символы, используя указанный charset
 *          - FileReader: чтение из символьного файла
 *      - PipedReader: трубный входящий символьный поток
 *      - StringReader: символьный поток, у которого источником является строка */


/* JAVA.IO.WRITER - ИСХОДЯЩИЙ СИМВОЛЬНЫЙ ПОТОК БАЙТОВ
 * - public abstract class Writer extends Object implements Appendable, Closeable, Flushable, AutoCloseable
 *
 * - методы:
 *      - append(char c): добавить символ к данному Writer
 *      - append(CharSequence csq): добавить последовательность символов к данному Writer
 *      - append(CharSequence csq, int start, int end): добавить кусок указанной последовательности
 *      - close(): закрыть поток, предварительно слив его
 *      - flush(): слить поток
 *      - write(char[] cbuf): записать массив символов
 *      - write(char[] cbuf, int off, int len): записать кусок массива символов
 *      - write(int c): записать символ
 *      - write(String str): записать строку
 *      - write(String str, int off, int len): записать кусок строки
 *
 * - иерархия:
 *      - BufferedWriter: буферизирует символы для эффективной записи в исходящий поток
 *          - LineNumberWriter:
 *      - CharArrayWriter: имплементирует символьный буфер, который можно использовать как Writer
 *      - /FilterWriter/: запись в фильтрованные символьные потоки
 *      - OutputStreamWriter: мост между символьным и байтовым потоком: записанные в него символы
 *      кодируются в байты, используя указанный charset
 *          - FileWriter: запись в символьный файл
 *      - PipedWriter: трубный исходящий символьный поток
 *      - PrintWriter: печатает отформатированное представление объекта в тектовый исходящий поток
 *      - StringWriter: символьный поток, который собирает свой выход в строковый буфер, который
 *      можно использовать для создания строк */



/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ИНТЕРФЕЙСЫ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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
 * - |Flushable|: цель для данных, которые можно слить (записать любой буферизированный выход в поток)
 *
 * - |ObjectStreamConstants|: константы, записываемые в Сериализационный Поток Объекта */



/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ЛЕГЕНДА~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * - Reader: простой класс
 * - /Reader/: абстрактный класс
 * - (Reader): финализированный класс
 * - |Reader|: интерфейс */


public class _Streams {

    static File currentFolder = new File("C:\\git\\ref_Java\\src\\io_IO\\files\\");

    public static void main(String[] args) throws IOException {
        copyFileContentByBytes();
        copyFileContentByChars();
        copyBufferedFileContentByBytes();
        copyBufferedFileContentByChars();

    }


    private static void copyFileContentByBytes() throws IOException {
        File src = new File(currentFolder, "bybyte_copy_src.txt");
        File dest = new File(currentFolder, "bybyte_copy_dest.txt");

        try (FileInputStream in = new FileInputStream(src);
             FileOutputStream out = new FileOutputStream(dest)) {

            int i;
            while ((i = in.read()) != -1) {
                out.write(i);
            }
        }
    }


    private static void copyFileContentByChars() throws IOException {
        File src = new File(currentFolder, "bychar_copy_src.txt");
        File dest = new File(currentFolder, "bychar_copy_dest.txt");

        try (FileReader in = new FileReader(src); FileWriter out = new FileWriter(dest)) {

            int i;
            while ((i = in.read()) != -1) {
                out.write(i);
            }
        }
    }


    private static void copyBufferedFileContentByBytes() throws IOException {
        File src = new File(currentFolder, "buffered_bychar_copy_src.txt");
        File dest = new File(currentFolder, "buffered_bychar_copy_dest.txt");

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(src));
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest))) {
            int i;
            while ((i = in.read()) != -1) {
                out.write(i);
            }
        }
    }


    private static void copyBufferedFileContentByChars() throws IOException {
        File src = new File(currentFolder, "buffered_bychar_copy_src.txt");
        File dest = new File(currentFolder, "buffered_bychar_copy_dest.txt");

        try (BufferedReader in = new BufferedReader(new FileReader(src));
             BufferedWriter out = new BufferedWriter(new FileWriter(dest))) {
            String s;
            while ((s = in.readLine()) != null) {
                out.write(s);
            }
        }
    }
}