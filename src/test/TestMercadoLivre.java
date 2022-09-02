package test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

class TestMercadoLivre {
    private static final String SEARCH_STRING = "feijoada";
    private void pesquisarPor(final String stringPesquisa) {
        MercadoLivre mercadoLivrePage = new MercadoLivre();

        mercadoLivrePage.acceptCookies();

        WebElement campoPesquisado = mercadoLivrePage.getSearchInput();
        campoPesquisado.clear();
        campoPesquisado.sendKeys(stringPesquisa);
        campoPesquisado.submit();

        new WebDriverWait(mercadoLivrePage.getDriver(), Duration.ofMillis(1000)).
                until((ExpectedCondition<Boolean>) objDriver ->
                    objDriver.getTitle().toLowerCase().startsWith(stringPesquisa));

        System.out.println("Título da página: " + mercadoLivrePage.getTitle());

        mercadoLivrePage.filterByFullShipping();

        mercadoLivrePage.filterByAriaLabel("Até R$8");

        List<Map.Entry<String, BigDecimal>> products = mercadoLivrePage.getProducts();

        System.out.println("quantidade de itens encontrados na página: " + products.size());

        for(Map.Entry<String, BigDecimal> product : products) {
            System.out.println();
            System.out.println("produto: " + product.getKey());
            System.out.println("preço: R$" + product.getValue());
            System.out.println();
        }

        mercadoLivrePage.getDriver().quit();
    }

    @Test
    void testPesquisarPorSearchString() {
        pesquisarPor(SEARCH_STRING);
    }
}
