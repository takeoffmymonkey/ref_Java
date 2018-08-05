package _lang_variables;

public class Declaration_Initialization {
    /*ОБЪЯВЛЕНИЕ*/
    int a;

    {
        /*ИНИЦИАЛИЗАЦИЯ
         * - должна возвращать тот же тип*/
        a = 4;
    }

    /*ОБЪЯВЛЕНИЕ И ИНИЦИАЛИЗАЦИЯ*/
    int b = 5;

    /* ДИНАМИЧЕСКАЯ ИНИЦИАЛИЗАЦИЯ
     * - с помощью выражения, действительного в момент объявления (в т. ч. вызовы методов, другие
     * переменные и константы)*/
    double c = Math.sqrt(a);

}