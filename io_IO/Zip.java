package io_IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


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
 *          - ZipIntputStream: распаковывает сжатые данные, хранящиеся в формате ZIP
 *          - GZIPIntputStream: распаковывает сжатые данные, хранящиеся в формате GZIP
 *      - Deflater: поддержка для общецелевой компресии при помощи популярной библиотеки компрессии
 *      ZLIB
 *      - Inflater: то же, но для декомпрессии
 *      - ZipEntry: репрезентация ZIP file entry.
 *      - ZipFile:	чтение entries из zip файла. */


public class Zip {

    public static void main(String[] args) throws IOException {
        zipToFile();
    }

    private static void zipToFile() throws IOException {
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