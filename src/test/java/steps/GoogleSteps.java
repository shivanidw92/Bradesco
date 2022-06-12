package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.BasePage;
import pages.GooglePage;

public class GoogleSteps {

        @Dado("que acesso Google")
        public void accessGoogle() {
                String GoogleUrl = "https://www.google.com/";
                BasePage.abrirNavegador(GoogleUrl);
                BasePage.waitOf(60);
        }

        @Quando("pesquiso o item {string}")
        public void pesquisoItem(String item) {
                GooglePage googleMainPage = new GooglePage();
                googleMainPage.pesquisarItem(item);
        }

        @Entao("verifico as imagens")
        public void verificoImagens() {
                GooglePage googleMainPage = new GooglePage();
                googleMainPage.verificoImagens();
        }

}
