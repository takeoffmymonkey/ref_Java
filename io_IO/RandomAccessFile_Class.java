package io_IO;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import _types_references_annotations.my_annotations.Ntrstn;

/* JAVA.IO.RANDOMACCESSFILE
 * - implements Closeable, DataInput, DataOutput, AutoCloseable
 *
 * - чтение и запись байтов в файл с неупорядоченным доступом
 *
 * - не является частью иерархии потоков, так как позволяет перемещаться по файлу в обе стороны
 *
 * - файл ведет себя как массив байтов, который сохранен в файловой системе
 *      - в данном массиве есть ("файловый") указатель
 *          - получить указатель: getFilePointer
 *          - установить указатель: seek
 *          - операции R/W происходят начиная с него, и он двигается по мере R/W
 *              - если запись превышает предел массива, он автоматически увеличивается
 *              - если чтение доходит до конца файла, но желаемое число байтов еще не считано, то
 *              вызывается EOFException
 *                  - если чтение невозможно по другим причинам, то вызывается IOException
 *
 * - режим чтения/записи указывается флагом в конструкторе:
 *      - "r": read only
 *          - вызов write методов приведет к IOException
 *      - "rw":	read и write
 *          - если файл не существует, будет сделана попытка его создать
 *      - "rws": read и write + каждый апдейт в контенте файла или метаданных синхронно записывается
 *      на устройство хранения
 *      - "rwd": read и write + каждый апдейт в контенте файла синхронно записывается на устройство
 *      хранения
 *
 * - по сути похож на пару совмещенных DataInputStream и DataOutputStream, дополненную методами
 * getFilePointer для текущей позиции и length для максимального размера файла
 *      - методы DataInputStream и DataOutputStream - это методы типа read/writeInt и т.д. */


@Ntrstn("Класс java.io.RandomAccessFile предназначен для чтения и записи байтов в файл с указанием " +
        "нужного места, с которого нужно начинать операцию. По сути похож на пару совмещенных " +
        "DataInputStream и DataOutputStream, дополненную методами getFilePointer для текущей позиции " +
        "и length для максимального размера файла (методы DataInputStream и DataOutputStream - это " +
        "методы типа read/writeInt и т.д.) Он не является частью иерархии потоков, так как позволяет " +
        "перемещаться по файлу в обе стороны")

@Ntrstn("Файл, представленный RandomAccessFile, ведет себя как как массив байтов, который сохранен в " +
        "файловой системе: у него есть указатель и все операции чтения/записи начинаются с него")

@Ntrstn("При открытии файла нужно указать в конструкторе флаг чтения/записи, например rw")

public class RandomAccessFile_Class {

    static File currentFolder = new File("C:\\git\\ref_Java\\src\\io_IO\\files\\");
    static File tempFile = new File(currentFolder, "random_access_primitives_test.txt");
    static File tempFile2 = new File(currentFolder, "output_formatting.txt");

    public static void main(String[] args) throws IOException {
        readWriteByteRandom();
        readWritePrimitivesRandom();
    }

    private static void readWriteByteRandom() throws IOException {
        try (RandomAccessFile rAF = new RandomAccessFile(tempFile2, "rw")) {
            int i;
            while ((i = rAF.read()) != -1) {
                rAF.write(i);
            }
        }
    }

    private static void readWritePrimitivesRandom() throws IOException {
        try (RandomAccessFile rAF = new RandomAccessFile(tempFile, "rw")) {
            rAF.writeByte(1);
            rAF.writeInt(222);
            rAF.writeBoolean(true);
            rAF.writeUTF("test");

            rAF.seek(0);

            System.out.println(rAF.readByte());
            System.out.println(rAF.readInt());
            System.out.println(rAF.readBoolean());
            System.out.println(rAF.readUTF());
        }
    }
}
