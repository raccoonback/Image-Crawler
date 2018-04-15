package com.landvibe.hackday.batch;

import com.landvibe.hackday.domain.Search;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.core.io.ClassPathResource;

import java.io.FileOutputStream;
import java.io.IOException;

public class SearchProcessor implements ItemProcessor<Search, Search> {
    private static final String DOMAIN_FOR_SEARCH = "http://www.naver.com";
    private String path;
    private WebDriver driver;

    @Override
    public synchronized Search process(Search item) throws IOException {
        driver.get(DOMAIN_FOR_SEARCH);
        WebElement element = driver.findElement(By.name("query"));
        element.sendKeys(item.getWord());
        element.submit();
        byte[] screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        FileOutputStream stream = new FileOutputStream(path + "/" + item.getWord() + ".png");
        stream.write(screenshotFile);
        stream.close();

        item.setFileName(item.getWord());
        return item;
    }

    public void init() throws IOException {
        ChromeOptions chromeOptions = new ChromeOptions().setHeadless(true);
        driver = new ChromeDriver(chromeOptions);
        path = new ClassPathResource("static/image/").getFile().getAbsolutePath();
    }

    public void tearDown() {
        driver.quit();
    }
}
