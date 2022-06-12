package browser;

import main.Variaveis;
import org.openqa.selenium.WebDriver;

public interface IBrowser {
    default String getDriverPathByOS(String driverName){
        String driverPath = Variaveis.get().asString("driverPath", "src/test/resources/drivers/");
        String osName = Variaveis.get().asString("os.name", "windows").toLowerCase();

        if(osName.contains("windows")) {
            return driverPath + driverName + ".exe";
        }
        else return driverPath + driverName; //Linux
        //TODO
        //else if(osName.contains("macos")){...
    }

    WebDriver getDriver();
}
