package steps;

import cucumber.api.java.es.Dado;
import cucumber.api.java.it.Quando;
import cucumber.api.java.pt.Entao;
import main.Variaveis;
import pages.ConfluencePage;

import static steps.BaseSteps.openBrowser;

public class ConfluenceSteps {
    @Dado("que acesso o Confluence")
    public void acessarConfluence() {
        String confluenceUrl = "https://confluence.bradescoseguros.com.br/";
        openBrowser(confluenceUrl);
    }

    @Quando("pesquiso pela pagina")
    public void pesquisoPagina() {
        String pagina = Variaveis.get().asString("bamboo_pagina");
        ConfluencePage confluencePage = new ConfluencePage();
        confluencePage.pesquisarPagina(pagina);
    }

    @Entao("verifico a pagina")
    public void verificoPagina() {
        ConfluencePage confluencePage = new ConfluencePage();
        confluencePage.verificoPagina();
    }
}
