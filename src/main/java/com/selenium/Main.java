package com.selenium;

import com.github.javafaker.Faker;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {

    final static Logger logger = Logger.getLogger(Main.class);

    private static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";

    private static final String CHROME_DRIVER_PATH = "G:\\chromedriver.exe";

    private static final String HUMANITY_URL = "https://www.humanity.com/";

    private static final String LOGIN_USERNAME = "zadatak@sharklasers.com";

    private static final String LOGIN_PASSWORD = "zadatak";

    public static void main(String[] args) {

        System.setProperty(WEBDRIVER_CHROME_DRIVER, CHROME_DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        try {

            driver.get(HUMANITY_URL);

            driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(driver, 5);

            wait.until(ExpectedConditions.titleIs("Online Employee Scheduling Software - Humanity"));
            String expectedTitle1 = "Online Employee Scheduling Software - Humanity";
            String actualTitle1;
            actualTitle1 = driver.getTitle();
            if (actualTitle1.contentEquals(expectedTitle1)) {
                logger.debug("Web site is shown!");
            } else {
                logger.debug("Web site is not shown!");
            }

            WebElement cookies = driver.findElement(By.linkText("Got it!"));
            cookies.click();

            WebElement loginButton1 = driver.findElement(By.xpath(
                    "/html[1]/body[1]/div[2]/header[1]/div[1]/div[1]/div[1]/div[1]/nav[1]/div[2" +
                            "]/div[1]/a[2]/p[1]"));
            loginButton1.click();

            wait.until(ExpectedConditions.titleIs("Online Employee Scheduling Software | Workforce Management"));
            String expectedTitle4 = "Online Employee Scheduling Software | Workforce Management";
            String actualTitle4;
            actualTitle4 = driver.getTitle();
            if (actualTitle4.contentEquals(expectedTitle4)) {
                logger.debug("Login page is shown!");
            } else {
                logger.debug("Login page is not shown!");
            }

            WebElement usernameField = driver.findElement(By.id("email"));
            usernameField.sendKeys(LOGIN_USERNAME);

            WebElement passwordField = driver.findElement(By.id("password"));
            passwordField.sendKeys(LOGIN_PASSWORD);

            WebElement logInButton2 = driver.findElement(By.xpath("//button[@name='login'][contains(text(),'Log in')]"));
            logInButton2.submit();

            logger.debug("Logging in...");

            wait.until(ExpectedConditions.titleIs("Dashboard - Dashboard - Humanity"));
            String expectedTitle2 = "Dashboard - Dashboard - Humanity";
            String actualTitle2;
            actualTitle2 = driver.getTitle();
            if (actualTitle2.contentEquals(expectedTitle2)) {
                logger.debug("Dashboard page is shown!");
            } else {
                logger.debug("Dashboard page is not shown!");
            }

            WebElement staffButton = driver.findElement(By.cssSelector(".primNavQtip__inner > .icon-user"));
            staffButton.click();

            wait.until(ExpectedConditions.titleIs("Staff - List - Humanity"));
            String expectedTitle3 = "Staff - List - Humanity";
            String actualTitle3;
            actualTitle3 = driver.getTitle();
            if (actualTitle3.contentEquals(expectedTitle3)) {
                logger.debug("Staff List page is shown!");
            } else {
                logger.debug("Staff List page is not shown!");
            }

            WebElement addemployeesButton = driver.findElement(By.id("act_primary"));
            wait.until(ExpectedConditions.elementToBeClickable(By.id("act_primary")));
            addemployeesButton.click();
            Thread.sleep(1000);

            wait.until(ExpectedConditions.titleIs("Staff - Add Staff - Humanity"));
            String expectedTitle5 = "Staff - Add Staff - Humanity";
            String actualTitle5;
            actualTitle5 = driver.getTitle();
            if (actualTitle5.contentEquals(expectedTitle5)) {
                logger.debug("Add Staff page is shown!");
            } else {
                logger.debug("Add Staff page is not shown!");
            }

            Faker faker = new Faker();

            String firstName;
            String lastname;
            boolean employee = false;
            boolean test = true;
            String message;
            String status = "1 employee saved!";

            do {
                do {
                    WebElement firstnameFiled = driver.findElement(By.id("_asf1"));
                    firstnameFiled.click();
                    firstnameFiled.clear();
                    firstName = faker.name().firstName();
                    firstnameFiled.sendKeys("" + firstName);

                    WebElement lastnameFiled = driver.findElement(By.id("_asl1"));
                    lastnameFiled.click();
                    lastnameFiled.clear();
                    lastname = faker.name().lastName();
                    lastnameFiled.sendKeys("" + lastname);


                    WebElement email = driver.findElement(By.id("_ase1"));
                    email.click();
                    email.clear();
                    email.sendKeys(firstName + lastname + "@gmail.com");

                    WebElement saveEmployees = driver.findElement(By.id("_as_save_multiple"));
                    saveEmployees.click();
                    Thread.sleep(2000);

                    message = driver.findElement(By.xpath("//div[@id='_status']")).getText();

                    try {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ResultsTable")));
                    } catch (Exception e) {
                        logger.debug("Employee is not created, there was an error. Trying again!\n" + e);
                    }
                } while (test != status.equals(message));

                try {
                    employee = driver.findElement(By.xpath(
                            "//td[contains(@class,'employee')][contains(text(),'" +
                                    firstName + " " + lastname + "')]")).isDisplayed();
                    logger.debug("Employee " + firstName + " " + lastname + " is created!");
                } catch (Exception e) {
                    logger.debug("Employee not verified, program will be terminated!\n" + e);
                    break;
                }
            } while (employee != true);

        } catch (Exception e) {
            logger.debug("There was an unexpected error!\n" + e);
        }

        try {
            Thread.sleep(3500);
        } catch (InterruptedException ie) {
            logger.debug("Pausing main thread failed!\n" + ie);
        }
        logger.debug("Exiting...");
        driver.quit();

    }
}
