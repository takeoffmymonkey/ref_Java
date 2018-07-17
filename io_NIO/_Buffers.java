package io_NIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/* ИЕРАРХИЯ
 * - Buffer: контейнер для данных определенного примитивного типа (позиция, лимит и вместимость;
 * очистить, перевернуть, перемотать, (пере)установить метку)
 *      - ByteBuffer: байтовый буфер (get/put, compact, views; аллоцировать, обернуть)
 *          - MappedByteBuffer: прямой байтовый буфер, чье содержание - замапленная в памяти
 *          область файла
 *      - CharBuffer: Char буфер (get/put, compact, views; аллоцировать, обернуть)
 *      - DoubleBuffer: Double буфер (get/put, compact, views; аллоцировать, обернуть)
 *      - FloatBuffer: Float буфер (get/put, compact, views; аллоцировать, обернуть)
 *      - IntBuffer: Int буфер (get/put, compact, views; аллоцировать, обернуть)
 *      - LongBuffer: Long буфер (get/put, compact, views; аллоцировать, обернуть)
 *      - ShortBuffer: Short буфер (get/put, compact, views; аллоцировать, обернуть)
 * - ByteOrder: типобезопасное перечислени для порядков байтов */


/* КЛАСС JAVA.NIO.BUFFER
 * - public abstract class Buffer extends Object
 *
 * - контейнер для фиксированного количества данных определенного примитивного типа
 *
 * - кроме контента, есть:
 *      - capacity: количество содержащихся внутри элементов
 *          - не может быть отрицательной или меняться
 *      - limit: индекс первого элемента, который не должен быть считан/записан
 *          - не может быть отрицательной или больше вместимости
 *      - position: индекс следующего элемента, который будет считан/записан
 *          - не может быть отрицательной или больше лимита
 *
 * - установка метки (mark) и переустановка в метку
 *      - mark: индекс, куда будет переустановлена позиция при вызове метода reset
 *      - не может быть отрицательной и превышать позицию
 *      - может быть не установленной
 *          - тогда вызов reset приведет к InvalidMarkException
 *      - игнорируется, если позиция или лимит изменяются до меньшего значения, чем метка
 *
 * - новый буфер:
 *      - всегда имеет позицию 0 и неопределенную метку
 *      - лимит может быть 0 или больше - в зависимости от типа буфера и способа его создания
 *      - каждый элемент инициализирован нулем
 *
 * - очистка, переворот и перемотка
 *      - clear: подготавливает буфер для записи в него
 *          - устанавливает лимит к вместимости и позицию к 0
 *      - flip: подготавливает буфер для чтения из него
 *          - устанавливает лимит к текущей позиции и затем позицию к 0
 *      - rewind: подготавливает буфер для повторного чтения данных, которые в нем уже содержаться
 *          - оставляет лимит без изменений и устанавливает позицию к 0
 *
 * - read-only буферы:
 *      - каждый буфер можно прочитать, но не каждый записать
 *          - узнать методом isReadOnly
 *      - методы изменения являются опциональными и выбрасывают ReadOnlyBufferException для read-only
 *      буферов
 *      - но можно менять метку, позицию и лимит
 *
 * - не являются потокобезопасными
 *
 * - многие методы возвращают сам буфер, позволяя делать цепочные операции:
 *      - напр. b.flip().position(23).limit(42);
 *
 * - методы:
 *      - array: массив, который представляет данный буфер (опциональная операция)
 *      - arrayOffset: смещение в данном массиве данного буфера для первого элемента (опц. операция)
 *      - capacity: текущая емкость буфера
 *      - clear: очистить буфер, установить позицию в 0, предел - равен емкости; используется для
 *      перезаписи существующего буфера
 *      - flip: предельное значение = позиция, позиция = 0; используется для подготовки буфера к
 *      чтению, после того как в него были записаны данные
 *      - hasArray: есть ли у буфера доступный массив
 *      - hasRemaining: есть ли элементы между текущей позицией и лимитом
 *      - isDirect: является ли буфер прямым
 *      - isReadOnly: является ли буфер read-only
 *      - limit: текущий предел
 *      - limit(int): установка предела
 *      - mark: метка = позиция
 *      - position: текущая позиция
 *      - position(int): установка позиции
 *      - remaining: разница между пределом и позицией
 *      - reset: позиция = метка
 *      - rewind: позиция = 0 */



