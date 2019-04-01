package com.example.admin.ccb.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;

import www.ccb.com.common.utils.ToastUtils;

import static android.content.Context.DOWNLOAD_SERVICE;

public class PicTureUtils {

    //保存图片
    public static void saveThePicture(String UriPath,Context context){
        try{
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(UriPath));
            //设置允许使用的网络类型，这里是移动网络和wifi都可以
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            // 允许媒体扫描，根据下载的文件类型被加入相册、音乐等媒体库
            request.allowScanningByMediaScanner();
            //禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
            request.setShowRunningNotification(false);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            //不显示下载界面
            request.setVisibleInDownloadsUi(true);
//        request.setDestinationInExternalPublicDir("/download/Maxus/","Maxus"+System.currentTimeMillis()+".jpg");
            String loadName = UriPath.replace("/","");
            request.setDestinationInExternalPublicDir("/download/ccMall/",System.currentTimeMillis()+".jpg"); //使用图片的url保存，h5在使用是时可以根据url获取到；
//      request.setDestinationInExternalFilesDir(BaseWebActivity.this, Environment.DIRECTORY_PICTURES, System.currentTimeMillis() + ".jpg");  //这种方式下载后可以查看，但是找不到文件
            //*设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错，因此最好不设置如果sdcard可用，下载后的文件        在/mnt/sdcard/Android/data/packageName/files目录下面，如果sdcard不可用,设置了下面这个将报错，不设置，下载后的文件在/cache这个  目录下面*//*
            //request.setDestinationInExternalFilesDir(this, null, "tar.apk");
            long id = downloadManager.enqueue(request);
            IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            DownloadMangerReceiver mDownloadMangerReceiver = new DownloadMangerReceiver();
           context.registerReceiver(mDownloadMangerReceiver, intentFilter);
        }catch (SecurityException re) {
            re.printStackTrace();
            ToastUtils.showToast("图片下载失败，请打开本地存储权限");
        } catch (JsonSyntaxException je) {
            je.printStackTrace();
            ToastUtils.showToast("数据格式异常。");
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToast("图片下载失败，请检查开启储存权限");
        }
    }

   static class DownloadMangerReceiver extends BroadcastReceiver {
        private DownloadManager manager;

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            manager =(DownloadManager)context.getSystemService(DOWNLOAD_SERVICE);
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                Toast.makeText(context, "文件下载完成", Toast.LENGTH_SHORT).show();

                /*
                 * 获取下载完成对应的下载ID, 这里下载完成指的不是下载成功, 下载失败也算是下载完成,
                 * 所以接收到下载完成广播后, 还需要根据 id 手动查询对应下载请求的成功与失败.
                 */



                // 根据获取到的ID，使用上面第3步的方法查询是否下载成功
            }
        }
    }
}
