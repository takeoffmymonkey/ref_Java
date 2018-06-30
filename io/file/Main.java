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
 *              - если ОС не поддерживает - исключение */


/* МЕТАДАННЫЕ (АТРИБУТЫ) ФАЙЛА
 * - size(Path): размер файла в байтах
 * - isDirectory(Path, LinkOption): является ли директорией
 * - isRegularFile(Path, LinkOption...): является обычным файлом
 * - isSymbolicLink(Path): является симлинком
 * - isHidden(Path): является скрытым
 * - getLastModifiedTime(Path, LinkOption...)/setLastModifiedTime(Path, FileTime):
 *      - чтение/установка времени последнего изменения файла
 * - getOwner(Path, LinkOption...)/setOwner(Path, UserPrincipal):
 *      - чтение/установка владельца
 * - getPosixFilePermissions(Path, LinkOption...)/setPosixFilePermissions(Path, Set<PosixFilePermission>):
 *      - чтение/установка разрешений POSIX
 *          - Portable Operating System Interface
 * - getAttribute(Path, String, LinkOption...)/setAttribute(Path, String, Object, LinkOption...):
 *      - чтение/установка значения атрибута
 * - readAttributes(Path, String, LinkOption...):
 *      - все атрибуты за раз
 *      - String - id нужного атрибута
 * - readAttributes(Path, Class<A>, LinkOption...):
 *      - все атрибуты за раз
 *      - Class<A> - тип нужного атрибута
 *          - возвращает объект этого класса
 *
 * - FileStore - класс, представляющий физической место, где находится файл
 *      - можно, например, узнать сколько места свободного
 * */

/* - связанные атрибуты сгруппированы в Views
 *      - View зависит от имплементации конкретной файловой системы или какой-то общей ф-ти
 *
 * - BasicFileAttributeView – базовые атрибуты, которые должны поддерживаться всеми имплементациями
 * файловой системы
 *
 * - DosFileAttributeView – расширяет базовые для поддержки атрибутов DOS
 *
 * - PosixFileAttributeView – расширяет базовые для поддержки атрибутов POSIX
 *      - в т.ч. владелец файла, владелец группы, и другие 9 связанные с доступом
 *
 * - FileOwnerAttributeView – поддерживается каждой имплементацией файловой системы, которая
 * поддреживает концепцию владельца файла
 *
 * - AclFileAttributeView – поддержка чтения/изменения Access Control Lists (ACL) файла
 *
 * - UserDefinedFileAttributeView – поддержка пользовательских метаданных
 *      - напр. в Solaris OS здесь можно хранить MIME тип файла */


