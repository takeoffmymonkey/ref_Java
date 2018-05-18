package __regex;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import types_references_annotations.my_annotations.Ntrstn;

/* РЕГУЛЯРНЫЕ ВЫРАЖЕНИЯ
 * - способ описать набор строк, основываясь на их общих характеристиках
 * - используются для поиска, изменения или манипулирования текстом или данными
 * - выражаются при помощи специального синтаксиса
 *      - схож с синтаксисом в Perl
 * - API для работы с ними находится в пакете java.util.regex:
 *      - Pattern: скомпилированное представление РВ
 *      - Matcher: производит операции сверки для последовательности символов, интерпретируя паттерн
 *      - {MatchResult}: результат операции сверки
 *      - PatternSyntaxException: непроверяемое исключение для указания синтаксической ошибки в
 *      паттерне
 * */


/*~~~~~~~~~~~~~~~~~~~~PATTERN - СКОМПИЛИРОВАННОЕ ПРЕДСТАВЛЕНИЕ РВ~~~~~~~~~~~~~~~~~~~~
 * - нет публичного конструктора
 *      - для получения объекта, нужно вызвать Pattern.compile(String regex), передав РВ
 *
 * - в перегруженную версию Pattern.compile(String regex, int flag) можно передавать флаги
 *      - определяют поведение при сверке
 *      - флагов может быть несколько:
 *          - перечисляются через |
 *
 * */


/* ОСНОВНЫЕ МЕТОДЫ PATTERN
 * - compile(String regex): компилирует предоставленное РВ в паттерн
 * - compile(String regex, int flags): компилирует предоставленное РВ в паттерн с учетом флага
 * - matches(): быстро провести сравнение без создания Matcher
 *      - напр. Pattern.matches("\\d","1");
 * - split(): делит вводимую строку по регулярному выражению
 *      - напр.
 *          Pattern p = Pattern.compile("\\d");
            String[] items = p.split("one9two4three7four1five"); // массив отдельных слов
 * - toString(): вернет строкой выражение, из которого сделан паттерн
 * - quote(): Returns a literal pattern String for the specified String
 * */


/* АНАЛОГИЧНЫЕ МЕТОДЫ ИЗ STRING
 * - matches():
 *      - напр. str.matches(regex) аналогично Pattern.matches(regex, str)
 * - split()
 * - replace() Replaces each substring of this string that matches the literal target sequence with the specified literal replacement sequence. The replacement proceeds from the beginning of the string to the end, for example, replacing "aa" with "b" in the string "aaa" will result in "ba" rather than "ab".
 * */


/* ОСНОВНЫЕ ФЛАГИ PATTERN
 * - CASE_INSENSITIVE: включает игнорирование регистра
 *      - по дефолту включает игнорирование только для US-ASCII
 *          - для Unicode нужен дополнительный флаг UNICODE_CASE
 *      - альтернатива - использовать в РВ (?i)
 *          - напр. (?i)foo
 *          - называется "выражение со встроенным флагом"
 *
 * - COMMENTS: позволяет присутствовать в паттерне пробелам и комментариям, игнорируя их
 *      - альтернатива - (?x)
 *
 * - MULTILINE: включает многострочный режим
 *      - т.е. ^ и $ работают для каждой строки, а не для всего ввода
 *      - альтернатива - (?u)
 * */


/* ~~~~~~~~~MATCHER - ДВИЖОК ДЛЯ ИНТЕРПРЕТАЦИИ ПАТТЕРНА И ЕГО СВЕРКИ С ПОЛУЧЕННОЙ СТРОКОЙ~~~~~~~~~
 * - нет публичного конструктора
 *      - для получения объекта, нужно вызвать matcher() для объекта Pattern
 *
 *
 * */


