package asserts;

/* Statement, который позволяет протестировать мое предположение относительно работы программы (и
убедиться, что все идет по плану)
*
* - по дефолту отключены (чтобы не понижать производительность - для оценки требуется столько же
* ресурсов, сколько и для вычисления аналогичного булеанового выражения). Когда отключены -
* эквивалентны пустым statements
*
* - улучшают производительность, потому что можно не писать проверки типа if (x < 0) throw new
* IllegalArgumentException("х < 0");), а просто отключить их в финальной программе
*
* - чтобы компилятор мог принимать код с assert, нужно использовать опцию командной строки -source
* 1.4 (флаг нужен, чтобы не было проблем совместимости): javac -source 1.4 MyClass.java
*
* - не приводит к проблемам с существующими бинарниками (.class), но если код использовал assert как
* идентификатор, то будет предупреждение или ошибка. Для таких случаев 2 режима перехода к новому
* порядку:
*   + source mode 1.3 (default) - компилятор принимает программы с идентификатором assert, но
*   показывает предупреждение. В таком случае не позволяется использовать assert.
*   + source mode 1.4 - компилятор генерирует ошибку в случае идентификатора assert. В таком случае
*   можно использовать assert*/


public class Main {


    public static void main(String[] args) {
        /*ФОРМА 1: assert Expression1;
         * - Expression1 - булеаново выражение, которое проверяется на истинность
         * - В объекте типа AssertionError конкретное значение выражения не хранится, поэтому его
         * нельзя запросить в дальнейшем*/
        //assert 5 > 10;
        /*ДАСТ ТОЛЬКО СТЕК ТРЕЙС:
         * Exception in thread "main" java.lang.AssertionError
         * at asserts.Main.main(Main.java:21)*/


        /*ФОРМА 2: assert Expression1 : Expression2;
         * - Expression1 - булеаново выражение, которое проверяется на истинность
         * - Expression2 - выражение, которое имеет значение (не может быть вызовом void метода)*/
        // assert 5 < 4 : "Хуяня якась"; // здесь мог бы быть код ошибки
        /*ДАСТ СТЕК ТРЕЙС С МОИМ ЗНАЧЕНИЕМ:
         * Exception in thread "main" java.lang.AssertionError: Хуяня якась
         * at asserts.Main.main(Main.java:34)*/
    }
}
