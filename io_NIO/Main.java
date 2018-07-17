package io_NIO;

import java.io.IOException;

import types_references_annotations.my_annotations.Ntrstn;

/* ОПЕРАЦИИ В NIO ПРОИСХОДЯТ С КАНАЛАМИ И БУФЕРАМИ. АНАЛОГИИ:
 * - канал: угольная шахта
 *      - либо наполняет вагонетку углем, либо извлекает из нее
 *
 * - буфер: вагонетка, которая посылается в шахту
 *      - (при чтении) возвращается наполненная углем, который я выгружаю
 *          - т.е. я работаю непосредственно только с буферами */


/* ПАКЕТ JAVA.NIO
 * - определяет классы буферов и описывает ключевые абстракции NIO
 *
 * - ключевые абстракции NIO:
 *      - буферы: контейнеры для данных
 *          - пакет java.nio
 *
 *      - charsets: наборы символов и ассоциированные с ними кодеры/декодеры, которые переводят в/из
 *      символы Unicode
 *          - пакет java.nio.charset
 *
 *      - каналы: каналы разных типов, которые представляют связи между сущностями, способными
 *      производить I/O операции
 *          - пакет java.nio.channels
 *
 *      - селекторы и ключи селекции: вместе с selectable каналами определяют удобство для
 *      мультиплексированного неблокирующего I/O
 *          - пакет java.nio.channels
 *
 * - в каждом из подпакетов java.nio есть свой пакет с сервис-провайдером (SPI)
 *      - их можно использовать для расширения дефолтных имплементаций
 *
 * - иерархия:
 *      - Buffer: контейнер для данных определенного примитивного типа (позиция, лимит и вместимость;
 *      очистить, перевернуть, перемотать, (пере)установить метку)
 *          - ByteBuffer: байтовый буфер (get/put, compact, views; аллоцировать, обернуть)
 *              - MappedByteBuffer: прямой байтовый буфер, чье содержание - замапленная в памяти
 *              область файла
 *          - CharBuffer: Char буфер (get/put, compact, views; аллоцировать, обернуть)
 *          - DoubleBuffer: Double буфер (get/put, compact, views; аллоцировать, обернуть)
 *          - FloatBuffer: Float буфер (get/put, compact, views; аллоцировать, обернуть)
 *          - IntBuffer: Int буфер (get/put, compact, views; аллоцировать, обернуть)
 *          - LongBuffer: Long буфер (get/put, compact, views; аллоцировать, обернуть)
 *          - ShortBuffer: Short буфер (get/put, compact, views; аллоцировать, обернуть)
 *      - ByteOrder: типобезопасное перечислени для порядков байтов */






/* - Работает с байтами
 *      - Файловый канал можно получить от FileInputStream, FileOutputStream или RandomAccessFile,
 *      которые были для этого переделаны
 * - для работы не с байтами (через Writer/Reader) есть вспомогательный класс java.nio.Channels с
 * набором методов, производящих Writer/Reader
 *
 * - интересная запись:
 *      FileChannel
 *          in = new FileInputStream(),
 *          out = new FileOutputStream();
 *
 * - копирование и запись можно делать методами transferTo и transferFrom класса FileChannel
 *      - in.transferTo(0, in.size(), out);
 *      - часто выполнять эту операцию не придется
 *
 *
 *  *
 * */


public class Main {
    public static void main(String[] args) throws IOException {

    }
}