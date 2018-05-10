package types_references_classes;

/*ВНУТРИ МЕТОДА ИЛИ КОНСТРУКТОРА ДЛЯ ССЫЛКИ НА ТЕКУЩИЙ ОБЪЕКТ
 * - можно использовать везде, где разрешается ссылаться на объект текущего класса
 * - напр. чтобы избежать затемнения:
 *      - поля с аналогичным именем
 *      - метода с аналогичным именем в подклассе
 *          - т.к. подкласс имеет доступ ко всем членам обрамляющего класса
 *          - и не существует без объекта обрамляющего класса*/


/*ВНУТРИ КОНСТРУКТОРА ДЛЯ ВЫЗОВА ДРУГОГО КОНСТРУКТОРА
 * - для вызова другого конструктора данного класса
 * - сокращает количество кода
 * - должен быть со скобками () и параметрами нужного конструктора
 * - должен идти первым
 * - нельзя использовать переменную экземпляра
 * - нельзя использовать вместе с super()
 * - медленней, чем конструктор с полным кодом
 *      - нужно создавать тысячи объектов*/

public class This {
    int field;


    /*ИСПОЛЬЗОВАНИЕ В КОНСТРУКТОРЕ ДЛЯ ВЫЗОВА ДРУГОГО КОНСТРУКТОРА*/
    public This() {
        this(5); // должен идти первым
//        super(); // нельзя использовать с this (первый вызов уже занят)
//        this(field);// нельзя использовать переменную экземпляра
    }


    /*ИСПОЛЬЗОВАНИЕ В КОНСТРУКТОРЕ ДЛЯ ССЫЛКИ НА ТЕКУЩИЙ ОБЪЕКТ*/
    public This(int field) {
        this.field = field; // параметр field затемняет поле field
    }


    /*ИСПОЛЬЗОВАНИЕ В МЕТОДЕ ДЛЯ ССЫЛКИ НА ТЕКУЩИЙ ОБЪЕКТ*/
    void meth(int field) {
        this.field = field; // параметр field затемняет поле field
    }

    /*ИСПОЛЬЗОВАНИЕ В МЕТОДЕ ДЛЯ ССЫЛКИ НА ТЕКУЩИЙ ОБЪЕКТ - ВО ВНУТРЕННЕМ КЛАССЕ*/
    class Inner {
        void meth() {
            This.this.meth(4);
        }
    }
}