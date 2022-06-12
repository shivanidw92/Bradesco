package elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class GoogleElements extends BasePage {

    @FindBy(css = "input[title='Pesquisar']")
    public static WebElement campoPesquisarItem;

}