/* ОСНОВНЫЕ МЕТОДЫ MATCHER
 *
 * - индексные методы:
 * - start(): вернет стартовый индекс последнего совпадения
 * - start(int group): вернет начальный индекс подпоследовательности, захваченной указанной группой
 * при предыдущей операции сверки
 * - end(): вернет индекс, следующий за последним совпадением
 * - end (int group): вернет индекс последнего символа + 1 подпоследовательности, захваченной
 * указанной группой при предыдущей операции сверки
 *
 * - методы изучения:
 * - matches(): пытается сверить весь диапазон с выражением
 * - find(): пытается найти следующую подходящую под паттерн подпоследовательность в введенной
 * последовательности
 * - lookingAt() Attempts to match the input sequence, starting at the beginning of the region, against the pattern.
 *
 * - методы замены:
 *public Matcher appendReplacement(StringBuffer sb, String replacement): Implements a non-terminal append-and-replace step.
public StringBuffer appendTail(StringBuffer sb): Implements a terminal append-and-replace step.
public String replaceAll(String replacement): Replaces every subsequence of the input sequence that matches the pattern with the given replacement string.
public String replaceFirst(String replacement): Replaces the first subsequence of the input sequence that matches the pattern with the given replacement string.
public static String quoteReplacement(String s): Returns a literal replacement String for the specified String. This method produces a String that will work as a literal replacement s in the appendReplacement method of the Matcher class. The String produced will match the sequence of characters in s treated as a literal sequence. Slashes ('\') and dollar signs ('$') will be given no special meaning.
 *
 *
 *
 * - groupCount(): вернет количество групп в РВ (нулевая группа, т.е. все выражение, не учитывается)
 * - group (int group): возвращает вводимую подподпоследовательность, захваченную указанной группой
 * при предыдущей операции сверки
 *
 *
 *
 * */


/* АНАЛОГИЧНЫЕ МЕТОДЫ ИЗ STRING
 *public String replaceFirst(String regex, String replacement): Replaces the first substring of this string that matches the given regular expression with the given replacement. An invocation of this method of the form str.replaceFirst(regex, repl) yields exactly the same result as the expression Pattern.compile(regex).matcher(str).replaceFirst(repl)
public String replaceAll(String regex, String replacement): Replaces each substring of this string that matches the given regular expression with the given replacement. An invocation of this method of the form str.replaceAll(regex, repl) yields exactly the same result as the expression Pattern.compile(regex).matcher(str).replaceAll(repl)
 *
 *
 *
 * */


/*A PatternSyntaxException is an unchecked exception that indicates a syntax error in a regular expression pattern. The PatternSyntaxException class provides the following methods to help you determine what went wrong:

public String getDescription(): Retrieves the description of the error.
public int getIndex(): Retrieves the error index.
public String getPattern(): Retrieves the erroneous regular expression pattern.
public String getMessage(): Returns a multi-line string containing the description of the syntax error and its index, the erroneous regular-expression pattern, and a visual indication of the error index within the pattern.*/




/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~СИНТАКСИС~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

/* СТРОКОВЫЕ ЛИТЕРАЛЫ
 * - начинаются с нулевого индекса
 *
 * - диапазон начинается перед вхождением и заканчивается после последнего
 *      - т.е. если строка "foo" сверяется с выражением "foo", то начало вхождения будет индекс 0,
 *      а конец - индекс 3
 *          - т.е. если с 3 индекса (напр. для строки foofoo) начнется следующее вхождение и
 *          диапазоны будут частично пересекаться
 *
 * */

/* СИМВОЛЫ
 * B: символ В
 * \xhh: символ  с 16-ричным кодом 0xhh
 * \\uhhhh: символ Unicode с 16-ричным представлением 0xhhhh
 * \t: табуляция
 * \n: новая строка
 * \r: возврат курсора
 * \f: подача страницы
 * \e: Escape
 * */



/* КЛАССЫ (ТИПЫ) СИМВОЛОВ - НАБОР СИМВОЛОВ В КВАДРАТНЫХ СКОБКАХ ДЛЯ СВЕРКИ С 1 СИМВОЛОМ В СТРОКОВОМ
 * ВВОДЕ
 *
 * - простой класс:
 *      - конструкция: [abc]
 *      - совпадает для: a, b, c
 *      - синоним: [a|b|c]
 *
 * - отрицание:
 *      - конструкция: [^abc]
 *      - совпадает для: любой символ, кроме a, b, c
 *
 * - диапазон:
 *      - конструкция: [a-zA-Z]
 *      - совпадает для: любой символ от а до z или от A до Z (влючительно)
 *
 * - объединение:
 *      - конструкция: [a-d[m-p]]
 *      - совпадает для: любой символ от а до d или от m до p
 *      - синоним: [a-dm-p]
 *
 * - пересечение:
 *      - конструкция: [a-z&&[def]]
 *      - совпадает для: d, e, f
 *
 * - вычитание:
 *      - конструкция: [a-z&&[^bc]]
 *      - совпадает для: от а до z, кроме b и с
 *      - синоним: [ad-z]
 *
 *      - конструкция: [a-z&&[^m-p]]
 *      - совпадает для: от а до z, кроме диапазона от m до p
 *      - синоним: [a-lq-z]
 * */


