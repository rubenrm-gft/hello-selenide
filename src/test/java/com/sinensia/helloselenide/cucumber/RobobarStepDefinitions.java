package com.sinensia.helloselenide.cucumber;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.sinensia.helloselenide.CartPage;
import com.sinensia.helloselenide.CheckoutPage;
import com.sinensia.helloselenide.OrderPage;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;

public class RobobarStepDefinitions {
    CartPage cartPage;
    CheckoutPage checkoutPage;
    OrderPage orderPage;

    @Given("user opens robobar website")
    @Given("l'usuari entra al bar")
    public void userOpensRobobarWebsite() {
        //Configuration.browserSize = "1288x800";
        SelenideLogger.addListener("allure", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false)
        );

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        //capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

        open("/");
        cartPage = new CartPage();
        checkoutPage = null;
    }

    @When("user adds a cola")
    public void userAddACola() {
        cartPage.addCola();
    }

    @When("user adds {int} cola")
    public void userAddsNCola(int n) {
        for(int i=0; i<n; ++i){
            cartPage.addCola();
        }
    }

    @When("user adds a beer")
    public void userAddsABeer() {
        cartPage.addBeer();
    }

    @When("user adds {int} beer")
    public void userAddsNBeer(int n) {
        for(int i=0; i<n; ++i){
            cartPage.addBeer();
        }
    }

    @When("user adds a wine")
    public void userAddsAWine() {
        cartPage.addWine();
    }

    @When("user adds {int} wine")
    public void userAddsNWine(int n) {
        for(int i=0; i<n; ++i){
            cartPage.addWine();
        }
    }

    @When("user adds {int} cola {int} beer {int} wine")
    public void userAddsColaBeerWine(int cola, int beer, int wine) {
        userAddsNCola(cola);
        userAddsNBeer(beer);
        userAddsNWine(wine);
    }

    @When("user adds {int} cola {int} beer {int} wine and her {int}")
    public void userAddsColaBeerWineAndHerAge(int cola, int beer, int wine, int age) {
        userAddsNCola(cola);
        userAddsNBeer(beer);
        userAddsNWine(wine);
        userEnterHerAgeIs(age);
    }

    @Then("total should be €{double}")
    public void totalShouldBe(Double total) {
        cartPage.getTotal().shouldBe(exactText(String.format("€%.2f", total)));
    }

    @Then("total should be {string}")
    public void totalShouldBe(String total) {
        cartPage.getTotal().shouldBe(exactText(total));
    }

    @When("user press submit button")
    public void userPressSubmitButton() {
        cartPage.checkout();
        checkoutPage = new CheckoutPage();
    }

    @When("user enter her age is {int}")
    public void userEnterHerAgeIs(int age) {
        checkoutPage.setAge(String.format("%s", age));
    }

    @When("user press order button")
    public void userPressOrderButton() {
        checkoutPage.order();
        orderPage = new OrderPage();
    }

    @Then("alert is active")
    public void alertIsActive() {
        orderPage.getAlertDiv().shouldNotBe(hidden);
    }

    @Then("alert is not active")
    public void alertIsNotActive() {
        orderPage.getAlertDiv().shouldBe(hidden);
    }

    @When("order is confirmed")
    public void orderIsConfirmed() {
        orderPage.getSentMessage().shouldBe(text("Coming right up! ~bzzzt~"));
    }

    @But("checkout result is {string}")
    public void checkoutResultIsExpected(String expected) {
        throw new PendingException();
    }

    @Given("user is ready to checkout with alcohol")
    public void userIsReadyToCheckoutWithAlcohol() {
        userOpensRobobarWebsite();
        userAddsABeer();
        userPressSubmitButton();
    }

    @Then("robobar does not allow checkout")
    public void robobarDoesNotAllowCheckout() {
        orderPage = checkoutPage.order();
        orderPage.getAlertDiv().shouldNotBe(hidden);
    }

    @Then("robobar confirms order")
    public void robobarConfirmsOrder() {
        orderPage = checkoutPage.order();
        orderPage.getAlertDiv().shouldBe(hidden);
        orderPage.getSentMessage().shouldBe(text("Coming right up! ~bzzzt~"));
    }
}