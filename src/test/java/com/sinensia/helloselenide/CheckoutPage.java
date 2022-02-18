package com.sinensia.helloselenide;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// http://localhost:3000/#!/review
public class CheckoutPage {

    public SelenideElement textCheckoutPage = $("h2");

    public SelenideElement btnOrderConfirm = $(".btn-success");

    public SelenideElement btnOrderCancel = $(".btn-default");

    public SelenideElement ageInput = $("#ageInput" );
    
    public SelenideElement confirmation() {
        return textCheckoutPage;
    }

    public SelenideElement getOrder() {
        return btnOrderConfirm;
    }

    public OrderPage order() {
        btnOrderConfirm.click();
        return page(OrderPage.class);
    }

    public CartPage cancel() {
        btnOrderCancel.click();
        return page(CartPage.class);
    }

    public void setAge(String age) {
        ageInput.sendKeys(age);
    }

    public String getAge() {
        return ageInput.getText();
    }

    public SelenideElement ageInput() {
        return ageInput;
    }
}
