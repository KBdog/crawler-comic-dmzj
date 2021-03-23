package testapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/*
    此API通过Charles抓包获得,未进行任何破解。此API仅供开发研究使用,API使用者造成的一切侵犯动漫之家权益的行为,请使用者自行承担责任。
    动漫之家搜索api:
    http://sacg.dmzj.com/comicsum/search.php?s=${comic/author}
    小说详情api:
    http://v2.api.dmzj.com/novel/${id}.json (旧)
    http://v3api.dmzj.com/novel/${id}.json (新)
    漫画详情api:
    http://v2.api.dmzj.com/comic/${id}.json (旧)
    http://v3api.dmzj.com/comic/comic_${id}.json(新)
    http://v3api.dmzj1.com/comic/comic_${id}.json(新)
    漫画下载api:
    https://imgzip.dmzj.com/${first_character/number}/${comic_id}}/${chapter_id}.zip
    其中
    ${first_character/number}：漫画名称首字母或者数字
    ${comic_id}:漫画id
    ${chapter_id}:章节id
    漫画章节信息api:
    http://v3api.dmzj.com/chapter/${comic_id}/${chapter_id}.json
    漫画章节吐槽api:
    http://v3api.dmzj.com/viewPoint/0/${comic_id}/${chapter_id}.json
 */
public class TestDmzjApi {
    public static void main(String[] args) throws IOException {
        String keyword="炎拳";
//        searchComicArray(keyword);
//        searchComicMsgById(32693);
        searchChaptersById(32693,83906);
//        downloadComicChapter("y",32693,83906);
    }

    /*
    @param:keyword
    动漫之家搜索api:
    http://sacg.dmzj.com/comicsum/search.php?s=${comic/author}
    http://sacg.dmzj1.com/comicsum/search.php?s=${comic/author}
    关键字查询所有结果
     */
    public static void searchComicArray(String keyword) throws IOException {
        URL url=new URL("http://sacg.dmzj1.com/comicsum/search.php?s="+keyword);
        URLConnection connection=url.openConnection();
        connection.connect();
        //io流操作
        InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String comicJson=bufferedReader.readLine();
        //截掉定义字符串
        String []comic_array=comicJson.split("var g_search_data = ");
        //截掉最后语句分号
        String result_comic_array=comic_array[1].substring(0,comic_array[1].length()-1);
        //解析json
        JSONArray jsonArray=JSONArray.parseArray(result_comic_array);
        //获取第一个搜索结果
        JSONObject firstComicJson=(JSONObject) jsonArray.get(0);
        //根据结果获取漫画id
        System.out.println("关键字："+keyword+"的漫画id-"+firstComicJson.get("id"));
        System.out.println(formatJson(firstComicJson));
    }

    /*
    @param:comic_id
    漫画详情api:
    http://v2.api.dmzj.com/comic/${id}.json (旧)
    http://v3api.dmzj.com/comic/comic_${id}.json(新)
    http://v3api.dmzj1.com/comic/comic_${id}.json(新)
    漫画简介信息
     */
    public static void searchComicMsgById(int comic_id) throws IOException {
        URL url=new URL("http://v3api.dmzj1.com/comic/comic_"+comic_id+".json");
        URLConnection connection=url.openConnection();
        connection.connect();
        //io流操作
        InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String comicJson=bufferedReader.readLine();
        //获取到漫画简介json
        JSONObject jsonObject=JSONObject.parseObject(comicJson);
        //获取chapters
        JSONArray json_chapters=(JSONArray) jsonObject.get("chapters");
        //获取datas
        JSONObject datas=json_chapters.getJSONObject(0);
        //获取chapters数组
        JSONArray chapters=(JSONArray) datas.get("data");
//        System.out.println(formatJson(chapters));
        for(int i=0;i<chapters.size();i++){
            JSONObject chapterMsg=chapters.getJSONObject(i);
            System.out.println("章节id:"+chapterMsg.get("chapter_id")
                    +"章节名:"+chapterMsg.get("chapter_title"));
        }

    }

    /*
    @param:chapter_id,comic_id
    漫画章节信息api:
    http://v3api.dmzj.com/chapter/${comic_id}/${chapter_id}.json
    http://v3api.dmzj1.com/chapter/${comic_id}/${chapter_id}.json
    漫画章节信息
     */
    public static void searchChaptersById(int comic_id,int chapter_id) throws IOException {
        URL url=new URL("http://v3api.dmzj1.com/chapter/"+comic_id+"/+"+chapter_id+"+.json");
        URLConnection connection=url.openConnection();
        connection.connect();
        //io流操作
        InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String comicJson=bufferedReader.readLine();
        //解析获得到的json字符串
        JSONObject jsonObject=JSONObject.parseObject(comicJson);
        //获取每一页的url json
        JSONArray urlObject= jsonObject.getJSONArray("page_url");
        //截掉前后的中括号
        String tmpUrlString=urlObject.toString().substring(1,urlObject.toString().length()-1);
        //根据逗号分割每个url
        String []urlList=tmpUrlString.split(",");
        for(int i=0;i<urlList.length;i++){
            //去掉头尾的引号
            System.out.println(urlList[i].replaceAll("\"",""));
        }
    }

    /*
    @param:first_number,comic_id,chapter_id
    漫画下载api:
    https://imgzip.dmzj.com/${first_character/number}/${comic_id}}/${chapter_id}.zip
    下载zip
     */
    public static void downloadComicChapter(String first_number,int comic_id,int chapter_id) throws IOException {
        String urlString="https://imgzip.dmzj1.com/"+
                first_number+"/"+ comic_id+"/"+chapter_id+".zip";
        URL url=new URL(urlString);
        URLConnection connection=url.openConnection();
        connection.setRequestProperty("referer","http://imgsmall.dmzj.com/");
        connection.connect();
        //io流操作
        File file=new File("C:\\Users\\Lenovo\\Desktop\\爬虫\\tmp.zip");
        InputStream inputStream=connection.getInputStream();
        BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);
        OutputStream outputStream=new FileOutputStream(file);
        byte []cache=new byte[1024];
        int i=bufferedInputStream.read(cache);
        while(i!=-1){
            outputStream.write(cache,0,i);
            i=bufferedInputStream.read(cache);
        }
    }

    //格式化json
    public static String formatJson(JSON json){
        return JSON.toJSONString(json,SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
    }

}
