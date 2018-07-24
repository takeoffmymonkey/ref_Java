package io;

import _types_references_annotations.my_annotations.Ntrstn;

/* ПАКЕТЫ ФУНКЦИОНАЛА I/O
 * - IO: с 1.0
 *      - java.io: поддержка системного I/O при помощи потоков данных, сериализации и файловой системы
 *
 * - NIO: с 1.4
 *      - java.nio: буферы (контейнер данных) для "кусочных" операций с памятью (могут быть
 *      аллоцированы прямо в памяти для высокой скорости)
 *      - java.nio.channels:
 *          - каналы - т.е. связь между устройствами, способными производить операции I/O
 *          - селекторы для мультиплексированного неблокирующего I/O
 *      - java.nio.channels.spi: имплементации для пакета java.nio.channels
 *      - java.nio.charset: наборы символов, кодеры/декодеры для перевода между
 *      байтами и символами Unicode
 *      - java.nio.charset.spi: имплементации для наборов символов
 *      - com.sun.nio.sctp: API для Stream Control Transport Protocol (надежный ориентированный на
 *      сообщения транспортный протокол)
 *
 * - NIO.2: с 1.7
 *      - java.nio.file: интерфейсы и классы для доступа к файлам, их атрибутам и файловой системе
 *      - java.nio.file.attribute: интерфейсы и классы для доступа к атрибутам файловой системы
 *      - java.nio.file.spi: классы для создания имплементации файловой системы
 *
 * - дополнительный функционал:
 *      - java.util, java.util.prefs, java.util.zip и т.д. */


/* API's:
 * - работа с файлами и их атрибутами
 *      - в т.ч. неупорядоченным доступ к файлу
 * - работа с симлинками
 * - работа с файловыми системами и их атрибутами
 * - сериализация
 * - наборы символов, декодеры и кодировщики для перевода между байтами и символами Unicode
 * - стандартный IO и консоль
 * - форматирование вывода
 * - постройка масштабируемых серверов при помощи асинхронного или мультиплексового
 * неблокирующего I/O
 * - проход по файловому дереву
 * - служба наблюдения за ресурсом
 * - работа с форматами ZIP и GZIP
 * - сканирование строки с разбитием по сепаратору и определению типа примитива*/


/* ПЕРЕДАЧА ДАННЫХ
 * - по типам, с которыми идет работа:
 *      - "байтовые": InputStream и OutputStream
 *      - "символьные": Reader и Writer
 *      - "с примитивами и объектами": сериализация, DataInputStream и DataOutputStream
 *      - при этом сами данные внутри потока представлены как байты
 *
 * - есть "источник" и "цель":
 *      - файлы на диске
 *      - другие устройства
 *      - другие программы
 *      - массивы памяти
 *      - и т.д.
 *
 * - можно использовать "буфер" (для повышения производительности):
 *          - контейнер для данных
 *          - без буфера работа идет побайтово/посимвольно
 *          - для IO: BufferedInputStream, BufferedOutputStream, BufferedReader и BufferedWriter
 *          - для NIO: работа с буфером обязательна
 *
 * - производятся через "потоки" или "каналы" данных:
 *      - "поток": непрерываемая последовательность данных
 *          - используется в IO
 *          - с одной стороны источник, с другой цель
 *              - входящий поток для чтения данных из источника
 *              - исходящий поток для записи данных в цель
 *          - без буфера нельзя двигаться назад по потоку
 *          - может быть только read-write
 *          - операции R/W являются блокирующими
 *          - без буфера передача данных идет побайтово/посимвольно
 *          - необходимо открывать и закрывать
 *
 *      - "канал": трубопровод для передачи данных
 *          - с одной стороны источник, с другой цель
 *              - 1 канал и для чтения и для записи
 *              - передача данных в канал и из канала происходит через буфер-посредник
 *                  - по буферу можно двигаться вперед/назад
 *          - используется c NIO и NIO.2
 *          - может быть read-only, write-only, read-write
 *          - операции R/W могут быть неблокирующими при использовании канала для
 *          stream-ориентированных потоков (напр. сокетов)
 *          - реализация близка к нативной реализации в ОС
 *
 * - могут быть блокирующими и неблокирующими
 *      - "блокирующие": при использовании потока
 *      todo - "неблокирующие":  */


