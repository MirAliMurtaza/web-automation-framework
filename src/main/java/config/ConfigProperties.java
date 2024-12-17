package config;

public class ConfigProperties {
    public static ApplicationConfigReader appConfig = new ApplicationConfigReader();

    public static String Device = appConfig.getDevice();
    public static String Headless = appConfig.getHeadless();
    public static String Browser = appConfig.getBrowser();
    public static String Environment = appConfig.getEnvironment();

    public static String UserName = appConfig.getUserName();
    public static String Password = appConfig.getPassword();

    public static String BaseURI = appConfig.getBaseURI();
    public static String Url = appConfig.getUrl();

    public static String IsEnableReporting = appConfig.getIsEnableReporting();
    public static String IsEnableRecording = appConfig.getIsEnableRecording();
    public static String LogTestRail = appConfig.getLogTestRail();




}
