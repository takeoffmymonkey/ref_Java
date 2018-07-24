package _lang_variables;

/* Именованная ячейка памяти, которой можно присвоить значение*/

public class Main {

    public static void main(String[] args) {
        // должна иметь тип (определяет размер в памяти и набор операций с переменной)
        int a;


        //значение изменяется операцией присваивание или инкрементом/декрементом
        a = 4;
        a++;


        // должна содержать значение, которое совместимо с ее типом
        //int b = "String";


        // Длина имени не ограничена
        int dsfsdfsdfskdflskdflskdfsdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdvdfkasgfsgsdgfsdfsdfsdfsdfsdkfsdfsdfsdfnsdkjgnvsdvkjnsdvsdv = 1;


        // перед использованием должна быть объявлена
        //с = 6;


        //Блок кода определяет видимость
        {
            a = 4; // внешняя здесь видна, поэтому нельзя создавать новую с таким же именем
            int b = 3; // внутренняя не будет видна cнаружи
        }
        // b = 5; // здесь уже не видна


        //уничтожается при выходе из области действия (определяется блоком кода)
        // <<< здесь а умрет :(
    }
}
