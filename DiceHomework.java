package com.dicehomework;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DiceHomework {

	public static void main(String[] args) {

		String[] keywords = { "Test Analyst", "none", "Test Manager", "Test Lead", "Tester", "Java Developer",
				"Java Programmer", "Java UI Developer", "Selenium Automation Engineer", "Selenium Tester",
				"Selenium Mobile Web Service Tester", "Python Developer", "Automation Tester",
				"Automation Test Engineer", "Data Scientist", "Database Administrator", "Database Developer",
				"Data Entry", "Data Analyst", "IT Manager" };
		List<String> list = new ArrayList<>();
		for (int i = 0; i < keywords.length; i++) {
			list.add(keywords[i]);
		}
	
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String url = "https://dice.com";
		driver.get(url);

		String actualTitle = driver.getTitle();
		String expectedTitle = "Job Search for Technology Professionals | Dice.com";

		if (actualTitle.equals(expectedTitle)) {
			System.out.println("Step PASS. Dice homepage successfully loaded");
		} else {
			System.out.println("Step FAIL. Dice homepge did not load successfully");
			throw new RuntimeException("Step FAIL. Dice homepge did not load successfully");
		}
		
		String count = "";
		for (int i = 0; i < list.size(); i++) {
			driver.findElement(By.id("search-field-keyword")).clear();
			driver.findElement(By.id("search-field-keyword")).sendKeys(list.get(i));
			String location = "77099";
			driver.findElement(By.id("search-field-location")).clear();
			driver.findElement(By.id("search-field-location")).sendKeys(location);
			driver.findElement(By.id("findTechJobs")).click();
			try {
				count = driver.findElement(By.id("posiCountId")).getText();
			} catch (RuntimeException exception) {
				count = "Error";
			}

			if (count.equals("Error")) {
				System.out.println((i + 1) + ". No such job found.");
				driver.navigate().back();
			} else {

				System.out.println((i + 1) + ". " + list.get(i) + "   " + count + " jobs are available");
				driver.navigate().back();
			}
		}
		driver.close();
		System.out.println("TEST COMPLETED -" + LocalDateTime.now());
	}

}
