package pages;

import elements.EcommerceCartElements;
import org.openqa.selenium.support.PageFactory;

public class EcommerceCartPage extends EcommerceCartElements {

    public EcommerceCartPage(){ PageFactory.initElements(driver, this);}

    public void acessarCarrinho(){
        botaoCarrinho.click();
    }

    public String isAlertaCarrinhoVazio(){
        return alertaCarrinhoVazio.getText();
    }

}