/* ЧТЕНИЕ, ЗАПИСЬ И СОЗДАНИЕ ФАЙЛОВ
 * - по сложности:
 *      - 1. read/writeAllBytes(), readWriteAllLines()
 *          - часто используются
 *          - работа с малыми файлами
 *
 *      - 2. newBufferedReader(), newBufferedWriter()
 *          - работа с текстовыми файлами
 *
 *      - 3. newInputStream(), newOutputStream()
 *          - потоки
 *          - небуферизированы
 *          - используй с существующими API
 *
 *      - 4. newByteChannel()
 *          - каналы и ByteBuffers
 *
 *      - 5. FileChannel
 *          - сложные фичи
 *          - блокировка файла
 *          - работа с памятью
 *
 * - методы по созданию позволяют задавать начальные атрибуты
 *
 * - параметр StandardOpenOptions: опциональный параметр в некоторых методах
 *      - WRITE – открывает файл для записи
 *      - APPEND – добавляет новые данные в конец файла
 *          - используется с WRITE или CREATE
 *      - TRUNCATE_EXISTING – обрезает существующий файл до 0 байт
 *         - используется с WRITE
 *      - CREATE_NEW – создает новый файл и выбрасывает исключение, если он существует
 *      - CREATE – открывает файл, если он существует или создает новый, если не существует
 *      - DELETE_ON_CLOSE – удаляет файл при закрытии потока (stream)
 *          - полезно для временных файлов
 *      - SPARSE – подсказывает, что новосозданный файл будет разреженным (sparse).
 *          - полезно на некоторых файловых системах (напр., NTFS), где большие файлы с "пробелами"
 *          данных могут сохраняться более эффективным способом, когда такие пробелы не занимают
 *          места на диске.
 *      - SYNC – соблюдает синхронизацию для файла (и содержание и метаданные) на устройсте хранения
 *      - DSYNC – соблюдает синхронизацию для файла (только содержание ) на устройсте хранения
 *
 *
 * - часто используемые методы для малых файлов
 *      - чтение всех байтов/строк из файла за раз: readAllBytes(Path)/readAllLines(Path, Charset)
 *      - запись: write(Path, byte[], OpenOption...)/write(Path, Iterable< extends CharSequence>, Charset, OpenOption...)
 *
 * - буферизированные методы для текстовых файлов для Stream I/O:
 *      - чтение файла при помощи Buffered Stream I/O: newBufferedReader(Path, Charset)
 *      - запись: newBufferedWriter(Path, Charset, OpenOption...)
 *
 * - небуферизированные байтовые методы для Stream I/O:
 *      - чтение файла байтово при помощи InputStream: newInputStream(Path, OpenOption...)
 *      - создание и запись
 *
 * - методы для Channel I/O и ByteBuffer:
 *      - Channel API - чтение/запись буфера за раз
 *          - Stream API - чтение/запись символа за раз
 *      - ByteChannel интерфейс, который предоставляет базовые операции чтения/записи
 *      - newByteChannel(Path, OpenOption...)
 *      - newByteChannel(Path, Set<? extends OpenOption>, FileAttribute<?>...)
 *      - возвращают SeekableByteChannel
 *          - есть возможноть сохранять позицию в канале и менять ее, а также обрезать файл и
 *          запрашивать размер у файла
 *      - можно приводить к FileChannel
 *          - доступ к более сложному функционалу, типа работы с памятью, блокировки кусков файлов,
 *          записи байтов с абсолютной позиции без влияния на текущую позицию канала
 *
 * - методы для создания обычных и временных файлов:
 *      - обычные файлы: createFile(Path, FileAttribute<?>)
 *          - при существовании файла выбрасывается исключение
 *          - можно также использовать и методы newOutputStream
 *      - временные файлы:
 *          - createTempFile(Path, String, String, FileAttribute<?>): можно указать место создания
 *          - createTempFile(String, String, FileAttribute<?>): место создания - дефолтная папка для
 *          временных файлов
 *          - в обоих можно указать суффикс
 *          - в первом можно указать префикс
 * */

/* RANDOM ACCESS FILE
 * - позволяет непоследовательный доступ (чтение/запись) к содержимому файла
 * - используется интерфейс SeekableByteChannel
 * - получить можно от:
 *     - newByteChannel(Path, OpenOption...)/
 *     - newByteChannel(Path, Set<? extends OpenOption>, FileAttribute<?>...)
 *     - если привести к FileChannel, то можно расширить фунционал
 *
 * - методы:
 *      - position(): текущая позиция канала
 *      - position(long): установка позиции
 *      - read(ByteBuffer): чтение байтов в буфер из канала
 *      - write(ByteBuffer): запись байтов из буфера в канал
 *      - truncate(long): обрезает файл (или другую сущность), подключенный к каналу
 * */


