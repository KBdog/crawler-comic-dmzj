package testapi;

import com.google.common.io.Files;
import com.sun.org.apache.xalan.internal.lib.Extensions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import run.CrawlerRun;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.PlainText;
import utils.ProxyPool;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestAddExtension {
    public static void main(String[] args) {
        System.out.println("进入downloader！");
        //加载chrome驱动二进制位置
//        System.getProperties().setProperty("webdriver.chrome.driver", CrawlerRun.chromeDriverDirectory);
        //设置参数
        ChromeOptions options = new ChromeOptions();
        //配置chrome浏览器二进制位置
        options.setBinary(CrawlerRun.chromeExeDirectory);
        //参数配置
        options.addArguments("test-type"); //无视证书错误
//        options.addArguments("headless");// 无头模式，后台运行
        options.addArguments("disable-gpu");
        options.addArguments("Cookie:display_mode=1");
        options.addArguments("referer=http://imgsmall.dmzj.com/");
        //添加油猴插件
//        options.addExtensions(new File
//                ("D:\\下载文件目录\\chrome_driver\\Extensions\\dhdgffkkebhmkfjojejmpbldmpobfkfo\\4.11_0.crx"));
        //添加user-data
//        options.addArguments("--user-data-dir=D:\\下载文件目录\\chrome_driver\\user data");



        //从代理池中获取代理
        String proxy= ProxyPool.getProxy();
        System.out.println("当前代理："+proxy);
        //配置capability,把chromeoption放入capability中再放入chromedriver中
        DesiredCapabilities capabilities=DesiredCapabilities.chrome();
        //添加代理，使用快代理上的免费代理
        capabilities.setCapability("chrome.switches",
                Arrays.asList("--proxy-server=http://"+proxy));
        //添加chromeoption
        capabilities.setCapability(ChromeOptions.CAPABILITY,options);
        //使用service管理driver
        ChromeDriverService service=new ChromeDriverService.Builder().
                usingDriverExecutable(new File(CrawlerRun.chromeDriverDirectory)).usingAnyFreePort().build();
        WebDriver driver=new ChromeDriver(service,capabilities);
        //驱动请求访问
        driver.get("http://imgsmall.dmzj1.com/w/15663/34397/1.jpg");


//        String jsPath="D:\\下载文件目录\\chrome_driver\\js\\动漫之家助手.user.js";
//        String executor;
//        try {
//            executor = Files.toString(new File(jsPath), Charset.forName("utf-8"));
//            //加载js脚本
//            JavascriptExecutor js=(JavascriptExecutor) driver;
//            js.executeScript(executor);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //driver自身关闭驱动
        driver.close();
        driver.quit();
        //使用service关闭driver,关闭chrome driver接口
        service.stop();
    }
}
