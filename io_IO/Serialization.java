package io_IO;


/* СЕРИАЛИЗАЦИЯ
 * - transient
 * - Serializable - процесс сериализации происходит автоматически
 * - Externalizable
 *      - способность управлять процессом сериализации, напр. что сериализировать, а что нет
 *      - transient нет смысла использовать, потому что процесс не автоматический
 *
 * - альтернатива Externalizable: реализовать интерфейс Serializable и добавить (а не переопределить)
 * методы write/readObject
 *      - будут автоматически вызваны при сериализации
 * - версии
 * - при восстановлении система не имеет представления о том, что объекты были уже восстановлены и
 * имеются в программе, поэтому она создает новые
 * - статические значения не сериализируются
 * */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

public class Serialization {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        writeReadData();
        writeReadObjects();
    }

    static void writeReadData() throws IOException {
        int i = 5;
        double d = 5.5;
        String s = "test";

        DataOutputStream out = null;
        try {
            out = new DataOutputStream(new
                    BufferedOutputStream(new FileOutputStream("src\\io_io_and_streams\\write_data_test.txt")));

            out.writeInt(i);
            out.writeDouble(d);
            out.writeUTF(s);
        } finally {
            out.close();
        }

        DataInputStream in = null;
        double total = 0.0;
        try {
            in = new DataInputStream(new
                    BufferedInputStream(new FileInputStream("src\\io_io_and_streams\\write_data_test.txt")));

            int unit;
            double price;
            String desc;

            try {
                while (true) {
                    unit = in.readInt();
                    price = in.readDouble();
                    desc = in.readUTF();
                    System.out.format("You ordered %d units of %s at $%.2f%n",
                            unit, desc, price);
                    total += unit * price;
                }
            } catch (EOFException e) {
            }
            System.out.format("For a TOTAL of: $%.2f%n", total);
        } finally {
            in.close();
        }
    }


    static void writeReadObjects() throws IOException, ClassNotFoundException {
        BigDecimal bigDecimal = new BigDecimal(23.44);


        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new
                    BufferedOutputStream(new FileOutputStream("src\\io_io_and_streams\\write_obj_test.txt")));

            out.writeObject(bigDecimal);
        } finally {
            out.close();
        }

        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new
                    BufferedInputStream(new FileInputStream("src\\io_io_and_streams\\write_obj_test.txt")));

            BigDecimal bigDecimal1 = null;

            try {
                while (true) {
                    bigDecimal1 = (BigDecimal) in.readObject();
                    System.out.format("Object value = " + bigDecimal1);
                }
            } catch (EOFException e) {
            }
        } finally {
            in.close();
        }
    }
}
