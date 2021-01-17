package testapi;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TestApiByUrl {
    public static void main(String[] args) throws IOException {
        URL url=new URL("https://images.dmzj.com/webpic/17/whengkeaisda1209.jpg");
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

        File file=new File("C:\\Users\\Lenovo\\Desktop\\爬虫\\tmp.jpg");
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
}
