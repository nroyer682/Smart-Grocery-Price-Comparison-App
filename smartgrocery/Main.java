package smartgrocery;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a grocery item to search: ");
        String query = scanner.nextLine(); // reads what the user types

        // Scrape all stores
        List<Product> noFrillsProducts = Scraper.searchNoFrills(query);
        List<Product> loblawsProducts = Scraper.searchLoblaws(query);

        // Get products from both stores
        List<Product> allProducts = new ArrayList<>();
        allProducts.addAll(noFrillsProducts);
        allProducts.addAll(loblawsProducts);

        // Sort by price (lowest to highest)
        Collections.sort(allProducts, Comparator.comparingDouble(Product::getPrice));

        // Display results
        System.out.println("\nLowest prices for: " + query + "\n");
        for (Product product : allProducts) {
            System.out.println(product);
        }
        scanner.close();
    }
}
