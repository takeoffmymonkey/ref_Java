package operators_control_flow_logic_breaking;

/* Для выхода из помеченного блока (когда нужно прерывание глубоко вложенных циклов при нарушении
логики управления программой, но неудобно задавать условия для проверки каждого из этих циклов)*/

public class Break_WithLabel {

    public static void main(String[] args) {
        System.out.println("\"Break_WithLabel\"");

        // Помечаем любой оператор или блок
        read_data:
        while (true) // этот оператор помечен меткой
            stay_looped:{ // а это метка блока - если сюда вернуться, то все равно будешь в лупе
                for (; true; ) // цикл не помечен
                {
                    if (true) // условие для прерывания
                        break read_data; // выход к метке (без повторного входа в оператор)
                    // break stay_looped; // так останусь в лупе
                }
            }
        // <<<< после break выход сюда из блока
        // по метке можно только выйти, но не зайти

        System.out.println("Благополучно выбрался из обоих лупов");

    }

}
