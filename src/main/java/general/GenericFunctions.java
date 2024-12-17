package general;

import objects.CommonLocators;
import io.percy.selenium.Percy;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.substringAfter;

public class GenericFunctions {

    public static String generateRandomNum(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public static List<Integer> generateRandomNumSystem(int length, int sysCount) {
        List<Integer> rawRandomNumbers = new ArrayList<>();
        for (int i = 0; i < sysCount; i++) {
            rawRandomNumbers.add(Integer.valueOf(RandomStringUtils.randomNumeric(length)));
        }
        return rawRandomNumbers;
    }

    public static String generateAlphaNumeric(String prefix, int length) {
        String rawRandomNumber = RandomStringUtils.randomNumeric(length);
        return prefix + rawRandomNumber;
    }

    public static String RandomPhoneNumber(String rawRandomNumber) {
        return "+92300" + rawRandomNumber;
    }

    public static String generateRandomString(int length) {
        String name = RandomStringUtils.randomAlphabetic(length);
        String firstLetter = name.substring(0, 1).toUpperCase();
        String otherLetters = name.substring(1).toLowerCase();
        return firstLetter + otherLetters;
    }

    public static String generateEmail(String finalName) {
        return finalName + "@vd.com";
    }

    public static void clearFieldWithJS(By locator, String input) {
        JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
        String elementId = substringAfter(locator.toString(), ": ");

        js.executeScript("document.getElementById('" + elementId + "').value = '" + input + "'");
    }

    public static void mouseHover(By by) {
        Actions action = new Actions(WebDriverFactory.getDriver());
        action.moveToElement(WebDriverFactory.getDriver().findElement(by)).perform();
    }

    public static int stringToInt(String number) {
        return Integer.parseInt(number);
    }

    public void clickElementByText(String text) throws InterruptedException {
        WebDriverFactory.getDriver().findElement(By.xpath("//*[contains(text(), '" + text + "')]")).click();
        Thread.sleep(1000);
    }

    public int getColumnsName(String columnName) {
        int i = 0;
        List<WebElement> elements = WebDriverFactory.getDriver()
                .findElement(CommonLocators.byThead)
                .findElements(CommonLocators.byTr)
                .get(1)
                .findElements(CommonLocators.byTh);

        for (WebElement element : elements) {
            if (element.getText().trim().equals(columnName)) {
                break;
            }
            i++;
        }
        return i;
    }

    public List<WebElement> getRowsOfTable() {
        return WebDriverFactory.getDriver()
                .findElement(CommonLocators.byTable)
                .findElements(CommonLocators.byTr);
    }

    public void clickOnColumnOfTable(String itemName) {
        List<WebElement> rows = getRowsOfTable();
        for (int i = 0; i <= rows.size(); i++) {
            List<WebElement> columns = WebDriverFactory.getDriver().findElements(CommonLocators.byTd);
            for (WebElement column : columns)
                if (column.getText().trim().equals(itemName)) {
                    Actions actions = new Actions(WebDriverFactory.getDriver());
                    actions.click(column).build().perform();
                    break;
                }
        }
        WebDriverWaits.waitUntilLoaderDisapears();
    }

    public void scrollUp() {
        WebElement element = WebDriverFactory.getDriver().findElement(By.className("locator"));
        ((JavascriptExecutor) WebDriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(true)", element);
    }

    public static boolean selectOptionIfExists(By dropdown, String optionText) {
        WebElement dropdownElement = WebDriverFactory.getDriver().findElement(dropdown);
        Select select = new Select(dropdownElement);
        List<WebElement> options = select.getOptions();
        for (WebElement option : options) {
            if (option.getText().equals(optionText) && option.isEnabled()) {
                select.selectByVisibleText(optionText);
                return true;
            }
        }
        return false;
    }

    public static List<WebElement> findElements(By locator) {
        return WebDriverFactory.getDriver().findElements(locator);
    }

    public static WebElement findChildElement(WebElement element, By locator) {
        return element.findElement(locator);
    }

    public static List<WebElement> findChildElements(WebElement element, By locator) {
        return element.findElements(locator);
    }

    public static WebElement findElement(By locator) {
        return WebDriverFactory.getDriver().findElement(locator);
    }

    public static void scrollToElement(WebElement element) {
        try {
            ((JavascriptExecutor) WebDriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            throw new RuntimeException("Unable to scroll to element: " + e.getMessage());
        }
    }

    public static boolean ElementPresent(By locator) {
        try {
            WebDriverFactory.getDriver().findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Method to check if an element is present
    public static boolean isElementPresent(By locator) {
        return !WebDriverFactory.getDriver().findElements(locator).isEmpty();
    }

    // Method to navigate back
    public static void navigateBack() {
        WebDriverFactory.getDriver().navigate().back();
    }

    public static void navigateToMainPage() {
        WebDriverFactory.getDriver().navigate().to("https://automationteststore.com/");
    }

    public static void PercyCapture(String name) throws InterruptedException {
        Percy percy = new Percy(WebDriverFactory.getDriver());
        Thread.sleep(2000);
        percy.snapshot(name);
        Thread.sleep(2000);
    }

    public static void assertion(int actual, int expected) {
        Assert.assertEquals(actual, expected);
    }

    public static void assertion(By locator, String expected) {
        try {
            WebDriverWaits.visibilityOf(locator);
            String text = WebDriverFactory.getDriver().findElement(locator).getText();
            Assert.assertEquals(text, expected);
        } catch (ElementNotVisibleException e) {
            throw new AssertionError(String.format("The element provided %s is not on screen", locator));
        } catch (StaleElementReferenceException e) {
            throw new AssertionError(String.format("The element provided %s is stale", locator));
        } catch (InvalidElementStateException e) {
            throw new AssertionError(String.format("The element provided %s is not in desired state", locator));
        } catch (Exception e) {
            throw new AssertionError(String.format("The element provided %s is invalid", locator));
        }
    }

    public static void driverStart(String url) {
        WebDriverFactory.getDriver().get(url);
        WebDriverFactory.getDriver().manage().window().maximize();
    }

    public static void scrollToElement(By locator) {
        try {
            WebDriverWaits.visibilityOf(locator);
            ((JavascriptExecutor) WebDriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView();", WebDriverFactory.getDriver().findElement(locator));
        } catch (ElementNotVisibleException e) {
            throw new AssertionError(String.format("The element provided %s is not on screen", locator));
        } catch (StaleElementReferenceException e) {
            throw new AssertionError(String.format("The element provided %s is stale", locator));
        } catch (InvalidElementStateException e) {
            throw new AssertionError(String.format("The element provided %s is not in desired state", locator));
        } catch (Exception e) {
            throw new AssertionError(String.format("The element provided %s is invalid", locator));
        }
    }

    public static void selectElementFromDropDownByText(By locator, String value) {
        WebElement select = WebDriverFactory.getDriver().findElement(locator);
        Select option = new Select(select);
        option.selectByVisibleText(value);
    }

    public static void clickSpecificElementFromList(By locator, int index) {
        List<WebElement> elements = WebDriverFactory.getDriver().findElements(locator);
        elements.get(index).click();
    }

    public static void slideBar(By locator1, By locator2, int xOffset, int yOffset) {
        WebElement slider = WebDriverFactory.getDriver().findElement(locator1).findElement(locator2);
        Actions act = new Actions(WebDriverFactory.getDriver());
        act.dragAndDropBy(slider, xOffset, yOffset).build().perform();
    }

    public static void inputTextAlert(String input) {
        WebDriverFactory.getDriver().switchTo().alert().sendKeys(input);
    }

    public static void acceptAlert() {
        WebDriverFactory.getDriver().switchTo().alert().accept();
    }

    public static void radioButtonClick(By locator, int index) {
        List<WebElement> radioButtons = WebDriverFactory.getDriver().findElements(locator);
        radioButtons.get(index).click();
    }

    public static void inputText(By locator, String text) {
        try {
            WebElement element = WebDriverWaits.waitUntilElementVisible(locator);
            element.clear(); // Clear the text field before entering new text
            element.sendKeys(text);
        } catch (ElementNotVisibleException e) {
            throw new AssertionError(String.format("The element provided %s is not visible on the screen", locator));
        } catch (StaleElementReferenceException e) {
            throw new AssertionError(String.format("The element provided %s is stale", locator));
        } catch (InvalidElementStateException e) {
            throw new AssertionError(String.format("The element provided %s is not in a state to accept input", locator));
        } catch (Exception e) {
            throw new AssertionError(String.format("Error interacting with the element provided %s", locator));
        }
    }

    public static void assertionToDisplayed(By locator) {
        try {
            WebDriverWaits.visibilityOf(locator);
            Assert.assertTrue(WebDriverFactory.getDriver().findElement(locator).isDisplayed());
        } catch (ElementNotVisibleException e) {
            throw new AssertionError(String.format("The element provided %s is not on screen", locator));
        } catch (StaleElementReferenceException e) {
            throw new AssertionError(String.format("The element provided %s is stale", locator));
        } catch (InvalidElementStateException e) {
            throw new AssertionError(String.format("The element provided %s is not in desired state", locator));
        } catch (Exception e) {
            throw new AssertionError(String.format("The element provided %s is invalid", locator));
        }
    }

    public static void click(By locator) {
        try {
            WebDriverWaits.visibilityOf(locator);
            WebElement button = WebDriverWaits.waitUntilElementIsClickable(locator);
            button.click();
        } catch (ElementNotVisibleException e) {
            throw new AssertionError(String.format("The element provided %s is not on screen", locator));
        } catch (StaleElementReferenceException e) {
            throw new AssertionError(String.format("The element provided %s is stale", locator));
        } catch (InvalidElementStateException e) {
            throw new AssertionError(String.format("The element provided %s is not in desired state", locator));
        } catch (Exception e) {
            throw new AssertionError(String.format("The element provided %s is invalid", locator));
        }
    }
}
