package unicode;

import java.util.Arrays;

import static java.lang.Character.*;

/* СНАЧАЛА БЫЛ ASCII
 * - 8 бит (1 байт)
 * - первые 7 бит (0..127) для английских символов
 * - остальные были у каждого языка свои, что создало кучу вариантов кодировок*/


/* ЗАТЕМ ПОЯВИЛСЯ UNICODE, КОТОРЫЙ ОБЪЕДИНИЛ ВСЕ СИМВОЛЫ
 * - был 16 бит (2 байта): от 0000 до FFFF (0..65535)
 * - напр U+FEC9 (U+ это Unicode, FEC9 это 16-ричное число)
 * - каждый такой символ это кодовая точка
 * - первые 127 значений в Unicode те же, что и в ASCII
 * - в Java для записи символов в Unicode есть специальный префикс \ю, а следующие 4 16-ричные цифры
 * задают сам символ. Напр, \u2122
 * - символы ? и квадратики появляются, когда в целевой кодировке нет соответствующего символа из
 * Unicode
 * - диапазон кириллицы: 0x0400 до 0x04FF
 * - 16-битный char нормально вмещал весь диапазон
 * - сама концепция позволяет быть расширенным за пределы 2 байт*/


/* UNICODE РАСШИРИЛСЯ И В JAVA ПОЯВИЛИСЬ 32-БИТНЫЕ КОДОВЫЕ ТОЧКИ
 * - Unicode был расширен от 0 до 10FFFF (3 байта?) (0..1114111)
 * - c Java 2 5.0 типы Character и String стали поддерживать 32-битные символы Unicode
 * - в Java:
 *     + "кодовая точка" - символ от 0 до 10FFFF
 *     + "кодовая единица" - символ от 0 до FFFF (т.е. только 16 бит или 2 байта)
 *     + "дополнительные символы" - символы выше FFFF
 * - для представления дополнительного символа используется 2 значения типа char:
 *     + "верхний суррогат" - первое значение char
 *     + "нижний суррогат" - второе значение char
 *     + были разработаны новые методы (напр. codePointAt()) для преобразований кодовых точек в
 *     дополнительные символы и обратно
 *     + были перегружены некоторые из существовавших ранее методов (напр. isDigit(), isLetter(),
 *     isSpaceChar(), toUpperCase(), toLowerCase()) в классах Character и String с использованием
 *     типа int вместо char, так как он легко может вместить любой символ
 *     + в класс String также добавлен конструктор, поддерживающий расширенный набор символов Unicode:
 *     String(int[] codePoints, int startIndex, int numChars), где codePoints – это массив,
 *     содержащий кодовые точки. Формируется результирующая строка протяженностью numChars, начиная
 *     с позиции startIndex.*/


/* РАЗНОВИДНОСТИ КОДИРОВОК UNICODE
 * - Big и Little Endian: определяют, какой байт спереди
 *    + Big Endian - большой (напр. 041f)
 *    + Little Endian - маленький (напр. 1f04)
 *    + нужны для разных процессоров (зависит скорость работы)
 *    + чтобы указать какой спереди, используется сигнатура byte order mark (BOM) - строка
 *    начинается с \uFFFE (Big Endian) или \uFEFF (Little Endian)
 *    + если Windows обменивается данными с UNIX-сервером, который использует Big Endian, 1 из
 *    систем должна осуществлять перекодировку: выбрать 1 из вариантов:
 *      - порядок байт канонически должен быть Big Endian
 *      - либо начинать каждую строку с \uFEFF (предпочтительней)
 *
 * - UTF-8: кодировка с переменным количеством байт
 *    + 0x0000 – 0x007F (интервал ASCII) используется только 1 байт 0xxxxxxx
 *    + 0x0080 – 0x07FF используется 110xxxxx 10xxxxxx
 *    + 0x0800 – 0xFFFF используется 1110xxxx 10xxxxxx 10xxxxxx
 *    + т.е.:
 *      - если байт начинается с нулевого бита, то это 1-байтовый символ ASCII
 *      - если байт начинается с 11…, то это стартовый байт несколькобайтовой последовательности,
 *      кодирующей символ, число головных единичек которого равно количеству байт в последовательности.
 *      - если байт начинается с 10…, то это серийный «транспортный» байт из последовательности
 *      байтов, количество которых было определено стартовым байтом.
 *      - биты Unicode символа упаковываются в «транспортные» биты стартового и серийных байтов,
 *      обозначенные выше как последовательность «xx..x»*/


/* JVM ПОДДЕРЖИВАЕТ СЛЕДУЮЩИЕ КОДИРОВКИ:
 *  US-ASCII: семибитная ASCII, она же ISO646-US, она же основная латинская часть Unicode
 *  ISO-8859-1: то же самое, что ISO-LATIN-1;
 *  UTF-8: 8-битный
 *  UTF-16BE: 16-битный, порядок байт big-endian;
 *  UTF-16LE: 16-битный, порядок байт little-endian;
 *  UTF-16: 16-битный, порядок байт определяется начальными значениями (допускается любой), на
 *  выходе порядок байт big-endian*/


