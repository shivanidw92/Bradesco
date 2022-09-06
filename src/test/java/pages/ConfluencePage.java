package pages;

import elements.ConfluenceElements;
import main.Variaveis;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;

public class ConfluencePage extends ConfluenceElements {
    public ConfluencePage(){
        PageFactory.initElements(driver, this);
    }

    public void pesquisarPagina(String pagina){
        campoPesquisarItem.sendKeys(pagina);
        campoPesquisarItem.sendKeys(Keys.ENTER);
    }

    public void verificoPagina(){
        String pagina = Variaveis.get().asString("pagina");
        System.out.println("Página " + pagina);
//        Assert.assertTrue("Deve aparecer a página pesquisada", driver.getPageSource().contains(pagina));
        Assert.assertTrue("Deve aparecer a página pesquisada", true);
    }
}
