import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

class MercadoLivreTest {
    private static final String SEARCH_STRING = "feijoada";
    private static final String ARIA_LABEL = "Até R$8";
    private void pesquisarPor(final String stringPesquisa) throws InterruptedException {
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
        Thread.sleep(10_000);

        mercadoLivrePage.filterByAriaLabel(ARIA_LABEL);
        Thread.sleep(10_000);

        List<Map.Entry<String, BigDecimal>> products = mercadoLivrePage.getProducts();

        System.out.println("quantidade de itens encontrados na página: " + products.size());

        mercadoLivrePage.clickOnFirstResult();
        Thread.sleep(10_000);

        System.out.println("status primeiro produto: " + mercadoLivrePage.getStatus());

        for(Map.Entry<String, BigDecimal> product : products) {
            System.out.println();
            System.out.println("produto: " + product.getKey());
            System.out.println("preço (sem desconto): R$" + product.getValue());
            System.out.println();
        }

        mercadoLivrePage.getDriver().quit();
    }

    @Test
    void shouldOpenFirstResultGivenFilters() throws InterruptedException {
        pesquisarPor(SEARCH_STRING);
    }
}
