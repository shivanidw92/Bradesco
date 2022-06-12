package elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class EcommerceMainElements extends BasePage {

    @FindBy(id = "search_query_top")
    public static WebElement campoPesquisarProduto;

    @FindBy(name = "submit_search")
    protected static WebElement botaoPesquisarProduto;

    @FindBy(css = ".heading-counter")
    protected WebElement labelQtdResultadosEncontrados;

}
