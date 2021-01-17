package run;

import us.codecraft.webmagic.Spider;
import utils.ComicCrawlerDownloader;
import utils.ComicCrawlerPipeline;
import utils.ComicCrawlerSpider;

public class StartCrawlering {
    public static void runCrawler(){
        ComicCrawlerSpider crawler=new ComicCrawlerSpider();
        ComicCrawlerDownloader downloader=new ComicCrawlerDownloader();
        ComicCrawlerPipeline pipeline=new ComicCrawlerPipeline();
        Spider spider=Spider.create(crawler)
                .addUrl("http://manhua.dmzj.com/tuipibalongqitongxue/")
                //自定义downloader和pipeline
                .setDownloader(downloader)
                .addPipeline(pipeline);
        spider.run();
        System.out.println("爬虫结束！");
    }
}
