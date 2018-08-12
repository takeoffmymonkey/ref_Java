package operators_control_flow_conditional;

/* Предоставляет новый выбор, если результат логического выражения if – false*/

public class ElseIf {

    public static void main(String[] args) {
        System.out.println("\"ElseIf\"");

        if (1 < 0) ;
        else if (0 < 1) ;
        else ;//сюда не дойдет


        /*else в конце не обязателен*/
        if (1 < 0) ;
        else if (0 < 1) ;
    }
}
