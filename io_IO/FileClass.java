package io_IO;

/* JAVA.IO.FILE - АБСТРАКЦИЯ ИМЕНИ ПУТИ К ФАЙЛУ ИЛИ ПАПКЕ
 * - implements Serializable, Comparable<File>
 *
 * - объекты File не изменяемы
 *
 * - могут обозначать как существующий путь, так и не существующий
 *
 * - путь может быть:
 *      - абсолютным: полный, содержит всю информацию для находжения файла, который он обозначает
 *      - относительным: должен интерпретироваться - для полноты необходимо добавить дополнительную
 *      информацию из другого пути
 *          - по дефолту все классы в java.io всегда разрешают пути относительно текущей папки
 *          пользователя
 *              - обычно там, откуда запущена JVM
 *
 * - имена папок/файлов в пути разделяются "разделителем"
 *      - зависит от системы
 *      - доступен в статических полях separator и separatorChar
 *
 * - имеет 2 компонента:
 *      1. опциональная системно-зависимая строка-префикс
 *          - UNIX: "/" для коревой папки
 *              - абстрактный путь, обозначающий корневую папку, имеет префикс и пустую
 *              последовательность имен папок
 *              - относительный путь не имеет префикса
 *          - Windows:
 *              - для путей с идентификатором диска: буква диска, ":" и, возможно, "\", если путь
 *              абсолютный
 *              - для UNC путей: префикс "\\", а дальше в последовательности имен - имя хоста и имя
 *              share
 *              - относительный путь без названия диска не имеет префикса
 *
 *      2. последовательность из 0 или более строковых имен
 *
 * - каноническое имя: имя понятное для текущей системы
 *      - всегда является абсолютным путем
 *      - убраны "." и ".."
 *      - разрешены симлинки для UNIX систем
 *      - названия дисков переведены в стандартный регистр для Windows
 *
 * - файловая система может ограничивать доступ к реальным объектам, которые представляют File, так
 * что некоторые методы могут зафейлиться
 *
 * - пакет java.nio.file (c J7) определяет интерфейсы и классы для JVM для операций доступа к файлам,
 * файловым атрибутам и файловой системе
 *      - позволяет преодолевать многие ограничения класса File
 *      - класс File по сути заменен на Path
 *          - а работа с ним идет в классе Files
 *      - метод toPath переводит объект File в Path*/


/* КОНСТРУКТОРЫ
 * - File(String pathname)
 * - File(String parent, String child)
 * - File(File parent, String child)
 * - File(URI uri) */


/* ОСНОВНЫЕ МЕТОДЫ
 * - проверка файла:
 *      - canExecute()
 *      - canRead()
 *      - canWrite()
 *      - exists()
 *      - isAbsolute()
 *      - isDirectory()
 *      - isFile()
 *      - isHidden()
 *
 * - создание:
 *      - createNewFile()
 *      - createTempFile(String prefix, String suffix)
 *      - createTempFile(String prefix, String suffix, File directory)
 *      - mkdir()
 *      - mkdirs(): когда по пути нужно создать папки
 *
 * - удаление файла:
 *      - delete()
 *      - deleteOnExit()
 *
 * - переименование:
 *      - renameTo(File dest)
 *
 * - данные о файле:
 *      - lastModified()
 *      - length()
 *
 * - получение от файла:
 *      - getAbsoluteFile()
 *      - getAbsolutePath()
 *      - getCanonicalFile()
 *      - getCanonicalPath()
 *      - getName()
 *      - getParent()
 *      - getParentFile()
 *      - getPath()
 *
 * - установка для файла:
 *      - setExecutable(boolean executable, boolean ownerOnly)
 *      - setLastModified(long time)
 *      - setReadable(boolean readable)
 *      - setReadable(boolean readable, boolean ownerOnly)
 *      - setReadOnly()
 *      - setWritable(boolean writable)
 *      - setWritable(boolean writable, boolean ownerOnly)
 *
 * - получение списка других файлов:
 *      - list()
 *      - list(FilenameFilter filter)
 *      - listFiles()
 *      - listFiles(FileFilter filter)
 *      - listFiles(FilenameFilter filter)
 *
 * - получение данных о системных дисках и месте на диске:
 *      - listRoots()
 *      - getFreeSpace()
 *      - getTotalSpace()
 *      - getUsableSpace()
 *
 * - перевод файла:
 *      - toPath()
 *      - toString()
 *      - toURI() */