/* IO VS NIO
 * - IO: удобно, когда нужно держать открытыми ширококанальные подключения (напр. P2P)
 *      - ориентирован на поток (stream):
 *          - чтение 1 или нескольких байт за раз из потока
 *              - они нигде не кешируются
 *              - нельзя двигаться вперед-назад по потоку
 *                  - чтобы можно было - нужно сперва закешировать их в буфер
 *      - блокирующий IO:
 *          - когда ветка вызывает read/write, она блокируется до конца выполнения
 *
 * - NIO: удобно, когда нужно управлять кучей малых коротких подключений одновременно (напр. чат)
 *      - ориентирован на буфер (buffer):
 *          - данные читаются в буфер, который затем обрабатывается
 *          - можно двигаться по буферу вперед-назад
 *      - неблокирующий IO:
 *          - ветка может запросить у канала чтение данных и получить только то, что сейчас доступно,
 *          или ничего, если ничего не доступно
 *              - не обязана ждать, пока появятся данные
 *              - аналогично с записью
 *              - может теперь заняться другими каналами
 *          - поэтому сложнее в работе, т.к. нужно постоянно запрашивать, заполнился ли буфер, чтобы
 *          можно было с ним работать
 *      - селекторы:
 *          - позволяют одной ветке мониторить несколько каналов входа, которые зарегистрированы в
 *          селекторе
 * */

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Arrays;
import java.util.List;

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
        readBasicAttributes();
        readWriteUserAttribute();
        getStorageInformation();
        writeSmallFileByLines();
        writeTextByBufferedReader();
        createFile();
        createTempFile();
        randomAccessFileForReadWrite();
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
        p1.relativize(p3);// sally/bar
        p3.relativize(p1);// ../..
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


    private static void readBasicAttributes() throws IOException {
        Path file = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file\\src\\copy_test.txt");
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

        System.out.println("creationTime: " + attr.creationTime());
        System.out.println("lastAccessTime: " + attr.lastAccessTime());
        System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

        System.out.println("isDirectory: " + attr.isDirectory());
        System.out.println("isOther: " + attr.isOther());
        System.out.println("isRegularFile: " + attr.isRegularFile());
        System.out.println("isSymbolicLink: " + attr.isSymbolicLink());
        System.out.println("size: " + attr.size());
    }


    private static void readWriteUserAttribute() throws IOException {
        Path file = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file\\src\\copy_test.txt");

        UserDefinedFileAttributeView view = Files
                .getFileAttributeView(file, UserDefinedFileAttributeView.class);
        view.write("user.mimetype",
                Charset.defaultCharset().encode("text/html"));


        String name = "user.mimetype";
        ByteBuffer buf = ByteBuffer.allocate(view.size(name));
        view.read(name, buf);
        buf.flip();
        String value = Charset.defaultCharset().decode(buf).toString();
        System.out.println("My defined attribute (MIME) value: " + value);
    }

    private static void getStorageInformation() throws IOException {
        Path file = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file\\src\\copy_test.txt");

        FileStore store = Files.getFileStore(file);

        long total = store.getTotalSpace();
        long used = (store.getTotalSpace() - store.getUnallocatedSpace());
        long avail = store.getUsableSpace();

        System.out.println("Total space (bytes): " + total);
        System.out.println("Used space (bytes): " + used);
        System.out.println("Available space (bytes): " + avail);
    }


    private static void writeSmallFileByLines() throws IOException {
        Path file = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file\\writeAllLines.txt");

        List<String> list = Arrays.asList("coo", "coo", "shka");

        Files.write(file, list);
    }

    private static void writeTextByBufferedReader() {
        Path file = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file\\writeBufferedText.txt");
        Charset charset = Charset.forName("US-ASCII");
        String s = "Writing text via BufferedReader";
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset, StandardOpenOption.APPEND)) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private static void createFile() throws IOException {
        Path file = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file\\file_create_test.txt");
        try {
            Files.createFile(file);
        } catch (FileAlreadyExistsException x) {
            System.err.format("file named %s" +
                    " already exists%n", file);
        }
    }

    private static void createTempFile() throws IOException {
        Path tempFile = Files.createTempFile(null, ".myapp");
        System.out.format("The temporary file has been created: %s%n", tempFile);
    }


    private static void randomAccessFileForReadWrite() {
        Path file = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file\\file_create_test.txt");
        String s = "I was here!\n";
        byte data[] = s.getBytes();
        ByteBuffer out = ByteBuffer.wrap(data);

        ByteBuffer copy = ByteBuffer.allocate(12);

        try (FileChannel fc = (FileChannel.open(file, StandardOpenOption.READ, StandardOpenOption.WRITE))) {
            // Read the first 12 bytes of the file.
            int nread;
            do {
                nread = fc.read(copy);
            } while (nread != -1 && copy.hasRemaining());

            // Write "I was here!" at the beginning of the file.
            fc.position(0);
            while (out.hasRemaining())
                fc.write(out);
            out.rewind();

            // Move to the end of the file.  Copy the first 12 bytes to
            // the end of the file.  Then write "I was here!" again.
            long length = fc.size();
            fc.position(length - 1);
            copy.flip();
            while (copy.hasRemaining())
                fc.write(copy);
            while (out.hasRemaining())
                fc.write(out);
        } catch (IOException x) {
            System.out.println("I/O Exception: " + x);
        }
    }
}