# Smart Grocery Price Comparison App

A Java desktop application that helps you find the best grocery prices across multiple retailers. The app uses Selenium-based web scraping to compare prices between NoFrills and Loblaws, presenting results in an intuitive JavaFX user interface.

## Features

- **Multi-Store Price Comparison**: Search and compare grocery prices across NoFrills and Loblaws
- **Interactive GUI**: User-friendly JavaFX interface with product cards showing images, brands, names, and prices
- **Shopping List Management**: Click products to add them to your shopping list, click again to remove
- **Price Sorting**: Automatically displays products sorted by price (lowest to highest)
- **Visual Feedback**: Product cards highlight on hover and show selection state
- **Dual Interface**: Both GUI (App.java) and command-line (Main.java) versions available
- **Real-time Web Scraping**: Fetches current prices directly from store websites

## Prerequisites

### Required Software

1. **Java Development Kit (JDK)**
   - Java 11 or higher
   - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)

2. **ChromeDriver**
   - Download ChromeDriver from [https://chromedriver.chromium.org/](https://chromedriver.chromium.org/)
   - **Important**: Make sure the ChromeDriver version matches your installed Chrome browser version
   - Add ChromeDriver to your system PATH so it can be executed from any directory
   
   **Windows**: 
   - Extract ChromeDriver and place it in `C:\Windows` or add its location to PATH
   
   **macOS/Linux**:
   ```bash
   # Move to /usr/local/bin or another directory in your PATH
   sudo mv chromedriver /usr/local/bin/
   sudo chmod +x /usr/local/bin/chromedriver
   ```

3. **Google Chrome Browser**
   - Must be installed for ChromeDriver to work
   - Download from [https://www.google.com/chrome/](https://www.google.com/chrome/)

### Required Libraries

The application uses the following Java libraries:
- **JavaFX** - For the graphical user interface
- **Selenium WebDriver** - For web scraping functionality

You'll need to add these JAR files to your project classpath:
- `selenium-java-4.x.x.jar` (and its dependencies)
- JavaFX SDK (if not included in your JDK)

## Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/nroyer682/Smart-Grocery-Price-Comparison-App.git
   cd Smart-Grocery-Price-Comparison-App
   ```

2. **Set up dependencies**
   - Download Selenium Java bindings from [Selenium Downloads](https://www.selenium.dev/downloads/)
   - Download JavaFX SDK from [Gluon](https://gluonhq.com/products/javafx/) (if needed)
   - Add JAR files to your project's classpath

3. **Verify ChromeDriver installation**
   ```bash
   chromedriver --version
   ```

## Usage

### Running the GUI Application

The GUI version provides a visual interface with clickable product cards and shopping list management.

```bash
# Compile the application
javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls -cp ".:selenium-java-4.x.x.jar:*" smartgrocery/App.java smartgrocery/Product.java smartgrocery/Scraper.java

# Run the GUI
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls -cp ".:selenium-java-4.x.x.jar:*" smartgrocery.App
```

**GUI Features:**
1. Enter a grocery item in the search field
2. Click "Search" to compare prices across stores
3. View results in a grid layout with product images, brands, and prices
4. Click on any product card to add it to your shopping list (card turns green)
5. Click again to remove from shopping list
6. Click "View Shopping List" to see all selected items in a separate window

### Running the Command-Line Version

The CLI version provides a simple text-based interface.

```bash
# Compile
javac -cp ".:selenium-java-4.x.x.jar:*" smartgrocery/Main.java smartgrocery/Product.java smartgrocery/Scraper.java

# Run
java -cp ".:selenium-java-4.x.x.jar:*" smartgrocery.Main
```

**CLI Usage:**
1. Enter a grocery item when prompted
2. View results sorted by price in the terminal
3. Results show: Brand, Product Name, Price, Minimum Quantity, and Store

## Project Structure

```
Smart-Grocery-Price-Comparison-App/
├── smartgrocery/
│   ├── App.java        # JavaFX GUI application
│   ├── Main.java       # Command-line interface
│   ├── Product.java    # Product data model
│   └── Scraper.java    # Web scraping logic for NoFrills and Loblaws
├── README.md
└── .gitignore
```

## How It Works

1. **User Input**: User enters a search query (e.g., "milk", "bread", "apples")
2. **Web Scraping**: Selenium WebDriver opens headless Chrome browsers and navigates to:
   - NoFrills: `https://www.nofrills.ca/en/search?search-bar={query}`
   - Loblaws: `https://www.loblaws.ca/en/search?search-bar={query}`
3. **Data Extraction**: Scraper extracts product information:
   - Product name and brand
   - Price (including sale prices and minimum quantities)
   - Product images
4. **Data Processing**: Products from both stores are combined and sorted by price
5. **Display**: Results are shown in the GUI or terminal, allowing users to compare prices

## Troubleshooting

### ChromeDriver Issues

**Problem**: "chromedriver not found" or "WebDriverException"
- **Solution**: Ensure ChromeDriver is in your PATH and matches your Chrome version
- Check version compatibility: `chromedriver --version` vs Chrome browser version

**Problem**: "SessionNotCreatedException"
- **Solution**: Update ChromeDriver to match your Chrome browser version

### JavaFX Issues

**Problem**: "Error: JavaFX runtime components are missing"
- **Solution**: Add JavaFX modules to your module path and ensure `--add-modules` flag includes required modules

### Scraping Issues

**Problem**: "No products found" or empty results
- **Solution**: 
  - Check your internet connection
  - Website structure may have changed (CSS selectors in Scraper.java may need updating)
  - Some products might not be available in online stores

**Problem**: "Timeout" errors
- **Solution**: Increase wait time in Scraper.java or check internet speed

### Compilation Issues

**Problem**: "package org.openqa.selenium does not exist"
- **Solution**: Add Selenium JAR files to classpath

**Problem**: "package javafx does not exist"
- **Solution**: Add JavaFX SDK to module path

## Technical Details

### Dependencies
- **Selenium WebDriver 4.x**: Automates Chrome browser for web scraping
- **JavaFX**: Provides modern UI components and layouts
- **Java Standard Library**: Collections, I/O, and utilities

### Scraped Data
Currently supports:
- **NoFrills** (www.nofrills.ca)
- **Loblaws** (www.loblaws.ca)

Both stores use similar Chakra UI components, making scraping logic consistent.

## Future Enhancements

Potential improvements for this application:
- Add more grocery store chains (Metro, Walmart, etc.)
- Export shopping list to CSV or PDF
- Price history tracking and trend analysis
- Store location/availability information
- Filter by dietary restrictions or categories
- Mobile application version
- Database integration for caching results
- User accounts and saved shopping lists

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add your feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

## License

This project is available for educational and personal use. Please check with individual grocery store websites regarding their terms of service for web scraping.

## Disclaimer

This application is for educational purposes. Web scraping should be done responsibly and in accordance with website terms of service. The app uses publicly available price information but should not be used for commercial purposes without proper authorization.

---

**Note**: Store websites may update their structure, which could break the scraping functionality. If you encounter issues, the CSS selectors in `Scraper.java` may need to be updated to match the current website structure.
