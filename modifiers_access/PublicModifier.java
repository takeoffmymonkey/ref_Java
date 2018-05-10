package modifiers_access;

/* Не ограничивает область действия классом */


/*КЛАСС
 * - может быть либо public либо default
 * - в файле может быть не больше 1 публичного класса (или интерфейса)
 * - чтобы получить доступ из другого пакета, класс нужно импортировать*/
public class PublicModifier {

    /*ПУБЛИЧНОЕ ПОЛЕ
     * - наследуется подклассами */
    public int publicField;

    /*ПУБЛИЧНЫЙ КОНСТРУКТОР
     * - в аргументах модификатор доступа не указывается*/
    public PublicModifier() {
    }

    /*ПУБЛИЧНЫЙ МЕТОД
     * - наследуется подклассами
     * - в аргументах модификатор доступа не указывается*/
    public void publicMethod() {
    }

    /*ПУБЛИЧНЫЙ МЕТОД СУПЕРКЛАССА (ПЕРЕОПРЕДЕЛЕНИЕ)
     * - не должен сокращать видимость (т.е. должен оставаться public)*/
    @Override
    public String toString() {
        return super.toString();
    }

    /* ВНУТРЕННИЙ КЛАСС
     * - может быть публичный*/
    public class PublicInnerClass {
        public int publicInnerField;

        public void publicInnerMethod() {
        }
    }

    /* ВНУТРНЕННИЙ СТАТИЧЕСКИЙ (ВЛОЖЕННЫЙ) КЛАСС
     * - может быть публичный*/
    public static class PublicInnerStaticClass {
        public int publicInnerStaticClassField;

        public void publicInnerStaticClassMethod() {
        }
    }

    /* ВНУТРЕННИЙ ИНТЕРФЕЙС
     * - может быть публичный*/
    public interface PublicInnerInterface {
        int alwaysPublicStaticFinalVar = 0;

        void alwaysPublicAbstractMethod();
    }

}

/*ВНЕШНИЙ ИНТЕРФЕЙС
 * - может быть публичный, но должен быть в отдельном файле!*/
interface PublicOuterInterface {
    /*ПЕРЕМЕННАЯ ИНТЕРФЕЙСА
     * - всегда public static final*/
    int alwaysPublicStaticFinalVar = 0;

    /*МЕТОД ИНТЕРФЕЙСА
     * - всегда public abstract*/
    void alwaysPublicAbstractMethod();
}