/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ПРИМИТИВНЫЕ ПОДКЛАССЫ BUFFER~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/* JAVA.NIO.BYTEBUFFER
 * - public abstract class ByteBuffer extends Buffer implements Comparable<ByteBuffer>
 *
 * - определяют 6 категорий операций над своим типом буфера:
 *      - абсолютные(с указанием индекса) и относительные (без индекса) методы get/put для чтения/
 *      записи 1 байта
 *
 *      - относительные bulk get методы (с предоставлением массива), которые переносят
 *      последовательности байтов из этого буфера в массив
 *
 *      - относительные bulk put методы (с предоставлением массива), которые переносят
 *      последовательности байтов из этого массива или другого байтового буфера в буфер
 *
 *      - абсолютные и относительные методы get/put для чтения/записи значений другого примитивного
 *      типа с переводом их в/из последовательностей байтов в определенном байтовом порядке
 *          - таких методов нет в других примитивных подклассах
 *
 *      - методы для создания "view" буферов, которые позволяют рассматривать байтов буфер как буфер,
 *      содержащий значения другого примитивного типа
 *          - таких методов нет в других примитивных подклассах
 *
 *      - методы:
 *          - compact: элементы между текущей позицией и лимитом копируются в начало буфера,а
 *          позиция устанавливается в конец скопированного
 *          - duplicate: создание нового буфера с таким же содержанием
 *          - slice: создание нового буфера, который будет начинаться с текущей позиции в данном
 *          буфере
 *
 * - могут создаваться при помощи:
 *      - аллоцирования:
 *          - аллоцирование места для содержимого буфера
 *      - оборачивания существующего массива в буфер
 *      - дублицирования существующего (duplicate)
 *          - в т.ч. его куска (slice)
 *          - в т.ч. как read-only буффер (asReadOnly)
 *
 * - могут быть прямыми или непрямыми:
 *      - прямой:
 *          - JVM пытается проводить с ним нативные I/O операции
 *              - посторается избежать копирования содержимого буфера в/из промежуточного буфера до
 *              (или после) каждого вызова нативной операции I/O ОС
 *          - может быть создан:
 *              - вызовом фабричного метода allocateDirect
 *              - при помощи маппинга области файла в память
 *          - будет иметь несколько большие затраты на (де)аллокацию, чем непрямые буферы
 *          - контент может находится вне кучи, поэтому их влияние на память приложения может быть
 *          неочевидным
 *              - поэтому желательно аллоцировать только для крупных долгоживущих буферов, с
 *              которыми производятся нативные I/O операции
 *          - есть дополнительные файловые операции, определенные в MappedByteBuffer
 *      - непрямой:
 *          - создается при помощи оборачивания
 *      - узнать какой: isDirect
 *
 * - доступ к двоичным данным:
 *      - есть методы для чтения/записи значений других примитивных типов (кроме boolean)
 *          - значения переводятся в/из последовательностей байтов в соответствии с текущим байтовым
 *          порядком
 *              - узнать/установить порядок: методы order
 *                  - представлены классом ByteOrder
 *              - изначальное значение BIG_ENDIAN
 *      - для доступа к гетерогенным данным (последовательностям значений разных типов) есть семейство
 *      абсолютных и относительных методов get/put для каждого типа, напр:
 *          - getFloat()
 *          - getFloat(int index)
 *          - putFloat(float f)
 *          - putFloat(int index, float f)
 *      - для доступа к гомогенным данным (последовательностям значений одинаковых типов) есть
 *      методы для создания "видов" указанного байтового буфера
 *          - "видовый буфер": другой буфер, чей контент содержится в байтовом буфере
 *              - изменения в контенте байтового буфера будут видны в "видовом буфере" и наоборот
 *              - позиции, лимиты и метки этих 2 буферов независимы
 *              - напр. метод asFloatBuffer создает экземпляр FloatBuffer, которые содержит байтовый
 *              массив для которого делаются вызовы
 *              - имеет 3 главных преимущества над семействами специфичных для типа методов get/put
 *                  - индексируется не в понятиях байтов, а в понятиях зависимых от типа размеров
 *                  своих значений
 *                  - предоставляют относительные bult get/put методы, способные переносить смежные
 *                  последовательности между буферами и массивами или другими буферами того же типа
 *                  - потенциально более эффективен, т.к. является прямым - но только если
 *                  внутренний байтовый буфер является прямым
 *              - байтовый порядок фиксирован и является тем, что у байтового буфера в момент
 *              создания "вида"
 *
 * - некоторые методы поддерживают цепочные вызовы:
 *      - напр. bb.putInt(0xCAFEBABE).putShort(3).putShort(45); */


