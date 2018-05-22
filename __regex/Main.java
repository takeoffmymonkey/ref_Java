package __regex;


import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import types_references_annotations.my_annotations.Ntrstn;



/* РЕГУЛЯРНЫЕ ВЫРАЖЕНИЯ
 * - способ описать набор строк, основываясь на их общих характеристиках
 *
 * - используются для поиска, изменения или валидации текста
 *
 * - выражаются при помощи специального синтаксиса
 *      - схож с синтаксисом в Perl
 *
 * - API для работы с ними находится в пакете java.util.regex:
 *      - Pattern: скомпилированное представление РВ
 *      - Matcher: производит операции сверки для последовательности символов, интерпретируя паттерн
 *      - {MatchResult}: результат операции сверки
 *      - PatternSyntaxException: непроверяемое исключение для указания синтаксической ошибки в
 *      паттерне */



/*~~~~~~~~~~~~~~~~~~~~PATTERN - СКОМПИЛИРОВАННОЕ ПРЕДСТАВЛЕНИЕ РВ~~~~~~~~~~~~~~~~~~~~
 * - нет публичного конструктора
 *      - для получения объекта, нужно вызвать Pattern.compile(String regex), передав РВ
 *
 * - в перегруженную версию Pattern.compile(String regex, int flag) можно также передавать флаги
 *      - определяют поведение при сверке
 *      - флагов может быть несколько:
 *          - перечисляются через | */


/* ОСНОВНЫЕ ФЛАГИ МЕТОДА PATTERN.COMPILE()
 * - CASE_INSENSITIVE: включает игнорирование регистра
 *      - по дефолту включает игнорирование только для US-ASCII
 *          - для Unicode нужен дополнительный флаг UNICODE_CASE
 *      - альтернатива - использовать (?i)
 *          - напр. (?i)foo
 *          - называется "выражение со встроенным флагом"
 *
 * - COMMENTS: позволяет присутствовать в паттерне пробелам и комментариям, игнорируя их
 *      - альтернатива - (?x)
 *
 * - todo MULTILINE: включает многострочный режим
 *      - т.е. ^ и $ работают для каждой строки, а не для всего ввода
 *      - альтернатива - (?u) */


/* ОСНОВНЫЕ МЕТОДЫ PATTERN
 * - compile(String regex): компилирует предоставленное РВ в паттерн
 *      - compile(String regex, int flags): компилирует предоставленное РВ в паттерн с учетом флагов
 *
 * - matches(String regex, String str): производит сравнение без создания объекта Matcher
 *      - напр. Pattern.matches("\\d", "1");
 *
 * - split(CharSequence input): делит вводимую строку по регулярному выражению на массив строк
 *      - напр.
 *          Pattern p = Pattern.compile("\\d");
            String[] items = p.split("one9two4three7four1five"); // массив отдельных слов
 *
 * - toString(): вернет строкой РВ, из которого сделан паттерн
 *
 * - quote(String s): вернет в виде строки шаблон, который подходит для введенного текста
 *      - напр. для 123xxxAAA вернет \Q123xxxAAA\E */


/* АНАЛОГИЧНЫЕ МЕТОДЫ ИЗ STRING
 * - matches(): аналогично Pattern.matches(String regex, String str)
 * - split(): аналогично Pattern.split(CharSequence input)*/



/* ~~~~~~~~~MATCHER - ДВИЖОК ДЛЯ СВЕРКИ ШАБЛОНА С ПОЛУЧЕННОЙ СТРОКОЙ И ПОСЛЕДУЮЩИЙ ДЕЙСТВИЙ~~~~~~~~~
 * - нет публичного конструктора
 *      - для получения объекта, нужно вызвать matcher() для объекта Pattern */


