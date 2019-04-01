package www.ccb.com.common.utils;

public class UrlFactory {
    /**
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     */
    public static String DataUrl = "http://gank.io/api/data"; //get 分类数据

    /**
     * 获取最新一天的干货
     */
    public static String ToDayUrl = "http://gank.io/api/today"; //get 最新干货

    /**
     * 数据类型：福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
     * 个数： 数字，大于0
     */
    public static String RandomDataUrl = "http://gank.io/api/random/data"; //get 随机数据

    /**
     * 豆瓣
     * start开始数据，count请求多少条；
     * https://api.douban.com/v2/movie/in_theaters?start=2&count=5
     */
    public static String DoubanDataIn_theatersUrl = "https://api.douban.com/v2/movie/in_theaters"; //get 豆瓣电影上映中
    public static String DoubanDataTopUrl = "https://api.douban.com/v2/movie/top250"; //get 豆瓣电影排行榜

    /**
     * 新闻API
     * http://c.m.163.com/nc/article/headline/T1348647853363/0-40.html  头条
     * http://c.3g.163.com/nc/article/list/T1467284926140/0-20.html 精选
     * http://c.3g.163.com/nc/article/list/T1348648517839/0-20.html   娱乐
     * http://c.m.163.com/nc/auto/list/5bmz6aG25bGx/0-20.html 汽车
     * http://c.m.163.com/nc/auto/list/6YOR5bee/0-20.html  
     * http://c.m.163.com/nc/auto/list/6YOR5bee/20-20.html   
     * http://c.3g.163.com/nc/article/list/T1348649079062/0-20.html  运动
     * http://c.3g.163.com/nc/article/local/5bmz6aG25bGx/0-20.html 平顶山
     * http://c.m.163.com/nc/article/list/T1444270454635/0-20.html 漫画
     * http://c.m.163.com/nc/article/list/T1444270454635/20-20.html  更多
     */

    public static String NewsTopDataUrl = "http://c.m.163.com/nc/article/headline/T1348647853363/";
    public static String NewsGossipDataUrl = "http://c.3g.163.com/nc/article/list/T1348648517839/";

}
