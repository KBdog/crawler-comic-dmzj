package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import run.CrawlerRun;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/*
Downloader实现类
 */
public class ComicCrawlerDownloader implements Downloader, Closeable {
    @Override
    public void close() throws IOException {
        System.out.println("downloader closed!");
    }

    //download重写方法
    @Override
    public Page download(Request request, Task task) {
        System.out.println("进入downloader！");
        //加载chrome驱动二进制位置
        System.getProperties().setProperty("webdriver.chrome.driver", CrawlerRun.chromeDriverDirectory);
        //设置参数
        ChromeOptions options = new ChromeOptions();
        //配置chrome浏览器二进制位置
        options.setBinary(CrawlerRun.chromeExeDirectory);
        //参数配置
        options.addArguments("test-type"); //无视证书错误
        options.addArguments("headless");// 无头模式，后台运行
        options.addArguments("disable-gpu");
        options.addArguments("Cookie:display_mode=1");

        //从代理池中获取代理
        String proxy=ProxyPool.getProxy();
        System.out.println("当前代理："+proxy);
        //配置capability,把chromeoption放入capability中再放入chromedriver中
        DesiredCapabilities capabilities=DesiredCapabilities.chrome();
        //添加代理，使用快代理上的免费代理
        capabilities.setCapability("chrome.switches",
                Arrays.asList("--proxy-server=http://"+proxy));
        //添加chromeoption
        capabilities.setCapability(ChromeOptions.CAPABILITY,options);

        //加载驱动
//        WebDriver driver = new ChromeDriver(options);
        WebDriver driver=new ChromeDriver(capabilities);
        System.out.println("请求页:"+request.getUrl());
        //驱动请求访问
        driver.get(request.getUrl());
        //设置超时时间为10s，若10s内没有找到该元素(/html)则执行后续
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement webElement = driver.findElement(By.xpath("/html"));
        String content = webElement.getAttribute("outerHTML");
        //传递给process的page对象
        Page page = new Page();
        //ip被封或资源限制或没有成功爬到内容就把返回值设置成false让process重试
        if(content.contains("deny:")||content.contains("nginx/1.10.2")||content.contains("<body></body>")){
            page.setDownloadSuccess(false);
            System.out.println(content);
            System.out.println("访问网页出错！url:"+request.getUrl());
        }else {
            //page参数设置
            page.setRawText(content);
//            page.setHtml(new Html(content, request.getUrl()));
            page.setUrl(new PlainText(request.getUrl()));
            page.setRequest(request);
        }
        System.out.println("----------------downloader完----------------");
        driver.close();
        driver.quit();
        return page;
    }

    @Override
    public void setThread(int i) {

    }
}
