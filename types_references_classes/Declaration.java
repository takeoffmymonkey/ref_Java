package types_references_classes;

import java.io.Serializable;

import _types_references_annotations.my_annotations.Ntrstn;


/*ОБЪЯВЛЕНИЕ
 * - модификатор доступа:
 *      - 1 публичный класс в файле
 *
 * - имя класса с большой буквы (по конвенции)
 *      - имя файла должно совпадать с именем публичного класса
 *
 * - если есть, суперкласс после слова extends
 *
 * - если есть, список имлементируемых методов
 *
 * - тело в фигурных скобках*/


@Ntrstn("Java файл может содержать только 1 публичный класс, и имя этого класса должно совпадать с " +
        "именем файла")


/* КЛАСС ВЕРХНЕГО УРОВНЯ НЕ МОЖЕТ БЫТЬ СТАТИЧЕСКИМ */
public class Declaration extends Object implements Serializable {
}