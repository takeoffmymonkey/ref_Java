package types_references_strings;
import types_references_annotations.my_annotations.Ntrstn;

/* КЛАСС STRING
 * public final class String
 * extends Object
 * implements Serializable, Comparable<String>, CharSequence */


/* СОЗДАЮТСЯ
 * - строковыми литералами (в кавычках "")
 *      + при этом необязательно присваивать то, что в кавычках, переменной
 * - конструкторами (15 штук), например:
 *      + с помощью пустого конструктора
 *      + с помощью другой строковой переменной
 *      + с помощью массива символов
 *      + c помощью StringBuffer
 *      + c помощью StringBuilder */


/* СТРОКОВЫЙ ОБЪЕКТ НЕ МОЖЕТ БЫТЬ ИЗМЕНЕН
 * - результат операции со строкой всегда возвращает новую строку (кроме интернации)
 * - если нужно, чтоб объект можно было менять, используй StringBuffer или StringBuilder*/


/* ПУСТАЯ СТРОКА
 * – это объект символьной строки, у которой пустое содержимое (т.е. "")
 * - ссылочная переменная строчного типа может быть null (и скоро быть удалена сборщиком мусора)*/


/* КОНКАТЕНАЦИЯ
 * - String - единственный класс, для которого перепределена операция (+)
 *
 * - способы:
 *       + +
 *       + +=
 *       + StringBuilder.append()
 *       + String.join(" ", "чо", "как", "ваще") (с Java 8)
 *       + concat:
 *          - используй, если нужно конкатенировать только 1 раз
 *          - для остальных случаев, лучше + или StringBuffer/StringBuilder
 *          - может дать NullPointerException, если один из операндов равен null (в отличие от +, +=)
 *
 * - в цикле может приводить к перерасходу памяти - лучше использовать объекты StringBuffer/
 * StringBuilder
 *
 * - если выражение начинается со строки, то все последующие операнды также должны быть строками
 *      + но можно обойти заключением нужного выражения в скобки
 *
 * - при присоединении null-овой строковой ссылки, null добавляется как строка:
 *      + справедливо для всех типов строковых ссылок: String, StringBuffer, StringBuilder*/


/* STRING POOL (СТРОКОВЫЙ ПУЛ)
 * - множество строк в куче, где каждая имеет только 1 экземпляр
 * - сюда попадают только интернированные строки
 * - экономит ресурсы
 * - пул хранится в области «Perm Gen», которая зарезервирована для non-user объектов JVM (классы и
 * пр). Если этого не учитывать, можно получить OutOfMemory Error*/


/* ИНТЕРНАЦИЯ
 * - помещение (автоматическое или ручное) строки в пул с гарантией, что она будет в 1 экземпляре
 * - нужна, чтобы:
 *      + сохранить память
 *      + быстро сравнивать строки оператором ==:
 *          - прирост быстродействия будет ощутим только, если:
 *              + сравнение строк является частой операцией в программе
 *              + строки одинаковые по длине
 *
 * - автоматическая интернация:
 *      + работает для:
 *          - строковых литералов
 *          - константных выражений
 *      + НЕ работает для:
 *          - строковых объектов
 *          - конкатенации во время исполнения
 *
 * - ручная интернация:
 *      + intern() проверяет, есть ли в пуле такой объект и возвращает его (при необходимости создает)*/


/* СРАВНЕНИЕ
 * - == для определения, ссылаются ли 2 объекта на 1 и ту же область памяти:
 *      + быстрый, но не работает для разных объектов (не интернированных)
 * - f.equals(d) сравнивает содержат ли оба объекта одни и те же данные:
 *      + медленный, но сравнивает содержание (посимвольно)
 * - сравнение двух null строк
 *         + оператором ==: вернет true
 *         + методом equals: NullPointerException (но можно написать специальный метод - см. внизу)*/


