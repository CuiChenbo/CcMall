package www.ccb.com.common.utils;


import android.util.Log;

/**
 * Created by admin on 2016/8/3.
 */
public class LogUtils {
    static boolean isDebag = true;

    public static void i(String content) {
        if (isDebag) {
            Log.i("Log", content);
        }
    }

    public static void e(Object content) {
        if (isDebag) {
            Log.e("Log", content + "");
        }
    }

    public static void i(String tag, String content) {
        if (isDebag) {
            Log.i(tag, content);
        }
    }

    public static void v(String content) {
        if (isDebag) {
            Log.v("Log", content);
        }
    }

    public static void v(String tag, String content) {
        if (isDebag) {
            Log.v(tag, content);
        }
    }
    /**
     * 截断输出日志
     * @param msg
     */
    public static void out(String tag, String msg) {
        if (isDebag) {
            if (tag == null || tag.length() == 0 || msg == null || msg.length() == 0) return;
            int segmentSize = 3 * 1024;
            long length = msg.length();
            if (length <= segmentSize ) {// 长度小于等于限制直接打印
                Log.i(tag, msg);
            }else {
                while (msg.length() > segmentSize ) {// 循环分段打印日志
                    String logContent = msg.substring(0, segmentSize );
                    msg = msg.replace(logContent, "");
                    Log.i(tag, logContent);
                }
                Log.i(tag, msg);// 打印剩余日志
            }
        }
    }
}