/* ОСНОВНЫЕ ГРУППЫ МЕТОДОВ MATCHER
 * - методы изучения:
 *      - matches(): пытается сверить весь(!) диапазон с выражением
 *
 *      - find(): пытается найти следующую подходящую под паттерн подпоследовательность в введенной
 *      последовательности
 *          - т.е. если не был перезагружен (reset()), начинает поиск с последнего места
 *          - find(int index): перезагрузить поиск и начать с указанного индекса символа
 *
 *      - lookingAt(): сверяет введенный текст с паттерном, начиная с начала диапазона
 *          - т.е. совпадать всему тексту не обязательно!
 *              - в отличие от matches()
 *
 * - индексные методы:
 *      - start(): вернет стартовый индекс последнего совпадения
 *          - start(int group): вернет начальный индекс подпоследовательности, захваченной указанной
 *          группой при предыдущей операции сверки
 *
 *      - end(): вернет индекс, следующий за последним совпадением
 *          - end (int group): вернет индекс последнего символа (+1) подпоследовательности,
 *          захваченной указанной группой при предыдущей операции сверки
 *
 *
 *  - методы для работы с группами:
 *      - groupCount(): вернет количество групп в РВ
 *          - при этом нулевая группа, т.е. все выражение, не учитывается
 *
 *      - group (int group): возвращает вводимую подподпоследовательность, захваченную указанной
 *      группой при предыдущей операции сверки
 *
 *
 * - методы замены:
 *      - replaceAll(String replacement): заменяет предоставленной строкой каждую
 *      подпоследовательность в тексте, которая совпадает с шаблоном
 *
 *      - replaceFirst(String replacement): заменяет предоставленной строкой первую
 *      подпоследовательноть в тексте, которая совпадает с шаблоном */


/* АНАЛОГИЧНЫЕ МЕТОДЫ ИЗ STRING
 * - replaceFirst(String regex, String replacement): аналогично
 * - replaceAll(String regex, String replacement): аналогично */


/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~PATTERNSYNTAXEXCEPTION~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * - предназначен для иключения, указывающего на синтаксические проблемы в РВ
 *      - но IDE предупреждает о проблемах еще на стадии компиляции
 *
 * - является непроверяемым
 *      - т.е. не нужно обрамлять try-catch блоком */


/* МЕТОДЫ PATTERNSYNTAXEXCEPTION
 * - getDescription(): получить описание ошибки
 * - getIndex(): получить индекс ошибки
 * - getPattern(): получить паттерн, в котором находится ошибка
 * - getMessage(): получить многострочную строку с описанием синтаксической ошибки и ее индекса, а
 * также проблемный паттерн и визуальное указание на ошибку в паттерне*/



/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~СИНТАКСИС~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/* ~~~~~~~~~~~~~~~~~~~~~~~~СИМВОЛЫ~~~~~~~~~~~~~~~~~~~~~~~~
 * B: символ В
 * \xhh: символ с 16-ричным кодом 0xhh
 * \\uhhhh: символ Unicode с 16-ричным представлением 0xhhhh
 * \t: табуляция
 * \n: новая строка
 * \r: возврат курсора
 * \f: подача страницы
 * \e: Escape */


/* КЛАССЫ (ТИПЫ) СИМВОЛОВ - НАБОР СИМВОЛОВ В КВАДРАТНЫХ СКОБКАХ ДЛЯ СВЕРКИ С 1 СИМВОЛОМ В СТРОКОВОМ
 * ВВОДЕ
 * - [abc]: простой класс
 *      - совпадает с: a, b, c
 *      - синоним: [a|b|c]
 *
 * - [^abc]: отрицание
 *      - совпадает с: любой символ, кроме a, b, c
 *
 * - [a-zA-Z]: диапазон
 *      - совпадает с: любой символ от а до z или от A до Z (влючительно)
 *
 * - [a-d[m-p]]: объединение
 *      - совпадает с: любой символ от а до d или от m до p
 *      - синоним: [a-dm-p]
 *
 * - [a-z&&[def]]: пересечение
 *      - совпадает с: d, e, f
 *
 * - [a-z&&[^bc]]: вычитание
 *      - совпадает с: от а до z, кроме b и с
 *      - синоним: [ad-z]
 *
 * - [a-z&&[^m-p]]: вычитание
 *      - совпадает с: от а до z, кроме диапазона от m до p
 *      - синоним: [a-lq-z]*/


/* ПРЕДУСТАНОВЛЕННЫЕ КЛАССЫ СИМВОЛОВ (В PATTERN API) - УДОБНЫЕ СОКРАЩЕНИЯ ДЛЯ КЛАССОВ СИМВОЛОВ
 * - все, что начинается с обратной черты, нужно дополнительно экранировать обратной чертой в коде:
 *      - напр. вместо \d использовать \\d
 *
 * - .: любой символ
 * - \d: любая цифра (аналогично [0-9])
 * - \D: любая нецифра (аналогично [^0-9])
 * - \s: любой символ с пустотой (аналогично [\t\n\x0B\f\r])
 * - \S: любой непустой символ (аналогично [^\s])
 * - \w: любой символ в слове (аналогично [a-zA-Z_0-9])
 * - \W: любой символ в неслове (аналогично [^\w])*/


