package _lang_blocks;

/* БЛОКОВ МОЖЕТ БЫТЬ СКОЛЬКО УГОДНО И ГДЕ УГОДНО (В ТЕЛЕ КЛАССА) */

/* БЛОК ИСПОЛЬЗУЕТСЯ В СЛУЧАЯХ, КОГДА:
 * - необходимо инициализировать финализированную переменную
 * - значение поля неудобно вычислять с помощью выражения (напр., для этого нужен специально
 * созданный класс или метод, который не хочется создавать только для этих целей)
 * - во время инициализации необходимо обработать проверяемое исключение
 * - todo необходимо инициализировать поле анонимного класса (в анонимном классе невозможно
 * объявить конструктор) */


/* АЛЬТЕРНАТИВА - ПОМЕСТИТЬ КОД В МЕТОД
 * - и вызвать его для назначения значения
 * - в т.ч. для статических переменных*/

public class BlockUseCases {

    /*ИНИЦИАЛИЗАЦИЯ ФИНАЛИЗИРОВАННОЙ ПЕРЕМЕННОЙ*/
    final int var;

    {
        var = 3;
    }


    /*ЗНАЧЕНИЕ ПОЛЯ НЕУДОБНО ВЫЧИСЛЯТЬ ОДНИМ ВЫРАЖЕНИЕМ */
    double hardVar;

    {
        class MyInn extends Object {
            public double doTheLoop() {
                double random = 0.;
                for (int i = 0; i < 5; i++) {
                    random = Math.random();
                    try {
                        random = 5 / random;
                    } catch (ArithmeticException e) {
                        System.out.println("Division by zero");
                    }
                }
                return random;
            }
        }

        MyInn myInn = new MyInn();
        hardVar = myInn.doTheLoop();


        hardVar = alternative();
    }

    /*АЛЬТЕРНАТИВА - ПРИВАТНЫЙ МЕТОД*/
    static final double hardVar2 = alternative();


    /* НУЖНО ИНИЦИАЛИЗИРОВАТЬ ПОЛЕ В АНОНИМНОМ КЛАССЕ
     * todo доделать */
    AnonClass meth() {
        AnonClass anonClass = new AnonClass(5) {
            int newVal = 5; // инициализация поля другим значением

            void anMeth() {
                System.out.println("Anon non-original value: " + (anVal + newVal));
            }
        };
        return anonClass;
    }


    /* АЛЬТЕРНАТИВА - ПРИВАТНЫЙ МЕТОД*/
    private static double alternative() {
        class MyInn extends Object {
            public double doTheLoop() {
                double random = 0.;
                for (int i = 0; i < 5; i++) {
                    random = Math.random();
                    try {
                        random = 5 / random;
                    } catch (ArithmeticException e) {
                        System.out.println("Division by zero");
                    }
                }
                return random;
            }
        }
        MyInn myInn = new MyInn();
        return myInn.doTheLoop();
    }

    public static void main(String[] args) {
        BlockUseCases blockUseCases = new BlockUseCases();
        System.out.println(blockUseCases.var);
        System.out.println(blockUseCases.hardVar);
        System.out.println(blockUseCases.hardVar2);

        System.out.println(blockUseCases.meth().anVal);
        blockUseCases.meth().anMeth();

    }

    class AnonClass {
        int anVal;

        public AnonClass(int anVal) {
            this.anVal = anVal;
        }

        void anMeth() {
            System.out.println("Anon original value: " + anVal);
        }
    }
}