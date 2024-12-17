package objects.objective;

import general.GenericFunctions;
import general.MainCall;
import org.openqa.selenium.By;

public class LoginPage {

    public static void clickUsername() {
        GenericFunctions.click(Elements.usernameLocator);
    }
    public static void enterUsername(String username) {
        GenericFunctions.inputText(Elements.usernameLocator, username);
    }
    public static void clickPassword() {
        GenericFunctions.click(Elements.passwordLocator);
    }
    public static void enterPassword(String password) {
        GenericFunctions.inputText(Elements.passwordLocator, password);
    }
    public static void clickLgnButton() {
        GenericFunctions.click(Elements.lgnButton);
    }

}
