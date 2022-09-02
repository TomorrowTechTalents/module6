package test;

import org.openqa.selenium.By;

class MercadoLivre extends Page {
    MercadoLivre() {
        getDriver().get("https://www.mercadolivre.com.br");
    }

    void acceptCookies() {
        getDriver().findElement(By.className("cookie-consent-banner-opt-out__action")).click();
    }
}
