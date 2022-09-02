package test;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
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

        System.out.println("Título da página: " + mercadoLivrePage.getDriver().getTitle());

        WebElement shipping = mercadoLivrePage.getDriver().findElement(By.id("shipping_highlighted_fulfillment"));
        shipping.click();

        List<WebElement> price = mercadoLivrePage.getDriver().findElements(By.tagName("a"));
        for (WebElement element : price) {
            String value = element.getAttribute("aria-label");

            if (value != null && value.equals("Até R$25")) {
                element.click();

                break;
            }
        }

        List<WebElement> results = mercadoLivrePage.getDriver().
                                   findElements(By.className("ui-search-result__content-wrapper"));

        for (WebElement result : results) {
            String productName = result.findElement(By.className("ui-search-item__title")).getText();

            String productPrice = result.findElement(By.className("price-tag-fraction")).getText();

            List<WebElement> productFractionalPrice = result.findElements(By.className("price-tag-cents"));

            if (!productFractionalPrice.isEmpty()) {
                productPrice += "," + productFractionalPrice.get(0).getText();
            }

            System.out.println("produto: " + productName);
            System.out.println("preço: " + productPrice);
        }

        System.out.println("quantidade total de itens encontrados: " + results.size());

        mercadoLivrePage.getDriver().quit();
    }

    @Test
    void testPesquisarPorSearchString() {
        pesquisarPor(SEARCH_STRING);
    }
}
