package modifiers_other;

/*ФИНАЛИЗИРОВАННЫЙ КЛАСС
 * - запрещает наследование
 * - поэтому не может быть одновременно и final и abstract */
public final class FinalModifier {

    /*ФИНАЛИЗИРОВАННОЕ ПОЛЕ
     * - не может быть изменено
     * - не инициализируется по умолчанию
     * - должно быть сразу инициализировано или в конструкторе
     * - часто используется со static, чтобы сделать константу доступной*/
    final int finalField = 0;
    final int finalField2;


    public FinalModifier() {
        finalField2 = 0; // Допускается инициализация финализированного поля в конструкторе
    }

    /*ФИНАЛИЗИРОВАННЫЙ МЕТОД
     * - не может быть переопределен в подклассе*/
    final void finalMethod(final int finalLocalVar) {
        /*ФИНАЛИЗИРОВАННЫЙ АРГУМЕНТ
         * - не может быть изменен*/
        //finalLocalVar = 6;
    }


    /*НЕФИНАЛИЗИРОВАННЫЙ МЕТОД
     * - не может быть переопределен в подклассе, ТОЛЬКО если его класс финализирован*/
    void finalMethod() {
        /*ФИНАЛИЗИРОВАННАЯ ССЫЛКА НА ЛЮБОЙ ОБЪЕКТ
         * - всегда будет ссылаться на тот же объект
         * - сам объект может быть изменен
         * - поэтому можно сразу не инициализировать
         * - справедливо и для массивов, так как они также являются объектами*/
        final Object finalObject;
        finalObject = new Object();
        //finalObject = new Object(); // нельзя, так как уже была инициализация

        final Object[] finalObjectArray;
        finalObjectArray = new Object[]{};
    }
}


/*ИНТЕРФЕЙС
 * - не может быть финализированным, так как запретит наследование*/
interface NonFinalInterface {
    /*ПЕРЕМЕННАЯ ИНТЕРФЕЙСА
     * - всегда public, static, final*/
    int alwaysPublicStaticFinal = 0;

    /*МЕТОД ИНТЕРФЕЙСА
     * - не может быть финализированным
     * - так всегда abstract и public*/
    void alwaysPublicAbstract();
}