/* POSIX (ТОЛЬКО ДЛЯ US-ASCII)
 * - \p{Lower}: любой буквенный символ в нижнем регистре (аналогично [a-z])
 * - \p{Upper}:	любой буквенный символ в верхнем регистре (аналогично [A-Z])
 * - \p{ASCII}:	все символы ASCII (аналогично[\x00-\x7F])
 * - \p{Alpha}:	любой буквенный символ в любом регистре (аналогично [\p{Lower}\p{Upper}])
 * - \p{Digit}:	любой цифровой символ: (аналогично[0-9])
 * - \p{Alnum}:	любой буквенно-цифровой символ (аналогично[\p{Alpha}\p{Digit}])
 * - \p{Punct}:	любой символ пунктуации (аналогично[!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~]
 * - \p{Graph}:	любой отображаемый символ (аналогично[\p{Alnum}\p{Punct}])
 * - \p{Print}:	любой печатный символ (аналогично[\p{Graph}\x20])
 * - \p{Blank}:	пробел или табуляция (аналогично[ \t])
 * - \p{Cntrl}:	любой управляющий символ (аналогично[\x00-\x1F\x7F])
 * - \p{XDigit}: любой 16-ричный символ (аналогично[0-9a-fA-F])
 * - \p{Space}:	любой символ с пустотой (аналогично[ \t\n\x0B\f\r])*/


/* ~~~~~~~~~~~~~~~~~~~~~~~~КВАНТИФИКАТОРЫ~~~~~~~~~~~~~~~~~~~~~~~~
 * - указывают количество необходимых вхождений для сверки
 *
 * - относятся только к 1 символу
 *      - т.е. "abc+" значит, что квантификатор указан только для "c", а не для abc
 *      - но их можно добавлять к классам символов и захватам групп
 *          - класс символов: [abc]+
 *              - т.е. + относится к каждому из указанных символов
 *
 *          - захват группы: (abc)+
 *              - т.е. + относится ко всей последовательности abc */


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


/*~~~~~~~~~~~~~~~~~~~~~~~~ЗАХВАТ ГРУППЫ~~~~~~~~~~~~~~~~~~~~~~~~
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
 *
 * - у групп могут быть имена
 * */


/* С J7 есть поддержка Unicode 6.0
 *  - кодовые точки
 *  - свойства символов*/


@Ntrstn("Регулярное выражение позволяет на специальном языке задать шаблон, который описывает " +
        "некоторое условие, по которому будет производится поиск в тексте. Сам шаблон позволяет " +
        "описывать текст, основываясь на его каких-то общих характеристиках. Затем этот шаблон " +
        "направляется на желаемый текст, и в нем производится поиск мест, которые соответствуют " +
        "заданному в шаблоне условию. При помощи дополнительных средств (методов специального класса " +
        "в Java) с этими местами можно произвести желаемые действия - например, заменить их на " +
        "другой текст или разбить текст на массив в местах, где происходит совпадение шаблону. Кроме " +
        "того, сам факт совпадения можно использовать для определения, имеет ли весь текст " +
        "необходимую форму (например, является ли этот текст имейлом или IP-адресом) - т.е. для " +
        "валидации его формата")

@Ntrstn("Понятия регулярное выражение, паттерн и шаблон по своей сути являются синонимами (из " +
        "регулярного выражения получается шаблон (паттерн) для осуществления поиска).")

@Ntrstn("В Java язык регулярных выражений является надстройкой, работающей поверх Java при помощи " +
        "API в пакете java.util.regex, а IDE умеет его по-особому подсвечивать")


