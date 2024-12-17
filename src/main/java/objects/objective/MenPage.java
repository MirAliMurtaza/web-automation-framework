package objects.objective;

import general.GenericFunctions;
import general.WebDriverWaits;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.HashSet;
import java.util.List;

public class MenPage {




    public static void goToMenSection() {
        GenericFunctions.click(Elements.menSectionLink);
    }

    public static void addProductsEndingWithMToCart() {
        HashSet<String> processedProducts = new HashSet<>();
        int initialCount = Elements.MenitemsAddedToCartCount; // Keep track of initial count to calculate additions

        try {
            boolean foundNewProduct;
            do {
                // Re-fetch the elements every iteration to avoid stale references
                List<WebElement> products = GenericFunctions.findElements(Elements.productNames);
                foundNewProduct = false;

                for (WebElement product : products) {
                    String productName = product.getText().trim();

                    // Process only new products ending with 'M'
                    if (productName.endsWith("M") && !processedProducts.contains(productName)) {
                        foundNewProduct = true;
                        processedProducts.add(productName); // Mark this product as processed
                        GenericFunctions.scrollToElement(product);
                        product.click();

                        try {
                            WebElement addToCartButton = WebDriverWaits.waitUntilElementVisible(Elements.addToCartButtonOnProductPage);
                            addToCartButton.click();
                            Elements.MenitemsAddedToCartCount++;
                            System.out.println("Added to cart: " + productName);

                            // Navigate back twice to return to the product list
                            GenericFunctions.navigateBack();
                            GenericFunctions.navigateBack();
                        } catch (TimeoutException | StaleElementReferenceException e) {
                            System.out.println("Skipping, out of stock or page issue: " + productName);
                            // Navigate back once if there's an issue
                            GenericFunctions.navigateBack();
                        }

                        // Break to re-fetch fresh elements
                        break;
                    }
                }

                // Ensure elements are refreshed before next iteration
                if (foundNewProduct) {
                    WebDriverWaits.waitUntilElementVisible(Elements.productNames);
                }
            } while (foundNewProduct);

        } catch (Exception e) {
            System.out.println("Error processing items: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Total products added this run: " + (Elements.MenitemsAddedToCartCount - initialCount));
    }


    public static void checkCartItemsEndWithM() {
        // Check if any items were added to the cart before proceeding
        if (Elements.MenitemsAddedToCartCount == 0) {
            System.out.println("No items were added to the cart. Skipping cart checks.");
            return; // Exit the method early
        }

        // Navigate to the cart
        GenericFunctions.click(Elements.cartBtn);

        // Wait for the cart page to load
        WebDriverWaits.waitUntilElementVisible(By.id("cart_checkout1"));

        // Locate all product name elements in the cart
        List<WebElement> cartItems = GenericFunctions.findElements(Elements.cartProductNames);

        int itemsInCart = cartItems.size();
        System.out.println("Total items in cart: " + itemsInCart);

        Assert.assertEquals(itemsInCart, Elements.MenitemsAddedToCartCount, "Number of items in cart does not match expected.");

        for (WebElement item : cartItems) {
            String itemName = item.getText();
            System.out.println("Item in cart: " + itemName);
            Assert.assertTrue(itemName.endsWith("M"), "Item name does not end with 'M': " + itemName);
        }

        System.out.println("All items in the cart end with 'M'.");
    }
}
