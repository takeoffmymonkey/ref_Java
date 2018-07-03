package io.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Arrays;
import java.util.List;

import types_references_annotations.my_annotations.Ntrstn;



/* КЛЮЧЕВЫЕ АБСТРАКЦИИ NIO
 * - БУФЕР (java.nio): контейнер для ограниченного объема данных примитивного типа
 *      - имеет позицию (индекс следующего элемента для записи/чтения)
 *          - ее можно очищать, переворачивать (flip), перематывать (rewind) и помечать, а также
 *          переустанавливать ее на предыдущую метку
 *      - для каждого примитива (кроме boolean) есть класс
 *          - каждый класс определяет семейство методов:
 *              - get/put для доставания/записи данных
 *              - сжатие, дубликация и порезка буфера
 *              - статические методы для аллоцирования нового буфера и обворачивания существующего
 *              массива в буфер
 *      - может использоваться как src и trg для операций IO
 *      - имеет дополнительные ф-ции, которых нет в других буферах:
 *          - может аллоцировать прямой буфер, и JVM постарается работать с ним нативно
 *          - может создаваться мапингом куска файла прямо в память, в результате чего становятся
 *          доступными несколько дополнительных файловых операций, определенных в MappedByteBuffer
 *          - предоставляет доступ к своему содержимому как к гетерогенной или гомогенной
 *          последовательности бинарных данных любого небулеанового примитива либо в big-endian либо
 *          в little-endian порядках байтов
 *      - иерархия:
 *          - Buffer: позиция, лимит и вместимость; очистить, перевернуть, перемотать, пометить и
 *          переустановить
 *              - ByteBuffer: получить\положить, сжать, виды; аллоцировать, обернуть
 *                  - MappedByteBuffer: байтовый буфер, замапенный на файл
 *              - Char/Double/Float/Int/Long/ShortBuffer: получить\положить, сжать; аллоцировать,
 *              обернуть
 *          - ByteOrder: типобезопасное перечисление для порядков байтов
 *
 *
 * - НАБОРЫ СИМВОЛОВ (java.nio.charset) и ассоциированные с ними КОДЕРЫ(в т.ч. ДЕКОДЕРЫ), которые
 * переводят между байтами и символами Unicode
 *      - charset: именнованный маппинг 16-битных символов Unicode и последовательностей байтов
 *      - кодеры работают с буферами байтов и символов
 *      - обычно эти классы не используются напрямую, а используют существующие связанные с наборами
 *      символов конструкторы и методы в классе String с InputStreamReader и OutputStreamWriter
 *      - иерархия:
 *          - Charset: именованный маппинг между символами и байтами
 *              - определяет методы для создания кодеров для данного набора символов для получения
 *              разных имен, ассоциированных с набором
 *              - определяет методы для тестирования поддержки данного набора, для поиска экземпляра
 *              набора по имени и для создания мапинга, который содержит каждый набор, чья поддержка
 *              доступна текущей JVM
 *          - CharsetDecoder: декодирует байты в символы
 *          - CharsetEncoder: кодирует символы в байты
 *          - CoderResult: описывает результаты кодера
 *          - CodingErrorAction: описывает действия при определении возникновения ошибки кодировки
 *
 * - КАНАЛЫ: разные типы, которые представляют подключения к сущностям, способным производить IO
 * операции
 *      - например, устройство, сетевой сокет, файл, программный компонент
 *      - каналы могут быть открытыми или закрытыми
 *      - являются асинхронно закрываемыми и прерываемыми
 *      - иерархия:
 *          - Channel: связующее звено для IO операций
 *              - ReadableByteChannel: может передавать в буфер
 *                  - ScatteringByteChannel: может передавать в последовательность буферов
 *              - WritableByteChannel: может писать из буфера
 *                  - GatheringByteChannel: может писать из последовательности буферов
 *              - ByteChannel: может передавать/писать из буфера
 *                  - SeekableByteChannel: ByteChannel, подключенный к сущности, у которой
 *                  последовательность байтов переменной длины
 *                      - методы для запроса и изменений текущей позиции канала и его размера
 *              - AsynchronousChannel: поддерживает асинхронные операции IO
 *                  - AsynchronousByteChannel: может передавать/писать из буфера асинхронно
 *              - NetworkChannel: канал к сетевому сокету,
 *                  - MulticastChannel: может соединять Internet Protocol (IP) multicast groups
 *              - Channels: утилитные методы для взаимодействия каналов с потоками
 *      - файловые каналы:
 *          - FileChannel: читает, записывает мапит и манипулирует файлами
 *              - создается от статического метода open или вызовом getChannel у FileInputStream,
 *              FileOutputStream или RandomAccessFile
 *          - FileLock: блокировка (куска) файла
 *          - MappedByteBuffer: прямой байтовый буфер, замапенный на кусок файла
 *
 * - СЕЛЕКТОРЫ И КЛЮЧИ СЕЛЕКЦИИ, которые вместе с selectable каналами определяют мультиплексированный
 * неблокирующий функционал
 *      - из 3 веток для 3 задач, можно сделать 1 ветку, но многозадачную */



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


