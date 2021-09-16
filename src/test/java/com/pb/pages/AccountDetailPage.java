package com.pb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountDetailPage {

    WebDriver webDriver;
    By accountOverview = By.xpath("//a[normalize-space()='Accounts Overview']");
    By balanceId = By.id("balance");
    public AccountDetailPage(WebDriver webDriver) {
        this.webDriver=webDriver;
    }


    public void clickOnAccountOverviewPage(){
        webDriver.findElement(accountOverview).click();
    }

    public void clickOnCheckingAccountId(String newCheckingAccountID){
        By.linkText(newCheckingAccountID).findElement(webDriver).click();
    }
    public String getBalanceText(){
       return  webDriver.findElement(balanceId).getText();
    }

}
