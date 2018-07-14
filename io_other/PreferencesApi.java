package io_other;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/* ПАКЕТ JAVA.UTIL.PREFERENCES
 * - позволяет приложениям сохранять и восстанавливать пользовательские и системные предпочтения
 * (настройки) и конфигурационные данные
 *      - настройки хранятся набором пар ключ-значение, образующих иерархию узлов
 *      - 2 отдельных дерева узлов настроек: пользовательские и системные
 *          - обычно используется только 1 узел, названный как класс
 *      - данные сохраняются на диск в место, зависимое от платформы
 *          - в Windows - реестр
 *      - хранить можно только примитивы и строки
 *      - длина строки не больше 8 кб
 *
 * - иерархия:
 *      - интерфейсы:
 *          - NodeChangeListener: наблюдатель за событиями изменений на узле настройки
 *          - PreferenceChangeListener: наблюдатель за событиями изменений настройки
 *          - PreferencesFactory: фабрика объектов Preferences
 *
 *      - классы:
 *          - AbstractPreferences: скелетная имплементация класса Preferences, заметно облегчающая
 *          имплементацию
 *          - NodeChangeEvent: событие, выдаваемое узлом Preferences, обозначающее, что узел-ребенок
 *          был добавлен или удален
 *          - PreferenceChangeEvent: событие, выдаваемое узлом Preferences, обозначающее, что
 *          настройка была добавлена, удалена или у нее изменилось значение
 *          - Preferences: узел в иерархичной коллекции данных настроек
 *
 * - методы userNodeForPackage/systemNodeForPackage: для пользовательских предпочтений и для общей
 * конфигурационной инфы */

public class PreferencesApi {

    public static void main(String[] args) throws BackingStoreException {
        preferencesDemo();
    }

    private static void preferencesDemo() throws BackingStoreException {
        Preferences prefs = Preferences.userNodeForPackage(PreferencesApi.class);
        System.out.println(prefs.absolutePath());
        prefs.putInt("MyValue", 666);
        for (String s : prefs.keys()) {
            System.out.println(s + ":" + prefs.getInt(s, 0));
        }
    }
}
