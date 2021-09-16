package com.pb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver webDriver;

    By username = By.xpath("//input[@name='username']");
    By password = By.xpath("//input[@name='password']");
    By loginButton = By.xpath("//input[@value='Log In']");

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void typeUserName() {
        webDriver.findElement(username).sendKeys("john");

    }

    public void typePassword() {
        webDriver.findElement(password).sendKeys("demo");

    }

    public void clickOnLoginButton() {
        webDriver.findElement(loginButton).click();
    }

}
