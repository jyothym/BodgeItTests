package tw.java.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class BodgeIt {

	public BodgeIt (WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[contains(text(), 'Home')]")
	public WebElement lnkHome;

	@FindBy(xpath = "//a[contains(text(), 'About Us')]")
	public WebElement lnkAboutUs;

	@FindBy(xpath = "//a[contains(text(), 'Contact Us')]")
	public WebElement lnkContactUs;

	@FindBy(xpath = "//a[contains(text(), 'Login')]")
	public WebElement lnkLogin;

	@FindBy(xpath = "//a[contains(text(), 'Logout')]")
	public WebElement lnkLogout;
	
	@FindBy(xpath = "//a[contains(text(), 'Your Basket')]")
	public WebElement lnkYourBasket;

	@FindBy(xpath = "//a[contains(text(), 'Search')]")
	public WebElement lnkSearch;
	
	@FindBy(xpath = "//tr[3]/td/a")	//2nd product in the list
	public WebElement lnkProduct;

	@FindBy(id ="submit")
	public WebElement submit;
	
	@FindBy(xpath ="//h3")
	public WebElement pageTitle;
	
	@FindBy(id = "comments")
	public WebElement feedback;
	
	@FindBy(xpath ="//p")
	public WebElement confirmationMessage;
	
	@FindBy(xpath ="//table/tbody/tr/td[1]")
	public WebElement feedbackPreview;
	
	@FindBy(name ="username")
	public WebElement txtUsername;
	
	@FindBy(name ="password")
	public WebElement txtPassword;

	@FindBy(name ="password1")
	public WebElement txtRegisterPassword;

	@FindBy(name ="password2")
	public WebElement txtRetypePassword;

	@FindBy(linkText ="Register")
	public WebElement lnkRegister;
	
	

	public void clickMenu(String menu) {
		switch (menu) {
		case "Home": 
			lnkHome.click();
			break;
		case "About Us":
			lnkAboutUs.click();
			break;
		case "Contact Us":
			lnkContactUs.click();
			break;
		case "Login":
			lnkLogin.click();
			break;
		case "Your Basket":
			lnkYourBasket.click();
			break;
		case "Search":
			lnkSearch.click();
			break;
		}
		return;
	}
	
	public void selectAProduct() {
		lnkProduct.click();
	}

	public void addIntoBasket() {
		submit.click();
	}

	public String getPageTitle() {
		return pageTitle.getText();
	}

	public void addFeedback(String string) {
		feedback.sendKeys(string);
		submit.click();
	}

	public String getCofirmationMessage() {
		return confirmationMessage.getText();
	}

	public String getFeedbackPreview() {
		return feedbackPreview.getText();
	}

	public void loginUser(String username, String password) {
		lnkLogin.click();
		txtUsername.sendKeys(username);
		txtPassword.sendKeys(password);
		submit.click();
	}

	public void registerUser(String randomUser, String password) {
		lnkLogin.click();
		lnkRegister.click();
		txtUsername.sendKeys(randomUser);
		txtRegisterPassword.sendKeys(password);
		txtRetypePassword.sendKeys(password);
		submit.click();
	}

	public void logout() {
		lnkLogout.click();
	}
	


}