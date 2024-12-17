package objects;

import general.MainCall;
import org.openqa.selenium.By;
public class TrainingSessionPage {
    public static By byAlertButton = By.xpath("/html/body/section/div[1]/div[2]/div/div[2]/a");
    public static By bySlideBar = By.xpath("//*[@id=\"slider\"]");
    public static By byRange =By.xpath("//a[@class=\"ui-slider-handle ui-state-default ui-corner-all\"]");
    public static By bySuccess = By.id("rangeSuccess");
    public static By byPromptAlertButton = By.className("analystic");
    public static By AlertWithTextbox = By.xpath("//a[@href=\"#Textbox\"]");
    public static By buttontoDemoPromopt = By.xpath("//*[@id=\"Textbox\"]/button");

    public static By byFirstName = By.xpath("//*[@placeholder=\"First Name\"]");
    public static By byLastName = By.xpath("//*[@placeholder=\"Last Name\"]");
    public static By byEmail = By.xpath("//*[@ng-model=\"EmailAdress\"]");
    public static By byPhone = By.xpath("//*[@ng-model=\"Phone\"]");
    public static By byAddress = By.xpath("//*[@ng-model=\"Adress\"]");
    public static By byRadioButton = By.xpath("//*[@ng-model=\"radiovalue\"]");
    public static By bySubmit = By.id("submitbtn");

    public TrainingSessionPage() {
    }

  public void openMultipleModalAlert()
  {

      MainCall.genericFunctions.clickSpecificElementFromList(byAlertButton,0);
  }
  public void slideThirdRangeSlider()
  {

      MainCall.genericFunctions.slideBar(bySlideBar,byRange,60,0);
  }

  public void verifySuccessMessage()
  {

      MainCall.genericFunctions.assertion(bySuccess,"66");
  }

  public void EnterTextInAlertBox() throws InterruptedException {
      MainCall.genericFunctions.click(AlertWithTextbox);
      MainCall.genericFunctions.click(buttontoDemoPromopt);
//      MainCall.genericFunctions.click(byPromptAlertButton);
      MainCall.webDriverWaits.sleep(2000);
      MainCall.genericFunctions.inputTextAlert("Training Session 02");
      MainCall.webDriverWaits.sleep(2000);
      MainCall.genericFunctions.acceptAlert();
      MainCall.webDriverWaits.sleep(2000);
  }
  public void fillForm() throws InterruptedException {
      MainCall.webDriverFactory.getDriver().findElement(byFirstName).sendKeys("firstnme");
      MainCall.webDriverFactory.getDriver().findElement(byLastName).sendKeys("lastname");
      MainCall.webDriverFactory.getDriver().findElement(byEmail).sendKeys("spider@gmail.com");
      MainCall.webDriverFactory.getDriver().findElement(byPhone).sendKeys("(92)3336401111");
      MainCall.webDriverFactory.getDriver().findElement(byAddress).sendKeys("my home address");
      MainCall.genericFunctions.radioButtonClick(byRadioButton,1);
      MainCall.webDriverFactory.getDriver().findElement(bySubmit).click();
      MainCall.webDriverWaits.sleep(2000);
  }
}
