package io.file;

/*
 * - Файл идентифицируется своим путем в файловой системе, начиная с корневого узла
 *      - может быть файлом либо каталогом
 *
 * - Путь может быть:
 *      - абсолютным: от корневого узла
 *      - относительным: для нахождения файла должен быть объединен с другим
 *
 * - Делимитер - символ, используемый для разделения имен папок - специфичен для системы:
 *      - Windows: \
 *      - Solaris: /
 *
 *
 * - Некоторые системы поддерживают символические ссылки(symlink/soft link): специальный файл,
 * который служит ссылкой на другой файл
 *      - обычно операции с ними прозрачны для приложений - происходит автоматическая переадресация
 *      - при удалении или переименовании ссылки, сам объект по ссылке не меняется
 *      - разрешение ссылки - подстановка реального файла по адресу
 * */

/* ИНТЕРФЕЙС PATH
 * - начиная с J7/API 26
 * - public interface Path extends Comparable<Path>, Iterable<Path>, Watchable
 * - является одной из первичных точек входа в пакет java.nio.file
 * - программное представление пути в файловой системе
 * - содержит имя файла и список папок, которые формируют путь в данной ОС
 * - используется для исследования, нахождения и манипулирования файлами
 * - реального файла по пути может не быть
 * - можно использовать методы класса Files для проверки существования файла по пути, а также для
 * создания, удаления, изменения разрешений и т.д.
 *
 * - создание пути:
 *      - FileSystems.getDefault().getPath("/users/sally");
 *      - Paths.get()
 *          - сокращение для предыдущего метода
 *
 * - получение информации о пути:
 *      - путь хранит имена элементов как последовательность
 *          - самый высокий элемент: 0
 *          - самый низкий: n-1
 *      - по индексам можно получать отдельные имена либо части последовательности
 *      - методы:
 *          - toString(): строковое представление пути: напр. C:\home\joe\foo
 *          - getFileName(): имя файла или последнего элемента в последовательности: напр. foo
 *          - getName(0): путь элемента по индексу (0 - самый близкий к корню): напр. home
 *          - getNameCount(): количество элементов в пути: напр. 3
 *          - subpath(0,2): подпоследовательность пути (без корневого элемента): напр. home\joe
 *          - getParent(): путь к родительской директории: напр. \home\joe
 *          - getRoot: корень пути: напр. C:\
 *              - вернет null для относительного пути
 *
 * - удаление не нужного (нормализация): удаление . или папка/..
 *      - normalize()
 *          - синтаксическая операция - не проверяет файловую систему
 *              - чтобы удостоверится, что в результате путь будет рабочим: toRealPath()
 *
 * - конвертация пути:
 *      - toUri(): в URI: напр. file:///home/logfile
 *      - toAbsolutePath(): в абсолютный путь:
 *      - toRealPath():
 *          - разрешает симлинки
 *          - возвращает абсолютный путь для относительного
 *          - убирает лишние элементы из пути
 *
 * - объединение пути:
 *      - resolve():
 *          - присовокупляет переданный путь (должен быть без корневого элемента) к оригиналу
 *          - при передаче абсолютного пути, его и возвращает
 *
 * - создание пути между 2 локаций (путей):
 *      - relativize():
 *          - не сработает, если у 1 элемента есть корень, а у другого нет
 *          - если у обоих нет корня, это ок - будут рассматриваться как лежащие в 1 папке
 *
 * - сравнение 2 путей:
 *      - equals():
 *      - startsWith()/endsWith()
 *
 * - сравнение 2 файлов:
 *      - isSameFile();
 *
 * - также можно итерировать по элементам, сравнивать методом compareTo
 * */


/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


/* КЛАСС FILES
 * - еще одна первичная точка входа в java.nio.file
 * - работает с объектами Path
 * - предоставляет методы для чтения, записи и манипулирования файлами и папками
 *
 * */

/* РАБОТА С ФАЙЛАМИ:
 * - освобождение ресурсов:
 *       - большинство используемых ресурсов имплементируют java.io.Closeable
 *       - необходимо освобождать для эффективности
 *
 * - все методы, работающие с файловой системой выбрасывают IOException
 *       - лучше всего их вызовы обрамлять try-with-resources
 *           - компилятор генерирует нужный код закрытия
 *       - исключения, происходящие от FileSystemException, позволяют:
 *          - вернуть файл, вызвавший исключение: getFile()
 *          - получить развернутое сообщение: getMessage()
 *          - получить причину: getReason()
 *          - получить другие связанные файлы: getOtherFile()
 *
 * - некоторые методы Files принимают переменное число аргументов:
 *      - напр. Path Files.move(Path, Path, CopyOption...) // набор флагов для перемещения
 *
 * - некоторые методы Files могут совершать некоторые операции в некоторых файловых системах
 * атомически
 *      - т.е. должна быть завершена полностью и без прерываний или не считается завершенной
 *      - напр. move()
 *
 * - многие методы I/O поддерживают связывание методов
 *      - напр. String value = Charset.defaultCharset().decode(buf).toString();
 *
 * - 2 метода Files принимают аргументом Glob
 *      - для указания pattern-matching поведения
 *      - * : совпадает с любым числом символов (в т.ч. 0)
 *      - ** : то же, но переходят границы папок
 *          - обычно используется для всего сверки всего пути
 *      - ? : совпадает только с 1 символом
 *      - {} : коллекция подпаттернов
 *          - напр. {htm,html,pdf} совпадает "htm", "html", или "pdf".
 *      - [] : набор символов
 *          - может быть с диапазоном [-]
 *          - может быть с *, ?, \
 *              - работают так же, как и самостоятельно
 *          - напр. [aeiou] совпадает со любой гласной
 *      - любые другие символы: совпадает с собой
 *          - для специальных символов нужно использовать экранирование
 *
 * - класс Files "знает" о линках
 *      - некоторые методы также предоставляют возможность указывать поведение при встрече симлинка
 */


