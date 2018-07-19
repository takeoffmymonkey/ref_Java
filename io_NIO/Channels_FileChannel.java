package io_NIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

import types_references_annotations.my_annotations.Ntrstn;


/* ПАКЕТ java.nio.channels
 * - определяет каналы: разные типы, которые представляют подключения к сущностям, способным
 * производить I/O операции
 *      - например, устройство, сетевой сокет, файл, программный компонент
 *
 * - определяет селекторы для мультиплексированных неблокирующих операций I/O
 *
 * - иерархия каналов:
 *      - Channel: связующее звено для I/O операций
 *          - ReadableByteChannel: может передавать в буфер
 *              - ScatteringByteChannel: может передавать в последовательность буферов
 *          - WritableByteChannel: может писать из буфера
 *              - GatheringByteChannel: может писать из последовательности буферов
 *          - ByteChannel: может передавать/писать из буфера
 *              - SeekableByteChannel: ByteChannel, подключенный к сущности, у которой
 *              последовательность байтов переменной длины
 *                  - методы для запроса и изменений текущей позиции канала и его размера
 *          - AsynchronousChannel: поддерживает асинхронные операции I/O
 *              - AsynchronousByteChannel: может передавать/писать из буфера асинхронно
 *          - NetworkChannel: канал к сетевому сокету
 *              - MulticastChannel: может соединять Internet Protocol (IP) multicast groups
 *     - Channels: утилитные методы для взаимодействия каналов с потоками
 *
 * - иерархия файловых каналов:
 *      - FileChannel: читает, записывает, мапит и манипулирует файлами
 *          - создается от статического метода open или вызовом getChannel у FileInputStream,
 *          FileOutputStream или RandomAccessFile
 *      - FileLock: блокировка (куска) файла
 *      - MappedByteBuffer: прямой байтовый буфер, замапенный на кусок файла
 *
 * - иерархия мультиплексированного неблокирующего IO
 *      - SelectableChannel: канал, который можно мультиплексировать
 *          - DatagramChannel: канал к datagram-ориентированному сокету
 *          - Pipe.SinkChannel:	пишущий конец трубы (pipe)
 *          - Pipe.SourceChannel: читающий конец трубы
 *          - ServerSocketChannel: канал потоко-ориентированному слушающему сокету
 *          - SocketChannel: канал для потоко-ориентированного сокету
 *      - Selector: мультиплексор каналов, которые можно выбирать
 *      - SelectionKey: токен, представляющий регистрацию канала в селекторе
 *      - Pipe:	2 канала, формирующие однонаправленную трубу
 *
 * - иерархия асинхронного I/O
 *      - AsynchronousFileChannel: асинхронный канал для чтения, записи и манипулирования файлом
 *      - AsynchronousSocketChannel: асинхронный канал для потоко-ориентированного сокета
 *      - AsynchronousServerSocketChannel: то же, но для слушающего сокета
 *      - CompletionHandler: помощник для потребления результата асинхронной операции
 *      - AsynchronousChannelGroup: группа асинхронных каналов для шаринга ресурсов */


/* ИНТЕРФЕЙС java.nio.channels.CHANNEL
 * - public interface Channel extends Closeable
 *
 * - наследуется: AsynchronousByteChannel, AsynchronousChannel, ByteChannel, GatheringByteChannel,
 * InterruptibleChannel, MulticastChannel, NetworkChannel, ReadableByteChannel, ScatteringByteChannel,
 * SeekableByteChannel, WritableByteChannel
 *
 * - имплементируется: AbstractInterruptibleChannel, AbstractSelectableChannel,
 * AsynchronousFileChannel, AsynchronousServerSocketChannel, AsynchronousSocketChannel,
 * DatagramChannel, FileChannel, Pipe.SinkChannel, Pipe.SourceChannel, SelectableChannel,
 * ServerSocketChannel, SocketChannel
 *
 * - связующее звено для I/O операций
 *
 * - представляет открытое подключение к сущности, способной производить одну или более операций I/O
 * (например, читать или писать)
 *
 * - может быть либо открытым либо закрытым
 *      - открывается при создании
 *      - закрывается при закрытии
 *          - любые операции приведут ClosedChannelException
 *
 * - каналы в целом предназначены для безопасного многопоточного доступа
 *
 * - методы:
 *      - close
 *      - isOpen */


