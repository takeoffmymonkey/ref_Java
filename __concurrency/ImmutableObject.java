package __concurrency;

/* Часто используются неизменяемые объекты, т.к. они не могут быть повреждены при thread interference
 * или наблюдаться с измененным состоянием
 * - влияние от создания объекта часто переоценивается и может быть компенсирован удобствами:
 *      - уменьшенный оверхед благодаря сбору мусора
 *      - сокращение кода, который защищает изменяемые объекты от повреждения
 *
 * - правила для неизменяемых объектов:
 *      - не предоставлять сеттеры
 *      - все поля final private
 *      - не позволять подклассам переопределять методы. Например, сам класс final. Или private
 *      конструктор и создавать экземпляры фабричным методом.
 *      - если поля экземпляров ссылаются на изменяемые объекты, не позволять этим объектам изменяться:
 *          - не предоставлять методов, которые меняют изменяемые объекты
 *          - не делится ссылками на изменяемые объекты: не хранить ссылки на внешние, изменяемые
 *          объекты, передаваемые в конструктор. Если нужно, создавай копии и храни ссылки на копии.
 *          Аналогично, создавай копии внутренних меняемых объектов когда нужно, чтобы избежать
 *          возвращения оригиналов в методах
 * */


public class ImmutableObject {
}

final class ImmutableRGB {

    // Values must be between 0 and 255.
    final private int red;
    final private int green;
    final private int blue;
    final private String name;

    private void check(int red, int green, int blue) {
        if (red < 0 || red > 255
                || green < 0 || green > 255
                || blue < 0 || blue > 255) {
            throw new IllegalArgumentException();
        }
    }

    public ImmutableRGB(int red, int green, int blue, String name) {
        check(red, green, blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.name = name;
    }


    public int getRGB() {
        return ((red << 16) | (green << 8) | blue);
    }

    public String getName() {
        return name;
    }

    public ImmutableRGB invert() {
        return new ImmutableRGB(255 - red,
                255 - green,
                255 - blue,
                "Inverse of " + name);
    }
}
