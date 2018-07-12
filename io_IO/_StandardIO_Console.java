package io_IO;


/* COMMAND-LINE io: СТАНДАРТНЫЕ ПОТОКИ И КОНСОЛЬ
 * - "стандартный io" - концепция (UNIX) единого потока информации, используемого программой
 *      - чтобы соединять выход одной программы со входом другой при помощи стандарта
 *
 * - стандартный поток ввода (клавиатура): System.in
 *      - откуда приходит информация
 *      - объект InputStream
 *          - т.е. при работе нужно использовать обертку
 *              - напр. new BufferedReader(new InputStreamReader(System.in));
 *
 *
 * - стандартный поток вывода (дисплей): System.out
 *      - куда записывается информация
 *      - объект PrintStream, но внутри использует объект символьного потока OutputStream
 *
 * - стандартный поток ошибок: System.err
 *      - объект PrintStream, но внутри использует объект символьного потока OutputStream
 *
 * - потоки можно перенаправлять методами: System.setIn(), System.setOut(), System.setErr()
 *
 * - Console - более продвинутая версия стандартных потоков
 *      - использует символьные потоки
 *      - подходит, например, для ввода паролей
 *          - метод readPassword()
 *      - получить: System.console() */

import java.io.Console;

public class _StandardIO_Console {

    public static void main(String[] args) {
        //        askPasswordViaConsole();

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
