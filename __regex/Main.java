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
 *
 * */


/* ОСНОВНЫЕ МЕТОДЫ PATTERN
 * - compile(String regex): компилирует предоставленное РВ в паттерн
 * - compile(String regex, int flags): компилирует предоставленное РВ в паттерн с учетом флага
 *
 *
 * */


/* ОСНОВНЫЕ ФЛАГИ PATTERN
 * - CASE_INSENSITIVE: включает игнорирование регистра
 * - COMMENTS: позволяет присутствовать в паттерне пробелам и комментариям
 * - MULTILINE: включает многострочный режим
 * */


/* ~~~~~~~~~MATCHER - ДВИЖОК ДЛЯ ИНТЕРПРЕТАЦИИ ПАТТЕРНА И ЕГО СВЕРКИ С ПОЛУЧЕННОЙ СТРОКОЙ~~~~~~~~~
 * - нет публичного конструктора
 *      - для получения объекта, нужно вызвать matcher() для объекта Pattern
 *
 *
 * */


/* ОСНОВНЫЕ МЕТОДЫ MATCHER
 * - matches(): пытается сверить весь диапазон с выражением
 *
 * - find(): пытается найти следующую подходящую под паттерн подпоследовательность в введенной
 * последовательности
 *
 * - start(): вернет стартовый индекс последнего совпадения
 *
 *
 * - end(): вернет индекс, следующий за последним совпадением
 *
 * */

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
 *
 *
 * */

/* МЕТАСИМВОЛЫ <([{\^-=$!|]})?*+.>
 * - в некоторых ситуациях некоторые могут не считаться метасимволами
 *      - 2 способа вручную сделать их не специальными:
 *          - либо перед ним поставить \
 *              - напр. \.
 *          - либо заключить его в \Q \E
 *              - напр. \Q.\E
 *
 * .: любой символ
 * <:
 * (:
 * [:
 * {:
 * \:
 * ^:
 * -:
 * =:
 * $:
 * !:
 * |:
 * ]:
 * }:
 * ):
 * ?:
 * *:
 * +:
 * .:
 * >:
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


/* КВАНТИФИКАТОРЫ - УКАЗЫВАЮТ КОЛИЧЕСТВО НЕОБХОДИМЫХ ВХОЖДЕНИЙ ДЛЯ СВЕРКИ
 * - жадный:
 *      - используется по умолчанию
 *          - совпадение:
 *              X?: 1 или ни одного
 *              X*: 0 или более
 *              X+: 1 или более
 *              X{n}: ровно n раз
 *              X{n,}: не менее n раз
 *              X{n,m}: не менее n, но не более m раз
 *
 * - ленивый:
 *      - совпадение:
 *          - аналогично максимальному, но с дополнительным ? в конце
 *
 *
 * - ревнивый:
 *      - поддерживаются только в Java
 *      - совпадение:
 *          - аналогично максимальному, но с дополнительным + в конце
 *
 * */


/*Методы String для работы с регулярными выражениями:
 * - matches(regex) - соответствует ли строка выражению
 * - split(regex) - разделить строку по выражению
 * - replaceFirst(regex, to), replaceAll(regex, to), - заменить в строке по выражению
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
        String regex = "";




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
