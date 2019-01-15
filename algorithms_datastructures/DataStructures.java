package algorithms_datastructures;

/* Bit Vector
 * - массив битов
 *
 * - реализации в Java: EnumSet
 *
 * - достоинства:
 *      - очень компактный и очень быстрый
 *      - если малого размера, то могут храниться в регистрах
 *
 * - недостатки:
 *      - без компрессии могут тратить память для разряженных наборов
 *          - т.е. мало элементов, но большая вместимость
 *
 * - скорость:
 *      - доступ: O(1)
 *      - поиск: O(1)
 *      - вставка: O(1)
 *      - удаление: O(1) */


/* Static Array, Dynamic Array
 * - под размер массива выделяется блок памяти
 *
 * - элементы идентифицируются по индексу
 *      - целые числа из непрерывного диапазона
 *
 * - у Dynamic Array размер массива может меняться во время работы
 *      - выделяется новый больший блок памяти, куда копируется предыдущий, и тот удаляется
 *      - в самой структуре меняется ссылка и поле размера
 *
 * - достоинства:
 *      - скорость вычисления адреса элемента по индексу
 *          - т.е. они находятся друг за другом
 *      - одинаковое время доступа ко всем элементам
 *      - малый размер элемента
 *          - только данные
 *
 * - недостатки:
 *      - у статического нельзя менять размер
 *      - затраты на сдвиг массива при удалении/вставке элемента
 *      - затраты на копирование массива при расширении размера
 *
 * - реализации в Java:
 *      - статический: ArrayBlockingQueue
 *      - динамический: ArrayList, CopyOnWriteArrayList, CopyOnWriteArraySet, ArrayDeque
 *
 * - скорость:
 *      - доступ: O(1)
 *      - поиск: O(n)
 *      - вставка: O(n)
 *      - удаление: O(n) */


/* Linked List, Doubly Linked List
 * - состоит из узлов (Nodes)
 *      - содержит:
 *          - данные
 *          - ссылку на следующий узел
 *              - последний узел ссылатся на null
 *              - обеспечивает передвижение вперед
 *                  - получить ссылку на предыдущий невозможно
 *
 * - хранит ссылку на первый узел
 *
 * - в случае Doubly-Linked List каждый узел имеет ссылку на предыдущий
 *      - движение двунаправленное
 *          - легче удалять и менять местами элементы
 *
 * - преимущества:
 *      - эффективное добавление и удаление элементов
 *      - размер ограничен только памятью компьютера
 *      - динамическое добавление и удаление элементов
 *
 * - недостатки:
 *      - сложность прямого доступа к элементу
 *      - расход памяти на указатели
 *      - соседние элементы списка могут быть распределены в памяти нелокально, что снизит
 *      эффективность кэширования данных в процессоре
 *      - гораздо труднее производить параллельные операции, чем с массивами
 *
 * - реализации в Java:
 *      - Linked List: ConcurrentLinkedQueue, LinkedBlockingQueue, LinkedTransferQueue
 *      - Doubly Linked List: LinkedList, LinkedHashSet, LinkedHashMap
 *
 * - скорость:
 *      - доступ: O(n)
 *      - поиск: O(n)
 *      - вставка: O(1)
 *      - удаление: O(1) */


