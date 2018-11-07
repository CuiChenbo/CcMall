package www.ccb.com.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;


public class BaseApplication extends Application {

    private static BaseApplication instance;
    private static Stack<Activity> activityStack;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public String getCurrentProcessName() {
        String currentProcessName = "";
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        if (null == manager)
            return null;
        List<ActivityManager.RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();
        if (null == processInfoList || processInfoList.size() == 0)
            return null;
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
            if (processInfo.pid == pid) {
                currentProcessName = processInfo.processName;
                break;
            }
        }
        return currentProcessName;
    }

    public static BaseApplication get() {
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    public Activity getActivity(Class<?> cls) {
        if (activityStack == null)
            return null;
        if (activityStack.isEmpty())
            return null;
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 移除指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            // activity.finish();//此处不用finish
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?>... clses) {
        for (Class<?> cls : clses) {
            Iterator<Activity> iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                if (activity.getClass().equals(cls)) {
                    iterator.remove();
                    activity.finish();
                }
            }
        }
    }

    /**
     * 结束除参数外的所有activity
     *
     * @param clses
     */
    public void finishAllOther(Class<?>... clses) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            final Class actCls = activity.getClass();
            boolean finish = true;
            for (Class<?> cls : clses) {
                if (null == cls)
                    continue;
                if (actCls.equals(cls)) {
                    finish = false;
                    break;
                }
            }
            if (finish) {
                iterator.remove();
                activity.finish();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            iterator.remove();
            activity.finish();
        }
    }

    public void finishiTopActivity() {
        final int size = activityStack.size();
        if (size > 1) {
            activityStack.get(size - 1).finish();
        }
    }

    public static Activity getTopActivity() {
        if (activityStack.isEmpty())
            return null;
        final int size = activityStack.size();
        if (size >= 1) {
           return activityStack.get(size - 1);
        }else {
            return null;
        }
    }

    /**
     * 判断某Activity是否在栈中
     *
     * @param cls
     * @return
     */
    public boolean isExist(Class<?> cls) {
        if (activityStack == null)
            return false;
        if (activityStack.isEmpty())
            return false;
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        finishAllActivity();
    }
}