/*ОТЛИЧИЕ .EQUALS() от COMPARETO()
 * - equals():
 *      + дает результат сравнения на равенство содержания объектов: true или false
 *      + принимает любой объект в качестве параметра
 *
 * - compareTo():
 *      + предназначен для сортировки (т.к. сравнивает пока символы не начнут различаться)
 *      + является конкретной реализацией интерфейса Comparable (т.е. примет только String)
 *      + дает результат лексографического сравнения символа (основываясь на значении символа в
 *      Unicode):
 *          + 0 - символы находятся на одной и той же позиции (и, следовательно, нужно продолжить
 *          сравнение)
 *          + -5 - символ находится на 5 позицию назад от символа сравнения (сравнение прекращается)
 *          + +11 - символ находится на 11 позиций вперед от символа сравнения (сравнение прекращается) */


/* ДРУГИЕ МЕТОДЫ СРАВНЕНИЯ
 * - endsWith(): строка заканчивается на указанный суффикс?
 * - startsWith(): строка начинается с указанного префикса?
 * - compareToIgnoreCase(): то же, что compareTo, но без учета регистра
 * - equalsIgnoreCase(): то же, что equals, но без учета регистра
 * - regionMatches(): сравнение куска одной строки с куском такой же длину у второй
 * - matches(): сравнивает строку с регулярным выражением */


/* ПРЕОБРАЗОВАНИЯ
 * - число в строку:
 *      + конкатенация с пустой строкой
 *      + метод String.valueOf()
 *      + метод Integer.toString()
 * - строка в число
 *      + Integer.parseInt()
 *      + Integer.valueOf()*/


/* ФОРМАТИРОВАНИЕ
 * - результат статического метода String.format (функционал основан на классе Formatter) аналогичен
 * printf (только этот еще и печатает)
 * - для объектов String точность задет максимальное количество выводимых символов
 * - спецификатор s можно использовать для форматирования любого объекта:
 *         + eсли этот объект реализует интерфейс Formattable, вызывается метод formatTo().
 *         + если нет, для применяется метод toString().*/


/* ИСПОЛЬЗОВАНИЕ С РЕГУЛЯРНЫМИ ВЫРАЖЕНИЯМИ
 * todo пример регулярных выражений со строками*/


/* СТРОКИ ПОТОКОБЕЗОПАСНЫ
 * - можно использовать строку во многих потоках без дополнительной синхронизации
 * todo пример потокобезопасности строк*/


/* ИСПОЛЬЗОВАНИЕ СТРОК В КАЧЕСТВЕ КЛЮЧЕЙ В HASHMAP
 * - ключ рекомендуется делать неизменяемым
 * todo пример использования строк в качестве ключей в hashmap*/


/* ДРУГИЕ ПОЛЕЗНЫЕ МЕТОДЫ
 * - charAt(int index): Возвращает символ по указанному индексу.
 * - contentEquals(StringBuffer sb): Возвращает значение true только в том случае, если эта строка
 * представляет собой ту же последовательность символов как указанно в буфере строки (StringBuffer).
 * - copyValueOf(char[] data): Возвращает строку, которая представляет собой последовательность
 * символов, в указанный массив.
 * - getBytes(): Кодирует эту строку в последовательность байтов с помощью платформы charset,
 * сохраняя результат в новый массив байтов.
 * - getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin): Копирует символы из этой строки в
 * массив символов назначения.
 * - indexOf(int ch): Возвращает индекс первого вхождения указанного символа в данной строке.
 * - lastIndexOf(int ch): Возвращает индекс последнего вхождения указанного символа в этой строке.
 * - length(): Возвращает длину строки.
 * - replace(char oldChar, char newChar): Возвращает новую строку, в результате, заменив все вхождения
 * oldChar в этой строке на newChar.
 * - replaceAll(String __regex, String replacement): Заменяет каждую подстроку строки, соответствующей
 * заданному регулярному выражению с данной заменой.
 * - replaceFirst(String __regex, String replacement): Заменяет первые подстроки данной строки, которая
 * соответствует заданному регулярному выражению с данной заменой.
 * - split(String __regex): Разделяет эту строку, окружая данным регулярным выражением.
 * - substring(int beginIndex): Возвращает новую строку, которая является подстрокой данной строки.
 * - toCharArray(): Преобразует эту строку в новый массив символов.
 * - toLowerCase(): Преобразует все символы в данной строке в нижний регистр, используя правила
 * данного языкового стандарта.
 * - toString(): Этот объект (который уже является строкой!) возвращает себя.
 * - toUpperCase(): Преобразует все символы в строке в верхний регистр, используя правила данного
 * языкового стандарта.
 * - trim(): Возвращает копию строки с пропущенными начальными и конечными пробелами.
 * - valueOf(primitive data type x): Возвращает строковое представление переданного типа данных
 * аргумента.*/