/*
 * РАБОТА С ЛИНКАМИ
 * - создание симлинка: createSymbolicLink(Path, Path, FileAttribute<?>)
 * - создание жесткой линки: createLink(Path, Path)
 * - обнаружение симлинки: isSymbolicLink(Path)
 * - получение адресата симлинки: readSymbolicLink(Path)
 * */


/* ПРОХОД ПО ФАЙЛОВОМУ ДЕРЕВУ
 * - имплементация интерфейса FileVisitor
 *      - указывает поведение в ключевых точках:
 *          - когда посещается файл: visitFile
 *          - перед доступом к папке: preVisitDirectory
 *          - после доступа к папке: postVisitDirectory
 *          - при ошибке: visitFileFailed
 *      - можно просто расширить SimpleFileVisitor
 *      - при ошибке выбрасывается IOError
 *
 * - запуск прохода:
 *      - walkFileTree(Path, FileVisitor)
 *      - walkFileTree(Path, Set<FileVisitOption>, int, FileVisitor)
 *          - можно указать глубину, напр. Integer.MAX_VALUE
 *
 * - контролирование процесса:
 *      - например, при нахождении остановить дальнейший проход
 *      - методы FileVisitor возвращают значение FileVisitResult:
 *          - CONTINUE – нужно продолжать проход.
 *              - если preVisitDirectory возвращает CONTINUE, директория посещается
 *          - TERMINATE – прервать проход
 *              - никакие методы больше не вызываются
 *          - SKIP_SUBTREE – указанная папка и подпапки пропускаются
 *          - SKIP_SIBLINGS – в указанной папке больше ничего не происходит
 *
 * - поиск:
 *      - аналогично проходу, но для каждого файла/папки дополнительно сверяется вхождение объектом
 *      PathMatcher
 *          - получить: FileSystems.getDefault().getPathMatcher(String);
 *
 * - к сведению:
 *      - точный порядок прохода неизвестен
 *      - при удалении папки, не забыть удалить сначала содержимое
 *      - при рекурсивном копировании, перед копированием, нужно создать папку в preVisitDirectory
 *      - при поиске сравнение происходит в visitFile, но он находит только файлы
 *          - для папок сравнивать нужно, например, в preVisitDirectory
 *      - при удалении, необходимо знать, стоит ли следовать по симлинкам
 *          - по дефолту не следует
 * */


/*
 * WATCH SERVICE API - отслеживание изменений в папке
 * - базовые шаги:
 *      - создать наблюдателя WatchService для файловой системы
 *      - зарегистрировать отслеживаемую папку в наблюдателе и указать отслеживаемые события
 *          - при событии придет экземпляр WatchKey
 *      - имплементировать бесконечный цикл для ожидания наступления события
 *          - при событии сообщается о ключе и он помещается в очередь наблюдателя
 *      - получить ключ из очереди наблюдателя
 *          - из ключа можно получить имя файла
 *      - получить каждое ожидающее событие для ключа (может быть несколько) и процессить как надо
 *      - переустановить ключ и продолжить ожидание событий
 *      - закрыть службу
 *          - служба закрывается либо при выходе ветки либо методом closed
 *
 * - WatchKey потокобезопасен
 * */


/* ДРУГИЕ ПОЛЕЗНЫЕ МЕТОДЫ
 * - определение MIME типа: probeContentType(Path)
 *
 * - дефолтная файловая система: FileSystems.getDefault()
 *      - обычно используется в цепи вызовов
 *
 * - разделитель строки пути:
 *      - File.separator
 *      - FileSystems.getDefault().getSeparator()
 *
 * - хранилища (т.е. логические диски) в файловой системе:
 *      - FileSystems.getDefault().getFileStores()
 * */


