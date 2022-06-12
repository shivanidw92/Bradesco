package elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class EcommerceCartElements extends BasePage {

        @FindBy(css = "a[title='View my shopping cart']")
        protected WebElement botaoCarrinho;

        @FindBy(xpath = "/html/body/div/div[2]/div/div[3]/div/p")
        protected WebElement alertaCarrinhoVazio;

    }

