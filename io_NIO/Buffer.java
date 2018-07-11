package io_NIO;

/*  БУФЕР (java.nio): контейнер для ограниченного объема данных примитивного типа
 *      - имеет позицию (индекс следующего элемента для записи/чтения)
 *          - ее можно очищать, переворачивать (flip), перематывать (rewind) и помечать, а также
 *          переустанавливать ее на предыдущую метку
 *      - для каждого примитива (кроме boolean) есть класс
 *          - каждый класс определяет семейство методов:
 *              - get/put для доставания/записи данных
 *              - сжатие, дубликация и порезка буфера
 *              - статические методы для аллоцирования нового буфера и обворачивания существующего
 *              массива в буфер
 *      - может использоваться как src и trg для операций io
 *      - имеет дополнительные ф-ции, которых нет в других буферах:
 *          - может аллоцировать прямой буфер, и JVM постарается работать с ним нативно
 *          - может создаваться мапингом куска файла прямо в память, в результате чего становятся
 *          доступными несколько дополнительных файловых операций, определенных в MappedByteBuffer
 *          - предоставляет доступ к своему содержимому как к гетерогенной или гомогенной
 *          последовательности бинарных данных любого небулеанового примитива либо в big-endian либо
 *          в little-endian порядках байтов
 *      - иерархия:
 *          - Buffer: позиция, лимит и вместимость; очистить, перевернуть, перемотать, пометить и
 *          переустановить
 *              - ByteBuffer: получить\положить, сжать, виды; аллоцировать, обернуть
 *                  - MappedByteBuffer: байтовый буфер, замапенный на файл
 *              - Char/Double/Float/Int/Long/ShortBuffer: получить\положить, сжать; аллоцировать,
 *              обернуть
 *          - ByteOrder: типобезопасное перечисление для порядков байтов*/


/* BYTEBUFFER
 * - есть методы для извлечения примитивов из байтов
 *
 * - методы Buffer:
 *      - capacity: текущая емкость буфера
 *      - clear: очистить буфер, установить позицию в 0, предел - равен емкости; используется для
 *      перезаписи существующего буфера
 *      - flip: предельное значение = позиция, позиция = 0; используется для подготовки буфера к
 *      чтению, после того как в него были записаны данные
 *      - limit: текущий предел
 *      - limit(int): установка предела
 *      - mark: метка = позиция
 *      - position: текущая позиция
 *      - position(int): установка позиции
 *      - remaining: разница между пределом и позицией
 *      - hasRemaining: есть ли еще элементы между позицией и пределом
 * */

/* * - буфер:
 *      - состоит из данных и 4 индексов для доступа и манипуляции данными
 *          - mark
 *          - position
 *          - limit
 *          - capacity
 *      - содержит методы для работы с индексами, а также для чтения и записи*/


/* * - после вызова read() буфера FileChannel, чтобы сохранить байты в буфере ByteReader, вы также
 * должны вызвать для буфера метод flip(), позволяющий впоследствии извлечь из буфера его данные
 * (расчет делался на низкоуровневые операции). И если затем снова понадобится буфер для чтения,
 * нужно будет вызывать перед каждым методом read метод clear*/


/* * - перемещать данные каналов (из и в) допустимо только с помощью байтовых буферов ByteBuffer, а
 * для остальных примитивов можно либо создать отдельный буфер этого типа, либо получать его из
 * байтового буфера посредством методов с префиксом as
 *      - т.е. нет возможности преобразовывать буфер с примитивами к байтовому буферу
 *          - но можно помещать примитивы в байтовый буфер и извлекать их оттуда при помощи
 *          представлений
 *

 * - обернуть значит сделать из существующего массива буфер соответствующего типа*/

public class Buffer {
}
