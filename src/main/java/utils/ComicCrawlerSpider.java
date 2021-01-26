package utils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

/*
processor实现类
要爬的数据以及后续链式爬取的路径
 */
public class ComicCrawlerSpider implements PageProcessor {
    //标志
    private boolean flag=false;
    //设置抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    //两秒爬一次防止封ip
    private Site site = Site.me().setCycleRetryTimes(3000);
//                        .setSleepTime(500)
//                        .setTimeOut(5000)
//                        .setCycleRetryTimes(3000);
    //漫画图片集合
    private List<String> mangaImgList=new ArrayList<>();


    //根据dmzj漫画域名爬漫画https://manhua.dmzj.com/
    //备用域名https://manhua.dmzj1.com
    public void crawComicByOldUrl(Page page){
        //漫画首页url
        String indexPage=page.getUrl().toString();
        //从漫画首页获得所有章节
        List<String> chapterUrls=page.getHtml().xpath("//div[@class='cartoon_online_border']/ul/li/a/@href").all();
        for (String chapterUrl : chapterUrls) {
            System.out.println("chapterUrl:"+chapterUrl);
            Request request;
            //manhua.dmzj1.com
            if(indexPage.indexOf("manhua.dmzj1.com")!=-1){
                request=new Request("https://manhua.dmzj1.com"+chapterUrl);
                //manhua.dmzj.com
            }else {
                request=new Request("https://manhua.dmzj.com"+chapterUrl);
            }
            request.addHeader("Cookie", "display_mode=1");
            //把所有请求都放入队列准备访问
            page.addTargetRequest(request);
        }
        //进入章节目录后记录标题等信息
        if(flag){
            //标题
            String title=page.getHtml().xpath("//div[@class='display_graybg']" +
                    "/div[@class='display_middle']" +
                    "/span[@class='redhotl']/text()").toString();
            //漫画名
            String manga_name=page.getHtml().xpath("//div[@class='display_graybg']" +
                    "/div[@class='display_middle']" +
                    "/h1[@class='hotrmtexth1']/a/text()").toString();
            //在控制台打印漫画信息
            System.out.println(manga_name+":"+title+" "+page.getUrl());
            //从downloader中获取的page不完全，再把url重新放入队列中
            if(manga_name==null){
//                page.setDownloadSuccess(false);
//                page.addTargetRequest(page.getUrl().toString());
                System.out.println("空！！！");
            }
            //在url中漫画的名
            //System.out.println(page.getUrl().toString().split("/")[3]);
            //获取漫画章节选择器dom结点
            List<Selectable> s=page.getHtml().xpath("//div[@class='btmBtnBox']/select/option").nodes();
            //给mangaimglist放置内容时先清空
            mangaImgList.clear();
            //漫画名___标题___页数___图片地址
            for (Selectable selectable : s) {
                mangaImgList.add(manga_name+"___"+title+"___"+selectable.xpath("/option/text()")+"___"+
                        "https:"+selectable.xpath("/option/@value"));
            }
            //保存爬取结果
            page.putField("manga",mangaImgList);
        }
        System.out.println("-----------------process完---------------------");
        //第一次把请求放入队列后把标志值设为真，开始进行爬取每章标题的功能
        flag=true;
    }

    //process重写方法
    @Override
    public void process(Page page) {
        crawComicByOldUrl(page);
    }

    @Override
    public Site getSite() {
//        site.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");
//        //2020年11月26日12:18:25 加refer模拟从dmzj官网切换的页面
//        site.addHeader("Referer","https://manhua.dmzj.com/");
        return site;
    }

}
