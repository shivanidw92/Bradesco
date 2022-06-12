package pages;

import elements.EcommerceCartElements;
import org.openqa.selenium.support.PageFactory;

public class EcommerceCartPage extends EcommerceCartElements {

    public EcommerceCartPage(){ PageFactory.initElements(getDriver(), this);}

    public void acessarCarrinho(){
        botaoCarrinho.click();
        BasePage.waitOf(60);
    }

    public String isAlertaCarrinhoVazio(){
        return alertaCarrinhoVazio.getText();
    }

}