@Ntrstn("Синтаксис регулярного выражения состоит из следующий конструкций:" +
        "1 - символ/специальный символ: указывает искомый в тексте символ. Напр. x соответствует x, " +
        "\\xhh - символ с 16-ричным кодом 0xhh, а \\t - табуляция (чтобы корректно обрабатывать " +
        "символы, требующие обратную черту, их нужно экранировать дополнительной обратной чертой)" +
        "2 - классы (не как в Java) символов: набор символов в квадратных скобках для сверки с 1 " +
        "символом в тексте - например [abc] означает, что ищется 1 символ, который может быть либо a, " +
        "либо b, либо с. У этих классов есть специальные знаки, обозначающие дополнительные условия - " +
        "например,  [^0-3] означает, что ищется любой цифровой символ, который не находится в " +
        "диапазоне от 0 до 3." +
        "3 - предустановленные классы символов: то же, что и предыдущее, но имеет сокращенный " +
        "синтаксис для распространенных условий - например \\d означает, что ищется любой цифровой " +
        "символ (аналог [0-9]), а в виде POSIX то же самое записывается как \\p{Digit}. Иногда POSIX " +
        "имеет более короткую форму (но работает только для US-ASCII). Всегда стоит предпочитать предустановленные классы простым!" +
        "4 - квантификаторы: указывают количество раз, которому должно удовлетворять искомое условие, " +
        "например x* означает, что символ x может быть любое количество раз (даже 0) для совпадения " +
        "с условием. Квантификаторы можно применять как к отдельным символам, так и к классам " +
        "символов и группам. Квантификаторы также имеют алгоритмы (см. ниже)" +
        "5 - захват группы: способ объединить несколько символов в 1 группу в рамках одних скобок - " +
        "например, (abc(de)) означает, что есть 2 группы (номер считается по открывающейся скобке " +
        "(также есть нулевая группы - все выражение)) и совпадением, например, для второй группы (de)" +
        "будет считаться символы de в тексте. В самом регулярном выражении можно ссылаться на группу " +
        "при помощи backreferencing - например " +
        "6 - условие, в каком месте должно искаться совпадениеУКАЗАНИЕ МЕСТА, ГДЕ ПРОВОДИТСЯ ПОИСК НА СОВПАДЕНИЕ (BOUNDARY MATCHERS)" +
        "")

@Ntrstn("Алгоритмы квантификаторов.")

@Ntrstn("Классический способ работы с регулярными выражениями в Java заключается в использовании 2 " +
        "специальных классов - Pattern и Matcher. Класс Pattern предназначен для работы с самим " +
        "регулярным выражением, а класс Matcher применяет описанный шаблон к указанному тексту и " +
        "прендазначен для работы с найденными совпадениями. Кроме этих классов в пакете " +
        "java.util.regex также есть класс PatternSyntaxException для работы с исключениями, которые " +
        "вызываются при неправильно заданном регулярном выражении (т.е. при некорректном синтаксисе" +
        " - хотя IDE отлично справляется с этой работой, предупреждая о проблемах на стадии " +
        "компиляции). Но кроме этих классов, класс String также обладает некоторыми методами, " +
        "дублирующими методы классов Pattern и Matcher, без необходимости создавать эти объекты")

@Ntrstn("У обоих классов - Pattern и Matcher - нет конструкторов. Сначала создается объект Pattern " +
        "от статического метода Pattern.compile(String regex), куда на вход передается регулярное " +
        "выражение в виде простой строки (т.е. компилируется шаблон). Затем от этого объекта при " +
        "помощи метода matcher(String text), примимающего на вход текст, где будет производится " +
        "поиск, создается объект Matcher. Все манипуляции с местами в тексте, которые совпадают с " +
        "заданным шаблоном будут производится при помощи этого класса")

@Ntrstn("Метод Pattern.compile(String regex) имеет перегруженную версию " +
        "Pattern.compile(String regex, int flags), которая дополнительно принимает флаги, способные " +
        "модифицировать условия для поиска - например, при поиске игнорировать регистр. Флаги можно " +
        "перечислять при помощи | (битового ИЛИ). Кроме того, большинство флагов имеют синонимы в " +
        "виде специальных символов, которые можно и просто указать в самих регулярных выражениях")

@Ntrstn("Когда достаточно просто провалидировать формат (всего!) текста, то это можно сделать 3 " +
        "способами: " +
        "1 (классический) - при помощи метода matches() от объекта Matcher. Для этого сначала должен " +
        "быть создан объект класса Pattern" +
        "2 (быстрый) - при помощи статического метода метода Pattern.matches(String regex, String text) " +
        "без необходимости создавать объект класса Matcher" +
        "3 (быстрый) - при помощи метода string.matches(String regex), примененного к строковому " +
        "объекту.")

@Ntrstn("Методы класса Matcher, можно условно разделить на несколько групп: " +
        "1 - методы изучения: ищут совпадения по паттерну в тексте. Искать совпадения можно для " +
        "всего текста или для его куска. Также можно переходить по совпадениям" +
        "2 - индексные методы: возвращают начальный и конечный (+1) индексы последнего входжения. " +
        "Но перед этим должен быть совершен поиск, иначе будет выброшено исключение " +
        "IllegalStateException: No match available. Также есть возможность получить индексы " +
        "последнего вхождения для определенной группы" +
        "3 - методы групп: возвращают количество групп, указанных в регулярном выражении, либо " +
        "нужную группу в виде строки" +
        "4 - методы замены: заменяют совпадения в тексте другой предоставленной строкой")

