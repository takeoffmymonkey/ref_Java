package __io_NIO2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStore {

    public static void main(String[] args) throws IOException {
        getStorageInformation();
    }

    private static void getStorageInformation() throws IOException {
        Path file = Paths.get
                ("C:\\git\\ref_Java\\out\\production\\ref_Java\\io\\io.io_nio_nio2\\src\\copy_test.txt");

        java.nio.file.FileStore store = Files.getFileStore(file);

        long total = store.getTotalSpace();
        long used = (store.getTotalSpace() - store.getUnallocatedSpace());
        long avail = store.getUsableSpace();

        System.out.println("Total space (bytes): " + total);
        System.out.println("Used space (bytes): " + used);
        System.out.println("Available space (bytes): " + avail);
    }
}
