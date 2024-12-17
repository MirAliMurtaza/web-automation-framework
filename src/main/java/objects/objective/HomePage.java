package objects.objective;

import general.GenericFunctions;
import org.openqa.selenium.By;

public class HomePage {

    public static void clickElement(){
        GenericFunctions.click(Elements.loginbtn);
    }

    public static void clickHomepagebtn(){
        GenericFunctions.click(Elements.homepagebtn);
    }

    public static void scrollToDove(){
        GenericFunctions.scrollToElement(Elements.doveProducts);
    }

    public static void clickOnDove(){
        GenericFunctions.click(Elements.doveProducts);
    }

    public static void clickSortByDovePage(){
        GenericFunctions.click(Elements.sortbyDovePage);
    }

    public static void selectSortByDovePage(){
        GenericFunctions.selectElementFromDropDownByText(Elements.sortbyDovePage, "Date New > Old");
    }

    public static void clickListOfSortedItems(){
        GenericFunctions.clickSpecificElementFromList(Elements.listOfSortedItems, 0);
    }

    public static void clickAddToCartButton(){
        GenericFunctions.click(Elements.addToCartButtonPrdct);
    }

    public static void checkQuantityElement(){
        GenericFunctions.assertionToDisplayed(Elements.itemQuantity);
    }

    public static void checkTotalAmountElement(){
        GenericFunctions.assertion(Elements.itemTotalAmount, "$6.00");
    }

}
