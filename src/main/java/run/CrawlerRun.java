package run;

import us.codecraft.webmagic.Spider;
import utils.ComicCrawlerDownloader;
import utils.ComicCrawlerPipeline;
import utils.ComicCrawlerSpider;

public class CrawlerRun {
    //文件/文件夹信息
    public static String crawlerDirectory="D:/下载文件目录/爬虫/";
    public static String chromeDriverDirectory="D:/下载文件目录/chrome_driver/chromedriver.exe";
    public static String chromeExeDirectory="C:/Program Files (x86)/Google/Chrome/Application/chrome.exe";
    public static String mangaUrl="";
    //webmagic组件
    public static ComicCrawlerSpider crawler=new ComicCrawlerSpider();
    public static ComicCrawlerDownloader downloader=new ComicCrawlerDownloader();
    public static ComicCrawlerPipeline pipeline=new ComicCrawlerPipeline();
    public static Spider spider;

}
