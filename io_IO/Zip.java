package io_IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import types_references_annotations.my_annotations.Ntrstn;


/* ПАКЕТ JAVA.UTIL.ZIP
 * - классы для чтения и записы стандартных файловых форматов ZIP и GZIP
 *      - также классы для компрессии и декомпресси данных при помощи алгоритма DEFLATE,
 *      используемого в форматах ZIP и GZIP
 *      - также утилитные классы для вычисления проверочных сумм CRC-32 и Adler-32 из любых
 *      входных потоков
 *          - класс Adler32 - быстрый
 *          - класс CRC32 - медленный, но гораздо точнее
 *      - можно работать и с несколькими файлами в архиве
 *
 * - классы:
 *      - Adler32: вычисление контрольной суммы Adler-32 из потока данных
 *      - CRC32: вычисление контрольной суммы CRC-32 из потока данных
 *      - CheckedInputStream: метод getCheckSum() возвращает контрольную сумму для любого входного
 *      потока InputStream (не только для потока распаковки)
 *      - CheckedOutputStream: метод getCheckSum() возвращает контрольную сумму для любого выходного
 *      потока OutputStream (не только для потока распаковки)
 *      - DeflaterOutputStream: базовый класс для сжатия данных
 *          - ZipOutputStream: производит сжатие данных в формате ZIP
 *          - GZIPOutputStream: производит сжатие данных в формате GZIP
 *      - InflaterInputStream: базовый класс для классов распаковки сжатых данных
 *          - ZipInputStream: распаковывает сжатые данные, хранящиеся в формате ZIP
 *          - GZIPInputStream: распаковывает сжатые данные, хранящиеся в формате GZIP
 *      - Deflater: поддержка для общецелевой компресии при помощи популярной библиотеки компрессии
 *      ZLIB
 *      - Inflater: то же, но для декомпрессии
 *      - ZipEntry: репрезентация ZIP file entry.
 *      - ZipFile:	чтение entries из zip файла. */

@Ntrstn("Пакет java.util.zip предоставляет классы для чтения и записы стандартных файловых форматов " +
        "ZIP и GZIP, классы для компрессии и декомпресси данных при помощи алгоритма DEFLATE, " +
        "используемого в форматах ZIP и GZIP, а также утилитные классы для вычисления проверочных " +
        "сумм CRC-32 и Adler-32 из любых входных потоков. Некоторые классы в этом пакете " +
        "унаследованы от классов пакета java.io - например, DeflaterOutputStream (базовый класс для " +
        "сжатия данных) унаслован от FilterOutputStream")

@Ntrstn("Работатать можно и с 1 файлом и с несколькими в архиве. Zip архив представлен классом " +
        "ZipFile, а файлы внутри - классом ZipEntry")

public class Zip {

    public static void main(String[] args) throws IOException {
        readFromZip();
    }

    private static void readFromZip() throws IOException {
        ZipFile zipFile = new ZipFile("C:\\git\\ref_Java\\src\\io_IO\\files\\files.zip");
        ZipEntry zipEntry = zipFile.getEntry("1.txt");
        InputStreamReader in2 = new InputStreamReader(zipFile.getInputStream(zipEntry), "UTF8");
        int i;

        while ((i = in2.read()) != -1) {
            System.out.println((char) i);
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(zipFile.getInputStream(zipEntry)));
        System.out.println(in.readLine());
    }
}