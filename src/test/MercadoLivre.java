package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

class MercadoLivre extends Page {
    MercadoLivre() {
        getDriver().get("https://www.mercadolivre.com.br");
    }

    void acceptCookies() {
        getDriver().findElement(By.className("cookie-consent-banner-opt-out__action")).click();
    }

    WebElement getSearchInput() {
        return getDriver().findElement(By.name("as_word"));
    }
}
