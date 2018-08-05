package _types_references_strings;

import java.util.Formatter;

import types_references_annotations.my_annotations.Ntrstn;

/* СПЕЦИАЛЬНЫЕ КЛАССЫ ДЛЯ РАБОТЫ СО СТРОКАМИ
 * - в отличие от String, их можно менять без создания нового объекта
 * - нужно использовать, где нужно вносить много изменений в строку (напр. цикл)*/


/* КЛАСС STRINGBUILDER
 * public final class StringBuilder
 * extends Object
 * implements Appendable, CharSequence, Serializable */


/* КЛАСС STRINGBUFFER
 * public final class StringBuffer
 * extends Object
 * implements Appendable, CharSequence, Serializable */


/* STRINGBUILDER VS STRINGBUFFER
 * - StringBuilder:
 *      + быстрее
 *      + методы НЕ потокобезопасны (несинхронизированы)
 *      + добавлен в 1.5.0
 *
 * - StringBuffer:
 *      + медленнее
 *      + методы потокобезопасны (синхронизированы) */


/* КОНСТРУКТОРЫ
 * - StringBuilder(): создает пустой объект емкостью 16 символов
 * - StringBuilder(int capacity): создает пустой объект заданной емкости capacity
 * - StringBuilder(String str): создает объект емкостью str.length() + 16, содержащий строку str
 * - StringBuilder(CharSequence str): создает объект, содержащий CharSequence str */


/* МЕТОД APPEND ПОЗВОЛЯЕТ ДЕЛАТЬ ЦЕПОЧКИ ВЫЗОВОВ
 * - т.к. он возвращает объект StringB..*/


/* STRING <-> STRINGBUILDER, STRINGBUFFER
 * - у String, StringBuilder и StringBuffer есть соответствующие друг другу конструкторы
 * - значение StringBuilder или StringBuffer нельзя напрямую назначить в String
 * - перевести значение можно методом toString()
 * - следовательно, его можно опускать в местах, где он и так будет вызван автоматически */


/* ЕМКОСТЬ БУФЕРА
 * - узнать текущую емкость буфера - capacity()
 * - по дефолту емкость: длина строки + 16 дополнительный символов (для экономии времени при
 * распределении памяти в будущем)
 * - установить минимумальную емкость - ensureCapacity(int minCapacity):
 *      + будет выбрано большее из:
 *          - новая минимальная емкость
 *          - старая емкость * 2 + 2 символа
 * - при привышении содержанием предела емкости, новая емкость вычисляется по формуле:
 *      + текущее содержание * 2
 * - при сокращении содержания, емкость остается прежней */


/* ДЛИНА СТРОКИ
 * - узнать длину строки (содержание) - length()
 * - установить произвольную длину строки - setLength(int newLength):
 *      + если больше текущей:
 *          - дополнительные символы будут равны '\u0000' (пустота)
 *          - при превышении текущей емкости, она будет увеличена в 2 раза
 *      + если меньше текущей:
 *          - лишние символы обрежутся
 *          - емкость не изменится
 *      + если число отрицательное:
 *          - исключение StringIndexOutOfBoundsException*/


/* УДАЛЕНИЕ СИМВОЛОВ
 * - удаление диапазона - delete(int begin, int end):
 *      + удаляет из строки символы, начиная с индекса begin включительно до индекса end исключительно;
 *      + длина строки уменьшается на длину диапазона
 *      + если end больше длины строки, то до конца строки
 *      + если begin отрицательно, больше длины строки или больше end, то StringIndexOutOfBoundsException
 *      + если begin == end, удаление не происходит
 *
 * - удаление символа - deleteCharAt(int ind):
 *      + удаляет символ с указанным индексом ind.
 *      + длина строки уменьшается на 1
 *      + если индекс ind отрицателен или больше длины строки, то StringIndexOutOfBoundsException*/


