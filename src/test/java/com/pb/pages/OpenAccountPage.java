package com.pb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class OpenAccountPage {

    WebDriver webDriver;

    By openNewAccountLink = By.xpath("//a[normalize-space()='Open New Account']");
    By openNewAccountButton = By.xpath("//input[@value='Open New Account']");
    By accountType = By.id("type");
    By checkingAccount = By.xpath("//option[@value='0']");
    By savingsAccount = By.xpath("//option[@value='1']");
    By accountIdDefault = By.xpath("(//select[@id='fromAccountId']/option)[1]");
    By newAcountId = By.xpath("//a[@id='newAccountId']");
    By accountIdInPageDetails = By.xpath("//td[@id='accountId']");
    By accountTypeInPageDetails = By.xpath("//td[@id='accountType']");




    public String getAcccountIdInADPage(){
        return webDriver.findElement(accountIdInPageDetails).getText();
    }

    public String getAcccountTypeInADPage(){
        return webDriver.findElement(accountTypeInPageDetails).getText();
    }

    public OpenAccountPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }


    public void clickOnOpenNewAccountLeftSide() {
        webDriver.findElement(openNewAccountLink).click();
    }

    public void clickOnOpenNewAccountButton() {
        webDriver.findElement(openNewAccountButton).click();
    }


    public String getSavingsAccountText() {
        return webDriver.findElement(savingsAccount).getText();
    }
    public String getCheckingAccountText() {
        return webDriver.findElement(checkingAccount).getText();
    }

    public String getDefaultAccountIdText() {
        return webDriver.findElement(accountIdDefault).getText();
    }


    public String getNewAccountIdTest(){
        return webDriver.findElement(newAcountId).getText();
    }

    public void clickOnNewAccountCreated(){
        webDriver.findElement(newAcountId).click();
    }

    public void openSavingsAccount() {
        Select selectAccount = new Select(webDriver.findElement(accountType));
        selectAccount.selectByValue("1");
    }
}
