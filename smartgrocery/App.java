package smartgrocery;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.TilePane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.animation.PauseTransition;
import javafx.util.Duration;


import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class App extends Application {

    @Override
    public void start(Stage stage) {
        // UI components
        Label title = new Label("Search Smart Grocery:");
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");


        TilePane tilePane = new TilePane();
        tilePane.setHgap(10); // horizontal gap
        tilePane.setVgap(10); // vertical gap
        tilePane.setPrefColumns(5); // columns per row

        ScrollPane scroll = new ScrollPane(tilePane);
        scroll.setFitToWidth(true);
        scroll.setPannable(true);

        // Create shopping list
        List<Product> shoppingList = new ArrayList<>();

        // Search Button
        searchButton.setOnAction(e -> {
            String query = searchField.getText().trim();
            if (!query.isEmpty()) {
                List<Product> allResults = new ArrayList<>();

                allResults.addAll(Scraper.searchNoFrills(query));
                allResults.addAll(Scraper.searchLoblaws(query));
                // Debug
                System.out.println("Searching for: " + query);
                System.out.println("Found " + allResults.size() + " items.");

                // Sort by price
                Collections.sort(allResults, Comparator.comparingDouble(Product::getPrice));

                // Clear previous search results
                tilePane.getChildren().clear();

                if (allResults.isEmpty()) {
                    tilePane.getChildren().add(new Text("No results found for: " + query));
                } else {
                    for (Product product : allResults) {
                        VBox card = new VBox(5);
                        String defaultStyle = "-fx-border-color: black; -fx-padding: 10; -fx-background-color: #f9f9f9;";
                        String hoverStyle = "-fx-border-color: black; -fx-padding: 10; -fx-background-color: #d0ebff;";
                        String selectedStyle = "-fx-border-color: green; -fx-padding: 10; -fx-background-color: #e0ffe0;";

                        card.setStyle(defaultStyle);
                        // Track if this card was selected
                        final boolean[] selected = {false};

                        card.setOnMouseEntered(event -> {
                            if (!selected[0]) {
                                card.setStyle(hoverStyle);
                            }
                        });

                        card.setOnMouseExited(event -> {
                            if (!selected[0]) {
                                card.setStyle(defaultStyle);
                            }
                        });

                        // Make card clickable
                        Label listLabel = new Label();
                        card.setOnMouseClicked(event -> {
                            if (!selected[0]) {
                                shoppingList.add(product);
                                System.out.println(product.getName() + " added to shopping list!");
                                selected[0] = true; // mark as selected
                                card.setStyle(selectedStyle);
                                listLabel.setText(product.getName() + " added to shopping list!");
                                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                                pause.setOnFinished(e3 -> listLabel.setText(null));
                                pause.play();
                            } else {
                                shoppingList.remove(product);
                                System.out.println(product.getName() + " removed from shopping list");
                                selected[0] = false;
                                card.setStyle(defaultStyle);
                                listLabel.setText(product.getName() + " removed from shopping list");
                                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                                pause.setOnFinished(e3 -> listLabel.setText(null));
                                pause.play();
                            }

                        });



                        // Image
                        ImageView imageView = new ImageView(new Image(product.getImageUrl(), 100, 100, true, true));
                        // Brand
                        Text brandText = new Text(product.getBrand());
                        // Name
                        Text nameText = new Text(product.getName());
                        // Price
                        Text priceText = new Text(String.format("$%.2f", product.getPrice()));

                        card.getChildren().addAll(imageView, brandText, nameText, priceText);

                        tilePane.getChildren().add(card);
                    }
                }
            } else {
                tilePane.getChildren().clear();
                tilePane.getChildren().add(new Text("Please enter a search term"));
            }
        });

        Button listButton = new Button("View Shopping List");
        // Shopping list button
        listButton.setOnAction(e2 -> {
            Stage listStage = new Stage();
            listStage.setTitle("Your Shopping List");

            VBox listLayout = new VBox(10);
            listLayout.setStyle("-fx-padding: 15;");

            if (shoppingList.isEmpty()) {
                listLayout.getChildren().add(new Label("Your shopping list is empty."));
            } else {
                for (Product p : shoppingList) {
                    HBox row = new HBox(10); // One row per product
                    row.setStyle("-fx-padding: 10; -fx-border-color: gray; -fx-background-color: #f9f9f9; -fx-alignment: center-left;");

                    // Left: image
                    ImageView imageView = new ImageView(new Image(p.getImageUrl(), 50, 50, true, true));

                    // Right: Product Info
                    VBox textBox = new VBox(5);
                    Text nameText = new Text(p.getName());
                    Text brandText = new Text(p.getBrand());
                    Text priceText = new Text(String.format("$%.2f", p.getPrice()));
                    Text storeText = new Text(p.getStore());

                    textBox.getChildren().addAll(nameText, brandText, priceText, storeText);
                    row.getChildren().addAll(imageView, textBox);

                    listLayout.getChildren().add(row);
                }
            }

            ScrollPane scrollPane = new ScrollPane(listLayout);
            scrollPane.setFitToWidth(true);

            Scene listScene = new Scene(scrollPane, 600, 500);
            listStage.setScene(listScene);
            listStage.show();

        });

        // Layout
        VBox root = new VBox(10, title, searchField, searchButton, listButton, scroll);
        root.setStyle("-fx-padding: 15;");

        // Get screen size
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        // Scene
        Scene scene = new Scene(root, screenWidth, screenHeight);
        stage.setTitle("Smart Grocery Search");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
