package _packages_and_import.illusion_of_hierarchy;

//import packages_and_import.*; // не импортирует вложенные пакеты

import types_references_annotations.my_annotations.Ntrstn;

/*МНИМОСТЬ ИЕРАРХИИ ПАКЕТОВ
 * - пакеты packages_and_import.illusion_of_hierarchy и packages_and_import.other_package
 * не входят в пакет packages_and_import
 * - поэтому импорт всего пакета packages_and_import.* не приводит к импорту
 * packages_and_import.other_package
 * - вложенные пакеты только указывают на логическую связь*/


@Ntrstn("вложенные пакеты вообще никак не относятся к обрамляющему пакету")
public class InnerMain {
    public static int staticVar;

    public static void main(String[] args) {
        /*МНИМОСТЬ ИЕРАРХИИ ПАКЕТОВ*/
        PackageMain packageMain = new PackageMain(); // получен доступ через импорт
//        InnerMain2 innerMain2 = new InnerMain2(); // вложенный пакет не импортирован, нужен импорт
    }
}