/* IO
 * - 2 иерархии классов для байтовых и символьных потоков
 *      - байтовая: InputStream, OutputStream
 *      - символьная: Reader, Writer
 *          - почти полностью дублирует функционал байтовой
 *          - является мостом "байт-в-символ"
 *              - оборачивает байтовые объекты
 *          - автоматически переводят из Unicode в/из локального набора символов
 *          - являются более предпочтительной
 *              - но в некоторых случаях без байтовых классов не обойтись
 *                  - напр. при работе с java.util.zip или при передаче данных платформонезависимым
 *                  способом (в двоичном виде) классами DataInputStream/DataOutputStream
 *
 * - все операции при помощи потоков (т.е. побайтово/посимвольно)
 *      - но есть и буферные классы, оборачивающие простые потоки:
 *          - BufferedInputStream/BufferedReader
 *          - BufferedOutputStream/BufferedWriter
 *
 * - другие важные классы/интерфейсы:
 *      - java.io.File: путь к файлу или папке
 *      - java.io.RandomAccessFile: неупорядоченный доступ к файлу
 *      - java.io.Console: символьное консольное устройство
 *      - java.io.Piped...: предназначены для работы с несколькими программными потоками, т.е.
 *      concurrency
 *      - java.io.FilePermission: доступ к файлу или папке
 *      - java.io.Serializable: имплементирующий класс можно (де)сериализировать
 *      - java.io.FileFilter: фильтр для абстрактных имен путей
 *      - java.io.DataInput|DataOutput: чтение/запись байтов в/из бинарного потока и реконструкция из них
 *      примитивов
 *          - java.io.ObjectInput|ObjectOutput: также чтение/запись объектов
 *
 * - основан на паттерне Декоратор
 *      - функционал добавляется оборачиванием, напр:
 *          - new BufferedReader(new FileReader("1.txt")); // класс, читающий посимвольно файлы,
 *          передается в класс, читающий символы при помощи буфера, и получается класс, читающий
 *          посимвольно файлы при помощи буфера
 *      - FilterInputStream/FilterOutputStream - родители для Декораторов */


/* JAVA NIO
 * - является расширением, а не заменой IO
 * - основные дополнения:
 *      - значительно улучшенная скорость работы за счет добавления каналов и буферов
 *          - перепало и существующим классам в java.io
 *      - неблокирующие (сетевые?) операции
 *      - кодеры/декодеры наборов символов
 *      - сверка с паттерном регуляртного выражения
 *      - файловый интерфейс, поддерживающий локи и мапинг памяти
 *      - мультиплексированные неблокирующие удобства для написания масштабируемых серверов */


/* NIO.2
 * - является расширением, а не заменой IO и NIO
 * - основные дополнения:
 *      - (одно из главных) поддержка доступа к атрибутам файловой системы и файлов
 *          - напр., вместо java.io.File теперь java.nio.file.Path
 *      - масштабирование многих методов File:
 *          - теперь запрос листинга большой папки от сервера не приводит к подвисанию
 *          - теперь большие папки не приводят к проблемам с ресурсами памяти
 *      - поддержка базовых файловых операций (копирование, перенос и удаление) для всех ФС:
 *          - возможность атомический файловых операций переноса
 *          - улучшенная работа с обработкой исключений
 *              - раньше, напр., при удалении файла и фейле, невозможно узнать из-за чего фейл: нет
 *              файла или разрешений и т.д.
 *      - (одно из главных) возможность работы с разными файловыми системами в 1 унифицированном виде:
 *          - поддержка ФС расширяема - можно использовать дефолтные имплементации либо
 *          имплементировать самостоятельно
 *      - поддержка симлинков:
 *          - когда возможно, в операциях с симлинками осуществляется переход к их целям
 *          - возможность писать безопасный код для прохода по дереву каталога и адекватно
 *          обрабатывать рекурсивные симлинки
 *      - возможность создавать сервис отслеживания событий (напр. добавление новых файлов) в
 *      директории:
 *          - программа получает уведомление при наступлении отслеживаемого события
 *      - API для прохода по файловому дереву:
 *          - возможность производить операции с узлами
 *      - поддержка асинхронного IO для сетевых сокетов и файлов
 *      - мультикастинг при помощи DatagramChannel */


/* IO VS NIO
 * - конструкции NIO делают операции I/O быстрее традицонного IO, т.к. они ближе к нативному I/O ОС
 * - операции через IO блокирующие, а некоторые операции через NIO - нет
 * - если в IO нет буфера, то по потоку нельзя двигаться назад
 * - подходящие случаи использования:
 *      - IO: когда нужно держать открытыми ширококанальные подключения (напр. P2P)
 *      - NIO: когда нужно УПРАВЛЯТЬ кучей малых коротких подключений одновременно (напр. чат) */


