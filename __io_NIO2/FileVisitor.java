package __io_NIO2;

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

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileVisitor {

    public static void main(String[] args) throws IOException {
        walkFileTree();
        findFile();

    }

    private static void walkFileTree() throws IOException {
        /* РАСШИРЕНИЕ SimpleFileVisitor ДЛЯ ПОЛУЧЕНИЯ ИМПЛЕМЕНТАЦИИ ИНТЕРФЕЙСА FileVisitor */
        class PrintFiles extends SimpleFileVisitor<Path> {

            // Print information about each type of io.io_nio_nio2.
            @Override
            public FileVisitResult visitFile(java.nio.file.Path file, BasicFileAttributes attr) {
                if (attr.isSymbolicLink()) System.out.format("Symbolic link: %s ", file);
                else if (attr.isRegularFile()) System.out.format("Regular io.io_nio_nio2: %s ", file);
                else System.out.format("Other: %s ", file);
                System.out.println("(" + attr.size() + "bytes)");
                return FileVisitResult.CONTINUE;
            }

            // Print each directory visited.
            @Override
            public FileVisitResult postVisitDirectory(java.nio.file.Path dir, IOException exc) {
                System.out.format("Directory: %s%n", dir);
                return FileVisitResult.CONTINUE;
            }

            // If there is some error accessing the io.io_nio_nio2, let the user know.
            // If you don't override this method and an error occurs, an IOException is thrown.
            @Override
            public FileVisitResult visitFileFailed(java.nio.file.Path file, IOException exc) {
                System.err.println(exc);
                return FileVisitResult.CONTINUE;
            }
        }

        /* ЗАПУСК ПРОЦЕССА */
        Path startingDir = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2");
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

            // Compares the glob pattern against the io.io_nio_nio2 or directory name.
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

            // Invoke the pattern matching method on each io.io_nio_nio2.
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
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2");
        String pattern = "*.txt";

        Finder finder = new Finder(pattern);
        Files.walkFileTree(startingDir, finder);
        finder.done();
    }
}
