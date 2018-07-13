package io_IO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/* ДЕ/СЕРИАЛИЗАЦИЯ - ЧТЕНИЕ/ЗАПИСЬ ДАННЫХ В ПЕРЕНОСИМОЙ ФОРМЕ
 * - для де/сериализации примитивов и строк:
 *      - интерфейсы: DataOutput/DataInput
 *          - их реализации: DataOutputStream/DataInputStream
 *      - методы:
 *          - read/writeInt() и т.д. - для соответствующих примитивов
 *          - read/writeUTF() - для строк
 *
 * - для де/сериализации объектов (в т.ч. массивов и строк):
 *      - сериализируемый объект обязан имплементировать либо java.io.Serializable (маркерный) либо
 *      java.io.Externalizable
 *          - для Serializable процесс де/сериализации происходит автоматически
 *          - для Externalizable процесс можно настроить
 *      - интерфейсы ObjectOutput/ObjectInput
 *          - унаследованы от DataOutput/DataInput
 *              - поэтому позволяют также работать с примитивами
 *          - их реализации ObjectOutputStream/ObjectInputStream
 *      - методы:
 *          - те же, что у DataOutput/DataInput
 *          - read/writeObject() - для объектов, массивов и строк
 *
 * - реализации используются с классами, представляющие соответсвующий поток в источник/цель:
 *      - напр. new DataInputStream(new FileInputStream("file.txt"))
 *
 * - поля transient и static не сериализируются
 *      - при десериализации transient поле будет иметь дефолтное значение
 *
 * - при десериализации:
 *      - необходимо вызывать соответствующие типам методы в обратном порядке
 *      - объекты требуют обратного приведения в оригинальный тип
 *
 * - при де/сериализации объектов:
 *      - при сериализации:
  *         - записывается класс объекта, сигнатура класса и значения non-transient и non-static
  *         полей
 *              - ссылки на другие объекты приводят к записи этих объектов
 *                  - при нескольких ссылках на 1 объект, дополнительно объект не записывается
 *                      - т.н. "механизм общих ссылок", позволяющий корректно воссоздавать графы
 *                      объектов
 *          - желательно указывать версию класса, чтобы не было конфликтов версий при восстановлении
 *              - поле static final long serialVersionUID
 *
 *      - при десериализации:
 *          - производится контроль на существование класса в данной JVM
 *          - ссылки на другие объекты приводят к чтению этих объектов из входного потока по мере
 *          необходимости
 *          - новые объекты всегда аллоцируются в памяти, чтобы избежать перезаписи существующих
 *              - память аллоцируется для объекта и инициализирована в ноль (NULL)
 *              - вызываются безаргументные конструкторы для non-serializable родительских классов,
 *              а затем поля serializable классов восстанавливаются из потока, начиная с serializable
 *              класса, ближайшего к java.lang.object и заканчивая самым специфичным классом объекта */



/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ИНТЕРФЕЙСЫ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/* JAVA.IO.DATAOUTPUT
 * - public interface DataOutput
 *
 * - имплементируется DataOutputStream, FileCacheImageOutputStream, FileImageOutputStream,
 * ImageOutputStreamImpl, MemoryCacheImageOutputStream, ObjectOutputStream, RandomAccessFile
 *
 * - конвертирует данные из любого примитива в серию байтов и пишет их в двоичный поток
 *      - также можно конвертировать строку в модифицированный UTF-8 формат и записывать
 *      результирующую серию байтов
 *
 * - выбрасывает IOException если байт не может быть записан
 *
 * - методы:
 *      - write(byte[] b)
 *      - write(byte[] b, int off, int len)
 *      - write(int b)
 *      - writeBoolean(boolean v)
 *      - writeByte(int v)
 *      - writeBytes(String s)
 *      - writeChar(int v)
 *      - writeChars(String s)
 *      - writeDouble(double v)
 *      - writeFloat(float v)
 *      - writeInt(int v)
 *      - writeLong(long v)
 *      - writeShort(int v)
 *      - writeUTF(String s) - пишет 2 байта информации в исходящий поток, после чего идет
 *      модифицированная UTF-8 репрезентация каждого символа в строке
 *
 * - модифицированный UTF-8: отличия от стандартного:
 *      - null байт '\u0000' кодируется в 2-байтовый формат, а не 1-байтовый, чтобы у кодированной
 *      строки никогда не было встроенных nulls
 *      - используются только 1-байтовые, 2-байтовые и 3-байтовые форматы
 *      - дополнительные символы представлены в виде суррогатных пар */


