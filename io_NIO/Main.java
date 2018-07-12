package io_NIO;

import java.io.IOException;
import types_references_annotations.my_annotations.Ntrstn;

/* - Работает с байтами
 *      - Файловый канал можно получить от FileInputStream, FileOutputStream или _RandomAccessFile,
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
 * - мапинг файлов в память - возможность создавать и изменять файлы, которые слишком велики для
 * прямого размещения в памяти. Работа идет с файлом просто как с очень большим массивом
 *
 *
 *
 *  * - здесь операции io идут с каналами и буферами
 *          - канал: угольная шахта
 *              - либо наполняет вагонетку углем, либо извлекает из нее
 *          - буфер: вагонетка, которая посылается в шахту
 *              - (при чтении) возвращается наполненная углем, который я выгружаю
 *                  - т.е. я работаю непосредственно только с буферами
 * */



@Ntrstn("Разница между io и NIO ")

public class Main {
    public static void main(String[] args) throws IOException {

    }
}