/* ПРЕДУСТАНОВЛЕННЫЕ КЛАССЫ СИМВОЛОВ (В PATTERN API) - УДОБНЫЕ СОКРАЩЕНИЯ ДЛЯ КЛАССОВ СИМВОЛОВ
 * - все, что начинается с обратной черты, нужно дополнительно экранировать обратной чертой в коде:
 *      - напр. вместо \d использовать \\d
 *
 * - любой символ (может или не может совпадать с разделителями строк): .
 *      - конструкция:
 *
 * - цифра: \d
 *      - синоним: [0-9]
 *
 * - нецифра: \D
 *      - синоним: [^0-9]
 *
 * - пустой символ: \s
 *      - синоним: [ \t\n\x0B\f\r]
 *
 * - непустой символ: \S
 *      - синоним: [^\s]
 *
 * - символ в слове: \w
 *      - синоним: [a-zA-Z_0-9]
 *
 * - символ в неслове: \W
 *      - синоним: [^\w]
 * */


/*
* POSIX character classes (US-ASCII only)
\p{Lower}	A lower-case alphabetic character: [a-z]
\p{Upper}	An upper-case alphabetic character:[A-Z]
\p{ASCII}	All ASCII:[\x00-\x7F]
\p{Alpha}	An alphabetic character:[\p{Lower}\p{Upper}]
\p{Digit}	A decimal digit: [0-9]
\p{Alnum}	An alphanumeric character:[\p{Alpha}\p{Digit}]
\p{Punct}	Punctuation: One of !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
\p{Graph}	A visible character: [\p{Alnum}\p{Punct}]
\p{Print}	A printable character: [\p{Graph}\x20]
\p{Blank}	A space or a tab: [ \t]
\p{Cntrl}	A control character: [\x00-\x1F\x7F]
\p{XDigit}	A hexadecimal digit: [0-9a-fA-F]
\p{Space}	A whitespace character: [ \t\n\x0B\f\r]*/


/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~КВАНТИФИКАТОРЫ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * - указывают количество необходимых вхождений для сверки
 *
 * - относятся только к 1 символу
 *      - т.е. "abc+" значит, что квантификатор указан только для "c", а не для abc
 *      - но их можно добавлять к классам символов и захватам групп
 *          - класс символов: [abc]+
 *              - т.е. + относится к каждому из указанных символов
 *
 *          - захват группы: (abc)+
 *              - т.е. + относится ко всей последовательности abc
 * */


