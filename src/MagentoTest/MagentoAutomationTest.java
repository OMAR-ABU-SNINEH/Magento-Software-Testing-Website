package MagentoTest;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import dev.failsafe.Fallback;

public class MagentoAutomationTest {

	WebDriver driver = new ChromeDriver();
	
	JavascriptExecutor js = (JavascriptExecutor) driver;

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	Random rand = new Random();
	SecureRandom secRandom = new SecureRandom();

	String emailUser = "";
	String passwordUser = "";
	String userName = "";
	String logoutDirect = "https://magento.softwaretestingboard.com/customer/account/logout/";/* Sign Out */

	@BeforeTest
	public void mySetup() {
		driver.manage().window().maximize();
		driver.get("https://magento.softwaretestingboard.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));
	}

	@Test(priority = 1, enabled = true)
	public void createAnAccount() {

		WebElement createAccountLink = driver.findElement(By.partialLinkText("Account"));
		createAccountLink.click();

		WebElement firstNameInput = driver.findElement(By.id("firstname"));
		WebElement lastNameInput = driver.findElement(By.id("lastname"));
		WebElement emailInput = driver.findElement(By.id("email_address"));
		WebElement passwordInput = driver.findElement(By.id("password"));
		WebElement passwordConfirmInput = driver.findElement(By.id("password-confirmation"));
		WebElement createAccountButton = driver.findElement(By.cssSelector(".action.submit.primary"));

		String[] firstNames = { "John", "Emily", "Michael", "Sarah", "David", "Jessica", "James", "Ashley", "Robert",
				"Jennifer" };
		String[] lastNames = { "Smith", "Johnson", "Brown", "Williams", "Jones", "Garcia", "Miller", "Davis",
				"Rodriguez", "Martinez" };

		int firstNameIndex = rand.nextInt(firstNames.length);
		int lastNameIndex = rand.nextInt(lastNames.length);
		int randomNumForEmail = rand.nextInt(10000);

		String firstName = firstNames[firstNameIndex];
		String lastName = lastNames[lastNameIndex];
		String domain = "@gmail.com";

		emailUser = firstName + lastName + randomNumForEmail + domain;
		passwordUser = passwordGeneration();
		userName = firstName + lastName;

		firstNameInput.sendKeys(firstName);
		lastNameInput.sendKeys(lastName);
		emailInput.sendKeys(emailUser);
		passwordInput.sendKeys(passwordUser);
		passwordConfirmInput.sendKeys(passwordUser);
		createAccountButton.click();

		inforamtionAccount(emailUser, passwordUser, userName);

		boolean actualCreate = driver.findElement(By.className("logged-in")).getText().contains("Welcome");
		boolean expectCreate = true;

		Assert.assertEquals(actualCreate, expectCreate);
	}

	@Test(priority = 2, enabled = true)
	public void logout() {
		driver.get(logoutDirect);

		String actualLogout = driver.findElement(By.className("base")).getText();
		String expectLogout = "You are signed out";

		Assert.assertEquals(actualLogout, expectLogout);
	}

	@Test(priority = 3, enabled = true)
	public void login() {
		WebElement loginlink = driver
				.findElement(By.xpath("//div[@class='panel header']//a[contains(text(),'Sign In')]"));
		loginlink.click();

		WebElement emailInput = driver.findElement(By.id("email"));
		WebElement passwordInput = driver.findElement(By.id("pass"));
		WebElement loginButton = driver.findElement(By.id("send2"));

		emailInput.sendKeys(emailUser);
		passwordInput.sendKeys(passwordUser);
		loginButton.click();

		boolean actualLogin = driver.findElement(By.className("logged-in")).getText().contains("Welcome");
		boolean expectLogin = true;

		Assert.assertEquals(actualLogin, expectLogin);
	}

	@Test(priority = 4, enabled = true)
	public void addAllWomenItemsCard() {
		WebElement womenSection = driver.findElement(By.xpath("//span[normalize-space()='Women']"));
		womenSection.click();

		WebElement cardItems = driver.findElement(By.className("product-items"));
		List<WebElement> cardItemsList = cardItems.findElements(By.tagName("li"));

		for (int i = 0; i < cardItemsList.size(); i++) {

			WebElement cardItem = cardItemsList.get(i);
			cardItem.click();

			WebElement allSizes = driver
					.findElement(By.xpath("//div[@class='swatch-attribute size']//div[@role='listbox']"));
			WebElement allColors = driver
					.findElement(By.xpath("//div[@class='swatch-attribute color']//div[@role='listbox']"));
			WebElement quantity = driver.findElement(By.id("qty"));

			List<WebElement> allSizesList = allSizes.findElements(By.tagName("div"));
			List<WebElement> allColorsList = allColors.findElements(By.tagName("div"));

			int sizeRandomNum = rand.nextInt(allSizesList.size());
			int colorRandomNum = rand.nextInt(allColorsList.size());

			WebElement size = allSizesList.get(sizeRandomNum);
			WebElement color = allColorsList.get(colorRandomNum);
			WebElement addToCartButton = driver.findElement(By.id("product-addtocart-button"));

			size.click();
			color.click();

			quantityRandomFilled(quantity);

			addToCartButton.click();

			assertAddedItemsMessage();

			backPage();

			// Remember to reinitialize values after going back.
			cardItems = driver.findElement(By.className("product-items"));
			cardItemsList = cardItems.findElements(By.tagName("li"));
		}
		driver.get("https://magento.softwaretestingboard.com/");
	}

	@Test(priority = 5, enabled = true)
	public void addAllMenItemsCard() {
		WebElement menSection = driver.findElement(By.xpath("//span[normalize-space()='Men']"));
		menSection.click();

		WebElement cardItems = driver.findElement(By.className("product-items"));
		List<WebElement> cardItemsList = cardItems.findElements(By.tagName("li"));

		for (int i = 0; i < cardItemsList.size(); i++) {

			WebElement cardItem = cardItemsList.get(i);
			cardItem.click();

			WebElement allSizes = driver
					.findElement(By.xpath("//div[@class='swatch-attribute size']//div[@role='listbox']"));
			WebElement allColors = driver
					.findElement(By.xpath("//div[@class='swatch-attribute color']//div[@role='listbox']"));
			WebElement quantity = driver.findElement(By.id("qty"));

			List<WebElement> allSizesList = allSizes.findElements(By.tagName("div"));
			List<WebElement> allColorsList = allColors.findElements(By.tagName("div"));

			int sizeRandomNum = rand.nextInt(allSizesList.size());
			int colorRandomNum = rand.nextInt(allColorsList.size());

			WebElement size = allSizesList.get(sizeRandomNum);
			WebElement color = allColorsList.get(colorRandomNum);
			WebElement addToCartButton = driver.findElement(By.id("product-addtocart-button"));

			size.click();
			color.click();

			quantityRandomFilled(quantity);

			addToCartButton.click();

			assertAddedItemsMessage();

			backPage();

			// Remember to reinitialize values after going back.
			cardItems = driver.findElement(By.className("product-items"));
			cardItemsList = cardItems.findElements(By.tagName("li"));
		}
		driver.get("https://magento.softwaretestingboard.com/");
	}

	@Test(priority = 6, enabled = true)
	public void addAllGearItemsCard() {
		WebElement gearSection = driver.findElement(By.xpath("//span[normalize-space()='Gear']"));
		gearSection.click();

		WebElement cardItems = driver.findElement(By.className("product-items"));
		List<WebElement> cardItemsList = cardItems.findElements(By.tagName("li"));

		for (int i = 0; i < cardItemsList.size(); i++) {

			WebElement cardItem = cardItemsList.get(i);
			cardItem.click();

			By locatorAddBtn = By.id("product-addtocart-button");
			By locatorCustomBtn = By.id("bundle-slide");
			By locatorQuantity = By.id("qty");

			WebElement addToCartButton;
			WebElement customizeButton;
			WebElement quantity;

			if (driver.findElement(locatorAddBtn).isDisplayed()) {

				quantity = driver.findElement(locatorQuantity);
				quantityRandomFilled(quantity);

				addToCartButton = driver.findElement(locatorAddBtn);
				addToCartButton.click();

			} else {

				customizeButton = driver.findElement(locatorCustomBtn);
				customizeButton.click();

//				wait.until(ExpectedConditions.visibilityOfElementLocated(locatorQuantity));/*if you don't use timeout wait (in before test)*/

				quantity = driver.findElement(locatorQuantity);
				quantityRandomFilled(quantity);
				
				
				addToCartButton = driver.findElement(locatorAddBtn);
				js.executeScript("arguments[0].click();", addToCartButton);
				
//				addToCartButton = driver.findElement(locatorAddBtn);
//				addToCartButton.click();

			}

			assertAddedItemsMessage();

			backPage();

			// Remember to reinitialize values after going back.
			cardItems = driver.findElement(By.className("product-items"));
			cardItemsList = cardItems.findElements(By.tagName("li"));
		}
		driver.get("https://magento.softwaretestingboard.com/");
	}

	private void assertAddedItemsMessage() {

		WebElement addMessage = driver.findElement(By.cssSelector(".message-success.success.message"));
		
		boolean actualAdded = addMessage.getText().contains("You added");
		boolean expectAdded = true;

		Assert.assertEquals(actualAdded, expectAdded);
	}

	public void quantityRandomFilled(WebElement quantity) {

		int quantityRandomNum = 1 + rand.nextInt(10);/* to ignore zero values */
		quantity.clear();/* to clear default value */
		quantity.sendKeys("" + quantityRandomNum);/* to casting to string value */

	}

	public String passwordGeneration() {

		String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
		String DIGITS = "0123456789";
		String SPECIAL_CHARS = "!@#$%^&*()-_=+<>";
		String ALL_CHARS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARS;

		int PASSWORD_LENGTH = 7;

		StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int index = secRandom.nextInt(ALL_CHARS.length());
			password.append(ALL_CHARS.charAt(index));
		}

		return password.toString() + "#"; //to ensure the password is strong :)
	}

	public void inforamtionAccount(String email, String password, String userName) {

		System.out.println("********************************");
		System.out.println("The User Name: " + userName);
		System.out.println("The Email: " + email);
		System.out.println("The Password: " + password);
		System.out.println("********************************");

	}

	public void backPage() {

		driver.navigate().back();

	}
}
