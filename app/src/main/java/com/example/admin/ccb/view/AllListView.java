package com.example.admin.ccb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * NAME:CCB admin on 2016/8/16 10:15
 * 解决gridview只显示一行
 */
public class AllListView extends ListView {
    public AllListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AllListView(Context context) {
        super(context);
    }
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