/* ВИДЫ КВАНТИФИКАТОРОВ
 * - X?: 1 или ни одного
 *      - ищет совпадение для каждого символа и для нулевой длины в конце
 *          - т.е. при "а+" будет искать пристутствие или отсутствие в проверяемом символе символа
 *          "а":
 *              - для "aaaaa" будет 6 совпадений ("a"[1]"a"[2]"a"[3]"a"[4]"a"[5]""[6])
 *              - для "abaaaab" при будет 8 совпадений("a"[1]"b"[2]"a"[3]"a"[4]"a"[5]"a"[6]"b"[7]""[8])
 *
 * - X*: 0 или более
 *      - ищет совпадение для всех встреч символа и для нулевой длины в конце
 *          - т.е. при "а*" будет искать присутствие группы символов "а" или их отстутствие
 *              - для "aaaaa" будет 2 совпадения ("aaaaa"[1]""[2])
 *              - для "abaaaab" при будет 5 совпадений ("a"[1]"b"[2]"aaaa"[3]"b"[4]""[5])
 *
 * - X+: 1 или более
 *      - ищет совпадение для всех встреч символа (пока не встретится другой) и без нулевой длины в
 *      конце
 *          - т.е. при "а+" будет искать присутствие группы ненулевой длины из символов "а"
 *              - для "aaaaa" будет 1 совпадение ("ааааа"[1])
 *              - для "abaaaab" будет 2 совпадения ("a"[1]b"aaaa"[2]b)
 *
 * - X{n}: ровно n раз
 *      - ищет совпадение для всех встреч символа (пока не встретится другой) указанное количество
 *      раз
 *          - т.е. при "а3" будет искать присутствие группы символов "а" длиной в 3 символа
 *              - для "aaaaaaaa" будет 2 совпадения ("aaa"[1]"aaa"[2]aa)
 *
 * - X{n,}: не менее n раз
 *      - ищет совпадение для всех встреч символа (пока не встретится другой) не меньше указанного
 *      количества раз
 *          - т.е. при "а3," будет искать присутствие группы символов "а" длиной от 3 символов
 *              - для "aaaaaaaa" будет 1 совпадения ("aaaaaaaa"[1])
 *
 * - X{n,m}: не менее n, но не более m раз
 *      - ищет совпадение для всех встреч символа (пока не встретится другой) в соответствии с
 *      указанным диапазоном раз
 *          - т.е. при "а3,4" будет искать присутствие группы символов "а" длиной от 3 до 4 символов
 *              - для "aaaaaaaaaaa" будет 3 совпадения ("aaaa[1]aaaa"[2]aaa[3])*/


/* АЛГОРИТМЫ КВАНТИФИКАТОРОВ
 * - жадный:
 *      - перед началом сверки "съедает" всю вводимую строку и начинает сверку с конца
 *          - т.е. последним идет сверка между индексом 1 и 0
 *      - используется по умолчанию
 *      - т.е. для ".*foo" со строкой "xfooxxxxxxfoo" будет найдено 1 совпадение
 *          - т.к. последние foo было съедено с самого начала вместе со всей сторокой
 *
 * - ленивый:
 *      - противоположен жадному
 *          - т.е. начинает сверку с начала строки, и поедает символы один за одним
 *          - последней идет сверка всей строки
 *      - синтаксис аналогичен жадному, но с дополнительным ? в конце
 *      - т.е. для ".*?foo" со строкой "xfooxxxxxxfoo" будет найдено 2 совпадения
 *
 * - ревнивый:
 *      - всегда сначала съедает всю вводимую строку
 *      - проверка проходит только единожды без отступления обратно
 *          - даже если такое отступление привело бы совпадению
 *      - поддерживаются только в Java
 *      - синтаксис аналогичен жадному, но с дополнительным + в конце
 *      - т.е. для ".*+foo" со строкой "xfooxxxxxxfoo" не будет найдено ни 1 совпадения
 *          - т.к. перечитка не происходит?
 *      - используется для оптимизации скорости
 *
 *      The first example uses the greedy quantifier .* to find "anything", zero or more times, followed by the letters "f" "o" "o". Because the quantifier is greedy, the .* portion of the expression first eats the entire input string. At this point, the overall expression cannot succeed, because the last three letters ("f" "o" "o") have already been consumed. So the matcher slowly backs off one letter at a time until the rightmost occurrence of "foo" has been regurgitated, at which point the match succeeds and the search ends.

The second example, however, is reluctant, so it starts by first consuming "nothing". Because "foo" doesn't appear at the beginning of the string, it's forced to swallow the first letter (an "x"), which triggers the first match at 0 and 4. Our test harness continues the process until the input string is exhausted. It finds another match at 4 and 13.

The third example fails to find a match because the quantifier is possessive. In this case, the entire input string is consumed by .*+, leaving nothing left over to satisfy the "foo" at the end of the expression. Use a possessive quantifier for situations where you want to seize all of something without ever backing off; it will outperform the equivalent greedy quantifier in cases where the match is not immediately found.
 *      */


