package io_other;

/* PREFERENCES API c 1.4
 * - набор пар ключ-значение, образующих иерархию узлов
 *      - обычно используется только 1 узел, названный как класс
 * - хранить можно только примитивы и строки
 *      - длина строки не больше 8 кб
 * - позволяются автоматически сохранять и восстанавливать информацию о предпочтениях пользователя и
 * конфигурации программы
 * - методы userNodeForPackage/systemNodeForPackage: для пользовательских предпочтений и для общей
 * конфигурационной инфы
 * - система предпочтений привлекает для хранения данных подходящие системные возможности текущей ОС
 *      - в Windows используется, например, реестр
 *
 * */

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;



public class PreferencesApi {

    public static void main(String[] args) throws BackingStoreException {
        preferencesDemo();
    }

    private static void preferencesDemo() throws BackingStoreException {
        Preferences prefs = Preferences.userNodeForPackage(PreferencesApi.class);
        prefs.putInt("MyValue", 666);
        for (String s : prefs.keys()) {
            System.out.println(s + ":" + prefs.getInt(s, 0));
        }
    }

}
