package _numbers_exponent;

public class Main {
    double doubleExponential = 6.022E23; // после Е степень 10-ти (т.к. по дефолту система 10-тичная)
    double doubleExponential2 = 314159E-05; // отрицательная степень
    double doubleExponential3 = 2e+100; // положительная степень и маленькая е
    double doubleExponentialSixteen = 0x1234.234p4; // по основанию 16 (вместо E/e стоит p/P)
    // (P – двоичный порядок, т.е. степень 2, на которую умножается число)

    float floatExponential = 6.022E23f; // после Е идет степень
    float floatExponential2 = 314159E-05f; // отрицательная степень
    float floatExponential3 = 2e+10f; // положительная степень и маленькая е
    float floatExponentialSixteen = 0x12.2P2f; // по основанию 16 (вместо E/e стоит p/P)
}
