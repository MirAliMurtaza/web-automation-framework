package testcases;

import general.*;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

public class TrainingSession extends BaseTest {

    @Test(description = "225371")
    public static void AlertWindow() throws InterruptedException {
        MainCall.genericFunctions.driverStart("https://demo.automationtesting.in/Modals.html");
        MainCall.trainingSessionPage.openMultipleModalAlert();
        MainCall.genericFunctions.PercyCapture("Alert Window");
    }


    //updated
    @Test(description = "225371")
    public static void Slider() throws InterruptedException {
        MainCall.genericFunctions.driverStart("https://demo.automationtesting.in/Slider.html");
        MainCall.webDriverWaits.sleep(5000);
        MainCall.trainingSessionPage.slideThirdRangeSlider();
//        MainCall.trainingSessionPage.verifySuccessMessage();
        MainCall.genericFunctions.PercyCapture("Slider");
    }

    //updated
    @Test(description = "225371")
    public static void JavaScriptAlert() throws InterruptedException {
        MainCall.genericFunctions.driverStart("https://demo.automationtesting.in/Alerts.html");
        MainCall.trainingSessionPage.EnterTextInAlertBox();
        MainCall.genericFunctions.PercyCapture("JavaScriptAlert");
    }

    @Test(description = "225371")
    public static void InputForm() throws InterruptedException {
        MainCall.genericFunctions.driverStart("https://demo.automationtesting.in/Register.html");
        MainCall.trainingSessionPage.fillForm();
        MainCall.genericFunctions.PercyCapture("InputForm");
    }
}