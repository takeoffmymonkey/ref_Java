package asserts.when_to_use;

/*ВНУТРЕННИЕ ИНВАРИАНТЫ
* - инвариант - определенность, не изменяемость*/

public class InternalInvariants {

    public static void main(String[] args) {
        /*Пример 1 (ОШИБОЧНЫЙ КОММЕНТАРИЙ):
         * - чтобы исключить ошибочную уверенность
         * БЫЛО:*/
        int i = -8;
        if (i % 3 == 0) {
            // вариант, где переменная делится на 3 без остатка
        } else if (i % 3 == 1) {
            // вариант, где переменная делится на 3 с остатком 1
        } else { // Значит i % 3 == 2
            /* НО ЭТО ОШИБОЧНОЕ ПРЕДПОЛОЖЕНИЕ!
             * так как есть еще отрицательные значения, если переменная была отрицательной!*/
        }

        /*Пример 1 (ОШИБОЧНЫЙ КОММЕНТАРИЙ):
         * СТАЛО:*/
        if (i % 3 == 0) {
            // вариант, где переменная делится на 3 без остатка
        } else if (i % 3 == 1) {
            // вариант, где переменная делится на 3 с остатком 1
        } else {
            assert i % 3 == 2 : i;
        }



        /*Пример 2 (SWITCH БЕЗ DEFAULT)
         * - уверенность, что какой-то case будет обязательно выполнен
         * БЫЛО:*/
        Suit suit = Suit.CLUBS;
        switch (suit) {
            case CLUBS:
                break;
            case DIAMONDS:
                break;
            case HEARTS:
                break;
            case SPADES:
        }
        /*Пример 2 (SWITCH БЕЗ DEFAULT)
         * СТАЛО:*/
        switch (suit) {
            case CLUBS:
                break;
            case DIAMONDS:
                break;
            case HEARTS:
                break;
            case SPADES:
                break;
            default:
                assert false : suit; // т.е. сюда и так не добраться в случае успеха
                /*Другой вариант: default: throw new AssertionError(suit);
                 * - затраты на вычисления такие же
                 * - но будет работать с отключенными assert*/
        }
    }


    enum Suit {CLUBS, DIAMONDS, HEARTS, SPADES}
}
