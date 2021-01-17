package run;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import utils.ComicCrawlerDownloader;
import utils.ComicCrawlerPipeline;
import utils.ComicCrawlerSpider;

public class CrawlerRun {
    //文件/文件夹信息
    public static String crawlerDirectory="D:/下载文件目录/爬虫/";
    public static String chromeDriverDirectory="D:/下载文件目录/chrome_driver/chromedriver.exe";
    public static String chromeExeDirectory="C:/Program Files (x86)/Google/Chrome/Application/chrome.exe";
    public static String mangaUrl="";
    //漫画爬虫线程标志 false表示漫画未爬完 true表示漫画已经爬完
//    public static boolean threadFlag=false;
    //webmagic组件
    public static ComicCrawlerSpider crawler=new ComicCrawlerSpider();
    public static ComicCrawlerDownloader downloader=new ComicCrawlerDownloader();
    public static ComicCrawlerPipeline pipeline=new ComicCrawlerPipeline();
    //spider
//    public static Spider spider=Spider.create(crawler).setDownloader(downloader).addPipeline(pipeline);
//    public static Spider spider=Spider.create(crawler);
    public static Spider spider;

    public static void main(String[] args) {
//        Spider spider=Spider.create(crawler)
//                .addUrl("http://manhua.dmzj.com/weinixianshangchuye")
//                //自定义downloader和pipeline
//                .setDownloader(downloader)
//                .addPipeline(pipeline);
//        spider.run();
//        System.out.println("爬虫结束！");
//        System.out.println(spider.getStatus());

//        spider.run();
    }
}
