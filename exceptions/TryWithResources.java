package exceptions;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;

import types_references_annotations.my_annotations.Ntrstn;

/* БЛОК TRY-WITH-RESOURCES - ЗАМЕНА БЛОКУ TRY-FINALLY, ГДЕ НУЖНО ЗАКРЫТИЕ РЕСУРСОВ
 * - блок finally в try для этого обычно и используется */


/* УДОБСТВО
 * - сокращает синтаксис
 *      - не нужен блок finally
 *      - ссылочные переменные на ресурсы не нужно создавать вне блока, чтобы обратиться к ним в
 *      finally для их закрытия
 *      - все ресурсы указываются в одном месте и единожды
 * - нельзя случайно забыть закрыть ресурс */


/* ДОПОЛНИТЕЛЬНЫЕ БЛОКИ: CATCH, FINALLY
 * - может не иметь дополнительных блоков вообще
 * - может иметь только дополнительные блоки catch
 * - может иметь только дополнительный блок finally
 * - может иметь и дополнительные блоки catch и дополнительный блок finally
 * - блоки срабатывают после закрытия ресурсов
 *      - сначала catch, если было соответствующее исключение
 *      - потом обязательно finally
 * - т.к. метод close() ресурса выбрасывает исключение, оно должно быть где-то обработано */


/* РЕСУРС
 * - объект, который должен быть закрыт после завершения работы с ним
 *      - должен имплементировать:
 *          - или Closeable
 *              - для IOException
 *          - или AutoCloseable
 *              - для более общего Exception
 *                  - потом можно переопределить на более точный
 *      - считается неявно завершенным
 *          - нельзя присвоить другое значение
 *          - область действия ограничивается блоком try
 * - объявление + инициализация ресурса идет после try в скобках
 *      - если несколько, то через ;
 *      - здесь нельзя только обяъявить ссылку
 *      - здесь нельзя только инициализировать объект
 *      - здесь нельзя ссылаться на созданный объект */


/* ПОРЯДОК СОЗДАНИЯ И ЗАКРЫТИЯ РЕСУРСОВ
 * - создаются в порядке, указанном в скобках
 * - закрытие происходит, когда:
 *      - происходит любое исключение
 *          - напр. даже в конструкторе ресурса
 *      - блок try завершился
 * - закрытие происходит в обратном порядке, указанном в скобках */


/* ПРОИГНОРИРОВАННЫЕ ИСКЛЮЧЕНИЯ
 * - на пути открытия ресурсов ->работы в try-> закрытия открытых ресурсов могут возникнуть
 * несколько исключений
 *      - дополнительные исключения может возникнуть только при закрытии открытых ресурсов
 *          - т.е. напр.
 *              - открывает ресурс 1 - ок
 *              - открывает ресурс 2 - исключение
 *              - закрывает ресурс 1 - тоже исключение
 * - дополнительные исключения добавляются как Suppressed к первому возникшему
 *      - т.е. возвращается только 1 исключение-объект какого-то типа
 *      - с Java 7 можно получать Suppressed исключения вызовом getSuppressed() */


@Ntrstn("Блок try-with-resources является удобной заменой блока try-finally, где требуется " +
        "освобождение какого-либо ресурса. Он заметно сокращает и упрощает работу с ресурсами - " +
        "используется меньше синтаксиса (не нужен блок finally, все ресурсы закрываются в 1 блоке, " +
        "а ссылочные переменные не нужно создавать снаружи блока, чтобы потом к ним можно было " +
        "обратиться внутри блока finally)), а также не получится забыть освободить ресурс")

@Ntrstn("Блок try-with-resources может быть без дополнительных блоков либо с дополнительными блоками " +
        "catch (если нужно обработать выбрашенное исключение; этих блоков может быть несколько) и " +
        "finally (может быть 1). Блоки catch и finally могут присутствовать как отдельно, так и " +
        "вместе. Они срабатывают после блока try-with-resources: сначала catch, если было выброшено " +
        "соответствующее исключение, потом обязательно finally")

