package com.tsystems.shop.selenium;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class SignUpSeleniumTest {

    private WebDriver browser;

    @Before
    public void init() {
        browser = new ChromeDriver();
    }

    @Test
    public void SignUpPositiveSeleniumTest1() {
        browser.get(Url.ACCOUNT);

        browser.findElement(By.className("sign-up")).findElement(By.tagName("a")).click();

        browser.findElement(By.id("name")).sendKeys("Test");
        browser.findElement(By.id("surname")).sendKeys("Test");
        browser.findElement(By.id("email")).sendKeys("someEmail@gmail.com");
        browser.findElement(By.id("password")).sendKeys("123456");
        browser.findElement(By.className("submit-button")).click();

        assertEquals(Url.ACCOUNT, browser.getCurrentUrl());
        assertEquals("someEmail@gmail.com", browser.findElement(By.className("email")).getText());
    }


    @After
    public void destroy() {
        browser.close();
    }
}
