package _formatting;

/* ИНТЕРФЕЙС FORMATTABLE
 * - для кастомных классов
 * - обеспечивает частичный функционал Formatter */


/* FORMATTO(Formatter formatter, int flags, int width, int precision)
 * - единственный метод, который нужно имплементировать
 *
 * - вызывается символом преобразования 's' (вместо toString())
 *
 * - formatter: экземпляр Formatter. Имплементирующие классы могут вызывать formatter.out() или
 * formatter.locale(), чтобы получить Appendable или Locale, используемый этим formatter
 *
 * - flags: флаги модифицируют выходной формат. Значение интерпретируется как битовая маска. Может
 * быть установлена любая комбинация из следующий флагов: FormattableFlags.LEFT_JUSTIFY,
 * FormattableFlags.UPPERCASE и FormattableFlags.ALTERNATE. Если нет флага, применяется дефолтное
 * форматирование имплементирующего класса.
 *
 * - width: минимальное количество символов для записы в вывод. Если длина конвертированного
 * значения меньше, чем ширина, тогда вывод будет смещен пробелами, пока финальное число символов
 * не будет равно ширине. Оступы по дефолту начинаются с начала. Если установлен флаг
 * FormattableFlags.LEFT_JUSTIFY flag, то отступы будут с конца. Если длина -1, то минимума нет.
 *
 * - precision: максимальное число символов для записи в вывод. Точность применяется до ширины,
 * таким образом вывод будет обрезан до символов точности, даже если ширина больше, чем точность.
 * Если точность -1, то явного лимита на количество символов нет.
 *
 * - IllegalFormatException - выбрасывается, если любой из параметров невалиден. Все возможные
 * ошибки форматирования описаны в секции Detail спецификации Formatter */


import java.util.Formattable;
import java.util.FormattableFlags;
import java.util.Formatter;
import java.util.IllegalFormatException;

public class Formattable_Main implements Formattable {
    StringBuilder s = new StringBuilder("test string");

    @Override
    public void formatTo(Formatter formatter, int flags, int width, int precision)
            throws IllegalFormatException {

        if (flags == FormattableFlags.UPPERCASE) {
            formatter.format(s.toString().toUpperCase());
        } else {
            formatter.format(s.toString());
        }
    }


    public static void main(String[] args) {
        Formattable_Main fmArg = new Formattable_Main();
        Formatter formatter = new Formatter();

        String stringFormat = "%S\n"; //
        formatter.format(stringFormat, fmArg);
        System.out.println(formatter.toString()); // TEST STRING
    }
}