/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ЗАХВАТ ГРУППЫ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * - способ объединить несколько символов в 1 группу в рамках одних скобок
 * - нумерация идет по открывающимся скобкам. Т.е. для ((A)(B(C))):
 *      1. ((A)(B(C)))
 *      2. (A)
 *      3. (B(C))
 *      4. (C)
 *
 * - нулевая группа - все выражение в целом
 *      - не учитывается методом groupCount
 *
 * - некоторые методы Matcher принимают на вход номер группы
 *
 * - группы, которые начинаются с (?, явлюются чистыми незахватывающими группами
 *      - т.е. не захватывают текст и не добавляются в общее число групп
 *
 * */

/* ОБРАТНАЯ ССЫЛКА НА ГРУППУ (BACKREFERENCING)
 * - часть от вводимой строки, совпадающей с захваченной группой сохраняется в памяти для дальнейшего
 * вызова при помощи обратной ссылки (backreferencing)
 *      - указывается в РВ как обратная черта \, после которой идет цифра, указывающая номер
 *      вызываемой группы
 *          - напр. (\d\d) определяет одну захваченную группу, которая соответствует 2 цифрам подряд
 *              - чтобы было совпадение для 2 цифр, после которых идут эти же цифры, нужно такое РВ:
 *              (\d\d)\1
 *                  - тогдя для "1212" будет совпадение
 *                  - а для "1234" нет
 *      - для вложенный захваченных группы обратные ссылки работают аналогично
 * */



/* УКАЗАНИЕ МЕСТА, ГДЕ ПРОВОДИТСЯ ПОИСК НА СОВПАДЕНИЕ (BOUNDARY MATCHERS)
 * - ^: начало строки
 * - $: конец строки
 * - \b: в рамках слова
 *      - "\bdog\b" для "The dog plays" - есть совпадение
 *      - "\bdog\b" для "The doggie plays" - нет совпадения
 * - \B: в рамках неслова
 *      - "\bdog\B" для "The doggie plays" - есть совпадение
 *      - "\bdog\B" для "The dog plays" - нет совпадения
 * - \A: начало ввода
 * - \G: конец предыдущего совпадения
 *      - "dog" для dog dog - 2 совпадения
 *      - "\Gdog" для dog dog - 1 совпадение
 * - \Z: конец ввода, но для конечного прерывателя (final terminator), если таковой имеется
 * - \z: конец ввода
 *
 * */



/* СИМВОЛЬНЫЕ КЛАССЫ
 * .: любой символ
 * [abc]: любой из символов a, b, c (то же, что и a|b|c)
 * [^abc]: любой символ, кроме a, b, c (отрицание)
 * [a-zA-Z]: любой символ от a до z и от A до Z (диапазон)
 * [abc[hij]]: любой из символов a, b, c, h, i, j (то же, что и a|b|c|h|i|j) (объединение)
 * [a-z&&[hij]]: символ h, i, j (пересечение)
 * \s: пропуск (пробел, табуляция, новая строка, подача страницы, возврат курсора)
 * \S: символ, не являющийся пропуском (то же, что [^\s])
 * \d: цифра [0-9]
 * \D: не цифра [^0-9]
 * \w: символ слова [a-zA-Z_0-9]
 * \W: символ, не являющийся символом слова [^\w]
 *
 * */


/* Группы
 * - части регекса, заключенные в скобки, к которым потом можно обращаться по номеру группы
 * - 0 - совпадение всего выражения
 * - 1 - совпадение первого подвыражения в круглых скобках и т.д.
 * */


/* С J7 есть поддержка Unicode 6.0
 *  - кодовые точки
 *  - свойства символов*/

@Ntrstn("Регулярные выражения - мощный и гибкий инструмент обработки текстов. Они позволяют " +
        "определять на программном уровне сложные шаблоны для поиска текста во входной строке. " +
        "Обнаружив совпадение для шаблона, его можно обработать как нужно. Это компактный и " +
        "динамичный язык, который может использоваться для решения самых разнообразных задач " +
        "обработки строк, поиска и выделения совпадений, редактирования и проверки")

@Ntrstn("В Java язык регулярных выражений является надстройкой, работающей поверх Java")

@Ntrstn("Регулярное выражение и паттерн используются как синонимы, так как перед тем, как начать " +
        "работать с текстом и РВ создается объект Pattern")

@Ntrstn("Желательно использовать предустановленные классы символов для более быстрого и удобного " +
        "чтения регулярных выражений")

@Ntrstn("нужно экранировать символы дополнительной обратной чертой символы, которые начинаются с " +
        "обратной черты")

@Ntrstn("В регулярных выражения ьакже используются && для логического И и | для логического ИЛИ ")

@Ntrstn("IDE по особому подсвечивает синтаксис регулярных выражений")

@Ntrstn("Совпадения нулевой длины (zero-length matches) - начинаются и заканчиваются в одной и той " +
        "же позиции индекса. Они происходят в следующих случаях: в пустых строках, в начале " +
        "введенной строки, после последнего символа введенной строки или между 2 символами введенной " +
        "строки ")

@Ntrstn("Метод matches есть и у Matcher и у Pattern")

@Ntrstn("У класса String есть аналогичные методы Pattern и Matcher")


public class Main {

    public static void main(String[] args) {
        /*~~~~~~~~~~~~~~~~~~~~PATTERN + MATCHER ~~~~~~~~~~~~~~~~~~~~ */
        /*СОЗДАНИЕ ПАТТЕРНА ИЗ РВ */
        Pattern pattern = Pattern.compile("Some"); // без класса символов

        /* СОЗДАНИЕ MATCHER C УКАЗАНИЕМ СТРОКИ, ГДЕ ИСКАТЬ */
        Matcher matcher = pattern.matcher("Some string");


        /*~~~~~~~~~~~~~~~~~~~~КЛАССЫ (ТИПЫ) СИМВОЛОВ~~~~~~~~~~~~~~~~~~~~*/
        /* ПРОСТОЙ КЛАСС */
        Pattern patternClassSimple = Pattern.compile("[Ss]ome string"); // любой из указанных символов
        /* ОТРИЦАНИЕ */
        Pattern patternClassNegation = Pattern.compile("[^A]ome string"); // не указанный символ
        /* ДИАПАЗОН */
        Pattern patternClassRange = Pattern.compile("[A-Z]ome string"); // любой из указанного диапазона
        /* ОБЪЕДИНЕНИЕ */
        Pattern patternClassUnion = Pattern.compile("[A-E[N-Z]]ome string");// любой из указанных диапазонов
        /* ПЕРЕСЕЧЕНИЕ */
        Pattern patternClassIntersection = Pattern.compile("[A-Z&&[OPS]]ome string"); // любой из пересечения
        /* ВЫЧИТАНИЕ */
        Pattern patternClassSubtraction = Pattern.compile("[A-Z&&[^PQ]]ome string");
        Pattern patternClassSubtraction2 = Pattern.compile("[A-Z&&[^D-F]]ome string");

        Matcher matcherClassSimple = patternClassSimple.matcher("Some string");
        Matcher matcherClassRange = patternClassRange.matcher("Some string");
        Matcher matcherClassUnion = patternClassUnion.matcher("Some string");
        Matcher matcherClassIntersection = patternClassIntersection.matcher("Some string");
        Matcher matcherClassSubtraction = patternClassSubtraction.matcher("Some string");
        Matcher matcherClassSubtraction2 = patternClassSubtraction2.matcher("Some string");

        /*~~~~~~~~~~~~~~~~~~~~ПРЕДУСТАНОВЛЕННЫЕ КЛАССЫ СИМВОЛОВ~~~~~~~~~~~~~~~~~~~~*/
        /*ЛЮБОЙ СИМВОЛ*/
        Pattern patternPredefinedAny = Pattern.compile("[.]ome string");
        /*ЦИФРОВОЙ СИМВОЛ*/
        Pattern patternPredefinedDigit = Pattern.compile("[\\d]ome string");
        /*НЕЦИФРОВОЙ СИМВОЛ*/
        Pattern patternPredefinedNonDigit = Pattern.compile("[\\D]ome string");
        /*ПУСТОЙ СИМВОЛ*/
        Pattern patternPredefinedSpace = Pattern.compile("Some[\\s]string");
        /*НЕПУСТОЙ СИМВОЛ*/
        Pattern patternPredefinedNonSpace = Pattern.compile("[\\S]ome string");
        /*СИМВОЛ В СЛОВЕ*/
        Pattern patternPredefinedWord = Pattern.compile("[\\w]ome string");
        /*СИМВОЛ В НЕСЛОВЕ*/
        Pattern patternPredefinedNonWord = Pattern.compile("Some[\\W]string");

        Matcher matcherPredefinedAny = patternPredefinedAny.matcher("Some string");
        Matcher matcherPredefinedDigit = patternPredefinedDigit.matcher("Some string");
        Matcher matcherPredefinedNonDigit = patternPredefinedNonDigit.matcher("Some string");
        Matcher matcherPredefinedSpace = patternPredefinedSpace.matcher("Some string");
        Matcher matcherPredefinedNonSpace = patternPredefinedNonSpace.matcher("Some string");
        Matcher matcherClassPredefinedWord = patternPredefinedWord.matcher("Some string");
        Matcher matcherPredefinedNonWord = patternPredefinedNonWord.matcher("Some string");


        /* ~~~~~~~~~~~~~~~~~~~~КВАНТИФИКАТОРЫ~~~~~~~~~~~~~~~~~~~~ */
        /* 1 ИЛИ НИ ОДНОГО */
        Pattern patternQuantifierOnceOrZero = Pattern.compile("[S]?ome string");
        /* 0 ИЛИ БОЛЕЕ */
        Pattern patternQuantifierZeroOrMore = Pattern.compile("[S]*ome string");
        /* 1 ИЛИ БОЛЕЕ */
        Pattern patternQuantifierOnceOrMore = Pattern.compile("[S]+ome string");
        /* УКАЗАННОЕ КОЛИЧЕСТВО РАЗ */
        Pattern patternQuantifierNTimes = Pattern.compile("[S]{1}ome string");
        /* МИНИМУМ УКАЗАННОЕ КОЛИЧЕСТВО РАЗ */
        Pattern patternQuantifierMinNTimes = Pattern.compile("[S]{1,}ome string");
        /* ДИАПАЗОН УКАЗАННЫХ РАЗ */
        Pattern patternQuantifierRangeTimes = Pattern.compile("[S]{1,10}ome string");

        Matcher matcherQuantifierOnceOrZero = patternQuantifierOnceOrZero.matcher("Some string");
        Matcher matcherQuantifierZeroOrMore = patternQuantifierZeroOrMore.matcher("Some string");
        Matcher matcherQuantifierOnceOrMore = patternQuantifierOnceOrMore.matcher("Some string");
        Matcher matcherQuantifierNTimes = patternQuantifierNTimes.matcher("Some string");
        Matcher matcherQuantifierMinNTimes = patternQuantifierMinNTimes.matcher("Some string");
        Matcher matcherQuantifierRangeTimes = patternQuantifierRangeTimes.matcher("Some string");


        /* ~~~~~~~~~~СРАВНЕНИЕ КВАНТИФИКАТОРОВ~~~~~~~~~~ */
        String s = "a";

        String regexOneOrZero = "a?";
        //1 совпадение: "a" - начало index 0 и конец index 1
        //2 совпадение: "" - начало index 1 и конец index 1

        String regexAnyNumber = "a*";
        //1 совпадение: "a" - начало index 0 и конец index 1
        //2 совпадение: "" - начало index 1 и конец index 1

        String regexOneOrMore = "a+"; //
        // 1 совпадение: "a" - начало index 0 и конец index 1


        String s2 = "aaaaa";
        // String regexOneOrZero = "a?";
        //1 совпадение: "a" - начало index 0 и конец index 1
        //2 совпадение: "a" - начало index 1 и конец index 2
        //3 совпадение: "a" - начало index 2 и конец index 3
        //4 совпадение: "a" - начало index 3 и конец index 4
        //5 совпадение: "a" - начало index 4 и конец index 5
        //6 совпадение: "" - начало index 5 и конец index 5

        //String regexAnyNumber = "a*";
        //1 совпадение: "a" - начало index 0 и конец index 1
        //2 совпадение: "" - начало index 5 и конец index 5

        // String regexOneOrMore = "a+"; //
        // 1 совпадение: "aaaaa" - начало index 0 и конец index 5


        /* ЗАХВАТ ГРУППЫ */





        /*~~~~~~~~~~~~~~~~~~~~ МЕТОДЫ MATCHER ~~~~~~~~~~~~~~~~~~~~*/
        /* MATCHES() - попытка сверить весь диапазон с паттерном */
        System.out.println("matcher: " + matcher.matches()); // false, должен полностью соответствовать вводу
        // Простые классы символов
        System.out.println("matcherClassSimple: " + matcherClassSimple.matches()); // true
        System.out.println("matcherClassRange: " + matcherClassRange.matches()); // true
        System.out.println("matcherClassUnion: " + matcherClassUnion.matches()); // true
        System.out.println("matcherClassIntersection: " + matcherClassIntersection.matches()); // true
        System.out.println("matcherClassSubtraction: " + matcherClassSubtraction.matches()); // true
        System.out.println("matcherClassSubtraction2: " + matcherClassSubtraction2.matches()); // true
        // Специальные классы символов
        System.out.println("matcherPredefinedAny: " + matcherPredefinedAny.matches()); // true
        System.out.println("matcherPredefinedDigit: " + matcherPredefinedDigit.matches()); // false
        System.out.println("matcherPredefinedNonDigit: " + matcherPredefinedNonDigit.matches()); // true
        System.out.println("matcherPredefinedSpace: " + matcherPredefinedSpace.matches()); // true
        System.out.println("matcherPredefinedNonSpace: " + matcherPredefinedNonSpace.matches()); // true
        System.out.println("matcherClassPredefinedWord: " + matcherClassPredefinedWord.matches()); // true
        System.out.println("matcherPredefinedNonWord: " + matcherPredefinedNonWord.matches()); // true
        // Квантификаторы
        System.out.println("matcherQuantifierOnceOrZero: " + matcherQuantifierOnceOrZero.matches()); // true
        System.out.println("matcherQuantifierZeroOrMore: " + matcherQuantifierZeroOrMore.matches()); // true
        System.out.println("matcherQuantifierOnceOrMore: " + matcherQuantifierOnceOrMore.matches()); // true
        System.out.println("matcherQuantifierNTimes: " + matcherQuantifierNTimes.matches()); // true
        System.out.println("matcherQuantifierMinNTimes: " + matcherQuantifierMinNTimes.matches()); // true
        System.out.println("matcherQuantifierRangeTimes: " + matcherQuantifierRangeTimes.matches()); // true





        /* FIND() - поиск следующей подпоследовательности, которая соответствует паттерну */
        System.out.println(matcher.find()); // true




        /* ~~~~~~~~~~~~~~~~~~~~СОВПАДЕНИЯ НУЛЕВОЙ ДЛИНЫ~~~~~~~~~~~~~~~~~~~~ */
        /* ПУСТЫЕ СТРОКИ */
        String s1 = "a";
        // 2 совпадения: "a" - начало index 0 и конец index 1, "" - начало index 1 и конец index 1
        String regex = "a?"; // 1ое совпадение: на index 0 and ending at index 1
        String regex2 = "a*"; //
        // 1 совпадение: "a" - начало index 0 и конец index 1
        String regex3 = "a+*"; //




/*        Console console = System.console();
        if (console == null) {
            System.err.println("No console");
            System.exit(1);
        }

        while (true) {
            Pattern pattern = Pattern.compile(console.readLine("%nEnter your regex: "));

            Matcher matcher = pattern.matcher(console.readLine("Enter input string to search: "));

            boolean found = false;
            while (matcher.find()) {
                console.format("I found the text \"%s\" starting at index %d and ending at " +
                                "index %d.%n",
                        matcher.group(),
                        matcher.start(),
                        matcher.end());
                found = true;
            }

            if (!found) {
                console.format("No match found.%n");
            }
        }*/





        /* ПРОВЕРКА ТЕЛЕФОНА */
        /* ПРОВЕРКА EMAIL */
        /* ПРОВЕРКА IP */




        /*"Необязательный знак "минус", за которым следует однак или нескольких цифр:
         *      -?\d+ */
/*        System.out.println("-1234".matches("-?\\d+")); // true
        System.out.println("1234".matches("-?\\d+")); // true
        System.out.println("+1234".matches("-?\\d+")); // false
        *//*То же, но либо плюс либо минус:
         *      (-|\\+)?\\d+ *//*
        System.out.println("+1234".matches("(-|\\+)?\\d+")); // false*/

    }
}
