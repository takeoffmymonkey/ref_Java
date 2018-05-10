package operators_control_flow_logic_breaking;

/* Для выхода из текущего блока в операторе выбора или из тела цикла*/

public class Break {

    public static void main(String[] args) {
        System.out.println("\"Break\"");

        /*ВЫХОД ИЗ ЛУПА*/
        while (true) {
            System.out.println("В вечном цикле? Нет.");
            if (1 > 0) {
                break;
            }
        }
        // <<<< Выход сюда


        /* ВЫХОД ТОЛЬКО ИЗ ОДНОГО ЛУПА*/
        while (true) {
            System.out.println("В вечном цикле? Возможно.");
            while (true) if (1 > 0) break;
            //<<<< Выход только сюда
            break;// нужен, чтоб-таки выйти из лупа окончательно
        }


        /*ВЫХОД ИЗ ОПЕРАТОРА ВЫБОРА*/
        switch ("НЕСТРОКА") {
            case ("НЕСТРОКА"):
                System.out.println("Совпадение с НЕСТРОКА");
                break; // иначе будут ВЫПОЛНЕНЫ и следующие cases до конца блока или до break
            case ("СТРОКА"):
                System.out.println("Совпадение с СТРОКА");
                break;
        }
        // <<<< выход сюда
    }
}
