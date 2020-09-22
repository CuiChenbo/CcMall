package www.ccb.com.common.utils;

public class UrlFactory {
    /**
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     */
    public static String DataUrl = "http://gank.io/api/data"; //干货集中营 GET接口
    public static String GirlUrl = "https://gank.io/api/v2/data/category/Girl/type/Girl"; //美女福利图片

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
     * http://c.3g.163.com/nc/article/list/T1348649079062/0-20.html  运动
     * http://c.3g.163.com/nc/article/local/5bmz6aG25bGx/0-20.html 平顶山
     */

    public static String NewsTopDataUrl = "http://c.3g.163.com/nc/article/list/T1467284926140/";
    public static String NewsGossipDataUrl = "http://c.3g.163.com/nc/article/list/T1348648517839/";

    /**
     * 糗事百科
     * http://m2.qiushibaike.com/article/list/{type}?type=refresh&page={page}&count={count}
     * 参数type为类型，latest最新、text文本、image图片、video视频
     * 参数page为页码；参数count为每页数量
     * 示例：http://m2.qiushibaike.com/article/list/text?type=refresh&page=1&count=12
     */
    public static String QiuBaiUrl = "http://m2.qiushibaike.com/article/list/text";
}