/* JAVA.IO.DATAINPUT
 * - public interface DataInput
 *
 * - имплементируется DataInputStream, FileCacheImageInputStream, FileCacheImageOutputStream,
 * FileImageInputStream, FileImageOutputStream, ImageInputStreamImpl, ImageOutputStreamImpl,
 * MemoryCacheImageInputStream, MemoryCacheImageOutputStream, ObjectInputStream, RandomAccessFile
 *
 * - производит чтение байтов из бинарного потока и реконструирует данные из него в примитивы
 *      - также можно реконструировать текстовые данные в модифицированный UTF-8 формат.
 *
 * - обычно при чтении, если конец файла наступает раньше, чем считано желаемое количество байтов,
 * выбрасывается EOFException
 *      - если чтение невозможно по другим причинам, то выбрасывается IOException
 *          - например, при закрытии входного канала
 *
 * - методы:
 *      - readBoolean()
 *      - readByte()
 *      - readChar()
 *      - readDouble()
 *      - readFloat()
 *      - readFully(byte[] b)
 *      - readFully(byte[] b, int off, int len)
 *      - readInt()
 *      - readLine()
 *      - readLong()
 *      - readShort()
 *      - readUnsignedByte()
 *      - readUnsignedShort()
 *      - readUTF()
 *      - skipBytes(int n) */


/* JAVA.IO.OBJECTOUTPUT
 * - public interface ObjectOutput extends DataOutput, AutoCloseable
 *
 * - имплементируется ObjectOutputStream
 *
 * - расширяет DataOutput для возможности писать объекты
 *      - т.е. кроме примитивов, можно писать:
 *          - объекты
 *          - массивы
 *          - строки
 *
 * - дополнительные методы:
 *      - writeObject(Object obj): запись объект в поток */


/* JAVA.IO.OBJECTINPUT
 * - public interface ObjectInput extends DataInput, AutoCloseable
 *
 * - имплементируется ObjectInputStream
 *
 * - расширяет DataInput для возможности читать объекты
 *      - т.е. кроме примитивов, можно писать:
 *          - объекты
 *          - массивы
 *          - строки
 *
 * - дополнительные методы:
 *      - available(): число байтов, которые можно прочитать без блокирования
 *      - readObject() */


/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~РЕАЛИЗАЦИИ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/* JAVA.IO.DATAOUTPUTSTREAM
 * - public class DataOutputStream extends FilterOutputStream implements Closeable, DataOutput,
 * Flushable, AutoCloseable
 *
 * - запись примитивов в исходящий поток данных переносимым способом
 *      - приложение потом может считать их обратно при помощи входящего потока данных
 *
 * - дополнительные методы:
 *      - flush(): сливает данные в исходящий поток
 *      - size(): число записанных на данный момент байтов в поток */


/* JAVA.IO.DATAINTPUTSTREAM
 * - public class DataInputStream extends FilterInputStream implements Closeable, DataInput,
 * AutoCloseable
 *
 * - чтение примитивов из входного потока данных переносимым способом
 *      - приложение использует входной поток данных для получения данных
 *
 * - необязательно безопасен для многопоточного доступа */


/*JAVA.IO.OBJECTOUTPUTSTREAM
 * - public class ObjectOutputStream extends OutputStream implements Closeable, DataOutput, Flushable,
 * ObjectOutput, ObjectStreamConstants, AutoCloseable
 *
 * - пишет примитивы и графы объектов в OutputStream */


/*JAVA.IO.OBJECTINPUTSTREAM
 * - public class ObjectInputStream extends InputStream implements Closeable, DataInput, ObjectInput,
 * ObjectStreamConstants, AutoCloseable
 *
 * - десериализирует примитивы и объекты, раннее записанные при помощи ObjectOutputStream */


public class Serialization {
    static File currentFolder = new File("C:\\git\\ref_Java\\src\\io_IO\\files\\");
    static File testFile = new File(currentFolder, "serialization_primitives.txt");
    static File testFile2 = new File(currentFolder, "serialization_objects");


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        writeReadPrimitives();
        writeReadObjects();
    }


    private static void writeReadPrimitives() throws IOException {
        /* Writing */
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(testFile))) {
            out.writeByte(1);
            out.writeInt(222);
            out.writeBoolean(true);
            out.writeUTF("test");
        }

        /* Reading */
        try (DataInputStream in = new DataInputStream(new FileInputStream(testFile))) {
            System.out.println(in.readByte());
            System.out.println(in.readInt());
            System.out.println(in.readBoolean());
            System.out.println(in.readUTF());
        }
    }


    private static void writeReadObjects() throws IOException, ClassNotFoundException {
        /* Writing */
        MyObject obj = new MyObject();
        obj.i = 222;
        obj.iTr = 777; // будет 0
        obj.iSt = 444; //

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(testFile2))) {
            out.writeObject(obj);
        }

        /* Reading */
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(testFile2))) {
            MyObject object = (MyObject) in.readObject();
            System.out.println(object.i);
            System.out.println(object.iTr);
            System.out.println(object.iSt);
        }
    }
}


class MyObject implements Serializable {
    int i = 111;
    transient int iTr = 666;
    static int iSt = 333;
    String s = "hello, bitch";
}