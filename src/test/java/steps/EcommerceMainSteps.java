package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.*;
import pages.EcommerceMainPage;

import static steps.BaseSteps.openBrowser;

public class EcommerceMainSteps {

    @Dado("que acesso o ecommerce")
    public void accessEcommerce() {
        String ecommerceUrl = "http://automationpractice.com";
        openBrowser(ecommerceUrl);
    }

    @Quando("pesquiso o produto {string}")
    public void pesquisoProduto(String produto) {
        EcommerceMainPage ecommerceMainPage = new EcommerceMainPage();
        ecommerceMainPage.pesquisarProduto(produto);
    }

    @Entao("a pesquisa deve retornar resultados")
    public void validarQuePesquisaRetornouResultados(){
        EcommerceMainPage ecommerceMainPage = new EcommerceMainPage();
        Assert.assertTrue("O resultado da pesquisa está vazio.", ecommerceMainPage.isPesquisaRetornouResultados());
    }
}