/* ОПЕРАЦИИ С ФАЙЛАМИ И ПАПКАМИ
 * - проверка существования : exists(Path, LinkOption...) и notExists(Path, LinkOption...)
 *      - не являются полными противоположностями:
 *          - оба вернут false, если у файла неизвестный статус
 *
 * - проверка доступности: isReadable(Path), isWritable(Path) и isExecutable(Path)
 *
 * - проверка, являются ли 2 файла 1 и тем же: isSameFile(p1, p2)
 *
 * - удаление:
 *      - delete(Path):
 *          - перед удалением папки, она должна быть пустой, иначе исключение NoSuchFileException
 *      - deleteIfExists(Path):
 *          - не выбрасывает исключение
 *          - полезно при нескольких потоках, когда 1 уже успел удалить, поэтому не нужно исключение
 *
 * - копирование (в т.ч. из/в поток):
 *      - copy(Path, Path, CopyOption...), copy(InputStream, Path, CopyOptions...), copy(Path,
 *      OutputStream)
 *      - файла не должно уже существовать в месте назначения или нужен флаг REPLACE_EXISTING
 *      - папка копируется без файлов
 *      - при копировании симлинки, копируется то, что по адресу
 *          - чтобы скопировать просто ссылку: NOFOLLOW_LINKS или REPLACE_EXISTING
 *      - флаги:
 *          - REPLACE_EXISTING: копирует, даже если целевой файл уже существует
 *              - для симлинки копирует только ссылку
 *              - если цель - не пустая папка - FileAlreadyExistsException
 *          - COPY_ATTRIBUTES: копирует атрибуты файла
 *              - зависят от ОС
 *              - last-modified-time поддерживается всеми ОС
 *          - NOFOLLOW_LINKS: указывает, что по симлинкам не нужно следовать
 *              - т.е. копируется сама симлинка
 *
 * - перемещение: move(Path, Path, CopyOption...)
 *      - файла не должно уже существовать в месте назначения или нужен флаг REPLACE_EXISTING
 *      - можно перемещать пустые папки
 *      - если папка не пустая, перемещение возможно только если папку можно перемещать без
 *      перемещения ее содержания
 *      - в UNIX системах перемещение папки на одном диске подразумевает переименование папки
 *          - в таком случае метод работает даже, если в папке что-то есть
 *      - обычно используется с рекурсией
 *      - флаги:
 *          - REPLACE_EXISTING:
 *              - перенос даже, если цель уже существует
 *              - если это симлинка, она заменяется, но адрес остается тем же
 *          - ATOMIC_MOVE: атомический перенос
 *              - если ОС не поддерживает - исключение
 *
 * */

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) throws IOException {
        createPath();
        getPathInfo();
        pathCombination();
        getPathBetween2();
        comparePaths();
        iteratePath();
        checkAccessibility();
        copyFile();
//        moveFile();
    }


    /* ~~~~~~~~~~~~~~~~~~~~~~~PATH~~~~~~~~~~~~~~~~~~~~~~~*/
    private static void iteratePath() {
        Path p = Paths.get(URI.create("file:///Users/joe/FileTest.java"));
        for (Path path : p) {
            System.out.println(path);
        }
    }


    private static void createPath() {
        Path p1 = Paths.get("/tmp/foo");
        Path p2 = Paths.get(URI.create("file:///Users/joe/FileTest.java"));
        Path p3 = FileSystems.getDefault().getPath("/users/sally");
        Path p4 = Paths.get(System.getProperty("user.home"), "logs", "foo.log"); //C:\Users\takeoff\logs\foo.log
    }


    private static void getPathInfo() {
        Path path = Paths.get("C:\\home\\joe\\foo");

        System.out.format("toString: %s%n", path.toString());
        System.out.format("getFileName: %s%n", path.getFileName());
        System.out.format("getName(0): %s%n", path.getName(0));
        System.out.format("getNameCount: %d%n", path.getNameCount());
        System.out.format("subpath(0,2): %s%n", path.subpath(0, 2));
        System.out.format("getParent: %s%n", path.getParent());
        System.out.format("getRoot: %s%n", path.getRoot());
    }

    private static void pathCombination() {
        Path p1 = Paths.get("C:\\home\\joe\\foo");
        System.out.format("%s%n", p1.resolve("bar")); // C:\home\joe\foo\bar
    }


    private static void getPathBetween2() {
        Path p1 = Paths.get("home");
        Path p3 = Paths.get("home/sally/bar");
        Path p1_to_p3 = p1.relativize(p3);// Result is sally/bar
        Path p3_to_p1 = p3.relativize(p1);// Result is ../..
    }

    private static void comparePaths() {
        Path p1 = Paths.get("/tmp/foo");
        Path p2 = Paths.get("/tmp/bar");
        Path start = Paths.get("tmp");

        boolean b = p1.startsWith(start);
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~FILES~~~~~~~~~~~~~~~~~~~~~~~*/
    private static void checkAccessibility() {
        Path file = Paths.get("C:\\autoexec.bat");
        boolean isRegularExecutableFile = Files.isRegularFile(file) &
                Files.isReadable(file) & Files.isExecutable(file); // true
    }


    private static void copyFile() throws IOException {
        Path p1 = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file\\src\\copy_test.txt");

        Path p2 = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file\\trg\\copy_test.txt");

        Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
    }

    private static void moveFile() throws IOException {
        Path p1 = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file\\src\\move_test.txt");

        Path p2 = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file\\trg\\move_test.txt");

        Files.move(p1, p2, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
    }

}