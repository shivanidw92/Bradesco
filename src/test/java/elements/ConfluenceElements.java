package elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class ConfluenceElements extends BasePage {
    @FindBy(id = "quick-search-query")
    public static WebElement campoPesquisarItem;
}
