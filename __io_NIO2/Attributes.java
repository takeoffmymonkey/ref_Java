package __io_NIO2;

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


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.UserDefinedFileAttributeView;

public class Attributes {
    public static void main(String[] args) throws IOException {
        readBasicAttributes();
        readWriteUserAttribute();
    }

    private static void readBasicAttributes() throws IOException {
        java.nio.file.Path file = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2\\src\\copy_test.txt");
        BasicFileAttributes attr = java.nio.file.Files.readAttributes(file, BasicFileAttributes.class);

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
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2\\src\\copy_test.txt");

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

}