/* ДРУГИЕ ПРИМИТИВНЫЕ БУФЕРЫ
 * - ShortBuffer
 * - IntBuffer
 * - FloatBuffer
 * - DoubleBuffer
 * - LongBuffer
 * - CharBuffer
 *      - имеет дополнительные методы для работы со строками и CharSequence
 *
 * - в отличите от байтовых буферов:
 *      - не могут использоваться как источник/цель для операций I/O
 *      - не могут быть аллоцированы в прямой буфер
 *      - не могут создаваться маппингом области файла прямо в память
 *      - не предоставляют доступ к своему содержимому как к гетерогенной или гомогенной
 *      последовательности бинарных */



/* * - перемещать данные каналов (из и в) допустимо только с помощью байтовых буферов ByteBuffer, а
 * для остальных примитивов можно либо создать отдельный буфер этого типа, либо получать его из
 * байтового буфера посредством методов с префиксом as
 *      - т.е. нет возможности преобразовывать буфер с примитивами к байтовому буферу
 *          - но можно помещать примитивы в байтовый буфер и извлекать их оттуда при помощи
 *          представлений
 * - */


/* не создаются напрямую
        - slice, duplicate, asReadOnly
 * - изменения в обвернутом массиве сохраняются
 * - у charbuff есть дополнительные методы для работы со строками и последовательностями символов
 * - только байтбуфер может работать с файлами
  * */


public class _Buffers {

    static File currentFolder = new File("C:\\git\\ref_Java\\src\\io_NIO\\files\\");
    static File testFile = new File(currentFolder, "temp.txt");

    public static void main(String[] args) throws IOException {
        basicBufferMethods();
        basicPrimitiveBuffersMethods();
        byteBufferRelativeGetPutPrimitiveMethods();

//        wrapWriteReadFile();
    }

    private static void basicBufferMethods() {
        /* СОЗДАНИЕ (НЕ ОТНОСИТСЯ К МЕТОДАМ BUFFER) */
        Buffer buffer = ByteBuffer.allocate(10);

        /* ВМЕСТИМОСТЬ */
        System.out.println("Capacity: " + buffer.capacity());

        /* ЛИМИТ */
        System.out.println("Limit: " + buffer.limit()); // 10
        buffer.limit(9);
        System.out.println("New limit: " + buffer.limit()); // 9

        /* ПОЗИЦИЯ */
        System.out.println("Position: " + buffer.position()); // 0
        buffer.position(8);
        System.out.println("New position: " + buffer.position()); // 8

        /* МЕТКА */
        buffer.position(3);
        buffer.mark();
        buffer.position(8);
        buffer.reset();
        System.out.println("Returned to mark: " + buffer.position());

        /* ОЧИСТКА */
        buffer.clear();
        System.out.println("After clearance new position: " + buffer.position());
        System.out.println("After clearance new limit: " + buffer.limit());

        /* FLIP */
        buffer.position(4);
        System.out.println("Position before flip: " + buffer.position());
        buffer.limit(5);
        System.out.println("Limit before flip: " + buffer.limit());
        buffer.flip();
        System.out.println("Position after flip: " + buffer.position()); // 0
        System.out.println("Limit after flip: " + buffer.limit()); // 4

        /* REWIND */
        buffer.position(4);
        System.out.println("Position before rewind: " + buffer.position());
        buffer.limit(5);
        System.out.println("Limit before rewind: " + buffer.limit());
        buffer.rewind();
        System.out.println("Position after flip: " + buffer.position()); // 0
        System.out.println("Limit after flip: " + buffer.limit()); // 5

        /* DIRECT/READ-ONLY/HASARRAY CHECK*/
        System.out.println("Buffer is direct: " + buffer.isDirect()); // false
        System.out.println("Buffer is read-only: " + buffer.isReadOnly()); // false
        System.out.println("Buffer has array: " + buffer.hasArray()); // false

        /* ПОЛУЧЕНИЕ МАССИВА ОБРАТНО ИЗ БУФЕРА */
        byte[] arr = (byte[]) buffer.array();
        System.out.println("Returned array: " + Arrays.toString(arr));
    }


