package ru.shaldin.sd.refactoring;

public class Product {
    private final String name;
    private final long price;

    public Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String toString() {
        return name + "\t" + price;
    }
}
