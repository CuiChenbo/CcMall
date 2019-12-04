package com.example.admin.ccb.activity;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.ccb.R;
import www.ccb.com.common.base.BaseActivity;

import com.example.admin.ccb.bean.GankBean;
import com.example.admin.ccb.utils.ResCcb;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Random;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import www.ccb.com.common.utils.UrlFactory;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * 仿抖音
 */
public class VideoPlayerDouActivity extends BaseActivity {

    private BaseQuickAdapter<GankBean.ResultsBean, BaseViewHolder> adapter;

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
        SnapHelper snapHelper = new PagerSnapHelper();
        if (rvPlayer.getOnFlingListener() == null) snapHelper.attachToRecyclerView(rvPlayer);
    }

    @Override
    protected void initData() {
        Jzvd.SAVE_PROGRESS = false; //视频播放器不保存播放进度
        adapter = new BaseQuickAdapter<GankBean.ResultsBean, BaseViewHolder>(R.layout.item_player_full) {
            @Override
            protected void convert(BaseViewHolder helper, GankBean.ResultsBean item) {
                JzvdStd jzvdStd = helper.getView(R.id.videoplayer);
//                jzvdStd.setUp( item,"", Jzvd.SCREEN_WINDOW_LIST,new JZMediaIjk(jzvdStd));
                jzvdStd.setUp(item.getUrl(), "", Jzvd.SCREEN_FULLSCREEN);
                Glide.with(mContext)
                        .load(item)
                        .into(jzvdStd.thumbImageView);
                jzvdStd.positionInList = helper.getAdapterPosition();
            }
        };
        rvPlayer.setAdapter(adapter);

        okGetRequest("1" , UrlFactory.DataUrl,Arrays.asList("休息视频","10",String.valueOf(page)));
    }

    int page = 1;

    @Override
    protected void initList() {
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                okGetRequest("1" , UrlFactory.DataUrl,Arrays.asList("休息视频","10",String.valueOf(++page)));
            }
        },rvPlayer);
        adapter.disableLoadMoreIfNotFullPage();

        rvPlayer.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                JzvdStd jzvdStd = view.findViewById(R.id.videoplayer);
                if (jzvdStd != null && jzvdStd.STATE_PLAYING == jzvdStd.state) {
                    JzvdStd.releaseAllVideos();
                }
            }
        });

        rvPlayer.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            int firstVisibleItem, lastVisibleItem, visibleCount;
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
//                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                if (layoutManager instanceof LinearLayoutManager) {
//                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
//                    lastVisibleItem = linearManager.findLastVisibleItemPosition();
//                    firstVisibleItem = linearManager.findFirstVisibleItemPosition();
//                    visibleCount = lastVisibleItem - firstVisibleItem;//记录可视区域item个数
//                }
            }

            private void autoPlayVideo(RecyclerView view) {
                //循环遍历可视区域videoview,如果完全可见就开始播放
//                for (int i = 0; i < visibleCount; i++) {
                    if (view == null || view.getChildAt(0) == null) return;
                    JzvdStd videoView = view.getChildAt(0).findViewById(R.id.videoplayer);
                    if (videoView != null) {
                        Rect rect = new Rect();
                        videoView.getLocalVisibleRect(rect);
                        int videoHeight = videoView.getHeight();
                        if (rect.top == 0 && rect.bottom == videoHeight && videoView.state != JzvdStd.STATE_PLAYING) {
                            videoView.startVideo();
                            return;
                        }
                    }
//                }
            }


        });
        startVideo();
    }

    private void startVideo(){
        rvPlayer.post(() -> {
            //自动播放第一个
            View view = rvPlayer.getChildAt(0);
            if (view == null) return;
            JzvdStd videoView = view.findViewById(R.id.videoplayer);
            if (videoView == null && videoView.state == JzvdStd.STATE_PLAYING) return;
            videoView.startVideo();
        });
    }

    @Override
    protected void okResponseSuccess(String whit, Object t) {
        super.okResponseSuccess(whit, t);
        if (TextUtils.equals("1",whit)){
            adapter.loadMoreComplete();
            GankBean datas = new Gson().fromJson((String)t,GankBean.class);
            if (!datas.isError()){
                for (int i = 0; i < datas.getResults().size(); i++) {
                    datas.getResults().get(i).setUrl(ResCcb.getVideoDatas().get(new Random().nextInt(ResCcb.getVideoDatas().size()-1)).toString());
                }
                adapter.addData(datas.getResults());
                startVideo();
            }
        }
    }

    @Override
    protected void okResponseError(String whit, String body) {
        super.okResponseError(whit, body);
        adapter.loadMoreFail();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
