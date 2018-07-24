package _lang_literals;

public class Types {

    /*ЦЕЛОЧИСЛЕННЫЕ*/
    byte b = 123; // не должен привышать диапазон byte
    short s = 12312; // не должен привышать диапазон short
    char d = 12334;// char также является целочисленным типом, не должен превышать диапазон char
    int ten = 0;// по основанию 10: цифры 0..9
    int tenSeparated = 1______000___000;
    int two = 0b1010; // по основанию 2 (c J7): цифры 0..1 с префиксом 0b
    int twoSeparated = 0b1111_0100_0001; // c J7
    int eight = 03; // по основанию 8: цифры 0..7 с префиксом 0
    int eightSeparated = 02_3_34_2;
    int sixteen = 0x013F; // по основанию 16: 0..15 A..F с префиксом 0x (или 0X)
    int sixteen2 = 0X013f;
    int sixteenSeparated = 0x0_1____3F;
    long sixteenLong = 0x7fffffffffffffffL; // AKA 9223372036854775807L
    long sixteenLong2 = 0x7fffffffffffffffl; //маленькая l не рекомендуется
    long sixteenLongSeparated = 0x7fffffffffff_______ffffL;

    /*С ПЛАВАЮЩЕЙ ТОЧКОЙ*/
    double doubleIsDefault = 2.1; // дефолтное значение всегда в double
    double doubleIsDefaultButStill = 2.1d; // но все равно можно указать explicitly
    double doubleIsDefaultButStill2 = 2.1D; // но все равно можно указать explicitly
    double doubleIsDefaultButStill3 = 2D; // но все равно можно указать explicitly
    double doubleIsDefaultButStill4 = 2.d; // но все равно можно указать explicitly
    double doubleExponential = 6.022E23; // после Е идет степень 10-ти
    double doubleExponential2 = 314159E-05; // отрицательная степень
    double doubleExponential3 = 2e+100; // положительная степень и маленькая е
    double doubleExponentialSixteen = 0x12.2P2; // по основанию 16 (вместо E/e стоит p/P)
    // (P – двоичный порядок, т.е. степень 2, на которую умножается число)
    double doubleSeparated = 9__424_489_123.0_2_9;
    float floatIsNotDefault = 2.1f; // чтобы вручную перевести в float - добавить в конец f/F
    float floatIsNotDefault2 = 2.1F;
    float floatIsNotDefault3 = 2F;
    float floatIsNotDefault4 = 2.f;
    float floatExponential = 6.022E23f; // после Е идет степень
    float floatExponential2 = 314159E-05f; // отрицательная степень
    float floatExponential3 = 2e+10f; // положительная степень и маленькая е
    float floatExponentialSixteen = 0x12.2P2f; // по основанию 16 (вместо E/e стоит p/P)
    float floatSeparated = 2.1_34_3422F;


    /*ЛОГИЧЕСКИЕ*/
    // boolean wrong = 1; // Не имеют численных представлений (не 0 и 1 как в С)
    boolean moteThanLiteral = true; // true и false - вообще ключевые слова


    /*СИМВОЛЬНЫЕ*/
    char unicodeIndexes = '\u0023'; // могут быть индексами из символов Unicode (заключаются в ‘ ‘)
    char simple = 'd'; // могут быть символами (заключаются в ‘ ‘)


    /*СТРОКОВЫЕ*/
    String stringLiteral = "Строковый литерал"; // Заключаются в “ “
    String stringLiteralInQuotes = "\"Строковый литерал в кавычках\""; // отражающие символы
    // Должен начинаться и заканчиваться на 1 строке
    String stringLiteralLong = "LKDSFDFLKJDSFVLSDFLDSKJFSLDKJFLSKDJFLKJCLSKDCLKNSDLCKNSLDKCNSLDKNCLKSDNCLKSDNCLSDKCLDKCNSLDKC";
    //Длинный литерал можно разбить на малые и конкатенировать +
    String stringLiteralLongSeparated = "LKDSFDFLKJDSFVLSDFLDSKJFSLDKJFLSKD" +
            "JFLKJCLSKDCLKNSDLCKNSLDKCNSLDKNCLKSDNCLKSDNCLSDKCLDKCNSLDKC";


    public static void main(String[] args) {
        /*ДЕФОЛТНЫЙ ТИП ЦЕЛОЧИСЛЕННОГО ЛИТЕРАЛА
         * - по дефолту является int
         * - при назначении в тип меньше, чем int, должен вмещаться в его диапазон или явно приводиться
         * - при назначении в тип больше, должен быть не больше диапазона int, или явно указать l/L
         * - преобразование в литерал с плавающей точкой происходит автоматически по целевому типу*/
        System.out.println(((Object) 5555555).getClass().getName()); // даст java.lang.Integer
        byte byteA = 127; // ок
        //byte byteB = 128; // ошибка компилятора
        byte byteC = (byte) 128; // явное приведение
        long longA = 1231231; // ок
        //long longB = 1231231234344; // ошибка компилятора
        //long longC = (long) 1231231234344; // все равно ошибка компилятора
        long longC = 1231231234344L; // ок
        double doubleD = 132; // авто-преобразование, не требует явного указания типа (выводится?)
        float floatD = 132; // авто-преобразование, не требует явного указания типа (выводится?)


        /*ДЕФОЛТНЫЙ ТИП ЛИТЕРАЛА С ПЛАВАЮЩЕЙ ТОЧКОЙ
         * - по дефолту является double
         * - при назначении в float, должен явно указать f/F
         * - для преобразования в целочисленное требуется явное привидение*/
        System.out.println(((Object) 5555555.4).getClass().getName()); // даст java.lang.Double
        double doubleA = 33.3;
        double doubleB = 33.3f; // ок, так как целевой тип шире исходного
        // float floatA = 33.3; // должен указать тип float явно
        float floatA = 33.3f; // ок
        //byte b = 234.4; // требуется явное приведение
        byte b = (byte) 234.4; // ок
    }
}
