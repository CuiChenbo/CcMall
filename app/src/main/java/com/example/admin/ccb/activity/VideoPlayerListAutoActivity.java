package com.example.admin.ccb.activity;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import com.example.admin.ccb.base.BaseActivity;
import com.example.admin.ccb.utils.ResCcb;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * 列表式视频自动z播放
 */
public class VideoPlayerListAutoActivity extends BaseActivity {

    @Override
    public int getContentViewResource() {
        return R.layout.activity_video_player;
    }

    private RecyclerView rvPlayer;

    @Override
    protected void initView() {
        rvPlayer = findViewById(R.id.rvPlayer);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvPlayer.setLayoutManager(llm);
    }

    @Override
    protected void initData() {
        Jzvd.SAVE_PROGRESS = false; //视频播放器不保存播放进度
        rvPlayer.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_player, ResCcb.getVideoDatas()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                JzvdStd jzvdStd = helper.getView(R.id.videoplayer);
//                jzvdStd.setUp( item,"", Jzvd.SCREEN_WINDOW_LIST,new JZMediaIjk(jzvdStd));
                jzvdStd.setUp( item,"", Jzvd.SCREEN_WINDOW_LIST);
                Glide.with(mContext)
                        .load(item)
                        .into(jzvdStd.thumbImageView);
                jzvdStd.positionInList = helper.getAdapterPosition();
            }
        });
    }

    @Override
    protected void initList() {
        rvPlayer.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                JzvdStd jzvdStd = view.findViewById(R.id.videoplayer);
                if (jzvdStd != null && jzvdStd.CURRENT_STATE_PLAYING == jzvdStd.currentState) {
                    JzvdStd.resetAllVideos();
                }
            }
        });

        rvPlayer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItem, lastVisibleItem, visibleCount;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case SCROLL_STATE_IDLE: //滚动停止
                        autoPlayVideo(recyclerView);
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    lastVisibleItem = linearManager.findLastVisibleItemPosition();
                    firstVisibleItem = linearManager.findFirstVisibleItemPosition();
                    visibleCount = lastVisibleItem - firstVisibleItem;//记录可视区域item个数
                }
            }

            private void autoPlayVideo(RecyclerView view) {
                //循环遍历可视区域videoview,如果完全可见就开始播放
                for (int i = 0; i < visibleCount; i++) {
                    if (view == null || view.getChildAt(i) == null) return;
                    JzvdStd videoView = view.getChildAt(i).findViewById(R.id.videoplayer);
                    if (videoView != null) {
                        Rect rect = new Rect();
                        videoView.getLocalVisibleRect(rect);
                        int videoHeight = videoView.getHeight();
                        if (rect.top == 0 && rect.bottom == videoHeight && videoView.currentState != JzvdStd.CURRENT_STATE_PLAYING) {
                            videoView.startVideo();
                            return;
                        }
                    }
                }
            }


        });

        rvPlayer.post(() -> {
            //自动播放第一个
            View view = rvPlayer.getChildAt(0);
            JzvdStd videoView = view.findViewById(R.id.videoplayer);
            videoView.startVideo();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
