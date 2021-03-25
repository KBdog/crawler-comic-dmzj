# crawler-comic-dmzj
一个简单的dmzj爬虫工具（仅从网页端爬取）
## 说明
该工具使用了selenium对页面进行解析,webmagic进行解析数据的处理以及漫画图片的下载。这也是我第一个爬虫项目，技术渣请原谅。
## 依赖
1. chrome浏览器
2. chromedriver驱动，[下载地址](https://npm.taobao.org/mirrors/chromedriver/)(要下载对应浏览器版本的)
## 使用方法
1. 项目导入到IDEA
2. 在CrawlerRun类中修改自己的默认漫画存放路径、chrome浏览器路径和chromedriver路径(或每次启动自己再填入修改)
3. 运行启动类Starter
4. 选择dmzj漫画站的某个漫画首页并复制其url填入，如`https://manhua.dmzj.com/lastgame`
5. 点击开始（线程会监控下载过程，若完成下载会在url标签后面提示下载结束）

