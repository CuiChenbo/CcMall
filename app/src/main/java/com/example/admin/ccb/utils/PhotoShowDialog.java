package com.example.admin.ccb.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.admin.ccb.R;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author cuiChenBo
 * Created by zz on 2018/2/27 15:28.
 * 　　class explain:
 * 　　　　update:       upAuthor:      explain:
 */

public class PhotoShowDialog extends Dialog {
    private Context mContext;
    private List<String> photoLists;
    private int mPosition;
    private ViewPager vp;
    private TextView tv;

    public static PhotoShowDialog get(Context context,String photo){
        return new PhotoShowDialog(context,photo);
    }

    public PhotoShowDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public PhotoShowDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    public PhotoShowDialog(Context context,String photos) {
        this(context, R.style.Pic_Dialog);
        List s = new ArrayList<String>();
        s.add(photos);
        this.photoLists = s;
    }

    public PhotoShowDialog(Context context, List<String> photoLists, int position) {
        this(context, R.style.Pic_Dialog);
        this.photoLists = photoLists;
        this.mPosition = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_dialog);
        init();
    }

    private void init() {
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        vp = findViewById(R.id.vp);
        tv = findViewById(R.id.tv);
        vp.setAdapter(new VpAdapter(mContext));
        vp.setCurrentItem(mPosition);
        tv.setText(vp.getCurrentItem()+1+"/"+photoLists.size());
        tv.setVisibility(photoLists.size() == 1 ? View.INVISIBLE:View.VISIBLE);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv.setText(position+1+"/"+photoLists.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class VpAdapter extends PagerAdapter {
        Context context;
        public VpAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return photoLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view =View.inflate(context, R.layout.item_photo, null);
            PhotoView photoView = view.findViewById(R.id.photo);
                RequestOptions options = new RequestOptions()
                        .placeholder(R.mipmap.loading)
                        .error(R.mipmap.zanwutupian);
                Glide.with(context)
                        .load(photoLists.get(position))
                        .apply(options)
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(photoView);

            photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(ImageView view, float x, float y) {
                    PhotoShowDialog.this.dismiss();
                }
            });
            ((ViewPager) container).addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }
}