    private static void basicPrimitiveBuffersMethods() {
        /* МЕТОДЫ СОЗДАНИЯ */
        /* Аллоцирование */
        CharBuffer charBuffer = CharBuffer.allocate(5);
        /* Оборачивание */
        char[] chars = new char[5];
        CharBuffer charBuffer1 = CharBuffer.wrap(chars);

        /* МЕТОДЫ ЗАПИСИ/ЧТЕНИЯ */
        /* Абсолютные методы записи */
        charBuffer.put((char) 2);
        /* Абсолютные методы чтения */
        charBuffer.flip();
        System.out.println(charBuffer.get());
        /* Относительные методы записи */
        charBuffer.clear();
        charBuffer.put(0, 'c');
        /* Относительные методы чтения */
        System.out.println(charBuffer.get(0));
        /* Относительные балковые методы записи */
        chars[0] = 'A'; // изменения в массиве отражаются в обернутом варианте!
        charBuffer1.put(new char[]{'1', '2', '3', '4', '5'});
        /* Относительные балковые методы чтения */
        charBuffer1.flip();
        charBuffer1.get(chars);
        System.out.println(Arrays.toString(chars));

        /* COMPACTING */
        char[] chars2 = "Test".toCharArray();
        CharBuffer charBuffer2 = CharBuffer.wrap(chars2);
        System.out.println(charBuffer2.position());
        charBuffer2.position(2);
        charBuffer2.limit(4);
        charBuffer2.compact(); // s t s t
        System.out.println(Arrays.toString(chars2));

        /* DUPLICATING */
        CharBuffer charBuffer3 = charBuffer2.duplicate();

        /* SLICE */
        charBuffer3.position(2);
        CharBuffer charBuffer4 = charBuffer3.slice(); // начиная с текущей позиции st

        /* ASREADONLYBUFFER */
        CharBuffer charBuffer5 = charBuffer4.asReadOnlyBuffer();
        System.out.println(charBuffer5.isReadOnly()); // true
    }

    private static void byteBufferRelativeGetPutPrimitiveMethods() {
        byte[] bytes = "сука".getBytes(); // [-47, -127, -47, -125, -48, -70, -48, -80]
        System.out.println(Arrays.toString(bytes));
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
//        System.out.println(buffer.position());
        System.out.println(buffer.getChar());
    }


    private static void wrapWriteReadFile() throws IOException {
        /* ЗАПИСЬ */

        try (FileOutputStream outputStream = new FileOutputStream(testFile)) {
            FileChannel out = outputStream.getChannel();

            String s = "not a test";
            byte[] chars = s.getBytes();

            /* ОБОРАЧИВАНИЕ */
            ByteBuffer buffer = ByteBuffer.wrap(chars);

            out.write(buffer);
            out.close();
        }

        /* ЧТЕНИЕ */
        try (FileInputStream inputStream = new FileInputStream(testFile)) {
            FileChannel out = inputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) out.size());

            out.read(buffer);
            out.close();

            byte[] bytes = buffer.array();
            String output = new String(bytes);
            System.out.println(output);
        }
    }
}