@Ntrstn("Разница между IO и NIO ")


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
        getSystemRootDirectories();
        createAllDirsOnTheWay();
        getDirFilteredContent();
        getDirAdvancedFilteredContent();
        walkFileTree();
        findFile();
        determineMimeType();
        getDefaultFileSystem();
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


    /* ~~~~~~~~~~ПАПКИ~~~~~~~~~~*/
    private static void getSystemRootDirectories() {
        Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
        for (Path name : dirs) {
            System.err.println(name);
        }
    }

    private static void createAllDirsOnTheWay() throws IOException {
        Path dir = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file\\test_dir\\one_more_dir");

        Files.createDirectories(dir);
    }


    private static void getDirFilteredContent() throws IOException {
        Path dir = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.txt")) {
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
                        return (Files.isDirectory(file));
                    }
                };

        Path dir = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file");

        try (DirectoryStream<Path>
                     stream = Files.newDirectoryStream(dir, filter)) {
            for (Path entry : stream) {
                System.out.println(entry.getFileName());
            }
        }
    }


    /* ПРОХОД ПО СОДЕРЖИМОМУ ПАПКИ */

    private static void walkFileTree() throws IOException {
        /* РАСШИРЕНИЕ SimpleFileVisitor ДЛЯ ПОЛУЧЕНИЯ ИМПЛЕМЕНТАЦИИ ИНТЕРФЕЙСА FileVisitor */
        class PrintFiles extends SimpleFileVisitor<Path> {

            // Print information about each type of file.
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
                if (attr.isSymbolicLink()) System.out.format("Symbolic link: %s ", file);
                else if (attr.isRegularFile()) System.out.format("Regular file: %s ", file);
                else System.out.format("Other: %s ", file);
                System.out.println("(" + attr.size() + "bytes)");
                return FileVisitResult.CONTINUE;
            }

            // Print each directory visited.
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                System.out.format("Directory: %s%n", dir);
                return FileVisitResult.CONTINUE;
            }

            // If there is some error accessing the file, let the user know.
            // If you don't override this method and an error occurs, an IOException is thrown.
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                System.err.println(exc);
                return FileVisitResult.CONTINUE;
            }
        }

        /* ЗАПУСК ПРОЦЕССА */
        Path startingDir = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file");
        PrintFiles pf = new PrintFiles();
        Files.walkFileTree(startingDir, pf);
    }

    private static void findFile() throws IOException {
        class Finder extends SimpleFileVisitor<Path> {

            private final PathMatcher matcher;
            private int numMatches = 0;

            Finder(String pattern) {
                matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
            }

            // Compares the glob pattern against the file or directory name.
            void find(Path file) {
                Path name = file.getFileName();
                if (name != null && matcher.matches(name)) {
                    numMatches++;
                    System.out.println(file);
                }
            }

            // Prints the total number of matches to standard out.
            void done() {
                System.out.println("Matched: " + numMatches);
            }

            // Invoke the pattern matching method on each file.
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                find(file);
                return FileVisitResult.CONTINUE;
            }

            // Invoke the pattern matching method on each directory.
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                find(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                System.err.println(exc);
                return FileVisitResult.CONTINUE;
            }
        }

        /* ЗАПУСК ПРОЦЕССА ПОИСКА*/
        Path startingDir = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file");
        String pattern = "*.txt";

        Finder finder = new Finder(pattern);
        Files.walkFileTree(startingDir, finder);
        finder.done();
    }

    /*ДРУГИЕ МЕТОДЫ */

    private static void determineMimeType() throws IOException {
        Path filename = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\file\\file_create_test.txt");
        String type = Files.probeContentType(filename);
        if (type == null) {
            System.err.format("'%s' has an" + " unknown filetype.%n", filename);
        } else if (!type.equals("text/plain")) {
            System.err.format("'%s' is not" + " a plain text file.%n", filename);
        } else System.out.println("Type is: " + type);
    }

    private static void getDefaultFileSystem() {
        FileSystem fileSystem = FileSystems.getDefault(); // sun.nio.fs.WindowsFileSystem@10e140b
    }
}