/* СРАВНЕНИЕ 2 ОБЪЕКТОВ НЕВОЗМОЖНО
 * - т.к. не переопределены методы equals() и hashCode()
 * - т.е. можно сравнить только тот же это объект или нет (аналогично ==)
 * todo - хэш-коды всех объектов этого типа вычисляются так же, как и для класса */


/* НЕТ МЕТОДА FORMAT
 * - форматировать нужно через класс Formatter
 * - для строк точность задет максимальное количество выводимых символов */


/* ПРИ ПРИСОЕДИНЕНИИ NULL-ОВОЙ СТРОКОВОЙ ССЫЛКИ, NULL ДОБАВЛЯЕТСЯ КАК СТРОКА
 * - справедливо для всех типов строковых ссылок: String, StringBuffer, StringBuilder*/


/*ДРУГИЕ МЕТОДЫ
 *  reverse(): Этот метод изменяет значение объекта StringBuffer, который вызывает метод.
 *  insert(int offset, int i) Этот метод вставляет строку s в позицию упомянутую по смещению.
 *  replace(int start, int end, String str) Этот метод заменяет символы в подстроке данного StringBuffer символами в указанной строке.
 * - остальные методы похожи на методы String */


@Ntrstn("Главное предназначение классов StringBuilder и StringBuffer является их возможность изменять " +
        "строку без создания нового объекта (т.к. объекты String являются неизменяемыми). Поэтому, " +
        "в местах, где необходимо произвести множество изменений в строке (например конкатенация в " +
        "цикле), обязательно стоит отдавать им предпочтения в целях оптимизации")

@Ntrstn("Метод append() является 1 из главных методов и позволяет производить операцию конкатенации. " +
        "Кроме того, он возвращает объект того же класса, что позволяет делать цепочки вызовов.")

@Ntrstn("Аналогично конкатенации при помощи оператора + для класса String, если добавлять null-ссылку " +
        "методом append() классов StringBuilder и StringBuffer, то она добавится как текст null, а " +
        "NullPointerException выброшено не будет")

@Ntrstn("В отличие от класса String, метод equals для StringBuilder и StringBuffer не переопределен, " +
        "поэтому произвести посимвольное сравнение невозможно! (можно только сравнивать ссылки " +
        "оператором ==)")

@Ntrstn("Также, в отличие от класса String, у методов классов StringBuilder и StringBuffer нет " +
        "аналогов методов для работы с регулярными выражениями и форматированием")

@Ntrstn("Главными отличиями StringBuilder (появился только с Java 5.0) от StringBuffer, является то, " +
        "что последний является потокобезопасным, но медленным. Поэтому, при возможности, всегда " +
        "стоит использовать первый")

@Ntrstn("Значение StringBuilder или StringBuffer нельзя напрямую назначить в String, так как это " +
        "разные типы. Чтобы преобразовать объект обратно в строку, нужно воспользовать его методом " +
        "toString()")

@Ntrstn("У классов String, StringBuilder и StringBuffer есть соответствующие друг другу конструкторы. " +
        "Кроме того, у классов StringBuilder и StringBuffer есть возможность через конструктор задать " +
        "(недефолтный) размер буфера")

@Ntrstn("С целью оптимизации объекты этих классов по дефолту создаются со свободным буфером в 16 " +
        "символов (если в ручную не задано другая емкость) - будь то при создании пустой строки или " +
        "при создании объекта от существующей строки (в таком случае буфер добавляется в конец строки)")

@Ntrstn("Для буфера можно установить новую минимальную емкость методом ensureCapacity(int minCapacity), " +
        "при этом будет выбрано 1 из 2: либо эта новая емкость либо старая емкость * 2 + 2. При " +
        "превышении содержанием предела емкости, новая емкость вычисляется по формуле: текущее " +
        "содержание * 2. При сокращении емкости, емкость остается прежней")

