package smartgrocery;

public class Product {
    private final String name;
    private final double price;
    private final String store;
    private int minQuantity;
    private String imageUrl;
    private String brand;

    public Product(String name, double price, String store, int minQuantity, String imageUrl, String brand) {
        this.name = name;
        this.price = price;
        this.store = store;
        this.minQuantity = minQuantity;
        this.imageUrl = imageUrl;
        this.brand = brand;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public String getStore() {
        return this.store;
    }

    public int getMinQuantity() {
        return this.minQuantity;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getBrand() {
        return this.brand;
    }

    @Override
    public String toString() {
        return getBrand() + " " + getName() + " - $" + getPrice() + " MIN " + getMinQuantity() + " ( " + getStore() + " ) ";
    }
}