@Ntrstn("Ресурсом является объект класса, который имплементирует либо Closeable либо AutoCloseable. " +
        "В блоке try-with-resources он считается неявно завершенным (т.е. нельязя присвоить другое " +
        "значение) и область действия ограничивается блоком try. Объявление + инициализация " +
        "(обязательно вместе) происходит в скобках после ключевого слова try. Перечисляются ресурсы " +
        "через ;, а сослаться на уже существующий объект ресурса не получится")

@Ntrstn("Ресурсы создаются в порядке, указанном в скобках. Их автоматическое закрытие происходит при " +
        "возникновении исключения (даже в самом констркукторе ресурса) либо когда завершается блок " +
        "try. Порядок закрытия обратный созданию")

@Ntrstn("На пути создания ресурсов, работы в try, закрытия ресурсов могут возникнуть несколько " +
        "исключений (напр. 1 в try, второе при закрытии ресурса). В таком случае все исключения " +
        "будут добавляться как Suppressed к первому возникшему. Получить массив из подавленных " +
        "исключений - getSuppressed()")


public class TryWithResources {
    static void tryCatchFinallyVsTryWithResources() {
        /* TRY-CATCH-FINALLY */
        Resource r = null; // нужно выносить все переменные, чтобы был доступ в finally
        Resource r2 = null;
        Resource r3 = null;
        try {
        } catch (Exception e) {
        } finally { // нужен блок finally
            try {
                r.close(); // нужно вручную закрыть все ресурсы
                r2.close();
                r3.close(); // нужно не забыть никакой ресурс
            } catch (Exception e) {
            }
        }

        /* TRY-WITH-RESOURCES */
        try (Resource rr = null; Resource rr2 = null; Resource rr3 = null) { // все в 1 месте
        } catch (Exception e) {
        } // блок finally не нужен
        // ресурсы не нужно закрывать вручную
    }


    /* РЕСУРС СЧИТАЕТСЯ НЕЯВНО ЗАВЕРШЕННЫМ */
    /* ОБЛАСТЬ ДЕЙСТВИЯ ОГРАНИЧИВАЕТСЯ БЛОКОМ TRY*/
    static void finalResource() {
        try (Resource r = new Resource()) {
//            r = new Resource(); // нельзя переназначить
        } catch (Exception e) {
//            r.close(); // не доступен
        }
    }


    /* ДОПОЛНИТЕЛЬНЫЕ БЛОКИ */
    /* СРАБАТЫВАЮТ ПОСЛЕ ЗАКРЫТИЯ РЕСУРСОВ */
    static void catchFinally() throws Exception {
        /* БЕЗ БЛОКОВ */
        try (Resource r = null) {
        }

        /* ТОЛЬКО С БЛОКАМИ CATCH */
        try (Resource r = null) {
        } catch (IOException e) {
        } catch (Exception e) {
        }

        /* ТОЛЬКО С БЛОКОМ FINALLY */
        try (Resource r = null) {
        } finally {
        }

        /* И С БЛОКОМ CATCH И С БЛОКОМ FINALLY */
        try (Resource r = new Resource()) {
        } catch (Exception e) {
        } finally {
        }
    }


    /* ПРОИГНОРИРОВАННЫЕ ИСКЛЮЧЕНИЯ */
    static void suppressedException() {
        try (Resource r = new Resource(); // создание - ок
             Resource r2 = new Resource(); // создание - ок
             BadResource br = new BadResource(); // создание - ок | закрытие - подавленное исключение
             BadResource br2 = new BadResource(); // создание - ок | закрытие - главное исключение
        ) {
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getSuppressed())); // [java.io.IOException]
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            catchFinally();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/* ИМЛЕМЕНТАЦИЯ AutoCloseable */
class Resource implements AutoCloseable {

    public Resource() {
        System.out.println("Resource constructor");
    }

    @Override
    public void close() throws Exception {
        System.out.println("Resource close()");
    }
}

/* ИМЛЕМЕНТАЦИЯ Closeable */
class BadResource implements Closeable {

    public BadResource() {
        System.out.println("BadResource constructor");
    }

    @Override
    public void close() throws IOException {
        System.out.println("BadResource close()");
        throw new IOException();
    }
}