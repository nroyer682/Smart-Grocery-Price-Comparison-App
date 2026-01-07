package smartgrocery;

import org.openqa.selenium.By; // find html elements
import org.openqa.selenium.WebDriver; // main controller for the browser
import org.openqa.selenium.WebElement; // represents a single html item
import org.openqa.selenium.chrome.ChromeDriver; // allows controlling Chrome browser
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import java.util.NoSuchElementException;

import java.util.ArrayList;
import java.util.List;

public class Scraper {
    // Scrape NoFrills
    public static List<Product> searchNoFrills(String query) {
        List<Product> products = new ArrayList<>();

        // Start a new Chrome browser
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(options);

        try {
            // Build the search URL
            String url = "https://www.nofrills.ca/en/search?search-bar=" + query;
            driver.get(url);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // waits up to 10 seconds
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.chakra-linkbox")));

            // Small pause (wait for page to load)
            Thread.sleep(3000);

            // Scroll to bottom multiple times to load more products
            JavascriptExecutor js = (JavascriptExecutor) driver;
            for (int i = 0; i < 1; i++) {
                js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(2000); // wait after each scroll
            }

            List<WebElement> productCards = driver.findElements(By.cssSelector("div.chakra-linkbox"));
            System.out.println("Number of product cards found: " + productCards.size());

            for (WebElement productCard : productCards) {
                try {
                    // Find product name
                    String name = productCard.findElement(By.cssSelector("h3.chakra-heading[data-testid='product-title']")).getText();

                    System.out.println(name);
                    // Find product brand
                    String brand = "";
                    try {
                        brand = productCard.findElement(By.cssSelector("p.chakra-text[data-testid='product-brand']")).getText();
                    } catch (NoSuchElementException e) {
                        // Some items might have no brand
                    }

                    // Find product image
                    String imageUrl = "";
                    try {
                        imageUrl = productCard.findElement(By.cssSelector("img.chakra-image")).getAttribute("src");
                    } catch (NoSuchElementException e) {
                        // Some items might have no image
                    }

                    double price = 0.0;
                    int minQuantity = 1;
                    try {
                        WebElement salePrice = productCard.findElement(By.cssSelector("span.chakra-text[data-testid='sale-price']"));
                        String text = salePrice.getText();
                        String[] lines = text.split("\n");
                        for (String line : lines) {
                            if (line.trim().startsWith("$")) {
                                String priceStr = line.trim();

                                if (priceStr.contains("MIN")) {
                                    String[] parts = priceStr.split("MIN");
                                    String dollarPart = parts[0].trim().replace("$", "");
                                    String minPart = parts[1].trim();

                                    price = Double.parseDouble(dollarPart);
                                    minQuantity = Integer.parseInt(minPart);
                                } else {
                                    price = Double.parseDouble(priceStr.replace("$", ""));
                                }
                                break;
                            }
                        }

                    } catch (NoSuchElementException e) {
                        WebElement regularPrice = productCard.findElement(By.cssSelector("span.chakra-text[data-testid='regular-price']"));
                        String priceStr = regularPrice.getText().trim();
                        price = Double.parseDouble(priceStr.replace("$", ""));
                    }

                    // Create and add product
                    Product product = new Product(name, price, "NoFrills", minQuantity, imageUrl, brand);
                    products.add(product);

                } catch (Exception e) {
                    // Skip any broken product cards
                }
            }

        } catch (Exception e) {
            System.out.println("Error using Selenium for NoFrills:");
            e.printStackTrace();
        } finally {
            driver.quit(); // Always close browser
        }
        return products;
    }

    // Scrape Loblaws

    public static List<Product> searchLoblaws(String query) {
        List<Product> products = new ArrayList<>();

        // Start a new Chrome browser
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(options);

        try {
            // Build the search URL
            String url = "https://www.loblaws.ca/en/search?search-bar=" + query;
            driver.get(url);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.chakra-linkbox")));

            // Small pause (wait for page to load)
            Thread.sleep(3000);

            // Scroll to bottom multiple times to load more products
            JavascriptExecutor js = (JavascriptExecutor) driver;
            for (int i = 0; i < 1; i++) {
                js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(2000); // wait after each scroll
            }

            List<WebElement> productCards = driver.findElements(By.cssSelector("div.chakra-linkbox"));

            System.out.println("Number of product cards found: " + productCards.size());

            for (WebElement productCard : productCards) {

                try {
                    // Find product name
                    String name = productCard.findElement(By.cssSelector("h3.chakra-heading[data-testid='product-title']")).getText();

                    // Find product brand
                    String brand = "";
                    try {
                        brand = productCard.findElement(By.cssSelector("p.chakra-text[data-testid='product-brand']")).getText();
                    } catch (NoSuchElementException e) {
                        // Some items might have no brand
                    }

                    // Find product image
                    String imageUrl = "";
                    try {
                        imageUrl = productCard.findElement(By.cssSelector("img.chakra-image")).getAttribute("src");
                    } catch (NoSuchElementException e) {
                        // Some items might have no image
                    }

                    double price = 0.0;
                    int minQuantity = 1;
                    try {
                        WebElement salePrice = productCard.findElement(By.cssSelector("span.chakra-text[data-testid='sale-price']"));
                        String text = salePrice.getText();
                        String[] lines = text.split("\n");
                        for (String line : lines) {
                            if (line.trim().startsWith("$")) {
                                String priceStr = line.trim();

                                if (priceStr.contains("MIN")) {
                                    String[] parts = priceStr.split("MIN");
                                    String dollarPart = parts[0].trim().replace("$", "");
                                    String minPart = parts[1].trim();

                                    price = Double.parseDouble(dollarPart);
                                    minQuantity = Integer.parseInt(minPart);
                                } else {
                                    price = Double.parseDouble(priceStr.replace("$", ""));
                                }
                                break;
                            }
                        }

                    } catch (NoSuchElementException e) {
                        WebElement regularPrice = productCard.findElement(By.cssSelector("span.chakra-text[data-testid='regular-price']"));
                        String priceStr = regularPrice.getText().trim();
                        price = Double.parseDouble(priceStr.replace("$", ""));
                    }

                    // Create and add product
                    Product product = new Product(name, price, "Loblaws", minQuantity, imageUrl, brand);
                    products.add(product);

                } catch (Exception e) {
                    // Skip any broken product cards
                }
            }

        } catch (Exception e) {
            System.out.println("Error Scraping Loblaws:");
            e.printStackTrace();
        }

        return products;
    }
}