@Ntrstn("Также можно установить произвольную длину строки setLength(int newLength). Если она больше " +
        "текущей, то новые символы будут равны '\\u0000' (пустота), а при превышении текущей емкости, " +
        "она будет увеличена в 2 раза. Если она меньше текущей, то лишние символы обрежутся, а " +
        "емкость останется прежней. Для отрицательного значение будет выброшено " +
        "StringIndexOutOfBoundsException")

@Ntrstn("Также можно удалять символ (метод deleteCharAt(int ind)) и диапазон символов (метод " +
        "delete(int begin, int end)). При введении отрицательных значений выбрасывается " +
        "StringIndexOutOfBoundsException")

@Ntrstn("На самом деле для повышения эффективности при каждой конкатенации строк (String) не " +
        "создается новая строка, а компилятор использует класс StringBuilder с методом append() для " +
        "каждого действия, а в конце вызывается метод toString() (при этом StringBuilder не " +
        "появляется в исходном коде самой программы). Но при конкатенации в цикле, объект " +
        "StringBuilder будет создаваться каждый проход цикла, поэтому нельзя конкатенировать String " +
        "в цикле (!), а нужно явно использовать объект StringBuilder и его метод append()")

public class StringBuilder_StringBuffer {
    static String stringA = "";
    static StringBuilder stringBuilderA = new StringBuilder("");
    static StringBuilder stringBuilderA1 = new StringBuilder("");
    static StringBuilder stringBuilderA2 = new StringBuilder("");
    static StringBuffer stringBufferA = new StringBuffer("");
    static StringBuffer stringBufferA1 = new StringBuffer("");