/* LEGACY FILE I/O CODE (до NIO.2/J7/API 26)
 * - перевод File в Path и обратно:
 *      Path input = file.toPath();
 *      File file = Path.toFile(input);
 *
 * - соответствие функционала:
 *      - IO: java.io.File
 *      - NIO: java.nio.file.Path
 *
 *      - IO: java.io.RandomAccessFile
 *      - NIO: SeekableByteChannel
 *
 *      - IO: File.canRead, canWrite, canExecute
 *      - NIO: Files.isReadable, Files.isWritable, and Files.isExecutable
 *
 *      - IO: Files.isReadable, Files.isWritable, and Files.isExecutable
 *      - NIO: Files.isDirectory(Path, LinkOption...), Files.isRegularFile(Path, LinkOption...), and Files.size(Path)
 *
 *      - IO: File.lastModified() and File.setLastModified(long)
 *      - NIO: Files.getLastModifiedTime(Path, LinkOption...) and Files.setLastMOdifiedTime(Path, FileTime)
 *
 *      - IO: setExecutable, setReadable, setReadOnly, setWritable
 *      - NIO: setAttribute(Path, String, Object, LinkOption...)
 *
 *      - IO: new File(parent, "newfile")
 *      - NIO: parent.resolve("newfile")
 *
 *      - IO: File.renameTo
 *      - NIO: Files.move
 *
 *      - IO: File.delete
 *      - NIO: Files.delete
 *
 *      - IO: File.createNewFile
 *      - NIO: Files.createFile
 *
 *      - IO: File.deleteOnExit
 *      - NIO: createFile(DELETE_ON_CLOSE)
 *
 *      - IO: File.createTempFile
 *      - NIO: Files.createTempFile(Path, String, FileAttributes<?>), Files.createTempFile(Path, String, String, FileAttributes<?>)
 *
 *      - IO: File.exists
 *      - NIO: Files.exists and Files.notExists
 *
 *      - IO: File.compareTo and equals
 *      - NIO: Path.compareTo and equals
 *
 *      - IO: File.getAbsolutePath and getAbsoluteFile
 *      - NIO: Path.toAbsolutePath
 *
 *      - IO: File.getCanonicalPath and getCanonicalFile
 *      - NIO: Path.toRealPath or normalize
 *
 *      - IO: File.toURI
 *      - NIO: Path.toURI
 *
 *      - IO: File.isHidden
 *      - NIO: Files.isHidden
 *
 *      - IO: File.list and listFiles
 *      - NIO: Path.newDirectoryStream
 *
 *      - IO: File.mkdir and mkdirs
 *      - NIO: Path.createDirectory
 *
 *      - IO: File.listRoots
 *      - NIO: FileSystem.getRootDirectories
 *
 *      - IO: File.getTotalSpace, File.getFreeSpace, File.getUsableSpace
 *      - NIO: FileStore.getTotalSpace, FileStore.getUnallocatedSpace, FileStore.getUsableSpace, FileStore.getTotalSpace */


@Ntrstn("Функционал I/O сосредоточен в следующих группах пакетов:" +
        "1 - IO (c 1.0): пакет java.io: поддержка системного I/O при помощи потоков данных, " +
        "сериализации и файловой системы" +
        "" +
        "2 - NIO (c 1.4): пакеты:" +
        "   - java.nio: буферы (контейнер данных) для кусочных операций с памятью " +
        "   (могут быть аллоцированы прямо в памяти для высокой скорости) \n" +
        "   - java.nio.channels: каналы - т.е. связь между устройствами, способными производить " +
        "   операции I/O, а также селекторы для мультиплексированного неблокирующего I/O" +
        "   - java.nio.channels.spi: имплементации для пакета java.nio.channels (если нужно расширить)" +
        "   - java.nio.charset: наборы символов, кодеры/декодеры для перевода между байтами и " +
        "   символами Unicode" +
        "   - java.nio.charset.spi: имплементации для наборов символов" +
        "   - com.sun.nio.sctp: API для Stream Control Transport Protocol (надежный ориентированный " +
        "   на сообщения транспортный протокол" +
        "" +
        "3 - NIO.2 (c 1.7): пакеты:" +
        "   - java.nio.file: интерфейсы и классы для доступа к файлам, их атрибутам и файловой системе" +
        "   - java.nio.file.attribute: интерфейсы и классы для доступа к атрибутам файловой системы" +
        "   - java.nio.file.spi: классы для создания имплементации файловой системы" +
        "" +
        "4 - дополнительный функционал, пересекающийся с данными пакетами, находится в: java.util, " +
        "java.util.prefs, java.util.zip и т.д.")

@Ntrstn("IO, NIO и NIO.2 являются дополнениями друг другу, а не заменами (за исключением разве что" +
        "класса java.io.File в IO и пакета java.nio.file в NIO.2)")

