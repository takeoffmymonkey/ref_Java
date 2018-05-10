package operators_other;

/* - узнать, произошел ли объект от указанного класса, чтобы избежать runtime error
 *
 * - приведение типов в пределах иерархии классов может стать причиной ошибок, которые обнаруживаются
 * только во время выполнения
 *
 * - при разработке обобщенных процедур, оперирующих объектами из сложной иерархии классов
 *
 * - большинство программ не нуждается в нем, поскольку типы объектов обычно известны заранее
 *
 * - если объект не был инициализирован, то он будет давать false
 * */

public class InstanceOf {

    public static void main(String[] args) {

        /*ПРИМЕНЕНИЕ
         * - Только для ссылочных переменных
         * - Сравнивает тип переменной с референтным типом
         * - Возвращает boolean
         * - Вернет true при совместимости типов*/
        InstanceOf ref = new InstanceOf();
        if (ref instanceof Object) {
            ref.toString();
        }
    }
}