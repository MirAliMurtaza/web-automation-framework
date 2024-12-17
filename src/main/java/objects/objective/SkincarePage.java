package objects.objective;

import general.GenericFunctions;
import general.WebDriverWaits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.util.List;

public class SkincarePage {

    public static void navigateToUrl() {
        GenericFunctions.navigateToMainPage();
    }

    public static void clickOnSkinCare() {
        GenericFunctions.click(Elements.skinCareLink);
    }

    public static void logSaleItemsInfo() {
        List<WebElement> saleElements = GenericFunctions.findElements(Elements.saleItems);
        List<WebElement> outOfStockElements = GenericFunctions.findElements(Elements.saleOutOfStockItems);
        List<WebElement> saleInStockElements = GenericFunctions.findElements(Elements.saleInStockItems);

        System.out.println("Number of items on sale: " + saleElements.size());
        System.out.println("Number of sale items out of stock: " + outOfStockElements.size());
        System.out.println("Number of sale items in stock: " + saleInStockElements.size());

        // Update the count of sale in-stock items
        Elements.saleInStockItemsCount = saleInStockElements.size();
    }



    public static void addSaleItemsToCart() {
        List<WebElement> saleInStockElements = GenericFunctions.findElements(Elements.saleInStockItems);

        int itemsAddedToCart = 0; // Counter for items successfully added

        for (int i = 0; i < saleInStockElements.size(); i++) {
            try {
                WebElement item = saleInStockElements.get(i);

                // Scroll to the item
                GenericFunctions.scrollToElement(item);

                // Find the 'Add to Cart' button within the item
                List<WebElement> addToCartButtons = item.findElements(Elements.addToCartbtns);

                if (addToCartButtons.isEmpty()) {
                    continue; // Skip to the next item
                }

                WebElement addToCartButton = addToCartButtons.get(0);

                // Check if the 'Add to Cart' button is interactable
                if (addToCartButton.isDisplayed() && addToCartButton.isEnabled()) {
                    // Click the 'Add to Cart' button
                    addToCartButton.click();


                    // Wait for any actions to complete
                    WebDriverWaits.sleep(2);

                    itemsAddedToCart++; // Increment the counter
                } else {
                    System.out.println("'Add to Cart' button not interactable for item " + (i + 1));
                }
            } catch (Exception e) {
                System.out.println("Exception occurred while adding item " + (i + 1) + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Update the count of items added to the cart
        Elements.SkinitemsAddedToCartCount = itemsAddedToCart;
    }



    public static void checkCart() {
        // Click on 'Go to Cart' link
        GenericFunctions.click(Elements.goToCartLink);

        // Get total price from cart
        WebElement totalPriceElement = GenericFunctions.findElement(Elements.cartPrice);
        String totalPriceText = totalPriceElement.getText();
        System.out.println("Total Price in Cart: " + totalPriceText);

        // Remove currency symbol and parse to double
        double totalPrice = Double.parseDouble(totalPriceText.replace("$", "").replace(",", "").trim());
        System.out.println("Total Price (parsed): " + totalPrice);

        // Get the quantity inputs and calculate total quantity
        List<WebElement> quantities = GenericFunctions.findElements(Elements.quantityInputs);
        int totalQuantity = 0;
        for (WebElement qtyInput : quantities) {
            String qtyValue = qtyInput.getAttribute("value");
            totalQuantity += Integer.parseInt(qtyValue);
        }

        System.out.println("Total quantity of items in cart: " + totalQuantity);
        System.out.println("Expected total quantity: " + Elements.SkinitemsAddedToCartCount);

        // Assert that the total quantity matches the number of items added
        Assert.assertEquals(totalQuantity, Elements.SkinitemsAddedToCartCount, "Total quantity does not match expected!");
    }
}