/* КЛАСС java.nio.channels.FILECHANNEL
 * - public abstract class FileChannel extends AbstractInterruptibleChannel implements
 * Closeable, AutoCloseable, ByteChannel, Channel, GatheringByteChannel, InterruptibleChannel,
 * ReadableByteChannel, ScatteringByteChannel, SeekableByteChannel, WritableByteChannel
 *
 * - канал для чтения, записи, маппинга и манипулирования файлами
 *
 * - имеет текущую позицию, которую можно узнать и изменить
 *      - т.к. является SeekableByteChannel
 *
 * - можно узнать размер текущего файла
 *      - размер увеличивается, когда происходит запись байтов свыше текущего размера
 *      - размер уменьшается, когда канал обрезается (trancate)
 *
 * - нет методов для работы с метаданными файла
 *
 * - операции помимо стандартный байтовых каналов:
 *      - чтение или запись байтов с абсолютной позиции в файл без изменения текущей позиции канала
 *      - область файла можно замапить прямо в память, что очень эффективно для крупных файлов
 *      - апдейты в файле можно заставить отправлять в диск, чтобы убедиться, что данные не будут
 *      утеряны в случае креша
 *      - байты можно передавать из файла в другой канал (и наоборот) таким образом, что это может
 *      быть оптимизировано многими ОС в очень быстрые трансферы прямо в/из кеша файловой системы
 *      - область файла может быть заблокирована от доступа других программ
 *
 * - безопасны для многопоточных веток
 *      - только одна операция изменения позиции канала или изменения размера файла может быть в 1
 *      момент
 *          - остальные будут заблокированы до конца выполнения первой
 *      - другие операции, например, принимающие абсолютную позицию, могут выполняться одновременно
 *
 * - вид файла, представленного экземпляром FileChannel, гарантировано соответствует другим видам на
 * этот же файл
 *      - но не для одновременно работающих программ из-за кеширования в файловой системе и задержек
 *      сетевых файлосистемных протоколов
 *
 * - создается:
 *      - вызовом open
 *      - вызовом getChannel на FileInputStream, FileOutputStream или RandomAccessFile
 *          - состояние канала будет тесно связано с данными потоками или RandomAccessFile - например
 *          смена позиции там или здесь приведет к ее смене здесь или там
 *
 * - иногда требует "указать экземпляр, который "open for reading," "open for writing," или
 * "open for reading and writing"
 *      - FileInputStream.getChannel дает экземпляр, открытый для чтения
 *      - FileOutputStream.getChannel дает экземпляр, открытый для записи
 *      - RandomAccessFile.getChannel с флагом "r" дает экземпляр, открытый для чтения
 *      - RandomAccessFile.getChannel с флагом "rw" дает экземпляр, открытый для чтения и записи
 *
 * - открытый для записи канал может быть в режиме "append"
 *      - например, если был открыт от FileOutputStream, который был создан конструктором (File,true)
 *      - каждый вызов относительной операции записи будет сначала приводить к перемещению позиции в
 *      конец файла и записи данных после этого
 *          - являются ли операции перемещения и записи атомарными - зависит от системы
 *
 * - режимы маппинга:
 *      - MapMode.READ_ONLY: попытка изменить результирующий буфер приведет к ReadOnlyBufferException
 *      - MapMode.READ_WRITE: изменения в буфере перейдут в файл
 *          - могут быть не видны другим программам, которые замапили этот же файл
 *      - MapMode.PRIVATE: изменения в буфере не перейдут в файл, а будут создаваться приватные
 *      копии измененных кусков буфера
 *          - не будут видны другим программам, которые замапили этот же файл
 *
 * - методы:
 *      - force(): заставить записать изменения на диск с файлом
 *      - lock(): запросить замок на файл
 *      - map(): замапить область файла прямо в память
 *      - open(): открыть или создать файл и вернуть файловый канал для доступа к нему
 *      - position(): позиция канала в файле
 *      - read(): считать последовательность байтов в канале в данный буфер
 *      - size(): размер файла
 *      - transferFrom(): переместить байты в файл данного канала из предоставленного канала
 *      - transferTo(): то же, но наоборот
 *      - truncate(long size): обрезать файл до указанного размера
 *      - tryLock(): попытаться запросить замок на файл
 *      - write(): записать последовательность байтов в канал из предоставленного буфера */


@Ntrstn("Канал - как шахта, в которую на вагонетке (буфере) можно отправить данные или из которого " +
        "можно загрузить данные в вагонетку. Т.е. канал тесно работает с буфером. (Хотя в некоторых " +
        "случаях можно обойтись и без буфера - например методами transferTo/transferFrom перенести " +
        "содержимое одного канала в другой и тем самым скопировать содержимое канала (например файла)" +
        "без работы с буфером. Это, кстати, самый легкий и эффективный способ копирования)). Если " +
        "используется буфер (подходит только ByteBuffer), то, чтобы перенести данные из буфера в " +
        "канал, нужно вызвать метод канала write, а чтобы из канала перенести в буфер - метод read. " +
        "Не стоит забывать, что индекс буфера нахится в последнем месте, где остановилась последняя " +
        "операция над ним, так что может потребоваться его перемотать/перевернуть")

@Ntrstn("Все классы и интерфейсы каналов находятся в пакетах:" +
        "1. java.nio.channels: разные каналы, а также селекторы (для мультиплексированного " +
        "неблокирующего I/O) " +
        "2. java.nio.channels.spi: пакет с классами, которые можно расширять для своих имплементаций")

@Ntrstn("Канал может быть как открытым, так и закрытым, в отличие от потока, который после закрытия " +
        "перестает существовать. Интерфейс Channel определяет только 2 метода: close и isOpen")

@Ntrstn("Канал подключается к какой-то сущности, которая способна выполнять I/O операции, например " +
        "файл, сокет или устройство. Для этого используется соответствующий канал, например FileChannel")

