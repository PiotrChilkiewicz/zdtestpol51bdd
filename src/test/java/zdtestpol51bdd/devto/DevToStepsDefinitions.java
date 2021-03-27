package zdtestpol51bdd.devto;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import zdtestpol51bdd.devto.pages.MainPage;
import zdtestpol51bdd.devto.pages.PodcastListPage;
import zdtestpol51bdd.devto.pages.SingleBlogPage;

import java.util.List;
import java.util.Locale;

public class DevToStepsDefinitions {
    WebDriver driver;
    WebDriverWait wait;
    String firstBlogTitle;
    String firstCastTitle;
    String searchingPhrase;
    String podcastBtn;

    MainPage mainPage;
    SingleBlogPage singleBlogPage;
    PodcastListPage podcastListPage;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Given("I go to devto main page")
    public void i_go_to_devto_main_page() {
        mainPage = new MainPage(driver);
    }

    @When("I click on first blog displayed")
    public void i_click_on_first_blog_displayed() {
        firstBlogTitle = mainPage.firstBlog.getText();
        mainPage.selectFirstBlog();
    }

    @Then("I should be redirected to blog page")
    public void i_should_be_redirected_to_blog_page() {
        wait.until(ExpectedConditions.titleContains(firstBlogTitle));
        singleBlogPage = new SingleBlogPage(driver);
        String blogTitleText = singleBlogPage.blogTitle.getText();
        Assert.assertEquals(firstBlogTitle, blogTitleText);
    }

    @When("I go to podcast section")
    public void i_go_to_podcast_section() {
        mainPage.goToPodcastSection();
    }

    @When("I click on first podcast on the list")
    public void i_click_on_first_podcast_on_the_list() {
        wait.until(ExpectedConditions.titleContains("Podcasts"));
        podcastListPage = new PodcastListPage(driver);
        firstCastTitle = podcastListPage.firstCast.getText();
        firstCastTitle = firstCastTitle.replace("podcast", "");
        podcastListPage.selectFirstPodcast();
    }

    @Then("I should be redirected to podcast page")
    public void i_should_be_redirected_to_podcast_page() {
        wait.until(ExpectedConditions.titleContains(firstCastTitle));
        String castTitleText = singleBlogPage.blogTitle.getText();
        Assert.assertEquals(firstCastTitle, castTitleText);
    }

    @When("I search for {string} phrase")
    public void i_search_for_phrase(String phrase) {
        WebElement searchBar = driver.findElement(By.name("q"));
        searchBar.sendKeys(phrase);
        searchingPhrase = phrase;
        searchBar.sendKeys(Keys.ENTER);
    }

    @Then("Top {int} blogs found should have correct phrase in title or snippet")
    public void top_blogs_found_should_have_correct_phrase_in_title_or_snippet(Integer int1) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3.crayons-story__title"))); //h3
        wait.until(ExpectedConditions.attributeContains(By.id("substories"), "class", "search-results-loaded"));
        List<WebElement> allPosts = driver.findElements(By.className("crayons-story__body")); // div - caly wpis
        if (allPosts.size() >= int1) {
            for (int i = 0; i < int1; i++) {
                WebElement singlePost = allPosts.get(i);
                WebElement singlePostTitle = singlePost.findElement(By.cssSelector(".crayons-story__title > a")); //tytul kafelka
                String singlePostTitleText = singlePostTitle.getText().toLowerCase(); // wyciagnij tekst z tytulu
                Boolean isPhraseInTitle = singlePostTitleText.contains(searchingPhrase);
                if (isPhraseInTitle) { // isPhraseInTitle == true
                    Assert.assertTrue(isPhraseInTitle);
                } else {
                    WebElement snippet = singlePost.findElement(By.xpath("//div[contains(@class,'crayons-story__snippet')]"));
                    String snippetText = snippet.getText().toLowerCase();
                    Boolean isPhraseInSnippet = snippetText.contains(searchingPhrase);
                    Assert.assertTrue(isPhraseInSnippet);
                }
            }
        }
    }

    @When("I play the podcast")
    public void i_play_the_podcast() {
        wait.until(ExpectedConditions.titleContains(firstCastTitle));
        WebElement recordButton = driver.findElement(By.id("record"));
        recordButton.click();
    }

    @Then("Podcast Should be played")
    public void podcast_should_be_played() {
        WebElement initializing = driver.findElement(By.className("status-message"));
        wait.until(ExpectedConditions.invisibilityOf(initializing));
        WebElement pauseBtn = driver.findElement(By.xpath("//img[contains(@class,'pause-butt')]"));
        Boolean isPauseBtnVisible = pauseBtn.isDisplayed();
        Assert.assertTrue(isPauseBtnVisible);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}