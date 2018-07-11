package _io_NIO2;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;


/* ДРУГИЕ ПОЛЕЗНЫЕ МЕТОДЫ
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


/*- Делимитер - символ, используемый для разделения имен папок - специфичен для системы:
 *      - Windows: \
 *      - Solaris: /*/

public class FilesystemAndSeparator {

    public static void main(String[] args) {
        getSystemRootDirectories();
        getDefaultFileSystem();

    }
    private static void getSystemRootDirectories() {
        Iterable<java.nio.file.Path> dirs = FileSystems.getDefault().getRootDirectories();
        for (Path name : dirs) {
            System.err.println(name);
        }
    }


    private static void getDefaultFileSystem() {
        FileSystem fileSystem = FileSystems.getDefault(); // sun.nio.fs.WindowsFileSystem@10e140b
    }
}
