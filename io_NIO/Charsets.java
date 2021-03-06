package io_NIO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import types_references_annotations.my_annotations.Ntrstn;


/* ПАКЕТ java.nio.charset
 * - определяет "наборы символов", и кодеры/декодеры для перевода байтов и символов Unicode
 *      - набор символов (charset) - именной маппинг между последовательностями 16-битных символов
 *      Unicode и последовательностями байтов (определен стандартом RFC 2278)
 *      - декодер (decoder) - движок для перевода байтов в определенный набор символов
 *      - кодер (encoder) - движок для перевода символов в байты
 *
 * - кодер и декодер (т.н. "кодировщики") работают с байтовыми и символьными буферами
 *
 * - иерархия:
 *      - Charset: именнованный мапинг между символами и байтами
 *          - определяет методы для создания кодировщиков для указанного набора символов и для
 *          получения разных имен, ассоциированных с набором
 *          - определяет статические методы для тестирования поддержки определенного чарсета, для
 *          обнаружения экземляров чарсетов по имени и для создания карты, которая содержит все
 *          наборы символов, чья поддержка доступна в текущей JVM
 *      - CharsetDecoder: декодировщик байтов в символы
 *      - CharsetEncoder: кодировщик символов в байты
 *      - CoderResult: описывает результаты кодировки
 *      - CodingErrorAction: описывает действия, которые нужно принять при обнаружении ошибок кодировки
 *
 * - обычно эти классы не используются напрямую, а только в конструкторах и методах классов String,
 * InputStreamReader and OutputStreamWriter
 *
 * - при помощи java.nio.charset.spi можно расширить поддержку для новых чарсетов (класс CharsetProvider)*/


/* КЛАСС java.nio.charset
 * - public abstract class Charset extends Object implements Comparable<Charset>
 *
 * - именованный маппинг между последовательностью символов и 16-битных кодовых юнитов и
 * последовательностью байтов
 *
 * - экземпляры класса являются неизменяемыми
 *
 * - определяет методы:
 *      - для создания кодировщиков и для получения различных имен, ассоциированных с
 *      чарсетом
 *      - для тестирования поддержки определенного чарсета, для обнаружения экземляров чарсетов по
 *      имени и для создания карты, которая содержит все наборы символов, чья поддержка доступна в
 *      текущей JVM
 *
 * - методы безопасны для многопоточных веток
 *
 * - названия чарсетов
 *      - case-INSENSITIVE
 *      - имеет каноническое имя (метод name)
 *      - может иметь алиасы (метод aliases)
 *      - может иметь историческое имя - это либо каноническое имя либо один из алиасов (метод
 *      getEncoding классов InputStreamReader и OutputStreamWriter)
 *
 * - стандартные чарсеты (имплементируются любой JVM):
 *      - виды:
 *          - US-ASCII: 7-битный ASCII, a.k.a. ISO646-US, a.k.a. Basic Latin блок чарсета Unicode
 *          - ISO-8859-1: ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1
 *          - UTF-8: 8-битный UCS Transformation Format
 *          - UTF-16BE:	16-битный UCS Transformation Format, порядок байтов big-endian
 *          - UTF-16LE: 16-битный UCS Transformation Format, порядок байтов little-endian
 *          - UTF-16: 16-битный UCS Transformation Format, порядок байтов идентифицируется
 *          опциональной пометкой
 *      - у каждой JVM есть дефолтный чарсет, который может и не быть стандартным
 *          - определяется при запуске и обычно зависит от локали и чарсета, используемого в ОС
 *      - константы чарсетов в классе StandardCharsets
 *
 * - пометка байтового порядка:
 *      - при декодировании, чарсеты UTF-16BE и UTF-16LE интерпретируют начальные пометки байтового
 *      порядка как ZERO-WIDTH NON-BREAKING SPACE
 *          - при кодировании пометки не записываются
 *      - при декодировании, чарсет UTF-16 интерпретирует пометки байтового порядка в начале входного
 *      потока для определения байтового порядка потока, но если пометки нет, то дефолтом является
 *      big-endian
 *          - при кодировании используется байтовый порядок big-endian и записывается пометка big-endian
 *      - пометки байтового порядка, обнаруженные после первого элемента входящей последовательности
 *      не пропускаются, так как такой же код используется для представления ZERO-WIDTH NON-BREAKING
 *      SPACE */