@Ntrstn("Методы matches() (класс Matcher) и lookingAt() ищут совпадения в тексте по шаблону, но " +
        "метод matches() вернет true, только если с шаблоном совпадает весь текст, тогда как метод " +
        "lookingAt() вернет true и для отрезка текста, поскольку он просто начинает искать " +
        "совпадения с начала текстового диапазона.")

@Ntrstn("В отличие от методов matches() и lookingAt(), которые всегда начинают работать с начала " +
        "текста, метод find() начинает поиск с того места, где завершилось последнее вхождение (или " +
        "с начала, если до этого не запускался или не была совершена перезагрузка методом reset()). " +
        "Т.е. по сути, это удобный метод, чтобы переходить с одного вхождения на следующее. Можно " +
        "также указать индекс с символа, с которого нужно начинать поиск вхождения")

@Ntrstn("Методы класса String дублируют методы Pattern: поиск совпадений и разбивание строки на " +
        "массив в местах совпадений. Также они дублируют методы замены совпадений из класса Matcher")

@Ntrstn("Методы класса PatternSyntaxException позволяют отдельно получить описание, индекс ошибки и " +
        "сам проблемный паттерн. А метод getMessage() сочетает всю эту информацию")


@Ntrstn("Сверка текста с шаблоном осуществляется посимвольно. Существуют классы символов (не те, что " +
        "в Java), которые указываются в скобках шаблона и с которыми идет сверка. Они указываются в " +
        "квадратных скобках, напр. [abc] - означает, что совпадение произойдет, если проверяемый " +
        "символ в тексте является либо а, либо b либо c. Это же условие можно записать, например, " +
        "как [a|b|c]. А [0-9] означает, что совпадение произойдет, если проверяемый символ является " +
        "любым числом. Для удобства и легкости чтения, существуют также предустановленные классы " +
        "символов и POSIX. Например, [0-9] можно записать как \\d или как \\p{Digit} (верcия POSIX)" +
        "В некоторых случаях варианты POSIX являются короче других предустановленных. Всегда стоит " +
        "пользоваться наиболее короткими и удобочитаемыми версиями")


@Ntrstn("В регулярных выражения ьакже используются && для логического И и | для логического ИЛИ ")

@Ntrstn("Совпадения нулевой длины (zero-length matches) - начинаются и заканчиваются в одной и той " +
        "же позиции индекса. Они происходят в следующих случаях: в пустых строках, в начале " +
        "введенной строки, после последнего символа введенной строки или между 2 символами введенной " +
        "строки ")


public class Main {