/* Skip List
 * - ключевая идея: если над оригинальным отсортированным списком построить несколько слоев
 * дополнительных списков, сохраняющих порядок, но имеющих все меньше элементов с каждым новым слоем,
 * то если начинать поиск элемента с верхнего слоя, и при необходимости спускаться все ниже, то
 * понадобится меньше времени
 *
 * - состоит из нескольких отсортированных Doubly Linked List
 *      - т.н. слоев
 *
 * - нижний слой всегда содержит все элементы
 *
 * - одинаковые элементы в соседних слоях имеют ссылки друг на друга
 *
 * - любой слой начинается с самого малого значения, чтобы любая вставка происходила после него
 *          - т.е. напр. минус бесконечности
 *
 * - после обязательного добавления элемента в соответствующее место в нижнем слое, далее
 * используется вероятность (например, орел) для определения, нужно ли дублировать элемент на слое
 * выше
 *      - при необходимости верхний слой создается
 *          - в нем также необходимо создать минимальный элемент со ссылкой на этот же элемент в
 *          слое ниже
 *      - если выпадает орел, то этот элемент также добавляется в слой выше в соответствующее место
 *          - он связывается с элементами на своем уровне
 *              - а также связывается с собой на нижнем уровне
 *
 * - после каждой операции добавления (независимо от слоя) нужно повторно определить, нужно ли
 * дублировать этот элемент на уровне выше
 *      - и так пока не выпадет решка
 *
 * - таким образом создается:
 *      - сетка слоев
 *      - древовидная структура данных, которая облегчает операцию поиска
 *
 * - операция поиска элемента начинается с верхнего слоя с минимального элемента
 *      - искомый элемент сравнивается с текущим
 *          - если текущий меньше - переходим к следующему элементу на том же уровне
 *          - если текущий он - конец
 *          - если текущий больше - возврат к предыдущему и понижение уровня
 *
 * - удаление - поиск, удаление, и пересвязывание соседних элементов
 *
 * - реализации в Java: ConcurrentSkipListSet, ConcurrentSkipListMap
 *
 * - преимущества:
 *      - хорошая скорость всех операций
 *      - больше подходит для параллельного вычисления, чем Binary Search Tree
 *
 * - скорость:
 *      - средняя:
 *          - доступ: O(log(n))
 *          - поиск: O(log(n))
 *          - вставка: O(log(n))
 *          - удаление: O(log(n))
 *      - худшая:
 *          - доступ: O(n)
 *          - поиск: O(n)
 *          - вставка: O(n)
 *          - удаление: O(n) */


/* Binary Tree
 * - требования:
 *      - у каждого узла (родителя) может быть не больше 2 потомков (левый и правый наследники)
 *          - узел без потомков - лист
 *      - поддеревья также являются деревьями
 *
 * - является основанием для многих других структур
 *      - напр. Binary Search Tree, Binary Heap
 *
 * - могут быть несбалансированными (перевес на одной стороне) или балансированными (ровное
 * распределение узлов)
 *      - при перевесе какой-то стороны теряются преимущества
 *      - напр. сбалансированное: Binary Heap, Red-Black Tree */


/* - Binary Search Tree
 * - дополнительные требования:
 *      - значение ключа левого узла-наследника и всех ключей его наследников меньше, чем самого
 *      родительского узла
 *      - значение ключа правого узла-наследника и всех ключей его наследников больше и равны значению
 *      ключа самого родительского узла
 *
 * - узел содержит ссылки на левого и правого наследника, а также данные
 *      - также может быть ссылка на родительский узел
 *      - данные должно быть можно сравнивать
 *
 * - из-за того, что код удаления очень неудобный, можно просто помечать нод как удаленный
 *
 * - преимущества:
 *      - высокая эффективность поиска и сортировки
 *
 * - скорость:
 *      - средняя:
 *          - доступ: O(log(n))
 *          - поиск: O(log(n))
 *          - вставка: O(log(n))
 *          - удаление: O(log(n))
 *      - худшая:
 *          - доступ: O(n)
 *          - поиск: O(n)
 *          - вставка: O(n)
 *          - удаление: O(n) */


/* Red-Black Tree
 * - самобалансирующееся Binary Search Tree
 *
 * - дополнительные требования: так достигается сбалансированность
 *      - узел дополнительно либо красный, либо черный
 *      - корень черный
 *          - не очень важно
 *          - не относится к поддеревьям
 *      - все листья - черные и не хранят никаких данных
 *      - оба потомка красного узла - черные
 *      - глубина в черных узлах одинакова по всем путям от корневого до конечных потомков
 *          - т.е. если от корня (в т.ч. поддерева) пройтись по любому из его подпутей, сумма черных
 *          всегда будет такая же, как и у других подпутей
 *
 * - самый длинный путь (от корня к дальнейшему листу) не больше, чем в 2 раза длины самого
 * короткого пути (от корня к ближайшему листу)
 *      - самый короткий состоит только из черных узлов
 *      - самый длинный из чередующихся красных и черных
 *
 * - преимущества:
 *      - быстрее Binary Search Tree, т.к. сортировано
 *
 * - реализации в Java: TreeSet, TreeMap
 *
 * - скорость:
 *      - доступ: O(log(n))
 *      - поиск: O(log(n))
 *      - вставка: O(log(n))
 *      - удаление: O(log(n)) */


