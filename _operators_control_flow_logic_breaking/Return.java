package _operators_control_flow_logic_breaking;

/* - для возвращения управления из метода, который был вызван, в метод, который его вызвал */

public class Return {

    public static void main(String[] args) {
        System.out.println("\"Return\"");

        returningMethod();

        finallyMethod();
    }

    /*ПРОСТОЕ ВОЗВРАЩЕНИЕ ИЗ МЕТОДА*/
    static void method() {
        /* должен быть в методе*/
        return;
        // int v = 4; // сюда не дойдет
    }

    /*ВОЗВРАЩЕНИЕ ЗНАЧЕНИЯ*/
    static int returningMethod() {
        /*Если метод возвращает значение – оно указывается после return: return выражение;*/
        method(); // <<< вернусь сюда после выполнения
        return 5;
    }


    /*БЛОК FINALLY ВСЕ РАВНО ВЫПОЛНИТСЯ*/
    static void finallyMethod() {
        try {
            int a = 5 / 0;
        } catch (ArithmeticException e) {
            System.out.println("Попытаюсь выйти до блока finally");
            return;
        } finally {
            // все равно выполниться
            System.out.println("Не смог. Нахожусь в блоке finally");
        }
    }

    /* todo в лямбда-выражении вызывает возврат из самого выражения, но не из обрамляющего метода*/
}