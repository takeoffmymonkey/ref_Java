package __io_NIO2;


/* КЛАСС FILES
 * - еще одна первичная точка входа в java.nio.io.io_nio_nio2
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


/* СОЗДАНИЕ И ЧТЕНИЕ ПАПОК
 * - получение корневых каталогов системы: FileSystem.getRootDirectories()
 *
 * - создание папки: createDirectory(Path, FileAttribute<?>)
 *
 * - создание всех папок на пути: createDirectories(Path, FileAttribute<?>)
 *
 * - создание временной папки:
 *      - createTempDirectory(Path, String, FileAttribute<?>...): позволяет указать путь
 *      - createTempDirectory(String, FileAttribute<?>...): создает в дефолтном месте
 *
 * - получить содержание: newDirectoryStream(Path)
 *      - возвращает объект класса, который имплементирует DirectoryStream
 *          - также является Iterable
 *      - не забывай закрывать поток!
 *      - возвращаются все типы содержимого: папки, файлы, симлинки, скрытое
 *
 * - фильтрация содержимого:
 *      - при помощи Glob: newDirectoryStream(Path, String)
 *      - при момощи своего фильтра: DirectoryStream.Filter<T>
 *
 * */

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class Files {

    public static void main(String[] args) throws IOException{
        checkAccessibility();
        copyFile();
//        moveFile();
        writeSmallFileByLines();
        createFile();
        createTempFile();
        writeTextByBufferedReader();
        createAllDirsOnTheWay();
        getDirFilteredContent();
        getDirAdvancedFilteredContent();
        determineMimeType();

    }

    private static void checkAccessibility() {
        java.nio.file.Path file = Paths.get("C:\\autoexec.bat");
        boolean isRegularExecutableFile = java.nio.file.Files.isRegularFile(file) &
                java.nio.file.Files.isReadable(file) & java.nio.file.Files.isExecutable(file); // true
    }

    private static void copyFile() throws IOException {
        java.nio.file.Path p1 = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2\\src\\copy_test.txt");

        java.nio.file.Path p2 = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2\\trg\\copy_test.txt");

        java.nio.file.Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
    }

    private static void moveFile() throws IOException {
        java.nio.file.Path p1 = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2\\src\\move_test.txt");

        Path p2 = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2\\trg\\move_test.txt");

        java.nio.file.Files.move(p1, p2, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
    }


    private static void writeSmallFileByLines() throws IOException {
        Path file = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2\\writeAllLines.txt");

        List<String> list = Arrays.asList("coo", "coo", "shka");

        java.nio.file.Files.write(file, list);
    }

    private static void createFile() throws IOException {
        Path file = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2\\file_create_test.txt");
        try {
            java.nio.file.Files.createFile(file);
        } catch (FileAlreadyExistsException x) {
            System.err.format("io.io_nio_nio2 named %s" +
                    " already exists%n", file);
        }
    }

    private static void createTempFile() throws IOException {
        Path tempFile = java.nio.file.Files.createTempFile(null, ".myapp");
        System.out.format("The temporary io.io_nio_nio2 has been created: %s%n", tempFile);
    }


    private static void writeTextByBufferedReader() {
        Path file = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2\\writeBufferedText.txt");
        Charset charset = Charset.forName("US-ASCII");
        String s = "Writing text via BufferedReader";
        try (BufferedWriter writer = java.nio.file.Files.newBufferedWriter(file, charset, StandardOpenOption.APPEND)) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private static void createAllDirsOnTheWay() throws IOException {
        Path dir = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2\\test_dir\\one_more_dir");

        java.nio.file.Files.createDirectories(dir);
    }



    private static void getDirFilteredContent() throws IOException {
        Path dir = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2");

        try (DirectoryStream<Path> stream = java.nio.file.Files.newDirectoryStream(dir, "*.txt")) {
            for (Path file : stream) {
                System.out.println(file.getFileName());
            }
        } catch (DirectoryIteratorException x) {
            System.err.println(x);
        }
    }


    private static void getDirAdvancedFilteredContent() throws IOException {

        DirectoryStream.Filter<Path> filter =
                new DirectoryStream.Filter<Path>() {
                    public boolean accept(Path file) throws IOException {
                        return (java.nio.file.Files.isDirectory(file));
                    }
                };

        Path dir = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2");

        try (DirectoryStream<Path>
                     stream = java.nio.file.Files.newDirectoryStream(dir, filter)) {
            for (Path entry : stream) {
                System.out.println(entry.getFileName());
            }
        }
    }


    private static void determineMimeType() throws IOException {
        Path filename = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2\\file_create_test.txt");
        String type = java.nio.file.Files.probeContentType(filename);
        if (type == null) {
            System.err.format("'%s' has an" + " unknown filetype.%n", filename);
        } else if (!type.equals("text/plain")) {
            System.err.format("'%s' is not" + " a plain text io.io_nio_nio2.%n", filename);
        } else System.out.println("Type is: " + type);
    }
}