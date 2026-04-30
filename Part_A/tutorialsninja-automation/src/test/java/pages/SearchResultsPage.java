package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResultsPage extends BasePage {

    private static final By SEARCH_KEYWORD_INPUT = By.id("input-search");
    private static final By SEARCH_BUTTON = By.id("button-search");
    private static final By CATEGORY_DROPDOWN = By.id("input-category");
    private static final By SEARCH_SUBCATEGORIES_CHECKBOX = By.id("input-sub-category");
    private static final By PRODUCT_NAMES = By.cssSelector(".product-thumb h4 a");
    private static final By NO_RESULTS_MESSAGE = By.cssSelector("#content p");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public void searchWithKeyword(String keyword) {
        waitAndType(SEARCH_KEYWORD_INPUT, keyword);
        waitAndClickWithRetry(SEARCH_BUTTON);
    }

    public void selectCategory(String categoryText) {
        new Select(driver.findElement(CATEGORY_DROPDOWN)).selectByVisibleText(categoryText);
    }

    public void checkSearchInSubcategories() {
        waitAndClick(SEARCH_SUBCATEGORIES_CHECKBOX);
    }

    public List<String> getProductNames() {
        return driver.findElements(PRODUCT_NAMES).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public boolean isProductDisplayed(String productName) {
        return getProductNames().stream().anyMatch(name -> name.contains(productName));
    }

    public String getNoResultsMessage() {
        return getText(NO_RESULTS_MESSAGE);
    }
}
