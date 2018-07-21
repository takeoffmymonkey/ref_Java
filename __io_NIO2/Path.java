package __io_NIO2;

/* ИНТЕРФЕЙС PATH
 * - начиная с J7/API 26
 * - public interface Path extends Comparable<Path>, Iterable<Path>, Watchable
 * - является одной из первичных точек входа в пакет java.nio.io.io_nio_nio2
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
 *      - toUri(): в URI: напр. io.io_nio_nio2:///home/logfile
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


/*
 * - Файл идентифицируется своим путем в файловой системе, начиная с корневого узла
 *      - может быть файлом либо каталогом
 *
 * - Путь может быть:
 *      - абсолютным: от корневого узла
 *      - относительным: для нахождения файла должен быть объединен с другим
 *
 * */

import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Paths;

public class Path {

    public static void main(String[] args) {
        iteratePath();
        createPath();
        getPathInfo();
        pathCombination();
        getPathBetween2();
        comparePaths();
    }


    /* ~~~~~~~~~~~~~~~~~~~~~~~PATH~~~~~~~~~~~~~~~~~~~~~~~*/

    private static void iteratePath() {
        java.nio.file.Path p = Paths.get(URI.create("io.io_nio_nio2:///Users/joe/FileTest.java"));
        for (java.nio.file.Path path : p) {
            System.out.println(path);
        }
    }

    private static void createPath() {
        java.nio.file.Path p1 = Paths.get("/tmp/foo");
        java.nio.file.Path p2 = Paths.get(URI.create("io.io_nio_nio2:///Users/joe/FileTest.java"));
        java.nio.file.Path p3 = FileSystems.getDefault().getPath("/users/sally");
        java.nio.file.Path p4 = Paths.get(System.getProperty("user.home"), "logs", "foo.log"); //C:\Users\takeoff\logs\foo.log
    }

    private static void getPathInfo() {
        java.nio.file.Path path = Paths.get("C:\\home\\joe\\foo");

        System.out.format("toString: %s%n", path.toString());
        System.out.format("getFileName: %s%n", path.getFileName());
        System.out.format("getName(0): %s%n", path.getName(0));
        System.out.format("getNameCount: %d%n", path.getNameCount());
        System.out.format("subpath(0,2): %s%n", path.subpath(0, 2));
        System.out.format("getParent: %s%n", path.getParent());
        System.out.format("getRoot: %s%n", path.getRoot());
    }

    private static void pathCombination() {
        java.nio.file.Path p1 = Paths.get("C:\\home\\joe\\foo");
        System.out.format("%s%n", p1.resolve("bar")); // C:\home\joe\foo\bar
    }


    private static void getPathBetween2() {
        java.nio.file.Path p1 = Paths.get("home");
        java.nio.file.Path p3 = Paths.get("home/sally/bar");
        p1.relativize(p3);// sally/bar
        p3.relativize(p1);// ../..
    }

    private static void comparePaths() {
        java.nio.file.Path p1 = Paths.get("/tmp/foo");
        java.nio.file.Path p2 = Paths.get("/tmp/bar");
        java.nio.file.Path start = Paths.get("tmp");

        boolean b = p1.startsWith(start);
    }
}
