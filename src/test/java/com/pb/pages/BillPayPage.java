package com.pb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BillPayPage {

    WebDriver webDriver;

    public BillPayPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    By billPay = By.xpath("//a[normalize-space()='Bill Pay']");
    By payeeName = By.name("payee.name");
    By payeeAddress = By.name("payee.address.street");
    By payeeCity = By.name("payee.address.city");
    By payeeState = By.name("payee.address.state");
    By payeeZipCode = By.name("payee.address.zipCode");
    By payeePhoneNumber = By.name("payee.phoneNumber");
    By payeeAccountNumber = By.name("payee.accountNumber");
    By verifyAccount = By.name("verifyAccount");
    By amount = By.name("amount");

    By fromAccountId = By.name("fromAccountId");
    By sendPaymentButton = By.xpath("//input[@value='Send Payment']");

    public void clickOnBillPayLink() {
        webDriver.findElement(billPay).click();
    }

    public void sendPayeeName() {
        webDriver.findElement(payeeName).sendKeys("checkabc");

    }

    public void sendPayeeAddress() {
        webDriver.findElement(payeeAddress).sendKeys("checkabc");

    }

    public void sendPayeeCity() {
        webDriver.findElement(payeeCity).sendKeys("checkabc");


    }

    public void sendPayeeState() {
        webDriver.findElement(payeeState).sendKeys("checkabc");


    }

    public void sendPayeeZipCode() {
        webDriver.findElement(payeeZipCode).sendKeys("411000");


    }

    public void sendPayeephoneNumber() {
        webDriver.findElement(payeePhoneNumber).sendKeys("9876543210");


    }

    public void sendAccountNumber(String newCheckingAccountID) {
        webDriver.findElement(payeeAccountNumber).sendKeys(newCheckingAccountID);
        webDriver.findElement(verifyAccount).sendKeys(newCheckingAccountID);


    }

    public void sendAmmount() {
        webDriver.findElement(amount).sendKeys("100");
    }

    public void selectFromAccountId(String newSavingAccountID) {
        WebElement element = webDriver.findElement(fromAccountId);
        Select select = new Select(element);
        select.selectByValue(newSavingAccountID);
    }

    public void clickOnSendPayment() {
        webDriver.findElement(sendPaymentButton).click();
    }
}
