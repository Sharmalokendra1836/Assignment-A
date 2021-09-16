package com.sample;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class SampleMain {
    static String newCheckingAccountID = "";
    static String newSavingAccountID = "";

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Bholenath\\Downloads\\seleniumexample\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get("https://parabank.parasoft.com/parabank/index.htm");
        System.out.println(webDriver.getTitle());
        Thread.sleep(2000);
        webDriver.findElement(By.xpath("//input[@name='username']")).sendKeys("john");
        webDriver.findElement(By.xpath("//input[@name='password']")).sendKeys("demo");
        webDriver.findElement(By.xpath("//input[@value='Log In']")).click();

        webDriver.findElement(By.xpath("//a[normalize-space()='Open New Account']")).click();

        String checking = webDriver.findElement(By.xpath("//option[@value='0']")).getText();
        System.out.println(checking);
        Thread.sleep(3000);
        String accountID = webDriver.findElement(By.xpath("//option[@value='12345']")).getText();

        System.out.println(accountID);
        String CHECKING = "CHECKING";
        String ACCOUNT_ID = "12345";

        if (CHECKING.equals(checking) && ACCOUNT_ID.equals(accountID)) {
            Thread.sleep(3000);
            webDriver.findElement(By.xpath("//input[@value='Open New Account']")).click();
            Thread.sleep(2000);
            newCheckingAccountID = webDriver.findElement(By.xpath("//a[@id='newAccountId']")).getText();
            webDriver.findElement(By.xpath("//a[@id='newAccountId']")).click();
            Thread.sleep(1000);
            Thread.sleep(1000);
            System.out.println(webDriver.getTitle());


            String accountIDInADPage = webDriver.findElement(By.xpath("//td[@id='accountId']")).getText();
            String accountTypeInADPage = webDriver.findElement(By.xpath("//td[@id='accountType']")).getText();

//            Assert.assertEquals(webDriver.getTitle(),"Account Details");
            Assert.assertEquals(newCheckingAccountID, accountIDInADPage);
            Assert.assertEquals(checking, accountTypeInADPage);

        }
        Thread.sleep(4000);


        webDriver.findElement(By.xpath("//a[normalize-space()='Open New Account']")).click();

//        webDriver.findElement(By.xpath("//select[@id='type']")).click();

        Select selectAccount = new Select(webDriver.findElement(By.id("type")));
        selectAccount.selectByValue("1");
        String saving = webDriver.findElement(By.xpath("//option[@value='1']")).getText();
        System.out.println(saving);
        Thread.sleep(3000);
        String savingAccountId = webDriver.findElement(By.xpath("//option[@value='12345']")).getText();

        System.out.println(savingAccountId);
        String SAVINGS = "SAVINGS";
        String SAVING_ACCOUNT_ID = "12345";

        if (SAVINGS.equals(saving) && SAVING_ACCOUNT_ID.equals(savingAccountId)) {
            Thread.sleep(3000);
            webDriver.findElement(By.xpath("//input[@value='Open New Account']")).click();
            Thread.sleep(2000);
            newSavingAccountID = webDriver.findElement(By.xpath("//a[@id='newAccountId']")).getText();
            webDriver.findElement(By.xpath("//a[@id='newAccountId']")).click();
            Thread.sleep(1000);
            Thread.sleep(1000);
            System.out.println(webDriver.getTitle());


            String accountIDInADPage = webDriver.findElement(By.xpath("//td[@id='accountId']")).getText();
            String accountTypeInADPage = webDriver.findElement(By.xpath("//td[@id='accountType']")).getText();

//            Assert.assertEquals(webDriver.getTitle(),"Account Details");
            Assert.assertEquals(newSavingAccountID, accountIDInADPage);
            Assert.assertEquals(saving, accountTypeInADPage);


            //Bill pay work started
            webDriver.findElement(By.xpath("//a[normalize-space()='Bill Pay']")).click();
            webDriver.findElement(By.name("payee.name")).sendKeys("checkabc");
            webDriver.findElement(By.name("payee.address.street")).sendKeys("checkabc");
            webDriver.findElement(By.name("payee.address.city")).sendKeys("checkabc");
            webDriver.findElement(By.name("payee.address.state")).sendKeys("checkabc");
            webDriver.findElement(By.name("payee.address.zipCode")).sendKeys("411000");
            webDriver.findElement(By.name("payee.phoneNumber")).sendKeys("9876543210");
            webDriver.findElement(By.name("payee.accountNumber")).sendKeys(newCheckingAccountID);
            webDriver.findElement(By.name("verifyAccount")).sendKeys(newCheckingAccountID);
            webDriver.findElement(By.name("amount")).sendKeys("100");

            Select fromAccountId = new Select(webDriver.findElement(By.name("fromAccountId")));
            fromAccountId.selectByValue(newSavingAccountID);

            Thread.sleep(3000);
            webDriver.findElement(By.xpath("//input[@value='Send Payment']")).click();
        }


        //Verification of account balance in sender account
        webDriver.findElement(By.xpath("//a[normalize-space()='Accounts Overview']")).click();
        Thread.sleep(4000);

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0,1000)");
        By.linkText(newCheckingAccountID).findElement(webDriver).click();
        Thread.sleep(5000);
        String receiver = webDriver.findElement(By.id("balance")).getText();
        String receiverAvailable = webDriver.findElement(By.id("balance")).getText();
        Assert.assertEquals(receiver, "$100.00");
        Assert.assertEquals(receiverAvailable, "$100.00");

        Thread.sleep(5000);


        //debiter
        webDriver.findElement(By.xpath("//a[normalize-space()='Accounts Overview']")).click();
        Thread.sleep(4000);


        js.executeScript("window.scrollBy(0,1000)");
        By.linkText(newSavingAccountID).findElement(webDriver).click();
        Thread.sleep(5000);
        String debiter = webDriver.findElement(By.id("balance")).getText();
        String debiterAvailable = webDriver.findElement(By.id("balance")).getText();
        Assert.assertEquals(debiter, "$0.00");
        Assert.assertEquals(debiterAvailable, "$0.00");

        Thread.sleep(5000);
        webDriver.quit();
    }
}
