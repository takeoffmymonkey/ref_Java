package modifiers_other;

/* - Применяется в многопоточных приложениях, чтобы главная копия переменной постоянно отражала ее
 * текущее состояние (т.е. чтобы позволить знать JVM, что поток доступа к переменной всегда должен
 * объединять свою собственную копию переменной с главной копией в памяти)
 *
 * - Доступ к переменной volatile синхронизирует все кэшированные скопированные переменные в
 * оперативной памяти */


public class VolatileModifier {
    /*ПОЛЕ КЛАССА
     * - может быть volatile
     * - ссылка на объект может быть null;
     * - может быть статическим
     * - не может быть final*/
    volatile Object[] objectField;
    volatile static int intField;
    // volatile final int intField;// нельзя
}