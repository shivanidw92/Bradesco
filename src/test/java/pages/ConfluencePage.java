package pages;

import elements.ConfluenceElements;
import main.Variaveis;
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
        System.out.println(System.getenv("bamboo_pagina"));
        System.out.println(System.getProperty("bamboo_pagina"));
        System.out.println("env: "+Variaveis.get().AllFromEnvironmentVariables());
        System.out.println("local: "+Variaveis.get().AllFromLocalProperties());
        System.out.println("all: "+Variaveis.get().All());
        System.out.println(System.getProperty("bamboo_pagina"));
        String pagina = Variaveis.get().asString("bamboo_pagina");
        System.out.println("Página " + pagina);
    }
}
