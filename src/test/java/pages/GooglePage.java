package pages;

import elements.GoogleElements;
import main.Variaveis;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;

public class GooglePage extends GoogleElements {

    public GooglePage(){
        PageFactory.initElements(driver, this);
    }

    public void pesquisarItem(String item){
        campoPesquisarItem.sendKeys(item);
        campoPesquisarItem.sendKeys(Keys.ENTER);
    }

    public void verificoImagens(){
        System.out.println("Imagens de gatinhos ...");
    }

}
