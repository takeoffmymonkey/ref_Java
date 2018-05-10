package modifiers_access.some_package;

public class SomeClass {
    public int publicField; // Accessed everywhere
    protected int protectedField; // Accessed within same package and via inheriting this class
    int defaultField; // Accessed only within same package
    private int privateField; // Accessed only within this class
}
