package testcases.objective;

import config.ConfigProperties;
import general.BaseTest;
import objects.objective.ApparelsPage;
import objects.objective.HomePage;
import objects.objective.LoginPage;
import objects.objective.SkincarePage;
import objects.objective.MenPage;
import org.testng.annotations.Test;
import general.*;

public class TestCases extends BaseTest {

    @Test
    public static void Scenario1(){
        GenericFunctions.driverStart(ConfigProperties.Url);
        HomePage.clickElement();
        LoginPage.clickUsername();
        String username = ConfigProperties.UserName;
        LoginPage.enterUsername(username);
        LoginPage.clickPassword();
        String password = ConfigProperties.Password;
        LoginPage.enterPassword(password);
        LoginPage.clickLgnButton();
        HomePage.clickHomepagebtn();
        HomePage.scrollToDove();
        HomePage.clickOnDove();
        HomePage.clickSortByDovePage();
        HomePage.selectSortByDovePage();
        HomePage.clickListOfSortedItems();
        HomePage.clickAddToCartButton();
        HomePage.checkTotalAmountElement();
        HomePage.checkQuantityElement();
    }

    @Test
    public static void Scenario2(){
        GenericFunctions.driverStart(ConfigProperties.Url);
//        HomePage.clickElement();
//        LoginPage.clickUsername();
//        String username = ConfigProperties.UserName;
//        LoginPage.enterUsername(username);
//        LoginPage.clickPassword();
//        String password = ConfigProperties.Password;
//        LoginPage.enterPassword(password);
//        LoginPage.clickLgnButton();
        ApparelsPage.clickOnApparel();
        ApparelsPage.clickOnShirts();
        ApparelsPage.sortByRatingLowest();
        ApparelsPage.fetchShirts();
        ApparelsPage.clickOnApparel();
        ApparelsPage.clickOnShoes();
        ApparelsPage.sortByHighestValue();
        ApparelsPage.addHighestValueProductToCart();
        ApparelsPage.verifyItemsInCart();
    }

    @Test
    public static void Scenario3(){
            GenericFunctions.driverStart(ConfigProperties.Url);
            SkincarePage.clickOnSkinCare();
            SkincarePage.logSaleItemsInfo();
            SkincarePage.addSaleItemsToCart();
//            SkincarePage.checkCart();
    }
        @Test
        public static void Scenario4() {
            GenericFunctions.driverStart(ConfigProperties.Url);
            MenPage.goToMenSection();
            MenPage.addProductsEndingWithMToCart();
            MenPage.checkCartItemsEndWithM();
        }
}



