package testcases;

import  general.*;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class LoginTests extends BaseTest {

    @Test(description = "225371")
    public static void loginIntoApplication() throws InterruptedException, SQLException {
        //DB base Query Example
        MainCall.loginPage.dataBaseQuery();
        MainCall.log.logInfo("User is logged in successfully");
        MainCall.genericFunctions.assertion(1,1);
        MainCall.genericFunctions.PercyCapture("loginIntoApplication");

    }

    @Test(description = "Sign Up")
    public static void signApplication() throws InterruptedException{
        MainCall.log.logInfo("User sign up");
        MainCall.loginPage.selectNewUser();
        MainCall.loginPage.fillSignUp();
        MainCall.loginPage.selectSignUpSumbit();
    }

//    @Test(description = "Forget/Reset Password")
    public static void forgetPassword() throws InterruptedException{
        MainCall.log.logInfo("User reset passowrd");
        MainCall.loginPage.selectForgetPassword();
        MainCall.loginPage.enterEmail(EnvGlobals.email);
        MainCall.loginPage.selectForgetPasswordSumbit();
    }

    @Test (description = "224711")
    public static void Scrolling() throws InterruptedException {
        MainCall.genericFunctions.driverStart("https://webkul.com/blog/how-to-scroll-in-a-specific-element-using-selenium-webdriver/");
        MainCall.webDriverWaits.sleep(5000);
        MainCall.loginPage.scrollToElement();
        Thread.sleep(5000);
    }
    @Test (description = "224710")
    public static void ListBoxDemo() throws InterruptedException {
        MainCall.genericFunctions.driverStart("https://www.geodatasource.com/software/country-region-dropdown-menu-demo");
        MainCall.webDriverWaits.sleep(2000);
        MainCall.loginPage.selectValues("Aruba","Pakistan");
        MainCall.webDriverWaits.sleep(2000);
        MainCall.genericFunctions.PercyCapture("ListBox");
    }



}