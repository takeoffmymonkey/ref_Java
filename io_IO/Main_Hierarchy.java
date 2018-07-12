package io_IO;

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
 *      RandomAccessFile(file, "rw").seek(5*8).writeDouble(2d).close(); */


public class Main_Hierarchy {
}