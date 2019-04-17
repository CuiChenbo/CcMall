package com.example.admin.ccb.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.PointerIcon;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;

import java.util.Arrays;
import www.ccb.com.common.base.BaseActivity;
import www.ccb.com.common.utils.LogUtils;

public class OrderDetailsActivity extends BaseActivity {

    @Override
    public int getContentViewResource() {
        return R.layout.activity_order_details;
    }

    private RecyclerView rv;

    @Override
    protected void initView() {
        rv = findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        rv.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_order_details, Arrays.asList("", "")) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setIsRecyclable(false);
//               RelativeLayout layout2 = helper.getView(R.id.layout2);
//                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) layout2.getLayoutParams();
//                layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT;
//                layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT;
//                layout2.setLayoutParams(layoutParams);
                if (helper.getAdapterPosition() == 0) {
                    helper.getView(R.id.layout1).setVisibility(View.GONE);
                    helper.getView(R.id.layout2).setVisibility(View.VISIBLE);
                } else {
                    helper.getView(R.id.layout1).setVisibility(View.VISIBLE);
                    helper.getView(R.id.layout2).setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initList() {
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isY2;
            int TRIGGERDISTANCE = 200;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当滚动结束时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LogUtils.i("getItemCount--" + manager.getItemCount());
                    LogUtils.i("findFirstCompletelyVisibleItemPosition--" + manager.findFirstCompletelyVisibleItemPosition());
                    LogUtils.i("findFirstVisibleItemPosition--" + manager.findFirstVisibleItemPosition());
                    LogUtils.i("findLastCompletelyVisibleItemPosition--" + manager.findLastCompletelyVisibleItemPosition());
                    LogUtils.i("findLastVisibleItemPosition--" + manager.findLastVisibleItemPosition());
                    if (manager.getItemCount() != 2) return;
                    View v1 = manager.findViewByPosition(0);
                    if (v1 == null) {
                        //在第二层时，不做任何的处理
                        isY2 = true;
                        return;
                    }
                    int height = v1.getMeasuredHeight(); //获取第一层布局高度
                    int bottom = v1.getBottom(); //获取第一层布局距离父容器顶部的距离
                    LogUtils.i(height + "---" + v1.getBottom());
                    if (height - bottom > TRIGGERDISTANCE && !isY2) { //在第一层时，滑动距离超过指定数值，就自动滑动到第二层；
                        moveToPosition(recyclerView, 1);
                        isY2 = true;
                        return;
                    } else if (height - bottom < TRIGGERDISTANCE && !isY2) { //在第一层时，滑动距离不超过指定数值，就自动回到第一层；
                        moveToPosition(recyclerView, 0);
                        isY2 = false;
                        return;
                    }

                    if (bottom > TRIGGERDISTANCE && isY2) { //当前在第二层时，滑动后触发自动滑动时；
                        moveToPosition(recyclerView, 0);
                        isY2 = false;
                        return;
                    } else if (bottom < TRIGGERDISTANCE && isY2) { //当前在第二层时，滑动后没有触发自动滑动时；
                        moveToPosition(recyclerView, 1);
                        isY2 = true;
                        return;
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                LogUtils.i("dx--" + dx + ",dy--" + dy);
            }
        });
    }

    /**
     * RecyclerView滑动到指定位置，并停留在顶部
     * @param rv
     * @param position
     */
    private void moveToPosition(RecyclerView rv, int position) {
        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(this) {
            @Override
            protected int getVerticalSnapPreference() {
    //            SNAP_TO_START使子视图的左侧或顶部与父视图的左侧或顶部对齐。
    //            SNAP_TO_END使子视图的右侧或底部与父视图的右侧面或底部对齐。
    //            SNAP_TO_ANY根据子视图的当前位置与父布局的关系，决定子视图是否从头到尾跟随。
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScroller.setTargetPosition(position);
        rv.getLayoutManager().startSmoothScroll(smoothScroller);
    }
}