@Ntrstn("Абстрактный класс FileChannel имплементирует большую кучу интерфейсов из пакета " +
        "java.nio.channels")

@Ntrstn("Файловые каналы не создаются напрямую, а только: " +
        "1 - вызовом статического метода open(Path path, OpenOption... options) - канал, который " +
        "может писать/читать (зависит указанных опций)" +
        "2 - от FileInputStream.getChannel - канал, который только может читать" +
        "3 - от FileOutputStream.getChannel - канал, который только может писать" +
        "4 - от RandomAccessFile.getChannel - канал, который может писать/читать (зависит от указанного " +
        "при создании самого RandomAccessFile флага)")

@Ntrstn("Файловый канал имеет текущую позицию, которую можно узнать и изменить ((т.к. является " +
        "SeekableByteChannel)), знает размер файла, к которому подключен (размер меняется, если " +
        "дописать новые данные или сокращается, если обрезать данный канал), у него отсутствуют " +
        "методы для работы с метаданными файла и можно заставить его всегда автоматически писать в " +
        "конец файла")

@Ntrstn("К другим важным операциям файлового буфера относятся:" +
        " - чтение или запись байтов с абсолютной позиции в файл без изменения текущей позиции " +
        "канала" +
        " - область файла можно замапить прямо в память (очень эффективно для крупных файлов) и " +
        "работать на скорости нативных методов" +
        " - апдейты в файле можно заставить отправлять на диск, чтобы убедиться, что данные не будут" +
        "утеряны в случае креша" +
        " - байты можно передавать из файла в другой канал (и наоборот) таким образом, что это может " +
        "быть оптимизировано многими ОС в очень быстрые трансферы прямо в/из кеша файловой системы" +
        " - область файла может быть заблокирована от доступа других программ")

@Ntrstn("Существует утилитный класс java.nio.channels.channels, который предоставляет утилитные " +
        "методы для перевода из потока в канал и обратно")

public class Channels_FileChannel {

    static File currentFolder = new File("C:\\git\\ref_Java\\src\\io_NIO\\files\\");
    static File testFile = new File(currentFolder, "temp2.txt");
    static File testFile2 = new File(currentFolder, "temp3.txt");
    static File testFile3 = new File(currentFolder, "temp4.txt");

    public static void main(String[] args) throws IOException {
        createChannels();
        sizePositionTruncate();
        readToAppendWriteFromBuffer();
        mapping();
        transferingCopy();
    }

    private static void createChannels() throws IOException {
        /* МЕТОДОМ OPEN */
        try (FileChannel fileChannel = FileChannel.open(testFile.toPath(), StandardOpenOption.READ)) {
        }

        /* ОТ ПОТОКОВ */
        try (FileOutputStream outputStream = new FileOutputStream(testFile)) {
            try (FileChannel fileChannel2 = outputStream.getChannel()) {
            }
        }

        /* ОТ RANDOMACCESSFILE */
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(testFile, "r")) {
        }
    }

    private static void sizePositionTruncate() throws IOException {
        try (FileChannel fileChannel = FileChannel.open(testFile.toPath(),
                StandardOpenOption.WRITE)) {

            /* РАЗМЕР */
            System.out.println("Размер файла: " + fileChannel.size());

            /* ПОЗИЦИЯ */
            System.out.println("Позиция в файле: " + fileChannel.position());
            fileChannel.position(2);
            System.out.println("Новая позиция в файле: " + fileChannel.position());

            /* ОБРЕЗАНИЕ */
            fileChannel.truncate(2);
            System.out.println("Размер после обрезания: " + fileChannel.size());

        }
    }

    private static void readToAppendWriteFromBuffer() throws IOException {
        /* READ TO BUFFER */
        ByteBuffer buffer;

        try (FileInputStream inputStream = new FileInputStream(testFile2)) {
            try (FileChannel fileChannel = inputStream.getChannel()) {
                buffer = ByteBuffer.allocate((int) fileChannel.size());
                fileChannel.read(buffer);
                System.out.println(new String(buffer.array()));
            }
        }

        /* APPEND WRITE FROM BUFFER */
        try (FileOutputStream outputStream = new FileOutputStream(testFile2, true)) {
            try (FileChannel fileChannel = outputStream.getChannel()) {
                buffer.flip();
                fileChannel.write(buffer);
            }
        }
    }

    private static void mapping() throws IOException {
        /* READ-WRITE MODE */
        try (RandomAccessFile raf = new RandomAccessFile(testFile2, "rw")) {
            try (FileChannel fileChannel = raf.getChannel()) {
                fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileChannel.size());
                ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());
                fileChannel.read(buffer);
                buffer.position(1);
                buffer.putChar('F');
                buffer.rewind();
                fileChannel.write(buffer);
            }
        }
    }

    private static void transferingCopy() throws IOException {
        try (FileInputStream inputStream = new FileInputStream(testFile2);
             FileOutputStream outputStream = new FileOutputStream(testFile3)) {
            try (FileChannel inChannel = inputStream.getChannel();
                 FileChannel outChannel = outputStream.getChannel()) {
                inChannel.transferTo(0, inChannel.size(), outChannel);
            }
        }
    }
}