/* ТЕРМИНОЛОГИЯ КОДИРОВОК:
 * - кодированный набор символов: маппинг между набором абстрактных символов и набором чисел
 *      - например: US-ASCII, ISO 8859-1, JIS X 0201, и Unicode
 *      - бывают и просто наборы символов, например азбука
 *          - т.е. без привязки чисел
 *          - но обычно используется именно кодированный
 *
 * - схема кодировки символов: маппинг между 1 или более кодированным набором символов и набором
 * 8-битовых последовательностей
 *      - например: UTF-8, UTF-16, ISO 2022, и EUC
 *      - схемы часто ассоциируются с определенными кодированными наборами символов
 *          - например UTF-8 используется только с Unicode
 *      - некоторые схемы ассоциируются с несколькими кодированными наборами символов:
 *          - например EUC используется для кодирования символов в разных азиатских кодированных
 *          наборах символов
 *
 * - чарсет - комбинация 1 или более кодированного набора символов и схемы кодировки символов
 *      - несколько запутанно, т.к. некоторые ОС определяют чарсеты как синоним кодированного набора
 *      символов
 *      - когда кодированный набор символов эксклюзивно используется только с одной схемой кодировки
 *      символов, то чарсет обычно называет в честь кодированного набора символов
 *          - иначе чарсет называет в честь схемы кодировки символов, и, возможно, локали
 *          кодированных наборов символов, которые он поддерживает
 *      - т.е. US-ASCII это и имя кодированного набора символов и чарсета, который его кодирует, а
 *      EUC-JP - имя чарсета, который кодирует кодированные наборы символов JIS X 0201, JIS X 0208,
 *      и JIS X 0212 для японского языка
 *
 * - стандартной кодировкой символов в Java является UTF-16
 *     - т.е. чарсет в Java определяет маппинг между последовательностью последовательностью
 *     16-битных кодовых юнитов UTF-16 (т.е. последовательность chars) и последовательностью байтов*/


@Ntrstn("В терминологии есть некоторая путанница - понятием charset стандарт RFC 2278 (и " +
        "соответственно Java) называет схему кодировки символов, в то время как некоторые ОС могут " +
        "charset называть кодированные наборы символов. " +
        "" +
        "1 - Кодированный набор символов (coded character set): мапинг между набором абстрактных " +
        "символов и набором чисел. Т.е. какой символ какому числу соответствует, напр. (A-1), (B-2). " +
        "Реальные примеры: US-ASCII, ISO 8859-1, Unicode." +
        "" +
        "2 - Схема кодировки символов (character-encoding scheme): маппинг между 1 (или более) " +
        "кодированным набором символов и набором 8-битных последовательностей. Т.е. какой символ из " +
        "данного набора символов какому байту соответствует, напр. (A-00000001), (B-00000002). " +
        "Реальные примеры: UTF-8, UTF-16, ISO 2022. Схема часто ассоциируется с определенным " +
        "кодированным набором символов: например, UTF-8 используется только для кодирования Unicode, " +
        "но иногда схема может использоваться и для кодирования нескольких наборов символов." +
        "" +
        "Когда кодированный набор символов используется только с 1 схемой кодировки символов, то " +
        "соответвующий charset обычно называется в честь кодированного набора символов. Иначе, " +
        "charset обычно называется в честь схемы кодировки, и, возможно, локали кодированного набора " +
        "символов, который она поддерживает. Например, US-ASCII - это название и кодированного набора " +
        "символов и название charset, который его кодирует, в то время как EUC-JP - это название " +
        "charset, который кодирует наборы кодированных символов JIS X 0201, JIS X 0208, и JIS X " +
        "0212 для японского языка." +
        "" +
        "Родная схема кодировки в Java - UTF-16. Т.е. в Java charset значит маппинг между " +
        "последовательностью 16-битных кодовых точек (т.е. последовательность chars) и " +
        "последовательностью байтов")

@Ntrstn("Для работы с кодировками есть целый пакет java.nio.charset (+ java.nio.charset.spi - если " +
        "нужно расширить существующие имплементации для поддержки новой кодировки). Он определяет " +
        " - набор символов (класс Charset) - именной маппинг между последовательностями 16-битных " +
        "символов Unicode и последовательностями байтов (по стандарту RFC 2278)" +
        " - декодер (класс Decoder) - движок для перевода байтов в определенный набор символов" +
        " - кодер (Encoder) - движок для перевода символов в байты ")

@Ntrstn("Напрямую с пакетом java.nio.charset работать, вероятно, не придется, а достаточно будет " +
        "только указывать нужные кодировки при работе с классами String, InputStreamReader и " +
        "OutputStreamWriter (как перечисления класса StandardCharsets)")

@Ntrstn("Класс Charset:" +
        " - определяет методы для создания кодировщиков для указанного набора символов и для " +
        "получения разных названий, ассоциированных с набором (узнать название кодировки можно от " +
        "метода getEncoding классов InputStreamReader и OutputStreamWriter)" +
        " - определяет статические методы для тестирования поддержки определенного чарсета, для " +
        "обнаружения экземляров чарсетов по имени и для создания карты, которая содержит все наборы " +
        "символов, чья поддержка доступна в текущей JVM")

@Ntrstn("Кодеры и декодеры умеют работать с байтовыми и символьными буферами")

@Ntrstn("Класс StandardCharsets предоставляет именнованные константы charset, которые должны " +
        "поддерживаться любой JVM. При этом она запускается с дефолтным charset, который зависит " +
        "от локали и поддержки ОС, и может не быть стандартной")

public class Charsets {

    static File currentFolder = new File("C:\\git\\ref_Java\\src\\io_NIO\\files\\");
    static File testFile = new File(currentFolder, "charsets_test.txt");

    public static void main(String[] args) throws IOException {
        writeWithCharset();
    }

    private static void writeWithCharset() throws IOException {
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                new FileOutputStream(testFile), StandardCharsets.UTF_16LE)) {
            outputStreamWriter.write('ы');
            // в редакторе пишется как 4b04 в 16 битах, и чтобы отобразить, нужно выбрать эту кодировку
        }
    }
}