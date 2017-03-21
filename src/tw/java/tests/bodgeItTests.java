package tw.java.tests;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import tw.java.utils.BaseTest;

public class bodgeItTests extends BaseTest  {

	@Test(priority = 1)
	public void testaddToBasket() throws InterruptedException {
		clickMenu("Home");
		Assert.assertEquals(homePage.getPageTitle(), "Our Best Deals!");
		homePage.selectAProduct();
		Assert.assertEquals(homePage.getPageTitle(), "Product");		
		homePage.addIntoBasket();
		Assert.assertEquals(homePage.getPageTitle(), "Your Basket");
	}

	@Test(priority = 2)
	public void testFeedback() throws InterruptedException {
		clickMenu("Contact Us");
		Assert.assertEquals(homePage.getPageTitle(), "Contact Us");
		homePage.addFeedback("Testing...");
		Assert.assertEquals(homePage.getCofirmationMessage(), "Thank you for your feedback:");
	}

	@Test(priority = 3)
	public void tstRegisterUser() {
		String randomUser = RandomStringUtils.randomAlphabetic(10) + "@test.com";
		homePage.registerUser(randomUser, "password");
		Assert.assertTrue(driver.getPageSource().indexOf("You have successfully registered with The BodgeIt Store.") > 0);
		homePage.logout();
	}

	@Test(priority = 4)
	public void tstRegisterAndLoginUser() {
		String randomUser = RandomStringUtils.randomAlphabetic(10) + "@test.com";
		homePage.registerUser(randomUser, "password");
		Assert.assertTrue(driver.getPageSource().indexOf("You have successfully registered with The BodgeIt Store.") > 0);
		checkMenu("Logout", "logout.jsp");
		homePage.loginUser(randomUser, "password");
		Assert.assertTrue(driver.getPageSource().indexOf("You have logged in successfully:") > 0);
		homePage.logout();
	}

	public void checkMenu(String linkText, String page) {
		WebElement link = driver.findElement(By.linkText(linkText));
		link.click();
		Assert.assertEquals(BaseURL + page, driver.getCurrentUrl());
	}
	
	public void clickMenu(String menu) {
		driver.findElement(By.linkText(menu)).click();
	}

}