    public static void main(String[] args) {
        /* ПРЕДНАЗНАЧЕНЫ ДЛЯ ИНТЕНСИВНОЙ РАБОТЫ СО СТРОКАМИ */
        for (int i = 0; i < 20; i++) {
            // каждый раз создается новый объект - может привести к перерасходу памяти
            stringA += "rerere";
        }
        for (int i = 0; i < 20; i++) {
            stringBuilderA.append("rerere"); // объект остается один
        }


        /* КОНСТРУКТОРЫ: */
        stringBuilderA = new StringBuilder(); // пустой объект емкостью 16 символов
        stringBuilderA = new StringBuilder(123); // пустой объект емкостью 123 символа
        stringBuilderA = new StringBuilder("aa"); // объект емкостью str.length() + 16 и строкой "aa"
        stringBuilderA = new StringBuilder(stringBuilderA); // объект с содержанием объекта CharSequenct



        /* МЕТОД APPEND ПОЗВОЛЯЕТ ДЕЛАТЬ ЦЕПОЧКИ ВЫЗОВОВ */
        stringBuilderA.append("Hello").append(" world").append("!");



        /* STRING <-> STRINGBUILDER, STRINGBUFFER */
        stringA = new String(stringBuilderA); // есть соответствующий конструктор
        stringA = new String(stringBufferA); // есть соответствующий конструктор
        stringBuilderA = new StringBuilder(stringA); // есть соответствующий конструктор
        stringBuilderA = new StringBuilder(stringBufferA); // есть соответствующий конструктор
        stringBufferA = new StringBuffer(stringA); // есть соответствующий конструктор
        stringBufferA = new StringBuffer(stringBuilderA); // есть соответствующий конструктор
//        stringA = stringBuilderA; // не совместимые типы
        stringA = stringBuilderA.toString(); // для назначения нужно использовать toString()
        stringA = "a" + stringBuilderA; // автоматический вызов toString()
        System.out.println(stringBuilderA); // автоматический вызов toString()


        /* ЕМКОСТЬ БУФЕРА */
        stringBuilderA.capacity(); // узнать текущую емкость буфера
        stringBuilderA = new StringBuilder(); // 0 + 16 = 16 символов
        stringBuilderA = new StringBuilder("22"); // 2 + 16 = 18 символов
        stringBuilderA.ensureCapacity(30); // установить емкость не меньше указанной:
        // !!! даст 38, т.к. (18 * 2) + 2 > 30 ? (18 * 2) + 2 : 30
        stringBuilderA = new StringBuilder(); // 0 + 16 = 16 символов
        stringBuilderA.append("12345678901234567"); // емкость привышена: текущее содержание * 2 = 34


        /* ДЛИНА СТРОКИ */
        stringBuilderA = new StringBuilder();
        stringBuilderA.length(); // содержание: нет, длина: 0, емкость: 16
        stringBuilderA.append("aaa"); // содержание: aaa, длина: 3, емкость: 16
        stringBuilderA.setLength(16); // содержание: aaa             длина: 16, емкость: 16
        stringBuilderA.setLength(17); // содержание: aaa              длина: 17, емкость: 34
        stringBuilderA.setLength(2); // содержание: aa, длина: 2, емкость: 34
//        stringBuilderA.setLength(-1); // StringIndexOutOfBoundsException


        /* УДАЛЕНИЕ ДИАПАЗОНА СИМВОЛОВ */
        stringBuilderA = new StringBuilder("string");
        stringBuilderA.delete(3, 5); // strg, с 3-его ПО 5-тый (НЕ включительно), длина теперь 4
        stringBuilderA = new StringBuilder("string");
        stringBuilderA.delete(3, 155); // str, с 3-его по 155, т.е. здесь все после 2-ого
        stringBuilderA = new StringBuilder("string");
        stringBuilderA.delete(3, 3); // string, т.е. удаления не было
//        stringBuilderA.delete(-1, 5); // StringIndexOutOfBoundsException
//        stringBuilderA.delete(145, 155); // StringIndexOutOfBoundsException
//        stringBuilderA.delete(145, 5); // StringIndexOutOfBoundsException


        /* УДАЛЕНИЕ СИМВОЛА
         * - deleteCharAt(int ind):
         *      + удаляет символ с указанным индексом ind
         *      + длина строки уменьшается на единицу
         *      + если индекс ind отрицателен или больше длины строки, возникает исключительная ситуация */
        stringBuilderA = new StringBuilder("string");
        stringBuilderA.deleteCharAt(2); // sting, длина теперь 5
//        stringBuilderA.deleteCharAt(-1); // StringIndexOutOfBoundsException
//        stringBuilderA.deleteCharAt(100); // StringIndexOutOfBoundsException


        /* СРАВНЕНИЕ 2 ОБЪЕКТОВ НЕВОЗМОЖНО */
        stringBuilderA = new StringBuilder("aaa");
        stringBuilderA1 = new StringBuilder("aaa");
        System.out.println(stringBuilderA.equals(stringBuilderA1)); // false, т.к. разные объекты
        stringBuilderA = stringBuilderA2;
        stringBuilderA1 = stringBuilderA2;
        System.out.println(stringBuilderA.equals(stringBuilderA1)); // true, ссылаются на 1 объект


        /* НЕТ МЕТОДА FORMAT
         * - форматировать нужно через класс Formatter */
        int age = 34;
        stringA = "Илюха";
        Formatter formatter = new Formatter(); // нужен объект класса Formatter
        formatter.format("%s, в этом году тебе будет %d", stringA, age);
        System.out.println(formatter.toString());


        /*ПРИ ПРИСОЕДИНЕНИИ NULL-ОВОЙ СТРОКОВОЙ ССЫЛКИ, ДОБАВИТСЯ NULL В ВИДЕ СТРОКИ*/
        stringBuilderA = new StringBuilder("aaa");
        stringA = null;
        stringBuilderA.append(stringA); // aaanull


        /*ДРУГИЕ МЕТОДЫ*/
        stringBuilderA = new StringBuilder("reverse");
        stringBuilderA.reverse(); // esrever
        stringBuilderA.insert(3, "HYI"); // esrHYIever
        stringBuilderA.replace(3, 6, "PIZDA"); // esrPIZDAever
    }
}