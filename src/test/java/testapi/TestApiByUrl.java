package testapi;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TestApiByUrl {
    public static void main(String[] args) throws IOException {
//        URL url=new URL("https://images.dmzj.com/webpic/6/wzzsbp20200823.jpg");
        URL url=new URL("https://mirror2.mangafunc.fun/comic/tayutadexuanze/fb251/90937426-88ae-11ea-b924-00163e0ca5bd.jpg!kb_m_read");
        URLConnection connection=url.openConnection();
        connection.setRequestProperty("referer","http://imgsmall.dmzj.com/");
//        connection.setRequestProperty("referer","http://manhua.dmzj.com/");
        connection.connect();
//        String line="";
//        String result="";
//        BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
//        while((line=br.readLine())!=null){
//            result+=line;
//        }
//        System.out.println(result);
//        br.close();

        File file=new File("C:\\Users\\Lenovo\\Desktop\\爬虫\\test_download\\tmp.jpg");
        InputStream inputStream=connection.getInputStream();
        BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);
        OutputStream outputStream=new FileOutputStream(file);
        byte []cache=new byte[1024];
        int i=bufferedInputStream.read(cache);
        while(i!=-1){
            outputStream.write(cache,0,i);
            i=bufferedInputStream.read(cache);
        }
        bufferedInputStream.close();
        outputStream.close();
        inputStream.close();
    }
}
