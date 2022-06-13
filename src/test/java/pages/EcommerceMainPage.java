package pages;

import elements.EcommerceMainElements;
import org.openqa.selenium.support.PageFactory;

public class EcommerceMainPage extends EcommerceMainElements {

    public EcommerceMainPage(){
        PageFactory.initElements(driver, this);
    }

    public void pesquisarProduto(String produto){
        BasePage.waitVisibilityOf(campoPesquisarProduto);
        campoPesquisarProduto.sendKeys(produto);
        botaoPesquisarProduto.click();
    }

    public boolean isPesquisaRetornouResultados(){
        return !labelQtdResultadosEncontrados.getText().contains("0");
    }

}
