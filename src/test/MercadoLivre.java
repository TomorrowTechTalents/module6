package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class MercadoLivre extends Page {
    private static final String DECIMAL_SEPARATOR = ".";
    MercadoLivre() {
        getDriver().get("https://www.mercadolivre.com.br");
    }

    void acceptCookies() {
        getDriver().findElement(By.className("cookie-consent-banner-opt-out__action")).click();
    }

    WebElement getSearchInput() {
        return getDriver().findElement(By.name("as_word"));
    }

    void filterByFullShipping() {
        getDriver().findElement(By.id("shipping_highlighted_fulfillment")).click();
    }

    void filterByAriaLabel(String ariaLabel) {
        List<WebElement> elements = getDriver().findElements(By.tagName("a"));

        for (WebElement element : elements) {
            String value = element.getAttribute("aria-label");

            if (value != null && value.equals(ariaLabel)) {
                element.click();

                break;
            }
        }
    }

    List<Map.Entry<String, BigDecimal>> getProducts() {
        List<Map.Entry<String, BigDecimal>> products = new ArrayList<>();

        List<WebElement> results = getDriver().findElements(By.className("ui-search-result__content-wrapper"));
        for (WebElement result : results) {
            String productName = result.findElement(By.className("ui-search-item__title")).getText();
            String productPrice = result.findElement(By.className("price-tag-fraction")).getText();
            List<WebElement> productFractionalPrice = result.findElements(By.className("price-tag-cents"));

            if (!productFractionalPrice.isEmpty()) {
                productPrice += DECIMAL_SEPARATOR + productFractionalPrice.get(0).getText();
            }

            products.add(new AbstractMap.SimpleEntry<>(productName, new BigDecimal(productPrice)));
        }

        return products;
    }


}
