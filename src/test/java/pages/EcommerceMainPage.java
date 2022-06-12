package pages;

import elements.EcommerceMainElements;
import org.openqa.selenium.support.PageFactory;

public class EcommerceMainPage extends EcommerceMainElements {

    public EcommerceMainPage(){
        PageFactory.initElements(getDriver(), this);
    }

    public void pesquisarProduto(String produto){
        campoPesquisarProduto.sendKeys(produto);
        botaoPesquisarProduto.click();
    }

    public boolean isPesquisaRetornouResultados(){
        return !labelQtdResultadosEncontrados.getText().contains("0");
    }

}
