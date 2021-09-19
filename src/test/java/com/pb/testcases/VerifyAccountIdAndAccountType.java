package com.pb.testcases;

import com.pb.Util;
import com.pb.pages.AccountDetailPage;
import com.pb.pages.BillPayPage;
import com.pb.pages.LoginPage;
import com.pb.pages.OpenAccountPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.pb.Util.*;

public class VerifyAccountIdAndAccountType {
    WebDriver webDriver = new ChromeDriver();

    String newCheckingAccountID = "";
    String newSavingsAccountID = "";
    String checkedInitialBalance = "";
    String savingsInitialBalance = "";

    public VerifyAccountIdAndAccountType() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

    }

    @Test(priority = 0)
    public void verifyNewAccountOpenedForCheckings() throws InterruptedException {

        webDriver.manage().window().maximize();
        webDriver.get("https://parabank.parasoft.com/parabank/index.htm");
        Thread.sleep(1000);

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.typeUserName();
        loginPage.typePassword();
        loginPage.clickOnLoginButton();

        OpenAccountPage openAccountPage = new OpenAccountPage(webDriver);
        openAccountPage.clickOnOpenNewAccountLeftSide();
        Thread.sleep(1000);
        String checkingAccountText = openAccountPage.getCheckingAccountText();
        String CHECKING = "CHECKING";
        takeScreenshot(webDriver,"AccountType");
        if (CHECKING.equals(checkingAccountText)) {
            openAccountPage.clickOnOpenNewAccountButton();
            Thread.sleep(2000);
            newCheckingAccountID = openAccountPage.getNewAccountIdTest();
            openAccountPage.clickOnNewAccountCreated();
            Thread.sleep(1000);
            checkedInitialBalance = webDriver.findElement(By.id("balance")).getText();

            String accountIDInADPage = openAccountPage.getAcccountIdInADPage();
            String accountTypeInADPage = openAccountPage.getAcccountTypeInADPage();
            takeScreenshot(webDriver,"Account_Checked_Overview");
            Assert.assertEquals(newCheckingAccountID, accountIDInADPage);
            Assert.assertEquals(checkingAccountText, accountTypeInADPage);
        }
    }

    @Test(priority = 1)
    public void verifyNewAccountOpenedForSavings() throws InterruptedException {
        OpenAccountPage openAccountPage = new OpenAccountPage(webDriver);
        openAccountPage.clickOnOpenNewAccountLeftSide();
        Thread.sleep(3000);
        openAccountPage.openSavingsAccount();
        String savingsAccountText = openAccountPage.getSavingsAccountText();
        String SAVINGS = "SAVINGS";

        if (SAVINGS.equals(savingsAccountText)) {
            openAccountPage.clickOnOpenNewAccountButton();
            Thread.sleep(2000);
            newSavingsAccountID = openAccountPage.getNewAccountIdTest();
            openAccountPage.clickOnNewAccountCreated();
            Thread.sleep(1000);
            savingsInitialBalance = webDriver.findElement(By.id("balance")).getText();

            String accountIDInADPage = openAccountPage.getAcccountIdInADPage();
            String accountTypeInADPage = openAccountPage.getAcccountTypeInADPage();
            takeScreenshot(webDriver,"Account_Savings_Overview");
            Assert.assertEquals(newSavingsAccountID, accountIDInADPage);
            Assert.assertEquals(savingsAccountText, accountTypeInADPage);
        }
    }

    @Test(priority = 2)
    public void verifyBillPayAccount() throws InterruptedException {

        BillPayPage billPayPage = new BillPayPage(webDriver);

        billPayPage.clickOnBillPayLink();
        billPayPage.sendPayeeName();
        billPayPage.sendPayeeAddress();
        billPayPage.sendPayeeCity();
        billPayPage.sendPayeeState();
        billPayPage.sendPayeeZipCode();
        billPayPage.sendPayeephoneNumber();
        billPayPage.sendAccountNumber(newCheckingAccountID);
        billPayPage.sendAmmount();

        billPayPage.selectFromAccountId(newSavingsAccountID);
        Thread.sleep(1000);
        billPayPage.clickOnSendPayment();
        Thread.sleep(1000);

        AccountDetailPage accountDetailPage = new AccountDetailPage(webDriver);
        accountDetailPage.clickOnAccountOverviewPage();

        Thread.sleep(2000);

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0,1000)");
        accountDetailPage.clickOnCheckingAccountId(newCheckingAccountID);
        Thread.sleep(2000);
        String receiver = accountDetailPage.getBalanceText();
        String receiverAvailable = accountDetailPage.getBalanceText();
        takeScreenshot(webDriver,"After_Bill_Pay_Account_Checked_Overview");
        Assert.assertEquals(receiver, savingsInitialBalance);
        Assert.assertEquals(receiverAvailable, savingsInitialBalance);

        Thread.sleep(2000);

        //debiter
        accountDetailPage.clickOnAccountOverviewPage();
        Thread.sleep(4000);

        js.executeScript("window.scrollBy(0,1000)");
        accountDetailPage.clickOnCheckingAccountId(newSavingsAccountID);
        Thread.sleep(2000);
        String debiter = accountDetailPage.getBalanceText();
        String debiterAvailable = accountDetailPage.getBalanceText();
        takeScreenshot(webDriver,"After_Bill_Pay_Account_Savings_Overview");

        Assert.assertEquals(debiter, "$0.00");
        Assert.assertEquals(debiterAvailable, "$0.00");

        webDriver.findElement(By.xpath("//a[text()='Log Out']")).click();
        takeScreenshot(webDriver,"LogOut");
        webDriver.quit();

    }

}
