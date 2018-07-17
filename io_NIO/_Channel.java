package io_NIO;

/*КАНАЛЫ: разные типы, которые представляют подключения к сущностям, способным производить io
 * операции
 *      - например, устройство, сетевой сокет, файл, программный компонент
 *      - каналы могут быть открытыми или закрытыми
 *      - являются асинхронно закрываемыми и прерываемыми
 *      - иерархия:
 *          - Channel: связующее звено для io операций
 *              - ReadableByteChannel: может передавать в буфер
 *                  - ScatteringByteChannel: может передавать в последовательность буферов
 *              - WritableByteChannel: может писать из буфера
 *                  - GatheringByteChannel: может писать из последовательности буферов
 *              - ByteChannel: может передавать/писать из буфера
 *                  - SeekableByteChannel: ByteChannel, подключенный к сущности, у которой
 *                  последовательность байтов переменной длины
 *                      - методы для запроса и изменений текущей позиции канала и его размера
 *              - AsynchronousChannel: поддерживает асинхронные операции io
 *                  - AsynchronousByteChannel: может передавать/писать из буфера асинхронно
 *              - NetworkChannel: канал к сетевому сокету,
 *                  - MulticastChannel: может соединять Internet Protocol (IP) multicast groups
 *              - Channels: утилитные методы для взаимодействия каналов с потоками
 *      - файловые каналы:
 *          - FileChannel: читает, записывает, мапит и манипулирует файлами
 *              - создается от статического метода open или вызовом getChannel у FileInputStream,
 *              FileOutputStream или RandomAccessFile
 *          - FileLock: блокировка (куска) файла
 *          - MappedByteBuffer: прямой байтовый буфер, замапенный на кусок файла*/


/* * - чтобы записать в цель:
 *      - нужно записать данные в буфер
 *      - затем передать его в канал
 *      - после этого данные будут из буфера записаны в цель
 *
 * - чтобы прочитать из источника:
 *      - нужно передать буфер в канал
 *      - канал считывает данные в буфер
 *      - затем я считывают данные из буфера
 *
 * - т.е. ключевыми действиями являются запись и чтение данных из буфера*/


/* *      - каналы - трубопроводы, которые передают данные между буферами и сущностями на другом конце
 *          - данные помещаются в буфер, а буфер передается в канал
 *          - или данные забираются в буфер из канала
 *          - позволяют использовать нативные сервисы ОС без оверхеда
 *          - могут работать в блокирующем и неблокирующем режимах: только для
 *          stream-ориентированных потоков, напр. сокетов
 *          - FileChannel: RW канал
 *              - всегда блокирующий
 *              - не могут создаваться напрямую, а через getChannel() на файловом объекте типа
 *              RandomAccessFile, FileInputStream, или FileOutputStream
 *              - потокобезопасен*/


/* RANDOM ACCESS FILE
 * - позволяет непоследовательный доступ (чтение/запись) к содержимому файла
 * - используется интерфейс SeekableByteChannel
 * - получить можно от:
 *     - newByteChannel(Path, OpenOption...)/
 *     - newByteChannel(Path, Set<? extends OpenOption>, FileAttribute<?>...)
 *     - если привести к FileChannel, то можно расширить фунционал
 *
 * - методы:
 *      - position(): текущая позиция канала
 *      - position(long): установка позиции
 *      - read(ByteBuffer): чтение байтов в буфер из канала
 *      - write(ByteBuffer): запись байтов из буфера в канал
 *      - truncate(long): обрезает файл (или другую сущность), подключенный к каналу
 * */


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class _Channel {


    private static void randomAccessFileForReadWrite() {
        Path file = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2\\file_create_test.txt");
        String s = "I was here!\n";
        byte data[] = s.getBytes();
        ByteBuffer out = ByteBuffer.wrap(data);

        ByteBuffer copy = ByteBuffer.allocate(12);

        try (FileChannel fc = (FileChannel.open(file, StandardOpenOption.READ, StandardOpenOption.WRITE))) {
            // Read the first 12 bytes of the io.io_nio_nio2.
            int nread;
            do {
                nread = fc.read(copy);
            } while (nread != -1 && copy.hasRemaining());

            // Write "I was here!" at the beginning of the io.io_nio_nio2.
            fc.position(0);
            while (out.hasRemaining())
                fc.write(out);
            out.rewind();

            // Move to the end of the io.io_nio_nio2.  Copy the first 12 bytes to
            // the end of the io.io_nio_nio2.  Then write "I was here!" again.
            long length = fc.size();
            fc.position(length - 1);
            copy.flip();
            while (copy.hasRemaining())
                fc.write(copy);
            while (out.hasRemaining())
                fc.write(out);
        } catch (IOException x) {
            System.out.println("I/O Exception: " + x);
        }
    }
}
