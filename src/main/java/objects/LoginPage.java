package objects;



import general.CommonAssertions;
import general.MainCall;
import general.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.venturedive.base.config.BaseConfigProperties.projectName;
import static config.ConfigProperties.Url;
import static config.ConfigProperties.appConfig;

public class LoginPage
{
    public LoginPage() {}
    public static String selectQuery = "select * from sonardb.automation_report where id =270926;";
    public static String updateQuery = "update sonardb.automation_report set PROJECT_NAME = 'Vital' where PROJECT_NAME = 'Vital'";
    public static String deleteQuery = "delete from  sonardb.automation_report where id =272947;";
    public static By byLoginWelcome = By.id("logout-link");

    public static By byEmail = By.name("useremail");
    public static By byPassword = By.name("password");
    public static By byLogin = By.cssSelector("span[class*='jss']");
    public static By bySkip = By.cssSelector("button[title='Skip']");
    public static By byHelpPopUpCross = By.className("help-cross-btn");

    public static By byNewUser = By.partialLinkText("New User");
    public static By byForgetPassword = By.partialLinkText("Forgot your password");

    public static By byFirstName = By.name("firstname");
    public static By byLastName = By.name("lastname");
    public static By byCompany = By.name("account_name");
    public static By byTermsAndConditionCheck = By.cssSelector("input[type='checkbox']");
    public static By bySubmit = By.className("undefined");

    public static By byH4 = By.tagName("h4");
    public static By byButton = By.tagName("button");
    public static By bySpan = By.tagName("span");
    public static By byGotItButton = By.className("cc-btn");
    public static By byButtonScroll = By.xpath("//a[text()='Disclaimer']");//updated xpath previous xpath was changed on the website
    public static By bySelector = By.xpath("//select[@class=\"form-control gds-cr\"]");
    public static By byAdd = By.className("pAdds");

    public void enterUserDetails(String userMail, String pwd) {
        CommonAssertions.logActualReult("enter the username");
        MainCall.log.logInfo("User enters email and password");
        MainCall.webDriverFactory.getDriver().findElement(byEmail).sendKeys(userMail);
        CommonAssertions.logExpectedResult("email should be display on text box");
        MainCall.webDriverFactory.getDriver().findElement(byPassword).sendKeys(pwd);
    }

    public void clickGotIt()
    {
        if (appConfig.getDevice().equals("Windows")) {
            MainCall.genericFunctions.click(byGotItButton);
          //  MainCall.webDriverFactory.getDriver().findElement(byGotItButton).click();
            MainCall.webDriverWaits.sleep(1000);
        } else {
        }
    }

    public void clickLogin(){
        MainCall.log.logInfo("User clicks on Login button");
        WebElement elements = MainCall.webDriverFactory.getDriver().findElement(byLogin);
        elements.click();
    }

    public void clickLoginOnWelcomeScreen(){
        MainCall.log.logInfo("User clicks on Login in Welcome Screen");

        if (Url.contains("localhost"))
            MainCall.webDriverFactory.getDriver().findElement(byLoginWelcome).click();
    }
    public void verifyWelcomeScreen(){
        MainCall.log.logInfo("Verify Welcome Screen");
        MainCall.webDriverFactory.getDriver().findElement(byLoginWelcome).click();
    }

    public void selectHelpPopUpCross(){
        MainCall.log.logInfo("User clicks on cross on help popup");
        MainCall.webDriverFactory.getDriver().findElement(byHelpPopUpCross).click();
    }

    public void selectSkipButton(){
        MainCall.log.logInfo("User clicks on Skip button");
        if (MainCall.webDriverFactory.getDriver().getPageSource().contains("title=\"Skip\"")){
            MainCall.genericFunctions.assertionToDisplayed(bySkip);
            MainCall.webDriverFactory.getDriver().findElement(bySkip).click();
        }
    }

    public void selectNewUser(){
        WebDriverFactory.getDriver().findElement(byNewUser).click();
    }

    public void selectForgetPassword(){
        MainCall.webDriverFactory.getDriver().findElement(byForgetPassword).click();
        MainCall.genericFunctions.assertion(byH4,"Reset Password");
    }

    public void enterEmail(String email){
        MainCall.webDriverFactory.getDriver().findElement(byEmail).sendKeys(email);
    }

    public void fillSignUp(){
        String rand = MainCall.genericFunctions.generateRandomNum(3);
        MainCall.envGlobals.firstName = "AutoFirst" + rand;
        MainCall.envGlobals.lastName = "AutoLast" + rand;
        MainCall.envGlobals.email = "AutoEmail" + rand + "@abc.com";
        MainCall.envGlobals.password = "Admin123!";
        MainCall.envGlobals.company = "Comapny ABC" + rand;
        MainCall.webDriverFactory.getDriver().findElement(byFirstName).sendKeys(MainCall.envGlobals.firstName);
        MainCall.webDriverFactory.getDriver().findElement(byLastName).sendKeys( MainCall.envGlobals.lastName);
        MainCall.webDriverFactory.getDriver().findElement(byEmail).sendKeys(MainCall.envGlobals.email );
        MainCall.webDriverFactory.getDriver().findElement(byPassword).sendKeys(MainCall.envGlobals.password );
        MainCall.webDriverFactory.getDriver().findElement(byCompany).sendKeys(MainCall.envGlobals.company);
        MainCall.genericFunctions.click(byTermsAndConditionCheck);
    }

    public void selectForgetPasswordSumbit(){
        MainCall.webDriverFactory.getDriver().findElements(byButton).get(1).findElements(bySpan).get(1).click();
    }

    public void selectSignUpSumbit(){

        MainCall.webDriverFactory.getDriver().findElement(bySubmit).click();
    }

    public void scrollToElement()
     {
    MainCall.genericFunctions.scrollToElement(byButtonScroll);
     }
    public void selectValues(String value1,String value2)
    {
        MainCall.genericFunctions.selectElementFromDropDownByText(bySelector,value1);
        MainCall.genericFunctions.selectElementFromDropDownByText(bySelector,value2);

    }

    //Example DB Integration From jar
    public void dataBaseQuery() throws SQLException {
        ResultSet resultSet = MainCall.dbOp.getResult(selectQuery);
        while (resultSet.next()) {
            projectName = resultSet.getString("PROJECT_NAME");
            System.out.println(projectName);
            System.out.println(resultSet.getString("Build"));
            System.out.println(resultSet.getString("platform"));
        }

        int count  = MainCall.dbOp.executeQuery(updateQuery);
        System.out.println(count + "  rows are updated");
        count  = MainCall.dbOp.executeQuery(deleteQuery);
        System.out.println(count + "  rows are deleted");

    }
}

