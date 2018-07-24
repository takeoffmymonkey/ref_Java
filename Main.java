import _types_references_annotations.my_annotations.Ntrstn;

@Ntrstn("когда нет пакета, то относится к дефолтному unnamed")
/* ОТНОСИТСЯ К ДЕФОЛТНОМУ ПАКЕТУ UNNAMED*/
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.getClass().getPackage()); // null, а не unnamed
    }
}