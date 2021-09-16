package com.com.pb.testcases;

import com.pb.pages.AccountDetailPage;
import com.pb.pages.BillPayPage;
import com.pb.pages.LoginPage;
import com.pb.pages.OpenAccountPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VerifyAccountIdAndAccountType {
    WebDriver webDriver = new ChromeDriver();

    static String newCheckingAccountID = "";
    static String newSavingsAccountID = "";

    public VerifyAccountIdAndAccountType() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Bholenath\\Downloads\\seleniumexample\\chromedriver.exe");

    }

    @Test(priority = 0)
    public void verifyNewAccountOpenForCheckings() throws InterruptedException {

        webDriver.manage().window().maximize();
        webDriver.get("https://parabank.parasoft.com/parabank/index.htm");
        Thread.sleep(2000);

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.typeUserName();
        loginPage.typePassword();
        loginPage.clickOnLoginButton();

        OpenAccountPage openAccountPage = new OpenAccountPage(webDriver);
        openAccountPage.clickOnOpenNewAccountLeftSide();
        Thread.sleep(3000);
        String checkingAccountText = openAccountPage.getCheckingAccountText();

        String defaultAccountIdText = openAccountPage.getDefaultAccountIdText();

        String CHECKING = "CHECKING";
        String ACCOUNT_ID = "12345";

        if (CHECKING.equals(checkingAccountText) && ACCOUNT_ID.equals(defaultAccountIdText)) {
            Thread.sleep(3000);
            openAccountPage.clickOnOpenNewAccountButton();
            Thread.sleep(2000);
            newCheckingAccountID = openAccountPage.getNewAccountIdTest();
            System.out.println("Acount Id" + newCheckingAccountID);
            openAccountPage.clickOnNewAccountCreated();
            Thread.sleep(1000);

            String accountIDInADPage = openAccountPage.getAcccountIdInADPage();
            String accountTypeInADPage = openAccountPage.getAcccountTypeInADPage();
            Assert.assertEquals(newCheckingAccountID, accountIDInADPage);
            Assert.assertEquals(checkingAccountText, accountTypeInADPage);
        }
    }

    @Test(priority = 1)
    public void verifyNewAccountOpenForSavings() throws InterruptedException {
        OpenAccountPage openAccountPage = new OpenAccountPage(webDriver);
        openAccountPage.clickOnOpenNewAccountLeftSide();
        Thread.sleep(3000);

        openAccountPage.openSavingsAccount();

        String savingsAccountText = openAccountPage.getSavingsAccountText();

        String defaultAccountIdText = openAccountPage.getDefaultAccountIdText();

        String SAVINGS = "SAVINGS";
        String ACCOUNT_ID = "12345";

        if (SAVINGS.equals(savingsAccountText) && ACCOUNT_ID.equals(defaultAccountIdText)) {
            Thread.sleep(3000);
            openAccountPage.clickOnOpenNewAccountButton();
            Thread.sleep(2000);
            newSavingsAccountID = openAccountPage.getNewAccountIdTest();
            System.out.println("Acount Id" + newSavingsAccountID);
            openAccountPage.clickOnNewAccountCreated();
            Thread.sleep(1000);

            String accountIDInADPage = openAccountPage.getAcccountIdInADPage();
            String accountTypeInADPage = openAccountPage.getAcccountTypeInADPage();
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
        System.out.println(newCheckingAccountID);
        billPayPage.sendAccountNumber(newCheckingAccountID);
        billPayPage.sendAmmount();

        System.out.println(newSavingsAccountID);
        billPayPage.selectFromAccountId(newSavingsAccountID);
        Thread.sleep(3000);
        billPayPage.clickOnSendPayment();
        Thread.sleep(4000);

        AccountDetailPage accountDetailPage = new AccountDetailPage(webDriver);
        accountDetailPage.clickOnAccountOverviewPage();

        Thread.sleep(4000);

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0,1000)");
        accountDetailPage.clickOnCheckingAccountId(newCheckingAccountID);
        Thread.sleep(5000);
        String receiver = accountDetailPage.getBalanceText();
        String receiverAvailable = accountDetailPage.getBalanceText();
        Assert.assertEquals(receiver, "$100.00");
        Assert.assertEquals(receiverAvailable, "$100.00");

        Thread.sleep(5000);


        //debiter
        accountDetailPage.clickOnAccountOverviewPage();
        Thread.sleep(4000);


        js.executeScript("window.scrollBy(0,1000)");
        accountDetailPage.clickOnCheckingAccountId(newSavingsAccountID);
        Thread.sleep(5000);
        String debiter = accountDetailPage.getBalanceText();
        System.out.println(debiter);
        String debiterAvailable = accountDetailPage.getBalanceText();
        System.out.println(debiterAvailable);


        Assert.assertEquals(debiter, "$0.00");
        Assert.assertEquals(debiterAvailable, "$0.00");

        Thread.sleep(5000);
        webDriver.quit();

    }

}
