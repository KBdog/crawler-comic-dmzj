package run;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import us.codecraft.webmagic.Spider;

import java.io.File;

public class Starter extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //标签
        final Label label1=new Label("爬虫保存目录:");
        final Label label2=new Label("chrome驱动:");
        final Label label3=new Label("chrome浏览器:");
        //文本框 默认值
        final TextField text1=new TextField(CrawlerRun.crawlerDirectory);
        final TextField text2=new TextField(CrawlerRun.chromeDriverDirectory);
        final TextField text3=new TextField(CrawlerRun.chromeExeDirectory);
        final TextField urlText=new TextField(CrawlerRun.mangaUrl);
        text1.setEditable(false);
        text2.setEditable(false);
        text3.setEditable(false);
        //网格面板
        GridPane gridPane1=new GridPane();
        gridPane1.setPadding(new Insets(10,5,10,8));
        //设置面板上结点的水平间距
        gridPane1.setHgap(5);
        //设置面板上结点的垂直距离
        gridPane1.setVgap(5);

        /*
        往网格面板上放置组件
         */
        //放置标签
        gridPane1.add(label1,0,0);
        gridPane1.add(label2,0,1);
        gridPane1.add(label3,0,2);
        //放置文本框
        gridPane1.add(text1,1,0);
        gridPane1.add(text2,1,1);
        gridPane1.add(text3,1,2);

        //按钮
        Button button1=new Button("···");
        Button button2=new Button("···");
        Button button3=new Button("···");
        Button startButton=new Button("开始爬虫");
        Button pauseButton=new Button("暂停");
        Button quitButton=new Button("退出程序");
        //选取文件/文件目录按钮
        gridPane1.add(button1,2,0);
        gridPane1.add(button2,2,1);
        gridPane1.add(button3,2,2);

        /*
        按钮
         */
        //button1
        //文件夹选择器
        DirectoryChooser chooser=new DirectoryChooser();
        //设置初始路径，默认为D盘