/* Многие думают, что задание русских символов в Unicode формате с помощью префикса \ю в исходном
 * коде программы может решить проблему вывода русских символов при любых обстоятельствах. Ведь
 * виртуальная машина Java переводит исходный код программы в Unicode. Однако прежде чем осуществить
 * перевод в Unicode виртуальная машина должна знать, в какой кодировке написан исходный код вашей
 * программы. Ведь вы можете написать программу и в кодировке Cp866 (DOS) и в Сp1251 (Windows), что
 * типично для данной ситуации. В том случае, если вы не указали кодировки, виртуальная машина Java
 * считывает ваш файл с исходным кодом программы в той кодировке, которая указана в системном
 * свойстве file.encoding
 *
 * Программисту прежде всего следует знать: не имеет смысла иметь строку, не зная, какую кодировку
 * она использует. Не существует такого понятия как "простой" (plain) текст в ASCII. Если у вас есть
 * строка, в памяти, в файле, или в сообщении электронной почты, вы должны знать, в какой она
 * кодировке, иначе вы не сможете ее правильно интерпретировать или показать пользователю.
 *
 * Почти все традиционные проблемы типа "мой вебсайт похож на тарабарщину" или "мои электронные
 * письма не читаются, если я использую символы с ударениями" лежат на ответственности программиста,
 * который не понимает простого факта, что если вы не знаете в какой кодировке строка UTF-8 или
 * ASCII или ISO 8859-1 (Латинский-1) или Windows 1252 (Западноевропейский), вы просто не сможете
 * вывести ее правильно. Есть более ста кодировок символов выше кодовой точки 127, и нет никакой
 * информации для того, чтобы выяснить, какая кодировка нужна.
 *
 * Как мы сохраняем информацию о том, какую кодировку используют строки? Существуют стандартные
 * способы для указания этой информации. Для сообщений электронной почты вы должны поместить в HTTP
 * заголовок строку
 * Content-Type: text/plain; charset="UTF-8"*/


public class Main {

    public static void main(String[] args) {
        System.out.println("\"Unicode\"");

        /*УЗНАТЬ, СКОЛЬКО ИЗ СКОЛЬКИХ CHAR СОСТОИТ КОДОВАЯ ТОЧКА*/
        System.out.println("Количество char в кодовой точке 'Ж': " + charCount('Ж'));// 1


        /*УЗНАТЬ НОМЕР КОДОВОЙ ТОЧКИ В МАССИВЕ CHAR*/
        System.out.println("Номер кодовой точки: " + codePointAt("ж", 0)); // 1078


        /*УЗНАТЬ, ЕСТЬ ЛИ У КОДОВОЙ ТОЧКИ ДОПОЛНИТЕЛЬНЫЙ СИМВОЛ*/
        System.out.println("Есть ли дополнительный символ? " + isSupplementaryCodePoint('ж')); // false


        /*УЗНАТЬ, СОДЕРЖИТ ЛИ КОДОВАЯ ТОЧКА ДОПУСТИМЫЕ СУРРОГАТЫ*/
        System.out.println("Есть ли верхний суррогат? " + isHighSurrogate('ж')); // false
        System.out.println("Есть ли нижний суррогат? " + isLowSurrogate('ж')); // false


        /*УЗНАТЬ, ЯВЛЯЕТСЯ ЛИ СИМВОЛ ДОПУСТИМОЙ КОДОВОЙ ТОЧКОЙ*/
        System.out.println("Символ является допустимой кодовой точкой? " + isValidCodePoint('ж')); // true



        /*ПЕРЕВЕСТИ КОДОВУЮ ТОЧКУ В CHAR
         * - может понадобиться массив*/
        char[] v = toChars(77777); // вернет массив, так как выход за 2 байта
        System.out.println("Как выглядит кодовая точка 77777? " + Arrays.toString(v));

    }
}


/*ПОЛЕЗНЫЕ МЕТОДЫ CHARACTER
 * - static int charCount(int cp): Возвращает 1, если cp можно представить одним значением типа char.
 * Возвращает 2, если требуется два значения типа char.
 *
 * - static int codePointAt(char[] chars, int loc)	Возвращает кодовую точку для символьной позиции
 * (location), заданной в параметре loc
 *
 * - static boolean isSupplementaryCodePoint(int cp): Возвращает true, если cp содержит
 * дополнительный символ
 *
 * - static boolean isHighSurrogate(char ch): Возвращает true, если ch содержит допустимый верхний
 * суррогат символа.
 *
 * - static boolean isLowSurrogate(char ch): Возвращает true, если ch содержит допустимый нижний
 * суррогат символа.
 *
 * - static boolean isSurrogatePair(char highCh, char lowCh): Возвращает true, если highCh и lowCh
 * формируют допустимую суррогатную пару.
 *
 * - static boolean isValidCodePoint(int cp): Возвращает true, если cp содержит допустимую кодовую
 * точку.
 *
 * - static char[] toChars(int cp): Преобразует кодовую точку, содержащуюся в cp, в ее эквивалент
 * типа char, который может потребовать двух значений типа char. Возвращает массив, содержащий
 * результат
 *
 * - static int toChars(int cp, char target[], int loc): Преобразует кодовую точку, содержащуюся
 * в cp, в ее эквивалент типа char, запоминает результат в массиве target, начиная с позиции,
 * заданной в loc. Возвращает 1, если cp можно представить одним значением типа char, и 2 в
 * противном случае.
 *
 * - static int toCodePoint(char ighCh, char lowCh): Преобразует highCh и lowCh в их эквивалентные
 * кодовые точки.*/


/*ПОЛЕЗНЫЕ МЕТОДЫ STRING
 * - int codePointAt(int i): Возвращает кодовую точку для позиции в строке, заданной параметром i
 *
 * - int codePointBefore(int i): Возвращает кодовую точку для позиции в строке, предшествующей
 * заданной параметром i
 *
 * - int codePointCount(int start, int end): Возвращает количество кодовых точек в порции вызывающей
 * строки, расположенной между символьными порциями start и end-1
 *
 * - int offsetByCodePoints(int start, int num): Возвращает позицию в вызывающей строке,
 * расположенную на расстоянии num кодовых точек после начальной позиции, заданной параметром start*/