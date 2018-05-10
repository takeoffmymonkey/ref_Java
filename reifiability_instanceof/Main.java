package reifiability_instanceof;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import types_references_annotations.my_annotations.Ntrstn;

/* ДОСТУПНОСТЬ ИНФОРМАЦИИ О ТИПЕ ВО ВРЕМЯ РАБОТЫ С НИМ В RUNTIME
 * - позволяет узнавать тип при помощи оператора instanceof
 *      - только для ссылочных типов
 *
 * - использовать тип в качестве элемента массива
 *      - т.к. JVM обязана контролировать реальный тип массива и выбрасывать ArrayStoreException при
 *      некорректном добавлении элементов в него, чтобы избежать загрязнения кучи
 *          - такое возможно при назначении массива родительской ссылке и добавления в него
 *          родительских элементов */


/* МАТЕРИАЛИЗИРУЕМЫЕ ТИПЫ - ИНФОРМАЦИЯ О ТИПЕ ПОЛНОСТЬЮ ДОСТУПНА В RUNTIME
 * - необобщенный тип
 *      Object o;
 *      Serializable s;
 *
 * - обобщенный тип, у которого все аргументы типа являются неограниченными метасимволами
 *      List<?> l;
 *          - т.е. параметр типа нас не интересует
 *              - т.к. это может быть вообще любой параметр
 *
 * - базовые тип
 *      List l;
 *
 * - примитивный тип
 *      int i;
 *      boolean b;
 *
 * - массив, у которого элементы материализируемого типа
 *      Object [] o;
 *
 * - todo вложенный тип, где для каждого типа Т, разделенного точкой, Т является материализируемым типом
 *      - если у обобщенного класса X<T> есть обобщенный член класса Y<U>, то тип X<?>.Y<?>
 *      материализируемый, потому что X<?> */


/* НЕМАТЕРИАЛИЗИРУЕМЫ ТИПЫ - ИНФОРМАЦИЯ О ТИПЕ ПОЛНОСТЬЮ ИЛИ ЧАСТИЧНО ОТСУТСТВУЕТ В RUNTIME
 * - обобщенный тип, у которого в аргументах типа нет неограниченного метасимвола
 *      - напр. List<String> и List<Number> не отличимы для JVM
 *      - напр. для List<? extends Number> пришлось бы убедиться, что у объекта параметр типа
 *      соответствует указанной границе  */


@Ntrstn("Доступность информации о типе во время работы позволяет узнавать этот тип в runtime при " +
        "помощи оператора instanceof (работает только для ссылочных), а также контролировать типы " +
        "элементов, которые добавляются в массив (так JVM сможет выбросить ArrayStoreException при " +
        "добавлении родительского элемента в массив типа наследника, а это возможно, если сначала " +
        "такой массив назначить ссылке родительского типа)")

@Ntrstn("Материализируемые типы - информация о типе полностью доступна во время работы программы, " +
        "нематериализируемый - информация не полностью доступна или не доступна. Единственный тип, " +
        "который относится к нематериализируемым - это обобщенный тип, если у него в качестве " +
        "параметра указан НЕ неограниченный метасимвол. JVM не знает реальный тип параметра, т.к. к " +
        "моменту работы программы он стерт. Если тип параметра указан как ограниченный метасимвол, " +
        "JVM все равно пришлось бы сверять реальный параметр типа с этой границей, что также " +
        "невозможно сделать в runtime")


public class Main {

    /*~~~~~~~~~~~~~~~~~~МАТЕРИАЛИЗИРУЕМЫЕ ТИПЫ~~~~~~~~~~~~~~~~~~*/
    Number object = new Integer(1);
    Serializable interf = new Serializable() {
        @Override
        public int hashCode() {
            return super.hashCode();
        }
    };
    List<?> listUnkn = new ArrayList<String>();
    List listRaw = new ArrayList();
    int integ = 1;
    boolean bool = false;
    Object[] objArr = new Object[1];

    /*~~~~~~~~~~~~~~~~~~НЕМАТЕРИАЛИЗИРУЕМЫЕ ТИПЫ~~~~~~~~~~~~~~~~~~*/
    List<String> listString;
    List<? extends Number> listUnknBound;


    public static void main(String[] args) {
        Main m = new Main();

        System.out.println(m.object instanceof Number);
        System.out.println(m.interf instanceof Serializable);
        System.out.println(m.listUnkn instanceof List<?>);
        System.out.println(m.listRaw instanceof List);
//        System.out.println(m.integ instanceof int); // instanceof работает только для объектов
        System.out.println(m.objArr instanceof Object[]);

//        System.out.println(m.listString instanceof List<String>); // нематериализируемый
//        System.out.println(m.listUnknBound instanceof List<? extends Number>); // нематериализируемый
    }
}
