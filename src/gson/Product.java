package gson;

import com.google.gson.Gson;

/*
* Gson: How to exclude specific fields from Serialization without annotations
*
* https://stackoverflow.com/questions/4802887/gson-how-to-exclude-specific-fields-from-serialization-without-annotations
* */
public class Product {
    String name;
    String category;
    double price;

    ///User transient keyword to exclude a field from serialization and deserialization
    transient int  stock;

    public Product(String name, String category, double price, int stock) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    @Override public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }

    public static void main(String[] args) {
        Product product = new Product("vinamilk", "milk", 11000.0, 15);

        Gson gson = new Gson();
        // Serialization
        String json = gson.toJson(product);
        System.out.println(json);

        // Deserialization
        Product product1 = gson.fromJson(json, Product.class);
        System.out.println(product1);
    }
}
