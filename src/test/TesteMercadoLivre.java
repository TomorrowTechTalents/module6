package test;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TesteMercadoLivre {
    private static final String SEARCH_STRING = "limão";
	private void pesquisarPor(final String stringPesquisa) {
	    Page mercadoLivrePage = new MercadoLivre();

        mercadoLivrePage.driver.get("https://www.mercadolivre.com.br");

        WebElement cookies = mercadoLivrePage.driver.
                                              findElement(By.className("cookie-consent-banner-opt-out__action"));
        cookies.click();

        WebElement campoPesquisado = mercadoLivrePage.driver.findElement(By.name("as_word"));
        campoPesquisado.clear();
        campoPesquisado.sendKeys(stringPesquisa);
//        System.out.println("Título da página: " + mercadoLivrePage.driver.getTitle());
        campoPesquisado.submit();

        new WebDriverWait(mercadoLivrePage.driver, Duration.ofMillis(1000)).
                until((ExpectedCondition<Boolean>) objDriver -> {
                    return objDriver.getTitle().toLowerCase().startsWith(SEARCH_STRING);
                });

        System.out.println("Título da página: " + mercadoLivrePage.driver.getTitle());

        WebElement shipping = mercadoLivrePage.driver.findElement(By.id("shipping_highlighted_fulfillment"));
        shipping.click();

        List<WebElement> price = mercadoLivrePage.driver.findElements(By.tagName("a"));
        for (WebElement element : price) {
            String value = element.getAttribute("aria-label");

            if (value != null && value.equals("Até R$25")) {
                element.click();

                break;
            }
        }

        List<WebElement> results = mercadoLivrePage.driver.
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

        mercadoLivrePage.driver.quit();
	}

	@Test
    public void test_pesquisarPor_LetsCode() {
        pesquisarPor(SEARCH_STRING);
    }
}
