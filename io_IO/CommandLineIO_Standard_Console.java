package io_IO;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import _types_references_annotations.my_annotations.Ntrstn;


/* СТАНДАРТНЫЕ ПОТОКИ
 * - "стандартный io" - концепция (UNIX) единого потока информации, используемого программой
 *      - чтобы соединять выход одной программы со входом другой при помощи стандарта
 *
 * - стандартный поток ввода (клавиатура): System.in
 *      - откуда приходит информация
 *      - объект InputStream
 *          - т.е. при работе нужно использовать обертку
 *              - напр. new BufferedReader(new InputStreamReader(System.in));
 *
 * - стандартный поток вывода (дисплей): System.out
 *      - куда записывается информация
 *      - объект PrintStream
 *          - добавляет функционал символьной печати к объекту OutputStream
 *              - используется стандартная кодировка платформы
 *
 * - стандартный поток ошибок: System.err
 *      - объект PrintStream
 *          - добавляет функционал символьной печати к объекту OutputStream
 *              - используется стандартная кодировка платформы
 *
 * - потоки можно перенаправлять методами: System.setIn(), System.setOut(), System.setErr() */


/* JAVA.IO.CONSOLE - БОЛЕЕ ПРОДВИНУТАЯ ВЕРСИЯ СТАНДАРТНЫХ ПОТОКОВ
 * - public final class Console extends Object implements Flushable
 *
 * - символьное консольное устройство, ассоциированное с JVM
 *      - т.е. основано на Reader и Writer
 *      - может отстутствовать - зависит от метода запуска JVM
 *          - если JVM запущена из командной строки без перенаправления стандартных I/O потоков, то
 *          консоль будет и будет приаттачена к клавиатуре и дисплею, где запущена JVM
 *          - если JVM запущена автоматически, например, background job scheduler, то консоли нет
 *
 * - получить консоль от JVM: System.console()
 *      - вернет null, если консоли нет
 *
 * - R/W операции синхронизированы для гарантирования атомарности критических операций
 *      - поэтому могут блокировать потоки
 *
 * - вызов close() на объектах, возвращенных от reader() и writer() не закрывают потоки в них
 *
 * - при достижении конца входного потока методы чтения возвращают null
 *
 * - если приложению нужно читать пароль или другие важные данные, нужно использовать readPassword()
 * или readPassword(String, Object...) и вручную менять все на 0 в возвращенном символьном массиве,
 * чтобы минимизировать срок жизни данных в памяти
 *
 * - методы:
 *      - flush()
 *      - format(String fmt, Object... args): пишет отформатированную строку в выходной поток консоли
 *      - printf(String format, Object... args)
 *      - reader(): объект Reader, ассоциированный с данной консолью
 *      - readLine()
 *      - readLine(String fmt, Object... args
 *      - readPassword(): читает пароль с консоли с выключенным echoing
 *      - readPassword(String fmt, Object... args)
 *      - writer(): объект PrintWriter, ассоциированный с консолью */

@Ntrstn("Стандартный IO - концепция (UNIX) единого потока информации, используемого программой - " +
        "чтобы соединять выход одной программы со входом другой при помощи данного стандарта")

@Ntrstn("Стандартный поток ввода (откуда приходит информация) - обычно клавиатура - представлен в " +
        "Java объектом System.in типа InputStream (т.е. перед использованием его нужно оборачивать) ")

@Ntrstn("Стандартный поток вывода (куда уходит информация) - обычно дисплей - представлен в Java " +
        "объектом System.out типа PrintStream (т.е. добавляет функционал символьной печати к объекту " +
        "OutputStream, при этом используется стандартная кодировка платформы)")

@Ntrstn("Стандартный поток вывода ошибок - представлен в Java объектом System.err и аналогичен " +
        "System.out")

@Ntrstn("Стандартные потоки можно перенаправлять методами System.setIn(), System.setOut(), System.setErr()")

@Ntrstn("Существует более продвинутая версия старндартных потоков - класс java.io.console - " +
        "представляет символьное (основано на Reader и Writer) консольное устройство, ассоциированное " +
        "с JVM. Однако его наличие зависит от способа запуска JVM (получить/проверить - System." +
        "console()). R/W операции синхронизированы для гарантирования атомарности критических операций, " +
        "а также есть методы для безопасного чтения паролей")

public class CommandLineIO_Standard_Console {

    public static void main(String[] args) throws IOException {
        standardGetFromInput();
        standardPrintToDisplay();
        standardPrintToError();

        askPasswordViaConsole(); // все равно консоли нет
    }


    private static void standardGetFromInput() throws IOException {
        System.out.println("Print smth, then press Enter: ");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("You typed: " + in.readLine());
        }
    }


    private static void standardPrintToDisplay() {
        PrintStream out = System.out;
        out.append("appending to System.out...");
        out.write(124); // символ |
        out.flush();
    }


    private static void standardPrintToError() {
        PrintStream out = System.err;
        out.println("Houston, we have a problem");
    }


    static void askPasswordViaConsole() {
        Console c = System.console();
        if (c == null) {
            System.err.println("No console.");
            System.exit(1);
        }

        String login = c.readLine("Enter your login: ");
        char[] oldPassword = c.readPassword("Enter your old password: ");
    }
}