@Ntrstn("Совокупно функционал I/O предоставляет следующие API:" +
        " - работа с файлами и их атрибутами, в т.ч. неупорядоченным (в любое место) доступ к файлу" +
        " - работа с симлинками" +
        " - работа с файловыми системами и их атрибутами\n" +
        " - сериализация" +
        " - наборы символов, декодеры и кодировщики для перевода между байтами и символами Unicode\n" +
        " - стандартный IO и консоль" +
        " - форматирование вывода" +
        " - постройка масштабируемых серверов при помощи асинхронного или мультиплексового " +
        "неблокирующего I/O" +
        " - проход по файловому дереву" +
        " - служба наблюдения за ресурсом" +
        " - работа с форматами ZIP и GZIP" +
        " - сканирование строки с разбитием по сепаратору и определению типа примитива")

@Ntrstn("Передавать можно следующие данные: байты, символы, примитивы и объекты. У передачи есть " +
        "цель и источник, можно передавать через буфер либо побайтово/посимвольно(неэффективно), " +
        "передача может производиться через потоки (IO) и через каналы (NIO), может быть блокирующей " +
        "и не блокирующей")

@Ntrstn("IO находится в пакете java.io и содержит 2 основные крупные иерархии классов для " +
        "представления потоков: байтовых (InputStream, OutputStream) и символьных (Reader, Writer - " +
        "все равно основаны на байтовых потоках и почти полностью дублируют их функционал; сделаны " +
        "просто для удобства). IO основан на паттерне декоратор, где нужный функционал добавляется к " +
        "потоку оборачиванием. Кроме того, в пакете есть следующие важные сущности: java.io.File: " +
        "путь к файлу или папке, java.io.RandomAccessFile: неупорядоченный доступ к файлу, " +
        "java.io.Serializable: имплементирующий класс можно (де)сериализировать, " +
        "java.io.DataInput|DataOutput: чтение/запись байтов в/из бинарного потока и реконструкция из " +
        "них примитивов, java.io.ObjectInput|ObjectOutput: также чтение/запись объектов и другие")

@Ntrstn("NIO привнес следующие дополнения: " +
        " - значительно улучшенная скорость работы за счет добавления каналов и буферов (перепало и " +
        "существующим классам в java.io)" +
        " - неблокирующие операции" +
        " - кодеры/декодеры наборов символов" +
        " - сверка с паттерном регулярного выражения" +
        " - файловый интерфейс, поддерживающий локи и маппинг в память" +
        " - мультиплексированные неблокирующие удобства для написания масштабируемых серверов")

@Ntrstn(" NIO.2 привнес следующие дополнения: " +
        " - (одно из главных) поддержка доступа к атрибутам файловой системы и файлов" +
        " - масштабирование многих методов File: теперь запрос листинга большой папки от сервера не " +
        "приводит к подвисанию, и теперь большие папки не приводят к проблемам с ресурсами памяти" +
        " - поддержка базовых файловых операций (копирование, перенос и удаление) для всех ФС: " +
        "возможность атомический файловых операций переноса, улучшенная работа с обработкой " +
        "исключений, " +
        " - (одно из главных) возможность работы с разными файловыми системами в 1 унифицированном " +
        "виде" +
        " - поддержка симлинков" +
        " - возможность создавать сервис отслеживания событий (напр. добавление новых файлов) в " +
        "директории" +
        " - API для прохода по файловому дереву" +
        " - поддержка асинхронного IO для сетевых сокетов и файлов")

@Ntrstn("IO VS NIO:" +
        " - конструкции NIO делают операции I/O быстрее традицонного IO, т.к. они ближе к нативному I/O ОС\n" +
        " - операции через IO блокирующие, а некоторые операции через NIO - нет" +
        " - если в IO нет буфера, то по потоку нельзя двигаться назад" +
        " " +
        "Подходящие случаи использования:" +
        " - IO: когда нужно держать открытыми ширококанальные подключения (напр. P2P)\n" +
        " - NIO: когда нужно УПРАВЛЯТЬ кучей малых коротких подключений одновременно (напр. чат)")

@Ntrstn("NIO.2 заменил основную абстракцию пути к файлу или папке java.io.File на java.nio.file.Path, " +
        "а все методы File (и новые) перекочевали в класс java.nio.file.Files.")

@Ntrstn("Есть возможность переключаться между: " +
        " - байтовыми и символьными потоками: InputStreamReader/OutputStreamWriter" +
        " - потоками и каналами: утилитный класс java.nio.channels.Сhannels" +
        " - File и Path: file.toPath() и Path.toFile(input)")

public class Main_IO_vs_NIO_vs_NIO2 {
}
