package utils;

import run.CrawlerRun;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/*
Pipline实现类
取出process存储的数据
 */
public class ComicCrawlerPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            if (null != resultItems.get("manga")) {
                List<String> manga_list = resultItems.get("manga");
                if (!manga_list.isEmpty()) {
                    System.out.println("获取到了manga");
                    //五个线程，间隔1s
                    DownLoadImg(manga_list, 5, 500);
                    System.out.println("----------------pipeline完----------------------");
                }
            }else {
                System.out.println("获取不到manga");
                System.out.println("----------------pipeline完----------------------");
            }
        } catch (Exception e) {
            System.out.println("异常:");
            e.printStackTrace();
        }
    }

    private void DownLoadImg(final List<String> imgList, final int threadsize, final long sleeptime) {
        int count = 0;
        int size = imgList.size();
        System.out.println("当前漫画页数："+size);
        //开放多个线程进行并发下载
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadsize);
        CompletionService<String> cs = new ExecutorCompletionService<String>(fixedThreadPool);
        for (String url : imgList) {
            final String url1 = url;
            cs.submit(new Callable<String>() {
                public String call() throws Exception {
                    try {
                        Thread.sleep(sleeptime);
                        //测试 2020年11月26日10:55:24
//                        System.out.println(url1);
//                        return url1;
                        //下载图片
                        return down(url1);
                    } catch (InterruptedException e) {
                        System.out.println("线程异常");
                        return "error_" + "url1";
                    }
                }
            });
        }

        for (String url : imgList) {
            try {
                String a = cs.take().get();
                if (a != null) {
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (count == size) {
                    System.out.println("over");
                } else {
                    System.out.println(count + "/" + size);
                }
            }
        }
        fixedThreadPool.shutdown();
    }

    protected String down(String url) {
        try {
//            String directory_name="D:/下载文件目录/爬虫/";
            url = url.replace(" ", "");
            //存放爬虫的目录
//            File mangas_directory = new File("C:/Users/Lenovo/Desktop/爬虫/");
            File mangas_directory = new File(CrawlerRun.crawlerDirectory);
            if (!mangas_directory.exists() && !mangas_directory.isDirectory()) {
                mangas_directory.mkdir();
            }
            //存放漫画类别目录
//            File manga_directory = new File("C:/Users/Lenovo/Desktop/爬虫/" + url.split("___")[0]);
            File manga_directory = new File(CrawlerRun.crawlerDirectory + url.split("___")[0]);
            if (!manga_directory.exists() && !manga_directory.isDirectory()) {
                manga_directory.mkdir();
            }
            //存放漫画单话目录
//            File chapters_directory=new File("C:/Users/Lenovo/Desktop/爬虫/"+
//                    url.split("___")[0]+"/"+
//                    url.split("___")[1]);
            File chapters_directory=new File(CrawlerRun.crawlerDirectory+
                    url.split("___")[0]+"/"+
                    url.split("___")[1]);
            if(!chapters_directory.exists()&&!chapters_directory.isDirectory()){
                chapters_directory.mkdir();
            }
            //存放漫画单话每页图片
//            File img=new File("C:/Users/Lenovo/Desktop/爬虫/"+
//                    //漫画名
//                    url.split("___")[0]+"/"+
//                    //标题
//                    url.split("___")[1]+"/"+
//                    //页数
//                    url.split("___")[2]+".jpg");
            File img=new File(CrawlerRun.crawlerDirectory+
                    //漫画名
                    url.split("___")[0]+"/"+
                    //标题
                    url.split("___")[1]+"/"+
                    //页数
                    url.split("___")[2]+".jpg");
            if(!img.exists()){
                img.createNewFile();
            }

            //接收字节输入流
            InputStream is;
            //字节输出流
            FileOutputStream fos = new FileOutputStream(img);
            URL temp;
            String imgurl = url.split("___")[3];
            temp = new URL(imgurl.trim());
            HttpURLConnection uc = (HttpURLConnection) temp.openConnection();
            //添加header
            uc.addRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");
            //必须加refer 防封
            //漫画在url地址栏上的名字，如https://manhua.dmzj.com/wanfgodesabersang/63719.shtml中的wanfgodesabersang
            String manga_name_suffix=url.split("___")[3].split("/")[3]+"/";
            uc.addRequestProperty("Referer", "https://manhua.dmzj.com/"+manga_name_suffix);
            is = uc.getInputStream();
            //为字节输入流加缓冲
            BufferedInputStream bis = new BufferedInputStream(is);
            //为字节输出流加缓冲
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int length;
            byte[] bytes = new byte[1024 * 20];
            while ((length = bis.read(bytes, 0, bytes.length)) != -1) {
                fos.write(bytes, 0, length);
            }
            bos.close();
            fos.close();
            bis.close();
            is.close();
            return "success_" + "url1";
        } catch (Exception e) {
            e.printStackTrace();
            return "error_" + "url1";
        }
    }
}
