package www.ccb.com.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * Created by admin on 2016/8/3.
 */
public class ToastUtils {
    private static Toast toast;
    private static Toast centerToast;
    // 静态toast

    /**
     * toast消失后  toast == null   如果没有消失 只需要改变 文本内容就可以
     * @param context
     * @param text
     */
    public static void showToast(Context context, String text) {

        if (toast == null)
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setText(text);
        toast.show();
    }
    /**
     * toast消失后  toast == null   如果没有消失 只需要改变 文本内容就可以
     * @param context
     * @param text
     */
    public static void makeText(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

//    不需要传递上下文的吐司
    public static void showToast(String text){
        if (toast == null){
            toast = toast.makeText(UiUtils.getContext(),text,Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }

    /**
     * 居中显示
     * @param text
     */
    public static void showCenterToast(String text){
        if (centerToast == null) {
            centerToast = Toast.makeText(UiUtils.getContext(), text, Toast.LENGTH_SHORT);
        }
        centerToast.setGravity(Gravity.CENTER, 0, 0);
        centerToast.setText(text);
        centerToast.show();
    }
    /**
     * 居中显示
     * @param text
     */
    public static void showCenterToast(Context context,String text){
        if (centerToast == null) {
            centerToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        centerToast.setGravity(Gravity.CENTER, 0, 0);
        centerToast.setText(text);
        centerToast.show();
    }

    /**
     * 居中有图片
     * @param context
     * @param text
     */
    public static Toast showImageToast(Context context, String text , int image) {
        Toast imagetoast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        LinearLayout toastView = (LinearLayout) imagetoast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setPadding(0,0,5,0);
        imageCodeProject.setImageResource(image);
        toastView.addView(imageCodeProject, 0);
        imagetoast.setGravity(Gravity.CENTER, 0, 0);
        imagetoast.setText(text);
        return imagetoast;
    }

    //  gson解析异常的吐司
    public static void GsonExtremely(){
        if (toast == null){
            toast = toast.makeText(UiUtils.getContext(),"╮(╯_╰)╭ 服务器这会儿不在状态～～",Toast.LENGTH_SHORT);
        }
        toast.setText("╮(╯_╰)╭ 服务器这会儿不在状态～～");
        toast.show();
    }


    //  网络请求失败的吐司
    public static void failNetRequest(){
        if (toast == null){
            toast = toast.makeText(UiUtils.getContext(),"请求服务器失败",Toast.LENGTH_SHORT);
        }
        toast.setText("请求服务器失败");
        toast.show();
    }

    //  ras解密失败
    public static void failRSADecrypt(){
        if (toast == null){
            toast = toast.makeText(UiUtils.getContext(),"解密失败",Toast.LENGTH_SHORT);
        }
        toast.setText("解密失败");
        toast.show();
    }


    private ToastUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /* 是否弹toast总开关 */
    public static boolean isShow = true;

    public static void showStaticToast(final Activity act, final String msg) {
        //获取当前线程
        String nowThreadName = Thread.currentThread().getName();
        //如果为主线程
        if ("main".equals(nowThreadName)) {
            if (isShow)
                showToast(act, msg);

            //如果为子线程
        } else {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isShow)
                        showToast(act, msg);
                }
            });
        }
    }


}