@Ntrstn("Класс String нельзя наследовать, а сами строки являются неизменяемыми, и все методы, " +
        "которые их изменяют, на самом деле возвращают ссылку на новый экземпляр String с " +
        "добавленными изменениями. Как, собственно, метод и должен себя вести - не изменять принятый " +
        "аргумент")

@Ntrstn("На самом деле для повышения эффективности для каждой конкатенации не создается новая строка, а компилятор использует " +
        "класс StringBuilder с методом append() для каждого действия, а в конце вызывается toString. " +
        "При это стрингбилдер не появляется в исходном коде самой программы. Но при конкатенации в " +
        "цикле, StringBuilder будет создаваться каждый проход цикла, поэтому String нельзя использовать " +
        "в цикле, а нужно явно стрингбилдер")

@Ntrstn("Для объектов при конкатенации вызывается их метод toString(). Если у них этот метод " +
        "переопределен и в переопределении есть вызов самого себя (при помощи this - напр. чтобы " +
        "выводить адрес в памяти), то возникнет рекурсия. Чтобы исправить это, нужно вместо this " +
        "вызывать super.toString() ")

@Ntrstn("методы split и matches дублируют методы из Pattern, а методы replace... - из Matcher")
public class Strings {

    static String stringA;
    static String stringA1;
    static String stringA2;
    static StringBuilder stringBuilderA = new StringBuilder("");
    static int intA;

