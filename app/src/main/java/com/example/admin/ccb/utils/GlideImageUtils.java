package com.example.admin.ccb.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.admin.ccb.R;

import www.ccb.com.common.utils.UiUtils;

/**
 * NAME:CCB on 2016/10/13 16:27
 * Glide图片加载框架  circle
 */
public class GlideImageUtils {
    /**
     * 加载普通图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void Display(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.default_icon);
        Glide.with(context)
                .load(url)
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);
    }

    public static void Display(Context context, String url, ImageView imageView,int failImgRes,boolean isCenterCrop) {
        RequestOptions options = new RequestOptions()
                .error(failImgRes);
        if (isCenterCrop){
            options.centerCrop();
        }
        Glide.with(context)
                .load(url)
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);
    }

//    public static void DisplayChatItem(final Context context, String url, final ImageView imageView) {
//        RequestOptions options = new RequestOptions()
//                .placeholder(R.mipmap.ic_launcher_round)
//                .error(R.mipmap.ic_launcher_round)
//                .diskCacheStrategy(DiskCacheStrategy.ALL);
//        final ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
//
//
//        Glide.with(context)
//                .load(url)
//                .apply(options)
//                .transition(new DrawableTransitionOptions().crossFade())
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        layoutParams.width = UiUtils.dip2px(90);
//                        layoutParams.height = UiUtils.dip2px(90);
//                        imageView.setLayoutParams(layoutParams);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        if (resource.getIntrinsicHeight() == resource.getIntrinsicWidth()) {
////                            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT ;
//                            layoutParams.height = UiUtils.dip2px(90);
//                            layoutParams.width = UiUtils.dip2px(90);
//                        } else if (resource.getIntrinsicHeight() > resource.getIntrinsicWidth()) {
//                            float scale = (float) resource.getIntrinsicWidth() / (float) (UiUtils.dip2px(81));
//                            if (scale > 1) {
//                                int vh = Math.round((float) resource.getIntrinsicHeight() / scale);
////                                layoutParams.height = vh;
//                                layoutParams.height = UiUtils.dip2px(140);
//                            } else {
//                                layoutParams.height = UiUtils.dip2px(140);
//                            }
//                            layoutParams.width = UiUtils.dip2px(90);
//                        } else if (resource.getIntrinsicHeight() < resource.getIntrinsicWidth()) {
//                            float scale = (float) resource.getIntrinsicHeight() / (float) (UiUtils.dip2px(81));
//                            if (scale > 1) {
//                                int vh = Math.round((float) resource.getIntrinsicWidth() / scale);
////                                layoutParams.width = vh;
//                                layoutParams.width = UiUtils.dip2px(140);
//                            } else {
//                                layoutParams.width = UiUtils.dip2px(140);
//                            }
//                            layoutParams.height = UiUtils.dip2px(90);
//                        }
//
//                        imageView.setLayoutParams(layoutParams);
//                        return false;
//                    }
//                })
//                .into(imageView);
//    }

    public static void DisplayHomeGridViewItem(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
//                .priority(Priority.HIGH)
                .transform(new GlideRoundTransform(context, 0, context.getResources().getColor(R.color.colorWhite))) //color_e0e0e0
//                .fitCenter()
                //                .diskCacheStrategy(DiskCacheStrategy.NONE)
                ;
        Glide.with(context).load(url)
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);
    }

    /**
     * 加载普通图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void DisplayNoPlaceholder(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.mipmap.icon_fail)
                .error(R.color.colorTransparent)
//                .priority(Priority.HIGH)
//                .fitCenter()
                //                .diskCacheStrategy(DiskCacheStrategy.NONE)
                ;

        Glide.with(context)
                .load(url)
//                .placeholder(R.color.colorWhite)
//                .error(R.color.colorWhite)
//                .centerCrop()
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);
    }

    public static void DisplayNoCropNoPlace(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(R.mipmap.icon_fail)
                .error(R.color.colorWhite)
//                .priority(Priority.HIGH)
//                .fitCenter()
                //                .diskCacheStrategy(DiskCacheStrategy.NONE)
                ;

        Glide.with(context)
                .load(url)
//                .placeholder(R.color.colorWhite)
//                .error(R.color.colorWhite)
//                .centerCrop()
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);
    }

    public static void DisplayNoPlaceholderErrorf0f0f0(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.color_f0f0f0)
                .error(R.color.color_f0f0f0)
//                .priority(Priority.HIGH)
//                .fitCenter()
                //                .diskCacheStrategy(DiskCacheStrategy.NONE)
                ;

        Glide.with(context)
                .load(url)
//                .placeholder(R.color.color_f0f0f0)
//                .error(R.color.color_f0f0f0)
//                .centerCrop()
//                .crossFade()
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);
    }

    /**
     * 加载图片，并切成圆形
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void DisplayCircle(final Context context, String url, final ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.default_icon)
                .error(R.mipmap.default_icon)
                ;
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void DisplayRoundCorner(final Context context, String url, final ImageView imageView,
                                          final int dpCorner) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.zanwutupian)
                ;
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        circularBitmapDrawable.setCornerRadius(UiUtils.dp2px(dpCorner));
                        circularBitmapDrawable.setAntiAlias(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }
}
