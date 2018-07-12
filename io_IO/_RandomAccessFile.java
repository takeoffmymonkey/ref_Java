package io_IO;

/* RANDOM ACCESS FILE
 * - implements Closeable, DataInput, DataOutput, AutoCloseable
 * - не является частью иерархии потоков, так как позволяет перемещаться по файлу в обе стороны
 * - по сути похож на пару совмещенных DataInputStream и DataOutputStream, дополненную методами
 * getFilePointer для текущей позиции и length для максимального размера файла
 * - имеет уже встроенную буферизацию
 *
 * */


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

public class _RandomAccessFile {

    public static void main(String[] args) {
        randomAccessFileForReadWrite();
    }

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