/* Binary Heap + Priority Queue
 * - дополнительные условия:
 *      - значение в любой вершине не меньше, чем значения её потомков
 *          - т.н. max-heap
 *              - бывают также min-heap
 *      - глубина всех листьев отличается не более чем на 1 слой
 *      - последний слой заполняется слева направо без «дырок»
 *
 * - учитывая, что у элементов есть некоторый порядок, то имплементация может основываться на массиве
 *      - т.е. 0 элемент - корень и т.д.
 *
 * - когда удаляется корень, то на его место становится последний, чтобы обеспечить заполненность, а
 * потом этот последний опускается по уровням на свое место, меняясь местами с тем, с которым
 * сравнивается
 *      - т.н. починка свойств кучи или Heapify
 *
 * - вставка: добавляется в конец, а потом соответсвенно поднимается, сравниваясь с родителем
 *      - т.н. починка хипа
 *
 * - смена значения элемента просто требует починки хипа
 *
 * - т.к. самый большой элемент всегда сверху, то структуру можно использовать, например, как
 * Priority Queue
 *
 * - реализации в Java: PriorityQueue, PriorityBlockingQueue, DelayQueue
 *
 * - скорость:
 *      - доступ: O(1)
 *      - поиск: O(n)
 *      - вставка: O(1)
 *      - удаление: O(log(n)) */


/* Hash table
 * - на основе массива
 *
 * - хранится пара: ключ и объект
 *
 * - ключевой операцией является вычисление хеш-функции по ключу:
 *      - по какому-то алгоритму вычисляется (object.hashCode()) хеш-значение, которое затем по
 *      какому-то алгоритму переводится в индекс массива
 *      - нередко возникают коллизии - одинаковый индекс для разных значений
 *          - для 365 ячеек из 23 элементов вероятность коллизии > 50%
 *              - т.н. Парадок дней рождений
 *          - если ключи известны заранее, то можно было бы написать и "совершенную хеш-функцию"
 *              - т.н. прямая адресация
 *
 * - коэффициент загрузки: число хранимых элементов/размер массива
 *      - играет важную роль при определении среднего времени выполения операции
 *      - при достижении определенного коэффициента необходимо создавать новую таблицу и копировать
 *      все элементы
 *          - напр., у HashMap и HashTree предельный коэффициент 0.75
 *
 * - 2 варианта разрешения коллизий:
 *      - с цепочками:
 *          - каждая ячейка массива указывает на Linked List c парами при ключ-значение
 *          - при совпадении, к списку добавляется дополнительный элемент
 *          - чтобы найти элемент, определяется индекс, и перебираются все ключи по индексу
 *
 *      - с открытой адресацией:
 *          - пары ключ-значение хранятся в самих ячейках массива
 *          - если происходит коллизия, то индекс инкрементируется пока не будет найдена первая
 *          свободная ячейка
 *          - поиск аналогично: сначала по индексу, а потом в следующих ячейках
 *              - т.е. по тому же алгоритму, по которому происходит добавление
 *          - удаление затруднено, поэтому для каждой ячейки используют булевый флаг удаления
 *          - чем выше коэффициент загрузки, тем медленнее работа:
 *              - если он 0.5, то проб максимум 2
 *              - если 0.8, то уже максимум 5
 *              - если 0.9, то максимум 10
 *
 * - реализации в Java: HashSet, LinkedHashSet, HashMap, LinkedHashMap, WeakHashMap, ConcurrentHashMap
 *
 * - скорость:
 *      - средняя:
 *          - поиск: O(1)
 *          - вставка: O(1)
 *          - удаление: O(1)
 *      - худшая:
 *          - поиск: O(n)
 *          - вставка: O(n)
 *          - удаление: O(n) */

public class DataStructures {
}
