package objects.objective;

import general.GenericFunctions;
import general.WebDriverWaits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

import static general.MainCall.webDriverWaits;

public class ApparelsPage {

    public static void clickOnApparel() {
        GenericFunctions.click(Elements.appsNaccs);
    }


    public static void clickOnShirts() {
        GenericFunctions.click(Elements.tShirts);
    }

    public static void clickOnShoes() {
        GenericFunctions.click(Elements.shoes);
    }


    public static void sortByRatingLowest() {
        GenericFunctions.selectElementFromDropDownByText(Elements.sortDropdown, "Rating Lowest");
    }

    public static void sortByHighestValue() {
        GenericFunctions.selectElementFromDropDownByText(Elements.sortDropdown, "Price High > Low");
    }

    public static void sortByMedium() {
        GenericFunctions.selectElementFromDropDownByText(Elements.mediumShirt, "Medium");
    }

    public static void addHighestValueProductToCart() {
        List<WebElement> products = GenericFunctions.findElements(Elements.productList);
        if (!products.isEmpty()) {
            // Access the 'View' link directly using the provided HTML structure
            WebElement viewLink = products.get(0).findElement(Elements.ChildElementShoe);
            viewLink.click();  // This will navigate to the product detail page

            // Adjust quantity
            WebElement quantityInput = WebDriverWaits.waitUntilElementVisible(Elements.shoeQuantity);
            quantityInput.clear();
            quantityInput.sendKeys("2");

            // Click the add to cart button
            WebElement addToCartButton = WebDriverWaits.waitUntilElementVisible(Elements.innerBtn); // Update this selector
            addToCartButton.click();

            System.out.println("Highest value product added to the cart with quantity 2.");
        } else {
            System.out.println("No products found.");
        }
    }

    public static void fetchShirts() {
        int count = 0;
        int processed = 0;

        sortByRatingLowest();

        WebElement parentElement = GenericFunctions.findElement(Elements.productList);
        List<WebElement> childElements = GenericFunctions.findChildElements(parentElement, Elements.productItems);

        if (childElements.size() < 3) {
            System.out.println("Not enough products to process.");
            return;
        }
        
        while (processed < 3) {
            // Re-fetch the product list after each navigation to ensure fresh elements
            parentElement = GenericFunctions.findElement(Elements.productList);
            childElements = GenericFunctions.findChildElements(parentElement, Elements.productItems);

            // Ensure the list still has enough products
            if (childElements.size() < processed + 1) {
                System.out.println("Product list has changed, not enough products to process.");
                break;
            }

            // Get the product at the current index
            WebElement product = childElements.get(processed);

            // Check if the product is out of stock
            if (!product.findElements(Elements.outOfStockPrdct).isEmpty()) {
                System.out.println("Product " + (processed + 1) + " is out of stock.");
                processed++;  // Move to the next product
                continue;
            }

            // Attempt to add to cart if the product has an "Add to Cart" button
            List<WebElement> addToCartButtons = product.findElements(Elements.addToCartBtn);
            if (!addToCartButtons.isEmpty()) {
                WebElement addToCartButton = addToCartButtons.get(0);
                addToCartButton.click();
                System.out.println("Product " + (processed + 1) + " found and clicked 'Add to Cart'");

                // Select medium size
                sortByMedium();
                System.out.println("Medium size selected");

                // Click inside cart
                WebElement innerCartBtn = GenericFunctions.findElement(Elements.innerBtn);
                innerCartBtn.click();
                System.out.println("Clicked 'Add to Cart' inside the product page");

                count++;
                System.out.println("Product added to cart. Count: " + count);

                // Navigate back twice to return to the product list
                GenericFunctions.navigateBack();
                System.out.println("Navigated back once");
                GenericFunctions.navigateBack();
                System.out.println("Navigated back twice");

                // Re-sort by rating lowest for the next iteration
                if (processed < 2) {  // If there are more products to process
                    sortByRatingLowest();
                    System.out.println("Re-sorted by 'Rating Lowest'");
                }
            } else {
                System.out.println("Product " + (processed + 1) + " does not have 'Add to Cart' button.");
            }

            // Increment 'processed' after each product is checked
            processed++;
        }

        System.out.println("Processed " + processed + " products. Task completed.");
    }

    public static void verifyItemsInCart() {
        GenericFunctions.assertion(Elements.productDescriptionLocator1, "Designer Men Casual Formal Double Cuffs Grandad Band Collar Shirt Elegant Tie");
        GenericFunctions.assertion(Elements.productDescriptionLocator2, "Fiorella Purple Peep Toes");

        System.out.println("Assertion passed: All specified items are correctly listed in the shopping cart.");
    }
}