    public static void main(String[] args) {

        /*~~~~~~~~~~~~~~СОЗДАНИЕ~~~~~~~~~~~~~~*/
        /*С ПОМОЩЬЮ КАВЫЧЕК */
        String a = ""; // объект с нулевой длиной символьной строки и пустое содержимое
        String b = "test";
        String c = "te" + "st";
        System.out.println("test");//создался объект и вывелось его значение (без присваивания)


        /*С ПОМОЩЬЮ КОНСТРУКТОРОВ (НАИБОЛЕЕ ИСПОЛЬЗУЕМЫЕ)*/
        String first = new String(); // пустой конструктор
        String habr = "habrahabr";
        String second = new String(habr); // с помощью другой строковой переменной
        char[] habrAsArrayOfChars = {'h', 'a', 'b', 'r', 'a', 'h', 'a', 'b', 'r'};
        String third = new String(habrAsArrayOfChars); // с помощью массива символов
        String sixth = new String(new StringBuffer(habr)); // c помощью StringBuffer
        String seventh = new String(new StringBuilder(habr)); // c помощью StringBuilder


        /*~~~~~~~~~~~~~~СТРОКОВЫЙ ОБЪЕКТ НЕ МОЖЕТ БЫТЬ ИЗМЕНЕН~~~~~~~~~~~~~~*/
        stringA = stringA + " "; // вернет новый объект
        stringBuilderA = stringBuilderA.append(" "); // объект остается
        stringA = "aaa"; // интернация
        stringA1 = "aa" + "a"; // интернация
        System.out.println(stringA == stringA1); // true, то есть это тот же интернированный объект


        /*~~~~~~~~~~~~~~ПУСТАЯ СТРОКА~~~~~~~~~~~~~~*/
        stringA = ""; // пустая строка
        stringA = null; // ссылается на null


        /*~~~~~~~~~~~~~~КОНКАТЕНАЦИЯ~~~~~~~~~~~~~~*/
        stringA = "r" + "evolution"; // с помощью оператора +
        stringA += "rewerwerew"; // c помощью операции совмещенного присваивания
        stringBuilderA.append("dfs"); // с помощью метода append у StringBuilder
        stringA = String.join(" ", "чо", "как", "ваще"); // методом join
        stringA = stringA.concat("hello"); // с помощью метода concat


        /* СONCAT МОЖЕТ ПРИВОДИТЬ К NULLPOINTEREXCEPTION */
        stringA1 = null;
        stringA = stringA + stringA1; // просто добавить "null" к концу строки
        // stringA = stringA.concat(stringA1); // java.lang.NullPointerException


        /* В ЦИКЛЕ МОЖЕТ ПРИВОДИТЬ К ПЕРЕРАСХОДУ ПАМЯТИ */
        for (int i = 0; i < 20; i++) {
            stringA += "rerere"; // каждый раз создает новый объект строки
        }
        for (int i = 0; i < 20; i++) {
            stringBuilderA.append("rerere"); // объект остается 1
        }


        /* ЕСЛИ ВЫРАЖЕНИЕ НАЧИНАЕТСЯ СО СТРОКИ, ТО ВСЕ ПОСЛЕДУЮЩИЕ ОПЕРАНДЫ СТАНОВЯТСЯ СТРОКАМИ */
        System.out.println("sum " + 1 + 2); // даст sum 12
        System.out.println("sum " + (1 + 2)); // даст sum 3
        System.out.println(1 + 2 + "sum"); // 3sum


        /* ПРИ ПРИСОЕДИНЕНИИ NULL-ОВОЙ СТРОКОВОЙ ССЫЛКИ, NULL ДОБАВЛЯЕТСЯ КАК СТРОКА */
        stringA = "aaa";
        stringA1 = null;
        stringBuilderA = null;
        stringA = stringA + stringA1 + stringBuilderA; // даст aaanullnull


        /*~~~~~~~~~~~~~~STRING POOL (СТРОКОВЫЙ ПУЛ)~~~~~~~~~~~~~~*/
        stringA = "aaa";
        stringA1 = "aaa";
        System.out.println(stringA == stringA1); // даст true, так как это 1 объект


        /*~~~~~~~~~~~~~~ИНТЕРНАЦИЯ~~~~~~~~~~~~~~*/
        /* АВТОМАТИЧЕСКАЯ ИНТЕРНАЦИЯ */
        stringA = "aaa"; // строковый литерал
        stringA1 = "aa" + "a"; // константное выражение
        System.out.println(stringA == stringA1); // даст true


        /* АВТОМАТИЧЕСКАЯ ИНТЕРНАЦИЯ НЕ ПРОИСХОДИТ */
        stringA = new String("aaa"); // строковый объект
        stringA1 = "aaa";
        stringA2 = "" + stringA1; // конкатенация во время исполнения
        System.out.println(stringA == stringA1); // даст false
        System.out.println(stringA2 == stringA1); // даст false


        /* РУЧНАЯ ИНТЕРНАЦИЯ */
        stringA = new String("aaa").intern(); // проверяет, есть ли в пуле такой объект (и создает
        // его при необходимости)
        stringA = "aaa".intern(); // бессмыслено


        /*~~~~~~~~~~~~~~СРАВНЕНИЕ~~~~~~~~~~~~~~*/
        /* == ОПРЕДЕЛЯЕТ, ССЫЛАЮТСЯ ЛИ 2 ОБЪЕКТА НА 1 И ТУ ЖЕ ОБЛАСТЬ ПАМЯТИ*/
        stringA = "aaa";
        stringA1 = "aaa";
        System.out.println(stringA == stringA1); // даст true (строки были интернированы)
        String unInternedString = new String("aaa");
        String unInternedString2 = new String("aaa");
        System.out.println(unInternedString == unInternedString2); // даст false (не было интернации)


        /*.EQUALS() СРАВНИВАЕТ, СОДЕРЖАТ ЛИ 2 ОБЪЕКТА ОДНИ И ТЕ ЖЕ ДАННЫЕ (ПОСИМВОЛЬНО) */
        stringA = "bbb";
        stringA1 = "bbb";
        System.out.println("sdf" + stringA.equals(stringA1)); // даст true


        /* СРАВНЕНИЕ 2 NULL СТРОК */
        stringA = null;
        stringA1 = null;
        System.out.println(stringA == stringA1); // true
        // System.out.println(stringA.equals(stringA1)); // NullPointerException
        System.out.println(equals(stringA, stringA1)); // обходной метод, даст true


        /* ОТЛИЧИЕ .EQUALS() от COMPARETO() */
        stringA = "a";
        stringA1 = "a";
        stringA2 = "A";
        System.out.println(stringA.equals(stringA1)); // true (простое сравнение всего содержания)
        System.out.println(stringA.equals(stringA2)); // false (простое сравнение всего содержания)

        stringA = "a";
        stringA1 = "a";
        stringA2 = "A";
        System.out.println(stringA.compareTo(stringA1)); // 0 (т.е. равны)
        System.out.println(stringA.compareTo(stringA2)); // 32 (большая A отстает от маленькой а в
        // в Unicode на 32 символа)

        stringA = "aB ";
        stringA1 = "aBa";
        System.out.println(stringA.compareTo(stringA1)); // -65 (маленькая а опережает пустоту в
        // в Unicode на 65 символов)


        /* ДРУГИЕ МЕТОДЫ СРАВНЕНИЯ*/
        stringA = "a";
        stringA1 = "A";
        System.out.println(stringA.compareToIgnoreCase(stringA1)); // даст 0
        System.out.println(stringA.equalsIgnoreCase(stringA1)); // даст true
        stringA = "Abstract";
        stringA1.startsWith("Abs"); // даст true
        stringA1.endsWith("act"); // даст true
        stringA = "some long string";
        stringA1 = "very long string....";
        System.out.println(stringA.regionMatches(4, stringA1, 4, 12)); // true
        //TODO: добавить пример boolean matches(String __regex)


        /*~~~~~~~~~~~~~~ПРЕОБРАЗОВАНИЯ~~~~~~~~~~~~~~*/
        /*ЧИСЛО В СТРОКУ*/
        stringA = 123 + ""; // конкатенация с пустой строкой
        stringA = String.valueOf(123); // метод String.valueOf()
        stringA = Integer.toString(123); // метод Integer.toString()


        /*СТРОКУ В ЧИСЛО*/
        intA = Integer.parseInt(stringA);
        intA = Integer.valueOf(stringA);


        /*~~~~~~~~~~~~~~ФОРМАТИРОВАНИЕ~~~~~~~~~~~~~~*/
        stringA = "Илюха";
        intA = 34;
        stringA1 = String.format("%s, в этом году тебе будет %d", stringA, intA);
        System.out.println(stringA1);
        System.out.printf("%s, в этом году тебе будет %d", stringA, intA); // то же самое
        stringA1 = String.format("Илюха, в этом году тебе будет %s", intA); // спецификатор s
        // работает для всех объектов (вызывается toString() либо formatTo())
        System.out.println(stringA1);


        /* РЕГУЛЯРНЫЕ ВЫРАЖЕНИЯ */


        /* СТРОКИ ПОТОКОБЕЗОПАСНЫ */


        /* ИСПОЛЬЗОВАНИЕ СТРОК В КАЧЕСТВЕ КЛЮЧЕЙ В HASHMAP */

    }

    static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }
}