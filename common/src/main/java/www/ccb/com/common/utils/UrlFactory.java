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
     * start开始数据，count请求多少条；
     */
    public static String DoubanDataIn_theatersUrl = "https://api.douban.com/v2/movie/in_theaters?start=2&count=5"; //get 豆瓣电影上映中
    public static String DoubanDataTopUrl = "https://api.douban.com/v2/movie/top250"; //get 豆瓣电影排行榜


}