//        chooser.setInitialDirectory(new File("D:/"));
        //设置窗口标题
        chooser.setTitle("选择保存文件夹");
        //给按钮添加阴影特效
        button1.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            button1.setEffect(new DropShadow());
        });
        button1.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            button1.setEffect(null);
        });
        //按钮添加事件处理
        button1.setOnMouseClicked((MouseEvent e)->{
            try{
                //打开文件夹选择器
                String tmp=chooser.showDialog(primaryStage).getAbsolutePath()+"/";
                tmp=tmp.replace("\\","/");
                CrawlerRun.crawlerDirectory=tmp;
                text1.setText(CrawlerRun.crawlerDirectory);
                System.out.println("crawlerDirectory:选择了"+CrawlerRun.crawlerDirectory);
            }catch (Exception exception){
                CrawlerRun.crawlerDirectory=null;
                text1.setText(null);
                System.out.println("crawlerDirectory:没有选择文件夹");
            }
        });

        //button2
        FileChooser fileChooser1=new FileChooser();
        fileChooser1.setTitle("选择chrome驱动程序");
        button2.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            button2.setEffect(new DropShadow());
        });
        button2.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            button2.setEffect(null);
        });
        button2.setOnMouseClicked((MouseEvent e)->{
            try{
                //打开文件选择器
                String tmp=fileChooser1.showOpenDialog(primaryStage).getAbsolutePath();
                tmp=tmp.replace("\\","/");
                CrawlerRun.chromeDriverDirectory=tmp;
                text2.setText(CrawlerRun.chromeDriverDirectory);
                System.out.println("chromedriver:选择了"+CrawlerRun.chromeDriverDirectory);
            }catch (Exception exception){
                text2.setText(null);
                CrawlerRun.chromeDriverDirectory=null;
                System.out.println("chromedriver:没有选择文件");
            }
        });

        //button3
        FileChooser fileChooser2=new FileChooser();
        fileChooser2.setTitle("选择chrome浏览器");
        button3.addEventHandler(MouseEvent.MOUSE_ENTERED,(MouseEvent e)->{
            button3.setEffect(new DropShadow());
        });
        button3.addEventHandler(MouseEvent.MOUSE_EXITED,(MouseEvent e)->{
            button3.setEffect(null);
        });
        button3.setOnMouseClicked((MouseEvent e)->{
            try{
                //打开文件选择器
                String tmp=fileChooser1.showOpenDialog(primaryStage).getAbsolutePath();
                tmp=tmp.replace("\\","/");
                CrawlerRun.chromeExeDirectory=tmp;
                text3.setText(CrawlerRun.chromeExeDirectory);
                System.out.println("chrome:选择了"+CrawlerRun.chromeExeDirectory);
            }catch (Exception exception){
                text3.setText(null);
                CrawlerRun.chromeExeDirectory=null;
                System.out.println("chrome:没有选择文件");
            }
        });

        //startButton
        startButton.setOnMouseClicked((MouseEvent e)->{
            if(text1.getText().equals("")||text2.getText().equals("")
                    ||text3.getText().equals("")||urlText.getText().equals("")){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("信息填充不完善！");
                alert.showAndWait();
            }else{
                CrawlerRun.mangaUrl=urlText.getText();
                System.out.println("当前mangaUrl:"+CrawlerRun.mangaUrl);
//                CrawlerRun.spider.addUrl(CrawlerRun.mangaUrl)
//                        .setDownloader(CrawlerRun.downloader)
//                        .addPipeline(CrawlerRun.pipeline)
//                        .start();
                CrawlerRun.spider=Spider.create(CrawlerRun.crawler);
                CrawlerRun.spider.addUrl(CrawlerRun.mangaUrl)
                        .setDownloader(CrawlerRun.downloader)
                        .addPipeline(CrawlerRun.pipeline)
                        //开启五个线程爬
                        .thread(5)
                        .start();
//                urlText.setEditable(false);
            }
        });

        //pauseButton
        pauseButton.setOnMouseClicked((MouseEvent e)->{
            if(CrawlerRun.spider!=null){
                System.out.println("当前状态码:"+CrawlerRun.spider.getStatus());
                System.out.println("点击pauseButton");
                if(CrawlerRun.spider.getStatus().equals(Spider.Status.Running)){
//                CrawlerRun.mangaUrl=null;
                    CrawlerRun.spider.stop();
                }else {
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("状态码:"+CrawlerRun.spider.getStatus());
                    alert.setContentText("爬虫未开始！");
                    alert.showAndWait();
                    CrawlerRun.spider.stop();
                }
            }else{
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("错误信息:");
                alert.setContentText("爬虫未开始！");
                alert.showAndWait();
            }

        });

        //quitButton
        quitButton.setOnMouseClicked((MouseEvent e)->{
            System.out.println("点击quitButton");
//            Platform.exit();
            System.exit(0);
        });

        //x键
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });



        //放置程序运行相关按钮的面板
        GridPane gridPane2=new GridPane();
        gridPane2.setPadding(new Insets(10,8,10,8));
        //设置面板上结点的水平间距
        gridPane2.setHgap(40);
        //设置面板上结点的垂直距离
        gridPane2.setVgap(5);
        gridPane2.add(startButton,0,0);
        gridPane2.add(pauseButton,1,0);
        gridPane2.add(quitButton,2,0);



        //url信息
        GridPane urlPanel=new GridPane();
        urlPanel.setPadding(new Insets(10,5,10,8));
        urlPanel.setHgap(10);
        Label urlLabel=new Label("漫画首页Url:");
        urlText.setPromptText("请输入漫画url");
        urlPanel.add(urlLabel,0,0);
        urlPanel.add(urlText,1,0);
        //任务情况标签
        Label missionMsg=new Label("任务未开始");
        urlPanel.add(missionMsg,2,0);
        //监控线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(CrawlerRun.spider==null){
                        //表示爬虫未开始
//                        CrawlerRun.threadFlag=false;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                missionMsg.setText("任务未开始");
                            }
                        });
                    }else if(CrawlerRun.spider.getStatus().equals(Spider.Status.Running)){
                        //爬虫正在爬
//                        CrawlerRun.threadFlag=false;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                missionMsg.setText("任务正在进行中..");
                            }
                        });
                    }else if(CrawlerRun.spider.getStatus().equals(Spider.Status.Stopped)){
                        //表示漫画已经爬完，不管是人为原因停止还是系统自动爬完
//                        CrawlerRun.threadFlag=true;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                missionMsg.setText("任务已完成!");
                            }
                        });
                    }
                }
            }
        }).start();





        //总面板
        GridPane totalPanel=new GridPane();
        totalPanel.add(urlPanel,0,0);
        totalPanel.add(gridPane1,0,1);
        totalPanel.add(gridPane2,0,2);
        //视图  类似jframe
        Scene scene=new Scene(totalPanel,100,100);
        //application设置标题
        primaryStage.setTitle("动漫之家爬虫工具");
        //禁止最大化窗口
        primaryStage.setResizable(false);
        //设置面板大小
        primaryStage.setMinWidth(420);
        primaryStage.setMinHeight(260);
        //把按钮放置到面板上
        primaryStage.setScene(scene);
        primaryStage.show();



    }
}