import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FileClass {

    static File currentFolder = new File("C:\\git\\ref_Java\\src\\io_IO\\files\\");
    static File tempFile = new File(currentFolder, "temp.txt");
    static File tempDir = new File(currentFolder, "temp dir");

    public static void main(String[] args) throws IOException {
        showSeparator();
        createFile();
        setFileData();
        getFileData();
        getFileNamingData();
        createDir();
        renameFile();
        deleteDirOnExit();
        getRootData();
        listOtherFiles();
    }

    private static void listOtherFiles() {
        System.out.println("OTHER FILES: " + Arrays.deepToString(currentFolder.list()));
    }


    private static void getFileNamingData() throws IOException {
        System.out.println("FILE AS STRING: " + tempFile.toString());
        System.out.println("FILE AS ABSOLUTE FILE: " + tempFile.getAbsoluteFile());
        System.out.println("FILE AS ABSOLUTE PATH: " + tempFile.getAbsolutePath());
        System.out.println("FILE NAME: " + tempFile.getName());
        System.out.println("FILE PARENT: " + tempFile.getParent());
        System.out.println("FILE PARENT FILE: " + tempFile.getParentFile());
        System.out.println("FILE PATH: " + tempFile.getPath());
        System.out.println("FILE AS CANONICAL FILE: " + tempFile.getCanonicalFile());
        System.out.println("FILE AS CANONICAL PATH: " + tempFile.getCanonicalPath());
    }


    private static void renameFile() {
        File newFile = new File(currentFolder, "temp_renamed.txt");
        System.out.println("RENAMING TEMPFILE: " + tempFile.renameTo(newFile));
    }


    private static void deleteDirOnExit() {
        System.out.println("DELETING TEPMDIR ON EXIT");
        tempDir.deleteOnExit();
    }


    private static void createDir() {
        System.out.println("CREATING DIR: " + tempDir.mkdir());
    }


    private static void getFileData() {
        System.out.println("FILE EXISTS: " + tempFile.exists());
        System.out.println("FILE IS ABSOLUTE: " + tempFile.isAbsolute());
        System.out.println("FILE IS DIRECTORY: " + tempFile.isDirectory());
        System.out.println("FILE IS FILE: " + tempFile.isFile());
        System.out.println("FILE IS HIDDEN: " + tempFile.isHidden());
        System.out.println("FILE EXECUTABLE: " + tempFile.canExecute());
        System.out.println("FILE READABLE: " + tempFile.canRead());
        System.out.println("FILE WRITABLE: " + tempFile.canWrite());
        System.out.println("FILE LAST MODIFIED: " + tempFile.lastModified());
        System.out.println("FILE LENGTH: " + tempFile.length());
    }


    private static void setFileData() {
        System.out.println("SETTING FILE EXECUTABLE: " + tempFile.setExecutable(true));
        System.out.println("SETTING FILE LAST MODIFIED: " + tempFile.setLastModified(System.currentTimeMillis()));
        System.out.println("SETTING FILE READABLE: " + tempFile.setReadable(true));
        System.out.println("SETTING FILE READONLY: " + tempFile.setReadOnly());
        System.out.println("SETTING FILE WRITABLE: " + tempFile.setWritable(true));
    }


    private static void createFile() throws IOException {
        boolean result = tempFile.createNewFile();
        System.out.println("CREATING A FILE: " + result);
    }


    private static void showSeparator() {
        System.out.println("SEPARATORS: ");
        System.out.println(File.pathSeparator);
        System.out.println(File.separatorChar);
    }


    private static void getRootData() {
        System.out.println("CURRENT ROOTS: ");
        System.out.println(Arrays.deepToString(File.listRoots()));
        System.out.println("TOTAL SPACE ON ROOT: " + tempFile.getTotalSpace());
        System.out.println("FREE SPACE ON ROOT: " + tempFile.getFreeSpace());
        System.out.println("USABLE SPACE ON ROOT: " + tempFile.getUsableSpace());
    }
}
