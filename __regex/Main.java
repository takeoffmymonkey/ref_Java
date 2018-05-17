package __regex;

import types_references_annotations.my_annotations.Ntrstn;

/*Методы String для работы с регулярными выражениями:
 * - matches(regex) - соответствует ли строка выражению
 * - split(regex) - разделить строку по выражению
 * - replaceFirst(regex, to), replaceAll(regex, to), - заменить в строке по выражению
 * */

/* СИМВОЛЫ
 * B: символ В
 * \xhh: символ  с 16-ричным кодом 0xhh
 * \uhhhh: символ Unicode с 16-ричным представлением 0xhhhh
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


/*Pattern*/


/*Matcher*/


@Ntrstn("Регулярные выражения - мощный и гибкий инструмент обработки текстов. Они позволяют " +
        "определять на программном уровне сложные шаблоны для поиска текста во входной строке. " +
        "Обнаружив совпадение для шаблона, его можно обработать как нужно. Это компактный и " +
        "динамичный язык, который может использоваться для решения самых разнообразных задач " +
        "обработки строк, поиска и выделения совпадений, редактирования и проверки")


public class Main {

    public static void main(String[] args) {
        /*"Необязательный знак "минус", за которым следует однак или нескольких цифр:
         *      -?\d+ */
        System.out.println("-1234".matches("-?\\d+")); // true
        System.out.println("1234".matches("-?\\d+")); // true
        System.out.println("+1234".matches("-?\\d+")); // false
        /*То же, но либо плюс либо минус:
         *      (-|\\+)?\\d+ */
        System.out.println("+1234".matches("(-|\\+)?\\d+")); // false

    }
}
