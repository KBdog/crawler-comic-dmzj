package testapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.net.URLConnection;

public class TestSpider implements PageProcessor {
    private Site site = Site.me().setCycleRetryTimes(3000);
    @Override
    public void process(Page page) {
        String tmp=page.getJson().toString();
        System.out.println(tmp.charAt(0));

        if(tmp.charAt(0)=='['){
            JSONArray jsonArray=JSONArray.parseArray(tmp);
            System.out.println(JSON.toJSONString(jsonArray,SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat));
        }else{
            JSONObject jsonObject=JSONObject.parseObject(tmp);
            System.out.println(JSON.toJSONString(jsonObject,SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat));
        }
    }

    @Override
    public Site getSite() {
        site.setCharset("utf-8");
        site.addHeader("Referer","http://imgsmall.dmzj.com/");
        return site;
    }

    public static void main(String[] args) {
        Spider spider=Spider.create(new TestSpider());
        spider.addUrl("http://v3api.dmzj.com/v3/recommend.json");
//        spider.addUrl("http://sacg.dmzj.com/comicsum/search.php?s=无职转生");
        spider.run();
    }
}