    public static void main(String[] args) {
        String regex;
        String text;
        Pattern p;
        Matcher m;

        /*~~~~~~~~~~~~~~~~~~СПОСОБЫ ВАЛИДАЦИИ ТЕКСТА ПО ШАБЛОНУ~~~~~~~~~~~~~~~~~~*/
        System.out.println("ПРОВЕРКА ВСЕГО ТЕКСТА НА СООТВЕТСТВИЕ ШАБЛОНУ");
        /* КЛАССИЧЕСКИЙ: PATTERN + MATCHER */
        p = Pattern.compile("[Ss]ome string"); // задается необходимый для поиска шаблон
        m = p.matcher("Some string"); // задается текст, где производится поиск
        System.out.println("Классическая проверка: " + m.matches()); // производится проверка
        /* БЫСТРЫЙ: БЕЗ СОЗДАНИЯ ОБЪЕКТА MATCHER */
        System.out.println("Быстрая проверка без объекта Matcher: "
                + Pattern.matches("[Ss]ome string", ("Some string")));
        /* БЫСТРЫЙ: БЕЗ СОЗДАНИЯ СОЗДАНИЯ ОБЪЕКТОВ PATTERN И MATCHER */
        System.out.println("Быстрая проверка без объектов Pattern и Matcher: "
                + "Some string".matches("[Ss]ome string"));


        /*~~~~~~~~~~~~~~~~~~~~ФЛАГИ PATTERN.COMPILE()~~~~~~~~~~~~~~~~~~~~ */
        System.out.println("ФЛАГИ PATTERN.COMPILE() И ИХ СИНОНИМЫ");
        /* CASE_INSENSITIVE - ИГНОРИРОВАНИЕ РЕГИСТРА (+ ДЛЯ СИМВОЛОВ UNICODE)*/
        p = Pattern.compile("строка", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        m = p.matcher("СТРОКА");
        System.out.println("Игнорирование регистра: " + m.matches());
        System.out.println("Синоним:" + "some string".matches("(?i)SOME STRING"));

        /* COMMENTS - ИГНОРИРОВАНИЕ ПРОБЕЛОВ И КОММЕНТАРИЕВ В ПАТТЕРНЕ */
        p = Pattern.compile("some string", Pattern.COMMENTS);
        m = p.matcher("somestring");
        System.out.println("Игнорирование пробелов и комментариев: " + m.matches());
        System.out.println("Синоним:" + "somestring".matches("(?x)some string"));

        /* MULTILINE - РАЗРЕШЕНИЕ МНОГОСТРОЧНОГО РЕЖИМА ПАТТЕРНА */


        /* ~~~~~~~~~~~~~~~~~~~~ДРУГИЕ ВАЖНЫЕ МЕТОДЫ PATTERN~~~~~~~~~~~~~~~~~~~~
         * - compile() и matches() см. выше*/
        System.out.println("ДРУГИЕ ВАЖНЫЕ МЕТОДЫ PATTERN");
        /* SPLIT() - РАЗДЕЛИТЬ СТРОКУ НА МАССИВ ПО МЕСТАМ СОВПАДЕНИЙ */
        p = Pattern.compile(" ");
        String[] sa = p.split("one two three");
        System.out.println("split() делит строку на массив: " + Arrays.toString(sa));

        /*TOSTRING() - ВЕРНУТЬ РВ ОБРАТНО СТРОКОЙ */
        p = Pattern.compile("Blah");
        System.out.println("toString(): вернет шаблон обратно строкой: " + p);

        /*QUOTE() - ВЕРНУТЬ СТРОКОЙ ШАБЛОН, ПОДХОДЯЩИЙ ДЛЯ ВВЕДЕННОЙ СТРОКИ */
        System.out.println("Подобрать шаблон: " + Pattern.quote("123xxxAAA")); // \Q123xxxAAA\E


        /* ~~~~~~~~~~~~~~~~~~~~АНАЛОГИЧНЫЕ МЕТОДЫ И КЛАССА STRING ~~~~~~~~~~~~~~~~~~~~
         * - matches() см. выше */
        System.out.println("АНАЛОГИЧНЫЕ МЕТОДЫ ИЗ КЛАССА STRING");
        /* SPLIT() */
        text = "Split this string";
        System.out.println("split(): " + Arrays.toString(text.split(" ")));


        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ГРУППЫ МЕТОДОВ MATCHER~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        System.out.println("ГРУППЫ МЕТОДОВ MATCHER");
        regex = "(i(f))";
        text = "ififif";
        p = Pattern.compile(regex);
        m = p.matcher(text);
        /*~~~~~~~~~~~~ МЕТОДЫ ИЗУЧЕНИЯ ~~~~~~~~~~~~
         * - кроме matches()*/
        /* LOOKINGAT() - поиск совпадения начиная с начала диапазона, а не для ВСЕГО диапазона*/
        System.out.println("Соответствие шаблону найдено: " + m.lookingAt()); // true

        /* FIND() - поиск следующего куска текста, подходящего под паттерн
         * - find(int index) - перезагрузить поиск и начать с указанного символа */
        System.out.println("Соответствие шаблону найдено: " + m.find(3)); // true


        /*~~~~~~~~~~~~ МЕТОДЫ ЗАМЕНЫ ~~~~~~~~~~~~*/
        /* START() - стартовый индекс последнего совпадения
         *   - start(int group)*/
        System.out.println("Стартовый индекс последнего вхождения: " + m.start()); // 4
        System.out.println("Стартовый индекс последнего вхождения группы 2: " + m.start(2)); // 5
        /* END () - индекс, следующий за последним совпадением
         * - end(int group)*/
        System.out.println("Последний индекс последнего вхождения (+1): " + m.end()); // 6
        System.out.println("Последний индекс последнего вхождения (+1) группы 2: " + m.end(2)); // 6


        /*~~~~~~~~~~~~ МЕТОДЫ ГРУПП ~~~~~~~~~~~~*/
        /*GROUP(int number) - вернуть строкой группу по указанному номеру*/
        System.out.println("Количество групп в выражении: " + m.group(2)); // f
        /*GROUPCOUNT() - количество групп в выражении (без учета нулевой)*/
        System.out.println("Количество групп в выражении: " + m.groupCount()); // 2


        /*~~~~~~~~~~~~ МЕТОДЫ ЗАМЕНЫ ~~~~~~~~~~~~*/
        /* REPLACEFIRST() - замена первого вхождения*/
        System.out.println("Замена первого вхождения: " + m.replaceFirst("1")); //1ifif
        /* REPLACEALL() - замена всех вхождений*/
        System.out.println("Замена всех вхождений: " + m.replaceAll("2")); //222


        /* ~~~~~~~~~~~~~~~~~~~~АНАЛОГИЧНЫЕ МЕТОДЫ И КЛАССА STRING ~~~~~~~~~~~~~~~~~~~~ */
        System.out.println("АНАЛОГИЧНЫЕ МЕТОДЫ ИЗ КЛАССА STRING");
        regex = "(i(f))";
        text = "ififif";
        /* REPLACEFIRST() - замена первого вхождения*/
        System.out.println("Замена первого вхождения: " + text.replaceFirst(regex, "1")); //1ifif
        /* REPLACEALL() - замена всех вхождений*/
        System.out.println("Замена всех вхождений: " + text.replaceAll(regex, "2")); //222


        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ МЕТОДЫ PATTERNSYNTAXEXCEPTION~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        System.out.println("МЕТОДЫ КЛАССА PATTERNSYNTAXEXCEPTION");
//        regex = "(i&"; // нужно сначала раскомитить
        text = "ififif";
        try {

            p = Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            System.out.println(e.getMessage()); // Unclosed group near index 3 (i&
            //                                                                   ^
        }


        /*
         *//*~~~~~~~~~~~~~~~~~~~~КЛАССЫ (ТИПЫ) СИМВОЛОВ~~~~~~~~~~~~~~~~~~~~*//*
         *//* ПРОСТОЙ КЛАСС *//*
        Pattern patternClassSimple = Pattern.compile("[Ss]ome string"); // любой из указанных символов
        *//* ОТРИЦАНИЕ *//*
        Pattern patternClassNegation = Pattern.compile("[^A]ome string"); // не указанный символ
        *//* ДИАПАЗОН *//*
        Pattern patternClassRange = Pattern.compile("[A-Z]ome string"); // любой из указанного диапазона
        *//* ОБЪЕДИНЕНИЕ *//*
        Pattern patternClassUnion = Pattern.compile("[A-E[N-Z]]ome string");// любой из указанных диапазонов
        *//* ПЕРЕСЕЧЕНИЕ *//*
        Pattern patternClassIntersection = Pattern.compile("[A-Z&&[OPS]]ome string"); // любой из пересечения
        *//* ВЫЧИТАНИЕ *//*
        Pattern patternClassSubtraction = Pattern.compile("[A-Z&&[^PQ]]ome string");
        Pattern patternClassSubtraction2 = Pattern.compile("[A-Z&&[^D-F]]ome string");

        Matcher matcherClassSimple = patternClassSimple.matcher("Some string");
        Matcher matcherClassRange = patternClassRange.matcher("Some string");
        Matcher matcherClassUnion = patternClassUnion.matcher("Some string");
        Matcher matcherClassIntersection = patternClassIntersection.matcher("Some string");
        Matcher matcherClassSubtraction = patternClassSubtraction.matcher("Some string");
        Matcher matcherClassSubtraction2 = patternClassSubtraction2.matcher("Some string");

        *//*~~~~~~~~~~~~~~~~~~~~ПРЕДУСТАНОВЛЕННЫЕ КЛАССЫ СИМВОЛОВ~~~~~~~~~~~~~~~~~~~~*//*
         *//*ЛЮБОЙ СИМВОЛ*//*
        Pattern patternPredefinedAny = Pattern.compile("[.]ome string");
        *//*ЦИФРОВОЙ СИМВОЛ*//*
        Pattern patternPredefinedDigit = Pattern.compile("[\\d]ome string");
        *//*НЕЦИФРОВОЙ СИМВОЛ*//*
        Pattern patternPredefinedNonDigit = Pattern.compile("[\\D]ome string");
        *//*ПУСТОЙ СИМВОЛ*//*
        Pattern patternPredefinedSpace = Pattern.compile("Some[\\s]string");
        *//*НЕПУСТОЙ СИМВОЛ*//*
        Pattern patternPredefinedNonSpace = Pattern.compile("[\\S]ome string");
        *//*СИМВОЛ В СЛОВЕ*//*
        Pattern patternPredefinedWord = Pattern.compile("[\\w]ome string");
        *//*СИМВОЛ В НЕСЛОВЕ*//*
        Pattern patternPredefinedNonWord = Pattern.compile("Some[\\W]string");

        Matcher matcherPredefinedAny = patternPredefinedAny.matcher("Some string");
        Matcher matcherPredefinedDigit = patternPredefinedDigit.matcher("Some string");
        Matcher matcherPredefinedNonDigit = patternPredefinedNonDigit.matcher("Some string");
        Matcher matcherPredefinedSpace = patternPredefinedSpace.matcher("Some string");
        Matcher matcherPredefinedNonSpace = patternPredefinedNonSpace.matcher("Some string");
        Matcher matcherClassPredefinedWord = patternPredefinedWord.matcher("Some string");
        Matcher matcherPredefinedNonWord = patternPredefinedNonWord.matcher("Some string");


        *//* ~~~~~~~~~~~~~~~~~~~~КВАНТИФИКАТОРЫ~~~~~~~~~~~~~~~~~~~~ *//*
         *//* 1 ИЛИ НИ ОДНОГО *//*
        Pattern patternQuantifierOnceOrZero = Pattern.compile("[S]?ome string");
        *//* 0 ИЛИ БОЛЕЕ *//*
        Pattern patternQuantifierZeroOrMore = Pattern.compile("[S]*ome string");
        *//* 1 ИЛИ БОЛЕЕ *//*
        Pattern patternQuantifierOnceOrMore = Pattern.compile("[S]+ome string");
        *//* УКАЗАННОЕ КОЛИЧЕСТВО РАЗ *//*
        Pattern patternQuantifierNTimes = Pattern.compile("[S]{1}ome string");
        *//* МИНИМУМ УКАЗАННОЕ КОЛИЧЕСТВО РАЗ *//*
        Pattern patternQuantifierMinNTimes = Pattern.compile("[S]{1,}ome string");
        *//* ДИАПАЗОН УКАЗАННЫХ РАЗ *//*
        Pattern patternQuantifierRangeTimes = Pattern.compile("[S]{1,10}ome string");

        Matcher matcherQuantifierOnceOrZero = patternQuantifierOnceOrZero.matcher("Some string");
        Matcher matcherQuantifierZeroOrMore = patternQuantifierZeroOrMore.matcher("Some string");
        Matcher matcherQuantifierOnceOrMore = patternQuantifierOnceOrMore.matcher("Some string");
        Matcher matcherQuantifierNTimes = patternQuantifierNTimes.matcher("Some string");
        Matcher matcherQuantifierMinNTimes = patternQuantifierMinNTimes.matcher("Some string");
        Matcher matcherQuantifierRangeTimes = patternQuantifierRangeTimes.matcher("Some string");


        *//* ~~~~~~~~~~СРАВНЕНИЕ КВАНТИФИКАТОРОВ~~~~~~~~~~ *//*
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


        *//* ЗАХВАТ ГРУППЫ *//*





         *//*~~~~~~~~~~~~~~~~~~~~ МЕТОДЫ MATCHER ~~~~~~~~~~~~~~~~~~~~*//*
         *//* MATCHES() - попытка сверить весь диапазон с паттерном *//*
        System.out.println("matcher: " + mClassic.matches()); // false, должен полностью соответствовать вводу
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





        *//* FIND() - поиск следующей подпоследовательности, которая соответствует паттерну *//*
        System.out.println(mClassic.find()); // true




        *//* ~~~~~~~~~~~~~~~~~~~~СОВПАДЕНИЯ НУЛЕВОЙ ДЛИНЫ~~~~~~~~~~~~~~~~~~~~ *//*
         *//* ПУСТЫЕ СТРОКИ *//*
        String s1 = "a";
        // 2 совпадения: "a" - начало index 0 и конец index 1, "" - начало index 1 и конец index 1
        String regex = "a?"; // 1ое совпадение: на index 0 and ending at index 1
        String regex2 = "a*"; //
        // 1 совпадение: "a" - начало index 0 и конец index 1
        String regex3 = "a+*"; //
*/



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
