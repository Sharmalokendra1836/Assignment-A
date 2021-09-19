package com.sample;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;

public class SampleMain {
    static String newCheckingAccountID = "";
    static String newSavingAccountID = "";
    static String checkedInitialBalance = "";
    static String savingsInitialBalance = "";
    static WebDriver webDriver = new ChromeDriver();
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get("https://parabank.parasoft.com/parabank/index.htm");
        System.out.println(webDriver.getTitle());
        Thread.sleep(2000);
        webDriver.findElement(By.xpath("//input[@name='username']")).sendKeys("john");
        webDriver.findElement(By.xpath("//input[@name='password']")).sendKeys("demo");
        webDriver.findElement(By.xpath("//input[@value='Log In']")).click();
        Thread.sleep(4000);

        takeScreenshot("Login");
        webDriver.findElement(By.xpath("//a[normalize-space()='Open New Account']")).click();

        String checking = webDriver.findElement(By.xpath("//option[@value='0']")).getText();
        System.out.println(checking);
        Thread.sleep(3000);
        String accountID = webDriver.findElement(By.xpath("(//select[@id='fromAccountId']/option)[1]")).getText();

        System.out.println(accountID);
        String CHECKING = "CHECKING";

        if (CHECKING.equals(checking)) {
            Thread.sleep(3000);
            webDriver.findElement(By.xpath("//input[@value='Open New Account']")).click();
            Thread.sleep(2000);
            newCheckingAccountID = webDriver.findElement(By.xpath("//a[@id='newAccountId']")).getText();
            webDriver.findElement(By.xpath("//a[@id='newAccountId']")).click();
            Thread.sleep(1000);
            checkedInitialBalance = webDriver.findElement(By.id("balance")).getText();
            Thread.sleep(1000);
            System.out.println(webDriver.getTitle());


            String accountIDInADPage = webDriver.findElement(By.xpath("//td[@id='accountId']")).getText();
            String accountTypeInADPage = webDriver.findElement(By.xpath("//td[@id='accountType']")).getText();

            Assert.assertEquals(newCheckingAccountID, accountIDInADPage);
            Assert.assertEquals(checking, accountTypeInADPage);

        }
        Thread.sleep(4000);


        webDriver.findElement(By.xpath("//a[normalize-space()='Open New Account']")).click();

        Select selectAccount = new Select(webDriver.findElement(By.id("type")));
        selectAccount.selectByValue("1");
        String saving = webDriver.findElement(By.xpath("//option[@value='1']")).getText();
        System.out.println(saving);
        Thread.sleep(3000);
        String savingAccountId = webDriver.findElement(By.xpath("(//select[@id='fromAccountId']/option)[1]")).getText();

        System.out.println(savingAccountId);
        String SAVINGS = "SAVINGS";

        if (SAVINGS.equals(saving)) {
            Thread.sleep(3000);
            webDriver.findElement(By.xpath("//input[@value='Open New Account']")).click();
            Thread.sleep(2000);
            newSavingAccountID = webDriver.findElement(By.xpath("//a[@id='newAccountId']")).getText();
            webDriver.findElement(By.xpath("//a[@id='newAccountId']")).click();
            Thread.sleep(1000);
            savingsInitialBalance = webDriver.findElement(By.id("balance")).getText();
            Thread.sleep(1000);
            System.out.println(webDriver.getTitle());


            String accountIDInADPage = webDriver.findElement(By.xpath("//td[@id='accountId']")).getText();
            String accountTypeInADPage = webDriver.findElement(By.xpath("//td[@id='accountType']")).getText();

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
        Assert.assertEquals(receiver, checkedInitialBalance);
        Assert.assertEquals(receiverAvailable, checkedInitialBalance);

        Thread.sleep(5000);

        //debiter
        webDriver.findElement(By.xpath("//a[normalize-space()='Accounts Overview']")).click();
        Thread.sleep(4000);


        js.executeScript("window.scrollBy(0,1000)");
        By.linkText(newSavingAccountID).findElement(webDriver).click();
        Thread.sleep(5000);
        String debiter = webDriver.findElement(By.id("balance")).getText();
        String debiterAvailable = webDriver.findElement(By.id("balance")).getText();
        String value = savingsInitialBalance.replace("$", "");
        System.out.println(value);
        Assert.assertEquals(debiter, "$0.00");
        Assert.assertEquals(debiterAvailable, "$0.00");

        System.out.println("Closing browser");
        webDriver.close();
    }

    public static void takeScreenshot(String locator) {
        File src = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        File des = new File("./Results/Screenshots/"+" "+"_" + "sample.jpeg");
        try{
            FileUtils.copyFile(src,des);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
