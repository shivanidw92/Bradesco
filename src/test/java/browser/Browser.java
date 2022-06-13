package browser;

import main.Variaveis;
import org.openqa.selenium.WebDriver;

public class Browser {
    public static WebDriver getWebDriver(){
        return getDriver(getBrowser());
    }

    private static IBrowser getBrowser(){
        String browser = Variaveis.get().asString("browser", "chrome");

        if(browser.equalsIgnoreCase("chrome")) return new GoogleChrome();
        else if(browser.equalsIgnoreCase("firefox")) return new Firefox();
        else if(browser.equalsIgnoreCase("edge")) return new Edge();
        else if(browser.equalsIgnoreCase("ie")) return new InternetExplorer();
            //TODO
            //else if(browser.equalsIgnoreCase("opera")){...
        else throw new RuntimeException("Informe um browser válido");
    }

    private static WebDriver getDriver(IBrowser browser){
        return browser.getDriver();
    }
}
