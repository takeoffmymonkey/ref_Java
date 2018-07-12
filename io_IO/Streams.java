package io_IO;

/* ПОТОКИ (STREAMS) - ПОСЛЕДОВАТЕЛЬНОСТЬ ДАННЫХ, СЧИТЫВАЕМЫХ ИЗ ИСТОЧНИКА ИЛИ ЗАПИСЫВАЕМЫХ В ЦЕЛЬ
 * - источником/целью могут быть, например:
 *      - файлы на диске
 *      - другие устройства
 *      - другие программы
 *      - массивы памяти
 *      - и т.д.
 *
 * - поддерживают разные виды данных:
 *     - биты (напр. DataInputStream/DataOutputStream)
 *     - простые байты (напр. FileInputStream/FileOutputStream)
 *     - примитивы (напр. DataInputStream/DataOutputStream или ObjectInputStream/ObjectOutputStream)
 *     - локализированные символы (Unicode) (напр. FileReader/FileWriter)
 *     - объекты (напр. ObjectInputStream/ObjectOutputStream)
 *          - если записываются объекты, которые содержат другие объекты - они записываются все
 *              - объект дважды не записывается - записывается только ссылка на него
 *
 * - в отличие от каналов:
 *      - являются однонаправленными:
 *          - либо входящий (InputStream)
 *          - либо исходящий (OutputStream)
 *      - не могут быть закрытыми
 *          - после закрытия перестают существовать
 *      - после использования обязательно закрывать
 *      - манипулируют байтами, а не буферами (напр. с байтами) */




/*Поток
 * [РЕСУРС ДАННЫХ] -> -> (входящий поток) -> -> [ПРОГРАММА] -> -> (исходяший поток) -> -> [ЦЕЛЬ ДАННЫХ]
 * - Чтение с потока:
 *      - идентифицировать ресурс (напр. файл, строка, массив, сетевое подключение)
 *      - построить входящий поток при помощи идентифицированного ресурса
 *          - поток сразу предоставляется программе для чтения, его не нужно отдельно открывать
 *      - прочитать данные из входящего потока
 *          - обычно в цикле, 1 единица данных за другой, до уведомления о конце потока
 *      - закрыть входящий поток
 *
 * - Запись в поток:
 *      - идентифицировать цель (напр. файл, строка, массив, сетевое подключение)
 *      - построить исходящий поток при помощи идентифицированной цели
 *          - поток сразу предоставляется программе для записи, его не нужно отдельно открывать
 *      - записать данные в исходящий поток
 *      - закрыть поток
 *
 * - потоки основаны на Декораторе
 * */



/* Для эффективности, необходимо передавать байты/символы не по 1, а блоками
 * - для байтов: BufferedInputStream и BufferedOutputStream
 * - для символов: BufferedReader и BufferedWriter
 *
 * - входной поток считывает данные из буфера в памяти, когда буфер заполнен
 *
 * - выходной поток записывает данные в буфер, ко гда буфер заполнен
 *
 * - иногда необходимо очищать (flush) буфер, прежде чем он заполнится:
 *      - некоторые классы буферизированного вывода поддерживают autoflush
 *          - передается дополнительным аргументом в конструктор
 *      - срабатывает при наступлении определенных событий
 *          - напр. в PrintWriter autoflush срабатывает при каждом вызове println или format
 *      - запустить вручную: flush()
 *          - метод валиден для любого выходного потока
 *              - но только если он буферизирован
 *
 * - например, строками:
 *      - символ возвращения каретки\line-feed sequence: "\r\n"
 *      - символ возвращения каретки: "\r"
 *      - line-feed sequence: "\n" */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Streams {

    public static void main(String[] args) throws IOException {
        byteStreamCopy();
        charStreamCopy();
        charLineStreamCopy();
    }


    static void byteStreamCopy() throws IOException {
        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream("src\\io_io_and_streams\\byte_test_src.txt");
            out = new FileOutputStream("src\\io_io_and_streams\\byte_test_dest.txt");

            int c;

            while ((c = in.read()) != -1) {
                out.write(c);
            }

        } finally {
            if (in != null) {
                out.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }


    static void charStreamCopy() throws IOException {
        FileReader in = null;
        FileWriter out = null;

        try {
            in = new FileReader("src\\io_io_and_streams\\char_test_src.txt");
            out = new FileWriter("src\\io_io_and_streams\\char_test_dest.txt");

            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }

        } finally {
            if (in != null) {
                out.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    static void charLineStreamCopy() throws IOException {
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new FileReader("src\\io_io_and_streams\\char_line_test_src.txt"));
            out = new PrintWriter(new FileWriter("src\\io_io_and_streams\\char_line_test_dest.txt"));

            String l;
            while ((l = in.readLine()) != null) {
                out.println(l); // может не совпадать с разделителем строки, который был изначально
            }

        } finally {
            if (in != null) {
                out.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

}
